/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteHabitacion.utilidades;
import clienteHabitacion.vistas.RoomGUI;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidorAlertas.dto.IndicatorsDTO;

/**
 *
 * @author dawish
 */
public class SensorReading extends Thread {


    private RoomGUI ventana;
    private AtomicBoolean parada;

    public SensorReading(RoomGUI ventana) {
        this.ventana = ventana;
        this.parada = new AtomicBoolean(true);
    }
    public void iniciarLectura(){
        this.parada.set(true);
    }

    public void detenerSensores() {
        this.parada.set(false);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        IndicatorsDTO indicadores;
        //TODO CAMBIAR SEMILLA DE RANDOM
        while (parada.get()) {
            double frecuenciaCardiaca =  Math.floor(Math.random() * (120 - 60 + 1) + 60);
            double tensionArterialSistolica = Math.floor(Math.random() * (140 - 70 + 1) + 70);
            double tensionArterialDiastolica =  Math.floor(Math.random() * (90 - 50 + 1) + 50);
            double frecuenciaRespiratoria =  Math.floor(Math.random() * (45 - 12 + 1) + 12);
            double temperatura = Math.floor(Math.random() * (40 - 35 + 1) + 35); //TODO GENERAR CON DECIMALES
            double saturacionOxigeno = 80;
            
            try {
                
                SecureRandom r1 = SecureRandom.getInstance("SHA1PRNG");
                frecuenciaCardiaca = r1.nextInt((120) + 60);
                tensionArterialSistolica = r1.nextInt((140) + 70);
                tensionArterialDiastolica = r1.nextInt((90) + 50);
                frecuenciaRespiratoria = r1.nextInt((45) + 12);
                temperatura = r1.nextInt((40) + 35); //TODO GENERAR CON DECIMALES
                saturacionOxigeno = 80; //TODO revisar*/
                indicadores = new IndicatorsDTO(frecuenciaCardiaca, tensionArterialSistolica, tensionArterialDiastolica, frecuenciaRespiratoria, temperatura, saturacionOxigeno);
                String resultadoLectura = " \nEnviando Indicadores..."
                + "\nFrecuencia cardiaca: " + indicadores.cardiacFrequency
                + "\n Presión arterial sistolica: " + indicadores.systolicBloodPressure
                + "\n Presion arterial diastolica: " + indicadores.diastolicBloodpressure
                + "\n Frecuencia respiratoria: " + indicadores.breathingFrequency
                + "\n Temperatura: " + indicadores.temperature
                + "\n Saturacińn de oxigeno: " + indicadores.oxigenSaturation;
                ;
                ;
                ventana.getjTextNotifiacion().append("\n_________________________________________");
                ventana.getjTextNotifiacion().append(resultadoLectura);
                ventana.getjTextNotifiacion().append("\n_________________________________________");
                
                //  ventana.getServidor().enviarIndicadores(Integer.parseInt(ventana.getTxtHabitacion().getText()), indicadores);
                
                Thread.sleep(8 * 1000);
                
                //} catch (NoSuchAlgorithmException nsae) {
                // Forward to handler
            } catch (InterruptedException ex) {
                Logger.getLogger(RoomGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SensorReading.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
    
