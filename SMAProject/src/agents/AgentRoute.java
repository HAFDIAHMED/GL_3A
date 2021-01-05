package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import containers.ApplicationLauncher;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AgentRoute extends GuiAgent {
	
	public static HashMap<String, ArrayList<Double>> vehicules = new HashMap<String, ArrayList<Double>>();
	static	String  AIDD = null;
	@Override
	protected void setup() {
		ApplicationLauncher gui=(ApplicationLauncher) getArguments()[0];
		
		System.out.println("I'm roadAgent");
		System.out.println("My Name is " + this.getAID().getName());
		
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					List<String> stringList = Arrays.asList(msg.getContent().split(" "));
					ArrayList<Double> veh_coord = new ArrayList<Double>();
					try {
						veh_coord.add(Double.parseDouble(stringList.get(0)));
						veh_coord.add(Double.parseDouble(stringList.get(1)));
						veh_coord.add(Double.parseDouble(stringList.get(2)));
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					//System.out.println("GET0 = "+ veh_coord.get(0)  +"GET1 ="+ veh_coord.get(1));
					calldistance(veh_coord.get(0),veh_coord.get(1));
					vehicules.put(msg.getSender().getLocalName(), veh_coord);
					//System.out.println(vehicules);
				} else {
					block();
				}
			}
		});
		
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				if(vehicules.size()==ApplicationLauncher.nbrVoiture){
					
				for(int i=0 ; i!=vehicules.size() ; i++){
					ArrayList<Double> arr = vehicules.get("vehicleAgent"+i);
					for(int j=0 ; j!=vehicules.size() ; j++){
						
						if(i!=j){
						ArrayList<Double> arr2 = vehicules.get("vehicleAgent"+j);
						
						
						int x1 = (int)Math.abs(arr.get(0));
						int x2 = (int)Math.abs(arr2.get(0));
						
						if(Math.abs(arr.get(1)-arr2.get(1)) < 10 && Math.abs(x1 -x2)<3 ){
							//System.out.println(vehicules+" i = "+i + " j="+ j);
							if( arr.get(2) < arr2.get(2)){ AIDD = "vehicleAgent"+j ;}
							else if (arr.get(2) >= arr2.get(2)) {AIDD = "vehicleAgent"+i ; }
							
							//sendAlert("slow" , AIDD);
						
							//System.out.println("collision");
							
						}
					
						}
					}
					
					//if(b) break;
				}
				
			}}
		});
		

		
//		addBehaviour(new TickerBehaviour(this, 10) {
//			@Override
//			protected void onTick() {
//			}
//		});

		
		
		
		/*imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});*/
	}
	
	public Coordonnees calldistance(double X, double Y){
		
		int min = Integer.MAX_VALUE ;
		int index ;
		Coordonnees p = new Coordonnees(300 , 300);
		for(int i=0 ; i!=RoadPoint.BlocList.size() ; i++){
			int diffx = Math.abs((int) (RoadPoint.BlocList.get(i).Centre.X- X)) ;
			int diffy = Math.abs((int) (RoadPoint.BlocList.get(i).Centre.Y - Y)) ;
			//System.out.println("distance X rec = " + X + " disatnce Y rec "+ Y +" diffrence X ="+diffx+" difference Y ="+diffx+" "+i);
			
			int somme = diffx + diffy;
			min = somme;
			//System.out.println("somme ="+somme+" i = " +i);
			if(min >= somme ){
				
				min = somme;
				p.X=diffx;
				p.Y=diffy;
			}
			
		}
		
		return p;
	} 
	
	public void sendAlert(String dataMsg, String ontology) {
		
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		
		message.setContent(dataMsg);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				message.addReceiver(new AID(ontology, AID.ISLOCALNAME));
				send(message);
			}
		});
	
}

	@Override
	protected void beforeMove() {
		System.out.println("Avant de migrer vers une nouvelle location .....");
	}

	@Override
	protected void afterMove() {
		System.out.println("Je viens d'arriver � une nouvelle location .....");
	}

	@Override
	protected void takeDown() {
		System.out.println("avant de mourir .....");
	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}