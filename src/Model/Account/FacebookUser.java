/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Account;

import com.restfb.types.User;

/**
 *
 * @author Lilium Aikia
 */
public class FacebookUser 
{
    private User _user;
    private String _accessToken;
    
    public User getUser()
    {
        return _user;
    }
    
    public String getAccessToken()
    {
        return _accessToken;
    }
    
    public String getUid()
    {
        if(_user != null)
        {
            return _user.getId();
        }
        
        return "";
    }
    
    public String getName()
    {
        if(_user != null)
        {
            return _user.getName();
        }
        
        return "";
    }
    
    public String getEmail()
    {
        if(_user != null)
        {
            return _user.getEmail();
        }
        
        return "";
    }
    
    public String getFirstName()
    {
        if(_user != null)
        {
            return _user.getFirstName();
        }
        
        return "";
    }
    
    public String getLastName()
    {
        if(_user != null)
        {
            return "";
        }
        
        return _user.getId();
    }    
    
    public String getPicture()
    {
        if(_user != null)
        {
            return _user.getPicture().getUrl();
        }
        
        return "";
    }
    
    public FacebookUser(User user, String accessToken)
    {
        _user = user;
        _accessToken = accessToken;
    }
}
