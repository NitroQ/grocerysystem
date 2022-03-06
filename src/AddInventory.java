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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddInventory extends SQLConnect {

	JFrame frame;
	private JTextField prodname;
	private JTextField prodsku;
	private JTextField prodqty;
	private JTextField prodprice;
	private JTextField prodcost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddInventory window = new AddInventory();
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
	public AddInventory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension (473, 367);
		
		frame = new JFrame("GoShopper Inventory");
		frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,473, 568);
		Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(24, 25, 211, 50);
		Image img = new ImageIcon(this.getClass().getResource("/GoShopper3.png")).getImage();
		frame.getContentPane().setLayout(null);
		lblNewLabel.setIcon(new ImageIcon(img));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel Window_Name = new JLabel("Add Product");
		Window_Name.setBounds(24, 69, 288, 43);
		Window_Name.setFont(new Font("Segoe UI", Font.BOLD, 20));
		frame.getContentPane().add(Window_Name);
		
		prodname = new JTextField();
		prodname.setBounds(29, 147, 394, 35);
		frame.getContentPane().add(prodname);
		prodname.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(29, 114, 106, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		prodsku = new JTextField();
		prodsku.setColumns(10);
		prodsku.setBounds(29, 226, 394, 35);
		frame.getContentPane().add(prodsku);
		
		JLabel lblNewLabel_1_1 = new JLabel("Stock Keeping Unit (SKU)");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(29, 193, 155, 22);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		prodqty = new JTextField();
		prodqty.setColumns(10);
		prodqty.setBounds(29, 305, 394, 35);
		frame.getContentPane().add(prodqty);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(29, 272, 155, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		prodprice = new JTextField();
		prodprice.setColumns(10);
		prodprice.setBounds(29, 384, 182, 35);
		frame.getContentPane().add(prodprice);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1.setBounds(29, 351, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		prodcost = new JTextField();
		prodcost.setColumns(10);
		prodcost.setBounds(241, 384, 182, 35);
		frame.getContentPane().add(prodcost);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Cost");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1_1.setBounds(241, 351, 182, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JButton add_btn = new JButton("Save");
		add_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
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
		             frame.dispose();
		                
		    	 }catch(HeadlessException | SQLException ex){
		    		 JOptionPane.showMessageDialog(null, ex );
		         }
				
			}
		});
		add_btn.setBounds(29, 456, 109, 43);
		frame.getContentPane().add(add_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.setBounds(148, 456, 109, 43);
		frame.getContentPane().add(cancel_btn);
		
	}
}
