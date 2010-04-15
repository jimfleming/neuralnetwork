package org.ikaros.neuralnetwork;

import java.util.ArrayList;

abstract class AbstractNeuralNetwork implements INeuralNetwork {
    protected final ArrayList<INeuron>  neurons;
    protected final ArrayList<ISynapse> synapses;

    AbstractNeuralNetwork() {
        neurons = new ArrayList<INeuron>();
        synapses = new ArrayList<ISynapse>();
    }

    public ArrayList<INeuron> getNeurons() {
        return neurons;
    }

    public ArrayList<ISynapse> getSynapses() {
        return synapses;
    }

    void run() {
        for (INeuron neuron : neurons)
            neuron.test();
    
        for (INeuron neuron : neurons)
            neuron.send();
    }
}
