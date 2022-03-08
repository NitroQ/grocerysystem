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
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
     private JButton btn_Cancel;
     private JButton btn_Pay;
     private JButton btnNewButton_7;
     private JButton btnNewButton_8;
     private JTextField txt_Qt;
     private JButton btn_Print;
     private JButton btn_Void;
     private JTextField txt_SKUgoinput;
     private JTextField txt_Cash;
     private JLabel lbl_Skugoinput;
     private JLabel lbl_Qt;
     private JLabel GoShopperAdmin_Logo;
     private JLabel lbl_Discount_2;
     private JLabel lbl_Discount_1;
     private JPanel panel;
     private JLabel lbl_Discount_3;
     private JLabel GoShopper_BG;
     private JLabel lbl_Discount_4;
     private JLabel lbl_Discount_5;
     private JLabel lbl_Discount_6;
     private JLabel lbl_Discount_7;
     private JLabel lbl_Discount_8;
     private JLabel lbl_Discount_9;
     private JLabel txtView_Subtotal;
     private JLabel txtView_Discount;
     private JLabel txtView_VAT;
     private JLabel txtView_GrandTotal;
     private JLabel viewitem;
     private JLabel viewprice;
	
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
        
        txtView_Subtotal = new JLabel("Dynamic");
        txtView_Subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_Subtotal.setForeground(Color.WHITE);
        txtView_Subtotal.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_Subtotal.setBounds(182, 10, 112, 27);
        PaymentDetails.add(txtView_Subtotal);
        
        txtView_Discount = new JLabel("Dynamic");
        txtView_Discount.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_Discount.setForeground(Color.WHITE);
        txtView_Discount.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_Discount.setBounds(182, 40, 112, 27);
        PaymentDetails.add(txtView_Discount);
        
        txtView_VAT = new JLabel("Dynamic");
        txtView_VAT.setHorizontalAlignment(SwingConstants.RIGHT);
        txtView_VAT.setForeground(Color.WHITE);
        txtView_VAT.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        txtView_VAT.setBounds(182, 70, 112, 27);
        PaymentDetails.add(txtView_VAT);
        
        txtView_GrandTotal = new JLabel("Dynamic");
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
        
        viewitem = new JLabel("Datu Puti Vinegar 100ml");
        viewitem.setForeground(Color.WHITE);
        viewitem.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        viewitem.setBounds(20, 23, 285, 27);
        PaymentDetails_1.add(viewitem);
        
        viewprice = new JLabel("100.00");
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
        btn_Pay.setBackground(new Color(0, 139, 139));
        btn_Pay.setForeground(new Color(255, 255, 255));
        btn_Pay.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Pay.setBounds(639, 571, 332, 63);
        frame.getContentPane().add(btn_Pay);
        
        btnNewButton_7 = new JButton("Search SKU");
        btnNewButton_7.setBackground(new Color(0, 139, 139));
        btnNewButton_7.setForeground(new Color(255, 255, 255));
        btnNewButton_7.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
        btnNewButton_7.setBounds(839, 182, 132, 35);
        frame.getContentPane().add(btnNewButton_7);
        
        btnNewButton_8 = new JButton("Add SKU");
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
        btn_Print.setForeground(new Color(255, 255, 255));
        btn_Print.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Print.setBackground(new Color(220, 20, 60));
        btn_Print.setBounds(224, 571, 180, 63);
        frame.getContentPane().add(btn_Print);
        
        btn_Void = new JButton("Void");
        btn_Void.setBackground(new Color(220, 20, 60));
        btn_Void.setForeground(new Color(255, 255, 255));
        btn_Void.setFont(new Font("Segoe UI Variable", Font.BOLD, 17));
        btn_Void.setBounds(411, 571, 218, 63);
        frame.getContentPane().add(btn_Void);
        
        txt_SKUgoinput = new JTextField();
        txt_SKUgoinput.setBackground(new Color(245, 245, 245));
        txt_SKUgoinput.setColumns(10);
        txt_SKUgoinput.setBounds(688, 137, 185, 35);
        frame.getContentPane().add(txt_SKUgoinput);
        
        JComboBox jcb_GoDiscount = new JComboBox();
        jcb_GoDiscount.setBackground(new Color(255, 255, 255));
        jcb_GoDiscount.setFont(new Font("Segoe UI Variable", Font.PLAIN, 15));
        jcb_GoDiscount.setModel(new DefaultComboBoxModel(new String[] {"GoDiscount"}));
        jcb_GoDiscount.setBounds(688, 273, 283, 35);
        frame.getContentPane().add(jcb_GoDiscount);
        
        txt_Cash = new JTextField();
        txt_Cash.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txt_Cash.setColumns(10);
        txt_Cash.setBounds(688, 437, 283, 37);
        frame.getContentPane().add(txt_Cash);
        
        JComboBox jcb_PaymentMethod = new JComboBox();
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
        
        JButton emp_btn = new JButton("Employee");
        emp_btn.setForeground(Color.WHITE);
        emp_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        emp_btn.setBackground(new Color(0, 139, 139));
        emp_btn.setBounds(632, 30, 109, 35);
        frame.getContentPane().add(emp_btn);
        
        JButton inv_btn = new JButton("Inventory");
        inv_btn.setForeground(Color.WHITE);
        inv_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        inv_btn.setBackground(new Color(0, 139, 139));
        inv_btn.setBounds(753, 30, 109, 35);
        frame.getContentPane().add(inv_btn);
        
        JButton logout_btn = new JButton("Logout");
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
        
        lbl_Discount_5 = new JLabel("100.00 P");
        lbl_Discount_5.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Discount_5.setForeground(Color.WHITE);
        lbl_Discount_5.setFont(new Font("Segoe UI Variable", Font.PLAIN, 26));
        lbl_Discount_5.setBounds(108, 35, 165, 27);
        panel.add(lbl_Discount_5);
        
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
}