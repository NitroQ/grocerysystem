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
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frame;
	private JTextField loginUser;
	private JPasswordField loginPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension (1020, 700);
        
        frame = new JFrame("GoShopper Login");
        frame.getContentPane().setBackground(Color.WHITE);
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
        frame.getContentPane().setLayout(null);
        
        JLabel BGLogin = new JLabel("");
        Image loginBG = new ImageIcon(this.getClass().getResource("/bglogin.png")).getImage();
        BGLogin.setIcon(new ImageIcon (loginBG));
        BGLogin.setBounds(558, 0, 446, 661);
        frame.getContentPane().add(BGLogin);
        
        JLabel logo = new JLabel("");
        Image goshopperLogo = new ImageIcon (this.getClass().getResource("/Logo.png")).getImage();
        logo.setIcon(new ImageIcon(goshopperLogo));
        logo.setBounds(143, 40, 122, 154);
        frame.getContentPane().add(logo);
        
        JLabel GOshopper = new JLabel("GO");
        GOshopper.setFont(new Font("Spartan", Font.BOLD, 60));
        GOshopper.setBounds(35, 97, 122, 97);
        frame.getContentPane().add(GOshopper);
        
        JLabel lblNewLabel = new JLabel("HOPPER");
        lblNewLabel.setFont(new Font("Spartan", Font.BOLD, 60));
        lblNewLabel.setBounds(259, 88, 289, 114);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel loginText = new JLabel("LOGIN");
        loginText.setFont(new Font("Spartan", Font.PLAIN, 30));
        loginText.setBounds(35, 173, 122, 60);
        frame.getContentPane().add(loginText);
        
        JLabel lblRole = new JLabel("Choose your role");
        lblRole.setFont(new Font("Roboto Thin", Font.ITALIC, 24));
        lblRole.setBounds(35, 258, 250, 29);
        frame.getContentPane().add(lblRole);
        
        JComboBox loginRoles = new JComboBox();
        loginRoles.setBackground(UIManager.getColor("Button.light"));
        loginRoles.setFont(new Font("Roboto", Font.PLAIN, 24));
        loginRoles.setModel(new DefaultComboBoxModel(new String[] {"Terminal", "Admin"}));
        loginRoles.setBounds(35, 308, 278, 38);
        frame.getContentPane().add(loginRoles);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Roboto Thin", Font.ITALIC, 24));
        lblUsername.setBounds(35, 399, 155, 29);
        frame.getContentPane().add(lblUsername);
        
        loginUser = new JTextField();
        loginUser.setFont(new Font("Roboto", Font.PLAIN, 24));
        loginUser.setBackground(UIManager.getColor("Button.light"));
        loginUser.setBounds(35, 439, 278, 38);
        frame.getContentPane().add(loginUser);
        loginUser.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Roboto Thin", Font.ITALIC, 24));
        lblPassword.setBounds(35, 503, 155, 29);
        frame.getContentPane().add(lblPassword);
        
        loginPassword = new JPasswordField();
        loginPassword.setBackground(UIManager.getColor("Button.light"));
        loginPassword.setBounds(35, 543, 278, 38);
        frame.getContentPane().add(loginPassword);
        
        
       
        
        
        
        
        
	}
}
