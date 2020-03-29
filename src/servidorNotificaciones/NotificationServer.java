/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorNotificaciones;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import servidorNotificaciones.sop_corba.INotificationsManagement;
import servidorNotificaciones.sop_corba.INotificationsManagementPOATie;

/**
 *
 * @author dawish
 */
public class NotificationServer {

   public static void main(String[] args) throws org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName {
        try {
            String[] vec = new String[4];
            vec[0] = "-ORBInitialHost";
            vec[1] = "localhost";
            vec[2] = "-ORBInitialPort";
            vec[3] = "2020";
            System.out.println("1. Crea e inicia el orb");
            // crea e inicia el ORB
            ORB orb = ORB.init(vec, null);

            System.out.println("2. Obtiene la referencia al poa raiz, por medio del orb ");
            org.omg.CORBA.Object objPOA = null;
            objPOA = orb.resolve_initial_references("RootPOA");
            POA rootPOA = POAHelper.narrow(objPOA);

            System.out.println("3. Activa el POAManager");
            rootPOA.the_POAManager().activate();

            System.out.println("4. Crea el objeto servant");
            NotificationsManagement ObjServant = new NotificationsManagement();

            System.out.println("5. Crea el objeto tie y se registra una referencia al objeto servant mediante el contructor");
            INotificationsManagementPOATie objTIE = new INotificationsManagementPOATie(ObjServant);

            System.out.println("6. Obtiene la referencia al orb ");
            INotificationsManagement referenciaORB = objTIE._this(orb);

            System.out.println("7. Obtiene una referencia al servicio de nombrado por medio del orb");
            org.omg.CORBA.Object objRefNameService
                    = orb.resolve_initial_references("NameService");

            System.out.println("8. Convierte la ref genérica a ref de NamingContextExt");
            NamingContextExt refContextoNombrado = NamingContextExtHelper.narrow(objRefNameService);

            System.out.println("9.Construir un contexto de nombres que identifica al servant");
            String identificadorServant = "objNotificaciones";

            NameComponent[] path = refContextoNombrado.to_name(identificadorServant);       
            
            

            System.out.println("10.Realiza el binding de la referencia de objeto en el N_S");
            refContextoNombrado.rebind(path, referenciaORB);

            System.out.println("El Servidor esta listo y esperando ...");
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

    }
    
}
