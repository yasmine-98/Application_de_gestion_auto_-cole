

import java.awt.BorderLayout;




import java.util.jar.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import net.proteanit.sql.DbUtils;
import java.util.Date;
import java.util.Vector;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class Moniteur extends JFrame {

	private JPanel contentPane;
	private JTextField Cin;
	private JTextField Nom_prenom;
	private JTextField max_seance;
	private JTextField Telephone;
	private JTextField Adresse;
	private JComboBox Categorie_permis;
	private JComboBox Specialite;
	Connection cnx=null;
	ResultSet resultat =null;
	PreparedStatement prepared=null;
	private JTable table;
	private JDateChooser date_de_naissance;
	private JDateChooser date_de_recrutement;
	private JTextField txtAjouterUnCandidat;
	private JTextField txtModifierUnCandidat;
	private JTextField txtSupprimerUnCandidat;
	private JLabel lblNewLabel_14;
	private JLabel lblNewLabel_15;
	private JTextField Prix_seance;
	private JLabel lblPrixDuneSance;
	private JLabel lblSalaire;
	private JTextField recherche;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel label;
	private JLabel label_1;
	private JButton btnRecherche;
	private JLabel lblNewLabel_7;
	private JTextField txtFicheDePaiement;
	private JLabel lblNewLabel_11;
	private String etat_son;
	private String nom_prenom;
	private long cin;
	private long telephone;
	private String adresse;
	private float salaire;
	private float prix_seance;
	private int max_seances;
	private String naissance;
	private String recrutement;
	private String specialite;
	private String categorie;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			private JScrollPane scrollPane_1;

			public void run() {
				try {
					Moniteur frame = new Moniteur("on");
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
	public Moniteur(String etat) {
		etat_son=etat;
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		setTitle("Gestion des moniteurs");
		
		
		 JFrame.setDefaultLookAndFeelDecorated(true);
		setBounds(0, 0, 1290, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(new Color(102, 153, 204));
		setContentPane(contentPane);
		cnx = ConnexionMysql.ConnexionDB();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		 lblNewLabel_14 = new JLabel("Ce champ doit comport\u00E9 seulement 8 chiffes");
		lblNewLabel_14.setForeground(new Color(255, 51, 0));
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_14.setBounds(215, 87, 288, 15);
		panel.add(lblNewLabel_14);
		lblNewLabel_14.setVisible(false);
		 lblNewLabel_15 = new JLabel("Ce champ doit comport\u00E9 seulement 8 chiffres");
		lblNewLabel_15.setForeground(new Color(255, 51, 0));
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_15.setBounds(215, 200, 200, 15);
		panel.add(lblNewLabel_15);
		lblNewLabel_15.setVisible(false);
		txtAjouterUnCandidat = new JTextField();
		txtAjouterUnCandidat.setHorizontalAlignment(SwingConstants.LEFT);
		txtAjouterUnCandidat.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtAjouterUnCandidat.setText("Ajouter un moniteur");
		txtAjouterUnCandidat.setBounds(538, 177, 131, 15);
		panel.add(txtAjouterUnCandidat);
		txtAjouterUnCandidat.setColumns(10);
		txtAjouterUnCandidat.setVisible(false);
		txtSupprimerUnCandidat = new JTextField();
		txtSupprimerUnCandidat.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtSupprimerUnCandidat.setText("Supprimer un moniteur");
		txtSupprimerUnCandidat.setBounds(887, 177, 144, 15);
		panel.add(txtSupprimerUnCandidat);
		txtSupprimerUnCandidat.setColumns(10);
		txtSupprimerUnCandidat.setVisible(false);
		
		txtModifierUnCandidat = new JTextField();
		txtModifierUnCandidat.setText("Modifier un moniteur");
		txtModifierUnCandidat.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtModifierUnCandidat.setBounds(702, 177, 136, 15);
		panel.add(txtModifierUnCandidat);
		txtModifierUnCandidat.setColumns(10);
		txtModifierUnCandidat.setVisible(false);
		
		
		JLabel lblNewLabel = new JLabel("CIN du moniteur:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 70, 100, 20);
		panel.add(lblNewLabel);
		 date_de_recrutement = new JDateChooser();
		date_de_recrutement.setBounds(215, 254, 228, 20);
		panel.add(date_de_recrutement);
		date_de_recrutement.setDateFormatString("yyyy-MM-dd");
		Date date = new Date();
		date_de_recrutement.setDate(date);
		
		 date_de_naissance = new JDateChooser();
		date_de_naissance.setBounds(215, 143, 228, 20);
		panel.add(date_de_naissance);
		date_de_naissance.setDateFormatString("yyyy-MM-dd");
		
		
		JLabel lblNewLabel_1 = new JLabel("Nom et Prenom:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 107, 200, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Date de naissance:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 143, 200, 20);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("N\u00B0t\u00E9l\u00E9phone:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 180, 120, 20);
		panel.add(lblNewLabel_3);
		
		Cin = new JTextField();
		Cin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				recuperer_Champs();//Récupérer les informations d'un moniteur 
					}
				
			
		});
		Cin.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_14.setVisible(false);
			}
		});
		Cin.setBounds(215, 70, 228, 20);
		panel.add(Cin);
		Cin.setColumns(10);
		Cin.setText("");
		
		Nom_prenom = new JTextField();
		Nom_prenom.setFont(new Font("Tahoma", Font.BOLD, 11));
		Nom_prenom.setBounds(215, 107, 228, 20);
		panel.add(Nom_prenom);
		Nom_prenom.setColumns(10);
		Nom_prenom.setText("");
		
		JLabel lblNewLabel_4 = new JLabel("Gestion des moniteurs");
		lblNewLabel_4.setForeground(new Color(51, 102, 204));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel_4.setBounds(538, 10, 300, 40);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Adresse:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 217, 120, 20);
		panel.add(lblNewLabel_5);
		
		
		JLabel lblNewLabel_6 = new JLabel("Date de recrutement:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(10, 254, 228, 20);
		panel.add(lblNewLabel_6);
		
		max_seance = new JTextField();
		max_seance.setFont(new Font("Tahoma", Font.BOLD, 11));
		max_seance.setBounds(215, 365, 120, 20);
		panel.add(max_seance);
		max_seance.setColumns(10);
		max_seance.setText("0");
		
		JLabel lblNewLabel_8 = new JLabel("Maximum des seances \u00E0 travailler:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_8.setBounds(10, 365, 200, 20);
		panel.add(lblNewLabel_8);
		
		 Specialite = new JComboBox();
		 Specialite.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 	    specialite=Specialite.getSelectedItem().toString();
		 		if(specialite.equals("Code"))
		 		{
		 			lblSalaire.setVisible(true);
		 			lblPrixDuneSance.setVisible(false);
		 		}
		 		if(specialite.equals("Conduite"))
		 		{
		 			lblSalaire.setVisible(false);
		 			lblPrixDuneSance.setVisible(true);
		 		}
		 	}
		 });
		Specialite.setModel(new DefaultComboBoxModel(new String[] {"Conduite", "Code"}));
		Specialite.setFont(new Font("Tahoma", Font.BOLD, 11));
		Specialite.setBounds(215, 291, 228, 20);
		panel.add(Specialite);
		
		
		 Categorie_permis = new JComboBox();
		Categorie_permis.setModel(new DefaultComboBoxModel(new String[] {"A1", "A", "B", "B+E", "C", "C+E", "D", "D+E", "D1", "H"}));
		Categorie_permis.setFont(new Font("Tahoma", Font.BOLD, 11));
		Categorie_permis.setBounds(215, 328, 228, 20);
		panel.add(Categorie_permis);
		
		JLabel lblNewLabel_9 = new JLabel("Specialit\u00E9:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setBounds(10, 291, 200, 20);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Cat\u00E9gorie du permis:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(10, 328, 200, 20);
		panel.add(lblNewLabel_10);
		
		Telephone = new JTextField();
		Telephone.setFont(new Font("Tahoma", Font.BOLD, 11));
		Telephone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_15.setVisible(false);
			}
		});
		Telephone.setBounds(215, 180, 228, 20);
		panel.add(Telephone);
		Telephone.setColumns(10);
		Telephone.setText("");
		
		Adresse = new JTextField();
		Adresse.setFont(new Font("Tahoma", Font.BOLD, 11));
		Adresse.setBounds(215, 217, 228, 20);
		panel.add(Adresse);
		Adresse.setColumns(10);
		Adresse.setText("");
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtModifierUnCandidat.setVisible(false);
				if(cinExiste()==true)//Vérifier l'existance d'un moniteur
				{
					if(verifier()==true)//Vérifier la validation des champs obligatoires
				    {
				    modifier();//Modifier un moniteur déja existant
				    }
					
				}
			  else {
				
			    
			    message("Ce moniteur n'existe pas",false);
			  }
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(Color.black));
				txtModifierUnCandidat.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtModifierUnCandidat.setVisible(false);
			}
		});
		ImageIcon modifier =new ImageIcon(this.getClass().getResource("/modifm.png"));
		btnNewButton_1.setIcon(modifier);
		btnNewButton_1.setBounds(701, 101, 160, 210);
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSupprimerUnCandidat.setVisible(false);
				 cin=Integer.parseInt(Cin.getText());
				 if(cinExiste()==true) //Vérifier l'existance d'un moniteur
				 {
				   supprimer();//supprimer un moniteur déja existant
				 }
				 else
				 {
					 message("Ce moniteur n'existe pas",false);
				 }
				 }
		});
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_2.setBorder(BorderFactory.createLineBorder(Color.black));
				txtSupprimerUnCandidat.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_2.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtSupprimerUnCandidat.setVisible(false);
			}
		});
		ImageIcon supprimer =new ImageIcon(this.getClass().getResource("/suppm.png"));
		btnNewButton_2.setIcon(supprimer);
		btnNewButton_2.setBounds(871, 101, 160, 210);
		btnNewButton_2.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		panel.add(btnNewButton_2);
		
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAjouterUnCandidat.setVisible(false);
				if(cinExiste()==true)//Vérifier l'existance d'un moniteur
				{
					message("Ce moniteur existe déja",false);
					
				}
			    else {
			    	
			       if(verifier()==true)//Vérifier la validation des champs obligatoires
			        {
			    	   
			         ajouter();//Ajouter un moniteur non existant 
			        }
			    }
			}
		});
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_3.setBorder(BorderFactory.createLineBorder(Color.black));
				txtAjouterUnCandidat.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_3.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtAjouterUnCandidat.setVisible(false);
			}
		});
		ImageIcon ajouter =new ImageIcon(this.getClass().getResource("/ajoutm.png"));
		btnNewButton_3.setIcon(ajouter);
		btnNewButton_3.setBounds(520, 101, 160, 210);
		btnNewButton_3.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		panel.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 531, 1242, 146);
		panel.add(scrollPane);
		scrollPane.setVisible(false);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recuperer_champs_du_tableau();
				
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"CIN", "Nom et pr\u00E9nom", "Date de naissance", "N\u00B0 telephone", "Adresse", "Date de recrutement", "Sp\u00E9cialit\u00E9", "Categorie permis", "Nombre des seances maximales", "Prix seance", "Salaire"
			}
		));
		scrollPane.setViewportView(table);
		
		txtFicheDePaiement = new JTextField();
		txtFicheDePaiement.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtFicheDePaiement.setText("Fiche de paiement");
		txtFicheDePaiement.setBounds(1057, 177, 144, 15);
		panel.add(txtFicheDePaiement);
		txtFicheDePaiement.setColumns(10);
		txtFicheDePaiement.setVisible(false);
		
		JButton btnNewButton_4 = new JButton("Afficher les donn\u00E9es des moniteurs");
		btnNewButton_4.setBackground(new Color(204, 204, 255));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				scrollPane.setVisible(true);
				actualiser();//Afficher tous les informations sur tous les moniteurs
				
				
			}
		});
		btnNewButton_4.setForeground(new Color(0, 0, 204));
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_4.setBounds(10, 482, 382, 30);
		panel.add(btnNewButton_4);
		
		JLabel lblNewLabel_16 = new JLabel("");
		lblNewLabel_16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Menu frame1;
					frame1 = new Menu(etat_son);
					frame1.setVisible(true);
				    dispose();//fermer la fenetre
			}
		});
		ImageIcon retour =new ImageIcon(this.getClass().getResource("/retour.png"));
		lblNewLabel_16.setIcon(retour);
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setBounds(23, 10, 64, 57);
		panel.add(lblNewLabel_16);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtFicheDePaiement.setVisible(false);
			}
		

			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBorder(BorderFactory.createLineBorder(Color.black));
				txtFicheDePaiement.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtFicheDePaiement.setVisible(false);
			}
		});
		ImageIcon paiement =new ImageIcon(this.getClass().getResource("/paim.png"));
		btnNewButton.setIcon(paiement);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(Cin.getText().equals(""))
				 {
					 message("Vous devez saisir le numéro du carte cin ", false);
				 }
				 else {
				 btnRecherche.setVisible(true);
				 recherche.setVisible(true);
				 scrollPane_1.setVisible(true);
				 AfficherPaiement();//Afficher tous les fiches de paiement d'un moniteur spécifique
				 }
				
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 204));
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnNewButton.setBounds(1041, 101, 160, 210);
		panel.add(btnNewButton);
		
		Prix_seance = new JTextField();
		Prix_seance.setText("");
		Prix_seance.setFont(new Font("Tahoma", Font.BOLD, 11));
		Prix_seance.setColumns(10);
		Prix_seance.setBounds(215, 402, 120, 20);
		panel.add(Prix_seance);
		Prix_seance.setText("0.0");
		
		 lblPrixDuneSance = new JLabel("Prix d'une s\u00E9ance:");
		lblPrixDuneSance.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrixDuneSance.setBounds(10, 402, 168, 20);
		panel.add(lblPrixDuneSance);
		String spe=Specialite.getSelectedItem().toString();
		
		
		lblSalaire = new JLabel("Salaire:");
		lblSalaire.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalaire.setBounds(10, 402, 168, 20);
		panel.add(lblSalaire);
		
		
		
		lblSalaire.setVisible(false);
	
		recherche = new JTextField();
		
		recherche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_7.setVisible(false);
			}
		});
		recherche.setText("");
		recherche.setFont(new Font("Tahoma", Font.BOLD, 11));
		recherche.setColumns(10);
		recherche.setBounds(691, 355, 120, 20);
		panel.add(recherche);
		recherche.setVisible(false);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(568, 398, 597, 106);
		panel.add(scrollPane_1);
		scrollPane_1.setVisible(false);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"moniteur", "mois", "salaire"
			}
		));
		scrollPane_1.setViewportView(table_1);
		
		btnRecherche = new JButton("Recherche");
		ImageIcon recherche =new ImageIcon(this.getClass().getResource("/rech.png"));
		btnRecherche.setIcon(recherche);
		btnRecherche.setBackground(new Color(204, 204, 255));
		btnRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 rechercher();//Chercher tous les fiches de paiement des moniteurs selon une date précise 
				 
				
			}
		});
		btnRecherche.setVisible(false);
		btnRecherche.setForeground(new Color(0, 0, 204));
		btnRecherche.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRecherche.setBounds(843, 350, 200, 30);
		panel.add(btnRecherche);
		
		label = new JLabel("");
		ImageIcon mute =new ImageIcon(this.getClass().getResource("/off.png"));
		label.setIcon(mute);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(1163, 10, 64, 57);
		panel.add(label);
		label_1 = new JLabel("");
		ImageIcon son =new ImageIcon(this.getClass().getResource("/on.png"));
		label_1.setIcon(son);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(1163, 10, 64, 57);
		panel.add(label_1);
		
		
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label.setVisible(false);
				label_1.setVisible(true);
				etat_son="on";
			}
		});
		
		
		
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_1.setVisible(false);
				label.setVisible(true);
				etat_son="off";
			}
		});
		
		
		
		 lblNewLabel_7 = new JLabel("Forme: yyyy-mm");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_7.setForeground(new Color(255, 51, 0));
		lblNewLabel_7.setBounds(691, 375, 120, 15);
		panel.add(lblNewLabel_7);
		
		lblNewLabel_11 = new JLabel("");// Bouton pour accéder à la page help
		lblNewLabel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Help h= new Help("moniteur");
				h.setVisible(true);
				
			}
		});
		ImageIcon help =new ImageIcon(this.getClass().getResource("/help.png"));
		lblNewLabel_11.setIcon(help);
		lblNewLabel_11.setBounds(1098, 10, 72, 57);
		panel.add(lblNewLabel_11);
        lblNewLabel_7.setVisible(false);
		etat_son();//gérer l'etat sonore de l'interface
		}
	public float Salaire(String c)//Calculer le salaire d'un moniteur dont sa specialité est conduite  à partir de sa carte d'identité   
	{
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue()-1;
		String m=Integer.toString(month);
		int count=0;
		try
		{
			
		String sql="select count(*) as c from seance_moniteur where MONTH(date_heure)=? and cin=? and specialite=?";
		prepared  =cnx.prepareStatement(sql);
		prepared.setString(1, m);
		prepared.setString(2, c);
		prepared.setString(3, "Conduite");
		resultat=prepared.executeQuery();
		if (resultat.next())
		{
			 count=resultat.getInt("c");	 
		}
		
		}catch(Exception e1)
		{
			e1.printStackTrace();
	}
		float prix=Float.parseFloat(Prix_seance.getText());
		float salaire=prix*count;
		return salaire;
	}
	public void recuperer_champs_du_tableau()//Récupérer les informations  d'un moniteur en cliquant sur une ligne du tableau
	{
		Date date;
		int ligne =table.getSelectedRow();
		 nom_prenom = table.getModel().getValueAt(ligne, 1).toString();
		 cin = Integer.parseInt(table.getModel().getValueAt(ligne, 0).toString());
		 telephone =Integer.parseInt( table.getModel().getValueAt(ligne, 3).toString());
		 adresse = table.getModel().getValueAt(ligne, 4).toString();
		 specialite = table.getModel().getValueAt(ligne, 6).toString();
		 categorie = table.getModel().getValueAt(ligne, 7).toString();
		 max_seances = Integer.parseInt(table.getModel().getValueAt(ligne, 8).toString());
		 prix_seance = Float.parseFloat(table.getModel().getValueAt(ligne, 9).toString());
		 salaire = Float.parseFloat(table.getModel().getValueAt(ligne, 10).toString());
		
		
		try {
			 date= new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 2).toString());
			date_de_naissance.setDate(date);
			Date date1= new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 5).toString());
			date_de_recrutement.setDate(date1);
		}
		catch(ParseException ex)
		{
			Logger.getLogger(Moniteur.class.getName()).log(Level.SEVERE,null,ex);
		}
		Nom_prenom.setText(nom_prenom);
		Cin.setText(String.valueOf(cin));
		Telephone.setText(String.valueOf(telephone));
		Adresse.setText(adresse);
        max_seance.setText(String.valueOf(max_seances));
		Categorie_permis.setSelectedItem(categorie);
		Specialite.setSelectedItem(specialite);
		if(specialite.equals("Code"))
 		{
 			Prix_seance.setText(String.valueOf(salaire));
 			lblSalaire.setVisible(true);
 			lblPrixDuneSance.setVisible(false);
 		}
 		if(specialite.equals("Conduite"))
 		{
 			Prix_seance.setText(String.valueOf(prix_seance));
 			lblSalaire.setVisible(false);
 			lblPrixDuneSance.setVisible(true);
 		}
	}
		public void actualiser()//Afficher les données de tous les moniteurs dans le tableau
		{
			String sql="select * from moniteur";
			try {
				
				
				prepared  =cnx.prepareStatement(sql);
				resultat=prepared.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(resultat));
			}catch(Exception e1)
			{
				e1.printStackTrace();
		}
		}
		
		

		public void reinitialiser()//remise des champs à leurs valeurs initiales
		{
			Cin.setText("");
			Telephone.setText("");
			Adresse.setText("");
			Telephone.setText("");
			Nom_prenom.setText("");
			max_seance.setText("0");
			Prix_seance.setText("0.0");
			Date date=new Date();
			((JTextField)date_de_naissance.getDateEditor().getUiComponent()).setText("");
			date_de_recrutement.setDate(date);
			Specialite.setSelectedIndex(0);
			Categorie_permis.setSelectedIndex(0);
			}
		
		
		public void recuperer_Champs()//Récupérer les informations d'un moniteur à partir de la saisie de sa carte cin 
		{
			 
				cin =Integer.parseInt(Cin.getText().toString());
				String sql="select * from moniteur where cin=? ";
				try {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1, String.valueOf(cin));
					resultat=prepared.executeQuery();
					if(resultat.next())
					{
						nom_prenom=resultat.getString("nom_prenom");
						Nom_prenom.setText(nom_prenom);
						naissance=resultat.getString("date_naissance");
						((JTextField)date_de_naissance.getDateEditor().getUiComponent()).setText(naissance);
						 telephone=Integer.parseInt(resultat.getString("telephone"));
						Telephone.setText( String.valueOf(telephone));
						 adresse=resultat.getString("adresse");
						Adresse.setText(adresse);
						recrutement=resultat.getString("date_recrutement");
						((JTextField)date_de_recrutement.getDateEditor().getUiComponent()).setText(recrutement);
						 categorie=resultat.getString("categorie_permis");
						Categorie_permis.setSelectedItem(categorie);
						 specialite=resultat.getString("specialite");
						Specialite.setSelectedItem(specialite);
						
						 max_seances=Integer.parseInt(resultat.getString("max_seance"));
						max_seance.setText( String.valueOf(max_seances));
					}
				}
				catch(SQLException p) {
					p.printStackTrace();
				}
			

		}
		public void modifier()//Modifier les informations d'un moniteur déja existant 
		{
			recrutement  = ((JTextField)date_de_recrutement.getDateEditor().getUiComponent()).getText().toString();
			naissance  = ((JTextField)date_de_naissance.getDateEditor().getUiComponent()).getText().toString();
			String sql="update moniteur set nom_prenom=?,telephone=?,adresse=?,date_naissance=?,date_recrutement=?,categorie_permis=?,max_seance=?,prix_seance=?,specialite=? ,salaire=? where cin=? ";
			   
			   
			   try {
			    	prepared=cnx.prepareStatement(sql);
			    	prepared.setString(1, Nom_prenom.getText().toString());
			    	prepared.setString(11, Cin.getText().toString());
			    	prepared.setString(4, naissance);
			    	prepared.setString(2, Telephone.getText().toString());
			    	prepared.setString(3, Adresse.getText().toString());
			    	prepared.setString(5, recrutement);
			    	prepared.setString(6, Categorie_permis.getSelectedItem().toString());
			    	
			    	prepared.setString(7, max_seance.getText().toString());
			    	prepared.setString(9, Specialite.getSelectedItem().toString());
			    	if(Specialite.getSelectedItem().toString().equals("Code"))
			    	{
			    		prepared.setString(10, Prix_seance.getText().toString());
				    	prepared.setString(8, "0.0");
			    	}
			    	if(Specialite.getSelectedItem().toString().equals("Conduite"))
			    	
			    	{
			    		prepared.setString(8, Prix_seance.getText().toString());
			    		
			    		
				    	prepared.setString(10, String.valueOf(Salaire( Cin.getText().toString())));
			    	}
			    	
			    	
			    	
			    	
			    	prepared.executeUpdate();
			    	
			    	message("Moniteur modifié.",true);
			    	reinitialiser();
			    	actualiser();
			    	
			}catch(SQLException e1)
			    {
				e1.printStackTrace();
			    }
		}
		public boolean cinExiste()//Vérifier l'existance d'un moniteur à partir de sa carte d'identité
		{
			
			cin=Integer.parseInt(Cin.getText().toString());
			try {
				String sql="select * from moniteur where cin=?";
				prepared=cnx.prepareStatement(sql);
				
		    	prepared.setString(1, String.valueOf(cin));
		    	resultat=prepared.executeQuery();
		    
		    	if(resultat.next())
		    	{
		    		
		    		return true;
		    	}
                }catch(SQLException se){
			      
			      se.printStackTrace();
			      }
			
			return false;
		}
		public boolean verifier()//Supprimer un moniteur déja existant 
		{
			
			
			  boolean v;
				v=true;
				
			
		   cin=Integer.parseInt(Cin.getText().toString());
		   if((String.valueOf(cin).chars().allMatch( Character::isDigit )==false)||(String.valueOf(cin).length()!=8)||(String.valueOf(cin).equals("")))
			{
				message("Vérifiez votre carte d'identité.",false);
				lblNewLabel_14.setVisible(true);
				v=false;
			}
			
		   else
			{
				 nom_prenom=Nom_prenom.getText().toString();
				
				if((nom_prenom.matches("^[A-Z]|[a-z]*$")==false)||(nom_prenom.equals("")))
				{
					message("Vérifiez votre nom et prenom.",false);
					v=false;
				}
			    else
			    {
		           recrutement  = ((JTextField)date_de_naissance.getDateEditor().getUiComponent()).getText().toString();
			       if(recrutement.equals(""))
			        {
				     message("Vérifiez votre date de naissance.",false);
			        }
			       else
			       {
			         telephone=Integer.parseInt(Telephone.getText().toString());
			        if((String.valueOf(telephone).chars().allMatch( Character::isDigit )==false)||(String.valueOf(telephone).length()!=8)||(String.valueOf(telephone).equals("")))
			         {
				     message("vérifier votre numéro de téléphone",false);
				     lblNewLabel_15.setVisible(true);
				     v=false;
			         }
			        else
			        {
			          adresse=Adresse.getText().toString();
			         if((adresse.equals("")))
			         {
				      message("Vérifier votre adresse",false);
				      v=false;
				     }
			         else
			         {
			          max_seances=Integer.parseInt(max_seance.getText().toString());
			         if((String.valueOf(max_seances).chars().allMatch( Character::isDigit )==false)||(String.valueOf(max_seances).matches("[0]+")==true))
			             {
				          message("Vérifiez votre nombre des séances maximale à travailler.",false);
				          v=false;
				         }
			         else
			         {
			        	  prix_seance=Float.parseFloat(Prix_seance.getText().toString());
			        	  specialite=Specialite.getSelectedItem().toString();
			        	 if((String.valueOf(prix_seance).matches("[0]+|[0]+.[0]+"))||(String.valueOf(prix_seance).matches("[0-9]+.[0-9]+|[0-9]+")==false))
			        	 {
			        		 if(specialite.equals("Code"))
			        		 {
			        			 message("Vérifiez votre salaire.",false);
			        		 }
			        		 else
			        		 {
			        			 message("Vérifiez votre prix de séance.",false);
			        		 }
			        		 v=false;
			        	 }
			         }
			         }
			        }
			       }
			    }
			}
		 return v;
		  
		}
		public void supprimer()//Supprimer un moniteur déja existant 
		{
			cin=Integer.parseInt(Cin.getText().toString());
			String sql = "delete from moniteur where cin=?";

		    try {
		    	prepared=cnx.prepareStatement(sql);
		    	prepared.setString(1, String.valueOf(cin));
		    	prepared.executeUpdate();
		    	message("Moniteur suprimé.",true);
		    	reinitialiser();
		    	actualiser();
		    	
		}catch(SQLException e1)
		    {
			e1.printStackTrace();
		    }
	 
		}
		public void ajouter()//Ajouter un moniteur non existant 
		{
			nom_prenom=Nom_prenom.getText().toString();
			recrutement  = ((JTextField)date_de_recrutement.getDateEditor().getUiComponent()).getText().toString();
			naissance  = ((JTextField)date_de_naissance.getDateEditor().getUiComponent()).getText().toString();
			String sql="insert into moniteur(nom_prenom , cin, telephone, adresse, date_naissance,date_recrutement,categorie_permis,max_seance,prix_seance,specialite,salaire) values(?,?,?,?,?,?,?,?,?,?,?)";
			
			try {
				
				prepared=cnx.prepareStatement(sql);
		    	prepared.setString(1, nom_prenom);
		    	prepared.setString(2, Cin.getText().toString());
		    	prepared.setString(5, naissance);
		    	prepared.setString(3, Telephone.getText().toString());
		    	prepared.setString(4, Adresse.getText().toString());
		    	prepared.setString(6, recrutement);
		    	prepared.setString(7, Categorie_permis.getSelectedItem().toString());
		    	
		    	prepared.setString(8, max_seance.getText().toString());
		    	prepared.setString(10, Specialite.getSelectedItem().toString());
		    	if(Specialite.getSelectedItem().toString().equals("Conduite"))
		    	{
		    		prepared.setString(9, Prix_seance.getText().toString());
		    		prepared.setString(11, String.valueOf(Salaire( Cin.getText().toString())));
		    	}
		    	else
		    	{
		    		prepared.setString(9, "0.0");
		    		prepared.setString(11, Prix_seance.getText());
		    	}
		    	prepared.executeUpdate();
		    	message("Moniteur ajouté.",true);
		    	reinitialiser();// remise des champs à leurs valeurs initiales
		    	actualiser();//Afficher tous les fiches de tous les moniteurs dans un tableau
		    	
		}catch(SQLException e1)
		    {
			e1.printStackTrace();
		    }
		    }
		
		public boolean correction_date(String m)//Vérifier la date saisie si elle est sous cette forme yyyy:mm ou non
		{
			
			if(m.length()==7)
			{
			if((m.substring(0,4).chars().allMatch( Character::isDigit ))&&(m.substring(5,7).chars().allMatch( Character::isDigit ))&&(m.substring(4,5).equals("-")))
			{
				return true;
			}
			}
			return false;
		}

		public void rechercher()//Chercher tous les fiches de paiement d'une date spécifique
		{
            String m=recherche.getText().toString();
            if(correction_date(m))
            {
               try {
            	String sqle="select * from salaire_moniteur where date=?";
            	prepared  =cnx.prepareStatement(sqle);
				prepared.setString(1, m);
				resultat=prepared.executeQuery();
				if (resultat.next())
				{
					
				   String sql="select * from salaire_moniteur where date=?";
			       prepared  =cnx.prepareStatement(sql);
				   prepared.setString(1, m);
				   resultat=prepared.executeQuery();
				   table_1.setModel(DbUtils.resultSetToTableModel(resultat));
				}
				else {
					message("Il n'ya aucun paiement pour ce date",false);
					String sql="select * from salaire_moniteur where date=?";
				       prepared  =cnx.prepareStatement(sql);
					   prepared.setString(1, m);
					   resultat=prepared.executeQuery();
					   table_1.setModel(DbUtils.resultSetToTableModel(resultat));
				}
			   }catch(Exception e1)
			    {
				e1.printStackTrace();
		        }
            }
            else {
            	message("Verifiez la date saisie",false);
            	lblNewLabel_7.setVisible(true);
            	
            }
		}
		
		public void AfficherPaiement()// Afficher tous les fiches de paiement d'un moniteur spécifié
		{
			String sql="select * from salaire_moniteur where cin=?";
			try {
				
				
				prepared  =cnx.prepareStatement(sql);
				prepared.setString(1, Cin.getText().toString());
				resultat=prepared.executeQuery();
				
				table_1.setModel(DbUtils.resultSetToTableModel(resultat));
			
				
			}catch(Exception e1)
			{
				e1.printStackTrace();
		    }
          }
		
		public void message(String msg,boolean type)//Afficher un message de réussite ou d'echec pour informer l'utilisateur 
	    {
	    	if(label_1.isShowing())
			{
				Audio t = new Audio("audio",type);
				t.set_sound("on",type);
				t.start();
			    Audio t2 = new Audio("message",msg,type);
			    t2.start();
			}
			else if(label.isShowing())
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
			if(etat_son.equals("on"))
			{
			label.setVisible(false);
			label_1.setVisible(true);
			}
			else
			{
				label.setVisible(true);	
				label_1.setVisible(false);
			}
		}
}

