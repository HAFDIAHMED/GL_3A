package supportClasses;

import agents.Coordonnees;

public class LocationFeuRouge {
	public Coordonnees depart;
	public Coordonnees fin;
	public double Bloc = 0.0;

	public Coordonnees getDepart() {
		return depart;
	}

	public void setDepart(Coordonnees depart) {
		this.depart = depart;
	}

	public Coordonnees getFin() {
		return fin;
	}

	public void setFin(Coordonnees fin) {
		this.fin = fin;
	}

	public double getBloc() {
		return Bloc;
	}

	public void setBloc(double bloc) {
		Bloc = bloc;
	}

	public LocationFeuRouge(Coordonnees depart, Coordonnees fin, double Bloc) {
		this.depart = depart;
		this.fin = fin;
		this.Bloc = Bloc;
	}

}
