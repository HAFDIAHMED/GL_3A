package calcul;

// Pour utiliser le service de nommage :
import org.omg.CosNaming.*;

// inclure le paquetage des exceptions pouvant être déclenchées
// par le service de nommage :
import org.omg.CosNaming.NamingContextPackage.*;

// Pour manipuler les objets CORBA :
import org.omg.CORBA.*;

// Classes nécessaires pour référencer le POA ( >= Corba 2.2 / J2SE 1.4) :
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA; // interface.

// Propriétés pour initialiser l'ORB :
import java.util.Properties;

public class Serveur {

    public Serveur() {}

    public static void main(String args[]) {
        try {
            // créer et initialiser l'ORB qui intègre le service de noms :
            ORB orb = ORB.init(args, null);

            // obtenir la référence de rootpoa & activer le POAManager :
            POA rootpoa =
                POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // créer l’objet servant :
            Calcul_Servant calc = new Calcul_Servant();

            // obtenir la référence CORBA du servant :
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calc);
            calcul_montants href = calcul_montantsHelper.narrow(ref);

            // obtenir la référence du contexte de nommage :
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");

            // Utiliser NamingContextExt, qui est Interopérable : 
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // enregistrer le servant dans le service de nommage :
            String name = "calcul_ttc"; // nom arbitraire. 

            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("Le Serveur à votre écoute…");

            // attendre les appels des clients…
            orb.run();
        } catch (Exception exc) {
            System.err.println("Erreur : " + exc);
            exc.printStackTrace(System.out);
        }
    }
} // fin du serveur.