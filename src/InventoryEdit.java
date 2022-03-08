import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class InventoryEdit extends SQLConnect{

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
		    ps = con.prepareStatement("SELECT * FROM Inventory WHERE id = ?");
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
		
		frame = new JFrame("GoShopper Inventory");
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,473, 568);
		Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image img = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
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
					String query = "UPDATE Inventory SET prod_name = ?, qty = ?, price = ?, cost = ? WHERE id = ?";
				    con = DriverManager.getConnection(connectionUrl);
				    ps = con.prepareStatement(query);
		             ps.setString(1, prodname.getText());
		             ps.setString(2, prodqty.getText());
		             ps.setString(3, prodprice.getText());
		             ps.setString(4, prodcost.getText());
		             ps.setString(5, prod_id);
		             ps.executeUpdate();
		             JOptionPane.showMessageDialog(null, "Updated");
		             frame.dispose();
		                
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
		frame.getContentPane().add(cancel_btn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.PLAIN, 16));
		Image img2 = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img2));
		lblNewLabel.setBounds(24, 25, 211, 50);
		frame.getContentPane().add(lblNewLabel);
		
	}
}
