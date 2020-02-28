package Utilities;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

import ReusableComponents.GenericMethods;


public class DataBaseConnection {
	static Connection connection;
	static Statement statement;
	static ResultSet resultset;

	public static String getDBvalue(String query) throws ClassNotFoundException, SQLException, IOException {
		String jdbcDriver = "com.mysql.jdbc.Driver", result = null;
		Class.forName(jdbcDriver);
		connection = DriverManager.getConnection(GenericMethods.readDataProperties("DBConnectionURL"),
				GenericMethods.readDataProperties("DBUsername"), GenericMethods.readDataProperties("DBPassword"));
		statement = connection.createStatement();
		resultset = statement.executeQuery(query);
		while (resultset.next()) {
			result = resultset.getString(1);
		}
		resultset.close();
		statement.close();
		connection.close();
		return result;
	}
}
