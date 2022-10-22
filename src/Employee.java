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
import java.sql.DriverManager;
import java.sql.SQLException;
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
    private  String[] columns = {"Employee Number", "Employee Name", "Role"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
     private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };
    private EmployeeAdd add_window;
    private EmployeeEdit edit_emp;


	public void updateTable() {
		   model.setRowCount(0);
		try{
		    con = DriverManager.getConnection(connectionUrl, username, password);
		    ps = con.prepareStatement("SELECT * FROM Employee");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	if(!rs.getString("emp_id").equals("2000")) {
		    		model.addRow(new Object[]{rs.getString("emp_id"), rs.getString("fname") + " " +rs.getString("lname"), rs.getString("position").equals("Merch") ? "Merchandiser" : rs.getString("position") });	
		    	}
		    	
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
				if(edit_emp != null) {
					edit_emp.frame.dispose();
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
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add_window = new EmployeeAdd();
				add_window.frame.setVisible(true);
				
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnAdd.setBackground(new Color(220, 20, 60));
		btnAdd.setBounds(635, 30, 78, 35);
		panel.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_EmployeeList.getSelectedRow();
				if(row != -1) {
					String emp_id_edit = String.valueOf(table_EmployeeList.getModel().getValueAt(row, 0)); 
					edit_emp = new EmployeeEdit(emp_id_edit);
					edit_emp.frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "No selected Employee.");
				}
			}
		});
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setBounds(725, 30, 80, 35);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("Disable");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_EmployeeList.getSelectedRow();
				if(row != -1) {
					try{
						String del = String.valueOf(table_EmployeeList.getModel().getValueAt(row, 0)); 
					    con = DriverManager.getConnection(connectionUrl, username, password);
					    ps = con.prepareStatement("DELETE FROM Users WHERE emp_id = ?; UPDATE Employee SET empstatus = 'inactive' WHERE emp_id = ?");
					    ps.setString(1, del);
					    ps.setString(2, del);

					    int n = JOptionPane.showConfirmDialog(null, "Confirm Disable? \n this will delete User Login" , "WARNING", JOptionPane.YES_NO_OPTION);

					      if(n == JOptionPane.YES_OPTION) {
					          ps.executeUpdate();
					          updateTable();
					      }
				          
				 	 }catch(HeadlessException | SQLException ex){
				 		 JOptionPane.showMessageDialog(null, ex );
				      }
					
				}else {
					JOptionPane.showMessageDialog(null, "No Selected Item");
				}
				
				
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnDelete.setBackground(new Color(220, 20, 60));
		btnDelete.setBounds(814, 30, 102, 35);
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

class EmployeeEdit{

		JFrame frame;
	     private JTextField txtEditFName, txtEditLName,txtEditAddress,txtEditUsername, txtEditEmail,txtEditAge;
	     private JPasswordField fieldEditPassword;
	     private JComboBox cmbEditGender, cmbEditRole;
	     private  JCheckBox chckEditUser, chckActUser;
	     private String id, Fname, Lname, Address, Username, Email, Age, Gender, Role, Password, empstatus;
	     private Boolean user_type = false;


		public EmployeeEdit(String id) {
			this.id = id;
			initialize();
		}

		public void find() {
			try{
			    con = DriverManager.getConnection(connectionUrl, username, password);
			    ps = con.prepareStatement("SELECT * FROM Employee WHERE emp_id = ?");
			    ps.setString(1, id);
			    rs = ps.executeQuery();
			    while(rs.next()) {
				   Fname = rs.getString("fname");
				   Lname = rs.getString("lname");
				   Address = rs.getString("empaddress");
				   Email = rs.getString("email");
				   Age = rs.getString("age");
				   Gender = rs.getString("gender");
				   Role = rs.getString("position");
				   empstatus = rs.getString("empstatus");
			    }
			  if(empstatus.equals("active")) {
				  con = DriverManager.getConnection(connectionUrl, username, password);
				    ps = con.prepareStatement("SELECT * FROM Users WHERE emp_id = ?");
				    ps.setString(1, id);
				    rs = ps.executeQuery();
				    while(rs.next()) {
					   Username = rs.getString("username");
					   Password = rs.getString("password");
					   user_type = rs.getString("user_type").equals("admin") ? true : false;
				    }
				  
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
				
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension (718, 700);
			
			frame = new JFrame("GoShopper Employee Edit");
			frame.setResizable(false);
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,897, 508);
			frame.setLocationRelativeTo(null);
			
	        frame.setUndecorated(true);
	        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
			
			Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
			frame.setIconImage(icon);
			frame.getContentPane().setLayout(null);
			
			JLabel EditEmployeeText = new JLabel("Edit Employee");
			EditEmployeeText.setForeground(Color.BLACK);
			EditEmployeeText.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			EditEmployeeText.setBounds(28, 85, 325, 35);
			frame.getContentPane().add(EditEmployeeText);
			
			JLabel lblEditFName = new JLabel("First Name");
			lblEditFName.setForeground(Color.BLACK);
			lblEditFName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditFName.setBounds(28, 130, 294, 21);
			frame.getContentPane().add(lblEditFName);
			
			txtEditFName = new JTextField(Fname);
			txtEditFName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditFName.setBackground(new Color(245, 245, 245));
			txtEditFName.setBounds(28, 162, 273, 35);
			frame.getContentPane().add(txtEditFName);
			txtEditFName.setColumns(10);
			
			JLabel lblEditLName = new JLabel("Last Name");
			lblEditLName.setForeground(Color.BLACK);
			lblEditLName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditLName.setBounds(311, 130, 294, 21);
			frame.getContentPane().add(lblEditLName);
			
			txtEditLName = new JTextField(Lname);
			txtEditLName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditLName.setColumns(10);
			txtEditLName.setBackground(new Color(245, 245, 245));
			txtEditLName.setBounds(311, 162, 273, 35);
			frame.getContentPane().add(txtEditLName);
			
			JLabel lblEditGender = new JLabel("Gender");
			lblEditGender.setForeground(Color.BLACK);
			lblEditGender.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditGender.setBounds(396, 208, 325, 21);
			frame.getContentPane().add(lblEditGender);
			
			cmbEditGender = new JComboBox();
			cmbEditGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
			cmbEditGender.setSelectedItem(Gender.equals("F") ? "Female" : "Male");
			cmbEditGender.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
			cmbEditGender.setBackground(new Color(245, 245, 245));
			cmbEditGender.setBounds(396, 239, 188, 35);
			frame.getContentPane().add(cmbEditGender);
			
			JButton btnEdit = new JButton("Save");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
	
						String Fname = txtEditFName.getText();
						String Lname = txtEditLName.getText();
						String Email = txtEditEmail.getText();
						String Address = txtEditAddress.getText();
						String Age = txtEditAge.getText();
						String email = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";
						Pattern pattern = Pattern.compile(email, Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(Email);		
						
			             if(Fname.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "First name is empty");
				 		    }
				 		    else if(Lname.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Surname is empty");
				 		    }
				 		    else if(Email.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Email is empty");
				 		    }
							else if(!matcher.matches()) {
								JOptionPane.showMessageDialog(null, "Wrong email format");
							}
				 		    else if(Address.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Address is empty");
				 		    }
				 		    else if(Age.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Age is empty");
				 		    }
				 		   else if(empstatus.equals("active") &&  txtEditUsername.getText().trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Username is required.");
				 		    }
				 		    else if(empstatus.equals("inactive") &&  chckActUser.isSelected() && String.valueOf(fieldEditPassword.getPassword()).trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Password is required.");
				 		    }
				 		    else if(empstatus.equals("inactive") &&  chckActUser.isSelected() && txtEditUsername.getText().trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Username is required.");
				 		    }
				 		    else {
		
							    con = DriverManager.getConnection(connectionUrl, username, password);
							    ps = con.prepareStatement("UPDATE Employee SET fname = ?, lname = ?, email= ?, empaddress = ?, position = ?, gender = ?, age= ? WHERE emp_id = ? ");
					             ps.setString(1, txtEditFName.getText());
					             ps.setString(2, txtEditLName.getText());
					             ps.setString(3, txtEditEmail.getText());
					             ps.setString(4, txtEditAddress.getText());
					             ps.setString(5, String.valueOf(cmbEditRole.getSelectedItem()).equals("Merchandiser") ? "Merch" : String.valueOf(cmbEditRole.getSelectedItem()) );
					             ps.setString(6, String.valueOf(cmbEditGender.getSelectedItem()).equals("Female") ? "F" : "M" );
					             ps.setString(7, txtEditAge.getText());
					             ps.setString(8, id);
					             ps.executeUpdate();
		
					             
					             if(empstatus.equals("active")) {
					            	   ps = con.prepareStatement("UPDATE Users SET username = ?, password = ?, user_type = ? WHERE emp_id = ?");
							             ps.setString(1, txtEditUsername.getText());
							             if(fieldEditPassword.getPassword() != null && !String.valueOf(fieldEditPassword.getPassword()).trim().equals("")) {
							            	 ps.setString(2, String.valueOf(fieldEditPassword.getPassword()));
							             }else {
							            	 ps.setString(2, Password);
							             }
							             ps.setString(3, chckEditUser.isSelected() ? "admin" : "emp" );
							             ps.setString(4, id);
							             ps.executeUpdate();
							             JOptionPane.showMessageDialog(null, "Updated");
					             }else if(empstatus.equals("inactive") &&  chckActUser.isSelected()) {
					            	  ps = con.prepareStatement("INSERT INTO Users (emp_id, username, password, user_type) VALUES  (?,?,?,?)");
							             ps.setString(1, emp_id);
							             ps.setString(2, txtEditUsername.getText());
							             ps.setString(3, String.valueOf(fieldEditPassword.getPassword()));
							             ps.setString(4, chckEditUser.isSelected() ? "admin" : "emp" );
							             ps.executeUpdate();					             
							             JOptionPane.showMessageDialog(null, "Updated, User now active");
					             }
					             updateTable();
					             frame.dispose();
					}
			             


					}catch(HeadlessException | SQLException ex){
			    		 JOptionPane.showMessageDialog(null, ex );
			         }
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
			
			JLabel lblEditAddress = new JLabel("Address");
			lblEditAddress.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditAddress.setBounds(28, 207, 294, 21);
			frame.getContentPane().add(lblEditAddress);
			
			txtEditAddress = new JTextField(Address);
			txtEditAddress.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditAddress.setBackground(new Color(245, 245, 245));
			txtEditAddress.setBounds(28, 239, 358, 35);
			frame.getContentPane().add(txtEditAddress);
			txtEditAddress.setColumns(10);
			
			JLabel lblEditUsername = new JLabel("Username");
			lblEditUsername.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditUsername.setBounds(28, 284, 294, 21);
			frame.getContentPane().add(lblEditUsername);
			
			txtEditUsername = new JTextField(Username);
			txtEditUsername.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditUsername.setBackground(new Color(245, 245, 245));
			txtEditUsername.setBounds(28, 316, 273, 35);
			frame.getContentPane().add(txtEditUsername);
			txtEditUsername.setColumns(10);
			
			JLabel lblEditEmail = new JLabel("Email");
			lblEditEmail.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditEmail.setBounds(594, 130, 294, 21);
			frame.getContentPane().add(lblEditEmail);
			
			txtEditEmail = new JTextField(Email);
			txtEditEmail.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditEmail.setBackground(new Color(245, 245, 245));
			txtEditEmail.setBounds(594, 162, 273, 35);
			frame.getContentPane().add(txtEditEmail);
			txtEditEmail.setColumns(10);
			
			JLabel lblEditPassword = new JLabel("Password");
			lblEditPassword.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditPassword.setBounds(311, 285, 295, 21);
			frame.getContentPane().add(lblEditPassword);
			
			fieldEditPassword = new JPasswordField();
			fieldEditPassword.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
			fieldEditPassword.setBackground(new Color(245, 245, 245));
			fieldEditPassword.setBounds(311, 316, 273, 35);
			frame.getContentPane().add(fieldEditPassword);
			
			JLabel lblEditRole = new JLabel("Role");
			lblEditRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditRole.setBounds(594, 286, 294, 21);
			frame.getContentPane().add(lblEditRole);
			
			cmbEditRole = new JComboBox();
			cmbEditRole.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
			cmbEditRole.setModel(new DefaultComboBoxModel(new String[] {"Merchandiser", "Cashier", "Manager"}));
			cmbEditRole.setSelectedItem(Role.equals("Merch") ? "Merchandiser" : Role );
			cmbEditRole.setBounds(594, 316, 273, 35);
			frame.getContentPane().add(cmbEditRole);
			
			JLabel lblEditAge = new JLabel("Age");
			lblEditAge.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditAge.setBounds(594, 208, 294, 21);
			frame.getContentPane().add(lblEditAge);
			
			txtEditAge = new JTextField(Age);
			txtEditAge.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		char c = e.getKeyChar();
	        		
	        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
	        		e.consume();	        		
	        			}
	        	}
	        });
			txtEditAge.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEditAge.setBackground(new Color(245, 245, 245));
			txtEditAge.setBounds(594, 238, 273, 35);
			frame.getContentPane().add(txtEditAge);
			txtEditAge.setColumns(10);
			
			JLabel lblEditUser = new JLabel("User Type");
			lblEditUser.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditUser.setBounds(27, 369, 295, 21);
			frame.getContentPane().add(lblEditUser);
			
			chckEditUser = new JCheckBox("Admin");
			chckEditUser.setBackground(new Color(255, 255, 255));
			chckEditUser.setSelected(user_type);
			chckEditUser.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
			chckEditUser.setBounds(27, 396, 97, 31);
			frame.getContentPane().add(chckEditUser);
			
			
			if(empstatus.equals("inactive")) {
				JLabel Activate = new JLabel("User Type");
				Activate.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
				Activate.setBounds(150, 369, 295, 21);
				frame.getContentPane().add(Activate);
				
				chckActUser = new JCheckBox("Set Active?");
				chckActUser.setBackground(new Color(255, 255, 255));
				chckActUser.setSelected(false);
				chckActUser.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
				chckActUser.setBounds(150, 396, 97, 31);
				frame.getContentPane().add(chckActUser);
			}

			JLabel GoShopperAdmin_Logo = new JLabel("New label");
			GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
			GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
			frame.getContentPane().add(GoShopperAdmin_Logo);
		}
	}

class EmployeeAdd {

		JFrame frame;
	     private JTextField txtFName, txtLName, txtUsername, txtEmail, txtAge,txtAddress;
	     private JPasswordField fieldPassword;
	     private JComboBox  cmbPosition, cmbGender;
	     private JCheckBox chckUser;


		public EmployeeAdd() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension (718, 700);
			
			frame = new JFrame("GoShopper Employee Add");
			frame.setResizable(false);
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,897, 508);
			frame.setLocationRelativeTo(null);
	        frame.setUndecorated(true);
	        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
			Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
			frame.setIconImage(icon);		
			frame.getContentPane().setLayout(null);


			JLabel EditEmployeeText = new JLabel("Add Employee");
			EditEmployeeText.setForeground(Color.BLACK);
			EditEmployeeText.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
			EditEmployeeText.setBounds(28, 85, 325, 35);
			frame.getContentPane().add(EditEmployeeText);
			
			JLabel lblEditFName = new JLabel("First Name");
			lblEditFName.setForeground(Color.BLACK);
			lblEditFName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditFName.setBounds(28, 130, 294, 21);
			frame.getContentPane().add(lblEditFName);
			
			txtFName = new JTextField();
			txtFName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtFName.setBackground(new Color(245, 245, 245));
			txtFName.setBounds(28, 162, 273, 35);
			frame.getContentPane().add(txtFName);
			txtFName.setColumns(10);
			
			JLabel lblEditLName = new JLabel("Last Name");
			lblEditLName.setForeground(Color.BLACK);
			lblEditLName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditLName.setBounds(311, 130, 294, 21);
			frame.getContentPane().add(lblEditLName);
			
			txtLName = new JTextField();
			txtLName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtLName.setColumns(10);
			txtLName.setBackground(new Color(245, 245, 245));
			txtLName.setBounds(311, 162, 273, 35);
			frame.getContentPane().add(txtLName);
			
			JLabel lblEditGender = new JLabel("Gender");
			lblEditGender.setForeground(Color.BLACK);
			lblEditGender.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditGender.setBounds(396, 208, 325, 21);
			frame.getContentPane().add(lblEditGender);
			
			cmbGender = new JComboBox();
			cmbGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
			cmbGender.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
			cmbGender.setBackground(new Color(245, 245, 245));
			cmbGender.setBounds(396, 239, 188, 35);
			frame.getContentPane().add(cmbGender);
			
			
			JButton btnEdit = new JButton("Save");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String emp_id = null;
					try{
						
						String Fname = txtFName.getText();
						String Lname = txtLName.getText();
						String Email = txtEmail.getText();
						String Address = txtAddress.getText();
						String Age = txtAge.getText();
						String email = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";
						Pattern pattern = Pattern.compile(email, Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(Email);		
						
			            	 if(Fname.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "First name is empty");
				 		    }
				 		    else if(Lname.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Surname is empty");
				 		    }
				 		    else if(Email.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Email is empty");
				 		    }
							else if(!matcher.matches()) {
								JOptionPane.showMessageDialog(null, "Wrong email format");
							}
				 		    else if(Address.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Address is empty");
				 		    }
				 		    else if(Age.trim().equals("")) {
				 		    	JOptionPane.showMessageDialog(null, "Age is empty");
				 		    }
							 else if(String.valueOf(fieldPassword.getPassword()).trim().equals("")) {
								JOptionPane.showMessageDialog(null, "Password is required");
							}
							else if(txtUsername.getText().trim().equals("")) {
								JOptionPane.showMessageDialog(null, "Username is required");
							}
				 		    else {
					    con = DriverManager.getConnection(connectionUrl, username, password);
					    ps = con.prepareStatement("INSERT INTO Employee (fname, lname, email, empaddress, position, gender, age) VALUES (?,?,?,?,?,?,?);  SELECT @@IDENTITY AS 'identity';");
			             ps.setString(1, txtFName.getText() );
			             ps.setString(2, txtLName.getText());
			             ps.setString(3, txtEmail.getText());
			             ps.setString(4, txtAddress.getText());
			             ps.setString(5, String.valueOf(cmbPosition.getSelectedItem()).equals("Merchandiser") ? "Merch" : String.valueOf(cmbPosition.getSelectedItem()) );
			             ps.setString(6, String.valueOf(cmbGender.getSelectedItem()).equals("Female") ? "F" : "M" );
			             ps.setString(7, txtAge.getText());
			 		    rs = ps.executeQuery();
			 		    while(rs.next()) {
			 		    	emp_id = rs.getString("identity");
			 		    }
			 		    
			 		    if(emp_id == null) {
			 		    	JOptionPane.showMessageDialog(null, "Error Occurred");
			 		    }else {
			 		    	  ps = con.prepareStatement("INSERT INTO Users (emp_id, username, password, user_type) VALUES  (?,?,?,?)");
					             ps.setString(1, emp_id);
					             ps.setString(2, txtUsername.getText());
					             ps.setString(3, String.valueOf(fieldPassword.getPassword()));
					             ps.setString(4, chckUser.isSelected() ? "admin" : "emp" );
					             ps.executeUpdate();
			 		    }
			             JOptionPane.showMessageDialog(null, "Added");
			             updateTable();
			             frame.dispose();
					}


			                
			    	 }catch(HeadlessException | SQLException ex){
			    		 JOptionPane.showMessageDialog(null, ex );
			         }
					 
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
			
			JLabel lblEditAddress = new JLabel("Address");
			lblEditAddress.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditAddress.setBounds(28, 207, 294, 21);
			frame.getContentPane().add(lblEditAddress);
			
			txtAddress = new JTextField();
			txtAddress.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtAddress.setBackground(new Color(245, 245, 245));
			txtAddress.setBounds(28, 239, 358, 35);
			frame.getContentPane().add(txtAddress);
			txtAddress.setColumns(10);
			
			JLabel lblEditUsername = new JLabel("Username");
			lblEditUsername.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditUsername.setBounds(28, 284, 294, 21);
			frame.getContentPane().add(lblEditUsername);
			
			txtUsername = new JTextField();
			txtUsername.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtUsername.setBackground(new Color(245, 245, 245));
			txtUsername.setBounds(28, 316, 273, 35);
			frame.getContentPane().add(txtUsername);
			txtUsername.setColumns(10);
			
			JLabel lblEditEmail = new JLabel("Email");
			lblEditEmail.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditEmail.setBounds(594, 130, 294, 21);
			frame.getContentPane().add(lblEditEmail);
			
			txtEmail = new JTextField();
			txtEmail.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtEmail.setBackground(new Color(245, 245, 245));
			txtEmail.setBounds(594, 162, 273, 35);
			frame.getContentPane().add(txtEmail);
			txtEmail.setColumns(10);
			
			JLabel lblEditPassword = new JLabel("Password");
			lblEditPassword.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditPassword.setBounds(311, 285, 295, 21);
			frame.getContentPane().add(lblEditPassword);
			
			fieldPassword = new JPasswordField();
			fieldPassword.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
			fieldPassword.setBackground(new Color(245, 245, 245));
			fieldPassword.setBounds(311, 316, 273, 35);
			frame.getContentPane().add(fieldPassword);
			
			JLabel lblEditRole = new JLabel("Role");
			lblEditRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditRole.setBounds(594, 286, 294, 21);
			frame.getContentPane().add(lblEditRole);
			
			cmbPosition = new JComboBox();
			cmbPosition.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
			cmbPosition.setModel(new DefaultComboBoxModel(new String[] {"Merchandiser", "Cashier", "Manager"}));
			cmbPosition.setBounds(594, 316, 273, 35);
			frame.getContentPane().add(cmbPosition);
			
			JLabel lblEditAge = new JLabel("Age");
			lblEditAge.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditAge.setBounds(594, 208, 294, 21);
			frame.getContentPane().add(lblEditAge);
		
			
			txtAge = new JTextField();
			txtAge.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		char c = e.getKeyChar();
	        		
	        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
	        		e.consume();	        		
	        			}
	        	}
	        });
			txtAge.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
			txtAge.setBackground(new Color(245, 245, 245));
			txtAge.setBounds(594, 238, 273, 35);
			frame.getContentPane().add(txtAge);
			txtAge.setColumns(10);
			
			JLabel lblEditUser = new JLabel("User Type");
			lblEditUser.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
			lblEditUser.setBounds(27, 369, 295, 21);
			frame.getContentPane().add(lblEditUser);
			
			chckUser = new JCheckBox("Admin");
			chckUser.setBackground(new Color(255, 255, 255));
			chckUser.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
			chckUser.setBounds(27, 396, 97, 31);
			frame.getContentPane().add(chckUser);
			
			JLabel GoShopperAdmin_Logo = new JLabel("New label");
			GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
			GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
			frame.getContentPane().add(GoShopperAdmin_Logo);
		}
	}
}

