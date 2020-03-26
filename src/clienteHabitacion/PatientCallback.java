/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteHabitacion;

import servidorAlertas.sop_corba.IPatientCallbackPOA;

/**
 *
 * @author dawish
 */
public class PatientCallback extends IPatientCallbackPOA{

    @Override
    public String notifyAlert(int roomNumber, String message) {
        
        return message;
    }

    
}
