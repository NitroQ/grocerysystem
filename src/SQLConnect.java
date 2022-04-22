import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class SQLConnect {
	static String connectionUrl = "jdbc:sqlserver://goshopper.database.windows.net; "
			+ "databaseName= GoShopperPOS;"
			+ "username= FrankAdmin2020;"
			+ "password= GoShopper2022!@$;"
			+ "encrypt=true;trustServerCertificate=true;";
	static Connection con = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static  Statement st = null;
	public static enum LogType {
	    INSERT("insert"),
	    UPDATE("update"),
	    DISABLE("disable");
	    
	    private String val;
	    
	    private LogType(String val) { this.val = val; }
	    
	    @Override public String toString() { return this.val; }
	    
	    public String getAction() {
	            return this.val;
	    }
	}
	
	public static void main(String[] args) {
		try (Connection connection= DriverManager.getConnection(connectionUrl);){
			System.out.println("Connected Successfully");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createlog(LogType family, String emp_id, String where, String remarks) {
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("INSERT INTO Logs (emp_id, family, loc, remarks) VALUES (?,?,?,?)");
             ps.setString(1, emp_id);
             ps.setString(2, family.toString());
             ps.setString(3, where);
             ps.setString(4, remarks);
             ps.executeUpdate();
            
    	 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
	}

}
