import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inventory extends SQLConnect{

	JFrame frame;
	private String emp_id;
	private JTable table, tablehistory, tablelow;
	
	
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
	public Inventory(String emp_id) {
		this.emp_id = emp_id;
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
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	String prodname = rs.getString("prod_name");
		    	String sku = rs.getString("sku");
		    	String qty = rs.getString("qty");
		    	prodmodel.addRow(new Object[]{rs.getString("id"),sku,prodname,qty,
		    			rs.getString("cost"), rs.getString("price")});
		    	
		    	if(Integer.parseInt(qty)<= 50) {
		    		lowmodel.addRow(new Object[] {
		    				prodname,
		    				sku
		    		});
		    	}
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
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,frameSize.width, frameSize.height);
		frame.setResizable(false);
		Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit the program?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			    	frame.dispose();
			    }
			    else {
			    	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    }
			  }
			});
		Image img = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
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
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
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
				InventoryAdd inv_add = new InventoryAdd();
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
				InventoryEdit edit_inv = new InventoryEdit(String.valueOf(table.getModel().getValueAt(row, 0)));
				edit_inv.frame.setVisible(true);
			}
		});
		btnEdit.setBounds(784, 133, 89, 30);
		frame.getContentPane().add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
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
		
		JButton emp_btn = new JButton("Employee");
		emp_btn.setForeground(new Color(255, 255, 255));
		emp_btn.setBackground(new Color(0, 139, 139));
		emp_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		emp_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		emp_btn.setBounds(630, 33, 109, 35);
		frame.getContentPane().add(emp_btn);
		
		JButton inv_btn = new JButton("Inventory");
		inv_btn.setForeground(new Color(255, 255, 255));
		inv_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
		inv_btn.setBackground(new Color(0, 139, 139));
		inv_btn.setBounds(751, 33, 109, 35);
		frame.getContentPane().add(inv_btn);
		
		JButton logout_btn = new JButton("Logout");
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
}
