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
     private JTextField txtEmail;
     private JTextField textField;
     private JTextField txtAge;


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
		frame.setBounds(100, 100, 1024, 734);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAge.setBounds(31, 411, 293, 21);
		frame.getContentPane().add(lblAge);
		
		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(245, 245, 245));
		txtEmail.setBounds(28, 266, 294, 31);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblEmail.setBounds(28, 234, 293, 21);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblEmployee = new JLabel("Add Employee");
		lblEmployee.setForeground(Color.BLACK);
		lblEmployee.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		lblEmployee.setBounds(28, 85, 325, 35);
		frame.getContentPane().add(lblEmployee);
		
		JLabel lblFName = new JLabel("First Name");
		lblFName.setForeground(Color.BLACK);
		lblFName.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblFName.setBounds(28, 149, 325, 21);
		frame.getContentPane().add(lblFName);
		
		txtFName = new JTextField();
		txtFName.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFName.setBackground(new Color(245, 245, 245));
		txtFName.setBounds(28, 181, 294, 29);
		frame.getContentPane().add(txtFName);
		txtFName.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1.setBounds(363, 149, 325, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		txtLName = new JTextField();
		txtLName.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtLName.setColumns(10);
		txtLName.setBackground(new Color(245, 245, 245));
		txtLName.setBounds(363, 181, 294, 29);
		frame.getContentPane().add(txtLName);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setForeground(Color.BLACK);
		lblRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblRole.setBounds(28, 315, 325, 21);
		frame.getContentPane().add(lblRole);
		
		JComboBox cmdPosition = new JComboBox();
		cmdPosition.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmdPosition.setModel(new DefaultComboBoxModel(new String[] {"Merch", "Cashier", "Manager"}));
		cmdPosition.setBackground(new Color(245, 245, 245));
		cmdPosition.setBounds(28, 347, 294, 31);
		frame.getContentPane().add(cmdPosition);
		
		JButton btnEdit = new JButton("Save");
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Roboto", Font.BOLD, 14));
		btnEdit.setBackground(new Color(204, 102, 102));
		btnEdit.setBounds(34, 560, 78, 35);
		frame.getContentPane().add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Roboto", Font.BOLD, 14));
		btnCancel.setBackground(new Color(47, 79, 79));
		btnCancel.setBounds(122, 560, 120, 35);
		frame.getContentPane().add(btnCancel);
		Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdminSub.png")).getImage();
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblAddress.setBounds(363, 234, 294, 21);
		frame.getContentPane().add(lblAddress);
		
		textField = new JTextField();
		textField.setBackground(new Color(245, 245, 245));
		textField.setBounds(364, 267, 606, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblGender.setBounds(363, 315, 294, 21);
		frame.getContentPane().add(lblGender);
		
		JComboBox cmbGender = new JComboBox();
		cmbGender.setFont(new Font("Dialog", Font.PLAIN, 12));
		cmbGender.setModel(new DefaultComboBoxModel(new String[] {"Female", "Male"}));
		cmbGender.setBounds(363, 347, 294, 31);
		frame.getContentPane().add(cmbGender);
		
		JLabel GoShopperAdmin_Logo = new JLabel("New label");
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
		frame.getContentPane().add(GoShopperAdmin_Logo);
		
		txtAge = new JTextField();
		txtAge.setBackground(new Color(245, 245, 245));
		txtAge.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtAge.setBounds(31, 445, 291, 31);
		frame.getContentPane().add(txtAge);
		txtAge.setColumns(10);
	}
}