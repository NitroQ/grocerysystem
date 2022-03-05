import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
	public static void main(String[] args) {
		String connectionUrl = "jdbc:sqlserver://localhost:1433; "
				+ "databaseName= INF202Gesmundo;"
				+ "username= frankie;"
				+ "password= frankie123;"
				+ "encrypt=true;trustServerCertificate=true;";

		
		try (Connection connection= DriverManager.getConnection(connectionUrl);){
			System.out.println("Connected Successfully");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
