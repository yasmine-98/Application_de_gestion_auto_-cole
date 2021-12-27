import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import com.toedter.calendar.JCalendar;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.beans.VetoableChangeListener;
public class Seance extends JFrame {
	java.sql.Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	
	private JPanel contentPane;
	JTextField cinField;
    JTextField npField;
	JComboBox typeBox;
	JComboBox moniteurBox;//variable global
	JComboBox vehiculeBox;
	JComboBox salleBox;
	JDateChooser dateChooser;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
    private JTable table;
    private JTextField txtCliquerPourAjouter;
    private JTextField txtCliquerPourModifier;
    private JTextField txtRecherche;
    private JTextField txtCliquerDeuxFois;
    private JTextField txtAucunMoniteurDisponible;
    private JTextField txtAucuneVehiculeEst;
    private JTextField txtAucuneSalleDisponible;
    
    
    private String etatS="on";
    private Vector vec;
    private int  n1=0 , n2=0 ,nbcond =0;
    private String dat="";
    private long cin ;
    private String nom_prenom ;
	private String type ;
	private String dateS;
	private String moniteur;
	private String vehicule;
	private String nsalle;
	private String recherche;
  
    
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
					Vector v=new Vector();
					v.add("");
					v.add("");
					v.add("");
					v.add("");
					v.add("");
					v.add("");
					Seance frame = new Seance(v);
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
	public Seance(Vector v) {
		etatS=v.get(5).toString();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//Des conditions sur la fermeture de la fenêtre séance
				if(v.get(0).toString().equals(""))//1ère cas ouverture de la séance à partir du menu
				{
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					
						if(n2>0)
					     {
						     if(n2>=nbcond)
						      {
							    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							    
						      }
						     
						     else if(n2<nbcond)
						      {
						    	 int r=nbcond-n2;
						    	
						    	 message("Il vous reste encore "+r+" seances code a saisir.",false);
						       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					          } 
						     
					     }
				    	 if(n2==0)
				    	 {
					       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    	 }
				}
				else//2ème cas ouverture de la séance automatique dés l'ajout d'un candidat 
				{ 
				  if(v.get(4).toString().equals("Code"))
				 {
					int nb=Integer.parseInt(v.get(2).toString());
					
					if(n1 < nb)
					{   int r=nb-n1;
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						
						message("Il vous reste encore "+ r+" seances code a saisir.",false);
					}
				 }
				  if(v.get(4).toString().equals("Code et conduite"))
				  {
					 
					  int nbcode=Integer.parseInt(v.get(2).toString());
						if(n1 >= nbcode)
						{
							
								int nbcond=Integer.parseInt(v.get(3).toString());
								if(n2 < nbcond && n2!=0)
								{   int r=nbcond-n2;
								    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
									message("vous devez saisir encore "+r+"  seances conduit.",false);
								}
						}
						else
						{ int rc=nbcode-n1;
							setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							message("vous devez saisir encore "+ rc+"  seances code.",false);
						}
					 
				 }
				  if((v.get(4).toString().equals("Conduite"))||(v.get(4).toString().equals("Conduite sans examen")))
				  {
					  int nbcond=Integer.parseInt(v.get(3).toString());
						if(n2 < nbcond)
						{    int r=nbcond-n2;
							setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							
							message("vous devez saisir encore "+ r+"  séances conduites.",false);
						}
				  }
			    }
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		setResizable(false);
		setTitle("Gestion des s\u00E9ances");
		
		setBounds(80, 0, 1050, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 102, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cnx=ConnexionMysql.ConnexionDB(); 
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtCliquerPourModifier = new JTextField();
		txtCliquerPourModifier.setVisible(false);
		
		txtAucuneVehiculeEst = new JTextField();
		txtAucuneVehiculeEst.setVisible(false);
		txtAucuneVehiculeEst.setText("Aucune vehicule disponible.");
		txtAucuneVehiculeEst.setBounds(248, 292, 164, 20);
		panel.add(txtAucuneVehiculeEst);
		txtAucuneVehiculeEst.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("");
		lblNewLabel_14.setBackground(Color.WHITE);
		ImageIcon r =new ImageIcon(this.getClass().getResource("/rech.png"));
		lblNewLabel_14.setIcon(r);
		lblNewLabel_14.setBounds(197, 433, 20, 20);
		panel.add(lblNewLabel_14);
		
		txtCliquerDeuxFois = new JTextField();
		txtCliquerDeuxFois.setVisible(false);
		txtCliquerDeuxFois.setText("Cliquer deux fois.");
		txtCliquerDeuxFois.setBackground(new Color(176, 196, 222));
		txtCliquerDeuxFois.setBounds(285, 254, 103, 20);
		panel.add(txtCliquerDeuxFois);
		txtCliquerDeuxFois.setColumns(10);
		
		txtCliquerPourModifier.setBackground(new Color(176, 196, 222));
		txtCliquerPourModifier.setText("Cliquer pour modifier une seance.");
		txtCliquerPourModifier.setBounds(374, 310, 209, 20);
		panel.add(txtCliquerPourModifier);
		txtCliquerPourModifier.setColumns(10);
		
		txtCliquerPourAjouter = new JTextField();
		txtCliquerPourAjouter.setVisible(false);
		txtCliquerPourAjouter.setText("Cliquer pour ajouter une seance.");
		txtCliquerPourAjouter.setBackground(new Color(176, 196, 222));
		txtCliquerPourAjouter.setBounds(374, 175, 209, 20);
		panel.add(txtCliquerPourAjouter);
		txtCliquerPourAjouter.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("CIN du candidat:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 91, 103, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom et pr\u00E9nom:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 128, 97, 14);
		panel.add(lblNewLabel_1);
		
		cinField = new JTextField();
		cinField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				recuperer_champs();//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ.
			}
		});
		cinField.setBounds(131, 88, 164, 20);
		panel.add(cinField);
		cinField.setColumns(10);
		cinField.setText(v.get(0).toString());
		
		npField = new JTextField();
		npField.setBounds(131, 125, 164, 20);
		panel.add(npField);
		npField.setColumns(10);
		npField.setText(v.get(1).toString());
		
		JLabel lblNewLabel_2 = new JLabel("Type de seance:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 165, 103, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Date de seance:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 202, 97, 14);
		panel.add(lblNewLabel_3);
		
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setToolTipText("yyyy-MM-dd HH:mm");
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				 date_change();//permet de connaître si la date de la séance a changé ou pas (si oui il ya des modifications dans les champs).
			}
		});
		dateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
		dateChooser.setBounds(131, 202, 165, 22);
		panel.add(dateChooser);
		
		txtAucunMoniteurDisponible = new JTextField();
		txtAucunMoniteurDisponible.setVisible(false);
		txtAucunMoniteurDisponible.setText("aucun moniteur disponible.");
		txtAucunMoniteurDisponible.setBounds(248, 236, 164, 20);
		panel.add(txtAucunMoniteurDisponible);
		txtAucunMoniteurDisponible.setColumns(10);
		
			
		txtAucuneSalleDisponible= new JTextField();
		txtAucuneSalleDisponible.setVisible(false);
		txtAucuneSalleDisponible.setText("Aucune salle disponible.");
		txtAucuneSalleDisponible.setBounds(248, 337, 164, 20);
		panel.add(txtAucuneSalleDisponible);
		txtAucuneSalleDisponible.setColumns(10);
         
	    lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCliquerDeuxFois.setVisible(false);
				String dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
				moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				if(!dateS.equals(""))//Il faut préciser la date car par la suite on a besoin pour la sélection des moniteurs disponibles
				   {
					   if(remplirMoniteur()==false)//Cette fonction remplie la liste des moniteurs disponibles et retourne false s'il y a aucun moniteur disponible
				    	   txtAucunMoniteurDisponible.setVisible(true);// afficher un message 
			           else
			           {
			        	   moniteurBox.setEnabled(true);
					       lblNewLabel_11.setVisible(false);
			           }
				   }
				else
					message("D'abord saisir la date.",false);
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				txtCliquerDeuxFois.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtCliquerDeuxFois.setVisible(false);
				txtAucunMoniteurDisponible.setVisible(false);
			}
		});
		lblNewLabel_11.setBounds(130, 236, 165, 22);
		panel.add(lblNewLabel_11);
		
		moniteurBox = new JComboBox();
		moniteurBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Remplir la liste des véhicules ou salles dès que l'utilisateur choisi un moniteur
				txtAucuneSalleDisponible.setVisible(false);
				txtAucuneVehiculeEst.setVisible(false);
				String type=typeBox.getSelectedItem().toString();
				String m=moniteurBox.getSelectedItem().toString();
			 if(!lblNewLabel_11.isShowing())
			 {
				if (type.equals("Code") && (!m.equals("--")))
				{
				    salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
                  if(remplirsalle()==false)
           		      txtAucuneSalleDisponible.setVisible(true);
                  else
                	   salleBox.setEnabled(true);
				}
				if (type.equals("Conduite") && !m.equals("--"))
				{
				   vehiculeBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
                  if( remplirVehicule()==false)
                	  txtAucuneVehiculeEst.setVisible(true);
                  else
                	  vehiculeBox.setEnabled(true);
				}
			}
			}
		
		});
		moniteurBox.setEnabled(false);
		moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
		moniteurBox.setBounds(130, 236, 165, 22);
		panel.add(moniteurBox);
		
		JLabel lblNewLabel_4 = new JLabel("Moniteur:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 239, 97, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Vehicule:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 276, 61, 14);
		panel.add(lblNewLabel_5);
		
		vehiculeBox = new JComboBox();
		vehiculeBox.setEnabled(false);
		vehiculeBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
		vehiculeBox.setBounds(131, 273, 164, 22);
		panel.add(vehiculeBox);
		
		JLabel lblNewLabel_6 = new JLabel("N\u00B0salle");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(10, 313, 73, 14);
		panel.add(lblNewLabel_6);
		
	    salleBox = new JComboBox();
	    salleBox.setEnabled(false);
	    salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
		salleBox.setBounds(131, 310, 164, 22);
		panel.add(salleBox);
		
		typeBox = new JComboBox();
	    typeBox.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//Si le type de la séance change tous les autres champs seront initialisés car la liste des moniteur change en cas du code ou conduite
	    		moniteurBox.setEnabled(false);
		        lblNewLabel_11.setVisible(true);
	            salleBox.setEnabled(false);
	            vehiculeBox.setEnabled(false);
	            salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				vehiculeBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				((JTextField) dateChooser.getDateEditor().getUiComponent()).setText("");
	    	}
	    });
	    if((v.get(0).toString().equals("")) || v.get(4).toString().equals("Code et conduite") )
		  typeBox.setModel(new DefaultComboBoxModel(new String[] {"Code", "Conduite"}));
	    if(v.get(4).toString().equals("Code"))
	      typeBox.setModel(new DefaultComboBoxModel(new String[] {"Code"}));
	    if((v.get(4).toString().equals("Conduite"))||(v.get(4).toString().equals("Conduite sans examen")))
	      typeBox.setModel(new DefaultComboBoxModel(new String[] {"Conduite"}));
		typeBox.setBounds(131, 162, 164, 22);
		panel.add(typeBox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(61, 464, 911, 207);
		panel.add(scrollPane_1);
		
		vec = new Vector();
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				  recuperer_champs_du_table(v);//permet la récupération des informations du candidat choisi à partir du table.
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane_1.setViewportView(table);
		
		JButton btnNewButton = new JButton("");//bouton de l'ajout
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtCliquerPourAjouter.setVisible(false);
			}
		});
		btnNewButton.setBorder(null);
		ImageIcon add =new ImageIcon(this.getClass().getResource("/ajouts.png"));
		btnNewButton.setIcon(add);
		btnNewButton.setBackground(new Color(173, 216, 230));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typ =typeBox.getSelectedItem().toString();
			    String cin= cinField.getText().toString();
			    String dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
			   
			 if(!v.get(0).toString().equals(""))//cas ouverture de la séance automatique dés l'ajout d'un candidat 
		     {
				if((v.get(4).toString().equals("Code")) && (n1<Integer.parseInt(v.get(2).toString())))//La séance est code et le nombre de séances ajoutées est inférieur au nombre total des séances.
				{
					if(verif()==true)//vérifie la validation des champs obligatoires.
					{
					   if(date_existe()==true)//permet de vérifier si le candidat a déjà une séance a cette date ou non.
				    	 {
					       ajouter(); //permet l'ajoute d'une séance.
				            n1++;//incrémenter le nombre des séances ajoutées.
				            lblNewLabel_11.setVisible(true);
				            moniteurBox.setEnabled(false);
				            salleBox.setEnabled(false);
				            if(n1==Integer.parseInt(v.get(2).toString()))//Si toutes les séances sont ajoutées un message s'affiche.
				            {
					          message("les seances de code sont ajoutées.",true);
					          dispose();
				            }
				    	 }
					   else
						 message("Choisir une autre date.",false);
					}
				}
				if((v.get(4).toString().equals("Conduite")) && (n2<Integer.parseInt(v.get(3).toString())))//La séance est conduite et le nombre des séances ajoutées est inférieur au nombre total des séances.
				{
				  if(verif()==true)//vérifie la validation des champs obligatoires.
				  {
				   if(date_existe()==true)//permet de vérifier si le candidat a déjà une séance a cette date ou non.
				    {
					   ajouter();//permet l'ajoute d'une séance.
				       n2++;//incrémenter le nombre des séances ajoutées.
				       lblNewLabel_11.setVisible(true);
			            moniteurBox.setEnabled(false);
			            vehiculeBox.setEnabled(false);
				       if(n2==Integer.parseInt(v.get(3).toString()))//Si toutes les séances sont ajoutées un message s'affiche.
				       {
					    message("les seances de conduite sont ajoutées",true);
					     dispose();
				       }
				     }
				       else
							message("Choisir une autre date",false);
					}
					
				}
				
				if(v.get(4).toString().equals("Code et conduite")) 
				{
					if(typ.equals("Code"))
					{  
					 if(n1<Integer.parseInt(v.get(2).toString()))
					 {
					   if(verif()==true)//vérifie la validation des champs obligatoires.
						{
					      if(date_existe()==true)//permet de vérifier si le candidat a déjà une séance a cette date ou non.
						   {
				            ajouter();//permet l'ajoute d'une séance.
					        n1++; 
					       lblNewLabel_11.setVisible(true);
				            moniteurBox.setEnabled(false);
				            salleBox.setEnabled(false);
					        if(n1==Integer.parseInt(v.get(2).toString()))
					        {
						     message("les seances de code sont ajoutées vous pouvez quitter ou completer les seances de conduite.",true);
						   
					        }
					      }
					      else
								 message("Choisir une autre date",false);
						}
						
					 }
					 else
						 message("vous avez déja ajouter tous seances de code de ce candidat.",false);
						 
					}
				    if(typ.equals("Conduite"))
					{
				    	if(n1==Integer.parseInt(v.get(2).toString()))
				    	{
				    	  if(verif()==true)//vérifie la validation des champs obligatoires.
						  {
						    if(date_existe()==true)//permet de vérifier si le candidat a déjà une séance a cette date ou non.
							  {
					            ajouter();//permet l'ajoute d'une séance.
					            n2++;
					            lblNewLabel_11.setVisible(true);
					       
					            moniteurBox.setEnabled(false);
					            vehiculeBox.setEnabled(false);
					            if(n2==Integer.parseInt(v.get(3).toString()))
						        {
							     message("les seances de conduite sont toutes ajoutées.",true);
							     dispose();
						        }
						      }
						      else
								 message("Choisir une autre date.",false);
						  }
				    		
				    	}
				    	else
					    	message("vous devez ajouter tous les seances de code tous d'abord.",false);
					}
				   
				}
				
			}
			else//cas ouverture de la séance à partir du menu
			{
	          try {
						String count="0";
						String sql="select * from candidat where cin=? ";
						prepared=cnx.prepareStatement(sql);
						prepared.setString(1, cin);
						resultat=prepared.executeQuery();
						if(resultat.next())//voir si ce candidat existe ou pas
						{
						  if(typ.equals("Conduite"))//Les séances ajoutées à partir du menu ne peut être que des séances conduites.
						   {
							   nbcond=resultat.getInt("nb_conduite");
							   String sql1="select count(*) as count from seance_candidat where cin=? and type_seance=?  ";
							   prepared=cnx.prepareStatement(sql1);
							   prepared.setString(1, cin);
						       prepared.setString(2,"Conduite");
							   resultat=prepared.executeQuery();
							   if(resultat.next())
								{
									 count=resultat.getString("count");
									 if(Integer.parseInt(count)<nbcond)//Nombre des séances ajoutées est inférieur au nombre total des séances conduites.
										{
										 if(verif()==true)//vérifie la validation des champs obligatoires.
										  {
										    if(date_existe()==true)//permet de vérifier si le candidat a déjà une séance a cette date ou non.
											  {
												ajouter();//permet l'ajoute d'une séance.
												n2++;
												cinField.setEditable(false);
												npField.setEditable(false);
												lblNewLabel_11.setVisible(true);
									            moniteurBox.setEnabled(false);
									            vehiculeBox.setEnabled(false);
												if(n2==nbcond)
												{
												    cinField.setEditable(true);
												    npField.setEditable(true);
												    cinField.setText("");
												    npField.setText("");
												   message("les seances de conduite sont toutes ajoutées",true);
												  // dispose();
											     }
										       }
											   else
												   message("Choisir une autre date.",false);
											}
										}
									 if(Integer.parseInt(count)==nbcond && Integer.parseInt(count)!=0 )
										{
											message("Vous avez déja ajouter toutes les seances de conduite de ce candidat.",false);
										}
						           }
							}
							else
							  message("Vous avez déja ajouter toutes les seances de code lors de l'inscription.",false);
						  }
						   else
							  message("Verifier le numéro CIN.",false);
						
			    	    }catch(SQLException e1)
			            {
				         e1.printStackTrace();
			            }
			}
			}
		});
		btnNewButton.setBounds(395, 89, 154, 127);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
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
		ImageIcon edit =new ImageIcon(this.getClass().getResource("/modifs.png"));
		btnNewButton_1.setIcon(edit);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBackground(new Color(173, 216, 230));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setForeground(new Color(0, 0, 128));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifier(v);//permet la modification des données d'une séance.
			}
		});
		btnNewButton_1.setBounds(395, 229, 154, 128);
		panel.add(btnNewButton_1);	
		
		JCalendar calendar = new JCalendar();
		calendar.getDayChooser().getDayPanel().setBackground(new Color(240, 255, 255));
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//Calendrier affiche le planning du jour choisi en cliquant sur le numéro du jour.
				 SimpleDateFormat formater = null;
			    	formater = new SimpleDateFormat("yyyy-MM-dd");
			        String d=formater.format(calendar.getDate());
			        String sql="select * from seance_candidat where date_heure REGEXP ? ";
			        try {
			        	prepared=cnx.prepareStatement(sql);
			        	prepared.setString(1, d+" "+"[0-2][0-9]:[0-5][0-9]");
		 		        resultat=prepared.executeQuery(); 
		 		  		table.setModel(DbUtils.resultSetToTableModel(resultat));
			        }
			       catch(SQLException p)
				    {
				    	p.printStackTrace();
				    }
			}
		});
		calendar.setBounds(604, 91, 371, 253);
		
		panel.add(calendar);
		
		JButton btnNewButton_3 = new JButton("Afficher les seances Moniteur");
		btnNewButton_3.setForeground(new Color(0, 0, 128));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// en cliquant sur ce bouton la methode seanceMoniteur() est appellé.
				seanceMoniteur();//permet l’affichage de tout le planning moniteur.
			}
		});
		btnNewButton_3.setBounds(604, 369, 368, 31);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Afficher les seances Candidat");
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_4.setForeground(new Color(0, 0, 128));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// en cliquant sur ce bouton la methode seanceCandidat() est appellé.
				seanceCandidat();//permet l’affichage de tout le planning candidat.
			}
		});
		btnNewButton_4.setBounds(604, 422, 368, 31);
		panel.add(btnNewButton_4);
	
		
		JLabel lblNewLabel_8 = new JLabel("Gestion des s\u00E9ances");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel_8.setForeground(new Color(51, 102, 204));
		lblNewLabel_8.setBounds(408, 27, 306, 22);
		panel.add(lblNewLabel_8);
		
	    lblNewLabel_9 = new JLabel("");
		ImageIcon on =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_9.setIcon(on);
		lblNewLabel_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_10.setVisible(true);//afficher l'icon du muet.
				lblNewLabel_9.setVisible(false);//cacher l'icon du son.
				etatS="off";//changer l'etat vers off.
			}
		});
		lblNewLabel_9.setBounds(887, 11, 61, 69);
		panel.add(lblNewLabel_9);
		
	    lblNewLabel_10 = new JLabel("");
	    ImageIcon off =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_10.setIcon(off);
	    lblNewLabel_10.setVisible(false);
	    lblNewLabel_10.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		lblNewLabel_10.setVisible(false);//cacher l'icon du muet.
				lblNewLabel_9.setVisible(true);//afficher l'icon du son.
				etatS="on";
	    	}
	    });
		lblNewLabel_10.setBounds(887, 11, 61, 69);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setToolTipText("Reset");
		ImageIcon reset =new ImageIcon(this.getClass().getResource("/reset.png"));
		lblNewLabel_7.setIcon(reset);
		lblNewLabel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//en cliquant sur ce label la methode reinitialiser(v) est appllé.
				 reinitialiser(v);//remettre les champs dans leur état initial.
			}
		});
		lblNewLabel_7.setBounds(303, 71, 46, 49);
		panel.add(lblNewLabel_7);
		
		txtRecherche = new JTextField();
		txtRecherche.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtRecherche.getText().equals("Recherche"))
					txtRecherche.setText("");
				else
					txtRecherche.selectAll();
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtRecherche.getText().equals(""))
					txtRecherche.setText("Recherche");
			}
		});
		txtRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				rechercher();//permet la recherche des séances planifiées du moniteur ou du candidat, soit par le numéro de CIN soit par le nom et prénom.
			}
		});
		txtRecherche.setText("Recherche");
		txtRecherche.setBounds(61, 434, 126, 22);
		panel.add(txtRecherche);
		txtRecherche.setColumns(10);
		
		
		JLabel lblNewLabel_12 = new JLabel("help");
		lblNewLabel_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Help help=new Help("seance");
				help.setVisible(true);
			}
		});
		ImageIcon help =new ImageIcon(this.getClass().getResource("/help.png"));
		lblNewLabel_12.setIcon(help);
		lblNewLabel_12.setBounds(958, 11, 66, 69);
		panel.add(lblNewLabel_12);
		
		cin(v);//permet la conservation du numéro de CIN et le nom et prénom du candidat lors de l'ajoute des séances.

		etat_son(v.get(5).toString());//gère l’état sonore de l’interface.
	}
	
 public boolean remplirsalle()//remplit la liste des salles disponibles
	{ 
		boolean sexiste=false;
		Vector vec = new Vector();
		boolean v1=false;
		String moni=moniteurBox.getSelectedItem().toString();
		int b =moni.lastIndexOf("/");
		String nomM =moni.substring(0,b);
		String cinM=moni.substring(b+1,moni.length());
		String date =((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
		
		String sql="select numero_salle from seance_moniteur  where cin='"+cinM+"' and date_heure='"+date+"'";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
				String nb =resultat.getString("numero_salle").toString();
				salleBox.addItem(nb);
				sexiste=true;
			}
			else
			{  
				String sql1 ="select * from ecole ";
					prepared=cnx.prepareStatement(sql1);
			        resultat=prepared.executeQuery();
			        if(resultat.next())
			        {
			        	String nb =resultat.getString("nb_salles").toString();
			        	int x =Integer.parseInt(nb);
			        	while(x>0)
			        	{
			        	  if(date_salle(x,date)==true)
			        		   sexiste=true;
			        	  x--;
			        	}
			        }
       
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return sexiste;//return false si il n y a aucune salle disponible
	  }
 public boolean remplirVehicule()//remplit la liste des véhicules disponibles
	{  
		boolean vexiste=false;
		Vector v =new Vector();
		String date =((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
			String moni=moniteurBox.getSelectedItem().toString();
			int b =moni.lastIndexOf("/");
			String nomM =moni.substring(0,b);
			String cinM=moni.substring(b+1,moni.length());
			
			String sql="select categorie_permis from moniteur  where cin='"+cinM+"'";
			try {
				prepared=cnx.prepareStatement(sql);
			    resultat=prepared.executeQuery();
			    if(resultat.next())
			    {
			    	String cat =resultat.getString("categorie_permis").toString();
			    	
			    	String sql1="select * from vehicule v where  etat='Bon etat' and type=? and not exists(select * from frais_vehicule f where date_paiement=? and f.matricule=v.matricule)";
				    	prepared=cnx.prepareStatement(sql1);
				    	prepared.setString(1, cat);
					    prepared.setString(2, date);
					    resultat=prepared.executeQuery();
					    while(resultat.next())
					    {   
					    	v.addElement(resultat.getString("marque")+"/"+resultat.getString("matricule"));
				        }
					    for(int i=0;i<v.size();i++)
		        		{
				        	String vehi=v.get(i).toString();
				            if(date_vehicule(vehi,date)==true)
				            	 vexiste=true;
		        		}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vexiste;//retourne false si il n y a aucune vehicule disponible
		}
 public boolean remplirMoniteur()//Cette fonction remplie la liste des moniteurs disponibles
		{  int count=0;
		   boolean mexiste=false;
		   Vector vec =new Vector();
		   cin=Integer.parseInt(cinField.getText());
			dateS =((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
			int b=dateS.indexOf(" ");
		    String d=dateS.substring(0,b);
			String sql="select nom_prenom,type_inscription,categorie_permis from candidat where cin=?";
			try {
				prepared=cnx.prepareStatement(sql);
				prepared.setLong(1, cin);
				resultat=prepared.executeQuery();
				while(resultat.next())
				{
					String cat=resultat.getString("categorie_permis");
					String type=typeBox.getSelectedItem().toString();
			        if(type.equals("Code"))
	                { 
			        	String req="select * from moniteur m where specialite=? and categorie_permis=? and (max_seances>(select count(*) as n from seance_moniteur p where p.cin=m.cin and date_heure REGEXP ?)) and (exists (select * from seance_moniteur p where m.cin=p.cin and date_heure=? and nb_eleves<(select capacite_salle from ecole)) or (not exists (select * from seance_moniteur p where m.cin=p.cin and date_heure=?)))";
				        prepared=cnx.prepareStatement(req);
				        prepared.setString(1, type);
				        prepared.setString(2,cat);
				        prepared.setString(3, d+" "+"[0-2][0-9]:[0-5][0-9]");
				        prepared.setString(4, dateS);
				        prepared.setString(5, dateS);
				        resultat=prepared.executeQuery();
				        while(resultat.next())
				        {
				    	    String cin1=resultat.getString("cin");
				    	    String np1=resultat.getString("nom_prenom");
				    	    vec.addElement(np1+"/"+cin1); 
				        }
				        for(int i=0;i<vec.size();i++)
		        		{
				        	String v=vec.get(i).toString();
		
				        	int c  =v.lastIndexOf("/");
							String nomM =v.substring(0,c);
							String cinM=v.substring(c+1,v.length());
						
							if(date_code(cinM,nomM,dateS)==true)
								mexiste=true;
		        		} 
				   }
			        
			       if(type.equals("Conduite"))
			        {    
			        String req="select * from moniteur m where specialite=? and categorie_permis=? and (max_seances>(select count(*) as n from seance_moniteur s where s.cin=m.cin and date_heure REGEXP ?)) and not exists (select cin from seance_moniteur s where m.cin=s.cin and date_heure=?)";
			        	    prepared=cnx.prepareStatement(req);
			        	    prepared.setString(1, type);
					        prepared.setString(2, cat);
					        prepared.setString(3, d+" "+"[0-2][0-9]:[0-5][0-9]");
					        prepared.setString(4, dateS);
					        resultat=prepared.executeQuery();
					        while(resultat.next())
					        {  
					    	    String cin1=resultat.getString("cin");
					    	    String np1=resultat.getString("nom_prenom");
					    	    vec.addElement(np1+"/"+cin1);
		  	
					        } 
					        for(int i=0;i<vec.size();i++)
			        		{
					        	String v=vec.get(i).toString();
			
					        	int c  =v.lastIndexOf("/");
								String nomM =v.substring(0,c);
								String cinM=v.substring(c+1,v.length());
							
								if(date_conduite(cinM,nomM,dateS)==true)
									mexiste=true;
			        		}
			        }
				}
			}
			catch(SQLException p) {
				p.printStackTrace();
			}
			return mexiste ;
		}
 public void actualiserMoniteur(String cinM,String date,String nb)//mettre à jour le nombre des élèves dans une séance de code d'un moniteur.
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
 public void insertionCandidat(long cin,String np,String type,String dateS,String moni,String veh,String nsalle)//permet l'ajout d'une séance dans le planning candidat.
		{
			String sql2="insert into seance_candidat(cin,nom_prenom,type_seance,date_heure,moniteur,vehicule,numero_salle) values(?,?,?,?,?,?,?)";
			try {
				
				 prepared=cnx.prepareStatement(sql2);
				 prepared.setLong(1, cin);
				 prepared.setString(2, np);
				 prepared.setString(3, type);
				 prepared.setString(4, dateS);
				 prepared.setString(5, moni);
				 prepared.setString(6, veh);
				 prepared.setString(7, nsalle);
				 prepared.execute();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
 public void insertionMoniteur(String cinM,String nomM,String dateS,String specialite,String veh,String nb,String numsalle) {//permet l'ajout d'une séance dans le planning moniteur.

		String sql="insert into seance_moniteur(cin,nom_prenom,date_heure,specialite,nb_eleves,vehicule,numero_salle) values (?,?,?,?,?,?,?)";
			try {
				 prepared=cnx.prepareStatement(sql);
				 prepared.setString(1, cinM);
				 prepared.setString(2, nomM);
				 prepared.setString(3, dateS);
				 prepared.setString(4, specialite);
				 prepared.setString(5, nb);
				 prepared.setString(6, veh);
				 prepared.setString(7, numsalle);//pas besoin de salle
				 prepared.execute(); 
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
 public void ajouter() {//permet l'ajout d'une séance.
		int nbs=0;
			 cin =Integer.parseInt(cinField.getText());
			 nom_prenom =npField.getText().toString();
			 type =typeBox.getSelectedItem().toString();
			 dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
			moniteur=moniteurBox.getSelectedItem().toString();
			vehicule=vehiculeBox.getSelectedItem().toString();
			nsalle=salleBox.getSelectedItem().toString();
			int b  =moniteur.lastIndexOf("/");
			String nomM =moniteur.substring(0,b);
			String cinM=moniteur.substring(b+1,moniteur.length());
			
      
    	 if(verif()==true)
    	  {
			if(type.equals("Conduite"))
			{
			  insertionCandidat(cin,nom_prenom,type,dateS,moniteur,vehicule,"--");	
			  insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1","--");
			    moniteurBox.setSelectedItem("--");
				salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				vehiculeBox.setSelectedItem("--");
				salleBox.setSelectedItem("");
				((JTextField) dateChooser.getDateEditor().getUiComponent()).setText("");
			}
			if(type.equals("Code"))
			{
				boolean m=false;
			 try {
			    String sql1="select * from seance_moniteur where cin='"+cinM+"'and date_heure='"+dateS+"'";
			    //si le moniteur deja existe dans le planning meme heure
				prepared=cnx.prepareStatement(sql1);
				resultat=prepared.executeQuery();
				if(resultat.next())
				{
				  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
			      nb++;
			      String nbeleve=String.valueOf(nb);
			      actualiserMoniteur(cinM,dateS,nbeleve);
			      insertionCandidat(cin,nom_prenom,type,dateS,moniteur,"--",nsalle);
			      m=true;
			    }
			    String sql2="select * from seance_moniteur where cin='"+cinM+"' and date_heure<>'"+dateS+"'";
			    //le moniteur existe dans le planning et il n'a pas une seance a cette date
			    prepared=cnx.prepareStatement(sql2);
				resultat=prepared.executeQuery();
				if(resultat.next() && (m==false))
				{
			      insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1",nsalle);
		          insertionCandidat(cin,nom_prenom,type,dateS,moniteur,"--",nsalle);
		          m=true;
		        }
				
				else
				{
					insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1",nsalle);
			        insertionCandidat(cin,nom_prenom,type,dateS,moniteur,"--",nsalle);
			        m=true;
				}
	
				moniteurBox.setSelectedItem("--");
				salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
				vehiculeBox.setSelectedItem("--");
				salleBox.setSelectedItem("");
				((JTextField) dateChooser.getDateEditor().getUiComponent()).setText("");
				
			 }catch(SQLException e) {
				 e.printStackTrace();
			}
			}
			 afficheSeance();
         }
}
 public void actualiserCandidat(long cin,String np ,String date1,String date2,String typ,String moni,String veh,String nb)//permet l'ajout d'une séance dans le planning candidat.
{
	
	String sql="update seance_candidat set nom_prenom='"+np+"',type_seance='"+typ+"',date_heure='"+date2+"',moniteur='"+moni+"',vehicule='"+veh+"' ,numero_salle='"+nb+"' where cin='"+cin+"' and date_heure='"+date1+"'";
	try {
		prepared=cnx.prepareStatement(sql);
	    prepared.execute();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
 public void supprimerMoniteur(String cin,String date) {//permet la suppression d’une séance à une date spécifique du planning moniteur.
	
	int nbs=0;
	String sql="delete from seance_moniteur where cin='"+cin+"' and date_heure='"+date+"'";
	try {
		prepared=cnx.prepareStatement(sql);
		prepared.execute();
	    System.out.print("sup");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
 public void modifier(Vector v) {//permet la modification des données d'une séance.
if (vec.size()==0)
{
	message("Vous devez selectionner la seance à modifier apartir du table.",false);
}
else
{
	int nbs=0;
	long cin1=Integer.parseInt(vec.get(0).toString());
	String np1 =vec.get(1).toString();
	String date1 =vec.get(2).toString();
	String typ1=vec.get(3).toString();
	String moni1=vec.get(4).toString();
	//String veh1=vec.get(5).toString();
	//String nsalle1=vec.get(6).toString();
	int a =moni1.lastIndexOf("/");
	String cinM1=moni1.substring(a+1,moni1.length());

if(verif()==true) {
	if((cin1==cin) &&( np1.equals(nom_prenom)))
	{
	 cin =Integer.parseInt(cinField.getText());
	 nom_prenom =npField.getText().toString();
	 type =typeBox.getSelectedItem().toString();
	 dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	 moniteur=moniteurBox.getSelectedItem().toString();
	vehicule=vehiculeBox.getSelectedItem().toString();
	nsalle=salleBox.getSelectedItem().toString();
	int b  =moniteur.lastIndexOf("/");
	String nomM =moniteur.substring(0,b);
	String cinM=moniteur.substring(b+1,moniteur.length());
	
	actualiserCandidat(cin,nom_prenom,date1,dateS,type,moniteur,vehicule,nsalle);
	
	 if(typ1.equals("Conduite"))
	 {
		supprimerMoniteur(cinM1,date1);
		
	 }
	if(typ1.equals("Code"))	
	{
		String sql="select nb_eleves from seance_moniteur where cin='"+cinM1+"'and date_heure='"+date1+"'";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
			  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
			   if(nb>1)
			   {
				   nb--;
				   String nbeleve=String.valueOf(nb);
				   actualiserMoniteur(cinM1,date1,nbeleve);
				   
			   }
			   else
			   {
				   supprimerMoniteur(cinM1,date1);
				     
			   }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	if(type.equals("Conduite"))
	{
	  insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1","--");
	}
	if(type.equals("Code"))
	{
		//insertion du moniteur
		boolean m=false;
		 try {
		    String sql1="select * from seance_moniteur where cin='"+cinM+"'and date_heure='"+dateS+"'";
		    //si le moniteur deja existe dans le planning meme heure
			prepared=cnx.prepareStatement(sql1);
			resultat=prepared.executeQuery();
			if(resultat.next())
			{
			  int nb =Integer.parseInt(resultat.getString("nb_eleves"));
		      nb++;
		      String nbeleve=String.valueOf(nb);
		      actualiserMoniteur(cinM,dateS,nbeleve);
		     
		      m=true;
		    }
		    String sql2="select * from seance_moniteur where cin='"+cinM+"' and date_heure<>'"+dateS+"'";
		    //le moniteur existe dans le planning et il n'a pas une seance a cette date
		    prepared=cnx.prepareStatement(sql2);
		    resultat=prepared.executeQuery();
			if(resultat.next() && (m==false))
			{
			 System.out.print("inserer");
		     insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1",nsalle);
	         
	          m=true;
	        }
		    String sql= "select * from seance_moniteur p where p.cin<>'"+cinM+"' ";
		   //moniteur n'est pas dans le planning
		    prepared=cnx.prepareStatement(sql);
		    resultat=prepared.executeQuery();
			if(resultat.next() && (m==false))
			{
				insertionMoniteur(cinM,nomM,dateS,type,vehicule,"1",nsalle);
		        m=true;
			}
			
			
		 }catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	  afficheSeance();
	  reinitialiser(v);
   }
	else
	  message("Vous ne pouvez pas modifier cette seance car vous avez changer les données du candidat",false);
  }
}
}	
 public void seanceMoniteur()//permet l’affichage de tout le planning moniteur.
{
	
	try {
		String sql = " select *from seance_moniteur ";
		prepared =cnx.prepareStatement(sql);
		resultat = prepared.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(resultat));
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
 public void seanceCandidat()//permet l’affichage de tout le planning candidat.
{
	
	try {
		String sql = " select *from seance_candidat ";
		prepared =cnx.prepareStatement(sql);
		resultat = prepared.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(resultat));
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
 public void afficheSeance()//permet l’affichage de toute les séances du candidat à travers son numéro de CIN.
{
	 cin =Integer.parseInt(cinField.getText());
	try {
		String sql = " select * from seance_candidat where cin='"+cin+"' ";
		prepared =cnx.prepareStatement(sql);
		resultat = prepared.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(resultat));
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
 public boolean date_existe() {//permet de vérifier si le candidat a déjà une séance a cette date ou non.
	 cin= Integer.parseInt(cinField.getText());
     dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	
	 String hpro="00";
	 String hdern="00";
	 String mpro="00";
	 String mdern="00";
	  int a=dateS.lastIndexOf(":");
	  int b=dateS.indexOf(" ");
	  String datej=dateS.substring(0,b);
	  String datem=dateS.substring(a+1,dateS.length());
	   int m=Integer.parseInt(datem);
	  String h=dateS.substring(b+1,a);
	 //int ho=Integer.parseInt(h);
	
	 if(m==59)
	 {
		 mpro=new DecimalFormat("00").format(59-1);
		 hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern="00";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	     
	 }
	 if(m==00)
	 {
		 mpro="59";
	     hpro=h;
	     mdern="01";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	}
	else
	 {
		 mpro=new DecimalFormat("00").format(Integer.parseInt(datem)-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern=new DecimalFormat("00").format(Integer.parseInt(datem)+1);
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	 }
	 String datepro=datej+" "+hpro+":"+mpro;
	 String datedern=datej+" "+hdern+":"+mdern;
	    String sql="select * from seance_candidat where cin='"+cin+"' and date_heure between '"+dateS+"' and '"+datepro+"' ";
		try {
			prepared=cnx.prepareStatement(sql);
		    resultat=prepared.executeQuery(); 
		    if(resultat.next())
		    { 
		    	return false;
		    }
		 String sql2="select * from seance_candidat where cin='"+cin+"' and date_heure between '"+datedern+"' and'"+dateS+"' ";   
		  prepared=cnx.prepareStatement(sql2);
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
public boolean date_conduite(String cin,String np,String date) {
	boolean v=false;
	 String hpro="00";
	 String hdern="00";
	 String mpro="00";
	 String mdern="00";
	  int a=date.lastIndexOf(":");
	  int b=date.indexOf(" ");
	  String datej=date.substring(0,b);
	  String datem=date.substring(a+1,date.length());
	   int m=Integer.parseInt(datem);
	  String h=date.substring(b+1,a);
	
	if(m==59)
	 {
		 mpro=new DecimalFormat("00").format(59-1);
		 hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern="00";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	     v=true;
	 }
	 if(m==00)
	 {
		 mpro="59";
	     hpro=h;
	     mdern="01";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		 v=true;
	 }
	if(v==false)
	 {
		 mpro=new DecimalFormat("00").format(Integer.parseInt(datem)-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern =new DecimalFormat("00").format(Integer.parseInt(datem)+1);
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		
			
	 }
	 String datepro=datej+" "+hpro+":"+mpro;
	 String datedern=datej+" "+hdern+":"+mdern;
	    String sql="select * from seance_moniteur where cin='"+cin+"' and date_heure between '"+date+"' and '"+datepro+"' ";
		try {
			prepared=cnx.prepareStatement(sql);
		    resultat=prepared.executeQuery(); 
		    if(resultat.next())
		    { 
		    	return false ;
		    }
		     String sql2="select * from seance_moniteur where cin='"+cin+"' and date_heure between '"+datedern+"' and'"+date+"' ";   
		     prepared=cnx.prepareStatement(sql2);
		     resultat=prepared.executeQuery(); 
		     if(resultat.next())
		     { 
			   return false;
		     }
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moniteurBox.addItem(np+"/"+cin);
		
	    return true;
	}
public boolean date_code(String cin,String np,String date) {
	boolean v=false;
	 String hpro="00";
	 String hdern="00";
	 String mpro="00";
	 String mdern="00";
	  int a=date.lastIndexOf(":");
	  int b=date.indexOf(" ");
	  String datej=date.substring(0,b);
	  String datem=date.substring(a+1,date.length());
	   int m=Integer.parseInt(datem);
	  String h=date.substring(b+1,a);
	
	if(m==59)
	 {
		 mpro=new DecimalFormat("00").format(59-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern="00";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	     v=true;
	 }
	 if(m==00)
	 {
		 mpro="59";
	     hpro=h;
	     mdern="01";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		 v=true;
	 }
	if(v==false)
	 {
		 mpro=new DecimalFormat("00").format(Integer.parseInt(datem)-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern =new DecimalFormat("00").format(Integer.parseInt(datem)+1);
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		
			
	 }
	 String datepro=datej+" "+hpro+":"+mpro;
	 String datedern=datej+" "+hdern+":"+mdern;
	 String sql="select * from seance_moniteur where cin='"+cin+"' and date_heure='"+date+"' ";
	 try {
	     prepared=cnx.prepareStatement(sql);
	     resultat=prepared.executeQuery(); 
	     if(resultat.next())
	     { 
	    	 moniteurBox.addItem(np+"/"+cin);
	    	return true ;
	     }
	     String sql1="select * from seance_moniteur where cin='"+cin+"' and date_heure between '"+date+"' and '"+datepro+"' ";
	
		prepared=cnx.prepareStatement(sql1);
	    resultat=prepared.executeQuery(); 
	    if(resultat.next())
	    { 
	    	return false ;
	    }
	     String sql2="select * from seance_moniteur where cin='"+cin+"' and date_heure between '"+datedern+"' and'"+date+"' ";   
	     prepared=cnx.prepareStatement(sql2);
	     resultat=prepared.executeQuery(); 
	     if(resultat.next())
	     { 
		   return false;
	     }
	    
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	moniteurBox.addItem(np+"/"+cin);
    return true;
	}
 
public boolean date_salle(int salle ,String date) {
 
	boolean v=false;
	 String hpro="00";
	 String hdern="00";
	 String mpro="00";
	 String mdern="00";
	  int a=date.lastIndexOf(":");
	  int b=date.indexOf(" ");
	  String datej=date.substring(0,b);
	  String datem=date.substring(a+1,date.length());
	   int m=Integer.parseInt(datem);
	  String h=date.substring(b+1,a);
	
	if(m==59)
	 {
		 mpro=new DecimalFormat("00").format(59-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern="00";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	     v=true;
	 }
	 if(m==00)
	 {
		 mpro="59";
	     hpro=h;
	     mdern="01";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		 v=true;
	 }
	if(v==false)
	 {
		 mpro=new DecimalFormat("00").format(Integer.parseInt(datem)-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern =new DecimalFormat("00").format(Integer.parseInt(datem)+1);
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		
			
	 }
	 String datepro=datej+" "+hpro+":"+mpro;
	 String datedern=datej+" "+hdern+":"+mdern;
	 
	 String sql1="select * from seance_moniteur where numero_salle='"+salle+"' and date_heure between '"+date+"' and '"+datepro+"' ";
		
	 try {
		prepared=cnx.prepareStatement(sql1);
	    resultat=prepared.executeQuery(); 
	    if(resultat.next())
	    { 
	    	return false ;
	    }
	     String sql2="select * from seance_moniteur where numero_salle='"+salle+"' and date_heure between '"+datedern+"' and'"+date+"' ";   
	     prepared=cnx.prepareStatement(sql2);
	     resultat=prepared.executeQuery(); 
	     if(resultat.next())
	     { 
		   return false;
	     }
	    
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	salleBox.addItem(salle);

    return true;

 }
public boolean date_vehicule(String vehicule ,String date) {
	boolean v=false;
	 String hpro="00";
	 String hdern="00";
	 String mpro="00";
	 String mdern="00";
	  int a=date.lastIndexOf(":");
	  int b=date.indexOf(" ");
	  String datej=date.substring(0,b);
	  String datem=date.substring(a+1,date.length());
	   int m=Integer.parseInt(datem);
	  String h=date.substring(b+1,a);
	
	if(m==59)
	 {
		 mpro=new DecimalFormat("00").format(59-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern="00";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
	     v=true;
	 }
	 if(m==00)
	 {
		 mpro="59";
	     hpro=h;
	     mdern="01";
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		 v=true;
	 }
	if(v==false)
	 {
		 mpro=new DecimalFormat("00").format(Integer.parseInt(datem)-1);
	     hpro=new DecimalFormat("00").format(Integer.parseInt(h)+1);
	     mdern =new DecimalFormat("00").format(Integer.parseInt(datem)+1);
		 hdern=new DecimalFormat("00").format(Integer.parseInt(h)-1);
		
			
	 }
	 String datepro=datej+" "+hpro+":"+mpro;
	 String datedern=datej+" "+hdern+":"+mdern;
	 
	 String sql1="select * from seance_moniteur where vehicule='"+vehicule+"' and date_heure between '"+date+"' and '"+datepro+"' ";
		
	 try {
		prepared=cnx.prepareStatement(sql1);
	    resultat=prepared.executeQuery(); 
	    if(resultat.next())
	    { 
	    	return false ;
	    }
	     String sql2="select * from seance_moniteur where vehicule='"+vehicule+"' and date_heure between '"+datedern+"' and'"+date+"' ";   
	     prepared=cnx.prepareStatement(sql2);
	     resultat=prepared.executeQuery(); 
	     if(resultat.next())
	     { 
		   return false;
	     }
	    
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	vehiculeBox.addItem(vehicule);
    return true;
 }
public void message(String ch,boolean etat) //affiche un message sonore .
{
	  	
	if(lblNewLabel_9.isShowing())
	{
		Audio t = new Audio("audio",etat);
		t.set_sound("on",etat);
	    t.start();
	    Audio t1 = new Audio("message",ch,etat);
	    t1.start();
	}
	else if(lblNewLabel_10.isShowing())
	{
		Audio t = new Audio("audio",etat);
		t.set_sound("off",etat);
		t.start();
	    Audio t2 = new Audio("message",ch,etat);
	    t2.start();
	}
		
}
public void etat_son(String etat)// gère l’état sonore de l’interface.
{
	if(etat=="on")
	{
		lblNewLabel_9.setVisible(true);
		lblNewLabel_10.setVisible(false);
	}
	else if(etat=="off")
	{
		lblNewLabel_10.setVisible(true);
		lblNewLabel_9.setVisible(false);
	}
}
public boolean verif()//vérifie la validation des champs obligatoires.
{
	 cin =Integer.parseInt(cinField.getText());
	 nom_prenom =npField.getText().toString();
	 type =typeBox.getSelectedItem().toString();
	 dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	 moniteur=moniteurBox.getSelectedItem().toString();
	 vehicule=vehiculeBox.getSelectedItem().toString();
	 nsalle=salleBox.getSelectedItem().toString();
	 if((String.valueOf(cin).matches("[0-9]+")) && (!String.valueOf(cin).equals("")) && (String.valueOf(cin).length()==8))
		{
	 if(nom_prenom.matches("[a-zA-Z][a-z A-Z]*"))
		{
		 if(!dateS.equals(""))
			{
			 if(type=="Conduite")
			 {
				 if(moniteur.length()!=0 && !moniteur.equals("--"))
				 {
					 if((vehicule.length()!=0) && (!vehicule.equals("--")))
						 return true;
					 else
						 message("Verifier la vehicule",false);
				 }
				 else
					 message("Verifier le moniteur",false);
			 }
			 if(type=="Code")
			 {
				 if((!moniteur.equals("--")) && (moniteur.length()!=0))
				 {
					 if(nsalle.length()!=0 && (!nsalle.equals("--")))
						 return true;
					 else
						 message("Verifier la salle",false);
				 }
				 else
					 message("Verifier le moniteur",false);
			 }
			}
		 else 
		      message("Verifier la date",false);
		}
	  else 
	      message("Verifier le nom et le prenom",false);
	 
	}
	else 
		message("Verifier la cin",false);	
	
	return false;
}
public void rechercher()//permet la recherche des séances planifiées du moniteur ou du candidat, soit par le numéro de CIN soit par le nom et prénom.

{
	recherche=txtRecherche.getText().toString();
	 boolean test=false;
	 if(recherche.matches("[0-9]+"))
	 {
		    String sql="select * from seance_candidat where cin=?";
	 		try {
	 			prepared=cnx.prepareStatement(sql);
	 			prepared.setString(1,recherche);
	 			resultat=prepared.executeQuery();
	 			if(resultat.next())
	 			{
	 				test=true;
	 			}
	 			if(test==true)
	 			{
	 				String sql1="select * from seance_candidat where cin=?";
	    	 		
	    	 		prepared=cnx.prepareStatement(sql1);
	    	 		prepared.setString(1,recherche);
	    	 		resultat=prepared.executeQuery();
	    	 		table.setModel(DbUtils.resultSetToTableModel(resultat));
	 			}
	 			else
	 			{
	 				String sql1="select * from seance_moniteur where cin=?";
   	 			prepared=cnx.prepareStatement(sql1);
   	 			prepared.setString(1,recherche);
   	 			resultat=prepared.executeQuery();
   	 			table.setModel(DbUtils.resultSetToTableModel(resultat));
	 			}
	 			
	 			 			
	 		}
	 		catch(SQLException p)
	 		{
	 			p.printStackTrace();
	 		}
	 }
	 else 
	 {
		 String sql="select * from seance_candidat where nom_prenom=?";
 		try {
 			prepared=cnx.prepareStatement(sql);
 			prepared.setString(1,recherche);
 			resultat=prepared.executeQuery();
 			if(resultat.next())
 			{
 				test=true;
 			}
 			if(test==true)
 			{
 				String sql1="select * from seance_candidat where nom_prenom=?";
    	 		
    	 		prepared=cnx.prepareStatement(sql1);
    	 		prepared.setString(1,recherche);
    	 		resultat=prepared.executeQuery();
    	 		table.setModel(DbUtils.resultSetToTableModel(resultat));
 			}
 			else
 			{
 				String sql1="select * from seance_moniteur where nom_prenom=?";
	 			prepared=cnx.prepareStatement(sql1);
	 			prepared.setString(1,recherche);
	 			resultat=prepared.executeQuery();
	 			table.setModel(DbUtils.resultSetToTableModel(resultat));
 			}
 			
 			 			
 		}
 		catch(SQLException p)
 		{
 			p.printStackTrace();
 		}
	 }
	
}
public void date_change() {//permet de connaître si la date de la séance a changé ou pas.
	dateS=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	
	if(!dat.equals(dateS))
	{
		dat=dateS;
		moniteurBox.setEnabled(false);
		vehiculeBox.setEnabled(false);
        salleBox.setEnabled(false);
        if(!moniteurBox.getSelectedItem().toString().equals("--") && !lblNewLabel_11.isShowing())
        {
        	moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
	        salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
	        vehiculeBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
        }
        if(!lblNewLabel_11.isShowing())
	          lblNewLabel_11.setVisible(true);
	}
}
public void recuperer_champs() {//permet la récupération des informations du candidat lors de la saisie du numéro de CIN dans le champ.
	String sql="select nom_prenom from candidat where cin=?";
	try {
		prepared=cnx.prepareStatement(sql);
		prepared.setString(1,cinField.getText().toString() );
		resultat=prepared.executeQuery();
		if(resultat.next())
		{
			String np=resultat.getString("nom_prenom");
			npField.setText(np);
			afficheSeance();
			
		}
		else
		   if(cinField.getText().toString().length()==8)
		   {
			  message("Ce candidat n'existe pas.",false);
		      cinField.setText("");
		   }
	 }
		catch (Exception p) {
			p.printStackTrace();
		}
}
public void recuperer_champs_du_table(Vector v) {//permet la récupération des informations du candidat choisi à partir du table.
	
	 int ligne =table.getSelectedRow();
	 cin= cin=Integer.parseInt(table.getModel().getValueAt(ligne, 0).toString());
	 if(v.get(0).toString().equals("") || v.get(0).toString().equals(cin))
	{
		 
	    nom_prenom=table.getModel().getValueAt(ligne, 1).toString();
        type =table.getModel().getValueAt(ligne, 2).toString();
	    moniteur=table.getModel().getValueAt(ligne, 4).toString();
	    vehicule=table.getModel().getValueAt(ligne, 5).toString();
	   nsalle=table.getModel().getValueAt(ligne, 6).toString();
	    cinField.setText(String.valueOf(cin));
        npField.setText(nom_prenom);
        typeBox.setSelectedItem(type);
        moniteurBox.addItem(moniteur);
        
		moniteurBox.setSelectedItem(moniteur);
		vehiculeBox.addItem(vehicule);
		vehiculeBox.setSelectedItem(vehicule);
        salleBox.addItem(nsalle);
		salleBox.setSelectedItem(nsalle);

	 vec.add(cin);
	 vec.add(nom_prenom);
	   
	try {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) table.getModel().getValueAt(ligne, 3).toString());
		dateChooser.setDate(date);
		vec.add(dateFormat.format(date));
	} catch (ParseException ex) {
		Logger.getLogger(Seance.class.getName()).log(Level.SEVERE,null,ex);
	}
	   vec.add(type);
	   vec.add(moniteur);
	   //vec.add(vehicule);
	   //vec.add(nsalle);
	}
}
public void cin( Vector v) {//permet la conservation du numéro de CIN et le nom et prénom du candidat lors de l'ajoute des séances.
	if(!v.get(0).toString().equals(""))
	{
		cinField.setEditable(false);
		npField.setEditable(false);
	}
}
public void  reinitialiser(Vector v){//remettre les champs dans leur état initial.
	if(v.get(0).toString().equals(""))
	{
	cinField.setText("");
	npField.setText("");
	}
	moniteurBox.setSelectedItem("--");
	salleBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
	moniteurBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
	vehiculeBox.setModel(new DefaultComboBoxModel(new String[] {"--"}));
	
	((JTextField) dateChooser.getDateEditor().getUiComponent()).setText("");
	
	 vec.clear();//modifier
	
	moniteurBox.setEnabled(false);
    lblNewLabel_11.setVisible(true);
    salleBox.setEnabled(false);
    vehiculeBox.setEnabled(false);
}
}