import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.util.Vector;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class Help extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help frame = new Help("examen");
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
	public Help(String nom) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon.jpg")));
		setTitle("Aide");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 910, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 1400, 512);
		panel.add(lblNewLabel);
		if(nom.equals("moniteur"))//afficher la page d'aide de la page du moniteur
		{
			ImageIcon moniteur =new ImageIcon(this.getClass().getResource("/hm.png"));
			lblNewLabel.setIcon(moniteur);
        }
		if(nom.equals("examen"))//afficher la page d'aide de la page d'examen
		{
			ImageIcon examen =new ImageIcon(this.getClass().getResource("/he.png"));
			lblNewLabel.setIcon(examen);
      	}
		if(nom.equals("seance"))//afficher la page d'aide de la page de seance
		{
			ImageIcon seance =new ImageIcon(this.getClass().getResource("/hs.png"));
			lblNewLabel.setIcon(seance);
      	}
		if(nom.equals("candidat"))//afficher la page d'aide de la page du candidat
		{
			ImageIcon candidat =new ImageIcon(this.getClass().getResource("/hc.png"));
			lblNewLabel.setIcon(candidat);
      	}
		if(nom.equals("vehicule"))//afficher la page d'aide de la page de vehicule
		{
			ImageIcon candidat =new ImageIcon(this.getClass().getResource("/hv.png"));
			lblNewLabel.setIcon(candidat);
      	}
	}
}
