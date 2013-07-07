package com.mooseinspace.shipbuilder.server;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mooseinspace.shipbuilder.client.loaders.GetUserService;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;

public class GetUserServiceImpl extends RemoteServiceServlet implements GetUserService 
{

	@Override
	public PlayerInfo getPlayerInfo()
	{
		UserService userService = UserServiceFactory.getUserService();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		User currentUser = userService.getCurrentUser();

		PlayerInfo playerInfo;
		
		try
		{
			playerInfo = pm.getObjectById(PlayerInfo.class, currentUser.getUserId());
			playerInfo.setLogoutURL(userService.createLogoutURL("http://127.0.0.1:8888/Shipbuilder.html?gwt.codesvr=127.0.0.1:9997"));
		}
		catch (Exception e)
		{
			playerInfo = new PlayerInfo(currentUser.getUserId(), currentUser.getEmail(), currentUser.getNickname());
			playerInfo.setLogoutURL(userService.createLogoutURL("http://127.0.0.1:8888/Shipbuilder.html?gwt.codesvr=127.0.0.1:9997"));
		}
		finally
		{
			pm.close();
		}
		
		return playerInfo;
	}

	@Override
	public String modifyPlayerInfo(PlayerInfo playerInfo)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			pm.makePersistent(playerInfo);
		}
		catch (Exception e)
		{
			return "There was a problem";
		}
		finally
		{
			pm.close();
		}
		
		return "";
	}

}
