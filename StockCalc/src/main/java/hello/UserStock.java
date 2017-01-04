package hello;

public class UserStock extends User {
	
	
	private long invAmt;
	private Stock[] stocks;
	
	
	public long getInvAmt() {
		return invAmt;
	}
	public void setInvAmt(long invAmt) {
		this.invAmt = invAmt;
	}
	public Stock[] getStocks() {
		return stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}

	
	
}
