package com.bogusnazar.intime.Models;

;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


public class Meet {


    private String id;
    private String name;
    private String creatorID;
    private Timestamp createDate;
    private List<Timestamp> dates;
    private String description;
    private Map<String, int[]> votes;

    @Override
    public String toString() {
        return "Meet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public List<Timestamp> getDates() {
        return dates;
    }

    public void setDates(List<Timestamp> dates) {
        this.dates = dates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, int[]> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, int[]> votes) {
        this.votes = votes;
    }
}
