/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorNotificaciones;

import servidorNotificaciones.dto.NotificationDTO;
import servidorNotificaciones.sop_corba.INotificationsManagementOperations;
import servidorNotificaciones.sop_corba.INotificationsManagementPOA;
import servidorNotificaciones.vistas.NotificationsGUI;

/**
 *
 * @author dawish
 */
public class NotificationsManagement implements INotificationsManagementOperations {
    NotificationsGUI view = new NotificationsGUI();
    
    @Override
    public boolean notifyAlert(NotificationDTO objNotification) {
         this.view.setVisible(true);
        this.view.getLblNombApell().setText(objNotification.nameComplete);
        this.view.getLblNoHabitacion().setText(String.valueOf(objNotification.roomNumber));
        this.view.getLblEdad().setText(objNotification.age);
        this.view.getLblHora().setText(objNotification.hour);
        this.view.getLblFecha().setText(objNotification.date);
        this.view.getLblMensaje2().setText(objNotification.message);
       
        for (int i = 0; i < objNotification.indicators.length; i++) {
            
            String[] data=objNotification.indicators[i].split("-");
           this.view.getTblIndicadoresCausantes().setValueAt(data[0], i, 0);
           this.view.getTblIndicadoresCausantes().setValueAt(data[1], i, 1);
        }
  
        if (objNotification.lastFiveAlerts != null) {
             System.out.println(objNotification.lastFiveAlerts.length);
            for (int i = 0; i < 5; i++) {
               // System.out.println(objNotification.lastFiveAlerts[i]);
//                String[] datos = objNotification.lastFiveAlerts[i].split(",");
//                this.view.getTblUltimasAlertas().setValueAt(datos[0], i, 0);
//                this.view.getTblUltimasAlertas().setValueAt(datos[1], i, 1);
//                this.view.getTblUltimasAlertas().setValueAt(datos[2], i, 2);
            }
       
    }
        
      return true;
    }
}
    
