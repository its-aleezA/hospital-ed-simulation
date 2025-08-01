import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Display;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmergencyDepartment {
    private PriorityQueue waitingRoomQueue;
    private Queue<Patient> inPatientQueue;
    private Bed[] acuteBeds;
    private Bed[] subAcuteBeds;
    private Bed[] resuscitationBeds;
    public Physician[] physicians;
    
    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public EmergencyDepartment(int numAcuteBeds, int numSubAcuteBeds, int numResuscitationBeds, int numPhysicians) {
        waitingRoomQueue = new PriorityQueue(100);
        inPatientQueue = new LinkedList<>();

        acuteBeds = new Bed[numAcuteBeds];
        subAcuteBeds = new Bed[numSubAcuteBeds];
        resuscitationBeds = new Bed[numResuscitationBeds];
        physicians = new Physician[numPhysicians];

        for (int i = 0; i < numAcuteBeds; i++) acuteBeds[i] = new Bed(i + 1, "Acute");
        for (int i = 0; i < numSubAcuteBeds; i++) subAcuteBeds[i] = new Bed(i + 1, "Sub-Acute");
        for (int i = 0; i < numResuscitationBeds; i++) resuscitationBeds[i] = new Bed(i + 1, "Resuscitation");
        for (int i = 0; i < numPhysicians; i++) physicians[i] = new Physician(i + 1, "Resident", 2);
    }
    
    public void startPatientScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis(); // Current time in minutes
            int triageCategory = random.nextInt(5) + 1; // Random triage category (1-5)
            String condition = "Condition-" + random.nextInt(1000);
            Patient patient = new Patient(currentTime, triageCategory, condition);

            boolean added = addPatientToQueue(patient);
            if (added) {
                System.out.println("[" + formatTime(currentTime) + "] Patient " + patient.getId() +
                        " added with triage " + triageCategory);
            } else {
                System.out.println("[" + formatTime(currentTime) + "] Waiting room is full! Patient " +
                        patient.getId() + " cannot be added.");
            }
        }, 0, 1, TimeUnit.SECONDS);       
    }

    public void startPatientProcessingScheduler(Display display, Text resuscitationBedsText, Text resuscitationBedsText2, Text resuscitationBedsText3,
            Text acuteBedsText, Text acuteBedsText2, Text acuteBedsText3, Text acuteBedsText4, Text acuteBedsText5,
            Text subAcuteBedsText, Text subAcuteBedsText2, Text subAcuteBedsText3, Text subAcuteBedsText4, Text subAcuteBedsText5,
            Text subAcuteBedsText6, Text subAcuteBedsText7, Text subAcuteBedsText8, Text subAcuteBedsText9, Text subAcuteBedsText10,
            Text physicianText, Text physicianText2, Text physicianText3, Text physicianText4,
            Text inpatientQueueSize, Text patientAssignment) {
	scheduler.scheduleAtFixedRate(() -> {
		long currentTime = System.currentTimeMillis(); // Current time in milliseconds
		processPatients(currentTime, patientAssignment);  // Assuming this method processes patients and updates bed/physician states
	
		// Use display.asyncExec to update the text boxes on the UI thread
		display.asyncExec(new Runnable() {
		    @Override
		    public void run() {
		    	// Update Resuscitation Beds
		    	resuscitationBedsText.setText(resuscitationBeds[0].isAvailable() ? "Available" : "Occupied by Patient " + resuscitationBeds[0].currentPatient.getId());
		    	resuscitationBedsText2.setText(resuscitationBeds[1].isAvailable() ? "Available" : "Occupied by Patient " + resuscitationBeds[1].currentPatient.getId());
		    	resuscitationBedsText3.setText(resuscitationBeds[2].isAvailable() ? "Available" : "Occupied by Patient " + resuscitationBeds[2].currentPatient.getId());
		
		    	// Update Acute Beds
		    	acuteBedsText.setText(acuteBeds[0].isAvailable() ? "Available" : "Occupied by Patient " + acuteBeds[0].currentPatient.getId());
		    	acuteBedsText2.setText(acuteBeds[1].isAvailable() ? "Available" : "Occupied by Patient " + acuteBeds[1].currentPatient.getId());
		    	acuteBedsText3.setText(acuteBeds[2].isAvailable() ? "Available" : "Occupied by Patient " + acuteBeds[2].currentPatient.getId());
		    	acuteBedsText4.setText(acuteBeds[3].isAvailable() ? "Available" : "Occupied by Patient " + acuteBeds[3].currentPatient.getId());
		    	acuteBedsText5.setText(acuteBeds[4].isAvailable() ? "Available" : "Occupied by Patient " + acuteBeds[4].currentPatient.getId());
		
		    	// Update Sub-Acute Beds
		    	subAcuteBedsText.setText(subAcuteBeds[0].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[0].currentPatient.getId());
		    	subAcuteBedsText2.setText(subAcuteBeds[1].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[1].currentPatient.getId());
		    	subAcuteBedsText3.setText(subAcuteBeds[2].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[2].currentPatient.getId());
		    	subAcuteBedsText4.setText(subAcuteBeds[3].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[3].currentPatient.getId());
		    	subAcuteBedsText5.setText(subAcuteBeds[4].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[4].currentPatient.getId());
		    	subAcuteBedsText6.setText(subAcuteBeds[5].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[5].currentPatient.getId());
		    	subAcuteBedsText7.setText(subAcuteBeds[6].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[6].currentPatient.getId());
		    	subAcuteBedsText8.setText(subAcuteBeds[7].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[7].currentPatient.getId());
		    	subAcuteBedsText9.setText(subAcuteBeds[8].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[8].currentPatient.getId());
		    	subAcuteBedsText10.setText(subAcuteBeds[9].isAvailable() ? "Available" : "Occupied by Patient " + subAcuteBeds[9].currentPatient.getId());
		
		    	// Update Physicians
		    	physicianText.setText(physicians[0].canTakePatient() ? "Available" : "Treating Patients: " + physicians[0].patientCount);
		    	physicianText2.setText(physicians[1].canTakePatient() ? "Available" : "Treating Patients: " + physicians[1].patientCount);
		    	physicianText3.setText(physicians[2].canTakePatient() ? "Available" : "Treating Patients: " + physicians[2].patientCount);
		    	physicianText4.setText(physicians[3].canTakePatient() ? "Available" : "Treating Patients: " + physicians[3].patientCount);
		    	
		    	//Updating other text boxes
		    	inpatientQueueSize.setText("In-Patient Queue Size: " + inPatientQueue.size());
		    	//patientArrival.setText("In-Patient Queue Size: " + inPatientQueue.size());
		    }
		});
	}, 0, 1, TimeUnit.SECONDS); // Schedule the task to run every second
}


    private void processPatients(long currentTime, Text patientAssignment) {
        for (Physician doctor : physicians) {
            if (doctor.canTakePatient()) {
                Patient nextPatient = waitingRoomQueue.getNextPatient();
                if (nextPatient != null) {
                    doctor.assignPatient(nextPatient);
                    nextPatient.setTreatmentStartTime(currentTime);

                    boolean bedAssigned = assignBed(nextPatient);
                    if (bedAssigned) {
                        patientAssignment.setText("[" + formatTime(currentTime) + "] Patient " + nextPatient.getId() +
                                " assigned with Physician " + doctor.id);
                    } else {
                    	patientAssignment.setText("[" + formatTime(currentTime) + "] No available beds for Patient " + nextPatient.getId());
                    }
                }
            }
        }
        dischargePatients(currentTime);
        
        displayAvailability();
    }  
   
    private void displayAvailability() {
        System.out.println("\nBed Status:");
        for (Bed[] bedCategory : new Bed[][] {resuscitationBeds, acuteBeds, subAcuteBeds}) {
            for (Bed bed : bedCategory) {
                System.out.println("- Bed " + bed.id + " (" + bed.type + "): " +
                        (bed.isAvailable() ? "Available" : "Occupied by Patient " + bed.currentPatient.getId()));
            }
        }

        System.out.println("\nPhysician Status:");
        for (Physician doctor : physicians) {
            System.out.println("- Physician " + doctor.id + ": " +
                    (doctor.canTakePatient() ? "Available" : "Busy treating " + doctor.patientCount + " patients"));
        }

        System.out.println("\nIn-Patient Queue Size: " + inPatientQueue.size());
    }
    private boolean assignBed(Patient patient) {
        Bed[] bedPool;
        switch (patient.getTriageCategory()) {
            case 5:
            	bedPool = resuscitationBeds;
            	break;
            case 4:
            	bedPool = acuteBeds;
            	break;
            case 1:
            case 2:
            case 3:
                bedPool = subAcuteBeds;
                break;
            default:
            	return false;
        }

        for (Bed bed : bedPool) {
            if (bed.isAvailable()) {
                bed.assignPatient(patient);
                System.out.println("Patient " + patient.getId() + " assigned to Bed " + bed.id + " (" + bed.type + ")");
                return true;
            }
        }
        System.out.println("BEDS not available...");
        return false;
    }

    private void dischargePatients(long currentTime) {
        for (Bed[] bedCategory : new Bed[][]{resuscitationBeds, acuteBeds, subAcuteBeds}) {
            for (Bed bed : bedCategory) {
                Patient patient = bed.currentPatient;
                if (patient != null && currentTime - patient.treatmentStartTime >= patient.getTreatmentTime()) {
                    bed.releasePatient();
                    System.out.println("[" + formatTime(currentTime) + "] Patient " + patient.getId() +
                            " discharged from Bed " + bed.id + " (" + bed.type + ")");

                    // Check if patient requires in-patient admission
                    if (Math.random() > 0.5) { // 50% chance of in-patient admission
                        inPatientQueue.add(patient);
                        System.out.println("[" + formatTime(currentTime) + "] Patient " + patient.getId() +
                                " admitted as in-patient.");
                    }
                }
            }
        }
    }

    public boolean addPatientToQueue(Patient patient) {
        if (!waitingRoomQueue.isFull()) {
            waitingRoomQueue.addPatient(patient);
            return true;
        } else {
            return false;
        }
    }
    
   
    public void stopScheduler() {
        scheduler.shutdown();
    }

    private String formatTime(long minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }
}
