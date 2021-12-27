import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel_5;
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
					Menu frame = new Menu("off");
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
	public Menu(String s) {
		Image icon =new ImageIcon(this.getClass().getResource("/icon.jpg")).getImage();
		setIconImage(icon);
		setTitle("Menu");
		etat=s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		ImageIcon car_icon =new ImageIcon(this.getClass().getResource("/car.png"));
		lblNewLabel.setIcon(car_icon);
		lblNewLabel.setBounds(485, 74, 238, 352);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Gestion des candidats"); //En cliquant sur ce bouton, la page de gestion des candidats s'ouvre
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Candidat cand = new Candidat(etat);
				cand.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBackground(new Color(102, 204, 204));
		btnNewButton.setBounds(75, 30, 263, 45);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gestion des moniteurs");//En cliquant sur ce bouton, la page de gestion des moniteurs s'ouvre
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Moniteur mon= new Moniteur(etat);
				mon.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1.setBackground(new Color(102, 153, 204));
		btnNewButton_1.setBounds(75, 100, 263, 45);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Gestion des v\u00E9hicules");//En cliquant sur ce bouton, la page de gestion des véhicules s'ouvre
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vehicule veh=new Vehicule(etat);
				veh.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_2.setBackground(new Color(102, 102, 204));
		btnNewButton_2.setBounds(75, 170, 263, 45);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Gestion des s\u00E9ances"); //En cliquant sur ce bouton, la page de gestion des séances s'ouvre
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector v=new Vector();
				v.add("");
				v.add("");
				v.add("");
				v.add("");
				v.add("");
				v.add(false);
				v.add(etat);
				Seance seance=new Seance(v);
				seance.setVisible(true);
			}
		});
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_3.setBackground(new Color(153, 102, 204));
		btnNewButton_3.setBounds(75, 240, 263, 45);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Gestion des examens");//En cliquant sur ce bouton, la page de gestion des examens s'ouvre
		btnNewButton_4.setBackground(new Color(102, 0, 102));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Examen exam=new Examen(etat);
				exam.setVisible(true);
				dispose();
			}
		});
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_4.setBounds(75, 311, 263, 45);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Bilan financier"); //En cliquant sur ce bouton, la page de gestion du bilan s'ouvre
		btnNewButton_5.setBounds(75, 382, 263, 45);
		btnNewButton_5.setBackground(new Color(153, 0, 102));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bilan bilan=new Bilan(etat);
				bilan.setVisible(true);
				dispose();
			}
		});
		btnNewButton_5.setForeground(Color.WHITE);
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(btnNewButton_5);
		
		JLabel lblNewLabel_2 = new JLabel("");//En cliquant sur ce bouton, la page contenant les informations sur l'école s'ouvre
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Ecole ecole=new Ecole(etat);
				ecole.setVisible(true);
				dispose();
			}
		});
		ImageIcon home_icon =new ImageIcon(this.getClass().getResource("/home.png"));
		lblNewLabel_2.setIcon(home_icon);
		lblNewLabel_2.setBounds(760, 0, 64, 63);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		ImageIcon circle_icon =new ImageIcon(this.getClass().getResource("/circle.gif"));
		lblNewLabel_3.setIcon(circle_icon);
		lblNewLabel_3.setBounds(383, 59, 441, 367);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("on");//désactiver le son en cliquant sur ce bouton
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_4.setVisible(false);
				lblNewLabel_5.setVisible(true);
				etat="off";
			}
		});
		ImageIcon on_icon =new ImageIcon(this.getClass().getResource("/on.png"));
		lblNewLabel_4.setIcon(on_icon);
		lblNewLabel_4.setBounds(700, 0, 64, 63);
		panel.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("off");//activer le son en cliquant sur ce bouton
		lblNewLabel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_5.setVisible(false);
				lblNewLabel_4.setVisible(true);
				etat="on";
			}
		});
		ImageIcon off_icon =new ImageIcon(this.getClass().getResource("/off.png"));
		lblNewLabel_5.setIcon(off_icon);
		lblNewLabel_5.setBounds(700, 0, 64, 63);
		panel.add(lblNewLabel_5);
		etat_son(); //configurer l'état sonore de l'interafce à l'ouverture de la page
	}
	 public void etat_son()//configurer l'état sonore de l'interafce à l'ouverture de la page
		{
		    if(etat=="on")
			{
				lblNewLabel_4.setVisible(true);
				lblNewLabel_5.setVisible(false);
			}
			if(etat=="off")
			{
				lblNewLabel_5.setVisible(true);
				lblNewLabel_4.setVisible(false);
			}
		}
}
