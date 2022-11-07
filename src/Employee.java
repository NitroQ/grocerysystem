import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Dimension;



public class Employee extends SQLConnect {

	JFrame frame;
	private String emp_id, type;
	private JTable table_EmployeeList;
    private  String[] columns = {"Account", "Employee Number", "Employee Name", "Role"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
     private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };
    public EmployeeAdd add_window;

    public Boolean findacc(String emp_id) {
    	String id_emp = ""; Boolean acc = false;
    	Connection contwo = null;
    	PreparedStatement ps2 = null;
    	ResultSet rs2 = null;
   
    	try {
        	contwo = DriverManager.getConnection(connectionUrl, username, password);
    	    ps2 = contwo.prepareStatement("SELECT * FROM `users` WHERE `emp_id` = ?");
    	    ps2.setString(1, emp_id);
    	    rs2 = ps2.executeQuery();
    	    
    	    while(rs2.next()) {
    	    	 id_emp = rs2.getString("emp_id");
    	    }
    	}catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
        }
		if (emp_id.equals(id_emp)) {
			acc = true;
		}
		return acc;
    }
    
	public void updateTable() {
		   model.setRowCount(0);
		 
		try{
		    con = DriverManager.getConnection(connectionUrl2, username, password);
		    ps = con.prepareStatement("SELECT * FROM `llx_user`");
		    rs = ps.executeQuery();
		    
		    while(rs.next()) {
		    	String id_emp, emp_id, fname, lname, job, acc = " ";
		    	emp_id = rs.getString("rowid");
		    	fname = rs.getString("firstname");
		    	lname = rs.getString("lastname");
		    	job = rs.getString("job");
		    	
		    	if(findacc(emp_id)) {
		    		acc = "☑";
		    	}
		    
		       model.addRow(new Object[]{acc, emp_id, fname + " " + lname, job});	
			   model.fireTableDataChanged();
		    }
		   
 	 }catch(HeadlessException | SQLException ex){
 		 JOptionPane.showMessageDialog(null, ex );
      }
	}

	/**
	 * Create the application.
	 */

	public Employee(String emp_id, String type) {
		this.emp_id = emp_id;
		this.type = type;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {	
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (1020, 700);
		updateTable();
		
        frame = new JFrame("GoShopper Admin");
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,1003, 661);
        frame.setLocationRelativeTo(null);
        
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        
        Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
        frame.setIconImage(icon);
		frame.getContentPane().setLayout(null);
		

		JButton LogOut = new JButton("Back");
		LogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin window = new Admin(emp_id, type);
				window.frame.setVisible(true);
				frame.dispose();			
				if(add_window != null) {
					add_window.frame.dispose();
				}
			}
		});
		LogOut.setForeground(Color.WHITE);
		LogOut.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		LogOut.setBackground(new Color(0, 139, 139));
		LogOut.setBounds(865, 34, 120, 35);
		frame.getContentPane().add(LogOut);
		
		JLabel lblNewLabel_1 = new JLabel("HRIS Records");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		lblNewLabel_1.setBounds(35, 79, 331, 52);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192), 3, true));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(35, 137, 950, 499);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table_EmployeeList = new JTable(model);
		table_EmployeeList.setFont(new Font("Roboto", Font.PLAIN, 17));
		table_EmployeeList.setBackground(Color.WHITE);
		table_EmployeeList.setBorder(null);
		table_EmployeeList.setRowHeight(30);
		table_EmployeeList.setBounds(24, 174, 956, 329);
		JScrollPane scrollPane_1 = new JScrollPane(table_EmployeeList);
		scrollPane_1.setBounds(23, 84, 893, 392);
		panel.add(scrollPane_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Employee List");
		lblNewLabel_1_1.setBounds(23, 27, 331, 38);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 30));
		
		JButton btnAdd = new JButton("Manage Account");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_EmployeeList.getSelectedRow();
				if(row != -1) {
				String emp_id = String.valueOf(table_EmployeeList.getModel().getValueAt(row, 1));
				add_window = new EmployeeAdd(emp_id);
				add_window.frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "No Selected Employee");
				}
				
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnAdd.setBackground(new Color(220, 20, 60));
		btnAdd.setBounds(745, 30, 171, 35);
		panel.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete Account");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_EmployeeList.getSelectedRow();
				if(row != -1) {
				try {
					  con = DriverManager.getConnection(connectionUrl, username, password);
					    ps = con.prepareStatement("DELETE FROM `users` WHERE `emp_id` = ?");
					    ps.setString(1, String.valueOf(table_EmployeeList.getModel().getValueAt(row, 1)));
					   ps.executeUpdate();
					   JOptionPane.showMessageDialog(null, "Deleted Successfully.");
					   updateTable();
				}catch(HeadlessException | SQLException ex){
		    		 JOptionPane.showMessageDialog(null, ex );
		        }
				}  else {
					JOptionPane.showMessageDialog(null, "No Selected Employee");
				}
				
				  
				
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnDelete.setBackground(new Color(220, 20, 60));
		btnDelete.setBounds(594, 30, 141, 35);
		panel.add(btnDelete);
	
		
		JLabel GoShopperAdmin_Logo = new JLabel();
		Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdmin.png")).getImage();
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopperAdmin_Logo);
		
		JLabel GoShopperAdmin_Logo_1 = new JLabel();
		Image bg = new ImageIcon(this.getClass().getResource("/BGAdmin.png")).getImage();
		GoShopperAdmin_Logo_1.setIcon(new ImageIcon(bg));
		GoShopperAdmin_Logo_1.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopperAdmin_Logo_1);
	}
	class EmployeeAdd{

		JFrame frame;
	     private JTextField txtUsername;
	     private JPasswordField fieldPassword;
	     public JLabel lblAcc;
	     private String id, fname, lname, role, uname, pass;
	     private Boolean acc = false;

		public EmployeeAdd(String id) {
			this.id = id;		
			loadData();
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void loadAcc() {
			String id_emp = "";
	    	Connection contwo = null;
	    	PreparedStatement ps2 = null;
	    	ResultSet rs2 = null;
	   
	    	try {
	        	contwo = DriverManager.getConnection(connectionUrl, username, password);
	    	    ps2 = contwo.prepareStatement("SELECT * FROM `users` WHERE `emp_id` = ?");
	    	    ps2.setString(1, id);
	    	    rs2 = ps2.executeQuery();
	    	    
	    	    while(rs2.next()) {
	    	    	id_emp = rs2.getString("emp_id");
	    	    	if(id.equals(id_emp)) {
			    		acc = true;
			    		uname = rs2.getString("username");
			    		pass = rs2.getString("password") ;
			    	}
	    	    }
	    	}catch(HeadlessException | SQLException ex){
	    		 JOptionPane.showMessageDialog(null, ex );
	        }
		}
		private void loadData() {
		try {
			 con = DriverManager.getConnection(connectionUrl2, username, password);
			 ps = con.prepareStatement("SELECT * FROM `llx_user` WHERE `rowid` = ?");
			 ps.setString(1, id);
			  rs = ps.executeQuery();
			    while(rs.next()) {
			    	fname = rs.getString("firstname");
			    	lname = rs.getString("lastname");
			    	role = (rs.getString("admin") == "1" ? "admin" : "emp");
			    }
			    loadAcc();
			    	
		 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
		}
		private void initialize() {
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension (718, 700);
			
			frame = new JFrame("GoShopper Employee Add Account");
			frame.setResizable(false);
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,897, 508);
			frame.setLocationRelativeTo(null);
	        frame.setUndecorated(true);
	        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
			Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
			frame.setIconImage(icon);		
			frame.getContentPane().setLayout(null);


			JLabel EditEmployeeText = new JLabel("Add Employee Account");
			EditEmployeeText.setForeground(Color.BLACK);
			EditEmployeeText.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			EditEmployeeText.setBounds(28, 85, 325, 35);
			frame.getContentPane().add(EditEmployeeText);
			
			
			JButton btnEdit = new JButton("Save");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				if(!acc) {
					try{
						 con = DriverManager.getConnection(connectionUrl, username, password);
						 ps = con.prepareStatement("INSERT INTO Users (emp_id, username, password, user_type) VALUES  (?,?,?,?)");
			             ps.setString(1, id);
			             ps.setString(2, txtUsername.getText());
			             ps.setString(3, String.valueOf(fieldPassword.getPassword()));
			             ps.setString(4, role );
			             ps.executeUpdate();
						
			    	 }catch(HeadlessException | SQLException ex){
			    		 JOptionPane.showMessageDialog(null, ex );
			         }
				}else {
					try{
						 con = DriverManager.getConnection(connectionUrl, username, password);
						 ps = con.prepareStatement("UPDATE `users` SET `username`= ?,`password`= ? ,`user_type`= ? WHERE `emp_id` = ?");
			             ps.setString(1, txtUsername.getText());
			             ps.setString(2, (String.valueOf(fieldPassword.getPassword()).trim().equals("") ? pass : String.valueOf(fieldPassword.getPassword())));
			             ps.setString(3, role );
			             ps.setString(4, id);
			             ps.executeUpdate();
						
			    	 }catch(HeadlessException | SQLException ex){
			    		 JOptionPane.showMessageDialog(null, ex );
			         }
				}
					updateTable();
					frame.dispose();
				}
			});
			btnEdit.setForeground(Color.WHITE);
			btnEdit.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
			btnEdit.setBackground(new Color(220, 20, 60));
			btnEdit.setBounds(28, 443, 78, 35);
			frame.getContentPane().add(btnEdit);
			
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			btnCancel.setForeground(Color.WHITE);
			btnCancel.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
			btnCancel.setBackground(new Color(0, 139, 139));
			btnCancel.setBounds(117, 443, 120, 35);
			frame.getContentPane().add(btnCancel);
			Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdminSub.png")).getImage();
			
			JLabel lblEditUsername = new JLabel("Username");
			lblEditUsername.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditUsername.setBounds(28, 246, 294, 21);
			frame.getContentPane().add(lblEditUsername);
			
			txtUsername = new JTextField(uname);
			txtUsername.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtUsername.setBackground(new Color(245, 245, 245));
			txtUsername.setBounds(28, 278, 273, 35);
			frame.getContentPane().add(txtUsername);
			txtUsername.setColumns(10);
			
			JLabel lblEditPassword = new JLabel("Password");
			lblEditPassword.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditPassword.setBounds(311, 247, 295, 21);
			frame.getContentPane().add(lblEditPassword);
			
			fieldPassword = new JPasswordField();
			fieldPassword.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
			fieldPassword.setBackground(new Color(245, 245, 245));
			fieldPassword.setBounds(311, 278, 273, 35);
			frame.getContentPane().add(fieldPassword);
			
			JLabel GoShopperAdmin_Logo = new JLabel();
			GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
			GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
			frame.getContentPane().add(GoShopperAdmin_Logo);
			
			JLabel lblId = new JLabel("ID#" + id);
			lblId.setForeground(Color.BLACK);
			lblId.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			lblId.setBounds(28, 131, 120, 35);
			frame.getContentPane().add(lblId);
			
			JLabel lblEmpName = new JLabel(lname + ", " + fname);
			lblEmpName.setForeground(Color.BLACK);
			lblEmpName.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			lblEmpName.setBounds(128, 131, 361, 35);
			frame.getContentPane().add(lblEmpName);
			
			JLabel lblRole = new JLabel(role.equals("1") ? "Admin" : "Non-Admin");
			lblRole.setForeground(Color.BLACK);
			lblRole.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			lblRole.setBounds(28, 174, 361, 35);
			frame.getContentPane().add(lblRole);
			
			lblAcc = new JLabel("\u2611\uFE0F Existing Account");
			lblAcc.setBounds(28, 221, 152, 14);
			lblAcc.setVisible(acc);
			frame.getContentPane().add(lblAcc);
		}

	}
}

