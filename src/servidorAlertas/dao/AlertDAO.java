/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;
import servidorAlertas.conection.ConnectionDB;
import servidorAlertas.dto.AlertDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.dto.PatientDTOHolder;

/**
 *
 * @author dawish
 */
public class AlertDAO implements IAlertDAO{
    
     private final ConnectionDB connectionDB;

    public AlertDAO() {
        this.connectionDB = new ConnectionDB();
    }
     

    @Override
    public boolean registerAlert(AlertDTO objAlert) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AlertDTO> selectAlerts(int numAlerts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
