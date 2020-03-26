/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteHabitacion;

import clienteHabitacion.vistas.RoomGUI;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import servidorAlertas.dto.IndicatorsDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.sop_corba.IPatientCallback;
import servidorAlertas.sop_corba.IPatientCallbackHelper;
import servidorAlertas.sop_corba.IPatientManagementHelper;
import servidorAlertas.sop_corba.IPatientManagementOperations;

/**
 *
 * @author dawish
 */
public class AlertClient
{

  static IPatientManagementOperations ref;

  public static void main(String args[]) 
    {
        try{
           String[] vec = new String[4];
            vec[0] = "-ORBInitialHost";
            vec[1] = "localhost";
            vec[2] = "-ORBInitialPort";
            vec[3] = "2020";

            // crea e inicia el ORB
            ORB orb = ORB.init(vec, null);

            // obtiene la base del naming context
            org.omg.CORBA.Object objRef
                    = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
              POA rootPOA= POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            
            
            PatientCallback pacinte=new PatientCallback();
            
            org.omg.CORBA.Object ref1=rootPOA.servant_to_reference(pacinte);
            IPatientCallback href1=IPatientCallbackHelper.narrow(ref1);
            // *** Resuelve la referencia del objeto en el N_S ***
            String name = "objPaciente";
            ref = IPatientManagementHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtenido el manejador sobre el servidor de objetos: " + ref);
            RoomGUI view = new RoomGUI(ref,href1);
            view.setVisible(true);
           
            /* System.out.println("Obtenido el manejador sobre el servidor de objetos: " + ref);
            int numHabitacion;
            int rta = 0;
            do {
            rta = menu();
            switch (rta) {
            case 1:
            System.out.println(" Digite el nombre del paciente: ");
            String nombre = UtilidadesConsola.leerCadena();
            System.out.println(" Digite el apellido del paciente: ");
            String apellido = UtilidadesConsola.leerCadena();
            System.out.println(" Digite la edad del paciente: ");
            int edad = UtilidadesConsola.leerEntero();
            System.out.println(" Digite la habitacion del paciente: ");
            numHabitacion = UtilidadesConsola.leerEntero();
            /* PatientDTO objPaciente = new PatientDTO(nombre,apellido,numHabitacion,edad,href1);
            if ( ref.registrarPaciente(objPaciente)) {
            System.out.println("Paciente registrado con exito.");
            System.out.println(" Digite la frecuencia cardiaca: ");
            int frecuenciaCardiaca = servidor.UtilidadesConsola.leerEntero();
            ref.enviarIndicadores(numHabitacion, frecuenciaCardiaca);
            }
            else{
            System.out.println("Ha ocurrido un error al registrar el paciente.");
            }
            break;
            case 2:
            break;
            }
            } while (rta != 3);*/

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}