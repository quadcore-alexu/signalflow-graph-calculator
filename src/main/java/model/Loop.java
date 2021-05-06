package model;

import java.util.UUID;

public class Loop extends SubGraph{
    private int id ;
    private final long uniqueId = UUID.randomUUID().getMostSignificantBits();

    public long getUniqueId() {
        return uniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
