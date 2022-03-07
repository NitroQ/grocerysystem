import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
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

public class GoShopper_AdminEdit {

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
     private JTextField field_EmpName;
     private JTextField field_EmpNumber;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GoShopper_AdminEdit window = new GoShopper_AdminEdit();
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
	public GoShopper_AdminEdit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 367, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Employee");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		lblNewLabel_1.setBounds(28, 85, 325, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Employee Name");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(28, 119, 325, 29);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		field_EmpName = new JTextField();
		field_EmpName.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		field_EmpName.setBackground(new Color(245, 245, 245));
		field_EmpName.setBounds(28, 150, 294, 29);
		frame.getContentPane().add(field_EmpName);
		field_EmpName.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Employee Number");
		lblNewLabel_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1.setBounds(28, 185, 325, 29);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		field_EmpNumber = new JTextField();
		field_EmpNumber.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		field_EmpNumber.setColumns(10);
		field_EmpNumber.setBackground(new Color(245, 245, 245));
		field_EmpNumber.setBounds(28, 218, 294, 29);
		frame.getContentPane().add(field_EmpNumber);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Role");
		lblNewLabel_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1_1.setBounds(28, 252, 325, 29);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JComboBox combo_Role = new JComboBox();
		combo_Role.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		combo_Role.setBackground(new Color(245, 245, 245));
		combo_Role.setBounds(28, 280, 294, 29);
		frame.getContentPane().add(combo_Role);
		
		JButton btnEdit = new JButton("Save");
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setBounds(28, 330, 78, 35);
		frame.getContentPane().add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		btnCancel.setBackground(new Color(0, 139, 139));
		btnCancel.setBounds(116, 330, 120, 35);
		frame.getContentPane().add(btnCancel);
		
		JLabel GoShopperAdmin_Logo = new JLabel("New label");
		GoShopperAdmin_Logo.setIcon(new ImageIcon(GoShopper_AdminEdit.class.getResource("/GoShopper_AdminAssets/LogoAdminSub.png")));
		GoShopperAdmin_Logo.setBounds(-11, 0, 333, 393);
		frame.getContentPane().add(GoShopperAdmin_Logo);
	}
}
