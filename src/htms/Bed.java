package htms;

import htms.Patient;

public class Bed {
    protected int id;
    private boolean isOccupied;
    protected Patient currentPatient;
    protected String type;

    public Bed(int id, String t) {
        this.id = id;
        this.isOccupied = false;
        this.currentPatient = null;
        this.type = t;
    }
    
    public Bed(int id) {
        this.id = id;
        this.isOccupied = false;
        this.currentPatient = null;
        this.type = "n/a";
    }

    public boolean isAvailable() { return !isOccupied; }
    public void assignPatient(Patient patient) {
        this.currentPatient = patient;
        this.isOccupied = true;
    }
    public void releasePatient() {
        this.currentPatient = null;
        this.isOccupied = false;
    }
}