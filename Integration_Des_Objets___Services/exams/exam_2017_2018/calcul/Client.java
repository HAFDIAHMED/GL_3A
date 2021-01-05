package calcul;

import org.omg.CosNaming.*; // inclure le service de nommage
import org.omg.CORBA.*; // manipuler des objets CORBA
import org.omg.CosNaming.NamingContextPackage.*;

public class Client {

   public Client() {}

   public static void main(String args[]) {
      try {
         double mt_ht;
         double taux;
         double mt_ttc;
         mt_ht = Double.valueOf(args[0]);
         taux = Double.valueOf(args[1]);

         // créer et initialiser l'ORB
         ORB orb = ORB.init(args, null);

         // obtenir une référence au service de nommage
         org.omg.CORBA.Object objRef =
            orb.resolve_initial_references("NameService");

         // Utiliser NamingContextExt (au lieu de NamingContext), car
         //interopérable :

         NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

         // Demander la référence de l'objet au service de noms :

         String nom = "calcul_ttc"; // le nom “public” du service.
         calcul_montants calcul_ttc = calcul_montantsHelper.narrow(ncRef.resolve_str(nom));

         // Faire appel a l'objet serveur et imprimer les résultats :

         mt_ttc = calcul_ttc.calcul_ttc(mt_ht, taux);

         System.out.println("le montant ttc " + mt_ttc);
      } catch (Exception exc) {
         System.out.println("Erreur : " + exc);
         exc.printStackTrace(System.out);
      }
   } // fin du main
}