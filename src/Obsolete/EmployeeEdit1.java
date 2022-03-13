package Obsolete;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.table.TableModel;

import SQLConnect;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class EmployeeEdit1 extends SQLConnect {

	JFrame frame;
     private JTextField txtEditFName, txtEditLName,txtEditAddress,txtEditUsername, txtEditEmail,txtEditAge;
     private JPasswordField fieldEditPassword;
     private JComboBox cmbEditGender, cmbEditRole;
     private  JCheckBox chckEditUser;
     private String id, Fname, Lname, Address, Username, Email, Age, Gender, Role, Password;
     private Boolean user_type;


	public EmployeeEdit1(String id) {
		this.id = id;
		initialize();
	}

	public void find() {
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Employee WHERE id = ?");
		    ps.setString(1, id);
		    rs = ps.executeQuery();
		    while(rs.next()) {
			   Fname = rs.getString("fname");
			   Lname = rs.getString("lname");
			   Address = rs.getString("address");
			   Email = rs.getString("email");
			   Age = rs.getString("age");
			   Gender = rs.getString("gender");
			   Role = rs.getString("position");
		    }
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Users WHERE emp_id = ?");
		    ps.setString(1, id);
		    rs = ps.executeQuery();
		    while(rs.next()) {
			   Username = rs.getString("username");
			   Password = rs.getString("password");
			   user_type = rs.getString("user_type").equals("admin") ? true : false;
		    }
			   
    	 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		find();
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 718, 772);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel EditEmployeeText = new JLabel("Edit Employee");
		EditEmployeeText.setForeground(Color.BLACK);
		EditEmployeeText.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		EditEmployeeText.setBounds(28, 85, 325, 35);
		frame.getContentPane().add(EditEmployeeText);
		
		JLabel lblEditFName = new JLabel("First Name");
		lblEditFName.setForeground(Color.BLACK);
		lblEditFName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblEditFName.setBounds(28, 145, 294, 21);
		frame.getContentPane().add(lblEditFName);
		
		txtEditFName = new JTextField(Fname);
		txtEditFName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		txtEditFName.setBackground(new Color(245, 245, 245));
		txtEditFName.setBounds(28, 177, 294, 29);
		frame.getContentPane().add(txtEditFName);
		txtEditFName.setColumns(10);
		
		JLabel lblEditLName = new JLabel("Last Name");
		lblEditLName.setForeground(Color.BLACK);
		lblEditLName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblEditLName.setBounds(363, 145, 294, 21);
		frame.getContentPane().add(lblEditLName);
		
		txtEditLName = new JTextField(Lname);
		txtEditLName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		txtEditLName.setColumns(10);
		txtEditLName.setBackground(new Color(245, 245, 245));
		txtEditLName.setBounds(363, 177, 294, 29);
		frame.getContentPane().add(txtEditLName);
		
		JLabel lblEditGender = new JLabel("Gender");
		lblEditGender.setForeground(Color.BLACK);
		lblEditGender.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblEditGender.setBounds(28, 444, 325, 21);
		frame.getContentPane().add(lblEditGender);
		
		cmbEditGender = new JComboBox();
		cmbEditGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
		cmbEditGender.setSelectedItem(Gender.equals("F") ? "Female" : "Male");
		cmbEditGender.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbEditGender.setBackground(new Color(245, 245, 245));
		cmbEditGender.setBounds(28, 482, 294, 29);
		frame.getContentPane().add(cmbEditGender);
		
		JButton btnEdit = new JButton("Save");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				    con = DriverManager.getConnection(connectionUrl);
				    ps = con.prepareStatement("UPDATE Employee SET fname = ?, lname = ?, email= ?, address = ?, position = ?, gender = ?, age= ? WHERE id = ? ");
		             ps.setString(1, txtEditFName.getText());
		             ps.setString(2, txtEditLName.getText());
		             ps.setString(3, txtEditEmail.getText());
		             ps.setString(4, txtEditAddress.getText());
		             ps.setString(5, String.valueOf(cmbEditRole.getSelectedItem()).equals("Merchandiser") ? "Merch" : String.valueOf(cmbEditRole.getSelectedItem()) );
		             ps.setString(6, String.valueOf(cmbEditGender.getSelectedItem()).equals("Female") ? "F" : "M" );
		             ps.setString(7, txtEditAge.getText());
		             ps.setString(8, id);
		             ps.executeUpdate();
		             

		             ps = con.prepareStatement("UPDATE Users SET username = ?, password = ?, user_type = ? WHERE emp_id = ?");
		             ps.setString(1, txtEditUsername.getText());
		             if(fieldEditPassword.getPassword() != null || !String.valueOf(fieldEditPassword.getPassword()).equals("")) {
		            	 ps.setString(2, String.valueOf(fieldEditPassword.getPassword()));
		             }else {
		            	 ps.setString(2, Password);
		             }
		             ps.setString(3, chckEditUser.isSelected() ? "admin" : "emp" );
		             ps.setString(4, id);
		             ps.executeUpdate();
		             
		             JOptionPane.showMessageDialog(null, "Updated");
		            
		             frame.dispose();
		             
		             
				 }catch(HeadlessException | SQLException ex){
		    		 JOptionPane.showMessageDialog(null, ex );
		         }
			}
		});
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setBounds(28, 653, 78, 35);
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
		btnCancel.setBounds(116, 653, 120, 35);
		frame.getContentPane().add(btnCancel);
		Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdminSub.png")).getImage();
		
		JLabel lblEditAddress = new JLabel("Address");
		lblEditAddress.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditAddress.setBounds(28, 217, 294, 21);
		frame.getContentPane().add(lblEditAddress);
		
		txtEditAddress = new JTextField(Address);
		txtEditAddress.setBackground(new Color(245, 245, 245));
		txtEditAddress.setBounds(28, 249, 629, 29);
		frame.getContentPane().add(txtEditAddress);
		txtEditAddress.setColumns(10);
		
		JLabel lblEditUsername = new JLabel("Username");
		lblEditUsername.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditUsername.setBounds(28, 301, 294, 21);
		frame.getContentPane().add(lblEditUsername);
		
		txtEditUsername = new JTextField(Username);
		txtEditUsername.setBackground(new Color(245, 245, 245));
		txtEditUsername.setBounds(28, 333, 294, 29);
		frame.getContentPane().add(txtEditUsername);
		txtEditUsername.setColumns(10);
		
		JLabel GoShopperAdmin_Logo = new JLabel("New label");
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
		frame.getContentPane().add(GoShopperAdmin_Logo);
		
		JLabel lblEditEmail = new JLabel("Email");
		lblEditEmail.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditEmail.setBounds(363, 301, 294, 21);
		frame.getContentPane().add(lblEditEmail);
		
		txtEditEmail = new JTextField(Email);
		txtEditEmail.setBackground(new Color(245, 245, 245));
		txtEditEmail.setBounds(363, 333, 294, 29);
		frame.getContentPane().add(txtEditEmail);
		txtEditEmail.setColumns(10);
		
		JLabel lblEditPassword = new JLabel("Password");
		lblEditPassword.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditPassword.setBounds(27, 373, 295, 21);
		frame.getContentPane().add(lblEditPassword);
		
		fieldEditPassword = new JPasswordField();
		fieldEditPassword.setBackground(new Color(245, 245, 245));
		fieldEditPassword.setBounds(27, 404, 295, 29);
		frame.getContentPane().add(fieldEditPassword);
		
		JLabel lblEditRole = new JLabel("Role");
		lblEditRole.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditRole.setBounds(363, 373, 294, 21);
		frame.getContentPane().add(lblEditRole);
		
		cmbEditRole = new JComboBox();
		cmbEditRole.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbEditRole.setModel(new DefaultComboBoxModel(new String[] {"Merchandiser", "Cashier", "Manager"}));
		cmbEditRole.setSelectedItem(Role.equals("Merch") ? "Merchandiser" : Role );
		cmbEditRole.setBounds(363, 403, 294, 29);
		frame.getContentPane().add(cmbEditRole);
		
		JLabel lblEditAge = new JLabel("Age");
		lblEditAge.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditAge.setBounds(363, 444, 294, 21);
		frame.getContentPane().add(lblEditAge);
		
		txtEditAge = new JTextField(Age);
		txtEditAge.setBackground(new Color(245, 245, 245));
		txtEditAge.setBounds(363, 483, 294, 29);
		frame.getContentPane().add(txtEditAge);
		txtEditAge.setColumns(10);
		
		JLabel lblEditUser = new JLabel("User Type");
		lblEditUser.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditUser.setBounds(28, 522, 295, 21);
		frame.getContentPane().add(lblEditUser);
		
		chckEditUser = new JCheckBox("Admin");
		chckEditUser.setSelected(user_type);
		chckEditUser.setFont(new Font("Dialog", Font.PLAIN, 12));
		chckEditUser.setBounds(28, 550, 97, 31);
		frame.getContentPane().add(chckEditUser);
	}
}