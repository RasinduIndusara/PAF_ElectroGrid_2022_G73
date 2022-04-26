package com;

import model.Payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")
public class ServicePayment {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment() {
		return PaymentObj.readPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(
	@FormParam("PAccountNo") String PAccountNo,
	@FormParam("PCustomerName") String PCustomerName,
	@FormParam("PDate") String PDate,
	@FormParam("PMethod") String PMethod,
	@FormParam("PAmount") String PAmount) {
		String output = PaymentObj.insertPayment(PAccountNo, PCustomerName, PDate,  PMethod, PAmount);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePayment(String paymentData) { 
		// Convert the input string to a JSON object
		JsonObject Payment_Object = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object
		String PID = Payment_Object.get("PID").getAsString();
		String PAccountNo = Payment_Object.get("PAccountNo").getAsString();
		String PCustomerName = Payment_Object.get("PCustomerName").getAsString();
		String PDate = Payment_Object.get("PDate").getAsString();
		String PMethod = Payment_Object.get("PMethod").getAsString();
		String PAmount = Payment_Object.get("PAmount").getAsString();
		
		String output = PaymentObj.updatePayment(PID, PAccountNo, PCustomerName, PDate, PMethod, PAmount);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element
		String PID = doc.select("PID").text();
		String output = PaymentObj.deletePayment(PID);
		return output;
	}
}
