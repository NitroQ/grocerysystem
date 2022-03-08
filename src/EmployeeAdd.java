import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class EmployeeAdd {

	private JFrame frame;
	private JScrollPane scrollPane;
    private  String[] columns = {"Employee Number", "Employee Name", "Role", "Hours", "DTR"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
     private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };
     private JTextField txtFName;
     private JTextField txtLName;
     private JTextField txtUsername;
     private JTextField txtEmail;
     private JPasswordField fieldPassword;
     private JTextField txtAge;
     private JTextField txtAddress;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeAdd window = new EmployeeAdd();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeAdd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 718, 772);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(245, 245, 245));
		txtUsername.setBounds(28, 337, 294, 31);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblUsername.setBounds(28, 305, 293, 21);
		frame.getContentPane().add(lblUsername);
		
		JLabel AddEmployeeText = new JLabel("Add Employee");
		AddEmployeeText.setForeground(Color.BLACK);
		AddEmployeeText.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		AddEmployeeText.setBounds(28, 85, 325, 35);
		frame.getContentPane().add(AddEmployeeText);
		
		JLabel lblFName = new JLabel("First Name");
		lblFName.setForeground(Color.BLACK);
		lblFName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblFName.setBounds(28, 149, 294, 21);
		frame.getContentPane().add(lblFName);
		
		txtFName = new JTextField();
		txtFName.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFName.setBackground(new Color(245, 245, 245));
		txtFName.setBounds(28, 181, 294, 29);
		frame.getContentPane().add(txtFName);
		txtFName.setColumns(10);
		
		JLabel lblLName = new JLabel("Last Name");
		lblLName.setForeground(Color.BLACK);
		lblLName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblLName.setBounds(363, 149, 325, 21);
		frame.getContentPane().add(lblLName);
		
		txtLName = new JTextField();
		txtLName.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtLName.setColumns(10);
		txtLName.setBackground(new Color(245, 245, 245));
		txtLName.setBounds(363, 181, 294, 29);
		frame.getContentPane().add(txtLName);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setForeground(Color.BLACK);
		lblRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblRole.setBounds(363, 379, 293, 21);
		frame.getContentPane().add(lblRole);
		
		JComboBox cmdPosition = new JComboBox();
		cmdPosition.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmdPosition.setModel(new DefaultComboBoxModel(new String[] {"Merch", "Cashier", "Manager"}));
		cmdPosition.setBackground(new Color(245, 245, 245));
		cmdPosition.setBounds(363, 411, 294, 31);
		frame.getContentPane().add(cmdPosition);
		
		JButton btnEdit = new JButton("Save");
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Roboto", Font.BOLD, 14));
		btnEdit.setBackground(new Color(204, 102, 102));
		btnEdit.setBounds(28, 653, 78, 35);
		frame.getContentPane().add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Roboto", Font.BOLD, 14));
		btnCancel.setBackground(new Color(47, 79, 79));
		btnCancel.setBounds(116, 653, 120, 35);
		frame.getContentPane().add(btnCancel);
		Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdminSub.png")).getImage();
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEmail.setBounds(363, 305, 294, 21);
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(245, 245, 245));
		txtEmail.setBounds(363, 337, 293, 31);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblGender.setBounds(28, 453, 294, 21);
		frame.getContentPane().add(lblGender);
		
		JComboBox cmbGender = new JComboBox();
		cmbGender.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
		cmbGender.setBounds(28, 485, 294, 31);
		frame.getContentPane().add(cmbGender);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblPassword.setBounds(28, 379, 294, 21);
		frame.getContentPane().add(lblPassword);
		
		fieldPassword = new JPasswordField();
		fieldPassword.setBackground(new Color(245, 245, 245));
		fieldPassword.setBounds(28, 413, 294, 29);
		frame.getContentPane().add(fieldPassword);
		
		JLabel lblUser = new JLabel("User Type");
		lblUser.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblUser.setBounds(28, 538, 293, 21);
		frame.getContentPane().add(lblUser);
		
		JCheckBox chckUser = new JCheckBox("Admin");
		chckUser.setFont(new Font("Dialog", Font.PLAIN, 12));
		chckUser.setBounds(28, 569, 97, 31);
		frame.getContentPane().add(chckUser);
		
		txtAge = new JTextField();
		txtAge.setBackground(new Color(245, 245, 245));
		txtAge.setBounds(363, 485, 294, 31);
		frame.getContentPane().add(txtAge);
		txtAge.setColumns(10);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblAge.setBounds(363, 453, 294, 21);
		frame.getContentPane().add(lblAge);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblAddress.setBounds(28, 221, 295, 21);
		frame.getContentPane().add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setBackground(new Color(245, 245, 245));
		txtAddress.setBounds(28, 253, 626, 31);
		frame.getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
		
		JLabel GoShopperAdmin_Logo = new JLabel("New label");
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(-17, 0, 333, 393);
		frame.getContentPane().add(GoShopperAdmin_Logo);
	}
}