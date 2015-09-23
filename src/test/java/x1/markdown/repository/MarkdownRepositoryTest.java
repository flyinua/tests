/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import x1.markdown.WebAppTest;

/**
 *
 * @author vlad
 */
public class MarkdownRepositoryTest extends WebAppTest {
    
    @Autowired
    private MarkdownRepository repository;
    
    @Before
    public void setUp() {        
    }
    
    @Test
    public void testRepository() {
        //TODO
    }
}
