package servidorAlertas.dto;

import servidorAlertas.sop_corba.IPatientCallback;


/**
* servidorAlertas/sop_corba/IPatientManagementPackage/PatientDTO.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from PatientManagement.idl
* Thursday, March 26, 2020 4:29:02 PM COT
*/

public final class PatientDTO implements org.omg.CORBA.portable.IDLEntity
{
  public String name ;
  public String lastname;
  public int roomNumber;
  public String birthday ;
  public servidorAlertas.dto.IndicatorsDTO indicators;
  public servidorAlertas.sop_corba.IPatientCallback patientClbk;

  public PatientDTO ()
  {
  } // ctor

  public PatientDTO (String _name, String _lastname, int _roomNumber, String _birthday, servidorAlertas.dto.IndicatorsDTO _indicators, servidorAlertas.sop_corba.IPatientCallback _patientClbk)
  {
    name = _name;
    lastname = _lastname;
    roomNumber = _roomNumber;
    birthday = _birthday;
    indicators = _indicators;
    patientClbk = _patientClbk;
  } // ctor
 public PatientDTO (String _name, String _lastname, int _roomNumber, String _birthday)
  {
    name = _name;
    lastname = _lastname;
    roomNumber = _roomNumber;
    birthday = _birthday;
    indicators = new IndicatorsDTO();
    patientClbk =null;
  } // c
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public IndicatorsDTO getIndicators() {
        return indicators;
    }

    public void setIndicators(IndicatorsDTO indicators) {
        this.indicators = indicators;
    }

    public IPatientCallback getPatientClbk() {
        return patientClbk;
    }

    public void setPatientClbk(IPatientCallback patientClbk) {
        this.patientClbk = patientClbk;
    }

} // class PatientDTO
