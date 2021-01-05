package agents;



public class Coordonnees {
		double X;
		double Y;

		public double getX() {
			return X;
		}

		public void setX(double x) {
			X = x;
		}

		public double getY() {
			return Y;
		}

		public void setY(double y) {
			Y = y;
		}

		public Coordonnees(double x, double y) {
			this.X = x - 300;
			this.Y = y - 300;
		}

		public boolean equal(Coordonnees coordonnees) {
			if ((int)coordonnees.X == (int)this.X && (int)coordonnees.Y == (int)this.Y) {
				return true;
			} else {
				return false;
			}
		}
	}