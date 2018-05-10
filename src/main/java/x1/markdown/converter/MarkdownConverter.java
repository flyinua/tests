package x1.markdown.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Thread safe
public final class MarkdownConverter {

    private static final Pattern EMPHASIS = Pattern.compile("\\*([^\\*]+)\\*");
    private static final Pattern STRONG_EMPHASIS = Pattern.compile("\\*\\*(.+?)\\*\\*");
    private static final Pattern PARAGRAPH = Pattern.compile("\\n([^#\\n]+)");
    private static final Pattern HEADER = Pattern.compile("(#+)\\s*(.*)");
    private static final Pattern LINK = Pattern.compile("\\[([^\\[]*)\\]\\(([^\\)]+)\\)");

    public static String markdown2Html(String text) throws MarkdownConverterException {
        if (text == null || text.isEmpty()) {
            return text;
        }

        try {
            text = "\n" + text + "\n";

            // strong emphasis
            text = strongEmphasis(text);

            // emphasis
            text = emphasis(text);

            //links
            text = links(text);

            // paragraphs
            text = paragraphs(text);

            // headers
            text = headers(text);

            StringBuilder buffer = new StringBuilder();
            buffer.append("<html>\n").append("<body>\n").append(text.trim()).append("\n</body>\n").append("</html>");
            return buffer.toString();

        } catch (Exception th) {
            throw new MarkdownConverterException(th);
        }
    }

    static String strongEmphasis(String text) {
        return StringReplacer.replace(text, STRONG_EMPHASIS, (Matcher m) -> {
            return "<strong>$1</strong>";
        });
    }

    static String emphasis(String text) {
        return StringReplacer.replace(text, EMPHASIS, (Matcher m) -> {
            return "<em>$1</em>";
        });
    }

    static String unifyEndOfLines(String text) {
        text = text.replaceAll("\\r\\n", "\n");
        text = text.replaceAll("\\r", "\n");
        return text;
    }

    static String paragraphs(String text) {
        return StringReplacer.replace(text, PARAGRAPH, (Matcher m) -> {
            return "\n<p>$1</p>";
        });
    }

    static String headers(String text) {
        return StringReplacer.replace(text, HEADER, (Matcher m) -> {
            String marker = m.group(1);
            String heading = m.group(2);
            int level = marker.length();
            String tag = "h" + level;
            return "<" + tag + ">" + heading + "</" + tag + ">";
        });
    }

    static String links(String text) {
        return StringReplacer.replace(text, LINK, (Matcher m) -> {
            String address = m.group(1);
            String url = m.group(2);
            return "<a href=\"" + url + "\">" + address + "</a>";
        });
    }
}
