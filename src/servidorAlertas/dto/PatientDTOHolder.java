package servidorAlertas.dto;

/**
* servidorAlertas/sop_corba/IPatientManagementPackage/PatientDTOHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from PatientManagement.idl
* Thursday, March 26, 2020 4:29:02 PM COT
*/

public final class PatientDTOHolder implements org.omg.CORBA.portable.Streamable
{
  public servidorAlertas.dto.PatientDTO value;

  public PatientDTOHolder ()
  {
  }

  public PatientDTOHolder (servidorAlertas.dto.PatientDTO initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = servidorAlertas.dto.PatientDTOHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
        servidorAlertas.dto.PatientDTOHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return servidorAlertas.dto.PatientDTOHelper.type ();
  }

}
