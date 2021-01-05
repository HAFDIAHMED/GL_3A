package agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import Frame.VoitureGui;
import agents.Coordonnees;
import containers.ApplicationLauncher;
import jade.core.AID;

import jade.core.behaviours.CyclicBehaviour;

import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;

import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;

import javafx.scene.shape.Circle;


public class VehiculeAgent extends GuiAgent {
	//////
	public static int vit=5;
	public static int k=1;
	private static VoitureGui VoitureGUI;
	private String pos;
	private String typeCond="calme";
	////////////
	private ApplicationLauncher gui;
	public static boolean b = false;
	Coordonnees prochainepos = null;
	Map<String, List<Double>> signalMessage = new HashMap<String, List<Double>>();
	

	Circle Voiture;
	public static  double speed = 1;
	boolean arret=false;
	boolean collision=false;
	public VehiculeAgent() {
		
	}
	
	public boolean feuestrouge(double x, double y) {
		
		for (List<Double> color_coord : signalMessage.values()) {
			double signalColor = color_coord.get(0);
			double signalX = color_coord.get(1);
			double signalY = color_coord.get(2);
			double distToSignal = Math.sqrt(Math.pow(x - signalX, 2)
					+ Math.pow(y - signalY, 2));
			
			//System.out.println(distToSignal + " " + signalColor+" "+signalMessage.values().size());
			if (distToSignal < 8) {
				if (signalColor == FeuRouge.red) {
					return true;
				}
				
			}
		}
		return false;
		
	}
	

	@Override
	protected void setup() {

		// Initialisation de l'interface Java.
		k=0;
		VoitureGUI=new VoitureGui();
		VoitureGUI.setvAgent(this);
		///////////////////////////////////////////
		
		
		
		
		//Initialisation du voiture et l"enregistrer dans la route
		Object[] args = getArguments();
		if (args.length == 2) {
			
			gui = (ApplicationLauncher) getArguments()[0];
			Voiture = new Circle();
			Voiture.setUserData((double) getArguments()[1]);
			Voiture.setRadius(10.0);
			double carType= (double) getArguments()[1];
			Image image = new Image("/image/car1.png");
			Voiture.setFill(new ImagePattern(image));
			speed = 2.3;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.stackPane.getChildren().add(Voiture);
					gui.agentShapeList.add(Voiture);
					Voiture.toFront();
				}});initialize();

		} else {
			System.out.println("no container exist");
			doDelete();
		}
		String ontology = this.getAID().getName();
		String dataMsg = (300+Voiture.getTranslateX())+" "+(300+Voiture.getTranslateY())+" "+speed;
		sendToRoad(dataMsg, ontology);

		///////////////////////////////////////////////////////////////////////////
		
		
		
		//Different Behaviours 
		addBehaviour(new TickerBehaviour(this, 8) {
			@Override
			
			protected void onTick() {
				
				
				arret=feuestrouge(Voiture.getTranslateX(), Voiture.getTranslateY());
				collision=CarAhead((300+Voiture.getTranslateX()),( Voiture.getTranslateY() + 300 ));
				
				if (!arret && !collision) {
					if (prochainepos != null) {
						double deltaX = prochainepos.X - Voiture.getTranslateX();
						double deltaY = prochainepos.Y - Voiture.getTranslateY();
						double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
						
						if (distance < speed ) {
							
							//System.out.println("X :"+(agentShape.getTranslateX()) + "Y "+(agentShape.getTranslateY()));
							//System.out.println(RoadPoint.ChoicePoint.get(2).X +"  "+RoadPoint.ChoicePoint.get(2).Y);
							Coordonnees p = new Coordonnees((600+Voiture.getTranslateX()) ,(600+Voiture.getTranslateY()));		
							
							if(contain(p)){
								int rondomChoice = 1 + (int) (Math.random() * (((3 - 1) - 1) + 1));
								prochainepos = gui.roadPoint.Move(prochainepos , rondomChoice);
							}
									
							else{  prochainepos = gui.roadPoint.Move(prochainepos);}
							
							if (prochainepos != null) {
								deltaX = prochainepos.X - Voiture.getTranslateX();
								deltaY = prochainepos.Y - Voiture.getTranslateY();

							}

						}
					}
					if (prochainepos != null) {

						double deltaX = prochainepos.X - Voiture.getTranslateX();
						double deltaY = prochainepos.Y - Voiture.getTranslateY();
						double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

						double stepX = deltaX / distance;
						double stepY = deltaY / distance;

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Voiture.setTranslateX(Voiture.getTranslateX() + stepX * speed);
								Voiture.setTranslateY(Voiture.getTranslateY() + stepY * speed);
								pos="ma position actuelle : ("+ (int)(Voiture.getTranslateX() + stepX * speed+300)+","+(int)(300+Voiture.getTranslateY() + stepY * speed)+")";
								VoitureGUI.getJl().setText(pos);
							}
						});

					} else {
						
						initialize();
					}

				}
			}

			

		});
		
	
		
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					
					List<String> stringList = Arrays.asList(msg.getContent().split(" "));
					if(stringList.get(0).equals("slow")){
						//System.out.println("hhhhhhhhhhhhhhhhhhh");
						//speed = 0.4;
					}
					else{
					List<Double> color_coord = new ArrayList<Double>();
					try {
						color_coord.add(Double.parseDouble(stringList.get(0)));
						color_coord.add(Double.parseDouble(stringList.get(1)));
						color_coord.add(Double.parseDouble(stringList.get(2)));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					signalMessage.put(msg.getSender().getLocalName(), color_coord);
				} }else {
					block();
				}
			}
		});

		addBehaviour(new TickerBehaviour(this, 10) {
			@Override
			
			protected void onTick() {
			String ontology = this.myAgent.getAID().getName();
			String dataMsg = (300+Voiture.getTranslateX())+" "+(300+Voiture.getTranslateY())+" "+speed;
			sendToRoad(dataMsg , ontology);
			}
		});


		System.out.println("I'm vehicleAgent ");
		System.out.println("My Name is " + this.getAID().getName());
		
		
		
		
		    k=0;
			String type="Voiture normal";
			
			if((double)getArguments()[1]==100.0)type="Police";
			else if((double)getArguments()[1]==200.0)type="Ambulance";
			
			VoitureGUI.showMessage("Je suis "+ this.getAID().getLocalName()+" "
								+type+"\nType de conduite :"+ this.typeCond+"\nma vitesse est de :"+ this.speed+"m/s", true);
			VoitureGUI.getJl().setText(pos);
			
			//conGui.getJl2().setText("Distance/Carfoure : ");
	
		
	}
	
	private boolean CarAhead(double translateX, double translateY) {
		String AIDD = "";
		boolean b = false;
		if(AgentRoute.vehicules.size()==ApplicationLauncher.nbrVoiture){
		for(int i=0 ; i!=AgentRoute.vehicules.size() ; i++){
			AIDD = "vehicleAgent"+i;
			if(!AIDD.equals(this.getAID())){
			
			ArrayList<Double> arr = AgentRoute.vehicules.get("vehicleAgent"+i);
				int x = (int)Math.abs(arr.get(0));
				int y = (int)Math.abs(arr.get(1));
				if(Math.abs(translateY-y) < 50 && Math.abs(x -translateX)<15 ){
					
					//int o = (1 + (int) (Math.random() * (((3 - 1) - 1) + 1)));
					//if( o==1) b=true;
					//System.out.println("colision");
				}
				
				
			
				}
			}
		}
			//if(b) break;
		
		
	
		return b ;
	}

	public void initialize() {
		
		prochainepos = gui.roadPoint.getRoot();
		//agentShape.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		
		if (prochainepos != null) {

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Voiture.setTranslateX(prochainepos.X);
					Voiture.setTranslateY(prochainepos.Y);
				}
			});

		} else {
			System.out.println("problem of initializing...");
			doDelete();
		}
		
	}
	
	public void RegisterToRoad(){
		
		
	}
	public void sendToRoad(String dataMsg, String ontology) {
		
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			
			message.setContent(dataMsg);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					message.addReceiver(new AID("roadAgent", AID.ISLOCALNAME));
					send(message);
				}
			});
		
	}
	
	public boolean contain(Coordonnees p){
		boolean b= false;
		for(int i =0 ; i!=RoadPoint.ChoicePoint.size() ;i++ ){
			if(p.equal(RoadPoint.ChoicePoint.get(i))) b=true;
		}
		return b;
	}

	@Override
	protected void beforeMove() {
		System.out.println("Avant de migrer vers une nouvelle location .....");
	}

	@Override
	protected void afterMove() {
		System.out.println("Je viens d'arriver à une nouvelle location .....");
	}

	@Override
	protected void takeDown() {
		System.out.println("I'm going to die ...");
	}

	@Override
	protected void onGuiEvent(GuiEvent guiEvent) {
		// TODO Auto-generated method stub
		// if (guiEvent.getType() == 1) {
		// ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		// String livre = guiEvent.getParameter(0).toString();
		// aclMessage.addReceiver(new AID("rma", AID.ISLOCALNAME));
		// send(aclMessage);
		// }

	}
}


