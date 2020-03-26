/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;
import servidorAlertas.dto.PatientDTO;

/**
 *
 * @author dawish
 */
public interface IPatientDAO {
    
    public boolean registerPatient(PatientDTO objPatient);   
    public PatientDTO findPatient(int roomId);
    public boolean modifyPatient(int roomID,PatientDTO objPatientModified);
    public PatientDTO selectPatient(int roomId);
    public ArrayList<PatientDTO> selectAllPatients();
    
}
