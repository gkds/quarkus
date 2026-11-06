package org.kds.repair.service;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection="TheRepairMen")
public class Depanneur extends PanacheMongoEntity {
    public String name;
    public String domaine;
    public Integer rating;
    public String location;
}
