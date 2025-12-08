package managers; 

import interfaces.IPeer;
//eventocartacantada import eventos.EventoCartaCantada;

public class CantadorManager {

    private IPeer peer;
    private boolean cantando = false;
    private Thread hiloCantador;

    // Se llama desde la Facade al iniciar el programa
    public void inicializar(IPeer peer) {
        this.peer = peer;
    }

    /**
     * Inicia el ciclo de cantar cartas 
     * @param intervalo Tiempo en milisegundos entre cartas
     */
    public void iniciarCanto(long intervalo) {
        if (cantando) return; // Si ya está corriendo, no hace nada
        
        this.cantando = true;
        System.out.println(">>> [CANTADOR] ¡Soy el Host! Iniciando hilo de cartas...");

        hiloCantador = new Thread(() -> {
            try {
                while (cantando) {
                    // 1. Esperar el tiempo definido
                    Thread.sleep(intervalo);
                    
                    // 2. Aquí va la lógica real de seleccionar carta
                    System.out.println(">>> [CANTADOR] Enviando carta a la red...");
                    
                    /* DESCOMENTAR CUANDO TENGAS TU CLASE DE CARTA:
                    EventoCartaCantada evento = new EventoCartaCantada("CartaX");
                    if (peer != null) {
                        peer.enviarObjeto(evento);
                    }
                    */
                }
            } catch (InterruptedException e) {
                System.out.println(">>> [CANTADOR] Hilo interrumpido.");
            }
        });
        
        hiloCantador.start();
    }

    public void detenerCanto() {
        this.cantando = false;
        if (hiloCantador != null) {
            hiloCantador.interrupt();
        }
        System.out.println(">>> [CANTADOR] Detenido.");
    }

    public boolean isCantando() {
        return cantando;
    }
}