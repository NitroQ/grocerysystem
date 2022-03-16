import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class POS extends SQLConnect{

	JFrame frame;
	private POSSearch search_window;
	private JTable table;
    private JButton btn_Cancel,btn_Pay,btnNewButton_7, btnNewButton_8,btn_Print,btn_Void;
    private JTextField txt_Qt,skuinput,txt_Cash;
    private JLabel lbl_Skugoinput, lbl_Qt, GoShopperAdmin_Logo,lbl_Discount_2,lbl_Discount_1,lbl_Discount_3,GoShopper_BG,lbl_Discount_4,txt_change, lbl_Discount_6, lbl_Discount_7,lbl_Discount_8,lbl_Discount_9,txtView_Subtotal,txtView_Discount,txtView_VAT, txtView_GrandTotal,viewitem,viewprice;
	private JComboBox jcb_GoDiscount, jcb_PaymentMethod;
    JPanel panel;
    private String emp_id, type; 
    private  Double grandtotal;
	private  String[] columns = {"SKU", "Item", "Quantity", "Price"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
    private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };

	public POS(String emp_id, String type) {
		this.emp_id = emp_id;
		this.type = type;
		initialize();
	}
	
	public void addSKU(String sku, String qty) {
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory WHERE sku = ? AND qty <> 0");
		    ps.setString(1, sku);
		    rs = ps.executeQuery();
	
			   while(rs.next()) {
				   String prodname = rs.getString("prod_name");
				   String value = rs.getString("price");
				   String total = String.format("%.2f",Double.parseDouble(qty) * Double.parseDouble(value));
   			  	model.addRow(new Object [] {rs.getString("sku"),prodname, qty , total });
       			viewitem.setText(prodname);
       			viewprice.setText(value);
       			updateTotal();
       			skuinput.setText("");
       			txt_Qt.setText("");
			   }
			 
    	 }catch(HeadlessException | SQLException ex){
    		 JOptionPane.showMessageDialog(null, ex );
         }
	}
	
	public void updateTotal() {
		Double subtotal= 0.00, tax, nettotal, discount = 0.00;
		for (int count = 0; count < model.getRowCount(); count++){
			 subtotal +=  Double.parseDouble(model.getValueAt(count, 3).toString());
			}
		tax =  subtotal * 0.12; 
		nettotal = subtotal + tax;

		if(jcb_GoDiscount.getSelectedItem().equals("PWD")) {
			discount = nettotal * 0.20;
		}else if(jcb_GoDiscount.getSelectedItem().equals("Senior")) {
			discount = nettotal * 0.20;
		}else if(jcb_GoDiscount.getSelectedItem().equals("Member")) {
			discount = nettotal * 0.10;
		}
		
		grandtotal = nettotal - discount;
		
		txtView_Discount.setText("-"+ String.format("%.2f", discount));
		txtView_Subtotal.setText(String.format("%.2f", subtotal));
		txtView_VAT.setText(String.format("%.2f",tax));
		txtView_GrandTotal.setText(String.format("%.2f", grandtotal));
	}

	public void closeSubWindow() {
		if(search_window != null)
			search_window.frame.dispose();
	}
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension (1020, 700);
        
        frame = new JFrame("GoShopper POS");
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,1003, 661);
        frame.setLocationRelativeTo(null);   
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
        frame.setIconImage(icon);
        frame.getContentPane().setLayout(null);
        
        JPanel PaymentDetails = new JPanel();
        PaymentDetails.setBackground(new Color(70, 130, 180));
        PaymentDetails.setBounds(34, 89, 315, 142);
        frame.getContentPane().add(PaymentDetails);
        PaymentDetails.setLayout(null);
        
        lbl_Discount_6 = new JLabel("Subtotal:");
        lbl_Discount_6.setForeground(Color.WHITE);
        lbl_Discount_6.setFont(new Font("Segoe UI Variable", Font.BOLD | Font.ITALIC, 20));
        lbl_Discount_6.setBounds(20, 10, 96, 27);
        PaymentDetails.add(lbl_Discount_6);
        
        lbl_Discount_7 = new JLabel("Discount:");
        lbl_Discount_7.setForeground(Color.WHITE);
        lbl_Discount_7.setFont(new Font("Segoe UI Variable", Font.BOLD | Font.ITALIC, 20));
        lbl_Discount_7.setBounds(20, 40, 96, 27);
        PaymentDetails.add(lbl_Discount_7);
        
        lbl_Discount_8 = new JLabel("VAT (12%):");
        lbl_Discount_8.setForeground(Color.WHITE);
        lbl_Discount_8.setFont(new Font("Segoe UI Variable", Font.BOLD | Font.ITALIC, 20));
        lbl_Discount_8.setBounds(20, 70, 112, 27);
        PaymentDetails.add(lbl_Discount_8);
        
        lbl_Discount_9 = new JLabel("Grand Total:");
        lbl_Discount_9.setForeground(Color.WHITE);
        lbl_Discount_9.setFont(new Font("Segoe UI Variable", Font.BOLD | Font.ITALIC, 20));
        lbl_Discount_9.setBounds(20, 100, 128, 27);
        PaymentDetails.add(lbl_Discount_9);
        
        txtView_Subtotal = new JLabel("");
        txtView_Subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_Subtotal.setForeground(Color.WHITE);
        txtView_Subtotal.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_Subtotal.setBounds(182, 10, 112, 27);
        PaymentDetails.add(txtView_Subtotal);
        
        txtView_Discount = new JLabel("");
        txtView_Discount.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_Discount.setForeground(Color.WHITE);
        txtView_Discount.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_Discount.setBounds(182, 40, 112, 27);
        PaymentDetails.add(txtView_Discount);
        
        txtView_VAT = new JLabel("");
        txtView_VAT.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_VAT.setForeground(Color.WHITE);
        txtView_VAT.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_VAT.setBounds(182, 70, 112, 27);
        PaymentDetails.add(txtView_VAT);
        
        txtView_GrandTotal = new JLabel("");
        txtView_GrandTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_GrandTotal.setForeground(Color.WHITE);
        txtView_GrandTotal.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_GrandTotal.setBounds(182, 100, 112, 27);
        PaymentDetails.add(txtView_GrandTotal);
        
        JPanel PaymentDetails_1 = new JPanel();
        PaymentDetails_1.setBackground(new Color(70, 130, 180));
        PaymentDetails_1.setBounds(359, 89, 315, 142);
        frame.getContentPane().add(PaymentDetails_1);
        PaymentDetails_1.setLayout(null);
        
        viewitem = new JLabel("");
        viewitem.setForeground(Color.WHITE);
        viewitem.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        viewitem.setBounds(20, 23, 285, 27);
        PaymentDetails_1.add(viewitem);
        
        viewprice = new JLabel("");
        viewprice.setForeground(Color.WHITE);
        viewprice.setFont(new Font("Segoe UI Variable", Font.BOLD, 59));
        viewprice.setBounds(20, 50, 285, 75);
        PaymentDetails_1.add(viewprice);
        
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
        table.setBounds(10, 11, 372, 233);
        
        JScrollPane JSB_Purchase = new JScrollPane(table);
        JSB_Purchase.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JSB_Purchase.setBounds(34, 273, 639, 283);
        frame.getContentPane().add(JSB_Purchase);
        
        btn_Cancel = new JButton("Cancel");
        btn_Cancel.setForeground(new Color(255, 255, 255));
        btn_Cancel.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Cancel.setBackground(new Color(220, 20, 60));
        btn_Cancel.setBounds(34, 571, 180, 63);
        frame.getContentPane().add(btn_Cancel);
        
        btn_Pay = new JButton("Pay");
        btn_Pay.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String sale_id = "";
        	
    				try{
    					if(model.getRowCount() == 0) {
    						JOptionPane.showMessageDialog(null, "Add an Item");
    					}else if(txt_Cash.getText().trim().equals("") || txt_Cash.getText().equals(null) ) {
    						JOptionPane.showMessageDialog(null, "Not Paid!");
    					}else if(Double.parseDouble(txt_change.getText()) < 0 ) {
    						JOptionPane.showMessageDialog(null, "Change cannot be negative!");
    					}else {
    					    con = DriverManager.getConnection(connectionUrl);
        				    ps = con.prepareStatement("INSERT INTO Sales (emp_id, payment, discount, total, payment_amt, change) VALUES (?,?,?,?,?,?); SELECT @@IDENTITY AS 'identity';");
        		             ps.setString(1, emp_id);
        		             ps.setString(2, jcb_PaymentMethod.getSelectedItem().toString().toLowerCase());
        		             ps.setString(3, jcb_GoDiscount.getSelectedItem().equals("No Discount") ? null : jcb_GoDiscount.getSelectedItem().toString().toLowerCase());
        		             ps.setString(4, txtView_GrandTotal.getText().toString());
        		             ps.setString(5, txt_Cash.getText());
        		             ps.setString(6, txt_change.getText());
        		             rs = ps.executeQuery();
        		             while(rs.next()) {
        		            	 sale_id = rs.getString("identity");
        		             }
    	            	 	for (int count = 0; count < model.getRowCount(); count++){
    	            	 		String item_sku = model.getValueAt(count, 0).toString();
    	            	 		String item_qty = model.getValueAt(count, 2).toString();
    	            	 		String inv_qty = "";
            				    ps = con.prepareStatement("INSERT INTO Sold_Items (sale_id, sku, qty, item_total) VALUES (?,?,?,?)");
            				     ps.setString(1, sale_id);
            		             ps.setString(2, item_sku);
            		             ps.setString(3, item_qty);
            		             ps.setString(4, model.getValueAt(count, 3).toString());
            		             ps.executeUpdate();
            		             
            		             ps = con.prepareStatement("SELECT * FROM Inventory WHERE sku = ?");
            					    ps.setString(1, item_sku);
            					    rs = ps.executeQuery();
            					    while(rs.next()) {
            					    	inv_qty = rs.getString("qty");
            					    }
            					   int new_sku = Integer.parseInt(inv_qty) - Integer.parseInt(item_qty);
            					 ps = con.prepareStatement("UPDATE Inventory SET qty = ? WHERE sku = ? ");
           			             ps.setString(1, String.valueOf(new_sku));
           			             ps.setString(2, item_sku);
           			             ps.executeUpdate();

            		             JOptionPane.showMessageDialog(null, "Transaction Complete.");
        		   			}
    					}
//    		             updateTable();
    		             
   		            
    		                
    		    	 }catch(HeadlessException | SQLException ex){
    		    		 JOptionPane.showMessageDialog(null, ex );
    		         }

        	}
        });
        btn_Pay.setBackground(new Color(0, 139, 139));
        btn_Pay.setForeground(new Color(255, 255, 255));
        btn_Pay.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Pay.setBounds(639, 571, 332, 63);
        frame.getContentPane().add(btn_Pay);
        
        btnNewButton_7 = new JButton("Search SKU");
        btnNewButton_7.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		search_window = new POSSearch();
        		search_window.frame.setVisible(true);
        	}
        });
        btnNewButton_7.setBackground(new Color(0, 139, 139));
        btnNewButton_7.setForeground(new Color(255, 255, 255));
        btnNewButton_7.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
        btnNewButton_7.setBounds(839, 182, 132, 35);
        frame.getContentPane().add(btnNewButton_7);
        
        btnNewButton_8 = new JButton("Add SKU");
        btnNewButton_8.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String sku_add = skuinput.getText();
        		addSKU(sku_add, txt_Qt.getText().equals("") ? "1" : txt_Qt.getText());
        	}
        });
        btnNewButton_8.setForeground(new Color(255, 255, 255));
        btnNewButton_8.setBackground(new Color(220, 20, 60));
        btnNewButton_8.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
        btnNewButton_8.setBounds(688, 182, 146, 35);
        frame.getContentPane().add(btnNewButton_8);
        
        txt_Qt = new JTextField();
        txt_Qt.setBackground(new Color(245, 245, 245));
        txt_Qt.setColumns(10);
        txt_Qt.setBounds(883, 137, 88, 35);
        frame.getContentPane().add(txt_Qt);
        
        btn_Print = new JButton("Print");
        btn_Print.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 
        		
        	}
        });
        btn_Print.setForeground(new Color(255, 255, 255));
        btn_Print.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Print.setBackground(new Color(220, 20, 60));
        btn_Print.setBounds(224, 571, 180, 63);
        frame.getContentPane().add(btn_Print);
        
        btn_Void = new JButton("Void");
        btn_Void.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    int n = JOptionPane.showConfirmDialog(null, "Confirm Void?" , "WARNING", JOptionPane.YES_NO_OPTION);

			      if(n == JOptionPane.YES_OPTION) {
			    		int del = table.getSelectedRow();
		        		model.removeRow(del);
		        		updateTotal();
		        		viewitem.setText("");
		        		viewprice.setText("");
			      }

        	}
        });
        btn_Void.setBackground(new Color(220, 20, 60));
        btn_Void.setForeground(new Color(255, 255, 255));
        btn_Void.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Void.setBounds(411, 571, 218, 63);
        frame.getContentPane().add(btn_Void);
        
        skuinput = new JTextField();
        skuinput.setBackground(new Color(245, 245, 245));
        skuinput.setColumns(10);
        skuinput.setBounds(688, 137, 185, 35);
        frame.getContentPane().add(skuinput);
        
       jcb_GoDiscount = new JComboBox();
       jcb_GoDiscount.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		updateTotal();
       	}
       });
        jcb_GoDiscount.setBackground(new Color(255, 255, 255));
        jcb_GoDiscount.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
        jcb_GoDiscount.setModel(new DefaultComboBoxModel(new String[] {"No Discount", "PWD", "Senior", "Member"}));
        jcb_GoDiscount.setBounds(688, 273, 283, 35);
        frame.getContentPane().add(jcb_GoDiscount);
        
        txt_Cash = new JTextField();    
        txt_Cash.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		if(!txt_Cash.getText().equals("")) {
        			Double cash = Double.parseDouble(txt_Cash.getText());
            		Double change = cash - Double.parseDouble(txtView_GrandTotal.getText());
            		txt_change.setText(String.format("%.2f", change));	
        		}
        	}
        });
        txt_Cash.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txt_Cash.setColumns(10);
        txt_Cash.setBounds(688, 437, 283, 37);
        frame.getContentPane().add(txt_Cash);
        
        jcb_PaymentMethod = new JComboBox();
        jcb_PaymentMethod.setBackground(new Color(245, 245, 245));
        jcb_PaymentMethod.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
        jcb_PaymentMethod.setModel(new DefaultComboBoxModel(new String[] {"CASH", "VISA", "MASTER CARD"}));
        jcb_PaymentMethod.setBounds(688, 355, 283, 35);
        frame.getContentPane().add(jcb_PaymentMethod);
        
        JLabel lbl_Discount = new JLabel("Discount");
        lbl_Discount.setFont(new Font("Segoe UI Variable", Font.ITALIC, 20));
        lbl_Discount.setBounds(688, 241, 132, 22);
        frame.getContentPane().add(lbl_Discount);
        
        lbl_Skugoinput = new JLabel("SKU GoInput");
        lbl_Skugoinput.setFont(new Font("Segoe UI Variable", Font.BOLD, 24));
        lbl_Skugoinput.setBounds(687, 93, 199, 35);
        frame.getContentPane().add(lbl_Skugoinput);
        
        lbl_Qt = new JLabel("Qt.");
        lbl_Qt.setFont(new Font("Segoe UI Variable", Font.BOLD, 24));
        lbl_Qt.setBounds(883, 93, 90, 35);
        frame.getContentPane().add(lbl_Qt);
        
        JButton admin_btn = new JButton("Admin");
        admin_btn.setVisible(type.equals("admin")? true : false);
        admin_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Admin window = new Admin(emp_id, type);
				window.frame.setVisible(true);
				closeSubWindow();
				frame.dispose();
        	}
        });
        admin_btn.setForeground(Color.WHITE);
        admin_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        admin_btn.setBackground(new Color(0, 139, 139));
        admin_btn.setBounds(632, 30, 109, 35);
        frame.getContentPane().add(admin_btn);
        
        JButton inv_btn = new JButton("Inventory");
        inv_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Inventory inv = new Inventory(emp_id, type);
				inv.frame.setVisible(true);
				closeSubWindow();
				frame.dispose();
        	}
        });
        inv_btn.setForeground(Color.WHITE);
        inv_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        inv_btn.setBackground(new Color(0, 139, 139));
        inv_btn.setBounds(753, 30, 109, 35);
        frame.getContentPane().add(inv_btn);
        
        JButton logout_btn = new JButton("Logout");
        logout_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
              
                int confirmed = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to Log-Out of the System?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					Login log_in = new Login();
					log_in.frame.setVisible(true);
					closeSubWindow();
					frame.dispose();
				} else {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
        	}
        });
        logout_btn.setForeground(Color.WHITE);
        logout_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        logout_btn.setBackground(new Color(0, 139, 139));
        logout_btn.setBounds(872, 30, 109, 35);
        frame.getContentPane().add(logout_btn);
        
        lbl_Discount_2 = new JLabel("Mode of Payment");
        lbl_Discount_2.setFont(new Font("Segoe UI Variable", Font.ITALIC, 20));
        lbl_Discount_2.setBounds(688, 323, 218, 22);
        frame.getContentPane().add(lbl_Discount_2);
        
        lbl_Discount_1 = new JLabel("Payment Value");
        lbl_Discount_1.setFont(new Font("Segoe UI Variable", Font.ITALIC, 20));
        lbl_Discount_1.setBounds(688, 405, 218, 22);
        frame.getContentPane().add(lbl_Discount_1);
        
        panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBounds(688, 484, 283, 72);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        lbl_Discount_4 = new JLabel("Change:");
        lbl_Discount_4.setForeground(new Color(255, 255, 255));
        lbl_Discount_4.setBounds(10, 35, 107, 27);
        lbl_Discount_4.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
        panel.add(lbl_Discount_4);
        
        txt_change = new JLabel("");
        txt_change.setHorizontalAlignment(SwingConstants.RIGHT);
        txt_change.setForeground(Color.WHITE);
        txt_change.setFont(new Font("Segoe UI Variable", Font.PLAIN, 26));
        txt_change.setBounds(108, 35, 165, 27);
        panel.add(txt_change);
        
        lbl_Discount_3 = new JLabel("Purchase Details");
        lbl_Discount_3.setFont(new Font("Segoe UI Variable", Font.BOLD | Font.ITALIC, 20));
        lbl_Discount_3.setBounds(34, 241, 210, 22);
        frame.getContentPane().add(lbl_Discount_3);
        
        GoShopperAdmin_Logo = new JLabel();
        Image adminlogo = new ImageIcon(this.getClass().getResource("/LogoAdmin.png")).getImage();
		GoShopperAdmin_Logo.setIcon(new ImageIcon(adminlogo));
		GoShopperAdmin_Logo.setBounds(0, 0, 1006, 685);
        frame.getContentPane().add(GoShopperAdmin_Logo);
        
        GoShopper_BG = new JLabel();
		Image bg = new ImageIcon(this.getClass().getResource("/BGAdmin.png")).getImage();
		GoShopper_BG.setIcon(new ImageIcon(bg));
		GoShopper_BG.setBounds(0, 0, 1006, 685);
        frame.getContentPane().add(GoShopper_BG);
        
	}
	public class POSSearch {

		JFrame frame;
		private  String[] columns = {"SKU", "Item", "Price"};
	    private Object[][] data = {};
	     @SuppressWarnings("serial")
	   private DefaultTableModel inv_model = new DefaultTableModel(data, columns) {
	         @Override
	           public boolean isCellEditable(int row, int column) {
	               return false;
	           }
	     };
	     private JTextField txtsearch, searchqty;
	     private JComboBox chooseSearch;
		
		
		public void updateTable() {
			 inv_model.setRowCount(0);
			try{
			    con = DriverManager.getConnection(connectionUrl);
			    ps = con.prepareStatement("SELECT * FROM Inventory WHERE qty <> 0");
			    rs = ps.executeQuery();
			    while(rs.next()) {
			    	inv_model.addRow(new Object[]{rs.getString("sku"),rs.getString("prod_name"), rs.getString("price")});
			    }
	             
	 	 }catch(HeadlessException | SQLException ex){
	 		 JOptionPane.showMessageDialog(null, ex );
	      }
		}
		/**
		 * Create the application.
		 */
		public POSSearch() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
	        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension frameSize = new Dimension (647, 460);
			updateTable();
	        
	        frame = new JFrame("GoShopper POS Search");
	        frame.setResizable(false);
	        frame.getContentPane().setBackground(Color.WHITE);
	        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,634, 419);
	        frame.setLocationRelativeTo(null);
	        
	        frame.setUndecorated(true);
	        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
	        
	        Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
	        frame.setIconImage(icon);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.getContentPane().setLayout(null); 
	        
	        JTable table = new JTable(inv_model);
	        table.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
	        table.setBounds(10, 11, 372, 233);
	        
	        JScrollPane jspItem = new JScrollPane(table);
	        jspItem.setBounds(30, 172, 573, 169);
	        frame.getContentPane().add(jspItem);
	        
	        JButton btnBack = new JButton("Close");
	        btnBack.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		frame.dispose();
	        	}
	        });
	        btnBack.setBackground(new Color(0, 139, 139));
	        btnBack.setForeground(new Color(255, 255, 255));
	        btnBack.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
	        btnBack.setBounds(483, 43, 120, 35);
	        frame.getContentPane().add(btnBack);
	        
	        txtsearch = new JTextField();
	        txtsearch.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyReleased(KeyEvent e) {
	        		if(chooseSearch.getSelectedItem().equals("Search SKU")) {
	        			inv_model.setRowCount(0);
	        				try{
	        					String searchSKU = "SELECT * FROM Inventory WHERE qty <> 0 AND sku LIKE '%" + txtsearch.getText() +"%'";
	        				    con = DriverManager.getConnection(connectionUrl);
	        				    ps = con.prepareStatement(searchSKU);
	        				    rs = ps.executeQuery();
	        				    while(rs.next()) {
	        				    	inv_model.addRow(new Object[]{rs.getString("sku"),rs.getString("prod_name"), rs.getString("price")});
	        				    }
	        		             
	        		 	 }catch(HeadlessException | SQLException ex){
	        		 		 JOptionPane.showMessageDialog(null, ex );
	        		      }
	        		}if(chooseSearch.getSelectedItem().equals("Search Product")) {
	        			inv_model.setRowCount(0);
	      				try{
	      					String searchSKU = "SELECT * FROM Inventory WHERE qty <> 0 AND prod_name LIKE '%" + txtsearch.getText() +"%'";
	      				    con = DriverManager.getConnection(connectionUrl);
	      				    ps = con.prepareStatement(searchSKU);
	      				    rs = ps.executeQuery();
	      				    while(rs.next()) {
	      				    	inv_model.addRow(new Object[]{rs.getString("sku"),rs.getString("prod_name"), rs.getString("price")});
	      				    }
	      		             
	      		 	 }catch(HeadlessException | SQLException ex){
	      		 		 JOptionPane.showMessageDialog(null, ex );
	      		      }
		      		}else if(txtsearch.getText().trim().equals("")){
		        			updateTable();
		        		}
	        	}
	        });
	        txtsearch.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
	        txtsearch.setBackground(new Color(245, 245, 245));
	        txtsearch.setBounds(30, 127, 416, 35);
	        frame.getContentPane().add(txtsearch);
	        txtsearch.setColumns(10);
	        
	        JButton btnAdditem = new JButton("Add Item");
	        btnAdditem.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		int itemrow = table.getSelectedRow();
	        		addSKU(inv_model.getValueAt(itemrow, 0).toString(), searchqty.getText().equals("") ? "1" : searchqty.getText());
	        		frame.dispose();
	        	}
	        });
	        btnAdditem.setForeground(new Color(255, 255, 255));
	        btnAdditem.setBackground(new Color(220, 20, 60));
	        btnAdditem.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
	        btnAdditem.setBounds(456, 351, 147, 35);
	        frame.getContentPane().add(btnAdditem);
	        
	        searchqty = new JTextField();
	        searchqty.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyTyped(KeyEvent e) {
	        		char c = e.getKeyChar();
	        		
	        		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
	        		e.consume();	        		
	        			}
	        	}
	        });
	        searchqty.setBackground(new Color(245, 245, 245));
	        searchqty.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
	        searchqty.setColumns(10);
	        searchqty.setBounds(136, 351, 312, 35);
	        frame.getContentPane().add(searchqty);
	        
	        JLabel lblQuantity = new JLabel("Quantity:");
	        lblQuantity.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
	        lblQuantity.setBounds(30, 351, 106, 35);
	        frame.getContentPane().add(lblQuantity);
	        
	        JLabel lblSearchItem = new JLabel("Search Item");
	        lblSearchItem.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
	        lblSearchItem.setBounds(27, 86, 152, 35);
	        frame.getContentPane().add(lblSearchItem);
	        
	        chooseSearch = new JComboBox();
	        chooseSearch.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
	        chooseSearch.setModel(new DefaultComboBoxModel(new String[] {"Search Product", "Search SKU"}));
	        chooseSearch.setBounds(456, 127, 147, 34);
	        frame.getContentPane().add(chooseSearch);
	        
	        JLabel lblNewLabel = new JLabel("");
	        Image poslogo = new ImageIcon(this.getClass().getResource("/GoShopperLogo_POS.png")).getImage();
	        lblNewLabel.setIcon(new ImageIcon(poslogo));
			lblNewLabel.setBounds(-15, 0, 633, 446);
	        frame.getContentPane().add(lblNewLabel);
	        
	    
		}
	}
	
}