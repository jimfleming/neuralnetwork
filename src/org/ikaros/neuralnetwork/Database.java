package org.ikaros.neuralnetwork;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Database {
    private static Map<String, GraphDatabaseService> databases = new HashMap<String, GraphDatabaseService>();
    
    public enum Relationships implements RelationshipType {
        SYNAPSE
    }

    public static GraphDatabaseService getInstance(String path) {
        if (!databases.containsKey(path)) databases.put(path, new EmbeddedGraphDatabase(path));

        return databases.get(path);
    }
}
