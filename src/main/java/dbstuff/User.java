/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff;

/**
 *
 * @author elias
 */
public class User {
    private String id = null;
    private String name = null;
    private String pwd = null;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setClearPwd(String cleartxt) {
        this.pwd = Hashing.md5(cleartxt);
    }
    
    public void setHashedPwd(String hashtxt){
        this.pwd = hashtxt;
    }
    
    public boolean correctPwd(String cleartxt) {
        return (Hashing.md5(cleartxt) == null ? this.pwd == null : Hashing.md5(cleartxt).equals(this.pwd)); 
    }
}
