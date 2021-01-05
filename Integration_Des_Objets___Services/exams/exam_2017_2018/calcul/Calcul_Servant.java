package calcul;

// (importations ici, si cette classe est en tête du paquetage)

class Calcul_Servant extends _calcul_montantsPOA {

    public Calcul_Servant() {}

    public double calcul_ttc(double mt_ht, double taux) {
        return mt_ht * (1 + taux / 100);
    }
}