package agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import containers.ApplicationLauncher;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import supportClasses.Bloc;
import supportClasses.LocationFeuRouge;
import supportClasses.StatuFeuRouge;

public class FeuRouge extends GuiAgent {
	
	//Variable de classe
	private ApplicationLauncher gui;
	StackPane stackPane;	
	Light.Point redLight;
	Light.Point greenLight;
	public static final double red = 0;
	public static final double green = 1;
	public static final double alight = 80;
	public static final double notAlight = 2;
	double radius = 11.0;
	Line lineDetector;
	Circle circleDetector;
	LocationFeuRouge FeuInformation;

	

	public class FeuTraficInformation {
		String id;
		double group;

		public FeuTraficInformation(String id,double group){
			this.id = id;
			this.group = group;
		}
	}

	public String agentName = new String();
	HashMap<String, StatuFeuRouge> messages = new HashMap<String, StatuFeuRouge>();

	public class Queue {
		boolean wait = false;
		HashMap<String, String> messages = new HashMap<String, String>();
	}

	double timer = 0;
	public static final double timerMax = 10;

	public Queue queue = new Queue();
	public StatuFeuRouge signalState = new StatuFeuRouge();
	// public ACLMessage receivedMessage;

	public String maxSignal() {

		HashMap.Entry<String, StatuFeuRouge> msg0 = messages.entrySet().iterator().next();
		String nameMax = msg0.getKey();
		double scoreMax = msg0.getValue().nbVehicle * (msg0.getValue().waitingTime / msg0.getValue().passingTime);

		for (HashMap.Entry<String, StatuFeuRouge> msg : messages.entrySet()) {
			// System.out.println(msg.getValue().nbVehicle);

			String sender = msg.getKey();
			double score = msg.getValue().nbVehicle * (msg.getValue().waitingTime / msg.getValue().passingTime);
			;

			if (score > scoreMax) {
				scoreMax = score;
				nameMax = sender;
			} else if (score == scoreMax) {
				// System.out.println(sender+" vs "+maxName);
				if (nameMax.compareTo(sender) < 0) {
					nameMax = sender;
				}
			}
		}
		if (signalState.timer == 100) {
			signalState.timer = 0;
		} else {
			signalState.timer++;
		}
		// System.out.println(scoreMax+"/"+nameMax+"/"+agentName);
		return nameMax;
	}


	@Override
	protected void setup() {

		Object[] args = getArguments();
		agentName = this.getAID().getLocalName();
		if (args.length == 2) {

			gui = (ApplicationLauncher) getArguments()[0];
			FeuInformation = (LocationFeuRouge) getArguments()[1];
			stackPane = new StackPane();
			stackPane.setUserData(new FeuTraficInformation(this.getAID().getName(), FeuInformation.Bloc));
			
			circleDetector = new Circle();
			circleDetector.setRadius(35);
			circleDetector.setFill(Color.TRANSPARENT);
			

			for (Bloc centerTraffic : gui.roadPoint.BlocList) {
				if (centerTraffic.bloc == FeuInformation.Bloc) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							circleDetector.setTranslateY(centerTraffic.Centre.Y);
							circleDetector.setTranslateX(centerTraffic.Centre.X);
						}
					});
				}
			}

			lineDetector = new Line();
			lineDetector.setStartX(FeuInformation.depart.X);
			lineDetector.setStartY(FeuInformation.depart.Y);
			lineDetector.setEndX(FeuInformation.fin.X);
			lineDetector.setEndY(FeuInformation.fin.Y);

			stackPane.getChildren().add(lineDetector);
			lineDetector.setTranslateX((FeuInformation.depart.X - FeuInformation.fin.X) / 2);
			lineDetector.setTranslateY((FeuInformation.depart.Y - FeuInformation.fin.Y) / 2);
			lineDetector.setStrokeWidth(0.1);
			lineDetector.toFront();
			double x = (Math.cos(Math.PI / 6) * radius);
			double y = (Math.sin(Math.PI / 6) * radius);
			
			redLight = new Light.Point();
			createSignal(Color.RED, 0, -radius, redLight);
			

			greenLight = new Light.Point();
			createSignal(Color.GREEN, x, y, greenLight);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.stackPane.getChildren().add(stackPane);
					gui.stackPane.getChildren().add(circleDetector);

					stackPane.setTranslateX(FeuInformation.fin.X);
					stackPane.setTranslateY(FeuInformation.fin.Y);
					stackPane.toFront();

				}
			});

		} else {
			System.out.println("some argument are missing");
			doDelete();
		}

		addBehaviour(new TickerBehaviour(this, 2000) {
			@Override
			protected void onTick() {
				String ontology = "vehicleNumber";
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						String dataMsg = FeuInformation.Bloc + " " + getVehicleNum(lineDetector) + " " + signalState.passingTime
								+ " " + signalState.waitingTime + " " + signalState.timer;
						sendToSignal(dataMsg, ontology);
					}
				});

			}
		});

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {

				ACLMessage receivedMessage = receive();
				if (receivedMessage != null) {
					List<String> stringList = Arrays.asList(receivedMessage.getContent().split(" "));
					double senderGroup = 0;
					try {
						senderGroup = Double.parseDouble(stringList.get(0));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					if (senderGroup == FeuInformation.Bloc) {
						if (receivedMessage.getOntology() == "vehicleNumber") {

							if (Double.parseDouble(stringList.get(4)) == signalState.timer) {

								StatuFeuRouge signalS = new StatuFeuRouge();
								try {
									signalS.nbVehicle = Double.parseDouble(stringList.get(1));
									signalS.passingTime = Double.parseDouble(stringList.get(2));
									signalS.waitingTime = Double.parseDouble(stringList.get(3));
									signalS.timer = Double.parseDouble(stringList.get(4));
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
								messages.put(receivedMessage.getSender().getLocalName(), signalS);
							}
						}
					}
				} else {
					block();
				}
				if (messages.size() == 3) {
					boolean wait = false;
					if (greenLight.getZ() == notAlight && getVehicleNum(circleDetector) > 0) {
						wait = true;
					}
					
					if (maxSignal().equals(agentName) && !wait) {

						signalState.passingTime++;
						signalState.waitingTime = 1;
						
						greenLight.setZ(alight);
						redLight.setZ(notAlight);

						String dataMsgV = green + " " + FeuInformation.fin.X + " " + FeuInformation.fin.Y;
						sendToVehicle(dataMsgV);
					} else {
						if (getVehicleNum(lineDetector) > 0) {
							signalState.waitingTime++;
						}
						signalState.passingTime = 1;

						greenLight.setZ(notAlight);
						redLight.setZ(alight);

						String dataMsg = red + " " + FeuInformation.fin.X + " " + FeuInformation.fin.Y;
						sendToVehicle(dataMsg);

					}
					messages.clear();
				}
			}
		});
		System.out.println("I'm signalTrafficAgent ");
		System.out.println("My Name is " + this.getAID().getName());
	}

	public void sendToSignal(String dataMsg, String ontology) {
		for (int i = 0; i < gui.roadPoint.getSignalLocList().size(); i++) {
			int j = i;
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setOntology(ontology);
			message.setContent(dataMsg);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					message.addReceiver(new AID("signalTrafficAgent" + j, AID.ISLOCALNAME));
					send(message);
				}
			});
		}
	}

	public void sendToVehicle(String dataMsg) {
		for (int i = 0; i < gui.nbrVoiture; i++) {
			int j = i;
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setContent(dataMsg);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					message.addReceiver(new AID("vehicleAgent" + j, AID.ISLOCALNAME));
					send(message);
				}
			});
		}
	}

	public void createSignal(Color color, double x, double y, Light.Point lightPoint) {

		Circle circle = new Circle();
		circle.setRadius(this.radius);
		circle.setFill(color);
		stackPane.getChildren().add(circle);
		circle.setTranslateX(x);
		circle.setTranslateY(y);

		lightPoint.setColor(color);
		lightPoint.setX(x);
		lightPoint.setY(y);
		lightPoint.setZ(notAlight);
		
		Lighting lighting = new Lighting();
		lighting.setLight(lightPoint);
		circle.setEffect(lighting);
	}

	private double getVehicleNum(Shape shapeDetector) {

		double costVehicleDetected = 0;
		if (lineDetector != null) {
			for (Shape otherAgent : gui.agentShapeList) {
				Shape intersect = Shape.intersect(otherAgent, shapeDetector);
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					costVehicleDetected+=(double)otherAgent.getUserData();
				}
			}
		}
		return costVehicleDetected;
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
		
		
	}
}
