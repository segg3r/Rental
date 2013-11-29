import java.sql.Connection;
import java.sql.SQLException;

import by.gsu.segg3r.rental.connection.DbConnection;


public class Runner {

	public static void main(String[] args) {
		Connection cn;
		try {
			cn = DbConnection.getConnection();
			DbConnection.closeConnection(cn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
