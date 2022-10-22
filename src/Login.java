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
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends SQLConnect {

	JFrame frame;
	private JTextField loginUser;
	private JPasswordField loginPassword;
	private String empid, type;

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
        
        frame = new JFrame("GoShopper Inventory");
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds (ss.width / 2 - frameSize.width / 2, ss.height/2 - frameSize.height/2,1003, 661);
        frame.setLocationRelativeTo(null);
        
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        
        Image icon = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
        frame.setIconImage(icon);
        frame.getContentPane().setLayout(null);
        
        JLabel lblX = new JLabel("x");
        lblX.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                	System.exit(0);
                } else {
                	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
        	}
        });
        lblX.setForeground(new Color(255, 255, 255));
        lblX.setHorizontalAlignment(SwingConstants.RIGHT);
        lblX.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
        lblX.setBounds(735, 15, 250, 29);
        frame.getContentPane().add(lblX);
        
        JLabel lblRole = new JLabel("Choose your role");
        lblRole.setFont(new Font("Segoe UI Variable", Font.ITALIC, 24));
        lblRole.setBounds(74, 206, 250, 29);
        frame.getContentPane().add(lblRole);
        
        JComboBox loginRoles = new JComboBox();
        loginRoles.setBackground(UIManager.getColor("Button.light"));
        loginRoles.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        loginRoles.setModel(new DefaultComboBoxModel(new String[] {"Terminal", "Inventory", "Admin"}));
        loginRoles.setBounds(74, 255, 349, 45);
        frame.getContentPane().add(loginRoles);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI Variable", Font.ITALIC, 24));
        lblUsername.setBounds(74, 319, 155, 29);
        frame.getContentPane().add(lblUsername);
        
        loginUser = new JTextField();
        loginUser.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        loginUser.setBackground(new Color(245, 245, 245));
        loginUser.setBounds(74, 358, 349, 45);
        frame.getContentPane().add(loginUser);
        loginUser.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI Variable", Font.ITALIC, 24));
        lblPassword.setBounds(74, 419, 155, 29);
        frame.getContentPane().add(lblPassword);
        
        loginPassword = new JPasswordField();
        loginPassword.setFont(new Font("Segoe UI Variable", Font.PLAIN, 20));
        loginPassword.setBackground(new Color(245, 245, 245));
        loginPassword.setBounds(74, 458, 349, 45);
        frame.getContentPane().add(loginPassword);
        
        JButton login_btn = new JButton("Login");
        login_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   try {
        			   String user = null, passw = null; 
        	           con = DriverManager.getConnection(connectionUrl, username, password);
        	           ps = con.prepareStatement("SELECT * FROM `Users` WHERE `username` = ? AND `password` = ? ");
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
        	            		POS pos = new POS(empid, type);
        	    				pos.frame.setVisible(true);
        	    				frame.dispose();
        	            	}else if (String.valueOf(loginRoles.getSelectedItem()).equals("Inventory")) {
        	            		Inventory inv = new Inventory(empid,type);
        	    				inv.frame.setVisible(true);
        	    				frame.dispose();
        	            	}else if (String.valueOf(loginRoles.getSelectedItem()).equals("Admin")) {
        	            		if(type.equals("admin")) {
        	            			Admin window = new Admin(empid, type);
        	    					window.frame.setVisible(true);
        	    					frame.dispose();
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
        login_btn.setFont(new Font("Segoe UI Variable", Font.BOLD, 15));
        login_btn.setBackground(new Color(220, 20, 60));
        login_btn.setBounds(74, 526, 114, 43);
        frame.getContentPane().add(login_btn);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("Login_Logo.png")));
        lblNewLabel_1.setBounds(0, -21, 1006, 663);
        frame.getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("Login_SidePanel.png")));
        lblNewLabel.setBounds(0, -22, 1006, 685);
        frame.getContentPane().add(lblNewLabel);
                
		

        
        
        
        
        
	}
}