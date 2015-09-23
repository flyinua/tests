package x1.markdown.converter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MarkdownConverterTest {

	@Test
	public void testStrongEmphasis() {
		assertEquals("<strong>lorem</strong>", MarkdownConverter.strongEmphasis("**lorem**"));
		assertEquals("<strong>lorem</strong> <strong>lorem</strong>", MarkdownConverter.strongEmphasis("**lorem** **lorem**"));
		assertEquals("<strong>lorem</strong><strong>lorem</strong>", MarkdownConverter.strongEmphasis("**lorem****lorem**"));
		assertEquals("<strong>l</strong>", MarkdownConverter.strongEmphasis("**l**"));
		assertEquals("<strong>?</strong>", MarkdownConverter.strongEmphasis("**?**"));
		assertEquals("****", MarkdownConverter.strongEmphasis("****"));

		assertNotEquals("<strong>lorem</strong>", MarkdownConverter.strongEmphasis("*lorem*"));
	}

	@Test
	public void testEmphasis() {
		assertEquals("<em>lorem</em>", MarkdownConverter.emphasis("*lorem*"));
		assertEquals("<em>lorem</em> <em>lorem</em>", MarkdownConverter.emphasis("*lorem* *lorem*"));
		assertEquals("<em>lorem</em><em>lorem</em>", MarkdownConverter.emphasis("*lorem**lorem*"));
		assertEquals("<em>l</em>", MarkdownConverter.emphasis("*l*"));
		assertEquals("<em>?</em>", MarkdownConverter.emphasis("*?*"));
		assertEquals("**", MarkdownConverter.emphasis("**"));

		assertNotEquals("<em>lorem</em>", MarkdownConverter.emphasis("**lorem**"));
	}

	@Test
	public void testUnifyEndOfLines() {
		assertEquals("\n", MarkdownConverter.unifyEndOfLines("\r\n"));
		assertEquals("\n", MarkdownConverter.unifyEndOfLines("\r"));
		assertEquals("\n", MarkdownConverter.unifyEndOfLines("\n"));
		assertEquals("plain_text", MarkdownConverter.unifyEndOfLines("plain_text"));
		assertEquals("plain_text\n", MarkdownConverter.unifyEndOfLines("plain_text\r\n"));
		assertEquals("plain_text\n", MarkdownConverter.unifyEndOfLines("plain_text\r"));
		assertEquals("plain_text\nplaint_text", MarkdownConverter.unifyEndOfLines("plain_text\r\nplaint_text"));
		assertEquals("plain_text\nplaint_text", MarkdownConverter.unifyEndOfLines("plain_text\rplaint_text"));
		assertEquals("plain_text\nplaint_text", MarkdownConverter.unifyEndOfLines("plain_text\nplaint_text"));
	}

	@Test
	public void testHeaders() {
		assertEquals("plain text", MarkdownConverter.headers("plain text"));
		assertEquals("<h1>plain text</h1>", MarkdownConverter.headers("# plain text"));
		assertEquals("\n<h1>plain text</h1>\n", MarkdownConverter.headers("\n# plain text\n"));
		assertEquals("<h1>plain text</h1>", MarkdownConverter.headers("#plain text"));
		assertEquals("<h1>plain text # plain text</h1>", MarkdownConverter.headers("# plain text # plain text"));
		assertEquals("<h1>plain text</h1>\n<h2>plain text</h2>", MarkdownConverter.headers("# plain text\n## plain text"));
	}

	@Test
	public void testParagraphs() {
		assertEquals("plain text", MarkdownConverter.paragraphs("plain text"));
		assertEquals("\n<p>plain text</p>", MarkdownConverter.paragraphs("\nplain text"));
		assertEquals("plain text\n", MarkdownConverter.paragraphs("plain text\n"));
		assertEquals("\n<p>plain text</p>\n", MarkdownConverter.paragraphs("\nplain text\n"));
	}

	@Test
	public void testLinks() {
		assertEquals("<a href=\"http://mysite.com\">diam</a>", MarkdownConverter.links("[diam](http://mysite.com)"));
		assertEquals("<a href=\"http://mysite.com\"></a>", MarkdownConverter.links("[](http://mysite.com)"));
	}

	@Test
	public void markdown2Html() {
		String text = "# Loremipsum\n"
				+ "Dolor sit amet,\n"
				+ "consetetur *sadipscing* elitr,\n"
				+ "sed [diam](http://mysite.com) nonumy eirmod tempor";
		assertEquals("<html>\n<body>\n<h1>Loremipsum</h1>\n" +
				"<p>Dolor sit amet,</p>\n" +
				"<p>consetetur <em>sadipscing</em> elitr,</p>\n" +
				"<p>sed <a href=\"http://mysite.com\">diam</a> nonumy eirmod tempor</p>\n</body>\n</html>", MarkdownConverter.markdown2Html(text));
	}
}
