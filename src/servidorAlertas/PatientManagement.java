/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import servidorAlertas.dao.AlertDAO;
import servidorAlertas.dao.PatientDAO;
import servidorAlertas.dto.AlertDTO;
import servidorAlertas.dto.IndicatorsDTO;
import servidorAlertas.dto.PatientDTO;
import servidorAlertas.dto.PatientDTOHolder;
import servidorAlertas.sop_corba.IPatientCallback;
import servidorAlertas.sop_corba.IPatientManagementOperations;
import servidorNotificaciones.dto.NotificationDTO;
import servidorNotificaciones.sop_corba.INotificationsManagementHelper;
import servidorNotificaciones.sop_corba.INotificationsManagementOperations;

/**
 *
 * @author dawish
 */
public class PatientManagement implements IPatientManagementOperations {

    private INotificationsManagementOperations refNotifiacations;

    private PatientDAO patientDAO;
    private ArrayList<String> indicadoresAlerta;
    private ArrayList<String> ultimasCincoAlertas;
    private AlertDAO alertDAO;
    Hashtable<Integer, IPatientCallback> diccionario = new Hashtable<Integer, IPatientCallback>();

    private void initDAO() {
        alertDAO = new AlertDAO();
        patientDAO = new PatientDAO();
    }
    @Override
    public boolean registerPatient(PatientDTO objPatient) {
        this.initDAO();
        boolean flag = false;
        if (objPatient != null) {
            if (this.patientDAO.registerPatient(objPatient)) {
                System.out.println("Registrando paciente...");
                flag = true;

            }
            if (diccionario.containsKey(objPatient.getRoomNumber())) {
                objPatient.getPatientClbk().notifyAlert(objPatient.getRoomNumber(), "Paciente ya registrado");
                flag = false;

            } else {
                diccionario.put(objPatient.getRoomNumber(), objPatient.getPatientClbk());
                objPatient.getPatientClbk().notifyAlert(objPatient.getRoomNumber(), "Paciente registrado");
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean findPatient(int roomId, PatientDTOHolder objPatient) {
        System.out.println("Buscando paciente...");
        this.initDAO();
        if (objPatient != null) {
            PatientDTO patient = this.patientDAO.findPatient(roomId);
            patient.setPatientClbk(diccionario.get(roomId));
            patient.setAge("");
            patient.setIndicators(new IndicatorsDTO());
            objPatient.value = patient;

            return true;
        }
        return false;
    }

    @Override
    public boolean modifyPatient(int roomId, PatientDTO objPatientModified) {
        this.initDAO();
        boolean flag = false;
        if (objPatientModified != null) {
            this.patientDAO.modifyPatient(objPatientModified.roomNumber, objPatientModified);
            System.out.println("Modificando paciente...");
            flag = true;
        }
        if (diccionario.containsKey(objPatientModified.getRoomNumber())) {
            objPatientModified.getPatientClbk().notifyAlert(objPatientModified.getRoomNumber(), "Paciente ya registrado");
            flag = false;

        } else {
            diccionario.put(objPatientModified.getRoomNumber(), objPatientModified.getPatientClbk());
            objPatientModified.getPatientClbk().notifyAlert(objPatientModified.getRoomNumber(), "Paciente registrado");
            flag = true;
        }
        return flag;
    }

    @Override
    public String[] selectAllPatients() {
        this.initDAO();
        System.out.println("Consultando lista de pacientes...");
        ArrayList<PatientDTO> patients = this.patientDAO.selectAllPatients();
        String[] patientsList = new String[patients.size()];
        for (int i = 0; i < patients.size(); i++) {
            patientsList[i] = patients.get(i).toString();

        }

        return patientsList;
    }

 @Override
    public boolean sendIndicators(int roomId, IndicatorsDTO listIndicators,IPatientCallback callback) {
        this.initDAO();
        PatientDTO patient = this.patientDAO.findPatient(roomId);
        if (!diccionario.containsKey(patient.getRoomNumber())) {
            diccionario.put(roomId, callback);
        }
        patient.setPatientClbk(diccionario.get(roomId));
        patient.setIndicators(listIndicators);
        int puntuacion = gestionarIndicadores(patient);

        if (puntuacion <= 1) {
            avisarHabitacion(roomId, "Monitorizacion con normalidad . . .");
        } else if (puntuacion == 2) {

            //MEJORAR CODIGO COCHINO!!!
            System.out.println("GENERANDO ALERTA");
             avisarHabitacion(roomId, "¡ALERTA! LLAMAR A LA ENFERMERA"+"_"+indicadoresAlerta.toString());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hour = dateFormat.format(date);
  
  
            java.util.Date d = new java.util.Date();
            java.sql.Date actualDate = new java.sql.Date(d.getTime());
            
            AlertDTO alert = new AlertDTO(patient.getRoomNumber(),actualDate,hour,puntuacion);
            alertDAO.registerAlert(alert);
            ArrayList alerta = construirMensajeDeAlerta(patient, alert,"ENFERMERA NECESARIA PARA ATENDER EMERGENCIA");
            avisarServidorNotificaciones(alerta);

        } else if (puntuacion >= 3) {
            System.out.println("GENERANDO ALERTA");
           avisarHabitacion(roomId, "¡ALERTA! LLAMAR A LA ENFERMERA Y AL MEDICO"+"_"+indicadoresAlerta.toString());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hour = dateFormat.format(date);
  
  
            java.util.Date d = new java.util.Date();
            java.sql.Date actualDate = new java.sql.Date(d.getTime());
            AlertDTO alert = new AlertDTO(patient.getRoomNumber(),actualDate,hour,puntuacion);
            alertDAO.registerAlert(alert);
            ArrayList alerta = construirMensajeDeAlerta(patient, alert,"MEDICO Y ENFERMERA NECESARIOS PARA ATENDER EMERGENCIA");
            avisarServidorNotificaciones(alerta);
        }
        return true;
    }

    public void getRefRemoteNotificationserver(String IpNS, String portNS) {
        try {
            String[] vec = new String[4];
            vec[0] = "-ORBInitialHost";
            vec[1] = IpNS;
            vec[2] = "-ORBInitialPort";
            vec[3] = portNS;

            ORB orb = ORB.init(vec, null);

            org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objref);

            String name = "objNotificaciones";
            refNotifiacations = INotificationsManagementHelper.narrow(ncRef.resolve_str(name));

        } catch (InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound e) {
            System.out.println("Error:" + e);
        }
    }

      private ArrayList construirMensajeDeAlerta(PatientDTO patient,AlertDTO alert,String msgInfo) {

        ArrayList alertMsg = new ArrayList();
        alertMsg.add(patient.getRoomNumber());
        alertMsg.add(patient.getName());
        alertMsg.add(patient.getLastname());
        alertMsg.add(patient.getAge());//TODO :Cuadrar edad

        alertMsg.add(alert.getHour());
        alertMsg.add(alert.getAlertDate());
        alertMsg.add(msgInfo);
        alertMsg.add(indicadoresAlerta);
        getLastAlerts(patient.getRoomNumber());
        alertMsg.add(ultimasCincoAlertas);

        return alertMsg;

    }

    private void getLastAlerts(int numHabitacion) {
        ultimasCincoAlertas = new ArrayList<>();
        ArrayList<AlertDTO> list = alertDAO.selectAlerts(numHabitacion, 5);
        for (int i = 0; i < list.size(); i++) {
            this.ultimasCincoAlertas.add(list.get(i).toString());
        }
    }

    public int gestionarIndicadores(PatientDTO patient) {
        this.indicadoresAlerta = new ArrayList();
        int puntuation = 0;
        switch (grupoAlQuePertenece(patient)) {
            case 1:
                puntuation = frecuenciaCardiaca(120, 140, puntuation, patient);
                puntuation = frecuenciaRespiratoria(40, 45, puntuation, patient);
                puntuation = temperatura(38, 38, puntuation, patient);
                puntuation = arterialSistolica(70, 100, puntuation, patient);
                puntuation = arterialDiastolica(50, 68, puntuation, patient);
                break;

            case 2:
                puntuation = frecuenciaCardiaca(100, 130, puntuation, patient);
                puntuation = frecuenciaRespiratoria(20, 30, puntuation, patient);
                puntuation = temperatura((float) 37.5, (float) 37.8, puntuation, patient);
                puntuation = arterialSistolica(84, 106, puntuation, patient);
                puntuation = arterialDiastolica(56, 70, puntuation, patient);
                break;

            case 3:
                puntuation = frecuenciaCardiaca(100, 120, puntuation, patient);
                puntuation = frecuenciaRespiratoria(20, 30, puntuation, patient);
                puntuation = temperatura((float) 37.5, (float) 37.8, puntuation, patient);
                puntuation = arterialSistolica(98, 106, puntuation, patient);
                puntuation = arterialDiastolica(56, 70, puntuation, patient);
                break;

            case 4:
                puntuation = frecuenciaCardiaca(80, 120, puntuation, patient);
                puntuation = frecuenciaRespiratoria(20, 30, puntuation, patient);
                puntuation = temperatura((float) 37.5, (float) 37.8, puntuation, patient);
                puntuation = arterialSistolica(99, 112, puntuation, patient);
                puntuation = arterialDiastolica(64, 70, puntuation, patient);
                break;

            case 5:
                puntuation = frecuenciaCardiaca(80, 100, puntuation, patient);
                puntuation = frecuenciaRespiratoria(12, 20, puntuation, patient);
                puntuation = temperatura(37, (float) 37.5, puntuation, patient);
                puntuation = arterialSistolica(104, 124, puntuation, patient);
                puntuation = arterialDiastolica(64, 86, puntuation, patient);
                break;

            case 6:
                puntuation = frecuenciaCardiaca(70, 80, puntuation, patient);
                puntuation = frecuenciaRespiratoria(12, 20, puntuation, patient);
                puntuation = temperatura(37, 37, puntuation, patient);
                puntuation = arterialSistolica(118, 132, puntuation, patient);
                puntuation = arterialDiastolica(70, 82, puntuation, patient);
                break;

            case 7:
                puntuation = frecuenciaCardiaca(60, 80, puntuation, patient);
                puntuation = frecuenciaRespiratoria(12, 20, puntuation, patient);
                puntuation = temperatura((float) 36.2, (float) 37.2, puntuation, patient);
                puntuation = arterialSistolica(110, 140, puntuation, patient);
                puntuation = arterialDiastolica(70, 90, puntuation, patient);
                break;

            default:
                break;

        }
        return puntuation;
    }

    private void avisarServidorNotificaciones(ArrayList alerta) {
        System.out.println("Notificando Al servidor de notificaciones");
        int room = Integer.parseInt(alerta.get(0).toString());
        String nameComplet = alerta.get(1).toString()+alerta.get(2).toString();
        String age = alerta.get(3).toString();
        String hour = alerta.get(4).toString();
        String date = alerta.get(5).toString();
        String mgs = alerta.get(6).toString();
        String auxIndicators=alerta.get(7).toString();
        auxIndicators=auxIndicators.replace("[", "");
        auxIndicators=auxIndicators.replace("]", "");
        String[] indicators = auxIndicators.split(",");
        
        String auxFiveAlerts = alerta.get(8).toString();
        System.out.println(auxFiveAlerts);
        auxFiveAlerts=auxFiveAlerts.replace("[", "");
        auxFiveAlerts=auxFiveAlerts.replace("]", "");
        String[] lastFiveAlerts=auxFiveAlerts.split(",");
        NotificationDTO objAlerta = new NotificationDTO( room,nameComplet,age,hour, date,mgs,indicators,lastFiveAlerts);
         this.refNotifiacations.notifyAlert(objAlerta);
    }
    private void avisarHabitacion(int numeroHabitacion, String Mensaje) {
        try {
            System.out.println("Avisando a la habitacion");
                
            PatientDTO patient = this.patientDAO.findPatient(numeroHabitacion);
            patient.setPatientClbk(diccionario.get(numeroHabitacion));
            System.out.println(patient.patientClbk.notifyAlert(numeroHabitacion, Mensaje));
          
        } catch (Exception e) {
            System.out.println("No se pudo notificar al paciente " + e);
        }

    }

    private int grupoAlQuePertenece(PatientDTO patient) {

        String[] partes = calcularEdadPaciente(patient.getBirthday());
        // AÑOS POS 0---MESES POS 1----SEMANAS POS 2----DIAS POS 3

        int anios = Integer.parseInt(partes[0]);
        int meses = Integer.parseInt(partes[1]);
        int semanas = Integer.parseInt(partes[2]);
        int dias = Integer.parseInt(partes[3]);

        if (anios == 0 && meses == 0 && semanas <= 6) {
            if (semanas == 0) {
                patient.setAge(dias + " Dias");
            } else {
                patient.setAge(semanas + " Semanas con " + dias + " Dias");
            }

            return 1;
        } // 1 año
        else if (anios <= 1) {
            if (meses == 0) {
                patient.setAge(semanas + " Semanas con " + dias + " Dias");
            } else {
                patient.setAge(meses + " Meses " + semanas + " Semanas " + dias + " Dias");

            }
            return 2;
        } // 2 años
        else if (anios <= 2) {
            patient.setAge(anios + " Años");
            return 3;
        } // 6 años
        else if (anios <= 6) {
            patient.setAge(anios + " Años");
            return 4;
        } //13 años
        else if (anios <= 13) {
            patient.setAge(anios + " Años");
            return 5;
        } // 16 años
        else if (anios <= 16) {
            patient.setAge(anios + " Años");
            return 6;
        } else {

            patient.setAge(anios + " Años");
            return 7;
        }

    }

    private String[] calcularEdadPaciente(String fecha) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fecha, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        int semanas = periodo.getDays() / 7;
        int dias = periodo.getDays() % 7;
        String[] edad = {Integer.toString(periodo.getYears()),
            Integer.toString(periodo.getMonths()),
            Integer.toString(semanas),
            Integer.toString(dias)};
        return edad;
    }

    private int frecuenciaCardiaca(float minimo, float maximo, int puntuacion, PatientDTO paciente) {
        double valor = paciente.getIndicators().getCardiacFrequency();
        int p = puntuacion;
        if (valor < minimo || valor > maximo) {
            indicadoresAlerta.add("Frecuencia Cardiaca-" + valor);
            p++;
        }
        return p;
    }

    private int frecuenciaRespiratoria(float minimo, float maximo, int puntuacion, PatientDTO paciente) {
        double valor = paciente.getIndicators().getBreathingFrequency();
        int p = puntuacion;
        if (valor < minimo || valor > maximo) {
            indicadoresAlerta.add("Frecuencia respiratoria-" + valor);
            p++;
        }
        return p;
    }

    private int temperatura(float minimo, float maximo, int puntuacion, PatientDTO paciente) {
        double valor = paciente.getIndicators().getTemperature();
        int p = puntuacion;
        if (valor < minimo || valor > maximo) {
            indicadoresAlerta.add("Temperatura-" + valor);
            p++;
        }
        return p;
    }

    private int arterialSistolica(float minimo, float maximo, int puntuacion, PatientDTO paciente) {
        double valor = paciente.getIndicators().getSystolicBloodPressure();
        int p = puntuacion;
        if (valor < minimo || valor > maximo) {
            indicadoresAlerta.add("Arterial Sistolica-" + valor);
            p++;
        }
        return p;
    }

    private int arterialDiastolica(float minimo, float maximo, int puntuacion, PatientDTO patient) {
        double valor = patient.getIndicators().getDiastolicBloodpressure();
        int p = puntuacion;
        if (valor < minimo || valor > maximo) {
            indicadoresAlerta.add("Arterial Diastolica-" + valor);
            p++;
        }
        return p;
    }

}
