/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_discovery;

import com.google.gson.JsonObject;

/**
 *
 * @author Alici
 */
public class ManejadorDesconexionPeer extends ManejadorMensajesSalida {

    @Override
    public void procesar(JsonObject json) {
        //TODO: David pon aquí la lógica para hacer un broadcast que envíe el evento
        //Si quieres checa como se hace en el Peer con el ManejadorBroadcast
        //Nomas no tendría lo del TipoMensaje, solo el como manda el mensaje.
        //O checa como el ManejadorNuevoPeer (de aquí del discovery) hace el broadcast.
        System.out.println("Todavía no se envían los eventos de desconexión");
    }

}
