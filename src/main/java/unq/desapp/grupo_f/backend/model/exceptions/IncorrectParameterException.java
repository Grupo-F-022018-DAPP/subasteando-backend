package unq.desapp.grupo_f.backend.model.exceptions;

public class IncorrectParameterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectParameterException(String errorMessage) {
		super(errorMessage);
	}
}
