/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas;

import java.util.ArrayList;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import servidorAlertas.dao.AlertDAO;
import servidorAlertas.dao.PatientDAO;
import servidorAlertas.dto.IndicatorsDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.dto.PatientDTOHolder;
import servidorAlertas.sop_corba.IPatientManagementOperations;
import servidorNotificaciones.sop_corba.INotificationsManagementHelper;
import servidorNotificaciones.sop_corba.INotificationsManagementOperations;

/**
 *
 * @author dawish
 */
public class PatientManagement implements IPatientManagementOperations{
    
    private INotificationsManagementOperations refNotifiacations;
    private AlertDAO alertDAO;
    private PatientDAO patientDAO;
    
    private void initPOAS(){
       //alertDAO=new AlertDAO();
       patientDAO=new PatientDAO();
    }
    @Override
    public boolean registerPatient(PatientDTO objPatient) {
        this.initPOAS();
        if (objPatient!=null) {
            this.patientDAO.registerPatient(objPatient);
            return true;
        }
        return false;
    }

    @Override
    public boolean findPatient(int roomId, PatientDTOHolder objPatient) {
        this.initPOAS();
        if (objPatient!=null) {
            objPatient.value=this.patientDAO.findPatient(roomId);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyPatient(int roomId,PatientDTO objPatientModified) {
        this.initPOAS();
        if (objPatientModified!=null) {
             this.patientDAO.modifyPatient(objPatientModified.roomNumber, objPatientModified);
             return true;
        }
       return false;
    }

    @Override
    public boolean selectPatient(int roomId, PatientDTOHolder objPatientSelected) {
        this.initPOAS();
        if (roomId>99 && roomId<1000) {
            objPatientSelected.value=this.patientDAO.selectPatient(roomId);
            return true;
        }
        return false;
    }
    @Override
    public String[] selectAllPatients() {
        this.initPOAS();
        ArrayList<PatientDTO> patients=this.patientDAO.selectAllPatients();
        String[] patientsList;
        patientsList = (String[])patients.toArray();
      return patientsList;
    }
    
    @Override
    public boolean sendIndicators(int roomId,IndicatorsDTO listIndicators) {
        this.initPOAS();
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getRefRemoteNotificationserver(String IpNS,String portNS){
        try {
            String[] vec=new String[4];
            vec[0]="-ORBInitialHost";
            vec[1]=IpNS;
            vec[2]="-ORBInitialPort";
            vec[3]=portNS;
            
            ORB orb= ORB.init(vec,null);
            
            org.omg.CORBA.Object objref=orb.resolve_initial_references("NameService");
            NamingContextExt ncRef=NamingContextExtHelper.narrow(objref);
            
            String name="objNotificaciones";
            refNotifiacations=INotificationsManagementHelper.narrow(ncRef.resolve_str(name));
            
        } catch (Exception e) {
        }
    }

 
}
