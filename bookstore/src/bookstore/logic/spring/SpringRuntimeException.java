package bookstore.logic.spring;

public class SpringRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SpringRuntimeException(Exception e) {
		super(e);
	}

}
