package com.mooseinspace.shipbuilder.client.layouts.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.mooseinspace.shipbuilder.client.loaders.BlobService;
import com.mooseinspace.shipbuilder.client.loaders.BlobServiceAsync;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class FileLoadPopup extends PopupPanel 
{	
	final FormPanel form = new FormPanel();
	final TextBox hullNameText = new TextBox();
	final FileUpload fileUpload = new FileUpload();
	
	public FileLoadPopup()
	{
		super(true);
		
		this.setPopupPosition(50, 50);
		
		final Label warningLbl = new Label("Warning, Danger!");
		warningLbl.setStyleName("warningLabel");
		
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) 
			{
				String results = event.getResults();
				
				warningLbl.setText(results);
			}
		});
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
			
	    VerticalPanel panel = new VerticalPanel();
	    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    panel.setStyleName("popupBack");
	    form.setWidget(panel);
	    panel.setSize("100%", "100%");
	    
		fileUpload.setStyleName("menuBar");
		fileUpload.setName("picFileUpload");
		
		panel.add(fileUpload);
		fileUpload.setWidth("400px");
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
				if (hullNameText.getText().isEmpty() || fileUpload.getFilename().isEmpty())
				{
					Window.alert("Select a File and create a Hull Name");
				}
				else
				{
					sendFile();
				}
			}
		});
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(horizontalPanel);
		
		Label lblNewLabel = new Label("Hull Name    ");
		lblNewLabel.setStyleName("label");
		horizontalPanel.add(lblNewLabel);
		
		hullNameText.setName("hullNameText");
		hullNameText.setStyleName("textBox");
		horizontalPanel.add(hullNameText);
		hullNameText.setWidth("300px");
		btnNewButton.setText("Upload");
		btnNewButton.setStyleName("button");
		panel.add(btnNewButton);
		btnNewButton.setSize("93px", "33px");
		
		panel.add(warningLbl);
			
		this.setWidget(form);
		form.setSize("420px", "135px");
	}
	
	private void sendFile()
	{
		BlobServiceAsync blobService = GWT.create(BlobService.class);
		
		AsyncCallback<String> callback = new AsyncCallback<String>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("Blob didn't work: " + caught.getMessage());
				
			}

			@Override
			public void onSuccess(String result) 
			{
				form.setAction(result);
				
				form.submit();
			}
			
		};
		
		try
		{
			blobService.uploadShipPic(callback);
		}
		catch (Throwable caught)
		{
			Window.alert("Blob upload didn't work: " + caught.getMessage());
		}
	}
}
