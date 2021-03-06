import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class Admin extends SQLConnect {

	JFrame frame;
	private Admin_ViewSales view;
	private Double total_sales = 0.00, total_cost = 0.00, total_profit = 0.00;
	private int total_trans = 0;
	private String emp_id, type;
	private JTable table_Sales;
    private  String[] columns = {"Purchase ID", "Purchase Sales", "Date"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
     private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };

     
 	public void updateTable() {
		  model.setRowCount(0);
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Sales ORDER BY sale_id DESC");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	model.addRow(new Object[]{rs.getString("sale_id"),rs.getString("total"), rs.getString("sale_date")});
		    	total_trans ++;
		    	
		    	if (rs.getString("total") != null) {
		    		total_sales += Double.parseDouble(rs.getString("total"));
		    	}
	    		if(rs.getString("profit") != null){
	    			total_profit += Double.parseDouble(rs.getString("profit"));
	    		}
		    }
		    
           
		 }catch(HeadlessException | SQLException ex){
			 JOptionPane.showMessageDialog(null, ex );
		 }
		
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory WHERE qty <> 0");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	total_cost += Double.parseDouble(rs.getString("cost")) * Integer.parseInt(rs.getString("qty"));
		    }
		    
           
		 }catch(HeadlessException | SQLException ex){
			 JOptionPane.showMessageDialog(null, ex );
		 }
	}

	/**
	 * Create the application.
	 */
	public Admin(String emp_id, String type) {
		this.emp_id = emp_id;
		this.type = type;
		initialize();
	}
	public void closeSubWindow() {
		if(view != null)
			view.frame.dispose();
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
		
		JButton btnNewButton = new JButton("Employee Records");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(204, 102, 102));
		btnNewButton.setFont(new Font("Roboto", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee emp = new Employee(emp_id, type);
				emp.frame.setVisible(true);
				frame.dispose();
				closeSubWindow();
			}
			
		});
		
		JButton LogOut = new JButton("Logout");
		LogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to Logout of the System?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					Login log_in = new Login();
					log_in.frame.setVisible(true);
					frame.dispose();
					closeSubWindow();
				} else {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		LogOut.setForeground(Color.WHITE);
		LogOut.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		LogOut.setBackground(new Color(0, 139, 139));
		LogOut.setBounds(865, 34, 120, 35);
		frame.getContentPane().add(LogOut);
		
		JButton viewSales_btn = new JButton("View");
		viewSales_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_Sales.getSelectedRow();
				if(row != -1) {
					String id = table_Sales.getModel().getValueAt(row, 0).toString();
					view = new Admin_ViewSales(id);
					view.frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "No selected sale.");
				}
				
			}
		});
		viewSales_btn.setForeground(Color.WHITE);
		viewSales_btn.setFont(new Font("Dialog", Font.BOLD, 14));
		viewSales_btn.setBackground(new Color(204, 102, 102));
		viewSales_btn.setBounds(884, 96, 91, 35);
		frame.getContentPane().add(viewSales_btn);
		
		JButton inv_btn = new JButton("Inventory");
		inv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory inv = new Inventory(emp_id, type);
				inv.frame.setVisible(true);
				frame.dispose();
				closeSubWindow();
			}
		});
		inv_btn.setForeground(Color.WHITE);
		inv_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		inv_btn.setBackground(new Color(0, 139, 139));
		inv_btn.setBounds(625, 34, 109, 35);
		frame.getContentPane().add(inv_btn);
		
		JButton pos_btn = new JButton("Terminal");
		pos_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				POS pos = new POS(emp_id, type);
				pos.frame.setVisible(true);
				frame.dispose();
				closeSubWindow();
			}
		});
		pos_btn.setForeground(Color.WHITE);
		pos_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		pos_btn.setBackground(new Color(0, 139, 139));
		pos_btn.setBounds(746, 34, 109, 35);
		frame.getContentPane().add(pos_btn);
		
		JLabel lblNewLabel_1_1 = new JLabel("Employee Records");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		lblNewLabel_1_1.setBounds(37, 533, 352, 52);
		frame.getContentPane().add(lblNewLabel_1_1);
		btnNewButton.setBounds(37, 595, 181, 35);
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
		
		JLabel lblNewLabel_3_3 = new JLabel(String.valueOf(total_trans));
		lblNewLabel_3_3.setForeground(Color.WHITE);
		lblNewLabel_3_3.setFont(new Font("Segoe UI Variable", Font.PLAIN, 35));
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_3.setBounds(21, 22, 184, 67);
		Profit_1.add(lblNewLabel_3_3);
		
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
		
		JLabel profitization = new JLabel(String.valueOf(total_profit));
		profitization.setForeground(Color.WHITE);
		profitization.setFont(new Font("Segoe UI Variable", Font.PLAIN, 35));
		profitization.setHorizontalAlignment(SwingConstants.CENTER);
		profitization.setBounds(24, 42, 184, 67);
		Profit.add(profitization);
		
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
		
		JLabel lblNewLabel_3_1 = new JLabel(String.valueOf(total_cost));
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Segoe UI Variable", Font.PLAIN, 35));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setBounds(24, 23, 184, 67);
		CostGoods.add(lblNewLabel_3_1);
		
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
		
		JLabel lblNewLabel_3 = new JLabel(total_sales.toString());
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Segoe UI Variable", Font.PLAIN, 35));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(23, 24, 184, 67);
		NetSales.add(lblNewLabel_3);
		
		
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
	public class Admin_ViewSales{

		JFrame frame;
		private String sale_id, emp_id, payment, discount, total, profit, date;
	    private  String[] columns = {"SKU", "Qty", "Item Total"};
	    private Object[][] data = {};
	     @SuppressWarnings("serial")
	     private DefaultTableModel model_sales = new DefaultTableModel(data, columns) {
	         @Override
	           public boolean isCellEditable(int row, int column) {
	               return false;
	           }
	     };

		public Admin_ViewSales(String sale_id) {
			this.sale_id = sale_id;
			initialize();
		}
		
		public void updateTable() {
			   model_sales.setRowCount(0);
			try{
			    con = DriverManager.getConnection(connectionUrl);
			    ps = con.prepareStatement("SELECT * FROM Sales WHERE sale_id = ?");
			    ps.setString(1, sale_id);
			    rs = ps.executeQuery();
			    while(rs.next()) {
			    	emp_id = rs.getString("emp_id");
			    	payment = rs.getString("payment");
			    	discount = rs.getString("discount");
			    	total = rs.getString("total");
			    	profit = rs.getString("profit");
			    	date = rs.getString("sale_date");
			    }
			    
			    ps = con.prepareStatement("SELECT * FROM Sold_Items WHERE sale_id = ?");
			    ps.setString(1, sale_id);
			    rs = ps.executeQuery();
			    while(rs.next()) {
			    	model_sales.addRow(new Object[] { rs.getString("sku"), rs.getString("qty"), rs.getString("item_total")});
			    }

	             
	 	 }catch(HeadlessException | SQLException ex){
	 		 JOptionPane.showMessageDialog(null, ex );
	      }
		}

		private void initialize() {
			updateTable();
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
			
			JButton Close_btn = new JButton("Close");
			Close_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			Close_btn.setForeground(Color.WHITE);
			Close_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
			Close_btn.setBackground(new Color(0, 139, 139));
			Close_btn.setBounds(740, 33, 120, 35);
			frame.getContentPane().add(Close_btn);
			
			
			JTable table_Sale = new JTable(model_sales);
			table_Sale.setFont(new Font("Roboto", Font.PLAIN, 17));
			table_Sale.setBackground(Color.WHITE);
			table_Sale.setBorder(null);
			table_Sale.setRowHeight(30);
			table_Sale.setBounds(368, 91, 492, 383);
			JScrollPane scrollPane_SalesRecords = new JScrollPane(table_Sale);
			scrollPane_SalesRecords.setBounds(394, 84, 466, 392);
			frame.getContentPane().add(scrollPane_SalesRecords);
			
			JLabel lblNewLabel_1 = new JLabel("Sale #" + sale_id);
			lblNewLabel_1.setForeground(Color.BLACK);
			lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
			lblNewLabel_1.setBounds(33, 28, 331, 52);
			frame.getContentPane().add(lblNewLabel_1);
			
			JLabel emp_serv = new JLabel("Served by: " + emp_id);
			emp_serv.setFont(new Font("Segoe UI Variable", Font.BOLD, 12));
			emp_serv.setBounds(33, 112, 198, 28);
			frame.getContentPane().add(emp_serv);
			
			JLabel payment_serv = new JLabel("Payment Method: " + payment);
			payment_serv.setFont(new Font("Segoe UI Variable", Font.BOLD, 12));
			payment_serv.setBounds(33, 151, 198, 28);
			frame.getContentPane().add(payment_serv);
			
			JLabel discount_serv = new JLabel("Discount Availed: " + (discount == null ? "No Discount" : discount) );
			discount_serv.setFont(new Font("Segoe UI Variable", Font.BOLD, 12));
			discount_serv.setBounds(33, 185, 198, 28);
			frame.getContentPane().add(discount_serv);
			
			JLabel date_serv = new JLabel("Date: " + date);
			date_serv.setFont(new Font("Segoe UI Variable", Font.BOLD, 12));
			date_serv.setBounds(33, 224, 198, 28);
			frame.getContentPane().add(date_serv);
			
			JLabel sale_total_serv = new JLabel("Total: " );
			sale_total_serv.setFont(new Font("Segoe UI Variable", Font.BOLD, 12));
			sale_total_serv.setBounds(33, 290, 198, 28);
			frame.getContentPane().add(sale_total_serv);
			
			JLabel sale_total = new JLabel(total);
			sale_total.setFont(new Font("Segoe UI Variable", Font.BOLD, 25));
			sale_total.setBounds(33, 328, 246, 52);
			frame.getContentPane().add(sale_total);
			
			
			
		}
	}
}