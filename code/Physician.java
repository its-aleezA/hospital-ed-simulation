public class Physician {
    protected int id;
    protected String level;
    private int maxPatients;
    private Patient[] patientsBeingTreated;
    int patientCount = 0;

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
                patientsBeingTreated[i] = patientsBeingTreated[--patientCount];
                patientsBeingTreated[patientCount] = null; // Clean up
                break;
            }
        }
    }
}
