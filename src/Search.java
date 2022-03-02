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
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 149, 573, 207);
        frame.getContentPane().add(scrollPane);
	}

}
