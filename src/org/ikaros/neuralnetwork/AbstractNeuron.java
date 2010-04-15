package org.ikaros.neuralnetwork;

import java.util.ArrayList;

import org.neo4j.graphdb.Node;

abstract class AbstractNeuron implements INeuron {
    protected final Node                node;
    protected final ArrayList<ISynapse> dendrites = new ArrayList<ISynapse>();

    protected float                     membranePotential      = 0.0f;
    protected float                     actionPotential     = 0.0f;

    AbstractNeuron(Node node) {
        this.node = node;
    }
    
    public Node getNode() {
        return node;
    }

    public void receive(float val) {
        membranePotential += val;
    }

    public void test() {
        if (membranePotential >= 1.0f)
            actionPotential = 1.0f; // neurons are either/or (on/off) should not pass on inputBuffer value but a new value

        membranePotential = 0;
    }

    public void send() {
        // TODO: neurons encode amplitude in frequency
            // receives 10.0
            // | | | | | 
            // receives 10.0 
            // | | | | | | | | | | | | | | |
        // TODO: neurons send signals as spikes _/\_
        
        for (ISynapse synapse : dendrites)
            synapse.receive(actionPotential);

        actionPotential = 0;
    }
}
