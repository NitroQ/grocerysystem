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
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class EmployeeEdit {

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
     private JTextField txtEditFName;
     private JTextField txtEditLName;
     private JTextField txtEditAddress;
     private JTextField txtEditUsername;
     private JTextField txtEditEmail;
     private JPasswordField fieldEditPassword;
     private JTextField txtEditAge;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeEdit window = new EmployeeEdit();
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
	public EmployeeEdit() {
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
		
		txtEditFName = new JTextField();
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
		
		txtEditLName = new JTextField();
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
		
		JComboBox cmbEditGender = new JComboBox();
		cmbEditGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
		cmbEditGender.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbEditGender.setBackground(new Color(245, 245, 245));
		cmbEditGender.setBounds(28, 482, 294, 29);
		frame.getContentPane().add(cmbEditGender);
		
		JButton btnEdit = new JButton("Save");
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setBounds(28, 653, 78, 35);
		frame.getContentPane().add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		txtEditAddress = new JTextField();
		txtEditAddress.setBackground(new Color(245, 245, 245));
		txtEditAddress.setBounds(28, 249, 629, 29);
		frame.getContentPane().add(txtEditAddress);
		txtEditAddress.setColumns(10);
		
		JLabel lblEditUsername = new JLabel("Username");
		lblEditUsername.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditUsername.setBounds(28, 301, 294, 21);
		frame.getContentPane().add(lblEditUsername);
		
		txtEditUsername = new JTextField();
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
		
		txtEditEmail = new JTextField();
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
		
		JComboBox cmbEditRole = new JComboBox();
		cmbEditRole.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbEditRole.setModel(new DefaultComboBoxModel(new String[] {"Merch", "Cashier", "Manager"}));
		cmbEditRole.setBounds(363, 403, 294, 29);
		frame.getContentPane().add(cmbEditRole);
		
		JLabel lblEditAge = new JLabel("Age");
		lblEditAge.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditAge.setBounds(363, 444, 294, 21);
		frame.getContentPane().add(lblEditAge);
		
		txtEditAge = new JTextField();
		txtEditAge.setBackground(new Color(245, 245, 245));
		txtEditAge.setBounds(363, 483, 294, 29);
		frame.getContentPane().add(txtEditAge);
		txtEditAge.setColumns(10);
		
		JLabel lblEditUser = new JLabel("User Type");
		lblEditUser.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEditUser.setBounds(28, 522, 295, 21);
		frame.getContentPane().add(lblEditUser);
		
		JCheckBox chckEditUser = new JCheckBox("Admin");
		chckEditUser.setFont(new Font("Dialog", Font.PLAIN, 12));
		chckEditUser.setBounds(28, 550, 97, 31);
		frame.getContentPane().add(chckEditUser);
	}
}