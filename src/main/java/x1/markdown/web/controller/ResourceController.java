/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import x1.markdown.security.Account;
import x1.markdown.security.AccountRepository;

/**
 *
 * @author vlad
 */
@RestController
public class ResourceController {
    
    @Autowired
    private AccountRepository users;
    
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/resource")
    public Map<String, Object> home(@AuthenticationPrincipal UserDetails userDetails) {
        Account user = users.findByUsername(userDetails.getUsername());
        Map<String, Object> model = new HashMap<>();
        model.put("roles", user.getRoles());
        return model;
    }
}
