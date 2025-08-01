package htms;

import java.util.Random;

public class Patient {
	private static int idCounter = 0;
	private static final Object idLock = new Object();
    private final int id;
    private final double arrivalTime;
    private final int triageCategory;
    private double treatmentStartTime;
    private double treatmentEndTime;
    private Bed bed;
    private final String condition;
    private String status;
    private final Random random = new Random();

    public Patient(double arrivalTime, int triageCategory, String condition) {
        synchronized (idLock) {
            this.id = ++idCounter;
        }
        this.arrivalTime = arrivalTime;
        this.triageCategory = validateTriage(triageCategory);
        this.condition = condition;
        this.status = "Waiting";
    }

    private int validateTriage(int triage) {
        return Math.max(1, Math.min(5, triage));
    }

    public int getId() { return id; }
    public double getArrivalTime() { return arrivalTime; }
    public int getTriageCategory() { return triageCategory; }
    public String getCondition() { return condition; }
    public String getStatus() { return status; }
    public double getTreatmentStartTime() { return treatmentStartTime; }
    
    public void setTreatmentStartTime(double time) { 
        this.treatmentStartTime = time;
        this.status = "In Treatment";
    }
    
    public void setStatus(String status) { this.status = status; }

    public double getTreatmentTime() {
        // Base time in minutes (converted to milliseconds)
        double baseTime = switch (triageCategory) {
            case 1 -> 120; // 2 hours for T1
            case 2 -> 90;  // 1.5 hours for T2
            case 3 -> 60;  // 1 hour for T3
            case 4 -> 30;  // 30 mins for T4
            case 5 -> 15;  // 15 mins for T5
            default -> 45; // Default 45 mins
        };
        
        // Add variability based on condition severity
        double severityFactor = 1.0 + (random.nextDouble() * 0.4 - 0.2);
        return (baseTime * 60 * 1000) * severityFactor;
    }

    @Override
    public String toString() {
        return String.format("Patient %d (T%d - %s)", id, triageCategory, condition);
    }
}