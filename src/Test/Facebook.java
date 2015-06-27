/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Controller.AccountController.FacebookController;
import java.io.IOException;
import static java.lang.System.out;

/**
 *
 * @author Untitled25364
 */
public class Facebook 
{
    public static void main(String[] args) throws IOException
    {
        FacebookController fc = new FacebookController();
//        String graph = fc.getFBGraph("");
//        Map<String, String> fbProfileData = fc.getGraphData(graph);
//        out.println("<div>Welcome "+fbProfileData.get("first_name"));
//        out.println("<div>Your Email: "+fbProfileData.get("email"));
//        out.println("<div>You are "+fbProfileData.get("gender"));		
        out.print(fc.getLoginDialogUrlString());
    }
}
