/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DataAccessLayer;

import Model.Account.Account;
import Model.Account.FacebookUser;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Lilium Aikia
 */
public class AccountDao 
{
    private static AccountDao _instance= null;
    public static AccountDao getInstance() throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        if(_instance == null)
        {
            _instance = new AccountDao();
        }
        
        return _instance;
    }
    
    private String _dataPath;
    private String _loginDataPath;
    private Document _doc;
    private Document _loginDoc;
    private Element _root;
    private Element _loginRoot;
    
    private void saveDatabase(Document doc, String path) throws TransformerConfigurationException, TransformerException
    {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }
    
    private void createNewDataFile() throws ParserConfigurationException, TransformerException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        _doc = docBuilder.newDocument();
        _root = _doc.createElement("AccountDatabase");
        _doc.appendChild(_root);
        
        createAttribute(_root, "LastAccountId", "0");
        createAttribute(_root, "LastDocumentId", "0");
        
        saveDatabase(_doc, _dataPath);
    }
    
    private void createNewLoginFile() throws ParserConfigurationException, TransformerException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        _loginDoc = docBuilder.newDocument();
        _loginRoot = _loginDoc.createElement("AccountLoginDatabase");
        _loginDoc.appendChild(_loginRoot);
        
        createAttribute(_loginRoot, "LastAccountLoginId", "0");
        
        saveDatabase(_doc, _dataPath);
    }
    
    private AccountDao() throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        _dataPath = "account.xml";
        File fXmlFile = new File(_dataPath);
        if(fXmlFile.exists())
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            _doc = dBuilder.parse(fXmlFile);
            _doc.normalize();
            _root = _doc.getDocumentElement();
        }
        else
        {
            createNewDataFile();
        }
        
        _loginDataPath = "account_login.xml";
        File alFile = new File(_loginDataPath);
        if(alFile.exists())
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            _loginDoc = dBuilder.parse(alFile);
            _loginDoc.normalize();
            _loginRoot = _loginDoc.getDocumentElement();
        }
        else
        {
            createNewLoginFile();
        }
    }
    
    private Attr createAttribute(Element parent, String name, String value)
    {
        Attr result = null;      
        
        if(parent != null)
        {
            result = parent.getOwnerDocument().createAttribute(name);
            parent.setAttributeNode(result);
            result.setValue(value);
        }
        
        return result;
    }
    
    public boolean setAttributeValue(Element parent, String name, String value)
    {
        boolean result = false;
        
        if(parent != null)
        {
            Attr attr = parent.getAttributeNode(name);
            if(attr != null)
            {
                attr.setValue(value);
                result = true;
            }
        }
        
        return result;
    }
    
    public Element toElement(Account input)
    {
        Element result = null;
        
        if(input != null)
        {
            result = _doc.createElement("Account");
            _root.appendChild(result);
            
            createAttribute(result, "Id", String.valueOf(input.getId()));
            createAttribute(result, "Username", input.getUsername());
            createAttribute(result, "Password", input.getPassword());
            createAttribute(result, "Email", input.getEmail());
            createAttribute(result, "FacebookAccessToken", input.getFacebookAccessToken());
            createAttribute(result, "FacebookUserId", input.getFBUserId());
        }
        
        return result;
    }
    
    public NodeList getNodes(String xPathString, Element dataSource) throws XPathExpressionException
    {
        NodeList result = null;
        
        if(xPathString != null
                && dataSource != null)
        {
            XPath xPath = XPathFactory.newInstance().newXPath();
            result = (NodeList)xPath.evaluate(xPathString, dataSource, XPathConstants.NODESET);
        }    
        
        return result;
    }
    
    public int insertAccount(Account account) throws XPathExpressionException, TransformerException
    {
        int result = -1;
        
        if(account != null
                && !exists(account.getFBUser().getUid(), "facebookUserId"))
        {
            int lastId = Integer.parseInt(_root.getAttribute("LastAccountId"));
            lastId++;
            result = lastId;
            _root.setAttribute("LastAccountId", String.valueOf(lastId));
            
            Element accountXml = toElement(account);
            _root.appendChild(accountXml);          
            
            setAttributeValue(accountXml, "Id", String.valueOf(result));
            createAttribute(accountXml, "DateRegister", new Date().toString());
            
            saveDatabase(_doc, _dataPath);
        }
        
        return result;
    }
    
    public boolean delete(int id) throws XPathExpressionException, TransformerException
    {
        boolean result = false;
        
        NodeList exists = getNodes("/AccountDatabase/Account[@Id=" + id + "]", _root);
        if(exists.getLength() > 0)
        {
            for (int i=0; i<exists.getLength(); i++) {
                _root.removeChild(exists.item(i));
            }
        }
        
        saveDatabase(_doc, _dataPath);
        
        return result;
    }
    
    public boolean update(int id, Account newData) throws XPathExpressionException, TransformerException
    {
        boolean result = false;
        
        if(newData != null)
        {
            NodeList exists = getNodes("/AccountDatabase/Account[@Id=" + id + "]", _root);
            if(exists.getLength() == 1)
            {
                setAttributeValue((Element) exists.item(0), "Password", newData.getPassword());
                setAttributeValue((Element) exists.item(0), "Email", newData.getEmail());
                setAttributeValue((Element) exists.item(0), "FacebookAccessToken", newData.getEmail());
                setAttributeValue((Element) exists.item(0), "FacebookUserId", newData.getEmail());
                saveDatabase(_doc, _dataPath);
                
                result = true;
            }
        }      
        
        return result;
    }
    
    public Account fromElement(Element input)
    {
        Account result = null;
        
        if(input != null)
        {
            result = new Account();
            
            result.setId(Integer.parseInt(input.getAttribute("Id")));
            result.setDateRegister(new Date(input.getAttribute("DateRegister")));
            result.setUsername(input.getAttribute("Username"));
            result.setPassword(input.getAttribute("Password"));
            result.setEmail(input.getAttribute("Email"));
            result.setFBUser(new FacebookUser(null, input.getAttribute("FacebookAccessToken")));
        }
        
        return result;
    }
    
    public Account getUser(String attrName, String attrValue) throws XPathExpressionException
    {
        Account result = null;
        
        NodeList exists = getNodes("/AccountDatabase/Account[@" + attrName + "=" + attrValue + "]", _root);
        if(exists.getLength() == 1)
        {
            result = fromElement((Element) exists.item(0));
        }
        
        return result;
    }
    
    public Account[] getUsers() throws XPathExpressionException
    {
        Account[] result = null;
        
        NodeList exists = getNodes("/AccountDatabase/Account", _root);
        if(exists.getLength() > 0)
        {
            result = new Account[exists.getLength()];
            for(int i=0; i<exists.getLength(); i++)
            {
                result[i] = fromElement((Element) exists.item(i));
            }
        }
        
        return result;
    }
    
    public Account[] getLoginUsers() throws XPathExpressionException
    {
        Account[] result = null;
        
        NodeList exists = getNodes("/AccountLoginDatabase/Account", _loginRoot);
        if(exists.getLength() > 0)
        {
            result = new Account[exists.getLength()];
            for(int i=0; i<exists.getLength(); i++)
            {
                Element id = (Element) exists.item(i);
                result[i] = getUser("Id", id.getAttribute("AccountId"));
            }
        }
        
        return result;
    }
    
    public boolean exists(String id, String type) throws XPathExpressionException
    {
        boolean result = false;
        
        NodeList exists = null;
        
        if(null != type)
        switch (type) {
            case "username":
                exists = getNodes("/AccountDatabase/Account[@Username=" + id + "]", _root);
                break;
            case "facebookUserId":
                exists = getNodes("/AccountDatabase/Account[@FacebookUserId=" + id + "]", _root);
                break;
        }
        
        if(exists != null)
        {
            if(exists.getLength() > 0)
            {
                result = true;
            }
        }        
        
        return result;
    }
    
    public String login(String username, String password) throws XPathExpressionException, TransformerException
    {
        String result = "";
        
        Account user = getUser("Username", username);
        if(user != null)
        {
            NodeList exists = getNodes("/AccountLoginDatabase/Account[@Username=" + username + "]", _loginRoot);
            Date now = new Date();
            
            result = username + now.toString() + password;
             
            if(exists.getLength() == 1)
            {
                setAttributeValue((Element) exists.item(0), "DateLogin", now.toString());
                setAttributeValue((Element) exists.item(0), "LoginToken", result);
            }
            else
            {
                Element login = _loginDoc.createElement("Account");
                _loginRoot.appendChild(login);
                
                createAttribute(login, "Id", String.valueOf(user.getId()));
                createAttribute(login, "Username", user.getUsername());
                createAttribute(login, "FacebookUserId", user.getFBUserId());
                createAttribute(login, "FacebookAccessToken", user.getFacebookAccessToken());
                createAttribute(login, "DateLogin", now.toString());
                createAttribute(login, "LoginToken", result);
            }
            
            saveDatabase(_loginDoc, _loginDataPath);
        }
        
        return result;
    }
    
    public String loginWithFacebook(String fBUserId, String accessToken) throws XPathExpressionException, TransformerException
    {
        String result = "";
        
        Account user = getUser("FacebookUserId", fBUserId);
        if(user != null)
        {
             NodeList exists = getNodes("/AccountLoginDatabase/Account[@FacebookUserId=" + fBUserId + "]", _loginRoot);
             Date now = new Date();
             
             result = fBUserId + now.toString() + accessToken;
             
            if(exists.getLength() == 1)
            {
                setAttributeValue((Element) exists.item(0), "DateLogin", now.toString());
                setAttributeValue((Element) exists.item(0), "LoginToken", result);
            }
            else
            {
                Element login = _loginDoc.createElement("Account");
                _loginRoot.appendChild(login);
                
                createAttribute(login, "Id", String.valueOf(user.getId()));
                createAttribute(login, "Username", user.getUsername());
                createAttribute(login, "FacebookUserId", user.getFBUserId());
                createAttribute(login, "FacebookAccessToken", user.getFacebookAccessToken());
                createAttribute(login, "DateLogin", now.toString());
                createAttribute(login, "LoginToken", result);
            }
            
            saveDatabase(_loginDoc, _loginDataPath);
        }
        
        return result;
    }
    
    public void logout(String id, String type) throws XPathExpressionException
    {
        NodeList exists = null;
        
        switch (type) {
            case "username":
                exists = getNodes("/AccountLoginDatabase/Account[@Username=" + id + "]", _loginRoot);
                break;
            case "facebookUserId":
                exists = getNodes("/AccountLoginDatabase/Account[@FacebookUserId=" + id + "]", _loginRoot);
                break;
        }
        
        if(exists != null)
        {
            if(exists.getLength() > 0)
            {
                for(int i=0; i<exists.getLength(); i++)
                {
                    _loginRoot.removeChild(exists.item(i));
                }
            }
        }      
    }
    
    public int createDocument(String id, String name, String fBUserId) throws TransformerException
    {
        int result;
        
        int lastId = Integer.parseInt(_root.getAttribute("LastDocumentId"));
        lastId++;
        result = lastId;
        _root.setAttribute("LastDocumentId", String.valueOf(lastId));

        Element documentXml = _doc.createElement("Document");
        _root.appendChild(documentXml);          

        createAttribute(documentXml, "Id", String.valueOf(result));
        createAttribute(documentXml, "DateCreated", new Date().toString());
        createAttribute(documentXml, "DocumentId", id);
        createAttribute(documentXml, "OwnerId", fBUserId);
        createAttribute(documentXml, "Name", name);

        saveDatabase(_doc, _dataPath);
        
        return result;
    }
    
    public boolean deleteDocument(String id) throws XPathExpressionException, TransformerException
    {
        boolean result = false;
        
        NodeList exists = getNodes("/AccountDatabase/Document[@Id=" + id + "]", _root);
        if(exists.getLength() > 0)
        {
            for (int i=0; i<exists.getLength(); i++) {
                _root.removeChild(exists.item(i));
            }
        }
        
        saveDatabase(_doc, _dataPath);
        
        return result;
    }
}
