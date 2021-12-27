import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class Vehicule extends JFrame {

	private JPanel contentPane;
	private JTextField matField;
	private JTextField marqueField;
	private JTextField carburantField;
	private JTable table;
	private JDateChooser achatChooser;
	private JComboBox etatBox;
	private JComboBox typeBox;
	java.sql.Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private int n=0;
	private Vector v;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JButton btnNewButton;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JButton btnNewButton_9;
	private JScrollPane scrollPane;
	private String etat_son="on";
	private String matricule;
	private String marque;
	private String type;
	private String etat;
	private String date_achat;
	private float carburant;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
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
					Vehicule frame = new Vehicule("off");
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
	public Vehicule(String s) {
		etat_son=s;
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v=new Vector();
		addWindowListener(new WindowAdapter() //interdire la fermeture de la fenetre aprés l'ajout d'un véhicule sans ajouter ses frais
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if(n>0 && !v.isEmpty())
				{
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					ajout_frais(v.get(0).toString());//vérifier si les frais d'un nouveau véhicule sont ajoutés ou non
					if(v.get(1).equals(true))
					{
						if(v.get(2).equals(true))
						{
							if(v.get(3).equals(true))
							{
								setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							}
							else
								message("Vous devez ajouter la visite pour le nouveau véhicule ajouté.",false);
						}
						else
							message("Vous devez ajouter la vignette pour le nouveau véhicule ajouté.",false);
					}
					else
						message("Vous devez ajouter l'assurance pour le nouveau véhicule ajouté.",false);
				}
				else
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} 		
		});
		setTitle("Gestion des v\u00E9hicules");
		setBounds(100, 0, 1155, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(102, 102, 204));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		typeBox = new JComboBox();
		typeBox.setModel(new DefaultComboBoxModel(new String[] {"A1", "A", "B", "B+E", "C", "C+E", "D", "D+E", "H"}));
		typeBox.setBounds(249, 209, 218, 22);
		panel.add(typeBox);
		
		etatBox = new JComboBox();
		etatBox.setModel(new DefaultComboBoxModel(new String[] {"En bonne Etat", "En panne"}));
		etatBox.setBounds(249, 246, 218, 22);
		panel.add(etatBox);
		
		matField = new JTextField();
		matField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				recuperer_champs();//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ
			}
		});
		matField.setBounds(249, 135, 218, 20);
		panel.add(matField);
		matField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Matricule:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 138, 76, 14);
		panel.add(lblNewLabel);
		
		marqueField = new JTextField();
		marqueField.setBounds(249, 172, 218, 20);
		panel.add(marqueField);
		marqueField.setColumns(10);
		
		achatChooser = new JDateChooser();
		achatChooser.setDateFormatString("yyyy-MM-dd");
		achatChooser.setBounds(249, 283, 218, 20);
		panel.add(achatChooser);
		
		JLabel lblNewLabel_1 = new JLabel("Marque:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 175, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Type:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 213, 46, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_5 = new JLabel("Date d'achat:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 283, 76, 14);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Etat:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(10, 250, 46, 14);
		panel.add(lblNewLabel_6);
		
		carburantField = new JTextField();
		carburantField.setBounds(250, 320, 217, 20);
		panel.add(carburantField);
		carburantField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 491, 1100, 193);
		panel.add(scrollPane);
		scrollPane.setVisible(false);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recuperer_champs_du_table();////récupérer les informations du candidat en cliquant sur la ligne correspondante du tableau
				
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"matricule", "marque", "type", "etat", "date_achat", "carburant"
			}
		));
		scrollPane.setViewportView(table);
		
		btnNewButton_1 = new JButton("Ajouter");//bouton d'ajout du véhicule
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				textField.setVisible(false);
			}
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(Color.black));
				textField.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setVisible(false);
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!v.isEmpty() && !ajout_frais(v.get(0).toString()))//interdire l'ajout d'un autre véhicule s'il y un véhicule ajouté sans ajouter ses frais
				{
					if(v.get(1).equals(false))
					{
						message("Vous devez ajouter l'assurance pour le nouveau véhicule ajouté.",false);	
					}
					else if(v.get(2).equals(false))
					{
						message("Vous devez ajouter la vignette pour le nouveau véhicule ajouté.",false);
								
					}
					else if(v.get(3).equals(false))
					{
						message("Vous devez ajouter la visite pour le nouveau véhicule ajouté.",false);
					}	
				}
				else
				    ajouter();//ajouter un nouveau véhicule
			}
		});
		
		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setVisible(false);
		textField.setText("Ajouter un véhicule");
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(939, 156, 157, 20);
		panel.add(textField);
		textField.setColumns(10);
		ImageIcon ajout =new ImageIcon(this.getClass().getResource("/ajoutv.png"));
		btnNewButton_1.setIcon(ajout);
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		btnNewButton_1.setBounds(911, 74, 218, 123);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Modifier");//bouton de modification du véhicule
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_2.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				textField_1.setVisible(false);
			}
			public void mouseEntered(MouseEvent e) {
				btnNewButton_2.setBorder(BorderFactory.createLineBorder(Color.black));
				textField_1.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_1.setVisible(false);
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!v.isEmpty() && !ajout_frais(v.get(0).toString()))//interdire la modification d'un véhicule s'il y un véhicule ajouté sans ajouter ses frais
				{
					if(v.get(1).equals(false))
					{
						message("Vous devez ajouter l'assurance pour le nouveau véhicule ajouté.",false);	
					}
					else if(v.get(2).equals(false))
					{
						message("Vous devez ajouter la vignette pour le nouveau véhicule ajouté.",false);
								
					}
					else if(v.get(3).equals(false))
					{
						message("Vous devez ajouter la visite pour le nouveau véhicule ajouté.",false);
					}	
				}
				else
				    modifier();//modifier les information d'un véhicule
			}
		});
		ImageIcon modif =new ImageIcon(this.getClass().getResource("/modifv.png"));
		btnNewButton_2.setIcon(modif);
		btnNewButton_2.setBackground(new Color(173, 216, 230));
		
		textField_1 = new JTextField();
		textField_1.setVisible(false);
		textField_1.setText("Modifier un véhicule");
		textField_1.setBackground(new Color(255, 255, 255));
		textField_1.setBounds(939, 285, 157, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		btnNewButton_2.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		btnNewButton_2.setBounds(911, 202, 218, 123);
		panel.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("supprimer");//bouton de suppression du véhicule
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_3.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				textField_2.setVisible(false);
			}
			public void mouseEntered(MouseEvent e) {
				btnNewButton_3.setBorder(BorderFactory.createLineBorder(Color.black));
				textField_2.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_2.setVisible(false);
			}
		});
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!v.isEmpty() && !ajout_frais(v.get(0).toString()))//interdire la suppression d'un véhicule s'il y un véhicule ajouté sans ajouter ses frais
				{
					if(v.get(1).equals(false))
					{
						message("Vous devez ajouter l'assurance pour le nouveau véhicule ajouté.",false);	
					}
					else if(v.get(2).equals(false))
					{
						message("Vous devez ajouter la vignette pour le nouveau véhicule ajouté.",false);
								
					}
					else if(v.get(3).equals(false))
					{
						message("Vous devez ajouter la visite pour le nouveau véhicule ajouté.",false);
					}
				}
				else
				    supprimer();//supprimer un véhicule
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setVisible(false);
		textField_2.setText("Supprimer un véhicule");
		textField_2.setBackground(new Color(255, 255, 255));
		textField_2.setBounds(939, 407, 157, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		ImageIcon supp =new ImageIcon(this.getClass().getResource("/suppv.png"));
		btnNewButton_3.setIcon(supp);
		btnNewButton_3.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		btnNewButton_3.setBounds(911, 325, 218, 131);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Afficher les donne\u00E9s des vehicules");//afficher le tableau contenant les informations des véhicules 
		btnNewButton_4.setBackground(new Color(255, 255, 255));
		btnNewButton_4.setForeground(new Color(51, 102, 204));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
				actualiser();//actualiser le tableau
			}
		});
		btnNewButton_4.setBounds(355, 457, 417, 23);
		panel.add(btnNewButton_4);
		
		JLabel lblNewLabel_14 = new JLabel("Gestion des v\u00E9hicules");
		lblNewLabel_14.setForeground(new Color(51, 102, 204));
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel_14.setBounds(417, 1, 352, 46);
		panel.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("Consommation du carburant par heure:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_15.setBounds(10, 323, 229, 14);
		panel.add(lblNewLabel_15);
		
		btnNewButton = new JButton("Assurance");//ouvrir la page de l'assurance
		btnNewButton.setForeground(new Color(51, 102, 204));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule=matField.getText().toString();
				if(!matricule.equals("") && matricule_existe())//vérifier si un véhicule existe ou non 
				{
					Frais_vehicule veh=new Frais_vehicule(matricule,"Assurance",etat_son,get());
					veh.setVisible(true);
					veh.actualiser("Assurance",matricule);
				}
				else 
					message("Vous devez vérifier le matricule.",false);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(594, 137, 210, 23);
		panel.add(btnNewButton);
		
		btnNewButton_6 = new JButton("Vidange");//ouvrir la page de la vidange
		btnNewButton_6.setBackground(new Color(255, 255, 255));
		btnNewButton_6.setForeground(new Color(51, 102, 204));
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule=matField.getText().toString();
				if(!matricule.equals("") && matricule_existe())//vérifier si un véhicule existe ou non 
				{
					Frais_vehicule veh=new Frais_vehicule(matricule,"Vidange",etat_son,get());
					veh.setVisible(true);
					veh.actualiser("Vidange",matricule);
				}
				else 
				    message("Vous devez vérifier le matricule.",false);
			}
		});
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_6.setBounds(594, 183, 210, 23);
		panel.add(btnNewButton_6);
		
		btnNewButton_7 = new JButton("Vignette");//ouvrir la page de la vignette
		btnNewButton_7.setForeground(new Color(51, 102, 204));
		btnNewButton_7.setBackground(new Color(255, 255, 255));
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule=matField.getText().toString();
				if(!matricule.equals("") && matricule_existe())//vérifier si un véhicule existe ou non 
				{
					Frais_vehicule veh=new Frais_vehicule(matricule,"Vignette",etat_son,get());
					veh.setVisible(true);
					veh.actualiser("Vignette",matricule);
				}
				else 
					message("Vous devez vérifier le matricule.",false);
			}
		});
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_7.setBounds(594, 228, 210, 23);
		panel.add(btnNewButton_7);
		
		btnNewButton_8 = new JButton("Visite");//ouvrir la page de la visite
		btnNewButton_8.setBackground(new Color(255, 255, 255));
		btnNewButton_8.setForeground(new Color(51, 102, 204));
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule=matField.getText().toString();
				if(!matricule.equals("") && matricule_existe())//vérifier si un véhicule existe ou non 
				{
					Frais_vehicule veh=new Frais_vehicule(matricule,"Visite",etat_son,get());
					veh.setVisible(true);
					veh.actualiser("Visite",matricule);
				}
				else 
					message("Vous devez vérifier le matricule.",false);
			}
		});
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_8.setBounds(594, 270, 210, 23);
		panel.add(btnNewButton_8);
		
		btnNewButton_9 = new JButton("Frais suppl\u00E9mentaires");//ouvrir la page des frais supplémentaires
		btnNewButton_9.setForeground(new Color(51, 102, 204));
		btnNewButton_9.setBackground(new Color(255, 255, 255));
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule=matField.getText().toString();
				if(!matricule.equals("") && matricule_existe())//vérifier si un véhicule existe ou non 
				{
					Frais_vehicule veh=new Frais_vehicule(matricule,"Frais supplémentaires",etat_son,get());
					veh.setVisible(true);
					veh.actualiser("Frais supplémentaires",matricule);
				}
				else 
					message("Vous devez vérifier le matricule.",false);
			}
		});
		btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_9.setBounds(593, 311, 211, 23);
		panel.add(btnNewButton_9);
		
		JLabel lblNewLabel_7 = new JLabel("");//retour au menu
		lblNewLabel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu menu=new Menu(etat_son);
				menu.setVisible(true);
				dispose();
			}
		});
		ImageIcon retour =new ImageIcon(this.getClass().getResource("/retour.png"));
		lblNewLabel_7.setIcon(retour);
		lblNewLabel_7.setBounds(0, 0, 69, 64);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("");//ouvrir la page d'aide
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Help help=new Help("vehicule");
				help.setVisible(true);
			}
		});
		ImageIcon help_icon =new ImageIcon(this.getClass().getResource("/help.png"));
		lblNewLabel_8.setIcon(help_icon);
		lblNewLabel_8.setBounds(1060, 0, 69, 63);
		panel.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("on");//désactiver le son en cliquant sur ce bouton
		lblNewLabel_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_9.setVisible(false);
				lblNewLabel_10.setVisible(true);
				etat_son="off";
			}
		});
		ImageIcon on_icon =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_9.setIcon(on_icon);
		lblNewLabel_9.setBounds(1000, 0, 73, 63);
		panel.add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("off");//activer le son en cliquant sur ce bouton
		lblNewLabel_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_10.setVisible(false);
				lblNewLabel_9.setVisible(true);
				etat_son="on";
			}
		});
		ImageIcon off_icon =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_10.setIcon(off_icon);
		lblNewLabel_10.setBounds(1000, 0, 61, 63);
		panel.add(lblNewLabel_10);
		etat_son();
	}
	public void actualiser()//actualiser le tableau
	{
		String sql="select * from vehicule";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		
	}
	public void reinitialiser()//réinitialiser les champs 
	{
		actualiser();
		matField.setText("");
		marqueField.setText("");
		((JTextField)achatChooser.getDateEditor().getUiComponent()).setText("");
		carburantField.setText("");
	}
	public boolean matricule_existe()//vérifier si un véhicule existe ou non 
    {
		matricule=matField.getText().toString();
    	String sql="select matricule from vehicule";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			while(resultat.next())
			{
				String mat=resultat.getString("matricule");
				if(mat.equals(matricule))
				{
					return(true);
				}
			}
			
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return (false);
    }
	public void ajouter()//ajouter un nouveau véhicule
	{	
		matricule=matField.getText().toString();
		marque=marqueField.getText().toString();
		type=typeBox.getSelectedItem().toString();
		etat=etatBox.getSelectedItem().toString();
		date_achat=((JTextField)achatChooser.getDateEditor().getUiComponent()).getText();
		carburant=Float.parseFloat(carburantField.getText().toString());
		if(matricule_existe())
		{
			message("Le matricule existe déja",false);
		}
		else {
		String sql="insert into vehicule(matricule,marque,type,etat,date_achat,carburant) values (?,?,?,?,?,?)";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(3, type);
			prepared.setString(4, etat);
			if(!matricule.equals(""))
			{
				prepared.setString(1, matricule);
				if(!marque.equals(""))
				{
					prepared.setString(2, marque);
					if(!date_achat.equals(""))
					{
						prepared.setString(5, date_achat);
						if((Float.toString(carburant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!Float.toString(carburant).matches("[0]+|[0]+.[0]+")))
						{
							prepared.setString(6, Float.toString(carburant));
							prepared.execute();
							message("Véhicule ajouté avec succés",true);
							n++;
							v.add(matricule);
							actualiser();
						}
						else
							message("Vérifiez la consommation du carburant par heure.",false);
					}
					else
						message("Vérifiez la date d'achat.",false);
				}
				else
					message("Vérifiez la marque du véhicule",false);
			}
			else
				message("Vérifiez le matricule du véhicule.",false);
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		}
	}
	public void supprimer()//supprimer un véhicule
	{
		matricule =matField.getText().toString();
		if(matricule.equals(""))
		{
			message("Ecrivez le matricule de la véhicule.",false);
		}
		
		else {
			if(matricule_existe()) {
		    String sql="delete from vehicule where matricule=?";
		    try {
			     prepared=cnx.prepareStatement(sql);
			     prepared.setString(1, matricule);
			     prepared.execute();
			     String sql1="delete from frais_vehicule where matricule=?";
			     prepared=cnx.prepareStatement(sql1);
			     prepared.setString(1, matricule);
			     prepared.execute();
			     message("Véhicule supprimé avec succés.",true);
			     reinitialiser();
		        }
		    catch(SQLException p)
		    {
			    p.printStackTrace();
		    }
			}
			else
				message("Le matricule n'existe pas.",false);
		}
	}
	public void modifier()//modifier les information d'un véhicule
	{
		matricule=matField.getText().toString();
		marque=marqueField.getText().toString();
		type=typeBox.getSelectedItem().toString();
		etat=etatBox.getSelectedItem().toString();
		date_achat=((JTextField)achatChooser.getDateEditor().getUiComponent()).getText();
		carburant=Float.parseFloat(carburantField.getText().toString());
	
		if(!matricule_existe())
		{
			message("Le matricule n'existe pas.",false);
		}
		else {
		String sql="update vehicule set marque=?,type=?,etat=?,date_achat=?,carburant=? where matricule=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(2, type);
			prepared.setString(3, etat);
			prepared.setString(6, matricule);
			if(!marque.equals(""))
			{
				prepared.setString(1, marque);
				if(!date_achat.equals(""))
				{
					prepared.setString(4, date_achat);
					if((Float.toString(carburant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!Float.toString(carburant).matches("[0]+|[0]+.[0]+")))
					{
						prepared.setString(5, Float.toString(carburant));
						prepared.execute();
						message("Véhicule modifié avec succés.",true);
						reinitialiser();
					}
					else
						message("Vérifiez la consommation du carburant par heure.",false);
				}
				else
					message("Vérifiez la date d'achat.",false);
			}
			else
				message("Vérifiez la marque du véhicule.",false);
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		}
	}
	public boolean ajout_frais(String mat)//vérifier si les frais d'un nouveau véhicule sont ajoutés ou non
	{   
		v.add(false);
		v.add(false);
		v.add(false);
		String sql="select * from frais_vehicule where matricule=? and type_frais=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1, mat);
			prepared.setString(2, "Assurance");
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				v.set(1, true);
			}
			String sql1="select * from frais_vehicule where matricule=? and type_frais=?";
			prepared=cnx.prepareStatement(sql1);
			prepared.setString(1, mat);
			prepared.setString(2, "Vignette");
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				v.set(2, true);
			}
			String sql2="select * from frais_vehicule where matricule=? and type_frais=?";
			prepared=cnx.prepareStatement(sql2);
			prepared.setString(1, mat);
			prepared.setString(2, "Visite");
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				v.set(3, true);
			}
			if(v.get(1).equals(true) && v.get(2).equals(true) && v.get(3).equals(true))
			{
				v.clear();
				return(true);
			}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return(false);
	}
	public void recuperer_champs()//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ
	{
		matricule=matField.getText().toString();
		String sql="select marque,type,etat,date_achat,carburant from vehicule where matricule=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1, matricule);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				String marque=resultat.getString("marque");
				marqueField.setText(marque);
				String type=resultat.getString("type");
				typeBox.setSelectedItem(type);
				String etat=resultat.getString("etat");
				etatBox.setSelectedItem(etat);
				String achat=resultat.getString("date_achat");
				((JTextField)achatChooser.getDateEditor().getUiComponent()).setText(achat);
				String car=resultat.getString("carburant");
				carburantField.setText(car);
			}
			
		}
		catch(SQLException p) {
			p.printStackTrace();
		}
	}
	public void recuperer_champs_du_table()//récupérer les informations du candidat en cliquant sur la ligne correspondante du tableau
	{
		int ligne =table.getSelectedRow();
		matricule=table.getModel().getValueAt(ligne, 0).toString();
		marque=table.getModel().getValueAt(ligne, 1).toString();
		type=table.getModel().getValueAt(ligne, 2).toString();
		etat=table.getModel().getValueAt(ligne, 3).toString();
		String carbur=table.getModel().getValueAt(ligne, 5).toString();
		matField.setText(matricule);
		marqueField.setText(marque);
		typeBox.setSelectedItem(type);
		etatBox.setSelectedItem(etat);
		carburantField.setText(carbur);
		try {
			Date achat= new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 4).toString());
			achatChooser.setDate(achat);
		}
		catch(ParseException ex)
		{
			Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	public void message(String msg,boolean type)//donner un message sonore selon son type (succès ou echec)
    {
    	if(lblNewLabel_9.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("on",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
		else if(lblNewLabel_10.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("off",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
    }
	public void etat_son()//configurer l'état sonore de l'interafce à l'ouverture de la page
	{
		if(etat_son=="on")
		{
			lblNewLabel_9.setVisible(true);
			lblNewLabel_10.setVisible(false);
		}
		else if(etat_son=="off")
		{
			lblNewLabel_10.setVisible(true);
			lblNewLabel_9.setVisible(false);
		}
	}
	public Vehicule get()//donner l'objet courant
	{
		return(this);
	}
}
