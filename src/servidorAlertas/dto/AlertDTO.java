/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dto;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author dawish
 */
public class AlertDTO {

    private int roomNum;
    private Date alertDate;
    private int puntuation;

    public AlertDTO() {
    }

    public AlertDTO(int roomNum, int puntuation) {
        this.roomNum = roomNum;
        this.alertDate = null;
        this.puntuation = puntuation;
    }

    public AlertDTO(String toString, String toString0, String toString1, String toString2, String toString3, String toString4, String toString5, ArrayList<String> indicadoresAlerta, ArrayList<String> ultimasCincoAlertas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    public int getPuntuation() {
        return puntuation;
    }

    public void setPuntuation(int puntuation) {
        this.puntuation = puntuation;
    }

}
