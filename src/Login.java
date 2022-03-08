import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends SQLConnect {

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
        frame.setResizable(false);
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
        Image goshopperLogo = new ImageIcon (this.getClass().getResource("/GoShopper.png")).getImage();
        logo.setIcon(new ImageIcon(goshopperLogo));
        logo.setBounds(35, 33, 308, 154);
        frame.getContentPane().add(logo);
        
        JLabel loginText = new JLabel("LOGIN");
        loginText.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        loginText.setBounds(35, 173, 122, 60);
        frame.getContentPane().add(loginText);
        
        JLabel lblRole = new JLabel("Choose your role");
        lblRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 24));
        lblRole.setBounds(35, 258, 250, 29);
        frame.getContentPane().add(lblRole);
        
        JComboBox loginRoles = new JComboBox();
        loginRoles.setBackground(UIManager.getColor("Button.light"));
        loginRoles.setFont(new Font("Roboto", Font.PLAIN, 24));
        loginRoles.setModel(new DefaultComboBoxModel(new String[] {"Terminal", "Inventory", "Admin"}));
        loginRoles.setBounds(35, 308, 278, 38);
        frame.getContentPane().add(loginRoles);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI Variable", Font.PLAIN, 24));
        lblUsername.setBounds(35, 368, 155, 29);
        frame.getContentPane().add(lblUsername);
        
        loginUser = new JTextField();
        loginUser.setFont(new Font("Roboto", Font.PLAIN, 24));
        loginUser.setBackground(UIManager.getColor("Button.light"));
        loginUser.setBounds(35, 408, 278, 38);
        frame.getContentPane().add(loginUser);
        loginUser.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI Variable", Font.PLAIN, 24));
        lblPassword.setBounds(35, 472, 155, 29);
        frame.getContentPane().add(lblPassword);
        
        loginPassword = new JPasswordField();
        loginPassword.setBackground(UIManager.getColor("Button.light"));
        loginPassword.setBounds(35, 512, 278, 38);
        frame.getContentPane().add(loginPassword);
        
        JButton login_btn = new JButton("Login");
        login_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   try {
        			   String user = null, passw = null, empid = null, type = null; 
        	           con = DriverManager.getConnection(connectionUrl);
        	           ps = con.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ? ");
        	           ps.setString(1, loginUser.getText());
        	           ps.setString(2, String.valueOf(loginPassword.getPassword()));
        	           rs = ps.executeQuery();
        	            while(rs.next()){
        	            	user = rs.getString("username");
        	            	passw = rs.getString("password");
        	            	empid = rs.getString("emp_id");
        	            	type = rs.getString("user_type");
        	            }
        	            
        	            if(loginUser.getText().equals("")) {
        	            	JOptionPane.showMessageDialog(null,"Username field is empty");
        	            }else if(String.valueOf(loginPassword.getPassword()).equals("")) {
        	            	JOptionPane.showMessageDialog(null,"Password field is empty");
        	            }
        	            else if(loginUser.getText().equals(user) && String.valueOf(loginPassword.getPassword()).equals(passw)) {
        	            	if(String.valueOf(loginRoles.getSelectedItem()).equals("Terminal")) {
        	            		
        	            	}else if (String.valueOf(loginRoles.getSelectedItem()).equals("Inventory")) {
        	            		Inventory inv = new Inventory(empid);
        	    				inv.frame.setVisible(true);
        	    				frame.dispose();
        	            	}else if (String.valueOf(loginRoles.getSelectedItem()).equals("Admin")) {
        	            		if(type.equals("admin")) {
        	            			JOptionPane.showMessageDialog(null,"Admin.");
        	            		}else {
        	            			JOptionPane.showMessageDialog(null,"You are not an Admin.");
        	            		}
        	            	}
    	            		
    	            	}else {
    	            		JOptionPane.showMessageDialog(null,"Wrong username/password");
    	            	}
        	            
        	        } catch (Exception ex) {
        	        	 JOptionPane.showMessageDialog(null, ex );
        	         }
        		
        	}
        });
        login_btn.setForeground(Color.WHITE);
        login_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 14));
        login_btn.setBackground(new Color(220, 20, 60));
        login_btn.setBounds(35, 574, 114, 43);
        frame.getContentPane().add(login_btn);
        
        
       
        
        
        
        
        
	}
}