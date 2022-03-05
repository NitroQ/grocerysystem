import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

public class Search {

	private JFrame frame;

	private  String[] columns = {"Item", "Quantity", "Price"};
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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search window = new Search();
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
	public Search() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension (647, 452);
        
        frame = new JFrame("GoShopper Inventory");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,frameSize.width, frameSize.height);

        //Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
        //frame.setIconImage(icon);
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
        frame.getContentPane().setLayout(null); 
        
        JTable table = new JTable(model);
        table.setBounds(10, 11, 372, 233);
        
        JScrollPane jspItem = new JScrollPane(table);
        jspItem.setBounds(30, 149, 573, 207);
        frame.getContentPane().add(jspItem);
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBack.setBounds(484, 53, 120, 35);
        frame.getContentPane().add(btnBack);
        
        txtsearch = new JTextField();
        txtsearch.setBounds(30, 103, 416, 35);
        frame.getContentPane().add(txtsearch);
        txtsearch.setColumns(10);
        
        JButton btnSearchSKU = new JButton("Search SKU");
        btnSearchSKU.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSearchSKU.setBounds(456, 103, 147, 35);
        frame.getContentPane().add(btnSearchSKU);
        
        JButton btnAdditem = new JButton("Add Item");
        btnAdditem.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdditem.setBounds(456, 367, 147, 35);
        frame.getContentPane().add(btnAdditem);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(158, 367, 288, 35);
        frame.getContentPane().add(textField_1);
        
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblQuantity.setBounds(30, 367, 120, 35);
        frame.getContentPane().add(lblQuantity);
        
        JLabel lblSearchItem = new JLabel("Search Item");
        lblSearchItem.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblSearchItem.setBounds(30, 63, 152, 35);
        frame.getContentPane().add(lblSearchItem);
	}
}
