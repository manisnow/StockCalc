package hello;

public class UserStock extends User {
	
	
	private long invAmt;
	private String investSchdAlertType;
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
	public String getInvestSchdAlertType() {
		return investSchdAlertType;
	}
	public void setInvestSchdAlertType(String investSchdAlertType) {
		this.investSchdAlertType = investSchdAlertType;
	}
	


	
	
}
