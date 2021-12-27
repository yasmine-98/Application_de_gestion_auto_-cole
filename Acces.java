import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Acces extends JFrame {

	private JPanel contentPane;
	java.sql.Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	private JTextField textUser;
	private JLabel lblNewLabel_1;
	private JPanel panel_1;
	private JPasswordField textPassword;
	private JLabel lblNewLabel_2;
	private JPanel panel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private String username;
	private String password;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Acces frame = new Acces();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Acces() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 100, 600, 400);
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 139, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		cnx=ConnexionMysql.ConnexionDB();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(128, 134, 250, 40);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textUser = new JTextField();
		textUser.addKeyListener(new KeyAdapter() {//configurer le champs de l'utilisateur
			@Override
			public void keyPressed(KeyEvent e) {
				  if(textUser.getText().equals("Utilisateur"))
					 textUser.setText("");
			}
		});
		textUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textUser.getText().equals("Utilisateur"))
					textUser.setText("");
				else
					textUser.selectAll();
			}
		});
		
		textUser.setBorder(null);
		textUser.setText("Utilisateur");
		textUser.setBounds(10, 11, 189, 20);
		panel.add(textUser);
		textUser.setColumns(10);
		
		lblNewLabel_1 = new JLabel("");
		ImageIcon util =new ImageIcon(this.getClass().getResource("/util.png"));
		lblNewLabel_1.setIcon(util);
		lblNewLabel_1.setBounds(209, 0, 41, 40);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(128, 185, 250, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		textPassword = new JPasswordField();
		textPassword.addFocusListener(new FocusAdapter() {//configurer le champs de mot de passe
			public void focusGained(FocusEvent e) {
				if(textPassword.getText().equals("Mot de passe")) {
				 textPassword.setEchoChar('●');
				  textPassword.setText("");
				}
				else
					textPassword.selectAll();
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textPassword.getText().equals("")) {
					textPassword.setText("Mot de passe");
				    textPassword.setEchoChar((char)0);
				}
			}
		});
		textPassword.setEchoChar((char)0);
		textPassword.setBorder(null);
		textPassword.setText("Mot de passe");
		textPassword.setBounds(10, 11, 188, 20);
		panel_1.add(textPassword);
		
		lblNewLabel_2 = new JLabel("");
		ImageIcon cad =new ImageIcon(this.getClass().getResource("/cad.png"));
		lblNewLabel_2.setIcon(cad);
		lblNewLabel_2.setBounds(215, 0, 35, 40);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//verifier les informations d'accés
				String sql="select * from ecole";
				try {
					prepared=cnx.prepareStatement(sql);
					resultat=prepared.executeQuery();
					 if(resultat.next())
		    	 	  {
						 String pass=resultat.getString("mot_de_passe");
						 String user=resultat.getString("utilisateur");
						   username = textUser.getText().toString();
							password = textPassword.getText().toString();
			                if( (password.equals(pass))&&(username.equals(user)))
			                {
			                	Menu menu = new Menu("on");
			                	menu.setVisible(true);
			                	dispose();
			                }
			                else
			                {
			                	JOptionPane.showMessageDialog(null, "Erreur de connexion ");
			                }
		    	 	  }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_2.setBackground(new Color(153, 51, 153));
		panel_2.setBounds(128, 257, 250, 47);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Connexion");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(88, 9, 74, 29);
		panel_2.add(lblNewLabel_4);
		
		UIManager.put("OptionPane.background", new ColorUIResource(173, 216, 230));
		UIManager.put("Button.foreground",new ColorUIResource(0, 0, 204));
		UIManager.put("OptionPane.messageForeground",new ColorUIResource(0, 0, 204));
	    UIManager.getLookAndFeelDefaults().put("Panel.background", new ColorUIResource(173, 216, 230));
	    Icon icon = new ImageIcon(this.getClass().getResource("/icon1.png"));
	    
		UIManager.put("OptionPane.informationIcon", icon);
		
		JLabel lblNewLabel_5 = new JLabel("X");//bouton de fermeture 
		lblNewLabel_5.setForeground(new Color(153, 51, 153));
		lblNewLabel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null,"êtes-vous sûr de vouloir fermer cette application?","Confirmation",JOptionPane.YES_NO_OPTION)==0)
				{
					dispose();
				}
			}
		});
		lblNewLabel_5.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblNewLabel_5.setBounds(584, 0, 16, 21);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Mot de passe oublié?");
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//affichage de la question de sécurité 
				String sql="select question,reponse from ecole";
				try {
					prepared=cnx.prepareStatement(sql);
					resultat=prepared.executeQuery();
					while(resultat.next())
					{
					    String ques=resultat.getString("question");
						String pass=resultat.getString("reponse");
						
						JOptionPane op = new JOptionPane("message", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, icon);
						String input=JOptionPane.showInputDialog(null, ques,"Question de securité", JOptionPane.INFORMATION_MESSAGE);
						if(pass.equals(input))
						{
							String etat="on";
							Ecole ecole=new Ecole("on");
							ecole.setVisible(true);
							dispose();
						}
					}
				
				}catch(SQLException p)
				{
					p.printStackTrace();
				}
			}
		});
		lblNewLabel_6.setBackground(Color.BLACK);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setForeground(Color.BLACK);
		lblNewLabel_6.setBounds(128, 224, 155, 14);
		contentPane.add(lblNewLabel_6);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		ImageIcon img =new ImageIcon(this.getClass().getResource("/violet.png"));
		lblNewLabel.setIcon(img);
		lblNewLabel.setBounds(0, 0, 600, 400);
		contentPane.add(lblNewLabel);
		
	}
}
