package org.ikaros.neuralnetwork;

interface ISynapse {
    INeuron getOneEnd();

    INeuron getOtherEnd();

    void receive(float val);
}
