import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class POSSearch extends SQLConnect {

	JFrame frame;

	private  String[] columns = {"SKU", "Item", "Price"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
   private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };
     private JTextField txtsearch;
     private JTextField textField_1;
	
	
	public void updateTable() {
		  model.setRowCount(0);
		try{
		    con = DriverManager.getConnection(connectionUrl);
		    ps = con.prepareStatement("SELECT * FROM Inventory WHERE qty <> 0");
		    rs = ps.executeQuery();
		    while(rs.next()) {
		    	model.addRow(new Object[]{rs.getString("sku"),rs.getString("prod_name"), rs.getString("price")});
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
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI Variable", Font.PLAIN, 17));
        table.setBounds(10, 11, 372, 233);
        
        JScrollPane jspItem = new JScrollPane(table);
        jspItem.setBounds(30, 172, 573, 169);
        frame.getContentPane().add(jspItem);
        
        JButton btnBack = new JButton("Back");
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
        txtsearch.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
        txtsearch.setBackground(new Color(245, 245, 245));
        txtsearch.setBounds(30, 127, 416, 35);
        frame.getContentPane().add(txtsearch);
        txtsearch.setColumns(10);
        
        JButton btnSearchSKU = new JButton("Search SKU");
        btnSearchSKU.setForeground(new Color(255, 255, 255));
        btnSearchSKU.setBackground(new Color(220, 20, 60));
        btnSearchSKU.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        btnSearchSKU.setBounds(456, 127, 147, 35);
        frame.getContentPane().add(btnSearchSKU);
        
        JButton btnAdditem = new JButton("Add Item");
        btnAdditem.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        	}
        });
        btnAdditem.setForeground(new Color(255, 255, 255));
        btnAdditem.setBackground(new Color(220, 20, 60));
        btnAdditem.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        btnAdditem.setBounds(456, 351, 147, 35);
        frame.getContentPane().add(btnAdditem);
        
        textField_1 = new JTextField();
        textField_1.setBackground(new Color(245, 245, 245));
        textField_1.setFont(new Font("Segoe UI Variable", Font.PLAIN, 14));
        textField_1.setColumns(10);
        textField_1.setBounds(136, 351, 312, 35);
        frame.getContentPane().add(textField_1);
        
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
        lblQuantity.setBounds(30, 351, 106, 35);
        frame.getContentPane().add(lblQuantity);
        
        JLabel lblSearchItem = new JLabel("Search Item");
        lblSearchItem.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
        lblSearchItem.setBounds(27, 86, 152, 35);
        frame.getContentPane().add(lblSearchItem);
        
        JLabel lblNewLabel = new JLabel("");
        Image poslogo = new ImageIcon(this.getClass().getResource("/GoShopperLogo_POS.png")).getImage();
        lblNewLabel.setIcon(new ImageIcon(poslogo));
		lblNewLabel.setBounds(-15, 0, 633, 446);
        frame.getContentPane().add(lblNewLabel);
	}
}