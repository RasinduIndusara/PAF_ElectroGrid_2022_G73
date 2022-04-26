package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Units {

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

	public String insertUnit(String unit_AccNo, String unit_Date, String unit_Amount, String preunit_price, String unit_Total) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into unit(`unit_ID`,`unit_AccNo`,`unit_Date`,`unit_Amount`,`preunit_price`,`unit_Total`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, unit_AccNo);
			preparedStmt.setString(3, unit_Date);
			preparedStmt.setString(4, unit_Amount);
			preparedStmt.setString(5, preunit_price);
			preparedStmt.setString(6, unit_Total);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the unit.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readUnit() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Unit ID</th><th>Account No</th><th>Date</th><th>Unit Amount</th><th>PerUnit Price</th><th>Total Amount</th></tr>";
			String query = "select * from unit";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String unit_ID = Integer.toString(rs.getInt("unit_ID"));
				String unit_AccNo = rs.getString("unit_AccNo");
				String unit_Date = rs.getString("unit_Date");
				String unit_Amount = rs.getString("unit_Amount");
				String preunit_price = rs.getString("preunit_price");
				String unit_Total = rs.getString("unit_Total");

				// Add into the html table
				output += "<tr><td>" + unit_ID + "</td>";
				output += "<td>" + unit_AccNo + "</td>";
				output += "<td>" + unit_Date + "</td>";
				output += "<td>" + unit_Amount + "</td>";
				output += "<td>" + preunit_price + "</td>";
				output += "<td>" + unit_Total + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the unit.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUnit(String unit_ID, String unit_AccNo, String unit_Date, String unit_Amount, String preunit_price, String unit_Total) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE unit SET unit_AccNo=?,unit_Date=?,unit_Amount=?,preunit_price=?,unit_Total=?" + "WHERE unit_ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, unit_AccNo);
			preparedStmt.setString(2, unit_Date);
			preparedStmt.setString(3, unit_Amount);
			preparedStmt.setString(4, preunit_price);
			preparedStmt.setString(5, unit_Total);
			preparedStmt.setInt(6, Integer.parseInt(unit_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the unit.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteUnit(String unit_ID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from unit where unit_ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(unit_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the unit.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
