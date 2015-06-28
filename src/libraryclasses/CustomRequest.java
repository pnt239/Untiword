/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryclasses;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Lilium Aikia
 */
public class CustomRequest 
{
    private boolean _customRequest;

    public CustomRequest(int initialCapacity) 
    {
        _customRequest = true;
        _action = "";
        if(initialCapacity > 0)
        {
            _arguments = new HashMap<>(initialCapacity);
        }
        else
        {
            _arguments = new HashMap<>();
        }
    }
    public boolean isCustomRequest()
    {
        return _customRequest;
    }
    
    private String _action;
    public String getAction()
    {
        return _action;
    }
    
    public void setAction(String value)
    {
        _action = value;
    }
    
    private HashMap<String, String> _arguments;
    public HashMap<String, String> getArguments()
    {
        return _arguments;
    }
    
    public CustomRequest(String request)
    {
        if(request != null)
        {
            try
            {
                if(request.startsWith("CUSTOM_REQUEST"))
                {
                    this._customRequest = true;
                    String[] parts = request.split("[?]");
                    if(parts.length == 2)
                    {
                        String[] parts2 = parts[0].split("[/]");
                        if(parts2.length == 2)
                        {
                            _action = parts2[1];
                        }
                        else
                        {
                            throw new Exception("Invalid / format");
                        }
                        
                        parts = parts[1].split("[&]");
                        _arguments = new HashMap<>(parts.length);
                        
                        for(int i=0; i<parts.length; i++)
                        {
                            parts2 = parts[i].split("[=]");
                            if(parts2.length == 2)
                            {
                                _arguments.put(parts2[0], parts2[1]);
                            }
                            else
                            {
                                throw new Exception("Invalid = format");
                            }
                        }
                    }
                    else
                    {
                        throw new Exception("Invalid ? format");
                    }
                }
                else
                {
                    this._customRequest = false;
                    throw new Exception("Is not a custom request");
                }
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }
    
    public String getValue(String argumentName)
    {
        return _arguments.get(argumentName);
    }
    
    public void setValue(String argumentName, String argumentValue)
    {
        _arguments.put(argumentName, argumentValue);
    }
    
    public String toString()
    {
        String result = "";
        if(_customRequest)
        {
            result = "CUSTOM_REQUEST/" + _action + "?";
            String[] keys = _arguments.keySet().toArray(new String[_arguments.keySet().size()]);
            for (int i=0; i<keys.length-1; i++) 
            {
                result += keys[i] + "=" + _arguments.get(keys[i]) + "&";
            }
            result += keys[keys.length-1] + "=" + _arguments.get(keys[keys.length-1]);
        }
        
        return result;
    }
}
