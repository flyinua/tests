package x1.markdown.converter;

public class MarkdownConverterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MarkdownConverterException(String message) {
		super(message);
	}

	public MarkdownConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public MarkdownConverterException(Throwable cause) {
		super(cause);
	}
}
