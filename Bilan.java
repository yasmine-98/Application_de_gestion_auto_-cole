import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Bilan extends JFrame{

	private JPanel contentPane;
	java.sql.Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	private JTextField rechField;
	private JTable table;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private String etat;

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
					Bilan frame = new Bilan("on");
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
	public Bilan(String s) {
		etat=s;
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		setTitle("Bilan financier");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 938, 521);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Bilan mensuel");//en cliquant sur ce bouton, les bilans mensuels s'affichent 
		btnNewButton.setForeground(new Color(51, 102, 204));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bilan_mensuel();
				actualiser_mensuel();
				audio(true);
			}
		});
		btnNewButton.setBounds(494, 147, 199, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Bilan annuel");//en cliquant sur ce bouton, les bilans annuels s'affichent
		btnNewButton_1.setForeground(new Color(51, 102, 204));
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bilan_annuel();
				actualiser_annuel();
				audio(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(703, 147, 199, 23);
		panel.add(btnNewButton_1);
		
		rechField = new JTextField();//le champs ou on écrit la date à chercher
		rechField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				bilan_mensuel();
				bilan_annuel();
			}
		});
		rechField.setBounds(10, 148, 177, 20);
		panel.add(rechField);
		rechField.setColumns(10);
		
		JLabel btnNewButton_2 = new JLabel("");//en cliquant sur ce bouton, le bilan correspondant la date écrit dans le rechField s'affiche
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rechercher();
			}
		});
		ImageIcon rech =new ImageIcon(this.getClass().getResource("/rech.png"));
		btnNewButton_2.setIcon(rech);
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBackground(new Color(0, 0, 128));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.setBounds(193, 147, 20, 23);
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 181, 900, 294);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(255, 255, 255));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Date", "Revenu candidat", "Frais moniteur", "Frais vehicule", "Revenu total"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Bilan financier");
		lblNewLabel.setForeground(new Color(51, 102, 204));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel.setBounds(359, 11, 273, 45);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");//retour au menu
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu menu=new Menu(etat);
				menu.setVisible(true);
				dispose();
			}
		});
		ImageIcon retour =new ImageIcon(this.getClass().getResource("/retour.png"));
		lblNewLabel_1.setIcon(retour);
		lblNewLabel_1.setBounds(0, 0, 64, 64);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_3 = new JLabel("on");//désactiver le son en cliquant sur ce bouton
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_3.setVisible(false);
				lblNewLabel_4.setVisible(true);
				etat="off";
			}
		});
		ImageIcon on_icon =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_3.setIcon(on_icon);
		lblNewLabel_3.setBounds(846, 0, 64, 64);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("off");//activer le son en cliquant sur ce bouton
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_4.setVisible(false);
				lblNewLabel_3.setVisible(true);
				etat="on";
			}
		});
		ImageIcon off_icon =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_4.setIcon(off_icon);
		lblNewLabel_4.setBounds(846, 0, 64, 64);
		panel.add(lblNewLabel_4);
		etat_son();//configurer l'état sonore de l'interafce à l'ouverture de la page
	}
	public float calculer_candidat(String m,String y) //calculer le revenu obtenu des candidats pour un mois spécifique 
	{
		float somme=0.0f;
		String sql="select * from frais_candidat where MONTH(date)=? and YEAR(date)=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1,m);
			prepared.setString(2,y);
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String mon=resultat.getString("montant_paye");
	 			Float p=Float.parseFloat(mon);
	 			somme+=p;
	 		}
	 		String sql1="select * from examen where MONTH(date)=? and YEAR(date)=?";
	 		prepared=cnx.prepareStatement(sql1);
			prepared.setString(1,m);
			prepared.setString(2,y);
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String exam=resultat.getString("montant");
	 			Float p=Float.parseFloat(exam);
	 			somme-=p;
	 		}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return(somme);
	}
	public float calculer_moniteur(String m,String y)//calculer les frais des moniteurs pour un mois spécifique
	{
		float somme=0.0f;
		String sql="select * from moniteur where specialite=? and MONTH(date_recrutement)<=? and YEAR(date_recrutement)<=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1,"Code");
			prepared.setString(2,m);
			prepared.setString(3,y);
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String sal=resultat.getString("salaire");
	 			Float s=Float.parseFloat(sal);
 				somme+=s;
	 		}
	 		String sql1="select * from moniteur where specialite=?";
	 		prepared=cnx.prepareStatement(sql1);
			prepared.setString(1,"Conduite");
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String cin=resultat.getString("cin");
	 			String prix=resultat.getString("prix_seance");
	 			String sql2="select count(*) as n from seance_moniteur where MONTH(date_heure)=? and YEAR(date_heure)=? and cin=?";
 				prepared=cnx.prepareStatement(sql2);
 				prepared.setString(1,m);
 				prepared.setString(2,y);
 				prepared.setString(3,cin);
 				resultat=prepared.executeQuery();
 		 		while(resultat.next())
 		 		{
 		 			int n=resultat.getInt("n");
 		 			somme+=n*Float.parseFloat(prix);
 		 		}
	 		}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return(somme);
	}
	public float calculer_vehicule(String m,String y) //calculer les frais des véhicules pour un mois spécifique
	{
		float somme=0.0f;
		String sql="select * from frais_vehicule where MONTH(date_paiement)=? and YEAR(date_paiement)=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1,m);
			prepared.setString(2,y);
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String mon=resultat.getString("montant");
	 			Float f=Float.parseFloat(mon);
	 			somme+=f;
	 		}
	 		String sql5="select * from vehicule";
	 		prepared=cnx.prepareStatement(sql5);
			resultat=prepared.executeQuery();
	 		while(resultat.next())
	 		{
	 			String ess=resultat.getString("carburant");
	 			String mat=resultat.getString("matricule");
	 			String marq=resultat.getString("marque");
	 			String sql6="select count(*) as n from seance_moniteur where vehicule=? and MONTH(date_heure)=? and YEAR(date_heure)=? ";
 				prepared=cnx.prepareStatement(sql6);
 				prepared.setString(1,mat+"/"+marq);
 				prepared.setString(2,m);
 				prepared.setString(3,y);
 				resultat=prepared.executeQuery();
 		 		while(resultat.next())
 		 		{
 		 			int n=resultat.getInt("n");
 		 			somme+=n*Float.parseFloat(ess);
 		 		}
	 		}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return(somme);
	}
	public void ajout_mensuel(String m,String y)//ajouter le revenu et les dépenses pour chaque mois au tableau correspondant à la base de données
	{
		String can=Float.toString(calculer_candidat(m,y));
	    String mon=Float.toString(calculer_moniteur(m,y));
	    String veh=Float.toString(calculer_vehicule(m,y));
	    String tot=Float.toString(calculer_candidat(m,y)-calculer_vehicule(m,y)-calculer_moniteur(m,y));	
   	    String sql1="insert into bilan_mensuel(date,revenu_candidat,frais_moniteur,frais_vehicule,revenu_total) values (?,?,?,?,?)";
		try {		  
					prepared=cnx.prepareStatement(sql1);
					if(Integer.parseInt(m)<10)
						prepared.setString(1,y+"-0"+m);
					else 
						prepared.setString(1,y+"-"+m);
					prepared.setString(2,can);
					prepared.setString(3,mon);
					prepared.setString(4,veh);
					prepared.setString(5,tot);
					prepared.execute();
			}
			catch(SQLException p)
			{
				p.printStackTrace();
			}
		
	}
	public void ajout_annuel(String y)//ajouter le revenu et les dépenses pour chaque année au tableau correspondant à la base de données
	{
		Float cand=0.0f;
		Float moni=0.0f;
		Float vehi=0.0f;
		Float tot=0.0f;
		try {
 		    	String sql1="select * from bilan_mensuel where YEAR(date)=?";
 		    	prepared=cnx.prepareStatement(sql1);
 		    	prepared.setString(1,y);
 		    	resultat=prepared.executeQuery();
 	 		    while(resultat.next())
 	 		    {
 	 		    	String c=resultat.getString("revenu_candidat");
 	 		    	String m=resultat.getString("frais_moniteur");
 	 		    	String v=resultat.getString("frais_vehicule");
 	 		    	String t=resultat.getString("revenu_total");
 	 		    	cand+=Float.parseFloat(c);
 	 		    	moni+=Float.parseFloat(m);
 	 		    	vehi+=Float.parseFloat(v);
 	 		    	tot+=Float.parseFloat(t);
 	 		    }
 	 		  String sql2="insert into bilan_annuel(annee,revenu_candidat,frais_moniteur,frais_vehicule,revenu_total) values (?,?,?,?,?)";
				prepared=cnx.prepareStatement(sql2);
				prepared.setString(1,y);
				prepared.setString(2,Float.toString(cand));
				prepared.setString(3,Float.toString(moni));
				prepared.setString(4,Float.toString(vehi));
				prepared.setString(5,Float.toString(tot));
				prepared.execute();
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
	public void actualiser_mensuel()//afficher les données du tableau contenant les bilans mensuels
	{
		String sql="select * from bilan_mensuel";
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
	public void actualiser_annuel()//afficher les données du tableau contenant les bilans annuels
	{
		String sql="select * from bilan_annuel";
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
	public void rechercher()//rechercher le bilan pour une année ou un mois donné
	{
		String rech=rechField.getText().toString();
		try {
		if(rech.matches("[0-9][0-9][0-9][0-9]"))
		{
			String sql1="select * from bilan_annuel where annee=?";
			prepared=cnx.prepareStatement(sql1);
			prepared.setString(1,rech);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}
		else {    
		    String sql="select * from bilan_mensuel where date=?";
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1,rech);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
	public void bilan_mensuel()//ajouter tout les bilans mensuels dés l'ouverture de l'école au tableau correspondant sans répétition
	{
		String ouv=get_date_ouverture();
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		String sql="select * from bilan_mensuel ORDER BY date DESC";
		try {
		    prepared=cnx.prepareStatement(sql);
		    resultat=prepared.executeQuery();
 		    if(resultat.next())
 		    {
 		    	String dat=resultat.getString("date");
 		    	String mois=dat.substring(5);
 		    	int m=Integer.parseInt(mois);
 		    	String annee=dat.substring(0,4);
 		    	int y=Integer.parseInt(annee);
 		    	if(y==year)
 		    	{
 		    	   if(m<month-1)
 		    	   {
 		    		   for(int i=m+1;i<month;i++)
 		    		   {
 		    			  ajout_mensuel(Integer.toString(i),annee);
 		    		   }
 		    	   }
 		    	}
 		    	else if(y==year-1)
 		    	{
 		    		for(int i=m;i<13;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(y));
		    		}
 		    		for(int i=1;i<month;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(year));
		    		}
 		    	}
 		    	else if(y<year-1)
 		    	{
 		    		for(int i=m;i<13;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(y));
		    		}
 		    		for(int i=y+1;i<year;i++)
 		    		{
 		    			for(int j=1;j<13;j++)
 			    		{
 			    		   ajout_mensuel(Integer.toString(j),Integer.toString(i));
 			    		}
 		    		}
 		    		for(int i=1;i<month;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(year));
		    		}
 		    	}
 		    }
 		    else
 		    {
 		    	String mois=ouv.substring(5,7);
 		    	int m=Integer.parseInt(mois);
 		    	String annee=ouv.substring(0,4);
 		    	int y=Integer.parseInt(annee);
 		    	if(y==year)
 		    	{
 		    	   if(m<month-1)
 		    	   {
 		    		   for(int i=m+1;i<month;i++)
 		    		   {
 		    			  ajout_mensuel(Integer.toString(i),annee);
 		    		   }
 		    	   }
 		    	   else if(m==month-1)
 		    	   {
 		    		   ajout_mensuel(Integer.toString(m),annee);
 		    	   }
 		    	}
 		    	else if(y==year-1)
 		    	{
 		    		for(int i=m;i<13;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(y));
		    		}
 		    		for(int i=1;i<month;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(year));
		    		}
 		    	}
 		    	else if(y<year-1)
 		    	{
 		    		for(int i=m;i<13;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(y));
		    		}
 		    		for(int i=y+1;i<year;i++)
 		    		{
 		    			for(int j=1;j<13;j++)
 			    		{
 			    		   ajout_mensuel(Integer.toString(j),Integer.toString(i));
 			    		}
 		    		}
 		    		for(int i=1;i<month;i++)
		    		{
		    		   ajout_mensuel(Integer.toString(i),Integer.toString(year));
		    		}
 		    	}
 		    }
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
	public void bilan_annuel()//ajouter tout les bilans annuels dés l'ouverture de l'école au tableau correspondant sans répétition
	{
		String ouv=get_date_ouverture();
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		String sql="select * from bilan_annuel ORDER BY annee DESC";
		try {
		    prepared=cnx.prepareStatement(sql);
		    resultat=prepared.executeQuery();
 		    if(resultat.next())
 		    {
 		    	String annee=resultat.getString("annee");
 		    	int y=Integer.parseInt(annee);
 		    	if(y<year-1)
 		    	{
 		    		for(int i=y;i<year;i++)
 		    		{
 		    			ajout_annuel(Integer.toString(i));
 		    		}
 		    	}
 		    }
 		    else
 		    {
 		    	String annee=ouv.substring(0,4);
 		    	int y=Integer.parseInt(annee);
 		    	if(y<year-1)
 		    	{
 		    		for(int i=y;i<year;i++)
 		    		{
 		    			ajout_annuel(Integer.toString(i));
 		    		}
 		    	}
 		    	
 		    }
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
	public String get_date_ouverture()//donner la date d'ouverture de l'école
	{
		String date="";
		String sql="select date_ouverture from ecole";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				date=resultat.getString("date_ouverture");
			}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		return(date);
	}
	public void audio(boolean type)//gérer le son selon le message (succès ou echec)
    {
    	if(lblNewLabel_3.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("on",type);
			t.start();
		}
		else if(lblNewLabel_4.isShowing())
		{
			Audio t = new Audio("audio",type);
			t.set_sound("off",type);
			t.start();
		}
    }
	public void etat_son()//configurer l'état sonore de l'interafce à l'ouverture de la page
	{
		if(etat=="on")
		{
			lblNewLabel_3.setVisible(true);
			lblNewLabel_4.setVisible(false);
		}
		else if(etat=="off")
		{
			lblNewLabel_4.setVisible(true);
			lblNewLabel_3.setVisible(false);
		}
	}
}
