import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class Inventory {

	JFrame frame;
	private JTable table, tablehistory, tablelow;
	
	
	private  String[] prodcolumns = {"SKU", "Product Name", "Stock", "Cost", "Price"};
	 private Object[][] proddata = {};
	  @SuppressWarnings("serial")
	private DefaultTableModel prodmodel = new DefaultTableModel(proddata, prodcolumns) {
		  @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
	  };
	  
		private  String[] historycolumns = {"Logs"};
		 private Object[][] historydata = {};
		  @SuppressWarnings("serial")
		private DefaultTableModel historymodel = new DefaultTableModel(historydata, historycolumns) {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		  };
		  
			private  String[] lowcolumns = {"Items"};
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory window = new Inventory();
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
	public Inventory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (1020, 700);
		
		frame = new JFrame("GoShopper Inventory");
		frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,frameSize.width, frameSize.height);
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
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(24, 25, 211, 50);
		Image img = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
		frame.getContentPane().setLayout(null);
		lblNewLabel.setIcon(new ImageIcon(img));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel Window_Name = new JLabel("Inventory Dashboard");
		Window_Name.setBounds(24, 69, 288, 43);
		Window_Name.setFont(new Font("Segoe UI", Font.BOLD, 20));
		frame.getContentPane().add(Window_Name);
		
		tablelow = new JTable(lowmodel);
		tablelow.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablelow.setRowHeight(20);
		tablelow.setBounds(100, 174, 288, 288);
		JScrollPane scrollPanelow = new JScrollPane(tablelow);
		scrollPanelow.setBounds(719, 475, 260, 160);
		frame.getContentPane().add(scrollPanelow);
		
		tablehistory = new JTable(historymodel);
		tablehistory.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablehistory.setRowHeight(20);
		tablehistory.setBounds(100, 174, 695, 288);
		JScrollPane scrollPanehistory = new JScrollPane(tablehistory);
		scrollPanehistory.setBounds(24, 475, 671, 160);
		frame.getContentPane().add(scrollPanehistory);
		
		table = new JTable(prodmodel);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(30);
		table.setBounds(24, 174, 956, 329);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 137, 956, 294);
		frame.getContentPane().add(scrollPane);
		

		
		JLabel Window_Name_1 = new JLabel("Stock History Logs");
		Window_Name_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		Window_Name_1.setBounds(24, 429, 288, 43);
		frame.getContentPane().add(Window_Name_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(691, 98, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(790, 98, 89, 23);
		frame.getContentPane().add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(889, 98, 89, 23);
		frame.getContentPane().add(btnDelete);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(592, 98, 89, 23);
		frame.getContentPane().add(btnRefresh);
		
		JLabel Window_Name_2 = new JLabel("In Stock Items");
		Window_Name_2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		Window_Name_2.setBounds(24, 100, 288, 43);
		frame.getContentPane().add(Window_Name_2);
		
		JButton emp_btn = new JButton("Employee");
		emp_btn.setBounds(627, 25, 109, 43);
		frame.getContentPane().add(emp_btn);
		
		JButton inv_btn = new JButton("Inventory");
		inv_btn.setBounds(746, 25, 109, 43);
		frame.getContentPane().add(inv_btn);
		
		JButton logout_btn = new JButton("Logout");
		logout_btn.setBounds(865, 25, 109, 43);
		frame.getContentPane().add(logout_btn);
		
		JLabel Window_Name_3 = new JLabel("Low Stock");
		Window_Name_3.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		Window_Name_3.setBounds(716, 429, 168, 43);
		frame.getContentPane().add(Window_Name_3);


		
		
		
		
		
	}
}
