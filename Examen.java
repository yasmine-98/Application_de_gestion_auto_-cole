
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Examen extends JFrame {

	private JPanel contentPane;
	private JTextField nom_prenom;
	private JTextField montant;
	private JTable table;
	Connection cnx=null;
	ResultSet resultat =null;
	ResultSet res =null;
	ResultSet resul =null;
	PreparedStatement prepared=null;
	private JComboBox Cin_candidat;
	private JComboBox type_examen;
	private JDateChooser date_examen;
    private JLabel label_1;
    private JLabel label;
    private JTextField txtAjouterExamen;
    private JTextField txtModifierExamen;
    private JTextField txtSuppExamen;
    private JScrollPane scrollPane;
    private JButton btnRecherche;
    private JDateChooser  recherche;
    private String etat_son;
    private long cin_candidat;
    private String nom_candidat;
    private float prix;
    private String date;
    private String type;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Examen frame = new Examen("on");
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
	public Examen(String etat) {
		etat_son=etat;
		setType(Type.POPUP);
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		setTitle("Gestion des examens");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1084, 660);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		cnx = ConnexionMysql.ConnexionDB();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gestion des examens");
		lblNewLabel.setForeground(new Color(51, 102, 204));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(379, 5, 288, 30);
		panel.add(lblNewLabel);
		txtAjouterExamen = new JTextField();
		txtAjouterExamen.setText("Ajouter examen");
		txtAjouterExamen.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtAjouterExamen.setBounds(544, 180, 100, 15);
		panel.add(txtAjouterExamen);
		txtAjouterExamen.setColumns(10);
		txtAjouterExamen.setVisible(false);
		
		txtModifierExamen = new JTextField();
		txtModifierExamen.setText("Modifier examen");
		txtModifierExamen.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtModifierExamen.setColumns(10);
		txtModifierExamen.setBounds(736, 180, 100, 15);
		panel.add(txtModifierExamen);
		txtModifierExamen.setVisible(false);
		
		txtSuppExamen = new JTextField();
		txtSuppExamen.setText("Supprimer examen");
		txtSuppExamen.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtSuppExamen.setColumns(10);
		txtSuppExamen.setBounds(927, 180, 100, 15);
		txtSuppExamen.setVisible(false);
		panel.add(txtSuppExamen);
		
		 Cin_candidat = new JComboBox();
		 Cin_candidat.setModel(new DefaultComboBoxModel(new String[] {""}));
		Cin_candidat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//En cliquant sur ce JComboBox les cartes cin des candidat qui n'ont pas encore passer l'examen vont s'apparaitre
			try
			{
				Cin_candidat.setModel(new DefaultComboBoxModel(new String[] {""}));
				
				String sql="select * from candidat c where  NOT EXISTS (select cin from  examen e where c.cin=e.cin and date>CURDATE() )";
				prepared=cnx.prepareStatement(sql);
				resultat=prepared.executeQuery();
				while(resultat.next())
				{
					Cin_candidat.addItem(resultat.getString("cin"));
				}
			
				
			}catch(SQLException e1)
		    {
			e1.printStackTrace();
		    }
			}
		});
		Cin_candidat.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cin_candidat.setBounds(180, 90, 250, 20);
		panel.add(Cin_candidat);
		
		JLabel lblNewLabel_1 = new JLabel("CIN candidat:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 90, 150, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNomEtPrnom = new JLabel("Nom et Pr\u00E9nom:");
		lblNomEtPrnom.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNomEtPrnom.setBounds(10, 127, 150, 20);
		panel.add(lblNomEtPrnom);
		
		JLabel lblTypeDexamen = new JLabel("Type d'examen:");
		lblTypeDexamen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTypeDexamen.setBounds(10, 164, 150, 20);
		panel.add(lblTypeDexamen);
		
		JLabel lblDateDexamen = new JLabel("Date d'examen:");
		lblDateDexamen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDateDexamen.setBounds(10, 201, 150, 20);
		panel.add(lblDateDexamen);
		
		JLabel lblMontant = new JLabel("Montant:");
		lblMontant.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMontant.setBounds(10, 238, 150, 20);
		panel.add(lblMontant);
		
		nom_prenom = new JTextField();
		nom_prenom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//Affichage du nom et prénom du candidat selectionné selon sa carte cin dés que vous cliquer sur le JTextField nom et prénom
				try {
				 cin_candidat=Integer.parseInt(Cin_candidat.getSelectedItem().toString());
				if(String.valueOf(cin_candidat).equals(" ")==false)
				{
					
					String sqle="select * from candidat where cin=?";
					prepared=cnx.prepareStatement(sqle);
					prepared.setString(1, String.valueOf(cin_candidat));
					resultat=prepared.executeQuery();
					if(resultat.next())
					{
						nom_prenom.setText(resultat.getString("nom_prenom"));
					}
				}
			}catch(SQLException e1)
		    {
			e1.printStackTrace();
		    }
			}
		});
		nom_prenom.setForeground(new Color(0, 0, 0));
		nom_prenom.setFont(new Font("Tahoma", Font.BOLD, 11));
		nom_prenom.setBounds(180, 127, 250, 20);
		panel.add(nom_prenom);
		nom_prenom.setColumns(10);
		
		 type_examen = new JComboBox();
		 type_examen.setModel(new DefaultComboBoxModel(new String[] {"Code", "Conduite"}));
		 type_examen.setFont(new Font("Tahoma", Font.BOLD, 11));
		 type_examen.setBounds(180, 164, 250, 20);
		panel.add( type_examen);
		
		date_examen = new JDateChooser();
	    date_examen.setDateFormatString("yyyy-MM-dd");
		date_examen.setBounds(180, 201, 250, 20);
		panel.add(date_examen);
		
		montant = new JTextField();
		montant.setForeground(Color.BLACK);
		montant.setFont(new Font("Tahoma", Font.BOLD, 11));
		montant.setColumns(10);
		montant.setBounds(180, 238, 150, 20);
		panel.add(montant);
		montant.setText("0.0");
		
		JButton btnNewButton = new JButton("Afficher tous les examens");
		btnNewButton.setBackground(new Color(204, 204, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnRecherche.setVisible(true);
				recherche.setVisible(true);
				scrollPane.setVisible(true);
				actualiser();//Afficher tous les examens enregistrés
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 204));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 340, 400, 30);
		panel.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 430, 795, 180);
		panel.add(scrollPane);
		scrollPane.setVisible(false);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recuperer_champs_du_tableau();//Récupérer les informations sur un examen en cliquant sur une ligne du tableau
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Candidat", "Type examen", "Date", "Montant"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		ImageIcon ajouter =new ImageIcon(this.getClass().getResource("/ajoute.png"));
		btnNewButton_1.setIcon(ajouter);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verifier())//Vérifier la validation des champs obligatoires
				{
				ajouter();//Ajouter un examen
				}
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtAjouterExamen.setVisible(false);
			}
		

			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(Color.black));
				txtAjouterExamen.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtAjouterExamen.setVisible(false);
			}
		});
		btnNewButton_1.setBounds(500, 100, 150, 180);
		panel.add(btnNewButton_1);
		
		JButton btnModifier = new JButton("");
		ImageIcon modifier =new ImageIcon(this.getClass().getResource("/modife.png"));
		btnModifier.setIcon(modifier);
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verifier())
				{
				modifier();//Modifier un examen
				}
			}
		});
		btnModifier.setBounds(660, 100, 176, 180);
		btnModifier.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		
		panel.add(btnModifier);
		btnModifier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtModifierExamen.setVisible(false);
			}
		

			@Override
			public void mouseEntered(MouseEvent e) {
				btnModifier.setBorder(BorderFactory.createLineBorder(Color.black));
				txtModifierExamen.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnModifier.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtModifierExamen.setVisible(false);
			}
		});
		
		JButton btnSupprimer = new JButton("");
		btnSupprimer.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
		ImageIcon supprimer =new ImageIcon(this.getClass().getResource("/suppe.png"));
		btnSupprimer.setIcon(supprimer);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprimer();//Supprimer un examen déja existant
			}
		});
		btnSupprimer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSuppExamen.setVisible(false);
			}
		

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSupprimer.setBorder(BorderFactory.createLineBorder(Color.black));
				txtSuppExamen.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSupprimer.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230)));
				txtSuppExamen.setVisible(false);
			}
		});
		btnSupprimer.setBounds(846, 100, 181, 180);
		panel.add(btnSupprimer);
		label = new JLabel("");
		ImageIcon mute =new ImageIcon(this.getClass().getResource("/off.png"));
		label.setIcon(mute);
		label.setBounds(916, 10, 64, 57);
		panel.add(label);
		label.setVisible(false);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label.setVisible(false);
				label_1.setVisible(true);
				etat_son="on";
			}
		});
		

		label_1 = new JLabel("");
		ImageIcon son =new ImageIcon(this.getClass().getResource("/on.png"));
		label_1.setIcon(son);
		label_1.setBounds(916, 10, 64, 57);
		panel.add(label_1);
	
		
		btnRecherche = new JButton("Recherche");
		ImageIcon recherche1 =new ImageIcon(this.getClass().getResource("/rech.png"));
        btnRecherche.setIcon(recherche1);
		btnRecherche.setBackground(new Color(204, 204, 255));
		btnRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recherche();//chercher tous les examens concernat une date spécifique
			}
		});
		btnRecherche.setForeground(new Color(0, 0, 204));
		btnRecherche.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRecherche.setBounds(257, 380, 150, 30);
		panel.add(btnRecherche);
		btnRecherche.setVisible(false);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Help h=new Help("examen");
				h.setVisible(true);
			}
		});
		ImageIcon help =new ImageIcon(this.getClass().getResource("/help.png"));
		lblNewLabel_3.setIcon(help);
		lblNewLabel_3.setBounds(990, 10, 64, 57);
		panel.add(lblNewLabel_3);
		
		JLabel label_2 = new JLabel("");//Retourner au menu par cette icone
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu m;
				m = new Menu(etat_son);
				m.setVisible(true);
				dispose();//fermure de fenètre 
				
			}
		});
		ImageIcon retour =new ImageIcon(this.getClass().getResource("/retour.png"));
		label_2.setIcon(retour);
		label_2.setBounds(10, 5, 64, 57);
		panel.add(label_2);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Cin_candidat.setEnabled(true);
					cin_candidat=Integer.parseInt(Cin_candidat.getSelectedItem().toString());
					if(String.valueOf(cin_candidat).equals(" ")==false)
					{
						
						String sqle="select * from candidat where cin=?";
						prepared=cnx.prepareStatement(sqle);
						prepared.setString(1, String.valueOf(cin_candidat));
						resultat=prepared.executeQuery();
						if(resultat.next())
						{
							nom_prenom.setText(resultat.getString("nom_prenom"));
						}
					}
					lblNewLabel_2.setVisible(false);
				}catch(SQLException e1)
			    {
				e1.printStackTrace();
			    }
			}
			
		});
		lblNewLabel_2.setBounds(180, 90, 250, 20);
		panel.add(lblNewLabel_2);
		
		recherche = new JDateChooser();
		recherche.setBounds(10, 380, 220, 20);
		panel.add(recherche);
		recherche.setDateFormatString("yyyy-MM-dd");
		recherche.setVisible(false);
	
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_1.setVisible(false);
				label.setVisible(true);
				etat_son="off";
			}
		});
		etat_son();
	}
	public void ajouter()//Ajouter un examen
	{
        date = ((JTextField)date_examen.getDateEditor().getUiComponent()).getText().toString();
		
		try {
		String sqle="insert into examen(cin, montant, date,type_examen )values(?,?,?,?)";
		prepared=cnx.prepareStatement(sqle);
    	prepared.setString(1, Cin_candidat.getSelectedItem().toString());
    	prepared.setString(2, montant.getText().toString());
    	prepared.setString(3, date);
    	prepared.setString(4, type_examen.getSelectedItem().toString());
    	prepared.executeUpdate();
    	message("Examen ajouté.",true);
    	reinitialiser();
    	actualiser();
		}catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }
		
	}
	
	public void supprimer()//Supprimer un examen déja existant
	{
		cin_candidat=Integer.parseInt(Cin_candidat.getSelectedItem().toString());
		String sql = "delete from examen where cin=?";

	    try {
	    	prepared=cnx.prepareStatement(sql);
	    	prepared.setString(1, String.valueOf(cin_candidat));
	    	prepared.executeUpdate();
	    	message("Examen suprimé.",true);
	    	
	    	reinitialiser();//remise des champs à leurs valeurs initiales
	    	actualiser();
	    	
	}catch(SQLException e1)
	    {
		e1.printStackTrace();
	    } 
	}
	public void actualiser()//Afficher tous les informations sur tous les examens dans un tableau
	{
		String sql="select * from examen";
		try {
			prepared  =cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}catch(Exception e1)
		{
			e1.printStackTrace();
	    }
	}
	public void modifier()//Modifier un examen existant
	{
        date = ((JTextField)date_examen.getDateEditor().getUiComponent()).getText().toString();
		
		try {
		String sqle="update examen set  montant=?, date=?,type_examen=? where cin=?";
		prepared=cnx.prepareStatement(sqle);
    	prepared.setString(4, Cin_candidat.getSelectedItem().toString());
    	prepared.setString(1, montant.getText().toString());
    	prepared.setString(2, date);
    	prepared.setString(3, type_examen.getSelectedItem().toString());
    	prepared.executeUpdate();
    	message("Examen modifié.",true);
		reinitialiser();
    	actualiser();
		}catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }
		
	}
	public boolean verifier()//Vérifier la validation des champs obligatoires
	{
		cin_candidat=Integer.parseInt(Cin_candidat.getSelectedItem().toString());
		   if(String.valueOf(cin_candidat).equals(""))
			{
				message("Vérifiez votre carte d'identité.",false);
				return false;
			}
			
		   else
			{
				nom_candidat=nom_prenom.getText().toString();
				
				if((nom_candidat.matches("^[A-Z]|[a-z]*$")==false)||(nom_candidat.equals("")))
				{
					message("Verifiez votre nom et prenom.",false);
					return false;
				}
			    else
			    {
			    	
			    	prix=Float.parseFloat(montant.getText().toString());
					
			    	if((String.valueOf(prix).matches("[0-9]+.[0-9]+|[0-9]+")==false)||(String.valueOf(prix).matches("[0]+|[0]+.[0]+")==true))
	                   {
			    		message("Verifiez votre montant.",false);
						return false;
	                   }
			    }
			}
		   return true;
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
	public void recuperer_champs_du_tableau()//Récupérer les information d'un examen en cliquant sur une ligne du tableau
	{
		Date date1;
		int ligne =table.getSelectedRow();
		 type = table.getModel().getValueAt(ligne, 1).toString();
		cin_candidat = Integer.parseInt(table.getModel().getValueAt(ligne, 0).toString());
		prix = Float.parseFloat(table.getModel().getValueAt(ligne, 3).toString());
		
		try {
			 date1= new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getModel().getValueAt(ligne, 2).toString());
			date_examen.setDate(date1);
			
			
		}
		catch(ParseException ex)
		{
			Logger.getLogger(Moniteur.class.getName()).log(Level.SEVERE,null,ex);
		}
		try {
			String sql="select * from candidat where cin=?";
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1, String.valueOf(cin_candidat));
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				nom_prenom.setText(resultat.getString("nom_prenom"));
			}
		}catch(Exception e1)
		{
			e1.printStackTrace();
	}
		Cin_candidat.addItem(String.valueOf(cin_candidat));
		Cin_candidat.setSelectedItem(String.valueOf(cin_candidat));
		montant.setText(String.valueOf(prix));
		type_examen.setSelectedItem(type);
		
	}
	public void reinitialiser()//Remise les champs à leurs valeurs initiales
	{
		Cin_candidat.setSelectedIndex(0);
		type_examen.setSelectedIndex(0);
		nom_prenom.setText("");
		montant.setText("0.0");
	    Date date=new Date();
		date_examen.setDate(date);
		}
	
	
      
	
	public void recherche()//Chercher tous les examens d'une date précise
	{
		String m =((JTextField) recherche.getDateEditor().getUiComponent()).getText();
        
           try {
        	String sqle="select * from examen where date=?";
        	prepared  =cnx.prepareStatement(sqle);
			prepared.setString(1, m);
			resultat=prepared.executeQuery();
			if (resultat.next())
			{
				
			   String sql="select * from examen where date=?";
		       prepared  =cnx.prepareStatement(sql);
			   prepared.setString(1, m);
			   resultat=prepared.executeQuery();
			   table.setModel(DbUtils.resultSetToTableModel(resultat));
			}
			else {
				message("Il n'ya aucun examen pour cette date.",true);
				String sql="select * from examen where date=?";
			       prepared  =cnx.prepareStatement(sql);
				   prepared.setString(1, m);
				   resultat=prepared.executeQuery();
				   table.setModel(DbUtils.resultSetToTableModel(resultat));
			}
		   }catch(Exception e1)
		    {
			e1.printStackTrace();
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

