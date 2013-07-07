package com.mooseinspace.shipbuilder.client.layouts;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.mooseinspace.shipbuilder.client.loaders.GetUserService;
import com.mooseinspace.shipbuilder.client.loaders.GetUserServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;

public class NewPlayerLayout implements PageLayout {

	public static final String NEWPLAYER_TOKEN = "newplayer";
	
	PlayerInfo playerInfo;
	
	TextBox playerNameText;
	
	@Override
	public void showLayout(PlayerInfo playerInfo) 
	{
		showLayout(playerInfo, RootPanel.get());
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void showLayout(final PlayerInfo playerInfo, Panel rootPanel) 
	{
		this.playerInfo = playerInfo;
		
//		RootPanel.get().clear();
		
		//***********************
		//****  Load Layout *****
		//***********************
//		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		rootPanel.setStyleName("blackBack");
		rootPanel.setSize("100%", "100%");
		
		VerticalPanel flowPanel = new VerticalPanel();
		flowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flowPanel.setStyleName("blackBack");
		rootPanel.add(flowPanel);
		flowPanel.setSize("100%", "");
		
		Label label = new Label("Welcome To Shipbuilder");
		flowPanel.add(label);
		label.setStyleName("title");
		
		LayoutPanel layoutPanel = new LayoutPanel();
		layoutPanel.setStyleName("centerBox");
		flowPanel.add(layoutPanel);
		layoutPanel.setSize("750px", "750px");
		
		Label lblNewLabel = new Label("Player Name:");
		lblNewLabel.setStyleName("label");
		layoutPanel.add(lblNewLabel);
		lblNewLabel.setSize("100px", "16px");
		layoutPanel.setWidgetLeftWidth(lblNewLabel, 47.0, Unit.PX, 96.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblNewLabel, 93.0, Unit.PX, 16.0, Unit.PX);
		
		playerNameText = new TextBox();
		playerNameText.setStyleName("textBox");
		layoutPanel.add(playerNameText);
		playerNameText.setSize("300px", "16px");
		layoutPanel.setWidgetLeftWidth(playerNameText, 153.0, Unit.PX, 325.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(playerNameText, 87.0, Unit.PX, 32.0, Unit.PX);
		
		Button submitButton = new Button("Create Player");
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				submitNewPlayer();
				
			}
		});
		submitButton.setStyleName("button");
		layoutPanel.add(submitButton);
		layoutPanel.setWidgetLeftWidth(submitButton, 47.0, Unit.PX, 255.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(submitButton, 144.0, Unit.PX, 40.0, Unit.PX);
		
		Label nicknameLabel = new Label(playerInfo.getNickname());
		nicknameLabel.setStyleName("label");
		layoutPanel.add(nicknameLabel);
		layoutPanel.setWidgetLeftWidth(nicknameLabel, 20.0, Unit.PX, 270.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(nicknameLabel, 20.0, Unit.PX, 16.0, Unit.PX);
		
//		HTML logoutButton = new HTML("<a href=\"" + playerInfo.getLogoutURL() + "\">Log Out</a>", true);
		Button logoutButton = new Button("Log Out");
		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				Window.Location.assign(playerInfo.getLogoutURL());
			}
		});
		logoutButton.setStyleName("button");
		layoutPanel.add(logoutButton);
		layoutPanel.setWidgetLeftWidth(logoutButton, 682.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(logoutButton, 16.0, Unit.PX, 28.0, Unit.PX);

	}
	
	private void submitNewPlayer()
	{
		playerInfo.setPlayerName(playerNameText.getText());
		
		// Initialize the service proxy.
	    GetUserServiceAsync playerLoadSvc = GWT.create(GetUserService.class);

	    // Set up the callback object.
	    AsyncCallback<String> callback = new AsyncCallback<String>() 
	    {
	    	public void onFailure(Throwable caught)
	    	{
	    		Window.alert("It didn't work: " + caught.getMessage());
	    	}


			@Override
			public void onSuccess(String result) 
			{
				if (result.equals("There was a problem"))
				{
					Window.alert("Player name was not saved!");
				}
				else
				{
					History.newItem(ShipViewLayout.SHIPVIEW_TOKEN);
				}
				
			}
	    };

	    // Make the call to the stock price service.
	    playerLoadSvc.modifyPlayerInfo(playerInfo, callback);
	}
}
