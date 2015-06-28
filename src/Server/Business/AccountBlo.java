/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Business;

import Controller.AccountController.FacebookController;
import Model.Account.Account;
import Model.Account.FacebookUser;
import Server.Dao.AccountDao;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Lilium Aikia
 */
public class AccountBlo 
{
    private static AccountBlo _instance = null;
    public static AccountBlo getInstance() throws ParserConfigurationException, SAXException, IOException, TransformerException
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
    
    public boolean exists(String userToken) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException
    {
        boolean result = false;
        
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        
        if(user != null)
        {
            if(AccountDao.getInstance().exists(user.getUid()))
            {
                result = true;
            }
        }
        
        return result;
    }
    
    public Account getUser(String fBUserId) throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        return AccountDao.getInstance().getUser(fBUserId);
    }
    
    public Account[] getUsers() throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        return AccountDao.getInstance().getUsers();
    }
    
    public Account[] getLoginUsers() throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        return AccountBlo.getInstance().getLoginUsers();
    }
    
    public String login(String userToken) throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        String result = "";
        
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        
        if(user != null)
        {
            if(AccountDao.getInstance().exists(user.getUid()) == false)
            {
                Account newAccount = new Account();
                newAccount.setFBUser(user);
                AccountDao.getInstance().insertAccount(newAccount);
            }
            result = AccountDao.getInstance().login(user.getUid(), userToken);
        }
        
        return result;
    }
    
    public void logout(String userToken) throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException
    {
        FacebookController fc = FacebookController.getInstance();
        FacebookUser user = fc.getUser(userToken);
        if(user != null)
        {
            AccountDao.getInstance().logout(user.getUid());
        }
    }
}
