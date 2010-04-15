package org.ikaros.neuralnetwork;

import org.neo4j.graphdb.Relationship;

import processing.core.PApplet;
import traer.physics.Spring;

public class DrawableSynapse extends AbstractSynapse implements Drawable {
    PApplet applet;
    Spring  spring;

    public DrawableSynapse(PApplet applet,
                           Spring spring,
                           DrawableNeuron one_end,
                           DrawableNeuron other_end,
                           Relationship relationship) {
        super(one_end, other_end, relationship);

        this.applet = applet;
        this.spring = spring;
    }

    public void draw() {
        applet.vertex(((DrawableNeuron) oneEnd).getParticle().position().x(),
                      ((DrawableNeuron) oneEnd).getParticle().position().y(),
                      ((DrawableNeuron) oneEnd).getParticle().position().z());
        applet.vertex(((DrawableNeuron) otherEnd).getParticle().position().x(),
                      ((DrawableNeuron) otherEnd).getParticle().position().y(),
                      ((DrawableNeuron) otherEnd).getParticle().position().z());
    }
}
