package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Complaint;

@Path("/Complaint")
public class ServiceComplaint {
	Complaint ComplainObj = new Complaint();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readComplaint() {
		return ComplainObj.readComplaint();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertComplaint(@FormParam("compAccNO") String compAccNO,		
	 @FormParam("compName") String compName,
	 @FormParam("compArea") String compArea,
	 @FormParam("compReason") String compReason,
	 @FormParam("compPhone") String compPhone)
	{
	 String output = ComplainObj.insertComplaint(compAccNO, compName, compArea, compReason, compPhone);
	return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateComplaint(String compData)
	{
	//Convert the input string to a JSON object
	 JsonObject complaint_Object = new JsonParser().parse(compData).getAsJsonObject();
	//Read the values from the JSON object
	 String compID = complaint_Object.get("compID").getAsString();
	 String compAccNO = complaint_Object.get("compAccNO").getAsString();
	 String compName = complaint_Object.get("compName").getAsString();
	 String compArea = complaint_Object.get("compArea").getAsString();
	 String compReason = complaint_Object.get("compReason").getAsString();
	 String compPhone = complaint_Object.get("compPhone").getAsString();
	 String output = ComplainObj.updateComplaint(compID, compAccNO, compName, compArea, compReason, compPhone);
	return output;
	} 
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteComplaint(String compData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(compData, "", Parser.xmlParser());

	//Read the value from the element <>
	 String compID = doc.select("compID").text();
	 String output = ComplainObj.deleteComplaint(compID);
	return output;
	}
	
}
