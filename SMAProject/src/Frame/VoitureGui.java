package Frame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import agents.VehiculeAgent;

public class VoitureGui extends JFrame {
	private JTextArea jtextAreaMessage=new JTextArea();
	
	private VehiculeAgent vAgent;
	/*private JTextField jTextField; 
	private JTextField jTNomAg;
	private JTextField jTPositionC;
	private JTextField jTPositionR;
	private JTextField jTVistesse;
	private JTextField jTTypeConduite;
	private JTextField jTDistC;
	private JTextField jTDistP;*/
	
	private JTextField jl=new JTextField(20);
	private JTextField jl1=new JTextField(20);
	private JTextField jl2=new JTextField(20);
	private JSlider framesPerSecond;
	public JSlider getFramesPerSecond() {
		return framesPerSecond;
	}

	public void setFramesPerSecond(JSlider framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	public JTextField getJl() {
		return jl;
	}

	public void setJl(JTextField jl) {
		this.jl = jl;
	}
	public JTextField getJl2() {
		return jl;
	}

	public void setJl2(JTextField jl) {
		this.jl = jl;
	}
	public VehiculeAgent getvAgent() {
		return vAgent;
	}

	public void setvAgent(VehiculeAgent vAgent) {
		this.vAgent = vAgent;
	}

	public VoitureGui() {
		
	
		//Create the slider
		 framesPerSecond = new JSlider(JSlider.VERTICAL,   0, 10, 5);
		//framesPerSecond.addChangeListener(this);
		framesPerSecond.setMajorTickSpacing(5);
		framesPerSecond.setPaintTicks(true);

		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put( new Integer( 0 ), new JLabel("Stop") );
		labelTable.put( new Integer( 2 ), new JLabel("Slow") );
		labelTable.put( new Integer( 10 ), new JLabel("Fast") );
		framesPerSecond.setLabelTable( labelTable );

		framesPerSecond.setPaintLabels(true);
		//jTextField.setFont(new Font("Arial", Font.BOLD, 20));
		
		framesPerSecond.addChangeListener(new ChangeListener() {
	            @Override public void stateChanged(ChangeEvent e) {
	                JSlider src = (JSlider) e.getSource();
	                if (src.getValueIsAdjusting()) return;
	                VehiculeAgent.speed=src.getValue();
	                System.out.println(src.getValue());
	            }
	        });
	       
		
		jtextAreaMessage.setFont(new Font("Arial", Font.BOLD, 14));
		jtextAreaMessage.setEditable(false);
		
		JPanel jpanelN=new JPanel();
		jpanelN.setLayout(new FlowLayout());
		JPanel jpanelN1=new JPanel();
		jpanelN1.setLayout(new FlowLayout());
		JPanel jpanelN2=new JPanel();
		jpanelN2.setLayout(new FlowLayout());
		
		
		jl.setText("Ma position :");
		jl.setFont(new Font("Arial", Font.BOLD, 14));
		jl.setEditable(true);
		jl1.setText("dist :");
		jl1.setFont(new Font("Arial", Font.BOLD, 14));
		jl1.setEditable(true);
		
		jl2.setText("dist/Carfour :");
		jl2.setFont(new Font("Arial", Font.BOLD, 14));
		jl2.setEditable(true);
		
		jpanelN1.add(jl);
		jpanelN2.add(jl1);
		jpanelN.add(jl2);
		this.setLayout(new BorderLayout());
	
		this.add(new JLabel("Ma position :"),BorderLayout.AFTER_LAST_LINE);
		this.add(jpanelN,BorderLayout.NORTH);
		this.add(jpanelN1,BorderLayout.AFTER_LAST_LINE);
		this.add(jpanelN2,BorderLayout.SOUTH);
		this.add(framesPerSecond,BorderLayout.WEST);
		
		
		
		//this.add(jl,BorderLayout.CENTER);
		
		this.add(new JScrollPane(jtextAreaMessage),BorderLayout.CENTER);
		this.setSize(400,200);
		
		this.setVisible(true);
	}
	
	
	public void showMessage(String msg,boolean append){
		
		if(append==true) jtextAreaMessage.append(msg+"\n");
		else jtextAreaMessage.setText(msg);
	}
}
