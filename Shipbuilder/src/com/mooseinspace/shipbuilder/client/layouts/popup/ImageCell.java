package com.mooseinspace.shipbuilder.client.layouts.popup;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.mooseinspace.shipbuilder.shared.TraderImage;

public class ImageCell extends AbstractCell<TraderImage> 
{

	interface Templates extends SafeHtmlTemplates
	{
		@SafeHtmlTemplates.Template("<table><tr><td rowspan=\"2\"><img src=\"{0}\"></td><td colspan=\"2\">{1}</td></tr>" +
				"<tr><td>Height: {2}px</td><td>Width: {3}px</td></tr><tr></table>")
		SafeHtml cell(SafeUri image, SafeHtml name, SafeHtml height, SafeHtml width);
	}
	
	private static Templates templates = GWT.create(Templates.class);
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, TraderImage value, SafeHtmlBuilder sb) 
	{
		if (value == null)
		{
			return;
	    }
		
		SafeUri safeImage = UriUtils.fromString(value.getImageURL()+"=s75");
		SafeHtml safeName = SafeHtmlUtils.fromString(value.getImageName());
		SafeHtml safeHeight = SafeHtmlUtils.fromString("" + value.getHeight());
		SafeHtml safeWidth = SafeHtmlUtils.fromString("" + value.getWidth());
		
		SafeHtml rendered = templates.cell(safeImage, safeName, safeHeight, safeWidth);
		sb.append(rendered);
	}

}
