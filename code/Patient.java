public class Patient {
    private static int idCounter = 0; // Static counter for unique IDs
    private int id; // Unique ID for each patient
    private double arrivalTime; // Time of arrival
    private int triageCategory; // Triage category (1-5)
    protected double treatmentStartTime; // Time when treatment starts
    protected double treatmentEndTime; // Time when treatment ends
    protected Bed bed; // Bed assigned to the patient
    protected String condition; // Patient's condition
    private String status; // Current status (e.g., "Waiting", "In Treatment", "Discharged")

    public Patient(double arrivalTime, int triageCategory, String condition) {
        this.id = ++idCounter; // Auto-increment unique ID
        this.arrivalTime = arrivalTime;
        this.triageCategory = triageCategory;
        this.condition = condition;
        this.status = "Waiting"; // Initial status is "Waiting"
    }

    // Getter for patient ID
    public int getId() {
        return id;
    }

    // Getter for arrival time
    public double getArrivalTime() {
        return arrivalTime;
    }

    // Getter for triage category
    public int getTriageCategory() {
        return triageCategory;
    }

    // Setter for treatment start time
    public void setTreatmentStartTime(double time) {
        this.treatmentStartTime = time;
    }

    // Setter for treatment end time
    public void setTreatmentEndTime(double time) {
        this.treatmentEndTime = time;
    }

    // Setter for patient status
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter for patient status
    public String getStatus() {
        return status;
    }

    // Method to calculate treatment time based on triage category
    public double getTreatmentTime() {
        // Treatment parameters defined by triage category
        int scale = 0;
        double shape1 = 0;
        double shape2 = 0;

        switch (triageCategory) {
            case 1:
                scale = 100;
                shape1 = 2.0;
                shape2 = 3.0;
                break;
            case 2:
                scale = 200;
                shape1 = 1.8;
                shape2 = 4.5;
                break;
            case 3:
                scale = 300;
                shape1 = 1.5;
                shape2 = 3.2;
                break;
            case 4:
                scale = 355;
                shape1 = 1.64;
                shape2 = 5.72;
                break;
            case 5:
                scale = 400;
                shape1 = 1.2;
                shape2 = 2.8;
                break;
            default:
                System.out.println("Invalid triage category.");
                return -1; // Return -1 for invalid category
        }

        return generatePearsonVIDistribution(scale, shape1, shape2);
    }

    // Generate treatment time based on Pearson VI distribution
    private double generatePearsonVIDistribution(int scale, double shape1, double shape2) {
        Random random = new Random();
        double u = random.nextDouble(); // Uniform random number [0, 1)
        return scale * Math.pow(u, 1 / shape1) * Math.pow(1 - u, 1 / shape2);
    }
    
    public static double calculatePDF(double x) {
        double mu = 156; // Mean
        return (1 / mu) * Math.exp(-x / mu);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", triageCategory=" + triageCategory +
                ", condition='" + condition + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
