package spf.websocket.beans;

import java.io.Serializable;

public class WebUser implements Serializable{
	
	private static final long serialVersionUID = 2410279221815109383L;
	//
	private int id;
	//用户ID
	private String userId;
	private String name;
	private String passwd;
	private String flag;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
