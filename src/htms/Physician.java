// Physician.java - Added more functionality
package htms;

import java.util.*;

public class Physician {
    protected final int id;
    protected final String level;
    private final int maxPatients;
    private final Patient[] patientsBeingTreated;
    private int patientCount = 0;

    public Physician(int id, String level, int maxPatients) {
        this.id = id;
        this.level = level;
        this.maxPatients = maxPatients;
        this.patientsBeingTreated = new Patient[maxPatients];
    }

    public boolean canTakePatient() {
        return patientCount < maxPatients;
    }

    public void assignPatient(Patient patient) {
        if (patientCount < maxPatients) {
            patientsBeingTreated[patientCount++] = patient;
        }
    }

    public void releasePatient(Patient patient) {
        for (int i = 0; i < patientCount; i++) {
            if (patientsBeingTreated[i] == patient) {
                // Shift remaining patients
                System.arraycopy(patientsBeingTreated, i+1, patientsBeingTreated, i, patientCount-i-1);
                patientsBeingTreated[--patientCount] = null;
                break;
            }
        }
    }
    
    public int getPatientCount() {
        return patientCount;
    }
    
    public int getMaxPatients() {
        return maxPatients;
    }
    
    public Patient[] getAssignedPatients() {
        return Arrays.copyOf(patientsBeingTreated, patientCount);
    }
}