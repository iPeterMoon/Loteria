package main;

import discovery.Discovery;

/**
 *
 * @author pedro
 */
public class Arrancador {

    public static void main(String[] args){
        
        Discovery discovery = Discovery.getInstance();
        discovery.start();
    }
}
