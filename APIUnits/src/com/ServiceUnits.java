package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Units;

@Path("/Units")
public class ServiceUnits {
	Units UnitsObj = new Units();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUnit() {
		return UnitsObj.readUnit();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUnit(
	 @FormParam("unit_AccNo") String unit_AccNo,			
	 @FormParam("unit_Date") String unit_Date,
	 @FormParam("unit_Amount") String unit_Amount,
	 @FormParam("preunit_price") String preunit_price,
	 @FormParam("unit_Total") String unit_Total)
	{
	 String output = UnitsObj.insertUnit (unit_AccNo, unit_Date, unit_Amount, preunit_price, unit_Total);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUnit(String unitsData)
	{
	//Convert the input string to a JSON object
	 JsonObject unitsObject = new JsonParser().parse(unitsData).getAsJsonObject();
	//Read the values from the JSON object
	 String unit_ID = unitsObject.get("unit_ID").getAsString();
	 String unit_AccNo = unitsObject.get("unit_AccNo").getAsString();
	 String unit_Date = unitsObject.get("unit_Date").getAsString();
	 String unit_Amount = unitsObject.get("unit_Amount").getAsString();
	 String preunit_price = unitsObject.get("preunit_price").getAsString();
	 String unit_Total = unitsObject.get("unit_Total").getAsString();
	 String output = UnitsObj.updateUnit(unit_ID, unit_AccNo, unit_Date, unit_Amount, preunit_price, unit_Total);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUnit(String unitsData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(unitsData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String unit_ID = doc.select("unit_ID").text();
	 String output = UnitsObj.deleteUnit(unit_ID);
	return output;
	}
}
