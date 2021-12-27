import java.io.FileInputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import javazoom.jl.player.Player;

public class Audio extends Thread{
	private String type;
	private String message;
	private boolean type_sound;
	private String sound="source/v.wav";
	  public Audio(String t, String m, boolean ty) {
		// TODO Auto-generated constructor stub
		  type=t;
		  message=m;
		  type_sound=ty;
	}
	  public Audio(String t,boolean ty) {
			// TODO Auto-generated constructor stub
			  type=t;
			  type_sound=ty;
		}
	public void run(){
	    if(type=="audio")
	    {
	    	try {
	  	  		  FileInputStream file=new FileInputStream(sound);
	  	  		  Player player=new Player(file);
	  	  		  player.play();
	  	  	  }
	  	  	  catch(Exception ex)
	  	  	  {
	  	  		  ex.printStackTrace();
	  	  	  }
	    }
	    else if(type=="message")
	    {
	    	Icon icon;
	    	UIManager.put("OptionPane.background", new ColorUIResource(173, 216, 230));
			UIManager.put("Button.foreground",new ColorUIResource(0, 0, 204));
			UIManager.put("OptionPane.messageForeground",new ColorUIResource(0, 0, 204));
		    UIManager.getLookAndFeelDefaults().put("Panel.background", new ColorUIResource(173, 216, 230));

	    	if(type_sound==true)
	    	{
	    		 icon = new ImageIcon(this.getClass().getResource("/vrai.png"));
	    		 UIManager.put("OptionPane.informationIcon", icon);
	 	    	JOptionPane.showMessageDialog(null,message,"message",JOptionPane.ERROR_MESSAGE,icon);

	    	}
	    	else
	    	{
	    		 icon = new ImageIcon(this.getClass().getResource("/x.png"));
	    		 UIManager.put("OptionPane.informationIcon", icon);
	 	    	JOptionPane.showMessageDialog(null,message,"message",JOptionPane.ERROR_MESSAGE,icon);

	    	}
	    }
	  }   
      public void set_sound(String m,boolean type)//modifier le son selon le type du message
      {   
    	  if(m=="on")
    	  {
    		  if(type==true)
    			  sound="source/s.wav";
    		  else
    			  sound="source/e.wav";
    	  }
    	  else if(m=="off")
    	  {
    		  sound="source/v.wav";
    	  }
      }
}
