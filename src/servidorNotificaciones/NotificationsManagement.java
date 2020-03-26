/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorNotificaciones;

import servidorNotificaciones.dto.NotificationDTO;
import servidorNotificaciones.sop_corba.INotificationsManagementOperations;
import servidorNotificaciones.sop_corba.INotificationsManagementPOA;

/**
 *
 * @author dawish
 */
public class NotificationsManagement implements INotificationsManagementOperations {

    @Override
    public boolean notifyAlert(NotificationDTO objNotification) {
        System.out.println(objNotification.name);
        return true;
    }
    
}
