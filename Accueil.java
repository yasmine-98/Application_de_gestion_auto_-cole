import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class Accueil extends JFrame {

	private JPanel contentPane;
	static java.sql.Connection cnx = null;
	static PreparedStatement prepared = null;
	static ResultSet resultat = null;
	private static JLabel lblNewLabel_1;
	private static JProgressBar progressBar;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
				try {
					cnx=ConnexionMysql.ConnexionDB();
					int x;
					Accueil frame = new Accueil();
					frame.setVisible(true); 
					for(x=0;x<=100;x++) {//configurer la barre de progression
		        	    Accueil.progressBar.setValue(x);
						Thread.sleep(50);
						Accueil.lblNewLabel_1.setText(Integer.toString(x)+" %");
						if (x==100)
				           {
							  frame.dispose();
							  String sql="select * from ecole";
							  try {
								  prepared=cnx.prepareStatement(sql);
					    		  resultat=prepared.executeQuery();
					    	 	  if(resultat.next())//vérifier si l'application est déja configurée ou non
					    	 	  {
					    	 		  Acces ac=new Acces();
					    	 		  ac.setVisible(true);
					    	 	  }
					    	 	  else {
					    	 		  Ecole ecole=new Ecole("on");
					    	 		  ecole.setVisible(true);
					    	 	  }
							  }catch (Exception e) {
									e.printStackTrace();
							}
				           }
				           }
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	/**
	 * Create the frame.
	 */
	public Accueil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gestion Auto Ecole");
		setBounds(300, 50, 798, 646);
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setUndecorated(true);
		cnx=ConnexionMysql.ConnexionDB();
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 790, 640);
		panel.setBackground(new Color(0,240, 240, 240));
		contentPane.add(panel);
	    panel.setLayout(null);
		
	    lblNewLabel_1 = new JLabel("Chargement en cours ");
	    lblNewLabel_1.setForeground(new Color(0, 0, 204));
	    lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(211, 479, 400, 30);
		panel.add(lblNewLabel_1);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(102, 153, 255));
	    progressBar.setBounds(270, 532, 300, 30);
	    panel.add(progressBar);
	   
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		ImageIcon car =new ImageIcon(this.getClass().getResource("/gifcar.gif"));
		lblNewLabel.setIcon(car);
		lblNewLabel.setBounds(0, 0, 960, 720);
		panel.add(lblNewLabel);
	}
}
