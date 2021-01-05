package supportClasses;

import agents.Coordonnees;

public class Bloc {
	
	public Coordonnees Centre;
	public double bloc = 0.0;
	
	public Bloc(Coordonnees center, double group) {
		
		this.Centre = center;
		this.bloc = group;
	}
	
}
