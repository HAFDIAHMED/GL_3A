package agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import agents.Coordonnees;
import jade.core.Agent;
import supportClasses.Bloc;
import supportClasses.LocationFeuRouge;

public class RoadPoint extends Agent {


	
	
	private List<List<Coordonnees>> MovesList;
	public static ArrayList<Coordonnees> ChoicePoint = new ArrayList<Coordonnees>();

	private List<Coordonnees> pointdepartList = new ArrayList<Coordonnees>();

	public List<LocationFeuRouge> getSignalLocList() {
		return ListeFeuRouge;
	}

	public void setSignalLocList(List<LocationFeuRouge> signalLocList) {
		ListeFeuRouge = signalLocList;
	}

	private List<LocationFeuRouge> ListeFeuRouge = new ArrayList<LocationFeuRouge>();
	
	public static List<Bloc> BlocList = new ArrayList<Bloc>();
	
	public RoadPoint() {

		BlocList = Arrays.asList(
				new Bloc(new Coordonnees(282, 451), 1),
				new Bloc(new Coordonnees(282, 181), 2)
				);
		
		ListeFeuRouge = Arrays.asList(new LocationFeuRouge(new Coordonnees(305, 600),new Coordonnees(305, 518), 1),
				new LocationFeuRouge(new Coordonnees(0, 475),new Coordonnees(219, 475), 1),
				new LocationFeuRouge(new Coordonnees(260, 220),new Coordonnees(260, 388), 1),
				
				new LocationFeuRouge(new Coordonnees(260, 0),new Coordonnees(260, 116), 2),
				new LocationFeuRouge(new Coordonnees(600, 158),new Coordonnees(349, 158), 2),
				new LocationFeuRouge(new Coordonnees(305, 400),new Coordonnees(305, 246), 2)
				
				);
		
		//entrer droite et l'entrer gauche
		pointdepartList = Arrays.asList( new Coordonnees(-200, 475), new Coordonnees(800, 158) );

		MovesList = Arrays.asList(
			
				
				//ButtonRoadPoint
				Arrays.asList(new Coordonnees(-200, 475), new Coordonnees(-60, 475)),
				Arrays.asList(new Coordonnees(-60, 475), new Coordonnees(-35, 475)),
				Arrays.asList(new Coordonnees(-35, 475), new Coordonnees(-39, 450) , new Coordonnees(-30, 475) , new Coordonnees(-31, 475) ),
				Arrays.asList(new Coordonnees(-39, 450), new Coordonnees(-39, 150)),
				Arrays.asList(new Coordonnees(-39, 150), new Coordonnees(-200, 158)),
				
				Arrays.asList(new Coordonnees(-31, 475), new Coordonnees(-31, 420)),
				Arrays.asList(new Coordonnees(-31, 420), new Coordonnees(-200, 418)),
				
				Arrays.asList(new Coordonnees(-30, 475), new Coordonnees(-40, 475)),
				Arrays.asList(new Coordonnees(-40, 475), new Coordonnees(260, 475)),
				Arrays.asList(new Coordonnees(260, 475),new Coordonnees(260, 600) , new Coordonnees(305, 475) , new Coordonnees(305, 474)),
				Arrays.asList(new Coordonnees(305, 475), new Coordonnees(313, 451)),
				Arrays.asList(new Coordonnees(305, 474), new Coordonnees(313, 450)),
				Arrays.asList(new Coordonnees(313, 451),new Coordonnees(305, 430)),
				Arrays.asList(new Coordonnees(313, 450),new Coordonnees(305, 431)),				
				Arrays.asList(new Coordonnees(305, 430), new Coordonnees(305,203)),
				Arrays.asList(new Coordonnees(305, 431), new Coordonnees(282,418)),
				Arrays.asList(new Coordonnees(282, 418), new Coordonnees(-200,418)),
				
	
				
				Arrays.asList(new Coordonnees(305,203), new Coordonnees(600, 203),new Coordonnees(313, 181),new Coordonnees(305, 600)),
				
				Arrays.asList(new Coordonnees(313, 181), new Coordonnees(313, 172)),
				Arrays.asList(new Coordonnees(313, 172), new Coordonnees(260, 172)),
				
				Arrays.asList(new Coordonnees(260, 172), new Coordonnees(260, 203)),
				
				/////////////////
				
				
				
				
				//UpperRoadPoint
				
				Arrays.asList(new Coordonnees(800, 158), new Coordonnees(622, 158)),
				Arrays.asList(new Coordonnees(622, 158), new Coordonnees(621, 158) ,  new Coordonnees(621, 160) ,  new Coordonnees(621, 166)),
				
				Arrays.asList(new Coordonnees(621, 160), new Coordonnees(620, 477)),
				Arrays.asList(new Coordonnees(620, 477), new Coordonnees(800, 477)),
				
				Arrays.asList(new Coordonnees(621, 166), new Coordonnees(621, 205)),
				Arrays.asList(new Coordonnees(621, 205), new Coordonnees(800, 205)),
				
				
				Arrays.asList(new Coordonnees(621, 158), new Coordonnees(306, 158)),
				Arrays.asList(new Coordonnees(306, 158), new Coordonnees(282, 148) , new Coordonnees(305,0)  ,new Coordonnees(283, 148)),
				Arrays.asList(new Coordonnees(282, 148),new Coordonnees(260,158)),
				Arrays.asList(new Coordonnees(283, 148),new Coordonnees(261,159)),			
				Arrays.asList(new Coordonnees(260,158), new Coordonnees(249, 181)),
				Arrays.asList(new Coordonnees(261,159), new Coordonnees(249, 180)),	
				Arrays.asList(new Coordonnees(249, 181), new Coordonnees(260, 203)),
				Arrays.asList(new Coordonnees(249, 180), new Coordonnees(260, 204)),
				Arrays.asList(new Coordonnees(260, 203) , new Coordonnees(260, 487)),
				Arrays.asList(new Coordonnees(260, 204) , new Coordonnees(282, 204)),
				Arrays.asList(new Coordonnees(282, 204) , new Coordonnees(800, 204)),
				
				Arrays.asList(new Coordonnees(260, 487), new Coordonnees(309,487)),
				Arrays.asList(new Coordonnees(309, 487), new Coordonnees(309,431)),
				Arrays.asList(new Coordonnees(309, 431), new Coordonnees(282,418)),
				/////////////////
				
				
				//UpperfoorTop
				/*Arrays.asList(new Point(260, 0), new Point(260, 159)),
				Arrays.asList(new Point(260, 159)  ,new Point(260, 161), new Point(261, 158) ,new Point(260, 160) ),				
				Arrays.asList(new Point(260, 161), new Point(260, 212)),
				Arrays.asList(new Point(260, 212), new Point(260, 203)),				
				Arrays.asList(new Point(261, 158) , new Point(282, 204)),
				Arrays.asList(new Point(260, 160), new Point(305, 161)),
				Arrays.asList(new Point(305, 161), new Point(305, 0)),*/
				//////////////////////
				

				Arrays.asList(new Coordonnees(-200, 430)),	
				Arrays.asList(new Coordonnees(800, 203)),		
				Arrays.asList(new Coordonnees(305,0)),
				Arrays.asList(new Coordonnees(260, 600))
				//new Coordonnees(305,203)
	
		);
		ChoicePoint.add(new Coordonnees(560 , 775));
		ChoicePoint.add(new Coordonnees(606 , 458));
		ChoicePoint.add(new Coordonnees(560 , 458));
		ChoicePoint.add(new Coordonnees(265, 775));
		ChoicePoint.add(new Coordonnees(922, 458));

	}

	public Coordonnees Move(Coordonnees loctInfo) {

		for (int i = 0; i < MovesList.size(); i++) {
			if (MovesList.get(i).get(0).equal(loctInfo)) {
				int size = MovesList.get(i).size();
				if (size > 1) {
					// int rondomChoice=Min + (int)(Math.random() * ((Max - Min) + 1))
					int rondomChoice = 1 + (int) (Math.random() * (((size - 1) - 1) + 1));
					return MovesList.get(i).get(rondomChoice);
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	public Coordonnees Move(Coordonnees loctInfo , int VehiculeChoice) {

		for (int i = 0; i < MovesList.size(); i++) {
			if (MovesList.get(i).get(0).equal(loctInfo)) {
				int size = MovesList.get(i).size();
				if (size > 1) {
					// int rondomChoice=Min + (int)(Math.random() * ((Max - Min) + 1))
					int rondomChoice = 1 + (int) (Math.random() * (((size - 1) - 1) + 1));
					return MovesList.get(i).get(VehiculeChoice);
				} else {
					return null;
				}}}
		return null;
	}
	
	
	public Coordonnees getRoot() {
		
		int size = pointdepartList.size();
		if (size > 0) {	
			int rondomChoice = 0 + (int) (Math.random() * (((size - 1) - 0) + 1));
			return pointdepartList.get(rondomChoice);
		} else {
			return null;
		}}
	
	
}
