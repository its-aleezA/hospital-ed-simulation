package htms;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Display;
import java.util.*;
import java.util.concurrent.*;

public class EmergencyDepartment {
    private final PriorityQueue waitingRoomQueue;
    private final Queue<Patient> inPatientQueue;
    private final Bed[] acuteBeds;
    private final Bed[] subAcuteBeds;
    private final Bed[] resuscitationBeds;
    private final Physician[] physicians;
    
    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private int totalPatientsTreated = 0;
    
    public EmergencyDepartment(int numAcuteBeds, int numSubAcuteBeds, 
                             int numResuscitationBeds, int numPhysicians) {
        waitingRoomQueue = new PriorityQueue(100);
        inPatientQueue = new LinkedList<>();

        acuteBeds = new Bed[numAcuteBeds];
        subAcuteBeds = new Bed[numSubAcuteBeds];
        resuscitationBeds = new Bed[numResuscitationBeds];
        physicians = new Physician[numPhysicians];

        initializeBedsAndPhysicians();
    }
    
    private void initializeBedsAndPhysicians() {
        for (int i = 0; i < acuteBeds.length; i++) {
            acuteBeds[i] = new Bed(i + 1, "Acute");
        }
        for (int i = 0; i < subAcuteBeds.length; i++) {
            subAcuteBeds[i] = new Bed(i + 1, "Sub-Acute");
        }
        for (int i = 0; i < resuscitationBeds.length; i++) {
            resuscitationBeds[i] = new Bed(i + 1, "Resuscitation");
        }
        
        // Create physicians with different levels and capacities
        for (int i = 0; i < physicians.length; i++) {
            String level = (i == 0) ? "Senior" : "Resident";
            int capacity = (i == 0) ? 3 : 2; // Senior can handle more patients
            physicians[i] = new Physician(i + 1, level, capacity);
        }
    }
    
    public void startSimulation(Display display, Text... texts) {
        startPatientScheduler();
        startPatientProcessingScheduler(display, texts);
    }
    
    private void startPatientScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            int triageCategory = getRandomTriageCategory();
            Disease disease = Disease.getRandomDisease(currentTime);
            Patient patient = new Patient(currentTime, triageCategory, disease.getName());

            synchronized (waitingRoomQueue) {
                if (addPatientToQueue(patient)) {
                    System.out.println("[" + formatTime(currentTime) + "] Patient " + patient.getId() +
                            " added with triage " + triageCategory + " (" + disease.getName() + ")");
                }
            }
        }, 0, 1, TimeUnit.SECONDS);       
    }

    private int getRandomTriageCategory() {
        // Weighted probabilities for more realistic distribution
        double rand = random.nextDouble();
        if (rand < 0.05) return 1;   // 5% T1
        if (rand < 0.15) return 2;    // 10% T2
        if (rand < 0.40) return 3;    // 25% T3
        if (rand < 0.70) return 4;    // 30% T4
        return 5;                     // 30% T5
    }

    private void startPatientProcessingScheduler(Display display, Text... texts) {
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            
            synchronized (this) {
                processPatients(currentTime);
                dischargePatients(currentTime);
            }
            
            display.asyncExec(() -> updateUI(texts));
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    private void updateUI(Text[] texts) {
        try {
            Display.getDefault().asyncExec(() -> {
                try {
                    // Update waiting room and triage display
                    if (!texts[texts.length-2].isDisposed()) {
                        texts[texts.length-2].setText(getWaitingRoomStatus());
                    }
                    if (!texts[texts.length-3].isDisposed()) {
                        texts[texts.length-3].setText(getTriageDistribution());
                    }
                    
                    // Update beds
                    updateBedStatuses(texts);
                    
                    // Update physicians
                    for (int i = 0; i < 4 && i < physicians.length; i++) {
                        if (!texts[i+18].isDisposed()) {
                            texts[i+18].setText(getPhysicianStatus(physicians[i]));
                        }
                    }
                    
                    // Update inpatient queue and stats
                    if (!texts[22].isDisposed()) {
                        texts[22].setText("In-Patients: " + inPatientQueue.size());
                    }
                    if (!texts[23].isDisposed()) {
                        texts[23].setText("Treated: " + totalPatientsTreated);
                    }
                } catch (Exception e) {
                    System.err.println("UI Update Error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Display Error: " + e.getMessage());
        }
    }
    
    private void updateBedStatuses(Text[] texts) {
        // Resuscitation beds (3)
        for (int i = 0; i < 3 && i < resuscitationBeds.length; i++) {
            texts[i].setText(getBedStatus(resuscitationBeds[i]));
        }
        
        // Acute beds (5)
        for (int i = 0; i < 5 && i < acuteBeds.length; i++) {
            texts[i+3].setText(getBedStatus(acuteBeds[i]));
        }
        
        // Sub-acute beds (10)
        for (int i = 0; i < 10 && i < subAcuteBeds.length; i++) {
            texts[i+8].setText(getBedStatus(subAcuteBeds[i]));
        }
    }

    private String getBedStatus(Bed bed) {
        if (bed == null || bed.isAvailable()) return "Available";
        return "Patient " + bed.currentPatient.getId() + 
               " (T" + bed.currentPatient.getTriageCategory() + ")";
    }
    
    private String getPhysicianStatus(Physician physician) {
        if (physician == null) return "N/A";
        return physician.canTakePatient() ? "Available" : 
            "Treating " + physician.getPatientCount() + "/" + physician.getMaxPatients();
    }

    public String getWaitingRoomStatus() {
        StringBuilder sb = new StringBuilder("Waiting Patients:\n");
        Patient[] patients = waitingRoomQueue.getPatients();
        for (Patient p : patients) {
            if (p != null) {
                sb.append("P").append(p.getId())
                  .append(" (T").append(p.getTriageCategory())
                  .append(") - ").append(p.getCondition())
                  .append("\n");
            }
        }
        return sb.toString();
    }

    public String getTriageDistribution() {
        int[] counts = new int[5];
        Patient[] patients = waitingRoomQueue.getPatients();
        for (Patient p : patients) {
            if (p != null) counts[p.getTriageCategory()-1]++;
        }
        return String.format("Triage Distribution:\nT1: %d\nT2: %d\nT3: %d\nT4: %d\nT5: %d", 
                counts[0], counts[1], counts[2], counts[3], counts[4]);
    }

    private void processPatients(long currentTime) {
        synchronized (waitingRoomQueue) {
            for (Physician doctor : physicians) {
                while (doctor.canTakePatient()) {
                    Patient nextPatient = waitingRoomQueue.getNextPatient();
                    if (nextPatient == null) break;
                    
                    doctor.assignPatient(nextPatient);
                    nextPatient.setTreatmentStartTime(currentTime);
                    assignBed(nextPatient);
                }
            }
        }
    }
    
    private boolean assignBed(Patient patient) {
        Bed[] bedPool = getBedPoolForTriage(patient.getTriageCategory());
        if (bedPool == null) return false;

        for (Bed bed : bedPool) {
            if (bed != null && bed.isAvailable()) {
                bed.assignPatient(patient);
                return true;
            }
        }
        return false;
    }

    private Bed[] getBedPoolForTriage(int triage) {
        switch (triage) {
            case 1: case 5: return resuscitationBeds;
            case 2: case 4: return acuteBeds;
            case 3: return subAcuteBeds;
            default: return null;
        }
    }

    private void dischargePatients(long currentTime) {
        for (Bed[] bedCategory : new Bed[][]{resuscitationBeds, acuteBeds, subAcuteBeds}) {
            for (Bed bed : bedCategory) {
                if (bed == null || bed.isAvailable()) continue;
                
                Patient patient = bed.currentPatient;
                if (patient != null && currentTime - patient.getTreatmentStartTime() >= patient.getTreatmentTime()) {
                    // Release resources
                    releasePatientResources(patient, bed);
                    totalPatientsTreated++;
                    
                    // Potential inpatient admission (higher probability for higher triage)
                    if (random.nextDouble() < getAdmissionProbability(patient.getTriageCategory())) {
                        inPatientQueue.add(patient);
                    }
                }
            }
        }
    }
    
    private double getAdmissionProbability(int triage) {
        switch(triage) {
            case 1: return 0.8; // 80% chance for T1
            case 2: return 0.6; // 60% for T2
            case 3: return 0.4; // 40% for T3
            case 4: return 0.2; // 20% for T4
            case 5: return 0.7; // 70% for T5 (resuscitation)
            default: return 0.3;
        }
    }

    private void releasePatientResources(Patient patient, Bed bed) {
        for (Physician doctor : physicians) {
            doctor.releasePatient(patient);
        }
        bed.releasePatient();
    }

    public boolean addPatientToQueue(Patient patient) {
        return !waitingRoomQueue.isFull() && waitingRoomQueue.addPatient(patient);
    }
    
    public void stopSimulation() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        return String.format("%02d:%02d", (seconds / 60) % 60, seconds % 60);
    }
    
    public int getTotalPatientsTreated() {
        return totalPatientsTreated;
    }
}