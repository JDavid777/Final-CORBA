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
        String previusMgs=this.view.getTxtADataIn().getText();
        this.view.getTxtADataIn().setText(previusMgs+"\n"+buildNotification(message));
        return "Habitacion notificada";
    }
    private String buildNotification(String mgs){
        String[] aux=mgs.split("_");
        aux[1]= aux[1].replace("["," ");
       
        aux[1]= aux[1].replace("]","");//Extraer caracters [ ] de la cadena para mejorar la apariencia del mensaje
        String[] indicators=aux[1].split(",");
        String mgsIndicators="";
        for (String indicator : indicators) {
            mgsIndicators += indicator + "\n"; 
        }
        String notification=aux[0]+"\n El paciente "+"\n"+"El paciente "+this.view.getLblPatientName().getText()+" presenta: "
                +"\n"+mgsIndicators;
        return notification;
    }

    
}
