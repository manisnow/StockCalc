package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stock {
	
	
	
	/* [
	{
	"id": "784961"
	,"t" : "TCS"
	,"e" : "NSE"
	,"l" : "2,299.80"
	,"l_fix" : "2299.80"
	,"l_cur" : "&#8377;2,299.80"
	,"s": "0"
	,"ltt":"3:48PM GMT+5:30"
	,"lt" : "Nov 25, 3:48PM GMT+5:30"
	,"lt_dts" : "2016-11-25T15:48:48Z"
	,"c" : "+111.35"
	,"c_fix" : "111.35"
	,"cp" : "5.09"
	,"cp_fix" : "5.09"
	,"ccol" : "chg"
	,"pcls_fix" : "2188.45"
	}
	] */

@JsonProperty("id")	
private	String id;
@JsonProperty("t")	 private	String t;
@JsonProperty("e")	 private    String e;
@JsonProperty("l")	private    String l;
@JsonProperty("l_fix")	private    String l_fix;
@JsonProperty("l_cur")	private    String l_cur;
@JsonProperty("s")	private    String s;
@JsonProperty("ltt")	private    String ltt;
@JsonProperty("lt")	private    String lt;
@JsonProperty("lt_dts")	private    String lt_dts;
@JsonProperty("c")	private    String c;
@JsonProperty("c_fix")	private    String c_fix;
@JsonProperty("cp")	private    String cp;
@JsonProperty("cp_fix")	private    String cp_fix;
@JsonProperty("ccol")	private    String ccol;
@JsonProperty("pcls_fix")	private    String pcls_fix;
@JsonProperty("percentage") private long percentage;



public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getT() {
	return t;
}
public void setT(String t) {
	this.t = t;
}
public String getE() {
	return e;
}
public void setE(String e) {
	this.e = e;
}
public String getL() {
	return l;
}
public void setL(String l) {
	this.l = l;
}
public String getL_fix() {
	return l_fix;
}
public void setL_fix(String l_fix) {
	this.l_fix = l_fix;
}
public String getL_cur() {
	return l_cur;
}
public void setL_cur(String l_cur) {
	this.l_cur = l_cur;
}
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public String getLtt() {
	return ltt;
}
public void setLtt(String ltt) {
	this.ltt = ltt;
}
public String getLt() {
	return lt;
}
public void setLt(String lt) {
	this.lt = lt;
}
public String getLt_dts() {
	return lt_dts;
}
public void setLt_dts(String lt_dts) {
	this.lt_dts = lt_dts;
}
public String getC() {
	return c;
}
public void setC(String c) {
	this.c = c;
}
public String getC_fix() {
	return c_fix;
}
public void setC_fix(String c_fix) {
	this.c_fix = c_fix;
}
public String getCp() {
	return cp;
}
public void setCp(String cp) {
	this.cp = cp;
}
public String getCcol() {
	return ccol;
}
public void setCcol(String ccol) {
	this.ccol = ccol;
}
public String getPcls_fix() {
	return pcls_fix;
}
public void setPcls_fix(String pcls_fix) {
	this.pcls_fix = pcls_fix;
}
public String getCp_fix() {
	return cp_fix;
}
public void setCp_fix(String cp_fix) {
	this.cp_fix = cp_fix;
}
public long getPercentage() {
	return percentage;
}
public void setPercentage(long percentage) {
	this.percentage = percentage;
}


@Override
public String toString() {
	return "Stock [id=" + id + ", t=" + t + ", e=" + e + ", l=" + l
			+ ", l_fix=" + l_fix + ", l_cur=" + l_cur + ", s=" + s + ", ltt="
			+ ltt + ", lt=" + lt + ", lt_dts=" + lt_dts + ", c=" + c
			+ ", c_fix=" + c_fix + ", cp=" + cp + ", cp_fix=" + cp_fix
			+ ", ccol=" + ccol + ", pcls_fix=" + pcls_fix + ", percentage="
			+ percentage + "]";
}


	
}


