/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
/**
 *
 * @author vlad
 */

public class Account {

    @Id
    private String id;
    private String username;
    private String password;
    private List<GrantedAuthority> roles;

    public Account() {
    }

    public Account(String username, String password, List<GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>(roles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRoles() {
        return new ArrayList<>(roles);
    }

    public void setRoles(List<GrantedAuthority> roles) {
        this.roles = new ArrayList<>(roles);
    }        
}
