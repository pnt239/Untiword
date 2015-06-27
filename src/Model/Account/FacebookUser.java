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
        return _user.getId();
    }
    
    public String getName()
    {
        return _user.getName();
    }
    
    public String getEmail()
    {
        return _user.getEmail();
    }
    
    public String getFirstName()
    {
        return _user.getFirstName();
    }
    
    public String getLastName()
    {
        return _user.getLastName();
    }    
    
    public FacebookUser(User user, String accessToken)
    {
        _user = user;
        _accessToken = accessToken;
    }
}
