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
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class POS {

	private JFrame frame;
	private JTable table;

	private  String[] columns = {"Item", "Quantity", "Price"};
    private Object[][] data = {};
     @SuppressWarnings("serial")
   private DefaultTableModel model = new DefaultTableModel(data, columns) {
         @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
     };
     private JTable jtable_Total;
     private JTable jTable_Product;
     private JButton btn_Cancel;
     private JButton btn_Pay;
     private JButton btn_Logout;
     private JButton btn_Inventory;
     private JButton btn_Employee;
     private JButton btnNewButton_7;
     private JButton btnNewButton_8;
     private JTextField txt_Qt;
     private JButton btn_Print;
     private JButton btn_Void;
     private JTextField txt_SKUgoinput;
     private JTextField txt_IDinput;
     private JTextField txt_Cash;
     private JTextField txt_Change;
     private JLabel lbl_ItemName;
     private JLabel jtable_Product;
     private JLabel lbl_Skugoinput;
     private JLabel lbl_Qt;
     private JLabel lbl_SubTotal;
     private JLabel lbl_Discount2;
     private JLabel Vat;
     private JLabel lbl_Total;
     private JLabel lbl_SubTotalnum;
     private JLabel lbl_Discount2_1;
     private JLabel Vat_1;
     private JLabel lbl_Total_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					POS window = new POS();
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
	public POS() {
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
        
        Vat_1 = new JLabel("15.26");
        Vat_1.setHorizontalAlignment(SwingConstants.RIGHT);
        Vat_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
        Vat_1.setBounds(195, 151, 101, 22);
        frame.getContentPane().add(Vat_1);
        
        lbl_Total_1 = new JLabel("165.26");
        lbl_Total_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Total_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lbl_Total_1.setBounds(195, 177, 101, 22);
        frame.getContentPane().add(lbl_Total_1);
        
        lbl_SubTotalnum = new JLabel("150.00");
        lbl_SubTotalnum.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_SubTotalnum.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lbl_SubTotalnum.setBounds(195, 100, 101, 22);
        frame.getContentPane().add(lbl_SubTotalnum);
        
        lbl_Discount2_1 = new JLabel("0.00");
        lbl_Discount2_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Discount2_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lbl_Discount2_1.setBounds(195, 125, 101, 22);
        frame.getContentPane().add(lbl_Discount2_1);
        
        lbl_Discount2 = new JLabel("Discount:");
        lbl_Discount2.setFont(new Font("Tahoma", Font.ITALIC, 19));
        lbl_Discount2.setBounds(28, 125, 148, 22);
        frame.getContentPane().add(lbl_Discount2);
        
        lbl_Total = new JLabel("Total:");
        lbl_Total.setFont(new Font("Tahoma", Font.ITALIC, 19));
        lbl_Total.setBounds(28, 177, 84, 22);
        frame.getContentPane().add(lbl_Total);
        
        Vat = new JLabel("VAT:");
        Vat.setFont(new Font("Tahoma", Font.ITALIC, 19));
        Vat.setBounds(28, 151, 148, 22);
        frame.getContentPane().add(Vat);
        
        lbl_SubTotal = new JLabel("SubTotal:");
        lbl_SubTotal.setFont(new Font("Tahoma", Font.ITALIC, 19));
        lbl_SubTotal.setBounds(28, 100, 148, 22);
        frame.getContentPane().add(lbl_SubTotal);
        
        jtable_Product = new JLabel("P 75.0");
        jtable_Product.setFont(new Font("Tahoma", Font.PLAIN, 43));
        jtable_Product.setBounds(337, 140, 342, 63);
        frame.getContentPane().add(jtable_Product);
        
        lbl_ItemName = new JLabel("Datu pula Soy sacue");
        lbl_ItemName.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lbl_ItemName.setBounds(337, 100, 342, 29);
        frame.getContentPane().add(lbl_ItemName);
        
        table = new JTable(model);
        table.setBounds(10, 11, 372, 233);
        
        JScrollPane JSB_Purchase = new JScrollPane(table);
        JSB_Purchase.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JSB_Purchase.setBounds(23, 224, 666, 332);
        frame.getContentPane().add(JSB_Purchase);
        
        jtable_Total = new JTable();
        jtable_Total.setBorder(new LineBorder(Color.LIGHT_GRAY));
        jtable_Total.setBounds(23, 83, 284, 125);
        frame.getContentPane().add(jtable_Total);
        
        jTable_Product = new JTable();
        jTable_Product.setBorder(new LineBorder(Color.LIGHT_GRAY));
        jTable_Product.setBounds(324, 83, 365, 125);
        frame.getContentPane().add(jTable_Product);
        
        btn_Cancel = new JButton("Cancel");
        btn_Cancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_Cancel.setBackground(Color.GRAY);
        btn_Cancel.setBounds(23, 571, 180, 63);
        frame.getContentPane().add(btn_Cancel);
        
        btn_Pay = new JButton("Pay");
        btn_Pay.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_Pay.setBounds(602, 571, 379, 63);
        frame.getContentPane().add(btn_Pay);
        
        btn_Logout = new JButton("LOG-OUT");
        btn_Logout.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btn_Logout.setBounds(861, 23, 120, 35);
        frame.getContentPane().add(btn_Logout);
        
        btn_Inventory = new JButton("Inventory");
        btn_Inventory.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btn_Inventory.setBounds(728, 23, 120, 35);
        frame.getContentPane().add(btn_Inventory);
        
        btn_Employee = new JButton("Employee");
        btn_Employee.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btn_Employee.setBounds(594, 23, 120, 35);
        frame.getContentPane().add(btn_Employee);
        
        btnNewButton_7 = new JButton("Search SKU");
        btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_7.setBounds(839, 168, 139, 35);
        frame.getContentPane().add(btnNewButton_7);
        
        btnNewButton_8 = new JButton("Add SKU");
        btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_8.setBounds(699, 168, 135, 33);
        frame.getContentPane().add(btnNewButton_8);
        
        txt_Qt = new JTextField();
        txt_Qt.setColumns(10);
        txt_Qt.setBounds(896, 124, 82, 35);
        frame.getContentPane().add(txt_Qt);
        
        btn_Print = new JButton("Print");
        btn_Print.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_Print.setBackground(Color.GRAY);
        btn_Print.setBounds(213, 571, 180, 63);
        frame.getContentPane().add(btn_Print);
        
        btn_Void = new JButton("Void");
        btn_Void.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_Void.setBounds(403, 571, 180, 63);
        frame.getContentPane().add(btn_Void);
        
        txt_SKUgoinput = new JTextField();
        txt_SKUgoinput.setColumns(10);
        txt_SKUgoinput.setBounds(701, 122, 186, 37);
        frame.getContentPane().add(txt_SKUgoinput);
        
        JComboBox jcb_GoDiscount = new JComboBox();
        jcb_GoDiscount.setBackground(Color.WHITE);
        jcb_GoDiscount.setFont(new Font("Tahoma", Font.BOLD, 20));
        jcb_GoDiscount.setModel(new DefaultComboBoxModel(new String[] {"GoDiscount"}));
        jcb_GoDiscount.setBounds(724, 276, 229, 35);
        frame.getContentPane().add(jcb_GoDiscount);
        
        txt_IDinput = new JTextField();
        txt_IDinput.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txt_IDinput.setColumns(10);
        txt_IDinput.setBounds(724, 322, 229, 37);
        frame.getContentPane().add(txt_IDinput);
        
        txt_Cash = new JTextField();
        txt_Cash.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txt_Cash.setColumns(10);
        txt_Cash.setBounds(724, 471, 229, 37);
        frame.getContentPane().add(txt_Cash);
        
        JComboBox jcb_PaymentMethod = new JComboBox();
        jcb_PaymentMethod.setBackground(Color.WHITE);
        jcb_PaymentMethod.setFont(new Font("Tahoma", Font.BOLD, 20));
        jcb_PaymentMethod.setModel(new DefaultComboBoxModel(new String[] {"CASH", "VISA", "MASTER CARD"}));
        jcb_PaymentMethod.setBounds(724, 425, 229, 35);
        frame.getContentPane().add(jcb_PaymentMethod);
        
        txt_Change = new JTextField();
        txt_Change.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txt_Change.setColumns(10);
        txt_Change.setBounds(724, 519, 229, 37);
        frame.getContentPane().add(txt_Change);
        
        JLabel lbl_Discount = new JLabel("Dicount");
        lbl_Discount.setFont(new Font("Tahoma", Font.BOLD, 24));
        lbl_Discount.setBounds(711, 231, 123, 22);
        frame.getContentPane().add(lbl_Discount);
        
        JLabel lbl_Purchase = new JLabel("Purchase");
        lbl_Purchase.setFont(new Font("Tahoma", Font.BOLD, 24));
        lbl_Purchase.setBounds(711, 382, 123, 22);
        frame.getContentPane().add(lbl_Purchase);
        
        lbl_Skugoinput = new JLabel("SKU GoInput");
        lbl_Skugoinput.setFont(new Font("Tahoma", Font.BOLD, 24));
        lbl_Skugoinput.setBounds(699, 89, 188, 22);
        frame.getContentPane().add(lbl_Skugoinput);
        
        lbl_Qt = new JLabel("Qt.");
        lbl_Qt.setFont(new Font("Tahoma", Font.BOLD, 24));
        lbl_Qt.setBounds(891, 91, 90, 22);
        frame.getContentPane().add(lbl_Qt);
        
        
        
        
	}
}
