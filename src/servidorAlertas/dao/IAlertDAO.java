/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;
import servidorAlertas.dto.AlertDTO;
import servidorAlertas.dto.IndicatorsDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.dto.PatientDTOHolder;
import servidorNotificaciones.dto.NotificationDTO;

/**
 *
 * @author dawish
 */
public interface IAlertDAO {
     

    public boolean registerAlert(AlertDTO objAlert);   
   
    public ArrayList<AlertDTO> selectAlerts(int numAlerts);
}
