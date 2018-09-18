package unq.desapp.grupo_f.backend.model.exceptions;

public class AuctionStateException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuctionStateException(String errorMessage) {
		super(errorMessage);
	}
}
