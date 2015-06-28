/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Dao;

import Model.Account.Account;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Lilium Aikia
 */
public class AccountDao 
{
    private String _dataPath;
    private Document _doc;
    private Element _root;
    
    private void createNewDataFile() throws ParserConfigurationException, TransformerException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("AccountDatabase");
        doc.appendChild(rootElement);
        
        Attr attr = doc.createAttribute("LastAccountId");
        attr.setValue("0");
        rootElement.setAttributeNode(attr);
        
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(_dataPath));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
}
    
    public AccountDao() throws ParserConfigurationException, SAXException, IOException, TransformerException
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
    }
    
    public Element toElement(Account input)
    {
        Element result = null;
        
        if(input != null)
        {
            result = _doc.createElement("Account");
            _root.appendChild(result);
            
            Attr username = _doc.createAttribute("Username");
            result.setAttributeNode(username);
            username.setValue(input.getName());
        }
        
        return result;
    }
    
    public int insertAccount(Account account)
    {
        int result = -1;
        
        if(account != null)
        {
            int lastId = Integer.parseInt(_root.getAttribute("LastAccountId"));
            lastId++;
            _root.setAttribute("LastAccountId", String.valueOf(lastId));
            
            Element accountXml = toElement(account);
            
            Attr id = _doc.createAttribute("Id");
            accountXml.setAttributeNode(id);
            id.setValue(String.valueOf(lastId));
        }
        
        return result;
    }
}
