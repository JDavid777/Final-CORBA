/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import servidorAlertas.conection.ConnectionDB;
import servidorAlertas.dto.AlertDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.dto.PatientDTOHolder;

/**
 *
 * @author dawish
 */
public class AlertDAO implements IAlertDAO {

    private final ConnectionDB connectionDB;

    public AlertDAO() {
        this.connectionDB = new ConnectionDB();
    }

    @Override
    public boolean registerAlert(AlertDTO objAlert) {
        this.connectionDB.connect();
        int resultado = -1;
        try {
            PreparedStatement sentencia = null;
            String consulta = "insert INTO alerts values(?,?,?,?)";

            sentencia = this.connectionDB.getConnection().prepareStatement(consulta);

            sentencia.setInt(1, objAlert.getRoomNum());
            sentencia.setDate(2,objAlert.getAlertDate());
            sentencia.setString(3, objAlert.getHour());
            sentencia.setInt(4, objAlert.getPuntuation());
            resultado = sentencia.executeUpdate();
            sentencia.close();
            this.connectionDB.disconnect();

        } catch (SQLException e) {
            System.out.println("error en la inserción: " + e.getMessage());
        }

        return resultado == 1;
    }

    @Override
    public ArrayList<AlertDTO> selectAlerts(int roomid,int numAlerts) {
        ArrayList<AlertDTO> alerts = new ArrayList();

        this.connectionDB.connect();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from alerts where roomIDAlert=? order by date desc limit ?";
            sentencia = this.connectionDB.getConnection().prepareStatement(consulta);
            sentencia.setInt(1,roomid);
            sentencia.setInt(2, numAlerts);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                AlertDTO objAlert = new AlertDTO();
                objAlert.setRoomNum(res.getInt("roomIDAlert"));
                objAlert.setAlertDate(res.getDate("date"));
                objAlert.setHour(res.getString("hour"));
                objAlert.setPuntuation(res.getInt("score"));
                System.out.println(objAlert.toString());
                alerts.add(objAlert);
            }
            sentencia.close();
            this.connectionDB.disconnect();

        } catch (SQLException e) {
            System.out.println("error Consulta: " + e.getMessage());
        }

        return alerts;
    }

}
