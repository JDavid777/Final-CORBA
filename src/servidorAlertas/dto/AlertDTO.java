/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dto;

/**
 *
 * @author dawish
 */
public class AlertDTO {
    private int roomNum;
    private String alertDate;
    private String alertHour;
    private int puntuation;

    public AlertDTO() {
    }

    public AlertDTO(int roomNum, String alertDate, String alertHour, int puntuation) {
        this.roomNum = roomNum;
        this.alertDate = alertDate;
        this.alertHour = alertHour;
        this.puntuation = puntuation;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getAlertHour() {
        return alertHour;
    }

    public void setAlertHour(String alertHour) {
        this.alertHour = alertHour;
    }

    public int getPuntuation() {
        return puntuation;
    }

    public void setPuntuation(int puntuation) {
        this.puntuation = puntuation;
    }
    
    
}
