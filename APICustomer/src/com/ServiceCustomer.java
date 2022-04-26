package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Customer;

@Path("/Customer")
public class ServiceCustomer {
	Customer CustomerObj = new Customer();

	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomer() {
		return CustomerObj.readCustomer();
	}
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("firstName") String firstName,			
	 @FormParam("lastName") String lastName,
	 @FormParam("address") String address,
	 @FormParam("nic") String nic,
	 @FormParam("email") String email,
	 @FormParam("phoneNo") String phoneNo)
	{
	 String output = CustomerObj.insertCustomer(firstName, lastName, address, nic, email, phoneNo);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData)
	{
	//Convert the input string to a JSON object
	 JsonObject customer_Object = new JsonParser().parse(customerData).getAsJsonObject();
	//Read the values from the JSON object
	 String cID = customer_Object.get("cID").getAsString();
	 String firstName = customer_Object.get("firstName").getAsString();
	 String lastName = customer_Object.get("lastName").getAsString();
	 String address = customer_Object.get("address").getAsString();
	 String nic = customer_Object.get("nic").getAsString();
	 String email = customer_Object.get("email").getAsString();
	 String phoneNo = customer_Object.get("phoneNo").getAsString();
	 String output = CustomerObj.updateCustomer(cID, firstName, lastName, address, nic, email, phoneNo);
	return output;
	} 
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(customerData, "", Parser.xmlParser()); 
	//Read the value from the element <ID>
	 String cID = doc.select("cID").text();
	 String output = CustomerObj.deleteCustomer(cID);
	return output;
	}
	
}
