/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Account;

import java.util.Date;

/**
 *
 * @author Lilium Aikia
 */
public class Account 
{
    private int _id;
    public int getId()
    {
        return _id;
    }
    
    public void setId(int id)
    {
        _id = id;
    }
    
    private Date _dateRegister;
    public Date getDateRegister()
    {
        return _dateRegister;
    }
    
    public void setDateRegister(Date value)
    {
        _dateRegister = value;
    }    
    
    private String _username;
    public String getUsername()
    {
        return _username;
    }
    
    public void setUsername(String value)
    {
        _username = value;
    }
    
    private String _password;
    public String getPassword()
    {
        return _password;
    }
    
    public void setPassword(String value)
    {
        _password = value;
    }
    
    private String _email;
    public String getEmail()
    {
        return _email;
    }
    
    public void setEmail(String value)
    {
        _email = value;
    }
    
    private FacebookUser _fBUser;
    public FacebookUser getFBUser()
    {
        return _fBUser;
    }
    
    public void setFBUser(FacebookUser value)
    {
        _fBUser = value;
    }
    
    public String getFacebookAccessToken()
    {
        if(_fBUser != null)
        {
            return _fBUser.getAccessToken();
        }
        
        return "";
    }
    
    public String getFBUserId()
    {
        if(_fBUser != null)
        {
            return _fBUser.getUid();
        }
        
        return "";
    }
    
    public String getFacebookName()
    {
        if(_fBUser != null)
        {
            return _fBUser.getName();
        }
        
        return "";
    }
    
    public String getFacebookFirstName()
    {
        if(_fBUser != null)
        {
            return _fBUser.getFirstName();
        }
        
        return "";
    }
    
    public String getFacebookLastName()
    {
        if(_fBUser != null)
        {
            return _fBUser.getLastName();
        }
        
        return "";
    }
    
    public String getFacebookEmail()
    {
        if(_fBUser != null)
        {
            return _fBUser.getEmail();
        }
        
        return "";
    }
}
