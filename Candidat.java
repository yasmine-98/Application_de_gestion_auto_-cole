import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class Candidat extends JFrame {


	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	
	private JTable table;
	private JPanel contentPane;
	private JTextField cinField;
	private JTextField npField;
	private JTextField telField;
	private JTextField adressField;

	private JTextField fraisField;
	private JTextField payField;
	private JTextField nbcondField;
	private JTextField codeField;
	private JTextField nbcodeField;
	private JTextField pcodeField;
	private JTextField pcondField;
	private JTextField conduiteField;
	private JTextField forfaitField;
	private JTextField resteField;
	private JTextField sommeField;
	private JTextField txtCliquerPourModifier;
	private JTextField txtCliquerPourSupprimer;
	private JLabel lblNewLabel_28;
	private JLabel lblNewLabel_27;
	private JComboBox paiementBox;
	private JComboBox catBox;
	private JComboBox typeBox;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private JTextField txtCliquerPourAjouter;
	private String etatC="on";
	private long cin ;
	private	String nom_prenom ;
	private long tel ;
	private String adresse ;
	private	String datei;
	private String dateN ;
	private	String type ;
	private	String categorie ;
	private	String paiement ;
	private	float prixcode ;
	private	float prixcond ;
	private	int nbcode ;
	private	int nbcond ;
	private float code;
	private float cond;
	private float montant ;
	private float frais;
	private float somme;
	private float reste;
	private float paye;
   
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
					Candidat frame = new Candidat("off");
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
	public Candidat(String etat) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		etatC=etat;// l’état sonore deja choisi par l'utilisteur
		setResizable(false);
		setTitle("Gestion des candidats");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1370, 730);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB();//la connexion avec la base
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CIN du candidat:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 71, 103, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Cat\u00E9gorie du permis:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 309, 118, 14);
		panel.add(lblNewLabel_4);
		
		catBox = new JComboBox();
		catBox.setModel(new DefaultComboBoxModel(new String[] {"A1", "A", "B", "B+E", "C", "C+E", "D", "D+E", "H"}));
		catBox.setSelectedIndex(0);
		catBox.setBounds(137, 305, 259, 22);
		panel.add(catBox);
		
		JLabel lblNewLabel_5 = new JLabel("Type d'inscription:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 350, 118, 14);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_22 = new JLabel("Prix du code:");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_22.setBounds(517, 108, 84, 14);
		panel.add(lblNewLabel_22);
		
		JLabel lblNewLabel_7 = new JLabel("Nombre des seances:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setBounds(517, 148, 119, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_10 = new JLabel("Prix d'examen code:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_10.setBounds(517, 189, 119, 14);
		panel.add(lblNewLabel_10);
		
		codeField = new JTextField();
		codeField.setEnabled(false);
		codeField.setText("0.0");
		codeField.setBounds(646, 186, 80, 20);
		panel.add(codeField);
		codeField.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("Prix d'examen conduite:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_11.setBounds(517, 350, 143, 14);
		panel.add(lblNewLabel_11);
		
		conduiteField = new JTextField();
		conduiteField.setEnabled(false);
		conduiteField.setText("0.0");
		conduiteField.setBounds(661, 347, 80, 20);
		panel.add(conduiteField);
		conduiteField.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("-Code:");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_12.setBounds(517, 69, 58, 14);
		panel.add(lblNewLabel_12);
		
		pcodeField = new JTextField();
		pcodeField.setEnabled(false);
		pcodeField.setText("0.0");
		pcodeField.setBounds(646, 105, 80, 20);
		panel.add(pcodeField);
		pcodeField.setColumns(10);
		
		nbcodeField = new JTextField();
		nbcodeField.setEnabled(false);
		nbcodeField.setText("0");
		nbcodeField.setBounds(646, 145, 80, 20);
		panel.add(nbcodeField);
		nbcodeField.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("-Conduite:");
		lblNewLabel_13.setForeground(Color.WHITE);
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_13.setBounds(517, 224, 111, 14);
		panel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_21 = new JLabel("Nombre des seances:");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_21.setBounds(517, 309, 134, 14);
		panel.add(lblNewLabel_21);
		
		nbcondField = new JTextField();
		nbcondField.setEnabled(false);
		nbcondField.setText("0");
		nbcondField.setBounds(661, 306, 80, 20);
		panel.add(nbcondField);
		nbcondField.setColumns(10);
		
		pcondField = new JTextField();
		pcondField.setEnabled(false);
		pcondField.setText("0.0");
		pcondField.setBounds(661, 265, 80, 20);
		panel.add(pcondField);
		pcondField.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Prix d'une seance:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_8.setBounds(517, 268, 111, 14);
		panel.add(lblNewLabel_8);
		
		typeBox = new JComboBox();
		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = typeBox.getSelectedItem().toString();
				pcondField.setEnabled(false);
				nbcondField.setEnabled(false);
				conduiteField.setEnabled(false);
				nbcodeField.setEnabled(false);
				pcodeField.setEnabled(false);
				codeField.setEnabled(false);
				//Pour afficher seulement les champs lié au type choisi
				if(type.equals("Code"))
				{
					pcodeField.setEnabled(true);
					nbcodeField.setEnabled(true);
					codeField.setEnabled(true);
				}
				if(type.equals("Code et conduite"))
				{
					pcondField.setEnabled(true);
					nbcondField.setEnabled(true);
					conduiteField.setEnabled(true);
					
					nbcodeField.setEnabled(true);
					pcodeField.setEnabled(true);
					codeField.setEnabled(true);
					
				}
				if(type.equals("Conduite"))
				{
					pcondField.setEnabled(true);
					nbcondField.setEnabled(true);
					conduiteField.setEnabled(true);
				}
				if(type.equals("Conduite sans examen"))
				{
					pcondField.setEnabled(true);
					nbcondField.setEnabled(true);
				}
			}
		});
		typeBox.setModel(new DefaultComboBoxModel(new String[] {"--", "Code", "Conduite", "Code et conduite", "Conduite sans examen"}));
		typeBox.setBounds(137, 346, 259, 22);
		panel.add(typeBox);
		
		JLabel lblNewLabel_6 = new JLabel("Paiement:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(10, 391, 118, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_16 = new JLabel("- Forfait:");
		lblNewLabel_16.setEnabled(false);
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_16.setBounds(841, 69, 97, 14);
		panel.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Montant:");
		lblNewLabel_17.setEnabled(false);
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_17.setBounds(845, 108, 64, 14);
		panel.add(lblNewLabel_17);
		
		forfaitField = new JTextField();
		forfaitField.setEnabled(false);
		forfaitField.setText("0.0");
		forfaitField.setBounds(950, 105, 80, 20);
		panel.add(forfaitField);
		forfaitField.setColumns(10);
		
	    paiementBox = new JComboBox();
		paiementBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_17.setEnabled(false);
				forfaitField.setEnabled(false);
				lblNewLabel_16.setEnabled(false);
				String p = paiementBox.getSelectedItem().toString();
				// Pour afficher le champ du forfait au cas convenable
				if(p.equals("forfait"))
				{
					lblNewLabel_16.setEnabled(true);
					lblNewLabel_17.setEnabled(true);
					forfaitField.setEnabled(true);
				}
			}
		});
		paiementBox.setModel(new DefaultComboBoxModel(new String[] {"sans forfait", "forfait"}));
		paiementBox.setBounds(137, 387, 259, 22);
		panel.add(paiementBox);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(140, 264, 256, 20);
		Date date = new Date();
		dateChooser.setDate(date);
		panel.add(dateChooser);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(137, 146, 259, 20);
		panel.add(dateChooser_1);
		
		cinField= new JTextField();
		cinField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {	
				recuperer_champs();//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ
				}
			});
		cinField.setBounds(137, 68, 259, 20);
		panel.add(cinField);
		cinField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nom et pr\u00E9nom:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 108, 97, 14);
		panel.add(lblNewLabel_1);
		
		npField = new JTextField();
		npField.setBounds(137, 105, 259, 20);
		panel.add(npField);
		npField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("N\u00B0 t\u00E9l\u00E9phone:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 189, 97, 14);
		panel.add(lblNewLabel_2);
		
		telField = new JTextField();
		telField.setBounds(137, 182, 259, 20);
		panel.add(telField);
		telField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Adresse:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 226, 103, 14);
		panel.add(lblNewLabel_3);
		
		adressField = new JTextField();
		adressField.setBounds(137, 223, 259, 20);
		panel.add(adressField);
		adressField.setColumns(10);
		
		txtCliquerPourAjouter = new JTextField();
		txtCliquerPourAjouter.setBorder(null);
		txtCliquerPourAjouter.setVisible(false);
		txtCliquerPourAjouter.setBackground(new Color(176, 196, 222));
		txtCliquerPourAjouter.setText(" Cliquer pour ajouter un candidat.");
		txtCliquerPourAjouter.setBounds(1081, 145, 195, 20);
		panel.add(txtCliquerPourAjouter);
		txtCliquerPourAjouter.setColumns(10);
		
		txtCliquerPourModifier = new JTextField();
		txtCliquerPourModifier.setBorder(null);
		txtCliquerPourModifier.setBackground(new Color(176, 196, 222));
		txtCliquerPourModifier.setText(" Cliquer pour modifier un condidat.");
		txtCliquerPourModifier.setToolTipText("");
		txtCliquerPourModifier.setBounds(1081, 280, 195, 20);
		panel.add(txtCliquerPourModifier);
		txtCliquerPourModifier.setColumns(10);
		txtCliquerPourModifier.setVisible(false);
		
		txtCliquerPourSupprimer = new JTextField();
		txtCliquerPourSupprimer.setBorder(null);
		txtCliquerPourSupprimer.setBackground(new Color(176, 196, 222));
		txtCliquerPourSupprimer.setText("Cliquer pour supprimer.");
		txtCliquerPourSupprimer.setBounds(1081, 417, 195, 20);
		panel.add(txtCliquerPourSupprimer);
		txtCliquerPourSupprimer.setColumns(10);
		txtCliquerPourSupprimer.setVisible(false);

		
		JButton btnNewButton_1 = new JButton("Modifier");
		btnNewButton_1.setBackground(new Color(173, 216, 230));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCliquerPourModifier.setVisible(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				txtCliquerPourModifier.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtCliquerPourModifier.setVisible(false);
			}
		});
		btnNewButton_1.setBorder(null);
		ImageIcon edit =new ImageIcon(this.getClass().getResource("/modifc.png"));
        btnNewButton_1.setIcon(edit);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 modifier();//permet la modification des informations d'un candidat.
			}
		});
		btnNewButton_1.setBounds(1146, 224, 118, 131);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Supprimer");
		btnNewButton_2.setBackground(new Color(173, 216, 230));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCliquerPourSupprimer.setVisible(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				txtCliquerPourSupprimer.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtCliquerPourSupprimer.setVisible(false);
			}
		});
		btnNewButton_2.setBorder(null);
		ImageIcon delete =new ImageIcon(this.getClass().getResource("/suppc.png"));
		btnNewButton_2.setIcon(delete);
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cinExiste())//vérifie l'existence du candidat
				{
					//afficher un message au utilisateur
					message("Vous devez saisir le numéro de cin du candidat ou séléctionner à partir du tableau un candidat.",false);
				}
				else//si le candidat existe l'utilisateur peut le supprimer
				{
					supprimer();//permet la suppression d'un candidat à travers son numéro de CIN et ses séances 
				}
				
			}
		});
		btnNewButton_2.setBounds(1146, 350, 118, 131);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("");//ajouter
		ImageIcon addcandi =new ImageIcon(this.getClass().getResource("/ajoutc.png"));
		btnNewButton.setIcon(addcandi);
		btnNewButton.setBackground(new Color(173, 216, 230));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(false);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(false);
			}
		});
		btnNewButton.setBorder(null);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cinExiste())//vérifie l'existence du candidat
			     {
			    	 message("le condidat déja existe.",false);//afficher un message au utilisateur
			     }
				else {
			    	
				       if(verifier()==true)//vérifie la validation des champs obligatoires
				       { 
				         ajouter();//permet l'ajoute d'un nouveau candidat
				       }
				    }
			}
		});
		btnNewButton.setBounds(1145, 72, 119, 131);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(10, 501, 1334, 158);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recuperer_champs_du_tableau();
				}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"CIN", "Nom et pr\u00E9nom", "N\u00B0 t\u00E9l\u00E9phone", "Adresse", "Date d'inscription", "cat\u00E9gorie", "type d'inscription", "paiement", "prix code", "prix conduite", "seances code", "seances conduite", "examen code", "examen conduite", "forfait", "Frais total", "Somme pay\u00E9e", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMinWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setMinWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setMinWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setMinWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(85);
		table.getColumnModel().getColumn(7).setMinWidth(85);
		table.getColumnModel().getColumn(8).setPreferredWidth(70);
		table.getColumnModel().getColumn(8).setMinWidth(70);
		table.getColumnModel().getColumn(9).setPreferredWidth(72);
		table.getColumnModel().getColumn(9).setMinWidth(72);
		table.getColumnModel().getColumn(10).setMinWidth(75);
		table.getColumnModel().getColumn(11).setPreferredWidth(93);
		table.getColumnModel().getColumn(11).setMinWidth(93);
		table.getColumnModel().getColumn(12).setPreferredWidth(74);
		table.getColumnModel().getColumn(12).setMinWidth(74);
		table.getColumnModel().getColumn(13).setPreferredWidth(92);
		table.getColumnModel().getColumn(13).setMinWidth(90);
		table.getColumnModel().getColumn(14).setPreferredWidth(70);
		table.getColumnModel().getColumn(14).setMinWidth(70);
		table.getColumnModel().getColumn(15).setPreferredWidth(70);
		table.getColumnModel().getColumn(15).setMinWidth(70);
		table.getColumnModel().getColumn(16).setPreferredWidth(78);
		table.getColumnModel().getColumn(16).setMinWidth(17);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_3 = new JButton("Afficher les informations des candidats:");
		btnNewButton_3.setForeground(new Color(0, 0, 128));
		btnNewButton_3.setBackground(new Color(230, 230, 250));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
				actualiser();//mettre à jour la table des candidats.
			}
		});
		btnNewButton_3.setBounds(514, 452, 328, 23);
		panel.add(btnNewButton_3);
		
		JLabel lblNewLabel_9 = new JLabel("Frais total:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setBounds(845, 185, 86, 14);
		panel.add(lblNewLabel_9);
		
		fraisField = new JTextField();
		fraisField.setEditable(false);
		fraisField.setToolTipText("");
		fraisField.setText("0.0");
		fraisField.setBounds(961, 186, 79, 20);
		panel.add(fraisField);
		fraisField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Calculer");
		btnNewButton_4.setForeground(new Color(0, 0, 128));
		btnNewButton_4.setBackground(new Color(230, 230, 250));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculer();//permet d'avoir les informations sur le paiement, le reste toute au long le montant déjà payé et le frais total.
			}
		});
		btnNewButton_4.setBounds(960, 346, 80, 23);
		panel.add(btnNewButton_4);
	
		JLabel lblNewLabel_14 = new JLabel("Date d'inscription:");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_14.setBounds(10, 268, 103, 14);
		panel.add(lblNewLabel_14);

		
		JLabel lblNewLabel_18 = new JLabel("Somme pay\u00E9e:");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_18.setBounds(846, 226, 103, 14);
		panel.add(lblNewLabel_18);
		
		sommeField = new JTextField();
		sommeField.setEditable(false);
		sommeField.setText("0.0");
		sommeField.setBounds(961, 223, 79, 20);
		panel.add(sommeField);
		sommeField.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("Reste:");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_19.setBounds(845, 312, 46, 14);
		panel.add(lblNewLabel_19);
		
		resteField = new JTextField();
		resteField.setText("0.0");
		resteField.setEditable(false);
		resteField.setBounds(961, 309, 80, 20);
		panel.add(resteField);
		resteField.setColumns(10);
		
		JLabel lblNewLabel_20 = new JLabel("payer:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_20.setBounds(845, 268, 46, 14);
		panel.add(lblNewLabel_20);
		
		payField = new JTextField();
		payField.setText("0.0");
		payField.setBounds(961, 268, 79, 20);
		panel.add(payField);
		payField.setColumns(10);
		
		
		JLabel lblNewLabel_23 = new JLabel("Gestion des candidats");
		lblNewLabel_23.setForeground(new Color(51, 102, 204));
		lblNewLabel_23.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel_23.setBounds(517, 11, 346, 33);
		panel.add(lblNewLabel_23);
		
		JLabel lblNewLabel_15 = new JLabel("- Paiement:");
		lblNewLabel_15.setForeground(new Color(255, 255, 255));
		lblNewLabel_15.setBackground(new Color(255, 255, 255));
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_15.setBounds(845, 148, 86, 14);
		panel.add(lblNewLabel_15);
		
		JLabel lblNewLabel_24 = new JLabel("Date de naissance:");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_24.setForeground(Color.BLACK);
		lblNewLabel_24.setBounds(10, 148, 118, 14);
		panel.add(lblNewLabel_24);
		
		JLabel lblNewLabel_25 = new JLabel("");
		ImageIcon re =new ImageIcon(this.getClass().getResource("/retour.png"));
		lblNewLabel_25.setIcon(re);
		lblNewLabel_25.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Retourner au menu en gardant l'état sonore actuel
				Menu obj= new Menu(etatC);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				dispose();
			}
		});
		lblNewLabel_25.setBounds(10, 0, 61, 69);
		panel.add(lblNewLabel_25);
		
	    lblNewLabel_27 = new JLabel("on");
		ImageIcon on =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_27.setIcon(on);
		lblNewLabel_27.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_28.setVisible(true);//afficher l'icon du muet
				lblNewLabel_27.setVisible(false);//cacher l'icon du son
				etatC="off";
			}
		});
		lblNewLabel_27.setBounds(1184, 0, 61, 69);
		panel.add(lblNewLabel_27);
		
		lblNewLabel_28 = new JLabel("off");
		ImageIcon off =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_28.setIcon(off);
		lblNewLabel_28.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_28.setVisible(false);//cacher l'icon du muet
				lblNewLabel_27.setVisible(true);//afficher l'icon du son 
				etatC="on";
			}
		});
		lblNewLabel_28.setBounds(1184, 0, 61, 69);
		panel.add(lblNewLabel_28);
		
		JLabel lblNewLabel_26 = new JLabel("help");
		lblNewLabel_26.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//afficher la page aide.
				Help help=new Help("candidat");
				help.setVisible(true);
			}
		});
		ImageIcon help =new ImageIcon(this.getClass().getResource("/help.png"));
		lblNewLabel_26.setIcon(help);
		lblNewLabel_26.setBounds(1255, 2, 64, 64);
		panel.add(lblNewLabel_26);
		
		etat_son(etat);//gère l’état sonore de l’interface.
	
	}
 public void calculer() {//permet d'avoir les informations sur le paiement, le reste toute au long le montant déjà payé et le frais total.
		 type = typeBox.getSelectedItem().toString();
		 paiement = paiementBox.getSelectedItem().toString();
		 prixcode =Float.parseFloat(pcodeField.getText());
		 prixcond =Float.parseFloat(pcondField.getText());
		 nbcond =Integer.parseInt(nbcondField.getText().toString());
		 code=Float.parseFloat(codeField.getText());
		 cond=Float.parseFloat(conduiteField.getText());
		 montant=Float.parseFloat(forfaitField.getText());
		 somme=Float.parseFloat(sommeField.getText());
		 paye=Float.parseFloat(payField.getText());
		
		if(paiement=="sans forfait" && type=="Code et conduite")
		{
			float a=prixcond*nbcond+prixcode+cond;
			fraisField.setText(String.valueOf(a));
			float s=somme+paye;
			float r=a-somme-paye;
			sommeField.setText(String.valueOf(s));
			resteField.setText(String.valueOf(r));
			payField.setText("0");
		}
		else if(paiement=="sans forfait" && type=="Conduite")
		{
			float a=prixcond*nbcond+cond;
			fraisField.setText(String.valueOf(a));
			float s=somme+paye;
			float r=a-somme-paye;
			sommeField.setText(String.valueOf(s));
			resteField.setText(String.valueOf(r));
			payField.setText("0");
			
		}
		else if(paiement=="sans forfait" && type=="Conduite sans examen")
		{
			float a=prixcond*nbcond;
			fraisField.setText(String.valueOf(a));
			float s=somme+paye;
			float r=a-somme-paye;
			sommeField.setText(String.valueOf(s));
			resteField.setText(String.valueOf(r));
			payField.setText("0");
			
		}
		else if(paiement=="sans forfait" && type=="Code" )
		{
			fraisField.setText(String.valueOf(prixcode));
			float s=somme+paye;
		    float r=prixcode-somme-paye;
			sommeField.setText(String.valueOf(s));
			resteField.setText(String.valueOf(r));
			payField.setText("0");
		}
		else if((paiement=="forfait"))
		{
			fraisField.setText(String.valueOf(montant));
			float s=somme+paye;
			float r=montant-somme-paye;
			sommeField.setText(String.valueOf(s));
			resteField.setText(String.valueOf(r));
			payField.setText("0");
			
		}
	}
 public void reinitialiser(){//remettre les champs dans leur état initial.
		cinField.setText("");
		npField.setText("");
		telField.setText("");
		adressField.setText("");
		catBox.setSelectedItem("");
		typeBox.setSelectedItem("");
		paiementBox.setSelectedItem("");
		pcodeField.setText("0.0");
		pcondField.setText("0.0");
		nbcodeField.setText("0");
		nbcondField.setText("0");
		payField.setText("0.0");
		codeField.setText("0.0");
		conduiteField.setText("0.0");
		forfaitField.setText("0.0");
		fraisField.setText("0.0");
		sommeField.setText("0.0");
		resteField.setText("0.0");
		Date date = new Date();
		dateChooser.setDate(date);
		((JTextField)dateChooser_1.getDateEditor().getUiComponent()).setText("");
	}
 public void modifier() {//permet la modification des informations d'un candidat.
	    cin =Integer.parseInt(cinField.getText());
	    nom_prenom =npField.getText().toString();
		 tel =Integer.parseInt(telField.getText());
		 adresse =adressField.getText().toString();
		 datei =((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	     dateN =((JTextField) dateChooser_1.getDateEditor().getUiComponent()).getText();
		 categorie = catBox.getSelectedItem().toString();
		 type = typeBox.getSelectedItem().toString();
		 paiement = paiementBox.getSelectedItem().toString();
		
		String sql="update candidat set nom_prenom=?,date_naissance=?,telephone=?,adresse=?,date_inscription=?,type_inscription=?,choix_paiement=?,categorie_permis=?,prix_code=?,nb_code=?,examen_code=?,prix_conduite=?,nb_conduite=?,examen_conduite=?,forfait=?,frais_total=?,montant_paye=?,reste=? where cin=? ";
		if(cinExiste()==true)
		{
			message("Vous devez saisir le numero de cin du candidat ou séléctionner à partir du tableau un candidat.",false);
		
		}
	  else
	  {
	  if(verifier())
	   { 
		try {
		 if(nombre_Seance(cin,nom_prenom,type)==true)
		 {
			 if(!payField.getText().toString().equals("0"))
			  {
			    ajouter_paiment();
			  }
			 prepared=cnx.prepareStatement(sql);
			 prepared.setString(8, categorie);
			 prepared.setString(6, type);
			 prepared.setString(7, paiement);
			 prepared.setLong(19, cin);
			
				 prepared.setString(1, nom_prenom);
				 prepared.setString(2, dateN);
				 prepared.setLong(3, tel);
				 prepared.setString(4, adresse);
				 prepared.setString(5, datei);
				 if(type=="Code")//code
				 {
					prepared.setString(13, "0");//nbre seance conduit zero
					prepared.setString(12, "0");//prix conduit
				    prepared.setString(14, "0");//prix examen
				     prepared.setString(9, pcodeField.getText().toString());
				     prepared.setString(10, nbcodeField.getText().toString());
				     prepared.setString(11, codeField.getText().toString());
					 if(paiement=="forfait")
					 {
					  prepared.setString(15, forfaitField.getText().toString());
					  prepared.setString(16, fraisField.getText().toString());
					  prepared.setString(17,sommeField.getText().toString());
					  prepared.setString(18, resteField.getText().toString());
					   prepared.execute();
					   actualiser();
					   reinitialiser();
					   message("Candidat modifié avec succés.",true);
							
							
					}
					if(paiement=="sans forfait")
					{
							prepared.setString(15, forfaitField.getText().toString());
							prepared.setString(16, fraisField.getText().toString());
							prepared.setString(17, sommeField.getText().toString());
							prepared.setString(18, resteField.getText().toString());

							prepared.execute();
							actualiser();
							reinitialiser();
							message("Candidat modifié avec succés.",true);
					
					      }
                 } 
				 if(type=="Conduite")//conduite
					{   prepared.setString(9, "0");
			            prepared.setString(10, "0");
				        prepared.setString(11, "0");
					     prepared.setString(13, nbcondField.getText().toString());
					     prepared.setString(14, conduiteField.getText().toString());
					      if(paiement=="forfait")
							{
					    	  prepared.setString(12, "0");//prix du seance conduite n'est pas demmandé
							  prepared.setString(15, forfaitField.getText().toString());
							  prepared.setString(16, fraisField.getText().toString());
							  prepared.setString(17, sommeField.getText().toString());
			                  prepared.setString(18, resteField.getText().toString());      
							  prepared.execute();
							  actualiser();
							  reinitialiser();
							  message("Candidat modifié avec succés.",true);
							}
							if(paiement=="sans forfait")
							{
								   prepared.setString(12, pcondField.getText().toString());
									prepared.setString(15,forfaitField.getText().toString());
									prepared.setString(16, fraisField.getText().toString());
									prepared.setString(17, sommeField.getText().toString());
									prepared.setString(18, resteField.getText().toString());

									prepared.execute();
									actualiser();
									reinitialiser();
									message("Candidat modifié avec succés.",true);
								
								}
							 }
				 if((type=="Conduite sans examen"))
					{    
						prepared.setString(14, "0");//prixexamen
						prepared.setString(9, "0");
				        prepared.setString(10, "0");
					    prepared.setString(11, "0");
					    prepared.setString(13, nbcondField.getText().toString());
					    if(paiement=="forfait")
						{
					      prepared.setString(12, "0");
					     
						 prepared.setString(15,forfaitField.getText().toString());
						 prepared.setString(16, fraisField.getText().toString());
						 prepared.setString(17, sommeField.getText().toString());
			             prepared.setString(18, resteField.getText().toString());
					     prepared.execute();
					     actualiser();
						 reinitialiser();
						 message("Candidat modifié avec succés.",true);
						}
							if(paiement=="sans forfait")
							{
							    prepared.setString(12, pcondField.getText().toString());
							    prepared.setString(15, forfaitField.getText().toString());
							    prepared.setString(16, fraisField.getText().toString());
								prepared.setString(17, sommeField.getText().toString());
								prepared.setString(18, resteField.getText().toString());

								prepared.execute();
								actualiser();
								reinitialiser();
								message("Candidat modifié avec succés.",true);
								
											
					     }
					}
				 if(type=="Code et conduite")//conduite et code
					{ 
					  prepared.setString(13, nbcondField.getText().toString());
					  prepared.setString(14, conduiteField.getText().toString());
					  prepared.setString(9, pcodeField.getText().toString());
					  prepared.setString(10, nbcodeField.getText().toString());
					  prepared.setString(11, codeField.getText().toString());
					  if(paiement=="forfait")
					  {
						   prepared.setString(12, "0");    		
						   prepared.setString(15, forfaitField.getText().toString());
					       prepared.setString(16, fraisField.getText().toString());
					       prepared.setString(17, sommeField.getText().toString());
                           prepared.setString(18, resteField.getText().toString());

					       prepared.execute();
					       actualiser();
					       reinitialiser();
							message("Candidat modifié avec succés.",true);
				          }	
							if(paiement=="sans forfait")
							{
									prepared.setString(12, pcondField.getText().toString());
									prepared.setString(15, forfaitField.getText().toString());
									prepared.setString(16, fraisField.getText().toString());
									prepared.setString(17, sommeField.getText().toString());
									prepared.setString(18, resteField.getText().toString());

									prepared.execute();
									actualiser();
									reinitialiser();
									message("Candidat modifié avec succés.",true);
							}			
			         }
			    
			 } 	 
				 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  }
	  }
}
 public void ajouter() {//permet l'ajoute d'un nouveau candidat
		 cin =Integer.parseInt(cinField.getText().toString());
		 nom_prenom =npField.getText().toString();
		 tel =Integer.parseInt(telField.getText());
		 adresse =adressField.getText().toString();
		 datei =((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	     dateN =((JTextField) dateChooser_1.getDateEditor().getUiComponent()).getText();
		 categorie = catBox.getSelectedItem().toString();
		 type = typeBox.getSelectedItem().toString();
		 paiement = paiementBox.getSelectedItem().toString();
	  String sql="insert into candidat(cin,nom_prenom,date_naissance,telephone,adresse,date_inscription,type_inscription,choix_paiement,categorie_permis,prix_code,nb_code,examen_code,prix_conduite,nb_conduite,examen_conduite,forfait,frais_total,Montant_paye,reste) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     
	  try {	
		  
			 ajouter_paiment();
			 prepared=cnx.prepareStatement(sql);
			 prepared.setString(9, categorie);
			 prepared.setString(7, type);
			 prepared.setString(8, paiement);
			 
				 prepared.setLong(1, cin);
				 prepared.setString(2, nom_prenom);
				 prepared.setString(3, dateN);
				 prepared.setLong(4, tel);
				 prepared.setString(5, adresse);
				 prepared.setString(6, datei);
				 if(type=="Code")//code
				 {
					prepared.setString(14, "0");//nbre seance conduit zero
					prepared.setString(13, "0");//prix conduit
				    prepared.setString(15, "0");//prix examen
				     prepared.setString(10, pcodeField.getText().toString());
				     prepared.setString(11, nbcodeField.getText().toString());
				     prepared.setString(12,codeField.getText().toString());
					 if(paiement=="forfait")
					 {
					  prepared.setString(16,forfaitField.getText().toString());
					  prepared.setString(17, fraisField.getText().toString());
					  prepared.setString(18, sommeField.getText().toString());
					  prepared.setString(19, resteField.getText().toString());
					  
					  prepared.execute();
					  message("Candidat ajouté avec succés.",true);
					  Vector vec =new Vector();
						vec.addElement(cin);
						vec.addElement(nom_prenom);
						vec.addElement(nbcode);
						vec.addElement(nbcond);
						vec.addElement(type);
						vec.addElement(etatC);
					     Seance s = new Seance(vec);
				         s.setVisible(true);
				         actualiser();
					  reinitialiser();	
							
					}
					if(paiement=="sans forfait")
					{
							prepared.setString(16, forfaitField.getText().toString());
							prepared.setString(17, fraisField.getText().toString());
							prepared.setString(18, sommeField.getText().toString());
							prepared.setString(19, resteField.getText().toString());
				
							prepared.execute();
							message("Candidat ajouté avec succés.",true);
							Vector vec =new Vector();
							vec.addElement(cin);
							vec.addElement(nom_prenom);
							vec.addElement(nbcode);
							vec.addElement(nbcond);
							vec.addElement(type);
							vec.addElement(etatC);
						     Seance s = new Seance(vec);
					         s.setVisible(true);
					         actualiser();
							reinitialiser();
												
					      }
                    } 
				 if(type=="Conduite")//conduite
					{   prepared.setString(10, "0");
			            prepared.setString(11, "0");
				        prepared.setString(12, "0");
					     prepared.setString(13, pcondField.getText().toString());
					     prepared.setString(14, nbcondField.getText().toString());
					     prepared.setString(15, conduiteField.getText().toString());
					      if(paiement=="forfait")
							{
					    	  prepared.setString(13, "0");//prix du seance conduite n'est pas demmandé
							  prepared.setString(16, forfaitField.getText().toString());
							  prepared.setString(17, fraisField.getText().toString());
							  prepared.setString(18, sommeField.getText().toString());
			                  prepared.setString(19, resteField.getText().toString());                 
							  prepared.execute();
							  message("Candidat ajouté avec succés.",true);
							  Vector vec =new Vector();
								vec.addElement(cin);
								vec.addElement(nom_prenom);
								vec.addElement(nbcode);
								vec.addElement(nbcond);
								vec.addElement(type);
								vec.addElement(etatC);
							     Seance s = new Seance(vec);
						         s.setVisible(true);
						         actualiser();
									reinitialiser();
									 
							}
							if(paiement=="sans forfait")
							{
								prepared.setString(13, pcondField.getText().toString());
									prepared.setString(16, forfaitField.getText().toString());
									prepared.setString(17, fraisField.getText().toString());
									prepared.setString(18, sommeField.getText().toString());
									prepared.setString(19, resteField.getText().toString());
                                  
									prepared.execute();
									message("Candidat ajouté avec succés.",true);
									Vector vec =new Vector();
									vec.addElement(cin);
									vec.addElement(nom_prenom);
									vec.addElement(nbcode);
									vec.addElement(nbcond);
									vec.addElement(type);
									vec.addElement(etatC);
								     Seance s = new Seance(vec);
							         s.setVisible(true);
							         actualiser();
									reinitialiser();	
							 }
					}
				if((type=="Conduite sans examen"))
					{    
						prepared.setString(15, "0");//prixexamen
						prepared.setString(10, "0");
				        prepared.setString(11, "0");
					    prepared.setString(12, "0");
					    prepared.setString(14, nbcondField.getText().toString());
					    if(paiement=="forfait")
						{
					      prepared.setString(13, "0");
						  prepared.setString(16, forfaitField.getText().toString());
						  prepared.setString(17, fraisField.getText().toString());
						  prepared.setString(18, sommeField.getText().toString());
			              prepared.setString(19, resteField.getText().toString());
					      prepared.execute();
					      message("Candidat ajouté avec succés.",true);
					      Vector vec =new Vector();
							vec.addElement(cin);
							vec.addElement(nom_prenom);
							vec.addElement(nbcode);
							vec.addElement(nbcond);
							vec.addElement(type);
							vec.addElement(etatC);
						     Seance s = new Seance(vec);
					         s.setVisible(true);
					         actualiser();
						  reinitialiser();
						  
						 
						}
						if(paiement=="sans forfait")
						{
							 prepared.setString(13, pcondField.getText().toString());
							 prepared.setString(16, forfaitField.getText().toString());
							 prepared.setString(17, fraisField.getText().toString());
							prepared.setString(18, sommeField.getText().toString());
							prepared.setString(19, resteField.getText().toString());
							prepared.execute();
							message("Candidat ajouté avec succés.",true);
							actualiser();
								reinitialiser();
								 Vector vec =new Vector();
									vec.addElement(cin);
									vec.addElement(nom_prenom);
									vec.addElement(nbcode);
									vec.addElement(nbcond);
									vec.addElement(type);
									vec.addElement(etatC);
								     Seance s = new Seance(vec);
							         s.setVisible(true);
											
					     }
					}
				 if(type=="Code et conduite")//conduite et code
					{ 
					  prepared.setString(14, nbcondField.getText().toString());
					  prepared.setString(15, conduiteField.getText().toString());
					  prepared.setString(10,pcodeField.getText().toString());
					  prepared.setString(11, nbcodeField.getText().toString());
					  prepared.setString(12, codeField.getText().toString());
					  if(paiement=="forfait")
					  {
						   prepared.setString(13, "0");    		
						   prepared.setString(16, forfaitField.getText().toString());
					        prepared.setString(17, fraisField.getText().toString());
					        prepared.setString(18, sommeField.getText().toString());
                            prepared.setString(19, resteField.getText().toString());
					       prepared.execute();
					       message("Candidat ajouté avec succés.",true);
					       Vector vec =new Vector();
							vec.addElement(cin);
							vec.addElement(nom_prenom);
							vec.addElement(nbcode);
							vec.addElement(nbcond);
							vec.addElement(type);
							vec.addElement(etatC);
						     Seance s = new Seance(vec);
					         s.setVisible(true);
					         actualiser();
					       reinitialiser();
				          }	
							
							if(paiement=="sans forfait")
							{
							   prepared.setString(13, pcondField.getText().toString());
							   prepared.setString(16, forfaitField.getText().toString());
								prepared.setString(17, fraisField.getText().toString());
								prepared.setString(18, sommeField.getText().toString());
								prepared.setString(19, resteField.getText().toString());
								prepared.execute();
								message("Candidat ajouté avec succés.",true);
								Vector vec =new Vector();
								vec.addElement(cin);
								vec.addElement(nom_prenom);
								vec.addElement(nbcode);
								vec.addElement(nbcond);
								vec.addElement(type);
								vec.addElement(etatC);
							     Seance s = new Seance(vec);
						         s.setVisible(true);
						         actualiser();
								reinitialiser();
							}
							 
			         }
				   
				 if(type.equals("Code"))
			           message("Vous devez remplir '"+nbcode+"' séances du code.",true);
			         if((type.equals("Conduite"))||(type.equals("Conduite sans examen")))
				          message("Vous devez remplir '"+nbcond+"' séances de conduite.",true);
			         if(type.equals("Code et conduite"))
			        	 message("Vous devez remplir '"+nbcode+"' séances du code puis '"+nbcond+"' séances de conduite.",true);
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	  
     

	}
 public void ajouter_paiment() {//enregistre le montant payé par le candidat.
		 cin =Integer.parseInt(cinField.getText().toString());
		String sql="insert into frais_candidat(cin,date,montant_paye) values (?,DATE( NOW()),?)";
		try {
			System.out.print("p");
			prepared=cnx.prepareStatement(sql);
			  prepared.setLong(1, cin);
			  prepared.setString(2,payField.getText().toString());
			  prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 public boolean nombre_Seance(long cin, String nom_prenom,String type) {//vérifie s'il peut modifier le nombre des séances ou non selon le cas.
		  nbcode =Integer.parseInt(nbcodeField.getText());
		 nbcond =Integer.parseInt(nbcondField.getText());

		 int x=0 , y=0;
		boolean v1 =false;
		 String codeA;
		 String condA;
			String sql="select * from candidat where cin='"+cin+"'";
				try {
					prepared=cnx.prepareStatement(sql);
					resultat=prepared.executeQuery();
					if(resultat.next())
					{ 
					  String typeA=resultat.getString("type_inscription");
				      codeA =resultat.getString("nb_code");
					  condA=resultat.getString("nb_conduite");
					  int nbcA=Integer.parseInt(condA);
					  int nbcN=nbcond;
					  int nbA=Integer.parseInt(codeA);
				      int nbN=nbcode;
				   if(!typeA.equals(type))
				   {
					   if ((typeA.equals("Code"))&& (type.equals("Code et conduite")))
							   v1=true;
					   if ((typeA.equals("Code et conduite"))&& (type.equals("Code")))
					   {
						   String sq = " select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' ";
							prepared =cnx.prepareStatement(sq);
							resultat=prepared.executeQuery();
							if(resultat.next())
							{  
								message("Le candidat a déja assisté aux séances de conduite. Vous ne pouvez pas modifier.",false);
								return false;
							}
							else
								v1=true;
					   }
						   
					   if((typeA.equals("Conduite"))&& (type.equals("Code et conduite")))
							   v1=true;
					   if((typeA.equals("Code et conduite"))&& (type.equals("Conduite")))
					   {
						   String sq = " select * from seance_candidat where cin='"+cin+"' and type_seance='Code' ";
							prepared =cnx.prepareStatement(sq);
							resultat=prepared.executeQuery();
							if(resultat.next())
							{  
								message("Le candidat a déja assisté aux séances du code. Vous ne pouvez pas les modifier.",false);
								return false;
							}
							else
								v1=true;
					   }

					   if((typeA.equals("Conduite sans examen"))&& (type.equals("Code et conduite")))
						   v1=true;
					   if((typeA.equals("Code"))&& (type.equals("Conduite")))
					   {
						   String sq = " select * from seance_candidat where cin='"+cin+"' and type_inscription='Code' ";
							prepared =cnx.prepareStatement(sq);
							resultat=prepared.executeQuery();
							if(resultat.next())
							{  
								message("Le candidat a deja assisté aux séances du code. Vous ne pouvez pas les modifier.",false);
								return false;
							}
							else
								v1=true;
					   }
					   if((typeA.equals("Conduite"))&& (type.equals("Code")))
					   {
						   String sq = " select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' ";
							prepared =cnx.prepareStatement(sq);
							resultat=prepared.executeQuery();
							if(resultat.next())
							{  
								message("Le candidat a déja assisté aux séances de conduite. Vous ne pouvez pas les modifier.",false);
								return false;
							}
							else
								v1=true;
					   }
				   }
				   if(typeA.equals(type))
				   {
					   v1=true;
				   }
				   if(v1==true)
				   {
					 if(type.equals("Code"))
					 {
						if(nbA==nbN)
						{
							return true;
						}
						if (nbA < nbN )
						{   
							
							Vector vec =new Vector();
							vec.addElement(cin);
							vec.addElement(nom_prenom);
							vec.addElement(nbN-nbA);
							vec.addElement("0");
							vec.addElement(type);
							vec.addElement(etatC);
						    Seance s = new Seance(vec);
					        s.setVisible(true);
					        return true ;
						}
						if(nbA>nbN)
						{System.out.print("nbA>nbN");
							String sql2 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure < CURDATE() ";
							prepared =cnx.prepareStatement(sql2);
							resultat=prepared.executeQuery();
							 while(resultat.next())
							 {
								x++;
								
							   
							   System.out.print(x);
							 }
							
							 if(x>nbN)
							 { 
								 message("Le candidat a deja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
								 return false;
							 }
							 if(x<=nbN)
							 {//supprimer les seances non lu
								 String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
								 prepared =cnx.prepareStatement(sql4);
								 resultat=prepared.executeQuery();
							   while(resultat.next())
							   {
									 String moni=resultat.getString("moniteur");
									 int a =moni.indexOf("/");
									 //int b=moni.lastIndexOf("/");
									 String cinM=moni.substring(a+1,moni.length());
									 String date=resultat.getString("date_heure");
									 String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
									  prepared=cnx.prepareStatement(sql5);
									  resultat=prepared.executeQuery();
									  if(resultat.next())
									  {
										  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
										   if(nb>1)
										   {
											   nb--;
											   String nbeleve=String.valueOf(nb);
											   actualiserMoniteur(cinM,date,nbeleve);
											  
										   }
										   else
										   {
											   supprimerMoniteur(cinM,date);
											     
										   }											
									  }	
							 
							       }
							   //supprimer seance non lu du planning candidat
							     String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
								 prepared =cnx.prepareStatement(sql3);
								 prepared.execute();
							      if(x<nbN)//ajouter la nouvelle seance
							      {
							    	  System.out.print("delete");
									   Vector vec =new Vector();
									   vec.addElement(cin);
									   vec.addElement(nom_prenom);
									   vec.addElement(nbN-x);
									   vec.addElement("0");
									   vec.addElement(type);
									   vec.addElement(etatC);
								       Seance s = new Seance(vec);
							           s.setVisible(true);
								     return true;
							      }
							      if(x==nbN)
									 {
									  message("Le candidat a assisté à ce nombre de séances.",false);
									  return true;
									 }
							 }
						}
					 }//code
					 if((type.equals("Conduite"))||(type.equals("Conduite sans examen")))
					 {
						    if(nbcA==nbcN)
								return true;
							if (nbcA < nbcN)
							{   
								
								Vector vec =new Vector();
								vec.addElement(cin);
								vec.addElement(nom_prenom);
								vec.addElement("0");
								vec.addElement(nbcN-nbcA);
								vec.addElement(type);
								vec.addElement(etatC);
							    Seance s = new Seance(vec);
						        s.setVisible(true);
						        return true ;
							}
							if(nbcA>nbcN)
							{
								String req= "select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure < CURDATE() ";
								prepared =cnx.prepareStatement(req);
								resultat=prepared.executeQuery();
								 while(resultat.next())
								 {
								   x++;
								   System.out.print(x);
								 }
								 if(x>nbcN)
								 { 
									 message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
									 return false;
								 }
								 if(x<=nbcN)
								 {//supprimer les seances non lu
									 String req1 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure > CURDATE() ";
									 prepared =cnx.prepareStatement(req1);
									 resultat=prepared.executeQuery();
									 while(resultat.next())
									   {
											 String moni=resultat.getString("moniteur");
											 int a =moni.indexOf("/");
											 String cinM=moni.substring(a+1,moni.length());
											 String date=resultat.getString("date_heure");
							                  supprimerMoniteur(cinM,date);
												
									    }
									 String req2 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure >CURDATE() ";
									 prepared =cnx.prepareStatement(req2);
									 prepared.execute();
									   
									   if(x<nbN)//ajouter la nouvelle seance
									      {
										    Vector vec =new Vector();
											vec.addElement(cin);
											vec.addElement(nom_prenom);
											vec.addElement("0");
											vec.addElement(nbcN-x);
											vec.addElement(type);
											vec.addElement(etatC);
										    Seance s = new Seance(vec);
									        s.setVisible(true);
										    return true;
									      }
									     if(x==nbN)
										 {
											  message("Le candidat a assisté à ce nombre de séances.",false);
											  return true;
										 }
								    }
							  }
							}//cond
					        if(type.equals("Code et conduite"))
					        {
					        	
								String sq = " select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' ";
								prepared =cnx.prepareStatement(sq);
								resultat=prepared.executeQuery();
								if(resultat.next())
								{   
								  if(nbcA==nbcN)
								  {
										if(nbA==nbN)
										{
								
											return true;
										}
										if (nbA < nbN )
										{   
											
											Vector vec =new Vector();
											vec.addElement(cin);
											vec.addElement(nom_prenom);
											vec.addElement(nbN-nbA);
											vec.addElement(nbcond);
											vec.addElement("Code");
											vec.addElement(etatC);
										    Seance s = new Seance(vec);
									        s.setVisible(true);
									        return true ;
										}
										if(nbA>nbN)
										{
											String sql2 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure < CURDATE() ";
											prepared =cnx.prepareStatement(sql2);
											resultat=prepared.executeQuery();
											 while(resultat.next())
											 {
												x++;
												
											 }
											
											 if(x>nbN)
											 { 
												 message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre de séances.",false);
												 return false;
											 }
											 if(x<=nbN)
											 {//supprimer les seances non lu
												 String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
												 prepared =cnx.prepareStatement(sql4);
												 resultat=prepared.executeQuery();
											   while(resultat.next())
											   {
													 String moni=resultat.getString("moniteur");
													 int a =moni.indexOf("/");
													 String cinM=moni.substring(a+1,moni.length());
													 String date=resultat.getString("date_heure");
													 String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
													  prepared=cnx.prepareStatement(sql5);
													  resultat=prepared.executeQuery();
													  if(resultat.next())
													  {
														  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
														   if(nb>1)
														   {
															   nb--;
															   String nbeleve=String.valueOf(nb);
															   actualiserMoniteur(cinM,date,nbeleve);
															  
														   }
														   else
														   {
															   supprimerMoniteur(cinM,date);
															     
														   }											
													  }	
											 
											       }
											   //supprimer seance non lu du planning candidat
											     String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
												 prepared =cnx.prepareStatement(sql3);
												 prepared.execute();
											      if(x<nbN)//ajouter la nouvelle seance
											      {
													   Vector vec =new Vector();
													   vec.addElement(cin);
													   vec.addElement(nom_prenom);
													   vec.addElement(nbN-x);
													   vec.addElement(nbcond);
													   vec.addElement("Code");
													   vec.addElement(etatC);
												       Seance s = new Seance(vec);
											           s.setVisible(true);
												     return true;
											      }
											      if(x==nbN)
													 {
													  message("Le candidat a assisté à ce nombre de séances.",false);
													  return true;
													 }
											   }
											 }
										}
										if (nbcA < nbcN)
										{   
											if(nbA==nbN)
											{
												Vector vec =new Vector();
												vec.addElement(cin);
												vec.addElement(nom_prenom);
												vec.addElement(nbcode);
												vec.addElement(nbcN-nbcA);
												vec.addElement("Conduite");
												vec.addElement(etatC);
											    Seance s = new Seance(vec);
										        s.setVisible(true);
										        return true ;
											}
											if (nbA < nbN )
											{   
												
												Vector vec =new Vector();
												vec.addElement(cin);
												vec.addElement(nom_prenom);
												vec.addElement(nbN-nbA);
												vec.addElement(nbcN-nbcA);
												vec.addElement(type);
												vec.addElement(etatC);
											    Seance s = new Seance(vec);
										        s.setVisible(true);
										        return true ;
											}
											if (nbA > nbN )
											{  
												String sql2 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure < CURDATE() ";
												prepared =cnx.prepareStatement(sql2);
												resultat=prepared.executeQuery();
												 while(resultat.next())
												 {
													x++;
										
												   System.out.print(x);
												 }
												 if(x>nbN)
												 { 
													 message("Le candidat a deja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre de séances.",false);
													 return false;
												 }
												 if(x<=nbN)
												 {//supprimer les seances non lu
													 String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
													 prepared =cnx.prepareStatement(sql4);
													 resultat=prepared.executeQuery();
													 while(resultat.next())
													   {
															 String moni=resultat.getString("moniteur");
															 int a =moni.indexOf("/");
															 //int b=moni.lastIndexOf("/");
															 String cinM=moni.substring(a+1,moni.length());
															 String date=resultat.getString("date_heure");
															 String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
															  prepared=cnx.prepareStatement(sql5);
															  resultat=prepared.executeQuery();
															  if(resultat.next())
															  {
																  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
																   if(nb>1)
																   {
																	   nb--;
																	   String nbeleve=String.valueOf(nb);
																	   actualiserMoniteur(cinM,date,nbeleve);
																	  
																   }
																   else
																   {
																	   supprimerMoniteur(cinM,date);
																	     
																   }											
															  }	
													 
													       }
													   //supprimer seance non lu du planning candidat
												         String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
													     prepared =cnx.prepareStatement(sql3);
													     prepared.execute();
													     if(x<nbN)//ajouter la nouvelle seance
													      {
													    	  System.out.print("delete");
															   Vector vec =new Vector();
															   vec.addElement(cin);
															   vec.addElement(nom_prenom);
															   vec.addElement(nbN-x);
															   vec.addElement(nbcN-nbcA);
															   vec.addElement(type);
															   vec.addElement(etatC);
														       Seance s = new Seance(vec);
													           s.setVisible(true);
														     return true;
													      }
													     if(x==nbN)
														 {
														  message("Le candidat a assisté à ce nombre de séances.",false);
														  return true;
														 }


												 }

											}
										}
										if(nbcA>nbcN)
										{
											String req= "select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure < CURDATE() ";
											prepared =cnx.prepareStatement(req);
											resultat=prepared.executeQuery();
											 while(resultat.next())
											 {
											   y++;
											 
											 }
											 if(y>nbcN)
											 { 
												    message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
													return false ;
											      
											 }
											 if(y<=nbcN)
											 {//supprimer les seances non lu
												 String req1 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure > CURDATE() ";
												 prepared =cnx.prepareStatement(req1);
												 resultat=prepared.executeQuery();
												 while(resultat.next())
												   {
														 String moni=resultat.getString("moniteur");
														 int a =moni.indexOf("/");
														 String cinM=moni.substring(a+1,moni.length());
														 String date=resultat.getString("date_heure");
										                  supprimerMoniteur(cinM,date);
															
												    }
												 String req2 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Conduite' and date_heure>CURDATE() ";
												 prepared =cnx.prepareStatement(req2);
												 prepared.execute();
												   if(y<nbcN)//ajouter la nouvelle seance
												     {
													   if(nbA==nbN)
														{
															Vector vec =new Vector();
															vec.addElement(cin);
															vec.addElement(nom_prenom);
															vec.addElement("0");
															vec.addElement(nbcN-y);
															vec.addElement("Conduite");//car on va ajouter seulement des seances conduite
															vec.addElement(etatC);
															Seance s = new Seance(vec);
													        s.setVisible(true);
													        return true ;
														}
													   if (nbA < nbN )
														{   
															
															Vector vec =new Vector();
															vec.addElement(cin);
															vec.addElement(nom_prenom);
															vec.addElement(nbN-nbA);
															vec.addElement(nbcN-y);
															vec.addElement(type);
															vec.addElement(etatC);
															Seance s = new Seance(vec);
													        s.setVisible(true);
													        return true ;
														}
													   if(nbA>nbN)
														{
															String sql2 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure < CURDATE() ";
															prepared =cnx.prepareStatement(sql2);
															resultat=prepared.executeQuery();
															while(resultat.next())
															 {
																x++;
															 }
															
															 if(x>nbN)
															 { 
																   message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
																   Vector vec =new Vector();
																	vec.addElement(cin);
																	vec.addElement(nom_prenom);
																	vec.addElement("0");
																	vec.addElement(nbcN-y);
																	vec.addElement("Conduite");//car on va ajouter selement desseance conduite
																	vec.addElement(etatC);
																	Seance s = new Seance(vec);
															        s.setVisible(true);
															        return true ;
															
															 }
															 if(x<=nbN)
															 {//supprimer les seances non lu
																 String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
																 prepared =cnx.prepareStatement(sql4);
																 resultat=prepared.executeQuery();
																  while(resultat.next())
																   {
																		 String moni=resultat.getString("moniteur");
																		 int a =moni.indexOf("/");
																		 //int b=moni.lastIndexOf("/");
																		 String cinM=moni.substring(a+1,moni.length());
																		 String date=resultat.getString("date_heure");
																		 String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
																		  prepared=cnx.prepareStatement(sql5);
																		  resultat=prepared.executeQuery();
																		  if(resultat.next())
																		  {
																			  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
																			   if(nb>1)
																			   {
																				   nb--;
																				   String nbeleve=String.valueOf(nb);
																				   actualiserMoniteur(cinM,date,nbeleve);
																				  
																			   }
																			   else
																			   {
																				   supprimerMoniteur(cinM,date);
																				     
																			   }											
																		  }	
	                                                                    
																   }
																   //supprimer seance non lu du planning candidat
																     String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
																	 prepared =cnx.prepareStatement(sql3);
																	 prepared.execute();
																	 if(x<nbN)//ajouter la nouvelle seance
																      {
																		   Vector vec =new Vector();
																		   vec.addElement(cin);
																		   vec.addElement(nom_prenom);
																		   vec.addElement(nbN-x);
																		   vec.addElement(nbcN-y);
																		   vec.addElement(type);
																		   vec.addElement(etatC);
																		   Seance s = new Seance(vec);
																           s.setVisible(true);
																	     return true;
																      }
																      if(x==nbN)
																		 {
																		  message("Le candidat a assisté à ce nombre de séances.",false);
																		  Vector vec =new Vector();
																			vec.addElement(cin);
																			vec.addElement(nom_prenom);
																			vec.addElement("0");
																			vec.addElement(nbcN-y);
																			vec.addElement("Conduite");//car on va ajouter selement desseance conduite
																		    Seance s = new Seance(vec);
																	        s.setVisible(true);
																	        return true ;
																	
																		 }
															 }
														}
																 
												     }
	  
											 if(y==nbcN)
											 {
												  message("Le candidat a assisté à ce nombre de séances de conduite.",false);
												  if(nbA==nbN)
													{
											
														return true;
													}
												  if (nbA < nbN )
													{   
														
														Vector vec =new Vector();
														vec.addElement(cin);
														vec.addElement(nom_prenom);
														vec.addElement(nbN-nbA);
														vec.addElement("0");
														vec.addElement("Code");
														vec.addElement(etatC);
													    Seance s = new Seance(vec);
												        s.setVisible(true);
												        return true ;
													}
												  if(nbA>nbN)
													{
														String sql2 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure < CURDATE() ";
														prepared =cnx.prepareStatement(sql2);
														resultat=prepared.executeQuery();
														 while(resultat.next())
														 {
															x++;
														 }
														 if(x>nbN)
														 { 
															 message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
															 return false;
														 }
														 if(x<=nbN)
														 {//supprimer les seances non lu
															 String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
															 prepared =cnx.prepareStatement(sql4);
															 resultat=prepared.executeQuery();
															 while(resultat.next())
															   {
																 String moni=resultat.getString("moniteur");
																 int a =moni.indexOf("/");
											
																 String cinM=moni.substring(a+1,moni.length());
																 String date=resultat.getString("date_heure");
																 String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
																 prepared=cnx.prepareStatement(sql5);
																 resultat=prepared.executeQuery();
																 if(resultat.next())
																  {
																	  int nb =Integer.parseInt(resultat.getString("Nb_eleves"));
																	   if(nb>1)
																	   {
																		   nb--;
																		   String nbeleve=String.valueOf(nb);
																		   actualiserMoniteur(cinM,date,nbeleve);
																		  
																	   }
																	   else
																	   {
																		   supprimerMoniteur(cinM,date);
																		     
																	   }											
																  }	
		  
																	  
															   }
															   //supprimer seance non lu du planning candidat
														     String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
															 prepared =cnx.prepareStatement(sql3);
															 prepared.execute();
															  if(x<nbN)//ajouter la nouvelle seance
														      {
														    	  System.out.print("delete");
																   Vector vec =new Vector();
																   vec.addElement(cin);
																   vec.addElement(nom_prenom);
																   vec.addElement(nbN-x);
																   vec.addElement("0");
																   vec.addElement("Code");
																   vec.addElement(etatC);
															       Seance s = new Seance(vec);
														           s.setVisible(true);
															     return true;
														      }
														      if(x==nbN)
																 {
																  message("Le candidat a assisté à toutes les séances du code.",false);
																  return true;
																 }


														 }
													}
											 }
										}

									}

								}
								else//il n'a pas enregistrer des seances conduite
						    	{
									if(nbA==nbN)
										return true;
									if (nbA < nbN)
									{   
										
										Vector vec =new Vector();
										vec.addElement(cin);
										vec.addElement(nom_prenom);
										vec.addElement(nbN-nbA);
										vec.addElement(nbcond);
										vec.addElement(type);
										vec.addElement(etatC);
									    Seance s = new Seance(vec);
								        s.setVisible(true);
								       return true ;
									}
									if(nbA>nbN)
									{
										String sql2 =" select from seance_candidat where cin='"+cin+"' type_seance='Code' and date_heure<CURDATE() ";
										prepared =cnx.prepareStatement(sql2);
										resultat=prepared.executeQuery();
										 while(resultat.next())
										 {
										   x++;
										 }
										 if(x>=nbN)
										 { 
											 message("Le candidat a déja assisté aux plusieurs séances. Vous ne pouvez pas réduire le nombre des séances.",false);
											 return false;
										 }
										 if(x<nbN)
										 {//supprimer les seances non lu
									              String sql4 = "select * from seance_candidat where cin='"+cin+"' and type_seance='Code' and date_heure > CURDATE() ";
									              prepared =cnx.prepareStatement(sql4);
									              resultat=prepared.executeQuery();
								                  while(resultat.next())
								                  {
									              	 String moni=resultat.getString("moniteur");
										             int a =moni.indexOf("/");
										  
										             String cinM=moni.substring(a+1,moni.length());
										             String date=resultat.getString("date_heure");
										            String sql5="select nb_eleves from seance_moniteur where cin='"+cinM+"'and date_heure='"+date+"'";
										            prepared=cnx.prepareStatement(sql5);
										            resultat=prepared.executeQuery();
										           if(resultat.next())
										          {
											       int nb =Integer.parseInt(resultat.getString("nb_eleves"));
											       if(nb>1)
											        {
												      nb--;
												      String nbeleve=String.valueOf(nb);
												      actualiserMoniteur(cinM,date,nbeleve);
												  
											        }
											        else
											       {
												   supprimerMoniteur(cinM,date);
												     
											       }											
										         }	
								 
								              }
								            //supprimer seance non lu du planning candidat
								            String sql3 = "delete from seance_candidat where cin='"+cin+"' and type_seance='Code'and date_heure > CURDATE() ";
									        prepared =cnx.prepareStatement(sql3);
									        prepared.execute();
								            if(x<nbN)//ajouter la nouvelle seance
								            {
								    	       Vector vec =new Vector();
										       vec.addElement(cin);
										       vec.addElement(nom_prenom);
										       vec.addElement(nbN-x);
										       vec.addElement(nbcond);
										       vec.addElement(type);
										       vec.addElement(etatC);
										       Seance s = new Seance(vec);
										       s.setVisible(true);
								           }
								           if(x==nbN)
										   {
										     message("Le candidat a assisté à ses séances.",false);
										      return true;
										   }
										 }
							          } 
					        	  }
					        }
									
					 }
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

		  return false;
	}

public void actualiser()//mettre à jour la table des candidats.
	{
		String sql = " select *from candidat ";
		try {
			prepared =cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 public boolean cinExiste()//vérifie l'existence du candidat
	{ 
		
		try {
			String sql1="select * from candidat where cin='"+cinField.getText()+"'";
			prepared =cnx.prepareStatement(sql1);
			//prepared.setInt(1,Integer.parseInt(cinField.getText()));
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 return true;
	 
	}
 public void supprimer() {//permet la suppression d'un candidat à travers son numéro de CIN et ses séances 
	  cin =Integer.parseInt(cinField.getText().toString());
	 String sql="delete from candidat where cin='"+cin+"'";
		try {
			prepared=cnx.prepareStatement(sql);
		    prepared.execute();
		    supprimer_seance(cin);
		    message("Candidat supprimé avec succés.",true);
		    actualiser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
 	       
}

 public void supprimerMoniteur(String cin,String date) {//permet la suppression d’une séance du planning  
		
		String sql="delete from seance_moniteur where cin='"+cin+"' and date_heure='"+date+"'";
		try {
			prepared=cnx.prepareStatement(sql);
			
		    prepared.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

 public void actualiserMoniteur(String cinM,String date,String nb)//mettre à jour le nombre des élèves dans une séance de code d'un moniteur,utilisé dans la méthode nombre_Seance(String cin,String type).

		{
			String sql="update seance_moniteur set nb_eleves=? where cin='"+cinM+"' and date_heure='"+date+"'";
		
			try {
				prepared=cnx.prepareStatement(sql);
				prepared.setString(1, nb);
			    prepared.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

 public void suprimerCandidat(long cin)//supprimer 
	{
		
		String sql="delete from seance_candidat where cin='"+cin+"'";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.execute();
			System.out.println(cin);
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
 public boolean verifier() {//vérifie la validation des champs obligatoires.
	    cin =Integer.parseInt(cinField.getText().toString());
	    nom_prenom =npField.getText().toString();
		tel =Integer.parseInt(telField.getText());
		 adresse =adressField.getText().toString();
		 datei =((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
		 dateN =((JTextField) dateChooser_1.getDateEditor().getUiComponent()).getText();
		 type = typeBox.getSelectedItem().toString();
		 paiement = paiementBox.getSelectedItem().toString();
		 prixcode =Float.parseFloat(pcodeField.getText());
		 prixcond =Float.parseFloat(pcondField.getText());
		 nbcode =Integer.parseInt(nbcodeField.getText().toString());
		 nbcond =Integer.parseInt(nbcondField.getText().toString());
		 code=Float.parseFloat(codeField.getText());
		 cond=Float.parseFloat(conduiteField.getText());
		 montant =Float.parseFloat(forfaitField.getText().toString());
		 frais=Float.parseFloat(fraisField.getText().toString());
		 somme=Float.parseFloat(sommeField.getText().toString());
		 reste=Float.parseFloat(resteField.getText().toString());
		 paye=Float.parseFloat(payField.getText().toString());
		
	 if((cinField.getText().toString().matches("[0-9]+")) && (!cinField.getText().toString().equals("")) && (cinField.getText().toString().length()==8))
		{
		 if(nom_prenom.matches("[a-zA-Z][a-z A-Z]*"))
			{
			 
			 if(dateN.length()!=0)
				{
				 
				 if(telField.getText().toString().matches("[0-9]+")&& (telField.getText().toString().length()==8))
					{
					
					 if(adresse.matches("^[a-zA-Z_0-9][a-z A-Z_0-9]*"))
						{
						 if(datei.length()!=0)
							{
						 if(!type.equals("--"))
							{
							 if(type=="Code")//code
							 {
								 if((String.valueOf(prixcode).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(prixcode).matches("[0]+|[0]+.[0]+")))
								 {
									 if((String.valueOf(nbcode).matches("[0-9]+")) && (!String.valueOf(nbcode).matches("[0]+")))
									 {
										 if((String.valueOf(code).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(code).matches("[0]+|[0]+.[0]+")))
										 {
											 if(paiement=="forfait")
											 {
												 if((String.valueOf(montant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(montant).matches("[0]+|[0]+.[0]+")))
													{
													  if((String.valueOf(reste).length()!=0)&&(montant==frais))
															return true ;
													 else
														message("Cliquer sur le bouton calculer.",false);
													
												 }
												 else
													 message("Vous devez remplir le montant du forfait.",false);
														
											 }
											 if(paiement=="sans forfait")
												{
												 if(((String.valueOf(frais).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(frais).matches("[0]+|[0]+.[0]+"))) && (prixcode==frais))
														return true ;
												   else
													   message("Cliquer sur le bouton calculer.",false);
												 
												}
										 }else
											 message("Vérifiez le prix d'examen du code.",false);
										}
									     
										else
									        message("Vérifiez le nombre des séances du code.",false);
										}
										else
											message("Vérifiez le prix du code total.",false);
							 }
							 
							 if(type=="Conduite")
							 {
							 if((String.valueOf(nbcond).matches("[0-9]+")) && (!String.valueOf(nbcond).matches("[0]+")))
							 {
							  if((String.valueOf(cond).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(cond).matches("[0]+|[0]+.[0]+")))
							  {
								 if(paiement=="forfait")
								 {
								 if((String.valueOf(montant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(montant).matches("[0]+|[0]+.[0]+")))
								{
									if((String.valueOf(reste).length()!=0)&&(montant==frais))
											return true ;
									else
										
									    message("Cliquer sur le bouton calculer.",false);		
									}
									else
										message("Vous devez remplir le montant du forfait.",false);
								
								}
								if(paiement=="sans forfait")
								{//
								  if((String.valueOf(prixcond).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(prixcond).matches("[0]+|[0]+.[0]+")))
								   {
									  if(((String.valueOf(frais).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(frais).matches("[0]+|[0]+.[0]+"))) && (prixcond*nbcond+cond==frais))
											return true ;
									  else
										message("Cliquer sur le bouton calculer.",false);
									}
									else
										message("Vérifiez le prix de séance de conduite.",false);		
								}
							}
							 else
								 message("Vérifiez le prix d'examen de conduite.",false);
						}
						 else 
							 message("Vérifiez le nombre des seances de conduite.",false);
					     
					 }
					
					 if((type=="Conduite sans examen"))
						{ 
						 if((String.valueOf(nbcond).matches("[0-9]+")) && (!String.valueOf(nbcond).matches("[0]+|[0]+.[0]+")))
						 {
							if(paiement=="forfait")
							{
							if((String.valueOf(montant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(montant).matches("[0]+|[0]+.[0]+")))
							{
							 if((String.valueOf(reste).length()!=0)&&(montant==frais))
									return true ;
							 else
								 message("Cliquer sur le bouton calculer.",false);				
							}
							else
								message("Vérifiez le montant du forfait.",false);
							}
							if(paiement=="sans forfait")
							{
							 if((String.valueOf(prixcond).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(prixcond).matches("[0]+|[0]+.[0]+")))
								 {
								 if(((String.valueOf(frais).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(frais).matches("[0]+|[0]+.[0]+"))) && (prixcond*nbcond==frais))
										return true ;
								  else
									  message("Cliquer sur le bouton calculer.",false);
								 }
							 else
								 message("Vérifiez le prix de séance de conduite.",false);
							}
						  }
						  else
							  message("Vérifiez le nombre des séances de conduite.",false);
					     }
					 if(type=="Code et conduite")
						{
							if((String.valueOf(nbcond).matches("[0-9]+")) && (!String.valueOf(nbcond).matches("[0]+")))
							   { 
							  
							    if((String.valueOf(cond).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(cond).matches("[0]+|[0]+.[0]+")))
							    {
							    	
							    	if((String.valueOf(prixcode).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(prixcode).matches("[0]+|[0]+.[0]+")))
							    	{	
								     
								     if((String.valueOf(nbcode).matches("[0-9]+")) && (!String.valueOf(nbcode).matches("[0]+")))
								     {
								   
								    	 if((String.valueOf(code).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(code).matches("[0]+|[0]+.[0]+")))
										 {
								     
								     if(paiement=="forfait")
										{
								        if((String.valueOf(montant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(montant).matches("[0]+|[0]+.[0]+")))
										{
								        	if((String.valueOf(reste).length()!=0)&&(montant==frais))
												return true;
										    else
										    	message("Cliquer sur le bouton calculer ",false);
									   }
									else
									{
										message("Vous devez remplir le montant du forfait.",false);
									}
								}
								if(paiement=="sans forfait")
								{
								  if((String.valueOf(prixcond).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(prixcond).matches("[0]+|[0]+.[0]+")))
								 {
									if(((String.valueOf(frais).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!String.valueOf(frais).matches("[0]+|[0]+.[0]+"))) && (prixcond*nbcond+cond+prixcode==frais))
											return true ;
									else
										message("Cliquer sur le bouton calculer.",false);
								}
								 else
									 message("Vérifiez le prix de séance de conduite.",false);
				 
								   }
							     
								     } 
								     else
								    	 message("Verifier le prix d'examen du code.",false);
									}
								     
									else
										message("Vérifiez le nombre des séances de code.",false);
									
									}
									else
										message("Verifier le prix du code total.",false);
								    	
							   }
							   else
								   message("Vérifiez le prix d'examen de conduite.",false);
							    	 
						     }
							  else
								  message("Vérifiez le nombre des séances de conduite.",false);
				           }
							}
						 else
							 message("Choisissez le type d'inscription.",false);
							}
						 else 
							 message("Vérifiez la date d'inscription.",false);
						
						}
					 else
						 message("Vérifiez l'adresse.",false);
						 
						
					}
				 else
					 message("Vérifiez le N° téléphone.",false);
					 
					
				}
			 else
				 message("Vérifiez la date de naissance.",false);
				
			}
		  else 
		      message("Vérifiez le nom et le prénom du candidat.",false);
		 
		}
		else 
			message("Vérifiez le numero de cin.",false);
			
	return false;
	}
	
 public void message(String ch,boolean etat) //affiche un message sonore .
{
	  	
	if(lblNewLabel_27.isShowing())
	{
		Audio t = new Audio("audio",etat);
		t.set_sound("on",etat);
	    t.start();
	    Audio t1 = new Audio("message",ch,etat);
	    t1.start();
	}
	else if(lblNewLabel_28.isShowing())
	{
		Audio t = new Audio("audio",etat);
		t.set_sound("off",etat);
		t.start();
	    Audio t2 = new Audio("message",ch,etat);
	    t2.start();
	}	
		
}
 public void etat_son(String etat)//gère l’état sonore de l’interface.
	{
		if(etat=="on")
		{
			lblNewLabel_27.setVisible(true);
			lblNewLabel_28.setVisible(false);
		}
		else if(etat=="off")
		{
			lblNewLabel_28.setVisible(true);
			lblNewLabel_27.setVisible(false);
		}
	}
 public void supprimer_seance(long cin)
 {
 	String sql1="select * from seance_candidat where cin=?";
 	try {
 		prepared=cnx.prepareStatement(sql1);
 		prepared.setLong(1, cin);
 			resultat=prepared.executeQuery();
 		while(resultat.next())
 		{
 			String date=resultat.getString("date_heure");
 			String type=resultat.getString("type_seance");
 			String moni=resultat.getString("moniteur");
			int b=moni.lastIndexOf("/");
		    String cinM=moni.substring(b+1,moni.length());
 	 		String npM =moni.substring(0,b);

 			if(type.equals("Code"))
 			{
 				String sql3="select nb_eleves from seance_moniteur where cin=? and date_heure > CURDATE() ORDER BY nb_eleves";
 		    	
 			    prepared=cnx.prepareStatement(sql3);
 			    prepared.setString(1, cinM);
 			    resultat=prepared.executeQuery();
 			    while(resultat.next())
 		        {
 		        	String nb=resultat.getString("nb_eleves");
 		        	int x=Integer.parseInt(nb);
 		        	//x--;
 		        	if(x==1) {
 		        		String sql4="delete from seance_moniteur where cin=? and date_heure>CURDATE() and nb_eleves=?";
 		        		prepared=cnx.prepareStatement(sql4);
 			        	prepared.setString(1,cinM);
 				    	prepared.setString(2,"1");
 			        	prepared.execute();
 			        	System.out.println("delete");
 		        		
 		        	}
 		        	else {
 		        		x--;
 		        		String n=Integer.toString(x);
 			        	String sql2="update seance_moniteur set nb_eleves=? where cin=? and date_heure>CURDATE()";
 						prepared=cnx.prepareStatement(sql2);
 			        	prepared.setString(1,n);
 			        	prepared.setString(2,cinM);
 			        	prepared.execute();
 		        	}
 		        	
 		        }
 			}
 			else
			{
				String sql6="delete from seance_moniteur where cin=? and date_heure>CURDATE()";
	    		prepared=cnx.prepareStatement(sql6);
	        	prepared.setString(1,cinM);
	        	prepared.execute();
			}
			suprimerCandidat(cin);
		}
		
	}
	catch(SQLException p)
 		{
 			p.printStackTrace();
 		}
}
 public void recuperer_champs() {//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ
	  cin=Integer.parseInt(cinField.getText());
		String sql="select nom_prenom,date_naissance,telephone,adresse,date_inscription,type_inscription,choix_paiement,categorie_permis,prix_code,nb_code,examen_code,prix_conduite,nb_conduite,examen_conduite,forfait,frais_total,Montant_paye,reste from candidat where cin=? ";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setLong(1, cin);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				nom_prenom=resultat.getString("nom_prenom");
				npField.setText(nom_prenom);
				 tel=Integer.parseInt(resultat.getString("telephone"));
				telField.setText(String.valueOf(tel));
				 adresse=resultat.getString("adresse");
				adressField.setText(adresse);
				 categorie=resultat.getString("categorie_permis");
				catBox.setSelectedItem(categorie);
				type=resultat.getString("type_inscription");
				typeBox.setSelectedItem(type);
				paiement=resultat.getString("choix_paiement");
				paiementBox.setSelectedItem(paiement);
				 prixcode=Float.parseFloat(resultat.getString("prix_code"));
				pcodeField.setText(String.valueOf(prixcode));
				 prixcond=Float.parseFloat(resultat.getString("prix_conduite"));
				pcondField.setText(String.valueOf(prixcond));
				nbcode=Integer.parseInt(resultat.getString("nb_code"));
				nbcodeField.setText(String.valueOf(nbcode));
				nbcond=Integer.parseInt(resultat.getString("nb_conduite"));
				nbcondField.setText(String.valueOf(nbcond));
				 code=Float.parseFloat(resultat.getString("examen_code"));
				codeField.setText(String.valueOf(code));
				cond=Float.parseFloat(resultat.getString("examen_conduite"));
				conduiteField.setText(String.valueOf(cond));
				 montant =Float.parseFloat(resultat.getString("forfait"));
				forfaitField.setText(String.valueOf(montant));
				frais=Float.parseFloat(resultat.getString("frais_total"));
				fraisField.setText(String.valueOf(frais));
				 somme=Float.parseFloat(resultat.getString("montant_paye"));
				sommeField.setText(String.valueOf(somme));
				 reste=Float.parseFloat(resultat.getString("reste"));
				resteField.setText(String.valueOf(reste));
				dateN=resultat.getString("date_naissance");
				((JTextField)dateChooser_1.getDateEditor().getUiComponent()).setText(dateN);
				 datei=resultat.getString("date_inscription");
				((JTextField)dateChooser.getDateEditor().getUiComponent()).setText(datei);
			}
			
		}
		catch(SQLException p) {
			p.printStackTrace();
		}
 }
 public void recuperer_champs_du_tableau()//permet la récupération des informations du candidat choisi à partir du table.
 {
	 int ligne =table.getSelectedRow();
		 cin=Integer.parseInt(table.getModel().getValueAt(ligne, 0).toString());
		 nom_prenom=table.getModel().getValueAt(ligne, 1).toString();
		 tel=Integer.parseInt(table.getModel().getValueAt(ligne, 3).toString());
		 adresse=table.getModel().getValueAt(ligne, 4).toString();
		 categorie=table.getModel().getValueAt(ligne, 8).toString();
		 type=table.getModel().getValueAt(ligne, 6).toString();
		 paiement=table.getModel().getValueAt(ligne, 7).toString();
		 prixcode=Float.parseFloat(table.getModel().getValueAt(ligne, 9).toString());
		 prixcond=Float.parseFloat(table.getModel().getValueAt(ligne, 12).toString());
		 nbcode=Integer.parseInt(table.getModel().getValueAt(ligne, 10).toString());
		 nbcond=Integer.parseInt(table.getModel().getValueAt(ligne, 13).toString());
		 code=Float.parseFloat(table.getModel().getValueAt(ligne, 11).toString());
		 cond=Float.parseFloat(table.getModel().getValueAt(ligne, 14).toString());
		 montant=Float.parseFloat(table.getModel().getValueAt(ligne, 15).toString());
		 frais=Float.parseFloat(table.getModel().getValueAt(ligne, 16).toString());
		 somme=Float.parseFloat(table.getModel().getValueAt(ligne, 17).toString());
		 reste=Float.parseFloat(table.getModel().getValueAt(ligne, 18).toString());
		
		cinField.setText(String.valueOf(cin));
		npField.setText(nom_prenom);
		telField.setText(String.valueOf(tel));
		adressField.setText(adresse);
		catBox.setSelectedItem(categorie);
		typeBox.setSelectedItem(type);
		paiementBox.setSelectedItem(paiement);
		pcodeField.setText(String.valueOf(prixcode));
		pcondField.setText(String.valueOf(prixcond));
		nbcodeField.setText(String.valueOf(nbcode));
		nbcondField.setText(String.valueOf(nbcond));
		codeField.setText(String.valueOf(code));
		conduiteField.setText(String.valueOf(cond));
		forfaitField.setText(String.valueOf(montant));
		fraisField.setText(String.valueOf(frais));
		sommeField.setText(String.valueOf(somme));
		resteField.setText(String.valueOf(reste));
	
		Date date;
		try {
			date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 5).toString());
			dateChooser.setDate(date);
			date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 2).toString());
			dateChooser_1.setDate(date);

		} catch (ParseException ex) {
			Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE,null,ex);
		}
 }
}