package com.mooseinspace.shipbuilder.client.layouts.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mooseinspace.shipbuilder.client.layouts.PageLayout;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;

public class AdminMakePropLayout implements PageLayout {

	Panel rootPanel;
	
	@Override
	public void showLayout(PlayerInfo playerInfo) 
	{
		showLayout(playerInfo, RootPanel.get());
		
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void showLayout(PlayerInfo playerInfo, Panel rootPanel) 
	{
		this.rootPanel = rootPanel;
		
		rootPanel.clear();
		
		Label lblNewLabel = new Label("Property Name");
		lblNewLabel.setStyleName("label");
		rootPanel.add(lblNewLabel);
		
		final TextBox propNameText = new TextBox();
		propNameText.setStyleName("textBox");
		rootPanel.add(propNameText);
		propNameText.setWidth("400px");
		
		Label lblNewLabel_1 = new Label("Unit Name");
		rootPanel.add(lblNewLabel_1);
		
		final TextBox unitNameText = new TextBox();
		unitNameText.setStyleName("textBox");
		rootPanel.add(unitNameText);
		unitNameText.setWidth("400px");
		
		Label lblNewLabel_2 = new Label("Unit Abv.");
		rootPanel.add(lblNewLabel_2);
		
		final TextBox unitAbrvText = new TextBox();
		unitAbrvText.setStyleName("textBox");
		rootPanel.add(unitAbrvText);
		unitAbrvText.setWidth("400px");
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
				if (propNameText.getText().isEmpty() || unitNameText.getText().isEmpty() || unitAbrvText.getText().isEmpty())
				{
					Window.alert("Fill all the boxes first!");
				}
				else
				{
					uploadProperty(propNameText.getText(), unitNameText.getText(), unitAbrvText.getText());
				}
			}
		});
		
		btnNewButton.setText("Upload");
		btnNewButton.setStyleName("button");
		rootPanel.add(btnNewButton);
		btnNewButton.setSize("93px", "33px");
		
	}
	
	private void uploadProperty(String propName, String propUnit, String propUnitAbv)
	{
		SystemProperty newProp = new SystemProperty();
		newProp.setPropertyName(propName);
		newProp.setUnitName(propUnit);
		newProp.setUnitAbv(propUnitAbv);
		
		AdminServiceAsync adminSvc = GWT.create(AdminService.class);

		adminSvc.addProperty(newProp, AdminLayout.getUploadCallback(rootPanel));
	}

}
