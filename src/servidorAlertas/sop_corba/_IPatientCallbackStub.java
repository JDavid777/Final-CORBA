package servidorAlertas.sop_corba;


/**
* servidorAlertas/sop_corba/_IPatientCallbackStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from PatientManagement.idl
* Wednesday, March 25, 2020 6:31:50 PM COT
*/

public class _IPatientCallbackStub extends org.omg.CORBA.portable.ObjectImpl implements servidorAlertas.sop_corba.IPatientCallback
{

  public String notifyAlert (int roomNumber, String message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("notifyAlert", true);
                $out.write_long (roomNumber);
                $out.write_string (message);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return notifyAlert (roomNumber, message        );
            } finally {
                _releaseReply ($in);
            }
  } // notifyAlert

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/IPatientCallback:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _IPatientCallbackStub
