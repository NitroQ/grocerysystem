import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class Inventory extends SQLConnect{

	JFrame frame;
	private String emp_id, type;
	private JTable table, tablehistory, tablelow;
	private InventoryAdd inv_add;
	private InventoryEdit edit_inv;
	private  String[] prodcolumns = {"SKU", "Product Name", "Stock", "Cost", "Price"};
	 private Object[][] proddata = {};
	  @SuppressWarnings("serial")
	private DefaultTableModel prodmodel = new DefaultTableModel(proddata, prodcolumns) {
		  @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
	  };
	  
		private  String[] historycolumns = {"Logs", "Time"};
		 private Object[][] historydata = {};
		  @SuppressWarnings("serial")
		private DefaultTableModel historymodel = new DefaultTableModel(historydata, historycolumns) {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		  };
		  
			private  String[] lowcolumns = {"Item",  "SKU"};
			 private Object[][] lowdata = {};
			  @SuppressWarnings("serial")
			private DefaultTableModel lowmodel = new DefaultTableModel(lowdata, lowcolumns) {
				  @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
			  };

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Inventory(String emp_id, String type) {
		this.emp_id = emp_id;
		this.type = type;
		initialize();
	}

	public void closeSubWindows() {
		if (edit_inv != null) {
			edit_inv.frame.dispose();
		}
		if (inv_add != null) {
			inv_add.frame.dispose();
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void updateTable() {
		   prodmodel.setRowCount(0);
		   lowmodel.setRowCount(0);
		   historymodel.setRowCount(0);
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory ORDER BY prod_name ASC");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	String prodname = rs.getString("prod_name");
		    	String sku = rs.getString("sku");
		    	String qty = rs.getString("qty");
		    	prodmodel.addRow(new Object[]{sku, prodname, qty, rs.getString("cost"), rs.getString("price")});
		    	
		    	if(Integer.parseInt(qty)<= 50) {
		    		lowmodel.addRow(new Object[] {
		    				prodname,
		    				sku
		    		});
		    	}
		    }
		    
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Logs WHERE loc = 'inventory' ORDER BY id DESC");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	String family = "";
		    	if(rs.getString("family").equals("insert")) {
		    		family = "Added";
		    	}else if(rs.getString("family").equals("update")) {
		    		family = "Changed";
		    	}else if(rs.getString("family").equals("disable")) {
		    		family = "Set No Stock";
		    	}
		    	String logging = family + " " + rs.getString("remarks");  
		    	historymodel.addRow(new Object[] { logging, rs.getString("log_date") });
		    }
      
                
    	 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
	}
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (1020, 700);
		updateTable();
		
		frame = new JFrame("GoShopper Inventory");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,1003, 661);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.getContentPane().setLayout(null);
		
		JLabel Window_Name = new JLabel("Inventory Dashboard");
		Window_Name.setBounds(34, 80, 425, 52);
		Window_Name.setFont(new Font("Segoe UI Variable", Font.BOLD, 39));
		frame.getContentPane().add(Window_Name);
		
		tablelow = new JTable(lowmodel);
		tablelow.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablelow.setRowHeight(20);
		tablelow.setBounds(100, 174, 288, 288);
		JScrollPane scrollPanelow = new JScrollPane(tablelow);
		scrollPanelow.setBounds(719, 469, 251, 166);
		frame.getContentPane().add(scrollPanelow);
		
		tablehistory = new JTable(historymodel);
		tablehistory.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablehistory.setRowHeight(20);
		tablehistory.setBounds(100, 174, 695, 288);
		JScrollPane scrollPanehistory = new JScrollPane(tablehistory);
		scrollPanehistory.setBounds(34, 469, 671, 166);
		frame.getContentPane().add(scrollPanehistory);
		
		table = new JTable(prodmodel);
		table.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.setRowHeight(30);
		table.setBounds(24, 174, 956, 329);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(34, 182, 936, 232);
		frame.getContentPane().add(scrollPane);
		

		JLabel Window_Name_1 = new JLabel("Stock History Logs");
		Window_Name_1.setFont(new Font("Segoe UI Variable", Font.PLAIN, 24));
		Window_Name_1.setBounds(34, 420, 288, 43);
		frame.getContentPane().add(Window_Name_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setBackground(new Color(220, 20, 60));
		btnAdd.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inv_add = new InventoryAdd();
				inv_add.frame.setVisible(true);
			}
		});
		btnAdd.setBounds(688, 133, 89, 30);
		frame.getContentPane().add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				if(row != -1) {
					edit_inv = new InventoryEdit(String.valueOf(table.getModel().getValueAt(row, 0)));
					edit_inv.frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "No Selected Item");
				}
				
			}
		});
		btnEdit.setBounds(784, 133, 89, 30);
		frame.getContentPane().add(btnEdit);
		
		JButton btnDelete = new JButton("No Stock");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
					if(table.getModel().getValueAt(row, 2).equals("0")) {
						JOptionPane.showMessageDialog(null, "Stock is already set to 0");
					}else if(row != -1) {
						try{
							String del = String.valueOf(table.getModel().getValueAt(row, 0)); 
						    con = DriverManager.getConnection(connectionUrl);
						    ps = con.prepareStatement("UPDATE Inventory SET qty = 0 WHERE sku = ?");
						    ps.setString(1, del);
	
						    int n = JOptionPane.showConfirmDialog(null, "Confirm Set No Stock?" , "WARNING", JOptionPane.YES_NO_OPTION);
	
						      if(n == JOptionPane.YES_OPTION) {
						          ps.executeUpdate();
						          String comment =   del + " : " + " sku now unavailable";
						           SQLConnect.createlog(SQLConnect.LogType.DISABLE, emp_id, "inventory", comment);
						      }
					          updateTable();
					          
					 	 }catch(HeadlessException | SQLException ex){
					 		 JOptionPane.showMessageDialog(null, ex );
					      }
					
				}else {
					JOptionPane.showMessageDialog(null, "No Selected Item");
				}
			}
		});
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setBackground(new Color(220, 20, 60));
		btnDelete.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnDelete.setBounds(881, 133, 89, 30);
		frame.getContentPane().add(btnDelete);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setForeground(new Color(255, 255, 255));
		btnRefresh.setBackground(new Color(220, 20, 60));
		btnRefresh.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnRefresh.setBounds(591, 133, 89, 30);
		frame.getContentPane().add(btnRefresh);
		
		JLabel Window_Name_2 = new JLabel("In Stock Items");
		Window_Name_2.setFont(new Font("Segoe UI Variable", Font.PLAIN, 24));
		Window_Name_2.setBounds(34, 129, 288, 43);
		frame.getContentPane().add(Window_Name_2);
		
		JButton admin_btn = new JButton("Admin");
	    admin_btn.setVisible(type.equals("admin")? true : false);
		admin_btn.setForeground(new Color(255, 255, 255));
		admin_btn.setBackground(new Color(0, 139, 139));
		admin_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		admin_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin window = new Admin(emp_id, type);
				window.frame.setVisible(true);
				closeSubWindows();
				frame.dispose();
			}
		});
		admin_btn.setBounds(630, 33, 109, 35);
		frame.getContentPane().add(admin_btn);
		
		JButton pos_btn = new JButton("Terminal");
		pos_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				POS pos = new POS(emp_id, type);
				pos.frame.setVisible(true);
				closeSubWindows();
				frame.dispose();
			}
		});
		pos_btn.setForeground(new Color(255, 255, 255));
		pos_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		pos_btn.setBackground(new Color(0, 139, 139));
		pos_btn.setBounds(751, 33, 109, 35);
		frame.getContentPane().add(pos_btn);
		
		JButton logout_btn = new JButton("Logout");
		logout_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to Log-Out of the System?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					Login log_in = new Login();
					log_in.frame.setVisible(true);
					closeSubWindows();
					frame.dispose();
				} else {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		logout_btn.setForeground(new Color(255, 255, 255));
		logout_btn.setBackground(new Color(0, 139, 139));
		logout_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		logout_btn.setBounds(870, 33, 109, 35);
		frame.getContentPane().add(logout_btn);
		
		JLabel Window_Name_3 = new JLabel("Low Stock");
		Window_Name_3.setFont(new Font("Segoe UI Variable", Font.PLAIN, 24));
		Window_Name_3.setBounds(719, 420, 168, 43);
		frame.getContentPane().add(Window_Name_3);
		
		JLabel GoShopperAdmin_Logo = new JLabel();
		Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdmin.png")).getImage();
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopperAdmin_Logo);
		
		JLabel GoShopper_BG = new JLabel();
		Image bg = new ImageIcon(this.getClass().getResource("/BGAdmin.png")).getImage();
		GoShopper_BG.setIcon(new ImageIcon(bg));
		GoShopper_BG.setBounds(0, 0, 1006, 685);
		frame.getContentPane().add(GoShopper_BG);
		
	}


class InventoryAdd {

	JFrame frame;
	private JTextField prodname;
	private JTextField prodsku;
	private JTextField prodqty;
	private JTextField prodprice;
	private JTextField prodcost;


	public InventoryAdd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (473, 367);
		
		frame = new JFrame("GoShopper Inventory Add");
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,473, 525);
        frame.setLocationRelativeTo(null);
        
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(24, 25, 211, 50);
		Image img = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel Window_Name = new JLabel("Add Product");
		Window_Name.setBounds(24, 86, 288, 43);
		Window_Name.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		frame.getContentPane().add(Window_Name);
		
		prodname = new JTextField();
		prodname.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodname.setBackground(new Color(245, 245, 245));
		prodname.setBounds(24, 165, 412, 35);
		frame.getContentPane().add(prodname);
		prodname.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1.setBounds(24, 133, 111, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		prodsku = new JTextField();
		prodsku.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodsku.setBackground(new Color(245, 245, 245));
		prodsku.setColumns(10);
		prodsku.setBounds(24, 243, 412, 35);
		prodsku.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
				e.consume();	        		
					}
			}
		});
		frame.getContentPane().add(prodsku);
		
		JLabel lblNewLabel_1_1 = new JLabel("Stock Keeping Unit (SKU)");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(24, 210, 233, 22);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		prodqty = new JTextField();
		prodqty.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodqty.setBackground(new Color(245, 245, 245));
		prodqty.setColumns(10);
		prodqty.setBounds(24, 321, 412, 35);
		prodqty.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
        		e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodqty);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1.setBounds(24, 288, 155, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		prodprice = new JTextField();
		prodprice.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodprice.setBackground(new Color(245, 245, 245));
		prodprice.setColumns(10);
		prodprice.setBounds(24, 399, 198, 35);
		prodprice.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || c== KeyEvent.VK_PERIOD)) {
        		e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodprice);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1_1.setBounds(24, 367, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		prodcost = new JTextField();
		prodcost.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodcost.setBackground(new Color(245, 245, 245));
		prodcost.setColumns(10);
		prodcost.setBounds(232, 399, 204, 35);
		prodcost.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || c== KeyEvent.VK_PERIOD)) {
        		e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodcost);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Cost");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1_1_1.setBounds(232, 367, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JButton add_btn = new JButton("Save");
		add_btn.setBackground(new Color(220, 20, 60));
		add_btn.setForeground(new Color(255, 255, 255));
		add_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		add_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{

					String Pname = prodname.getText();
					String Psku = prodsku.getText();
					String Pqty = prodqty.getText();
					String Pprice = prodprice.getText();
					String Pcost = prodcost.getText();
					
		             if(Pname.trim().equals("")) {
		            	 JOptionPane.showMessageDialog(null, "Product name is empty");
		             }
		             else if(Psku.trim().equals("")) {
		            	 JOptionPane.showMessageDialog(null, "SKU name is empty");
		             }
		             else if(Pqty.trim().equals("")) {
		            	 JOptionPane.showMessageDialog(null, "Quantity name is empty");
		             }
		             else if(Pprice.trim().equals("")) {
		            	 JOptionPane.showMessageDialog(null, "Price name is empty");
		             }
		             else if(Pcost.trim().equals("")) {
		            	 JOptionPane.showMessageDialog(null, "Cost name is empty");
		             }else {
		            	 
						String query = "INSERT INTO Inventory (sku, prod_name, qty, price, cost) VALUES (?,?,?,?,?)";
						con = DriverManager.getConnection(connectionUrl);
						ps = con.prepareStatement(query);
							ps.setString(1, prodsku.getText());
							ps.setString(2, prodname.getText());
							ps.setString(3, prodqty.getText());
							ps.setString(4, prodprice.getText());
							ps.setString(5, prodcost.getText());
							ps.executeUpdate();   
							JOptionPane.showMessageDialog(null, "Added");
							updateTable();
							frame.dispose();
					 }
		             
		             String comment =    prodsku.getText() + " | " + prodname.getText() + " |q" +prodqty.getText() + "|c"+prodcost.getText() + "|p" + prodprice.getText();
		             SQLConnect.createlog(SQLConnect.LogType.INSERT, emp_id, "inventory", comment);
		                
		    	 }catch(HeadlessException | SQLException ex){
		    		 JOptionPane.showMessageDialog(null, ex );
		         }
				
			}
		});
		add_btn.setBounds(29, 456, 109, 43);
		frame.getContentPane().add(add_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.setBackground(new Color(0, 139, 139));
		cancel_btn.setForeground(new Color(255, 255, 255));
		cancel_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		cancel_btn.setBounds(148, 456, 109, 43);
		cancel_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(cancel_btn);
		
	}
}

class InventoryEdit {

	JFrame frame;
	private JTextField prodname;
	private JTextField prodsku;
	private JTextField prodqty;
	private JTextField prodprice;
	private JTextField prodcost;
	private String prod_id, sku, prod_name, qty, price, cost;

	public InventoryEdit(String prod_id) {
		this.prod_id = prod_id;
		initialize();
	}

	public void find() {
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory WHERE sku = ?");
		    ps.setString(1, prod_id);
		    rs = ps.executeQuery();
		    while(rs.next()) {
			    sku = rs.getString("sku");
			    prod_name = rs.getString("prod_name");
			    qty = rs.getString("qty");
			    price = rs.getString("price");
			    cost = rs.getString("cost");
		    }
    	 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (473, 367);
		find();
		
		frame = new JFrame("GoShopper Inventory Edit");
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,473, 525);
        frame.setLocationRelativeTo(null);
        
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
    	Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Window_Name = new JLabel("Edit Product");
		Window_Name.setBounds(24, 86, 288, 43);
		Window_Name.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		frame.getContentPane().add(Window_Name);
		
		prodname = new JTextField(prod_name);
		prodname.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodname.setBackground(new Color(245, 245, 245));
		prodname.setBounds(24, 165, 412, 35);
		frame.getContentPane().add(prodname);
		prodname.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1.setBounds(24, 133, 106, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		prodsku = new JTextField(sku);
		prodsku.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodsku.setBackground(new Color(245, 245, 245));
		prodsku.setEditable(false);
		prodsku.setColumns(10);
		prodsku.setBounds(24, 243, 412, 35);
		frame.getContentPane().add(prodsku);
		
		JLabel lblNewLabel_1_1 = new JLabel("Stock Keeping Unit (SKU)");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(24, 211, 187, 22);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		prodqty = new JTextField(qty);
		prodqty.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodqty.setBackground(new Color(245, 245, 245));
		prodqty.setColumns(10);
		prodqty.setBounds(24, 321, 412, 35);
		prodqty.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
        				e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodqty);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1.setBounds(24, 289, 155, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		prodprice = new JTextField(price);
		prodprice.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodprice.setBackground(new Color(245, 245, 245));
		prodprice.setColumns(10);
		prodprice.setBounds(24, 399, 198, 35);
		prodprice.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || c== KeyEvent.VK_PERIOD)) {
        		e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodprice);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1_1.setBounds(24, 367, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		prodcost = new JTextField(cost);
		prodcost.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		prodcost.setBackground(new Color(245, 245, 245));
		prodcost.setColumns(10);
		prodcost.setBounds(232, 399, 204, 35);
		prodcost.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		char c = e.getKeyChar();
        		
        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || c== KeyEvent.VK_PERIOD)) {
        		e.consume();	        		
        			}
        	}
        });
		frame.getContentPane().add(prodcost);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Cost");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 16));
		lblNewLabel_1_1_1_1_1.setBounds(232, 367, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JButton add_btn = new JButton("Save");
		add_btn.setBackground(new Color(220, 20, 60));
		add_btn.setForeground(new Color(255, 255, 255));
		add_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		add_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					String Pname = prodname.getText();
					String Pqty = prodqty.getText();
					String Pprice = prodprice.getText();
					String Pcost = prodcost.getText();
					
					if(Pname.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Product name is empty");
					}
					else if(Pqty.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Quantity name is empty");
					}
					else if(Pprice.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Price name is empty");
					}
					else if(Pcost.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Cost name is empty");
					}else {

							String query = "UPDATE Inventory SET prod_name = ?, qty = ?, price = ?, cost = ? WHERE sku = ?";
							con = DriverManager.getConnection(connectionUrl);
							ps = con.prepareStatement(query);
							ps.setString(1, Pname);
							ps.setString(2, Pqty);
							ps.setString(3, Pprice);
							ps.setString(4, Pcost);
							ps.setString(5, prod_id);
							ps.executeUpdate();
							
							String comment = sku + "| ";
							if(!prodname.getText().equals(prod_name)) {
								comment += "name: " + prod_name + "-" + prodname.getText() + " ";
							}
							if(!prodqty.getText().equals(qty)) {
								comment += "qty: " + qty + "-" + prodqty.getText() + " ";
							}
							if(!prodcost.getText().equals(cost)) {
								comment += "cost: " + cost + "-" + prodcost.getText() + " ";
							}
							if(!prodprice.getText().equals(price)) {
								comment += "price: " + price + "-" + prodprice.getText() + " ";
							}
							
							SQLConnect.createlog(SQLConnect.LogType.UPDATE, emp_id, "inventory", comment);
							
							JOptionPane.showMessageDialog(null, "Updated");
							updateTable();
							frame.dispose();
					}
		                
		    	 }catch(HeadlessException | SQLException ex){
		    		 JOptionPane.showMessageDialog(null, ex );
		         }
				
			}
		});
		add_btn.setBounds(24, 456, 114, 43);
		frame.getContentPane().add(add_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.setBackground(new Color(0, 139, 139));
		cancel_btn.setForeground(new Color(255, 255, 255));
		cancel_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		cancel_btn.setBounds(148, 456, 109, 43);
		cancel_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(cancel_btn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.PLAIN, 16));
		Image img2 = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img2));
		lblNewLabel.setBounds(24, 25, 211, 50);
		frame.getContentPane().add(lblNewLabel);
		
	}
}


}

