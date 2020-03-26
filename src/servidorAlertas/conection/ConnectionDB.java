/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.conection;

import java.sql.*;

/**
 *
 * @author dawish
 */
public class ConnectionDB {
   private Connection objConnectionDB;    
   private String dbName;
   private String user;
   private String password;
   private String url;
    
   
    public ConnectionDB() {
        objConnectionDB=null;
        dbName="db_alerts";
        user="root";
        password="";
        url = "jdbc:mysql://localhost/"+this.dbName;
   
    }
    public ConnectionDB(String dbName,String user, String password) {
        this.objConnectionDB=null;
        this.dbName=dbName;
        this.user=user;
        this.password=password;
        this.url = "jdbc:mysql://localhost/"+dbName;
    }
    /**Permite hacer la conexion con la base de datos
     * @return 
     */
    public int connect() {
        int bandera=-1;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           //crea una instancia de la controlador de la base de datos
            objConnectionDB = DriverManager.getConnection(url,user,password);
          
            // gnera una conexión con la base de datos
             bandera=1;
        }
        catch(SQLException e){
             System.out.println("Error: " + e.getMessage());
        }catch(ClassNotFoundException e){
             System.out.println("Error class: " + e.getMessage());
        }catch(IllegalAccessException e){
             System.out.println("Error ilegal: " + e.getMessage());
        }catch(InstantiationException e){
             System.out.println("Error instancia: " + e.getMessage());
        }
           
        return bandera;
    }
    /**Cierra la conexion con la base de datos
     *
     */
   public void disconnect(){
       try{
            objConnectionDB.close();
        }

        catch(Exception e){
            System.out.println("Error " + e.getMessage());
        }
   }
     /**Retorna un objeto que almacena la referencia a la conexion con la base de datos
     *
     * @return 
     */
    public Connection getConnection(){
      return this.objConnectionDB;
   }
 
   
    
}
