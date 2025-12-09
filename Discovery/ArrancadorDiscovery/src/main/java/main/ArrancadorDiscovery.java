package main;

import discovery.DiscoveryAssembler;

/**
 *
 * @author pedro
 */
public class ArrancadorDiscovery {

    public static void main(String[] args){
        
        DiscoveryAssembler discovery = DiscoveryAssembler.getInstance();
        discovery.start();
    }
}
