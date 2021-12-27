import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

public class Frais_vehicule extends JFrame {

	private JPanel contentPane;
	private JTextField monField;
	private JDateChooser dateChooser;
	private JTable table;
	private String etat;
	private float montant;
	private String date;
	java.sql.Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;

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
					Vehicule veh=new Vehicule("on");
					Frais_vehicule frame = new Frais_vehicule("","Assurance","on",veh);
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
	public Frais_vehicule(String mat,String type,String s,Vehicule veh) {
		setTitle("Frais du v\u00E9hicule");
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		etat=s;
		veh.setEnabled(false);
		addWindowListener(new WindowAdapter()//interdire l'utilisation de la page du véhicule quand la page des frais est ouvert
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				veh.setEnabled(true);
			}		
		});		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(176, 224, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(type);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(51, 102, 204));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(92, 23, 399, 29);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Date:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 95, 46, 14);
		panel.add(lblNewLabel_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(103, 89, 169, 20);
		panel.add(dateChooser);
		
		JLabel lblNewLabel_2 = new JLabel("Montant:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 144, 65, 14);
		panel.add(lblNewLabel_2);
		
		monField = new JTextField();
		monField.setBounds(103, 141, 169, 20);
		panel.add(monField);
		monField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 200, 554, 140);
		panel.add(scrollPane);
		
		table = new JTable();
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
				"New column", "New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Enregistrer");//bouton d'enregistrement du frais
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(new Color(51, 102, 204));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enregistrer(mat,type);//enregistrer le frais
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(282, 140, 102, 23);
		panel.add(btnNewButton);
	}
	public void actualiser(String type, String matricule)//actualiser le tableau
	{
		String sql="select * from frais_vehicule where type_frais=? and matricule=?";
		try {
			prepared=cnx.prepareStatement(sql);
			prepared.setString(1,type);
			prepared.setString(2,matricule);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
		
	}
	public void enregistrer(String matricule,String type)//enregistrer le frais
	{
		montant =Float.parseFloat(monField.getText().toString());
		date =((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
		String sql="select * from frais_vehicule where matricule=? and type_frais=? and date_paiement=?";
		try {
			prepared=cnx.prepareStatement(sql);
    		prepared.setString(1,matricule);
    		prepared.setString(2,type);
    		prepared.setString(3,date);
    		resultat=prepared.executeQuery();
			if(!resultat.next())
			{
				String sql1="insert into frais_vehicule(matricule,type_frais,date_paiement,montant) values (?,?,?,?)";
	    		prepared=cnx.prepareStatement(sql1);
	    		prepared.setString(2,type);
	    		if(!matricule.equals(""))
	    		{
	    			prepared.setString(1,matricule);
	    			if(!date.equals("") && date.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]"))
	    			{
	    				prepared.setString(3,date);
	    				if((Float.toString(montant).matches("[0-9]+.[0-9]+|[0-9]+")) &&(!Float.toString(montant).matches("[0]+|[0]+.[0]+")))
	    				{
	    					prepared.setString(4,Float.toString(montant));
	    					prepared.execute();
	    					message("Enregisté",true);
	    		    		actualiser(type,matricule);
	    				}
	    				else
	    					message("Vérifiez le montant.",false);
	    			}
	    			else
	    				message("Vérifiez la date.",false);
	    			
	    		}
	    		
	    	}
		}
		catch(SQLException p)
		{
			p.printStackTrace();
		}
	}
	public void message(String msg,boolean type)//donner un message sonore selon son type (succès ou echec)
    {
    	if(etat=="on")
		{
			Audio t = new Audio("audio",type);
			t.set_sound("on",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
		else if(etat=="off")
		{
			Audio t = new Audio("audio",type);
			t.set_sound("off",type);
			t.start();
		    Audio t2 = new Audio("message",msg,type);
		    t2.start();
		}
    }
}
