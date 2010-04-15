package org.ikaros.neuralnetwork;

import org.neo4j.graphdb.Node;

import processing.core.PApplet;
import traer.physics.Particle;

public class DrawableNeuron extends AbstractNeuron implements Drawable {
    PApplet  applet;
    Particle particle;

    DrawableNeuron(PApplet applet, Particle particle, Node node) {
        super(node);

        this.applet = applet;

        this.particle = particle;
        this.particle.setMass(10);
    }

    Particle getParticle() {
        return particle;
    }

    public void draw() {
        applet.stroke(membranePotential * 100, 0, 0);

        applet.vertex(particle.position().x(),
                     particle.position().y(),
                     particle.position().z());
    }
}
