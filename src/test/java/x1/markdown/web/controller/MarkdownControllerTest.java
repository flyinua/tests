/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import x1.markdown.repository.MarkdownDocument;
import x1.markdown.repository.MarkdownRepository;

/**
 *
 * @author vlad
 */
public class MarkdownControllerTest {
        
    private MockMvc mockMvc;
    
    private String url = "/convert";
    
    @Mock
    MarkdownRepository markdownRepository;
    
    @InjectMocks
    MarkdownController markdownController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(markdownController).build();
    }
    
    @Test
    public void testConvert() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post(url).
                content("**text**".getBytes()))
                .andExpect(status().isOk());
        verify(markdownRepository, times(1)).save(any(MarkdownDocument.class));
        
        this.mockMvc.perform(
                MockMvcRequestBuilders.post(url).
                content(""))
                .andExpect(status().isBadRequest());
    }
}
