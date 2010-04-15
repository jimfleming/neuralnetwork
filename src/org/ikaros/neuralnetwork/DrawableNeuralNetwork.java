package org.ikaros.neuralnetwork;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import processing.core.PApplet;
import traer.physics.Particle;
import traer.physics.ParticleSystem;

public class DrawableNeuralNetwork extends AbstractNeuralNetwork implements Drawable {
    ParticleSystem particleSystem;
    PApplet        applet;

    public DrawableNeuralNetwork(PApplet applet) {
        super();

        particleSystem = new ParticleSystem();
        // particleSystem.setIntegrator(ParticleSystem.MODIFIED_EULER);
        // particleSystem.setDrag(2);

        this.applet = applet;

        reset();
        load();

        Database.getInstance("var/db").shutdown();
    }

    public void draw() {
        particleSystem.tick();

        applet.noFill();
        applet.strokeWeight(2);

        applet.stroke(0, 0, 255, 32);

        applet.beginShape(PApplet.LINES);
        for (ISynapse synapse : synapses)
            ((DrawableSynapse) synapse).draw();
        applet.endShape();

        applet.beginShape(PApplet.POINTS);
        for (INeuron neuron : neurons)
            ((DrawableNeuron) neuron).draw();
        applet.endShape();

        applet.noStroke();
        applet.fill(0, 0, 255, 10);
    }
    
    public void reset() {
        Transaction transaction = Database.getInstance("var/db").beginTx();

        try {
            for (Node node : Database.getInstance("var/db").getAllNodes()) {
                for (Relationship relationship : node.getRelationships())
                    relationship.delete();

                node.delete();
            }

            transaction.success();
        } finally {
            transaction.finish();
        }
    }

    public void load() {
        Transaction tx = Database.getInstance("var/db").beginTx();

        Iterable<Node> nodes;

        try {
            // Build the nodes
            for (int i = 0; i < 500; i++) {
                Database.getInstance("var/db").createNode();
            }

            // Build the relationships (random)
            for (Node nodeA : Database.getInstance("var/db").getAllNodes()) {
                for (Node nodeB : Database.getInstance("var/db").getAllNodes()) {
                    if (nodeA.getId() != nodeB.getId() && Math.random() < 0.01) nodeA.createRelationshipTo(nodeB,
                                                                                                           Database.Relationships.SYNAPSE);
                }
            }

            // Get all nodes
            nodes = Database.getInstance("var/db").getAllNodes();

            // Build the neurons from nodes
            for (Node node : nodes) {
                Particle particle = particleSystem.makeParticle();

                for (int i = 0; i < particleSystem.numberOfParticles(); i++) {
                    Particle temp = particleSystem.getParticle(i);

                    if (particle != temp) particleSystem.makeAttraction(particle,
                                                                        temp,
                                                                        -1000,
                                                                        20);
                }

                particle.position().set(applet.random(1),
                                        applet.random(1),
                                        applet.random(1));

                DrawableNeuron neuron = new DrawableNeuron(applet,
                                                           particle,
                                                           node);
                neurons.add(neuron);
            }

            // Get the relationships for the nodes
            for (INeuron neuronA : neurons) {
                // Build synapses from relationships
                for (Relationship relationship : ((DrawableNeuron) neuronA).node.getRelationships(Database.Relationships.SYNAPSE,
                                                                                                  Direction.OUTGOING)) {
                    for (INeuron neuronB : neurons) {
                        if (((DrawableNeuron) neuronB).node.getId() == relationship.getEndNode().getId()) {
                            DrawableSynapse synapse = new DrawableSynapse(applet,
                                                                          particleSystem.makeSpring(((DrawableNeuron) neuronA).getParticle(),
                                                                                                    ((DrawableNeuron) neuronB).getParticle(),
                                                                                                    0.2f,
                                                                                                    0.2f,
                                                                                                    20),
                                                                          (DrawableNeuron) neuronA,
                                                                          (DrawableNeuron) neuronB,
                                                                          relationship);
                            ((AbstractNeuron) neuronA).dendrites.add(synapse);
                            synapses.add(synapse);
                        }
                    }
                }
            }

            tx.success();
        } finally {
            tx.finish();
        }
    }
}
