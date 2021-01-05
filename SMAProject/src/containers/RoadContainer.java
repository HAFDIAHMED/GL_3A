package containers;

import java.util.Objects;

import agents.CarrefourAgents;
import agents.AgentRoute;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class RoadContainer {
	   public RoadContainer() throws StaleProxyException{
		   this.StartContainer();
	   }
	    public void StartContainer() throws StaleProxyException{
		Runtime rt = Runtime.instance(); //Créer une instance de la MV 
		ProfileImpl profile = new ProfileImpl(false);
		profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
		AgentContainer RoadContainer = rt.createAgentContainer(profile);
		AgentController RoadController = RoadContainer.createNewAgent("Road1", AgentRoute.class.getName() , new Objects[]{});
		//AgentController VehiculeController = RoadContainer.createNewAgent("Vehicule1", VehiculeAgent.class.getName() , new Objects[]{});
		AgentController CarfourController1 = RoadContainer.createNewAgent("CarrefourEntree", CarrefourAgents.class.getName() , new Objects[]{});
		AgentController CarfourController2 = RoadContainer.createNewAgent("CarrefourSortie", CarrefourAgents.class.getName() , new Objects[]{});
		
		RoadController.start();
	
		CarfourController1.start();
		CarfourController2.start();
		}
	    
	    
	    
}
