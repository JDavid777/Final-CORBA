package servidorNotificaciones.dto;


/**
* servidorNotificaciones/sop_corba/INotificationsManagementPackage/NotificationDTO.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Notifications.idl
* Wednesday, March 25, 2020 1:11:47 PM COT
*/

public final class NotificationDTO implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public String lastname = null;
  public int roomNumber = (int)0;
  public String age = null;
  public String message = null;
  public servidorNotificaciones.dto.IndicatorsDTO indicators = null;

  public NotificationDTO ()
  {
  } // ctor

  public NotificationDTO (String _name, String _lastname, int _roomNumber, String _age, String _message, servidorNotificaciones.dto.IndicatorsDTO _indicators)
  {
    name = _name;
    lastname = _lastname;
    roomNumber = _roomNumber;
    age = _age;
    message = _message;
    indicators = _indicators;
  } // ctor

} // class NotificationDTO
