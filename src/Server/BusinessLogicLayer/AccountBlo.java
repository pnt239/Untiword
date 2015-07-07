/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.BusinessLogicLayer;

import Controller.AccountController.FacebookController;
import Model.Account.Account;
import Model.Account.FacebookUser;
import Server.DataAccessLayer.AccountDao;
import java.io.IOException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import libraryclasses.PasswordEncryption;
import org.xml.sax.SAXException;

/**
 *
 * @author Lilium Aikia
 */
public class AccountBlo 
{
    private static AccountBlo _instance = null;
    public static AccountBlo getInstance() 
            throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        if(_instance == null)
        {
            _instance = new AccountBlo();
        }
        
        return _instance;
    }
    
    private AccountBlo() throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
    }
    
    public boolean exists(String userToken) 
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException
    {
        boolean result = false;
        
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        
        if(user != null)
        {
            if(AccountDao.getInstance().exists(user.getUid(), "facebookUserId"))
            {
                result = true;
            }
        }
        
        return result;
    }
    
    public Account getUser(String attrName, String attrValue) 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        return AccountDao.getInstance().getUser(attrName, attrValue);
    }
    
    public Account[] getUsers() 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        return AccountDao.getInstance().getUsers();
    }
    
    public Account[] getLoginUsers() 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        return AccountDao.getInstance().getLoginUsers();
    }
    
    private boolean isValidEmailAddress(String email) 
    {
        boolean result = true;
        try 
        {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } 
        catch (AddressException ex) 
        {
           result = false;
        }
        return result;
    }
    
    public int register(String username, String password, String email) 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        int result = -1;
        
        if(email != null
                && isValidEmailAddress(email)
                && !AccountDao.getInstance().exists(email, "email"))
        {
            Account user = new Account();
            user.setUsername(username);
            user.setPassword(PasswordEncryption.encryptPassword(password));
            user.setEmail(email);
            
            result = AccountDao.getInstance().insertAccount(user);
        }
        
        return result;
    }
    
    public String login(String applicationId, String email, String password)
    {
        String result = "";
        
        return result;
    }
    
    public String loginWithFacebook(String applicationId, String userToken) 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        String result = "";
        
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        
        if(user != null)
        {
            if(AccountDao.getInstance().exists(user.getUid(), "facebookUserId") == false)
            {
                Account newAccount = new Account();
                newAccount.setFBUser(user);
                AccountDao.getInstance().insertAccount(newAccount);
            }
            result = AccountDao.getInstance().loginWithFacebook(applicationId, user.getUid(), userToken);
        }
        
        return result;
    }
    
    public void logout(String userToken) 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        if(user != null)
        {
            AccountDao.getInstance().logout(user.getUid(), "facebookUserId");
        }
    }
}
