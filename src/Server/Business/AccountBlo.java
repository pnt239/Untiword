/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Business;

/**
 *
 * @author Lilium Aikia
 */
public class AccountBlo 
{
    public static AccountBlo _instance = null;
    public static AccountBlo getInstance()
    {
        if(_instance == null)
        {
            _instance = new AccountBlo();
        }
        
        return _instance;
    }
    
    public boolean exists(String userToken)
    {
        boolean result = false;
        
        return result;
    }
    
}
