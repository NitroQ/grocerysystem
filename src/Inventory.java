import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
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
import java.awt.Desktop;
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
	private JTable table;
	private  String[] prodcolumns = {"ID", "SKU", "Product Name", "Stock", "Cost", "Price"};
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


	/**
	 * Initialize the contents of the frame.
	 */
	public void updateTable() {
		   prodmodel.setRowCount(0);
		   lowmodel.setRowCount(0);
		   historymodel.setRowCount(0);
		try{
		    con = DriverManager.getConnection(connectionUrl2, username, password);
		    ps = con.prepareStatement("SELECT * FROM `llx_product` ORDER BY `label` ASC");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	String prodname = rs.getString("label");
		    	String sku = rs.getString("ref");
		    	Double qty = rs.getDouble("stock");
		    	prodmodel.addRow(new Object[]{rs.getString("rowid"),sku, prodname, rs.getDouble("stock"), rs.getDouble("cost_price"), rs.getDouble("price")});
		    	
		    	if(qty<= 50) {
		    		lowmodel.addRow(new Object[] {
		    				prodname,
		    				sku
		    		});
		    	}
		    }
		    
		    con = DriverManager.getConnection(connectionUrl, username, password);
		    ps = con.prepareStatement("SELECT * FROM `Logs` WHERE `loc` = 'inventory' ORDER BY `id` DESC");
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
		
		table = new JTable(prodmodel);
		table.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.setRowHeight(30);
		table.setBounds(24, 174, 956, 329);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(34, 182, 936, 444);
		frame.getContentPane().add(scrollPane);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setBackground(new Color(220, 20, 60));
		btnAdd.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
				        Desktop.getDesktop().browse(new URL("http://dolibarr.test/product/card.php?leftmenu=product&action=create&type=0").toURI());
				    } catch (Exception ez) {
				        ez.printStackTrace();
				    }
			}
		});
		btnAdd.setBounds(785, 141, 89, 30);
		frame.getContentPane().add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setBackground(new Color(220, 20, 60));
		btnEdit.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1) {
					String id = "http://dolibarr.test/product/card.php?id=" + table.getModel().getValueAt(row, 0).toString();
					 try {
					        Desktop.getDesktop().browse(new URL(id).toURI());
					    } catch (Exception ez) {
					        ez.printStackTrace();
					    }
				}else {
					JOptionPane.showMessageDialog(null, "No selected item.");
				}
				
			}
		});
		btnEdit.setBounds(881, 141, 89, 30);
		frame.getContentPane().add(btnEdit);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setForeground(new Color(255, 255, 255));
		btnRefresh.setBackground(new Color(220, 20, 60));
		btnRefresh.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnRefresh.setBounds(688, 141, 89, 30);
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
				"Are you sure you want to Logout of the System?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					Login log_in = new Login();
					log_in.frame.setVisible(true);
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



}




