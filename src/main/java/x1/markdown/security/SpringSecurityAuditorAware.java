/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.security;

import org.springframework.data.domain.AuditorAware;

/**
 *
 * @author vlad
 */
public class SpringSecurityAuditorAware implements AuditorAware<String>{
    
    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentLogin();
        return (userName != null ? userName : "Unknown");
    }
}
