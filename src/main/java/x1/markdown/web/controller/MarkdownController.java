package x1.markdown.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import x1.markdown.converter.MarkdownConverter;
import x1.markdown.converter.MarkdownConverterException;
import x1.markdown.repository.MarkdownDocument;
import x1.markdown.repository.MarkdownRepository;

@RestController
@RequestMapping(value = "/convert")
public class MarkdownController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkdownController.class);
    
    @Autowired
	private MarkdownRepository markdownRepository;
    
    @Secured({"ROLE_USER"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> convert(final @RequestBody String data) {
        LOGGER.info("[convert] - starting...");
        LOGGER.debug("[convert] - data: {}", data);                
        
        try {
            String html = MarkdownConverter.markdown2Html(data);
            return ResponseEntity.ok().body(html);
        } catch (MarkdownConverterException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            MarkdownDocument document = new MarkdownDocument(data);
            markdownRepository.save(document);
            
            LOGGER.info("[convert] - end...");
        }
    }    
}
