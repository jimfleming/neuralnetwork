package org.ikaros.neuralnetwork;

import java.util.ArrayList;

interface INeuralNetwork {
    ArrayList<INeuron> getNeurons();

    ArrayList<ISynapse> getSynapses();
}
