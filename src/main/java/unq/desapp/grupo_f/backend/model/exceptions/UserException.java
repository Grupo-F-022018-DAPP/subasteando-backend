package unq.desapp.grupo_f.backend.model.exceptions;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserException(String errorMessage) {
		super(errorMessage);
	}
}
