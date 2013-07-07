package com.mooseinspace.shipbuilder.client.layouts.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.mooseinspace.shipbuilder.client.layouts.PageLayout;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.client.loaders.BlobService;
import com.mooseinspace.shipbuilder.client.loaders.BlobServiceAsync;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class AdminImgLoadLayout implements PageLayout 
{
	final FormPanel form = new FormPanel();
	final TextBox hullNameText = new TextBox();
	final FileUpload fileUpload = new FileUpload();
	
	@Override
	public void showLayout(PlayerInfo playerInfo) 
	{
		showLayout(playerInfo, RootPanel.get());
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showLayout(PlayerInfo playerInfo, Panel rootPanel)
	{
		rootPanel.clear();
		
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
//	    panel.setStyleName("popupBack");
	    form.setWidget(panel);
	    panel.setSize("100%", "146px");
	    
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
		
		Label lblNewLabel = new Label("Picture Name    ");
		lblNewLabel.setStyleName("label");
		horizontalPanel.add(lblNewLabel);
		
		hullNameText.setName("picNameText");
		hullNameText.setStyleName("textBox");
		horizontalPanel.add(hullNameText);
		hullNameText.setWidth("300px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.add(horizontalPanel_1);
		
		Label lblNewLabel_1 = new Label("Picture Type");
		lblNewLabel_1.setStyleName("label");
		horizontalPanel_1.add(lblNewLabel_1);
		
		final ListBox imgTypeList = new ListBox();
		
		imgTypeList.setStyleName("textBox");
		horizontalPanel_1.add(imgTypeList);
		imgTypeList.setWidth("215px");
		imgTypeList.setVisibleItemCount(1);
		
		final TextBox imgTypeText = new TextBox();
		imgTypeText.setName("imgTypeText");
		imgTypeText.setStyleName("textBox");
		horizontalPanel_1.add(imgTypeText);
		imgTypeText.setWidth("256px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.add(horizontalPanel_2);
		
		Label lblXSizepix = new Label("Width (pix):");
		lblXSizepix.setStyleName("label");
		horizontalPanel_2.add(lblXSizepix);
		
		TextBox xSizeText = new TextBox();
		xSizeText.setName("xSizeText");
		xSizeText.setStyleName("textBox");
		horizontalPanel_2.add(xSizeText);
		
		Label lblYSizepix = new Label("Height (pix):");
		lblYSizepix.setStyleName("label");
		horizontalPanel_2.add(lblYSizepix);
		
		TextBox ySizeText = new TextBox();
		ySizeText.setName("ySizeText");
		ySizeText.setStyleName("textBox");
		horizontalPanel_2.add(ySizeText);
		btnNewButton.setText("Upload");
		btnNewButton.setStyleName("button");
		panel.add(btnNewButton);
		btnNewButton.setSize("93px", "33px");
		
		imgTypeList.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				imgTypeText.setText(imgTypeList.getItemText(imgTypeList.getSelectedIndex()));
			}
		});
		
		panel.add(warningLbl);
		
//		this.setWidget(form);
//		RootPanel.get().add(form);
		rootPanel.add(form);
		form.setHeight("184px");
		
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>()
		{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<String> result) 
			{
				for (String imgType : result)
				{
					imgTypeList.addItem(imgType);
				}
				
			}
			
		};
		
		AdminServiceAsync adminSvc = GWT.create(AdminService.class);
		adminSvc.getAllImageTypes(callback);
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

