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
import servidorAlertas.dto.PatientDTO;

/**
 *
 * @author dawish
 */
public class PatientDAO implements IPatientDAO {
    private ConnectionDB connectionDB;
        
    public PatientDAO()
    {
        this.connectionDB= new ConnectionDB();
    }

    @Override
    public boolean registerPatient(PatientDTO objPatient) {
        
        this.connectionDB.connect();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "insert into patient(roomID,name,lastname,birthday) values(?,?,?,?)";
            
            sentencia = this.connectionDB.getConnection().prepareStatement(consulta);
           
            sentencia.setInt(1, objPatient.roomNumber);
            sentencia.setString(2, objPatient.name);
            sentencia.setString(3, objPatient.lastname);
            sentencia.setString(4, objPatient.birthday);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            this.connectionDB.disconnect();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }

    @Override
    public PatientDTO findPatient(int roomId) {
        this.connectionDB.connect();
         PatientDTO objPatient= new PatientDTO();
        try {
            PreparedStatement sentence=null;
            String consult="select * from patient where roomnumberPatient="+roomId;
            sentence=this.connectionDB.getConnection().prepareStatement(consult);
            ResultSet reply =sentence.executeQuery();
            if (reply!=null) {
               
            objPatient.roomNumber=(reply.getInt("roomID"));
            objPatient.name=(reply.getString("name"));
            objPatient.setLastname(reply.getString("lastname"));
            objPatient.setBirthday(reply.getString("birthday"));
            }
            sentence.close();
            this.connectionDB.disconnect();
        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return objPatient;
                
    }

    @Override
    public boolean modifyPatient(int roomID, PatientDTO objPatientModified) {
        
        this.connectionDB.connect();
        int reply=0;
        try {
            PreparedStatement sentence=null;
            String consult="update patient set roomID="+objPatientModified.getRoomNumber()+", name="+objPatientModified.getName()+
                    ", lastname="+objPatientModified.getLastname()+", birthday="+objPatientModified.getBirthday()+" where roomID="+roomID+";";
            
            sentence=this.connectionDB.getConnection().prepareStatement(consult);
            reply=sentence.executeUpdate();
            sentence.close();
            this.connectionDB.disconnect();
        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        return reply==1;
    }

    @Override
    public PatientDTO selectPatient(int roomId) {
        this.connectionDB.connect();
        PatientDTO objPatient= new PatientDTO();
        try {
            PreparedStatement sentence=null;
            String consult="select * from patient where roomnumber="+roomId+";";
            sentence=this.connectionDB.getConnection().prepareStatement(consult);
            ResultSet reply= sentence.executeQuery();
            if(reply!=null){
                
               objPatient.roomNumber=(reply.getInt("roomID"));
               objPatient.name=(reply.getString("name"));
               objPatient.setLastname(reply.getString("lastname"));
               objPatient.setBirthday(reply.getString("birthday"));
            }
        }  catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        return objPatient;
    }

    @Override
    public ArrayList<PatientDTO> selectAllPatients() {
         ArrayList<PatientDTO> patients = new ArrayList();
        
        this.connectionDB.connect();
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select * from patient";
            sentencia = this.connectionDB.getConnection().prepareStatement(consulta);            
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
            PatientDTO objPatient= new PatientDTO();
            objPatient.roomNumber=(res.getInt("roomID"));
            objPatient.name=(res.getString("name"));
            objPatient.setLastname(res.getString("lastname"));
            objPatient.setBirthday(res.getString("birthday"));
            patients.add(objPatient);
            }
            sentencia.close();
            this.connectionDB.disconnect();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return patients;
    }
    
}
