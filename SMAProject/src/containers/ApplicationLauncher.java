package containers;

import java.util.ArrayList;

import java.util.List;

import agents.RoadPoint;
import jade.core.ProfileImpl;
import jade.core.Runtime;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import supportClasses.LocationFeuRouge;

public class ApplicationLauncher extends Application {

	public RoadPoint roadPoint = new RoadPoint();
	public StackPane stackPane = new StackPane();
	public static final double normalCarCost = 1;
	public static double  nbrVoiture=5;
	
	
	public ArrayList<Shape> agentShapeList;
	
	public static void main(String[] args) {
		launch(ApplicationLauncher.class);
	}

	public void startcontainer() {
		
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			System.out.println("Conection set");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
			System.out.println("container created");
			agentShapeList = new ArrayList<Shape>();
			
			//Creation et initialisation De l'agent Route
			AgentController roadAgent = agentContainer.createNewAgent("roadAgent", agents.AgentRoute.class.getName(),
					new Object[] {this , normalCarCost});
			roadAgent.start();
			 
			for (int i = 0; i < getRoadPoint().getSignalLocList().size(); i++) {
				LocationFeuRouge signalInfo = getRoadPoint().getSignalLocList().get(i);

				AgentController signalTrafficAgent;
				signalTrafficAgent = agentContainer.createNewAgent("signalTrafficAgent" + i, agents.FeuRouge.class.getName(),
						new Object[] { this, signalInfo });
				signalTrafficAgent.start();

			}
			
			
			for (int i = 0; i < nbrVoiture; i++) {
				AgentController Voiture;
				Voiture = agentContainer.createNewAgent("vehicleAgent"+i, agents.VehiculeAgent.class.getName(), new Object[] {this , normalCarCost});
				
				Voiture.start();
			}
			


		} catch (StaleProxyException e1) {
			// TODO Auto-generated catch block
			System.out.println("excep.....");
			e1.printStackTrace();
		}
	}

	public List<String> getLocalAgents(int xmin, int xmax, int ymin, int ymax) {

		List<String> localAgentList = new ArrayList<String>();
		for (Node node : stackPane.getChildren()) {
			if (node.getTranslateX() > xmin && node.getTranslateX() < xmax && node.getTranslateY() > ymin
					&& node.getTranslateY() < ymax) {
				if (node.getUserData() != null) {
					localAgentList.add(node.getUserData().toString());
					localAgentList.add(node.toString());
				}
			}
		}
		return localAgentList;
	}

	@Override
	public void start(Stage ApplicationView) throws Exception {

		 startcontainer();

		Image image = new Image("/image/city.png");
		ImageView imageView = new ImageView(image);

		stackPane.getChildren().add(imageView);
		imageView.toBack();
		
		VBox vbox = new VBox(stackPane);

		Scene scene = new Scene(vbox);
		
		ApplicationView.setScene(scene);
		ApplicationView.fullScreenProperty();
	
		ApplicationView.setResizable(true);
		
		ApplicationView.sizeToScene();

		ApplicationView.show();
	}

	public RoadPoint getRoadPoint() {
		return roadPoint;
	}

	public void setRoadPoint(RoadPoint roadPoint) {
		this.roadPoint = roadPoint;
	}
}
