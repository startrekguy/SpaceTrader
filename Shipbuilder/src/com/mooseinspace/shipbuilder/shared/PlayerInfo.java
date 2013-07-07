package com.mooseinspace.shipbuilder.shared;

import java.io.Serializable;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class PlayerInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5113521003749053693L;

	@PrimaryKey
	private String userID;
	
	@Persistent
	private String playerName;

	@Persistent
	private String email;
	
	@Persistent
	private String nickname;
	
	@NotPersistent
	private String logoutURL;
	
	//@Persistent
	//private User userInfo;

	public PlayerInfo()
	{
		this.email = "";
		this.nickname = "";
		this.userID = "";
		this.playerName = "";
	}
	
	public PlayerInfo(String userID, String email, String nickname)
	{
		this.email = email;
		this.nickname = nickname;
		this.userID = userID;
		this.playerName = "";
	}
	
	public PlayerInfo(String userID, String email, String nickname, String playerName)
	{
		this.email = email;
		this.nickname = nickname;
		this.userID = userID;
		this.playerName = playerName;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * @return the logoutURL
	 */
	public String getLogoutURL() {
		return logoutURL;
	}

	/**
	 * @param logoutURL the logoutURL to set
	 */
	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}
	
}
