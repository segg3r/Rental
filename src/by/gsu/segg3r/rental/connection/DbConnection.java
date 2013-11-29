package by.gsu.segg3r.rental.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

	private static final String URL = "jdbc:sqlserver://localhost:1443;integratedSecurity=true";
	private static final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	
	private static final String DRIVER_NOT_FOUND_ERROR = "Driver not found.";
	
	static {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(DRIVER_NOT_FOUND_ERROR, e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public static void closeResultSets(ResultSet... resultSets) throws SQLException {
		for (ResultSet resultSet : resultSets) {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}
	
	public static void closeStatements(Statement... statements) throws SQLException {
		for (Statement statement : statements) {
			if (statement != null) {
				statement.close();
			}
		}
	}
	
	public static void closeConnection(Connection cn) throws SQLException {
		if (cn != null) {
			cn.close();
		}
	}
}
