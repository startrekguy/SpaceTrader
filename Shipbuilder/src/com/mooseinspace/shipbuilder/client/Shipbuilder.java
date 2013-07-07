package com.mooseinspace.shipbuilder.client;

import com.mooseinspace.shipbuilder.client.layouts.NewPlayerLayout;
import com.mooseinspace.shipbuilder.client.layouts.ShipViewLayout;
import com.mooseinspace.shipbuilder.client.layouts.admin.AdminLayout;
import com.mooseinspace.shipbuilder.client.loaders.GetUserService;
import com.mooseinspace.shipbuilder.client.loaders.GetUserServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Shipbuilder implements EntryPoint, ValueChangeHandler<String> {

	private GetUserServiceAsync playerLoadSvc = GWT.create(GetUserService.class);
	PlayerInfo playerInfo;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		History.addValueChangeHandler(this);
		
		// Initialize the service proxy.
	    if (playerLoadSvc == null) {
	      playerLoadSvc = GWT.create(GetUserService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<PlayerInfo> callback = new AsyncCallback<PlayerInfo>() {
	      public void onFailure(Throwable caught)
	      {
	    	  
	      }

	      public void onSuccess(PlayerInfo result) 
	      {
	    	  playerInfo = result;
	    	  
	    	  if (result.getPlayerName().isEmpty())
	    	  {
	    		  History.newItem(NewPlayerLayout.NEWPLAYER_TOKEN);
	    	  }
	    	  else
	    	  {
	    		  	if (History.getToken().isEmpty())
	    		  	{
	    			  History.newItem(ShipViewLayout.SHIPVIEW_TOKEN);
	    	    	}
	    			else
	    			{
	    				changePage(History.getToken());
	    			}
	    	  }
	      }
	    };

	    // Make the call to the stock price service.
	    playerLoadSvc.getPlayerInfo(callback);
		
		
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) 
	{
		changePage(History.getToken());
	}
	
	public void changePage(String token)
	{
		if (token.equals(NewPlayerLayout.NEWPLAYER_TOKEN))
		{
			new NewPlayerLayout().showLayout(playerInfo);
		}
		else if (token.equals(ShipViewLayout.SHIPVIEW_TOKEN))
		{
			new ShipViewLayout().showLayout(playerInfo);
		}
		else if (token.equals(AdminLayout.ADMIN_TOKEN))
		{
			new AdminLayout().showLayout(playerInfo);
		}
	}
}
