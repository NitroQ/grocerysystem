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


}

