import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnect {
	static String connectionUrl = "jdbc:sqlserver://localhost:1433; "
			+ "databaseName= grocerysystem;"
			+ "username= frankie;"
			+ "password= frankie123;"
			+ "encrypt=true;trustServerCertificate=true;";
	static Connection con = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static  Statement st = null;
	
	public static void main(String[] args) {
		try (Connection connection= DriverManager.getConnection(connectionUrl);){
			System.out.println("Connected Successfully");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
