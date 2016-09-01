/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tui;

import controller.FacadeControllerClient;
import controller.InterfaceClient;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.asList;

/**
 *
 * @author riccardoz
 */
public class StartTuiClient {

    public static void main(String[] args) throws IOException {
        InterfaceClient clientBlueAirline = new FacadeControllerClient();
        if (clientBlueAirline.connect(MethodsControlClient.scannerInput(new ArrayList<>(asList("PUT IP SERVER"))).get(0))) {
            ControllerTxt r1 = new ControllerTxt(clientBlueAirline);
            Thread nuovoThread1 = new Thread(r1);
            nuovoThread1.start();
        }
        else{
            System.out.println("ERRORE CONNESSIONE SERVER NON RIUSCITA");
        }        
    }
}
