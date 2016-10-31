/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import org.sqlite.date.DateFormatUtils;

/**
 *
 * @author elias
 */
public class Session {
    private User user = null;
    private String code = null;
    private Date createdDate = null;
    private Date deathDate = null;
    private HashMap<String, Object> storage = null;
    
    private DbGuru dbG;
    
    public Session(User user, DbGuru dbG, Date deathDate){
        this.user = user;
        
        SecureRandom random = new SecureRandom();
        this.code = new BigInteger(130, random).toString(32);
        
        this.createdDate = new Date();
        this.deathDate = deathDate;
        
        this.dbG = dbG;
    }
    
    public Session(User user, String code, DbGuru dbG, Date createdDate, Date deathDate, HashMap<String, Object> storage){
        this.user = user;
        this.code = code;
        this.createdDate = createdDate;
        this.deathDate = deathDate;
        this.storage = storage;
        this.dbG = dbG;
    }
    
    public String getUserId() {
        return this.user.getId();
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getFormattedCreationDate(){
        return DateFormatUtils.format(this.createdDate, "yyyy-MM-dd HH:mm:ss");
    }
    
    public String getFormattedDeathDate(){
        return this.deathDate == null ? null : DateFormatUtils.format(this.deathDate, "yyyy-MM-dd HH:mm:ss");
    }
    
    public boolean isValid(){
        if(this.deathDate == null) return true;
        return true;
    }
}
