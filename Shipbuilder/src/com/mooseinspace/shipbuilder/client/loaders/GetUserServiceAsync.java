package com.mooseinspace.shipbuilder.client.loaders;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;

public interface GetUserServiceAsync
{
	void getPlayerInfo(AsyncCallback<PlayerInfo> callback);
	void modifyPlayerInfo(PlayerInfo playerInfo, AsyncCallback<String> callback);
}
