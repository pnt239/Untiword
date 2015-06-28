/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Account;

import libraryclasses.CustomRequest;

/**
 *
 * @author Lilium Aikia
 */
public class AccountLoginViewModel 
{
    private String _applicationUserId;
    public String getApplicationUserId()
    {
        return _applicationUserId;
    }
    
    public void setApplicationUserId(String value)
    {
        _applicationUserId = value;
    }
    
    private String _loginType;
    public String getLoginType()
    {
        return _loginType;
    }
    
    public void setLoginType(String value)
    {
        _loginType = value;
    }
    
    private String _accessToken;
    public String getAccessToken()
    {
        return _accessToken;
    }
    
    public void setAccessToken(String value)
    {
        _accessToken = value;
    }
    
    public AccountLoginViewModel()
    {
        
    }
    
    public AccountLoginViewModel(String loginMessage)
    {
        try
        {          
            CustomRequest request = new CustomRequest(loginMessage);
            if(request.isCustomRequest() 
                    && request.getAction().equals("AUTHORIZE"))
            {
                _applicationUserId = request.getValue("applicationId");
                _loginType = request.getValue("loginType");
                _accessToken = request.getValue("accessToken");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
