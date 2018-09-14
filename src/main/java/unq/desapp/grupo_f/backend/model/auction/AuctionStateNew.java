package unq.desapp.grupo_f.backend.model.auction;

public class AuctionStateNew implements AuctionState {

	@Override
	public Boolean isNew() {
		return true;
	}

	@Override
	public Boolean isInProgress() {
		return false;
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
