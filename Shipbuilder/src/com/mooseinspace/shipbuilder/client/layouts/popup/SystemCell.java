package com.mooseinspace.shipbuilder.client.layouts.popup;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

public class SystemCell extends AbstractCell<ShipSystem> {

	interface Templates extends SafeHtmlTemplates
	{
		@SafeHtmlTemplates.Template("<table class=\"hullcell\"><tr><td rowspan=\"3\"><img src=\"{0}\"></td>" +
				"<td class=\"smallTitle\">{1}</td></tr>" +
				"<tr><td>Mass: {2} Tons</td></tr>" +
				"<tr><td>Length: {3}m</td></tr></table>")
		SafeHtml cell(SafeUri image, SafeHtml name, SafeHtml mass, SafeHtml length);
	}
	
	private static Templates templates = GWT.create(Templates.class);
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, ShipSystem value, SafeHtmlBuilder sb) 
	{
		if (value == null)
		{
			return;
	    }
		
		value.setObjConfig(ShipSystem.LARGE_INNER_GRID_CONFIG);
		
		SafeUri safeImage = UriUtils.fromString(value.getImageURL()+"=s75");
		SafeHtml safeName = SafeHtmlUtils.fromString(value.getSystemName());
		SafeHtml safeLength = SafeHtmlUtils.fromString("" + value.getObjLength());
		SafeHtml safeMass = SafeHtmlUtils.fromString("" + value.getObjectMass());

		SafeHtml rendered = templates.cell(safeImage, safeName, safeMass, safeLength);
		sb.append(rendered);
	}

}

