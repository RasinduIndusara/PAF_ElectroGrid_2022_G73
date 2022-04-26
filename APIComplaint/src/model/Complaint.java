package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Complaint {

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

	
	public String insertComplaint(String compAccNO, String compName, String compArea, String compReason, String compPhone) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into complaint(`compID`,`compAccNO`,`compName`,`compArea`,`compReason`,`compPhone`)" 
			+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, compAccNO);
			preparedStmt.setString(3, compName);
			preparedStmt.setString(4, compArea);
			preparedStmt.setString(5, compReason);
			preparedStmt.setString(6, compPhone);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the complaint.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readComplaint() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Complaint ID</th><th>Account No</th><th>Customer Name</th><th>Area</th><th>Reason</th><th>Phone Number</th></tr>";
			String query = "select * from complaint";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String compID = Integer.toString(rs.getInt("compID"));
				String compAccNO = rs.getString("compAccNO");
				String compName = rs.getString("compName");
				String compArea = rs.getString("compArea");
				String compReason = rs.getString("compReason");
				String compPhone = rs.getString("compPhone");

				// Add into the html table
				output += "<tr><td>" + compID + "</td>";
				output += "<td>" + compAccNO + "</td>";
				output += "<td>" + compName + "</td>";
				output += "<td>" + compArea + "</td>";
				output += "<td>" + compReason + "</td>";
				output += "<td>" + compPhone + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the complaint.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateComplaint(String compID, String compAccNO, String compName, String compArea, String compReason, String compPhone) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE complaint SET compAccNO=?,compName=?,compArea=?,compReason=?,compPhone=?" + "WHERE compID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, compAccNO);
			preparedStmt.setString(2, compName);
			preparedStmt.setString(3, compArea);
			preparedStmt.setString(4, compReason);
			preparedStmt.setString(5, compPhone);
			preparedStmt.setInt(6, Integer.parseInt(compID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the complaint.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteComplaint(String compID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from complaint where compID =?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(compID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the complaint.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
