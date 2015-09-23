package x1.markdown.converter;

import java.util.regex.Matcher;

@FunctionalInterface
interface StringReplacerCallback {
	public String replace(Matcher match);
}
