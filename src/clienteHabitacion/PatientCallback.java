/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteHabitacion;

import clienteHabitacion.vistas.RoomGUI;
import servidorAlertas.sop_corba.IPatientCallbackPOA;

/**
 *
 * @author dawish
 */
public class PatientCallback extends IPatientCallbackPOA{

    private RoomGUI view;
    public PatientCallback(RoomGUI view) {
        this.view=view;
    }
    

    
    
    @Override
    public String notifyAlert(int roomNumber, String message) {
        this.view.getTxtADataIn().setText(message);
        return "Habitacion notificada";
    }

    
}
