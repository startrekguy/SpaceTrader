package com.mooseinspace.shipbuilder.client.layouts;

import com.google.gwt.user.client.ui.Panel;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;

public interface PageLayout 
{
	public void showLayout(final PlayerInfo playerInfo);
	public void showLayout(final PlayerInfo playerInfo, Panel rootPanel);
}
