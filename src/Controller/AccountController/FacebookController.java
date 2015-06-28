/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.AccountController;

import Model.Account.FacebookUser;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.User;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lilium Aikia
 */
public class FacebookController 
{
    private static FacebookController _instance = null;
    public static FacebookController getInstance()
    {
        if(_instance == null)
        {
            _instance = new FacebookController();
        }
        
        return _instance;
    }
    
    private String _appId;
    private String _appSecret;
    private String _accessToken;
    private String _redirectUri;
    private FacebookClient _facebookClient;
    
    private FacebookController()
    {       
        _appId = "1446350922337661";
        _appSecret = "bfa56d720b408d00edc8c282381992b6";
        _accessToken = "1446350922337661|JwR5oP03kxCMlauNTanKkElVWDM";
        _redirectUri = "http://localhost";
        _facebookClient = new DefaultFacebookClient("1446350922337661|JwR5oP03kxCMlauNTanKkElVWDM");
    }
    
    public String getFbUserAccessTokenUrl()
    {
        String result = null;
       
        try {
            result = "https://www.facebook.com/v2.3/dialog/oauth?response_type=token&display=popup"
                    + "&client_id=" + _appId
                    + "&redirect_uri=" + URLEncoder.encode(_redirectUri, "UTF-8")
                    + "&scope=" + URLEncoder.encode("user_friends,email", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FacebookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String parseUserAccessTokenUrl(String url)
    {
        String result = null;
        
        String[] parts = url.split("#access_token=");
        if(parts.length == 2)
        {
            if(parts[0].equals("http://localhost/?"))
            {
                parts = parts[1].split("&expires_in=");
                if(parts.length == 2)
                {
                    result = parts[0];
                }
            }       
        }
        
        return result;
    }
    
    public String getFBAuthUrl() 
    {
        String fbLoginUrl = "";
        try 
        {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" 
                    + "client_id=" + _appId 
                    + "&redirect_uri=" + URLEncoder.encode(_redirectUri, "UTF-8") 
                    + "&scope=user_friends%2Cemail";
        } 
        catch (UnsupportedEncodingException e) 
        {
        }
        return fbLoginUrl;
    }
    
    public String getFBGraphUrl(String code) 
    {
        String fbGraphUrl = "";
        try 
        {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" 
                    + "client_id=" + _appId 
                    + "&redirect_uri=" + URLEncoder.encode(_redirectUri, "UTF-8")
                    + "&client_secret=" + _appSecret 
                    + "&code=" + code;
        } 
        catch (UnsupportedEncodingException e) 
        {
        }
        return fbGraphUrl;
    }
    
    public String getAccessToken(String code) 
    {
        if ("".equals(_accessToken)) 
        {
            URL fbGraphURL;
            try 
            {
                fbGraphURL = new URL(getFBGraphUrl(code));
            } 
            catch (MalformedURLException e) 
            {
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try 
            {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null) 
                    b.append(inputLine).append("\n");
                in.close();
            } 
            catch (IOException e) 
            {
                throw new RuntimeException("Unable to connect with Facebook " + e);
            }

            _accessToken = b.toString();
            if (_accessToken.startsWith("{")) 
            {
                throw new RuntimeException("ERROR: Access Token Invalid: " + _accessToken);
            }
        }
        return _accessToken;
    }
    
    public String getFBGraph(String userAccessToken) 
    {
        String graph = null;
        try 
        {
            String g = "https://graph.facebook.com/me?" + userAccessToken;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            StringBuffer b;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()))) 
            {
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null) 
                    b.append(inputLine).append("\n");
            }
            graph = b.toString();
            System.out.println(graph);
        } 
        catch (Exception e) 
        {
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }
    
    public Map<String, String> getGraphData(String fbGraph) 
    {
        Map<String, String> fbProfile = new HashMap<>();
        try 
        {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email"))
                fbProfile.put("email", json.getString("email"));
            if (json.has("gender"))
                fbProfile.put("gender", json.getString("gender"));
        }
        catch (JSONException e)
        {
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
    
    public String getLoginDialogUrlString()
    {
        String result;
        
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(UserDataPermissions.USER_FRIENDS);
        
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_3);
        result = client.getLoginDialogUrl(_appId, _redirectUri, scopeBuilder);	
        result += "%2Cemail";
        
        return result;
    }
    
    public FacebookUser getUser(String accessToken)
    {
        FacebookUser result = null;
        
        try
        {
            FacebookClient fc = new DefaultFacebookClient(accessToken);
            result = new FacebookUser(fc.fetchObject("me", User.class), accessToken);
        }
        catch(Exception e)
        {
            
        }      
        
        return result;
    }
    
    public List<User> getUserFriends(FacebookUser user)
    {
        List<User> result = null;
        
        try
        {
            FacebookClient fc = new DefaultFacebookClient(user.getAccessToken());
            result = fc.fetchConnection("me/friends", User.class).getData();        
        }
        catch(Exception e)
        {
            out.print(e.toString());
        }
        
        return result;
    }
}
