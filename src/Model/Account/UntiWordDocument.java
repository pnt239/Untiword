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
public class UntiWordDocument 
{
    private int _id;
    public int getId()
    {
        return _id;
    }
    
    public void setId(int value)
    {
        _id = value;
    }
    
    private Date _dateCreated;
    public Date getDateCreated()
    {
        return _dateCreated;
    }
    
    public void setDateCreated(Date value)
    {
        _dateCreated = value;
    }
    
    private String _documentId;
    public String getDocumentId()
    {
        return _documentId;
    }
    
    public void setDocuemtnId(String value)
    {
        _documentId = value;
    }
    
    private String _ownerId;
    public String getOwnerId()
    {
        return _ownerId;
    }
    
    public void setOwnerId(String value)
    {
        _ownerId = value;
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
}
