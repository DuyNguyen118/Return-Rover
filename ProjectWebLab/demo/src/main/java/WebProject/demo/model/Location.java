package WebProject.ReRover.model;

import WebProject.ReRover.enums.Building;
import WebProject.ReRover.enums.Floor;

import jakarta.persistence.*;

@Entity
public class Location {
    private String room;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Building building;

    @Enumerated(EnumType.STRING)
    private Floor floor;

    // Constructors
    public Location() {}

    public Location(String room, Building building, Floor floor) {
        this.room = room;
        this.building = building;
        this.floor = floor;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public Building getBuilding() { 
        return building; 
    }
    public void setBuilding(Building building) { 
        this.building = building; 
    }

    public Floor getFloor() { 
        return floor; 
    }
    public void setFloor(Floor floor) { 
        this.floor = floor; 
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
}
