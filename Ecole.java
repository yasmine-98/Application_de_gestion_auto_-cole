import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class Ecole extends JFrame {

	private JPanel contentPane;
	private JTextField nom_prenom;
	private JTextField login;
	private JTextField Password;
	private JTextField lblNombreDesSalles;
	private JTextField nb_max_eleves;
	private JTextField Adresse;
	private JTextField Nom_ecole;
	private JDateChooser date_creation;
	private JComboBox question;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	static java.sql.Connection cnx = null;
	static PreparedStatement prepared = null;
	static ResultSet resul = null;
	private JTextField Reponse;
	private JTextField nb_salle;
	private String etat_son;
	private String nom_ecole;
	private String adresse;
	private String nom_prenom_directeur;
	private String utilisateur;
	private String mot_de_passe;
	private String reponse;
	private String question_secret;
	private String ouverture;
	private int nb_salles;
	private int capacite;

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
					Ecole frame = new Ecole("on");
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
	public Ecole(String etat) {
		etat_son=etat;
		setTitle("Ecole");
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		setBounds(200, 0, 660, 723);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB();
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Date d'ouverture :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(20, 166, 189, 24);
		panel.add(lblNewLabel);
		
		JLabel lblAdresse = new JLabel("Adresse :");
		lblAdresse.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAdresse.setBounds(20, 207, 156, 24);
		panel.add(lblAdresse);
		
		JLabel lblN = new JLabel("Directeur :");
		lblN.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblN.setBounds(20, 424, 156, 24);
		panel.add(lblN);
		
		JLabel lblLogin = new JLabel("Utilisateur:");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLogin.setBounds(20, 465, 166, 24);
		panel.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Mot de passe:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(20, 506, 189, 19);
		panel.add(lblPassword);
		
		JLabel lblFicheEcole = new JLabel("Fiche de l'\u00E9cole ");
		lblFicheEcole.setForeground(new Color(51, 102, 204));
		lblFicheEcole.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblFicheEcole.setBounds(194, 11, 297, 30);
		panel.add(lblFicheEcole);
		
		JLabel lblNombreDesSalles = new JLabel("Nombre des salles :");
		lblNombreDesSalles.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreDesSalles.setBounds(20, 292, 189, 24);
		panel.add(lblNombreDesSalles);
		
		JLabel lblNombreDesEleves = new JLabel("Nombre des \u00E9l\u00E8ves maximal par salle :");
		lblNombreDesEleves.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreDesEleves.setBounds(20, 327, 355, 24);
		panel.add(lblNombreDesEleves);
		
		JLabel lbldonnesSurcole = new JLabel("- Informations sur l'\u00E9cole :");
		lbldonnesSurcole.setForeground(new Color(255, 255, 255));
		lbldonnesSurcole.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbldonnesSurcole.setBounds(10, 85, 300, 30);
		panel.add(lbldonnesSurcole);
		
		JLabel lblDonnesSur = new JLabel("- Informations sur le directeur:");
		lblDonnesSur.setForeground(Color.WHITE);
		lblDonnesSur.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDonnesSur.setBounds(10, 373, 365, 30);
		panel.add(lblDonnesSur);
		
		JLabel lblNomDeLcole = new JLabel("Nom de l'\u00E9cole :");
		lblNomDeLcole.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNomDeLcole.setBounds(20, 126, 189, 24);
		panel.add(lblNomDeLcole);
		
		Nom_ecole = new JTextField();
		Nom_ecole.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Nom_ecole.setBounds(194, 125, 300, 20);
		panel.add(Nom_ecole);
		Nom_ecole.setColumns(10);
		
		 date_creation = new JDateChooser();
		date_creation.setBounds(194, 165, 300, 20);
		date_creation.setDateFormatString("yyyy-MM-dd");
		panel.add(date_creation);
		
		Adresse = new JTextField();
		Adresse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Adresse.setColumns(10);
		Adresse.setBounds(194, 205, 300, 20);
		panel.add(Adresse);
		
		JLabel lblInformationsSur = new JLabel("- Informations sur les salles :");
		lblInformationsSur.setForeground(Color.WHITE);
		lblInformationsSur.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformationsSur.setBounds(10, 251, 300, 30);
		panel.add(lblInformationsSur);
		
		nb_max_eleves = new JTextField();
		nb_max_eleves.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nb_max_eleves.setBounds(371, 333, 120, 20);
		panel.add(nb_max_eleves);
		nb_max_eleves.setColumns(10);
		nb_max_eleves.setText("0");
		
		nb_salle = new JTextField();
		nb_salle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nb_salle.setBounds(371, 293, 120, 20);
		panel.add(nb_salle);
		nb_salle.setColumns(10);
		nb_salle.setText("0");
		
		nom_prenom = new JTextField();
		nom_prenom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nom_prenom.setColumns(10);
		nom_prenom.setBounds(191, 425, 300, 20);
		panel.add(nom_prenom);
		
		login = new JTextField();
		login.setFont(new Font("Tahoma", Font.PLAIN, 15));
		login.setColumns(10);
		login.setBounds(191, 465, 300, 20);
		panel.add(login);
		
		Password = new JTextField();
		Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Password.setColumns(10);
		Password.setBounds(191, 505, 300, 20);
		panel.add(Password);
		
		JButton btnNewButton = new JButton("Enregistrer");
		btnNewButton.setIcon(null);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verifier()==true)//Vérifier la validation des champs obligatoire
				{
				Enregistrer();//Enregistrer les informations de école
			    }
			}
		});
		btnNewButton.setForeground(new Color(51, 102, 204));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(236, 627, 200, 24);
		panel.add(btnNewButton);
		
		JLabel lblQuestionSecret = new JLabel("Question de s\u00E9curit\u00E9:");
		lblQuestionSecret.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQuestionSecret.setBounds(20, 545, 166, 24);
		panel.add(lblQuestionSecret);
		
		 question = new JComboBox();
		question.setFont(new Font("Tahoma", Font.PLAIN, 15));
		question.setModel(new DefaultComboBoxModel(new String[] {"Quelle est votre couleur pr\u00E9fer\u00E9?", "Quelle est votre date de naissance?"}));
		question.setBounds(191, 545, 300, 20);
		panel.add(question);
		
		JLabel lblReponse = new JLabel("Reponse:");
		lblReponse.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblReponse.setBounds(20, 587, 156, 19);
		panel.add(lblReponse);
		
		Reponse = new JTextField();
		Reponse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Reponse.setColumns(10);
		Reponse.setBounds(191, 585, 300, 20);
		panel.add(Reponse);
		
		lblNewLabel_11 = new JLabel("on");
		lblNewLabel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_11.setVisible(false);
				lblNewLabel_12.setVisible(true);
				etat_son="off";
			}
		});
		ImageIcon on =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_11.setIcon(on);
		lblNewLabel_11.setBounds(569, -2, 62, 71);
		panel.add(lblNewLabel_11);
		
		lblNewLabel_12 = new JLabel("off");
		lblNewLabel_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_12.setVisible(false);
				lblNewLabel_11.setVisible(true);
				etat_son="on";
			}
		});
		ImageIcon off =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_12.setIcon(off);
		lblNewLabel_12.setBounds(569, -2, 62, 71);
		panel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    Menu m;
					try {
					
				     String sql="select * from ecole";
                    prepared=cnx.prepareStatement(sql);
			         resul=prepared.executeQuery();
			         if(resul.next())
			         {
			        	    m = new Menu(etat_son);
							m.setVisible(true);
							dispose(); //ferméture de la fenétre 
			         }
			         else
			         {
			        	 message("Vous devez enregistrer les coordonnées de l'ecole avant.",false);
			         }
			         
			    }catch(SQLException e1)
			    {
				e1.printStackTrace();
			    }
			}
		});
		ImageIcon retour =new ImageIcon(this.getClass().getResource("/retour.png"));
		lblNewLabel_1.setIcon(retour);
		lblNewLabel_1.setBounds(0, -2, 68, 67);
		panel.add(lblNewLabel_1);
		
		recuperer_champs();
		etat_son();
	}
	public void Enregistrer()//enregistrer des informarions sur l'ecole
	{
		
		String sql = "delete from ecole ";

	    try {
	    	prepared=cnx.prepareStatement(sql);
	        prepared.executeUpdate();
	}catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }

		
		 ouverture  = ((JTextField)date_creation.getDateEditor().getUiComponent()).getText().toString();
		String sql1="insert into ecole(directeur , nom_ecole, date_ouverture, adresse, nb_salles,capacite_salle, utilisateur,mot_de_passe,question,reponse) values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			
			prepared=cnx.prepareStatement(sql1);
	    	prepared.setString(1, nom_prenom.getText().toString());
	    	prepared.setString(2, Nom_ecole.getText().toString());
	    	prepared.setString(3, ouverture);
	    	prepared.setString(4, Adresse.getText().toString());
	    	prepared.setString(7, login.getText().toString());
	    	prepared.setString(5, nb_salle.getText().toString());
	    	prepared.setString(6, nb_max_eleves.getText().toString());
	    	prepared.setString(8, Password.getText().toString());
	    	prepared.setString(9, question.getSelectedItem().toString());
	    	prepared.setString(10, Reponse.getText().toString());
	    	
	    	prepared.executeUpdate();
	    	
	    	message("Enregistrement réussit.",true);
	    	
	    	
	    	
	}catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }
	}
	


	public boolean verifier()//Vérifier la validation des champs obligatoires
	{
		boolean v;
		v=true;
		  nom_ecole=Nom_ecole.getText();
		if((nom_ecole.matches("^[A-Z]|[a-z]*$")==false)||(nom_ecole.equals("")))
		{
			message("Verifiez le nom de l'école.",false);
			v=false;
		}
		else
		{
			  adresse=Adresse.getText();
			 
				if((adresse.matches("^[A-Z]|[a-z]*$")==false)||(adresse.equals("")))
				{
					message("Verifiez votre adresse.",false);
					v=false;
		
				}
				else {
					nb_salles=Integer.parseInt(nb_salle.getText());
					if((String.valueOf(nb_salles).chars().allMatch( Character::isDigit )==false)||(String.valueOf(nb_salles).matches("[0]+")==true))
					{
						message("Verifiez votre nombres des salles.",false);
						v=false;
						
					}
					else
					{
					capacite=Integer.parseInt(nb_max_eleves.getText());
					if((String.valueOf(capacite).chars().allMatch( Character::isDigit )==false)||(String.valueOf(capacite).matches("[0]+")==true))
					{
						message("Verifiez votre capacite de salle.",false);
						v=false;
						
					}
					else {
		          nom_prenom_directeur=nom_prenom.getText();
		 
			      if((nom_prenom_directeur.matches("^[A-Z]|[a-z]*$")==false)||(nom_prenom_directeur.equals("")))
			      {
				    message("Verifiez le nom du directeur.",false);
				    v=false;
	
			      }
			
			else
			{
				utilisateur=login.getText();
			if(utilisateur.equals(""))
			{
				message("Verifiez votre utilisateur.",false);
				v=false;
	
			}
			else
			{
				mot_de_passe=Password.getText();
			if(mot_de_passe.equals(""))
			{
				message("Verifiez votre mot de passe.",false);
				v=false;
	
			}
			
			
			else
			{
			 reponse=Reponse.getText();
			if(reponse.equals(""))
			{
				message("Verifiez votre réponse. ",false);
				v=false;
				
			}}}
			}
			}
			}
				}
			}
		
		
			
			
			
	return v;
	}

	public void recuperer_champs()//Récuperer les informations dans les champs automatiquement 
	{
		String sql="select * from ecole";
		   try {
		    	prepared=cnx.prepareStatement(sql);
		        prepared.executeQuery();
		    	
		        resul=prepared.executeQuery();	
		    	
		   if(resul.next())
		{
			nom_ecole=resul.getString("nom_ecole");
			Nom_ecole.setText(nom_ecole);
			adresse=resul.getString("adresse");
			Adresse.setText(adresse);
			ouverture=resul.getString("Date_ouverture");
			((JTextField)date_creation.getDateEditor().getUiComponent()).setText(ouverture);
			nom_prenom_directeur=resul.getString("directeur");
			nom_prenom.setText(nom_prenom_directeur);
			utilisateur=resul.getString("utilisateur");
			login.setText(utilisateur);
			mot_de_passe=resul.getString("mot_de_passe");
			Password.setText(mot_de_passe);
			 nb_salles=Integer.parseInt(resul.getString("nb_salles"));
			nb_salle.setText(String.valueOf(nb_salles));
			 capacite=Integer.parseInt(resul.getString("capacite_salle"));
			nb_max_eleves.setText(String.valueOf(capacite));
			 question_secret=resul.getString("question");
			question.setSelectedItem(question_secret);
			 reponse=resul.getString("reponse");
			Reponse.setText(reponse);
	}
	}catch(SQLException e1)
    {
	e1.printStackTrace();
    }
	}
	public void message(String msg,boolean type)//Afficher un message de réussite ou d'echec pour informer l'utilisateur 
    {
    	if(lblNewLabel_11.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("on",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
		else if(lblNewLabel_12.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("off",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
    }
	public void etat_son()//gérer l'etat sonore de l'interface
	{
		if(etat_son=="on")
		{
			lblNewLabel_11.setVisible(true);
			lblNewLabel_12.setVisible(false);
		}
		else if(etat_son=="off")
		{
			lblNewLabel_12.setVisible(true);
			lblNewLabel_11.setVisible(false);
		}
	}
}
