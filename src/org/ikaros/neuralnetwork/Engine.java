package org.ikaros.neuralnetwork;

import peasy.PeasyCam;
import processing.core.PApplet;

//TODO: Load JSON file into network
// Build or add on to existing network?

// TODO: Network should be more closed, ideally:
// INeuralNetwork brain = new Neo4jNeuralNetwork();
// brain.load("var/db");
// brain.setInputs();
// brain.setOutputs();
// brain.run();

// TODO: Bindings to input/output (adapter pattern)? Neuron doesn't need to know what it's sending it's signal to, only where

// Example:
    // Create 3 neurons:
    // Link the first to the second and third
    // Link the second to the first and third
    // Link the third to the first and second
    // {
        // [[1, 2], [0, 2], [0, 1]]
    // }

public class Engine extends PApplet {
    private static final long serialVersionUID = 1L;

    DrawableNeuralNetwork     neuralNetwork;
    PeasyCam                  cam;

    @Override
    public void setup() {
        size(1280, 800, OPENGL);
        hint(DISABLE_OPENGL_2X_SMOOTH);
        hint(DISABLE_DEPTH_TEST);
        hint(DISABLE_DEPTH_SORT);
        
        cam = new PeasyCam(this, 400);

        neuralNetwork = new DrawableNeuralNetwork(this);
    }

    @Override
    public void draw() {
        background(255);

        neuralNetwork.getNeurons().get(50).receive(1);
        neuralNetwork.run();
        neuralNetwork.draw();
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "org.ikaros.neuralnetwork.Engine" });
    }
}
