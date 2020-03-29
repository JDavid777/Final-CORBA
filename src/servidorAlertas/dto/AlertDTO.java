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
    private String hour;
    private int puntuation;

    public AlertDTO() {
    }

    public AlertDTO(int roomNum, int puntuation) {
        this.roomNum = roomNum;
        this.alertDate = null;
        this.puntuation = puntuation;
    }

    public AlertDTO(int roomNum, Date alertDate, String hour, int puntuation) {
        this.roomNum = roomNum;
        this.alertDate = alertDate;
        this.hour = hour;
        this.puntuation = puntuation;
    }

  

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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

    @Override
    public String toString() {
        return roomNum + ";" + alertDate + ";" +hour+";"+ puntuation;
    }

}
