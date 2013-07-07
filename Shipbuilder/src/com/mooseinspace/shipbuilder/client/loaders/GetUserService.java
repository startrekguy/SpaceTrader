package com.mooseinspace.shipbuilder.client.loaders;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;


@RemoteServiceRelativePath("getPlayerInfo")
public interface GetUserService extends RemoteService
{
	PlayerInfo getPlayerInfo();
	String modifyPlayerInfo(PlayerInfo playerInfo);
}
