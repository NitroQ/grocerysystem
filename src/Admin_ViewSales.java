import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;

public class Admin_ViewSales extends SQLConnect{

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
		
		JLabel discount_serv = new JLabel("Discount Availed: " + discount);
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
