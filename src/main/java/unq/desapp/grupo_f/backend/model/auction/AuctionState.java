package unq.desapp.grupo_f.backend.model.auction;

public interface AuctionState {
	public Boolean isNew();
	public Boolean isInProgress();
	public Boolean isFinished();
}
