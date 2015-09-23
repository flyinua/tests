/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.security;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author vlad
 */
public interface AccountRepository extends MongoRepository<Account, String> {
    public Account findByUsername(String username);
}
