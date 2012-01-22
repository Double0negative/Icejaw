package runtime.main;

import runtime.net.Site;

public class Profile {

	
	String pass = "";
	String username = "";
	String url = "";
	String title = "";
	int index = 0;
	boolean loggedin = false;
	boolean abs = false;
	
	
	Profile(String url, boolean abs, String username, String pass,boolean needPass, boolean active, String title ){
		site = new Site(url, username, pass);
		this.abs = abs;
		this.url = url;
		this.username = username;
		this.pass = pass;
		this.index= index;
		this.active = active;
		this.title = title;
		this.needPass = needPass;
	}
	
	
	public boolean isLoggedin() {
		return loggedin;
	}

	public boolean getabs(){
		return  abs;
	}

	public void setabs(boolean abs){
		this.abs = abs;
	}
	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}




	boolean active = true;
	public boolean isNeedPass() {
		return needPass;
	}




	public void setNeedPass(boolean needPass) {
		this.needPass = needPass;
	}




	boolean needPass = false;
	Site site = null;

	
	
	





	public String getPass() {
		return pass;
	}




	public void setPass(String pass) {
		this.pass = pass;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getUrl() {
		return url;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public int getIndex() {
		return index;
	}




	public void setIndex(int index) {
		this.index = index;
	}




	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}




	public Site getSite() {
		return site;
	}




	public void setSite(Site site) {
		this.site = site;
	}
	
	
	

}
	
	
	
	
	

