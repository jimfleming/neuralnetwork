package org.ikaros.neuralnetwork;

import org.neo4j.graphdb.Relationship;

abstract class AbstractSynapse implements ISynapse {
    protected final Relationship relationship;

    protected final INeuron      oneEnd;
    protected final INeuron      otherEnd;

    AbstractSynapse(INeuron one_end,
                    INeuron other_end,
                    Relationship relationship) {
        this.relationship = relationship;

        this.oneEnd = one_end;
        this.otherEnd = other_end;
    }
    
    public Relationship getRelationship() {
        return relationship;
    }

    public INeuron getOneEnd() {
        return oneEnd;
    }

    public INeuron getOtherEnd() {
        return otherEnd;
    }

    public void receive(float val) {
        // TODO: perform any modulation here
        
        otherEnd.receive(val);
    }
}
