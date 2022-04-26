package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/grideletro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String PAccountNo, String PCustomerName, String PDate, String PMethod, String PAmount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`PID`,`PAccountNo`,`PCustomerName`,`PDate`,`PMethod`,`PAmount`)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PAccountNo);
			preparedStmt.setString(3, PCustomerName);
			preparedStmt.setString(4, PDate);
			preparedStmt.setString(5, PMethod);
			preparedStmt.setString(6, PAmount);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment ID</th><th>Electricity Account No</th><th>Customer Name</th><th>Date</th><th>Payment Method</th><th>Amount</th></tr>";
			String query = "select * from payment";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PID = Integer.toString(rs.getInt("PID"));
				String PAccountNo = rs.getString("PAccountNo");
				String PCustomerName = rs.getString("PCustomerName");
				String PDate = rs.getString("PDate");
				String PMethod = rs.getString("PMethod");
				String PAmount = rs.getString("PAmount");

				output += "<tr><td>" + PID + "</td>";
				output += "<td>" + PAccountNo + "</td>";
				output += "<td>" + PCustomerName + "</td>";
				output += "<td>" + PDate + "</td>";
				output += "<td>" + PMethod + "</td>";
				output += "<td>" + PAmount + "</td>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String PID, String PAccountNo, String PCustomerName, String PDate, String PMethod, String PAmount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET PAccountNo=?,PCustomerName=?,PDate=?,PMethod=?,PAmount=? WHERE PID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, PAccountNo);
			preparedStmt.setString(2, PCustomerName);
			preparedStmt.setString(3, PDate);
			preparedStmt.setString(4, PMethod);
			preparedStmt.setString(5, PAmount);
			preparedStmt.setInt(6, Integer.parseInt(PID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String PID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where PID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);
 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
