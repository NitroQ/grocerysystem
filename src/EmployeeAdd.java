import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class EmployeeAdd extends SQLConnect{

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
//					updateTable();
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