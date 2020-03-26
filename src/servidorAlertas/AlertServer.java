package servidorAlertas;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import servidorAlertas.sop_corba.*;

public class AlertServer {

  public static void main(String args[]) {
    try{
        
        
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
        objPOA=orb.resolve_initial_references("RootPOA");
        POA rootPOA = POAHelper.narrow(objPOA);

        System.out.println("3. Activa el POAManager");
        rootPOA.the_POAManager().activate();

               
        System.out.println("4. Crea el objeto servant");
        PatientManagement ObjServant = new PatientManagement();   
        ObjServant.getRefRemoteNotificationserver(vec[1], vec[3]);
        
        System.out.println("5. Crea el objeto tie y se registra una referencia al objeto servant mediante el contructor");
        IPatientManagementPOATie objTIE= new IPatientManagementPOATie(ObjServant);

        System.out.println("6. Obtiene la referencia al orb ");
        IPatientManagement referenciaORB = objTIE._this(orb);

        System.out.println("7. Obtiene una referencia al servicio de nombrado por medio del orb");
        org.omg.CORBA.Object objRefNameService =
                orb.resolve_initial_references("NameService");

        System.out.println("8. Convierte la ref genérica a ref de NamingContextExt");
        NamingContextExt refContextoNombrado = NamingContextExtHelper.narrow(objRefNameService);

        System.out.println("9.Construir un contexto de nombres que identifica al servant");
        String identificadorServant = "objPaciente";

        NameComponent[] path = refContextoNombrado.to_name(identificadorServant);       
        System.out.println("10.Realiza el binding de la referencia de objeto en el N_S");
        refContextoNombrado.rebind(path, referenciaORB);

        System.out.println("El Servidor esta listo y esperando ...");
        orb.run();
    } 
	
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
	  
      System.out.println("Servidor: Saliendo ...");
	
  }
}