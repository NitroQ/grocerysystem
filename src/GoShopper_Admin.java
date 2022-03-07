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

public class GoShopper_Admin {

	private JFrame frame;
	private JTable table_Sales;
	private JScrollPane scrollPane;
    private  String[] columns = {"Purchase ID", "Purchase Sales", "Date"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
     private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GoShopper_Admin window = new GoShopper_Admin();
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
	public GoShopper_Admin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1020, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("HRIS Records");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(204, 102, 102));
		btnNewButton.setFont(new Font("Roboto", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton LogOut = new JButton("Log-Out");
		LogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		LogOut.setForeground(Color.WHITE);
		LogOut.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		LogOut.setBackground(new Color(0, 139, 139));
		LogOut.setBounds(865, 34, 120, 35);
		frame.getContentPane().add(LogOut);
		
		JLabel lblNewLabel_1_1 = new JLabel("HRIS Records");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		lblNewLabel_1_1.setBounds(37, 533, 253, 52);
		frame.getContentPane().add(lblNewLabel_1_1);
		btnNewButton.setBounds(37, 595, 160, 35);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1_2 = new JLabel("Sales Records");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		lblNewLabel_1_2.setBounds(531, 83, 253, 52);
		frame.getContentPane().add(lblNewLabel_1_2);
		
	    table_Sales = new JTable(model);
	    table_Sales.setShowGrid(false);
	    table_Sales.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
	    table_Sales.setRowHeight(30);
	    table_Sales.setBounds(531, 145, 454, 487);
	    JScrollPane scrollPane_SalesRecords = new JScrollPane(table_Sales);
	    scrollPane_SalesRecords.setBounds(531, 145, 454, 487);
	    frame.getContentPane().add(scrollPane_SalesRecords);
		
		JLabel lblNewLabel_1 = new JLabel("Admin Dashboard");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		lblNewLabel_1.setBounds(37, 83, 331, 52);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel Profit_1 = new JPanel();
		Profit_1.setLayout(null);
		Profit_1.setBackground(new Color(204, 102, 102));
		Profit_1.setBounds(278, 337, 231, 182);
		frame.getContentPane().add(Profit_1);
		
		JLabel lblSales_2_1 = new JLabel("Transactions");
		lblSales_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSales_2_1.setForeground(Color.WHITE);
		lblSales_2_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblSales_2_1.setBounds(39, 119, 152, 39);
		Profit_1.add(lblSales_2_1);
		
		JLabel lblSales_2_1_1 = new JLabel("Total");
		lblSales_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSales_2_1_1.setForeground(Color.WHITE);
		lblSales_2_1_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblSales_2_1_1.setBounds(39, 92, 152, 39);
		Profit_1.add(lblSales_2_1_1);
		
		JPanel Profit = new JPanel();
		Profit.setLayout(null);
		Profit.setBackground(new Color(0, 153, 102));
		Profit.setBounds(37, 337, 231, 182);
		frame.getContentPane().add(Profit);
		
		JLabel lblSales_2 = new JLabel("Profit");
		lblSales_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSales_2.setForeground(Color.WHITE);
		lblSales_2.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblSales_2.setBounds(39, 119, 152, 39);
		Profit.add(lblSales_2);
		
		JPanel CostGoods = new JPanel();
		CostGoods.setLayout(null);
		CostGoods.setBackground(new Color(32, 178, 170));
		CostGoods.setBounds(278, 145, 231, 182);
		frame.getContentPane().add(CostGoods);
		
		JLabel lblNewLabel_2 = new JLabel("Cost of");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblNewLabel_2.setBounds(39, 92, 152, 39);
		CostGoods.add(lblNewLabel_2);
		
		JLabel lblSales_1 = new JLabel("Goods");
		lblSales_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSales_1.setForeground(Color.WHITE);
		lblSales_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblSales_1.setBounds(39, 119, 152, 39);
		CostGoods.add(lblSales_1);
		
		JPanel NetSales = new JPanel();
		NetSales.setBackground(new Color(244, 164, 96));
		NetSales.setBounds(37, 145, 231, 182);
		frame.getContentPane().add(NetSales);
		NetSales.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Total Net");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblNewLabel.setBounds(39, 92, 152, 39);
		NetSales.add(lblNewLabel);
		
		JLabel lblSales = new JLabel("Sales");
		lblSales.setHorizontalAlignment(SwingConstants.CENTER);
		lblSales.setForeground(Color.WHITE);
		lblSales.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		lblSales.setBounds(39, 119, 152, 39);
		NetSales.add(lblSales);
		
		JLabel GoShopperAdmin_Logo = new JLabel("New label");
		GoShopperAdmin_Logo.setIcon(new ImageIcon(GoShopper_Admin.class.getResource("/GoShopper_AdminAssets/LogoAdmin.png")));
		GoShopperAdmin_Logo.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopperAdmin_Logo);
		
		JLabel GoShopperAdmin_Logo_1 = new JLabel("New label");
		GoShopperAdmin_Logo_1.setIcon(new ImageIcon(GoShopper_Admin.class.getResource("/GoShopper_AdminAssets/BGAdmin.png")));
		GoShopperAdmin_Logo_1.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopperAdmin_Logo_1);
	}
}
