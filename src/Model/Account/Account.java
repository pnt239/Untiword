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
    
    private FacebookUser _fBUser;
    public FacebookUser getFBUser()
    {
        return _fBUser;
    }
    
    public void setFBUser(FacebookUser value)
    {
        _fBUser = value;
    }
    
    private String _ip;
    
    public String getIp()
    {
        return _ip;
    }
    
    public void setIp(String value)
    {
        _ip = value;
    }
    
    private String _name;
    
    public String getName()
    {
        return _name;
    }
    
    public void setName(String value)
    {
        _name = value;
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
    
    private String _email;
    public String getEmail()
    {
        return _email;
    }
    
    public void setEmail(String value)
    {
        _email = value;
    }
    
    private String _fBUserId;   
    public String getFBUserId()
    {
        return _fBUserId;
    }
    
    public void setFBUserId(String value)
    {
        _fBUserId = value;
    }
}
