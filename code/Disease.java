import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Disease {
    private String name;
    private double scale;  // Weibull scale parameter (β)
    private double shape;  // Weibull shape parameter (α)
    private double nextAvailableTime; // Time when this disease can appear again

    private static final Random random = new Random();

    // A static map of predefined diseases with their Weibull parameters
    private static final Map<String, Disease> DISEASES = new HashMap<>();

    static {
        DISEASES.put("Gastrointestinal", new Disease("Gastrointestinal", 180, 0.914));
        DISEASES.put("Cardiac/Vascular", new Disease("Cardiac/Vascular", 200, 1.1));
        DISEASES.put("Neurological", new Disease("Neurological", 150, 0.8));
        DISEASES.put("Respiratory", new Disease("Respiratory", 190, 1.05));
        DISEASES.put("Renal", new Disease("Renal", 170, 0.9));
        // Add more diseases and their Weibull parameters as needed
    }

    // Constructor
    public Disease(String name, double scale, double shape) {
        this.name = name;
        this.scale = scale;
        this.shape = shape;
        this.nextAvailableTime = 0; // Initially, the disease can appear immediately
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getScale() {
        return scale;
    }

    public double getShape() {
        return shape;
    }

    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    // Method to calculate Weibull probability density function
    public double weibullPDF(double x) {
        if (x < 0) {
            return 0;
        }
        double beta = this.scale;
        double alpha = this.shape;
        return (alpha / beta) * Math.pow(x / beta, alpha - 1) * Math.exp(-Math.pow(x / beta, alpha));
    }

    // Method to generate random interarrival time based on Weibull distribution
    public double generateWeibullTime() {
        double uniformRandom = random.nextDouble(); // Value between 0 and 1
        double beta = this.scale;
        double alpha = this.shape;
        return beta * Math.pow(-Math.log(1 - uniformRandom), 1 / alpha);
    }

    // Method to check if the disease is available at the current time
    public boolean isAvailable(double currentTime) {
        return currentTime >= nextAvailableTime;
    }

    // Method to update the next available time for the disease
    public void updateNextAvailableTime(double currentTime) {
        double interval = generateWeibullTime();
        this.nextAvailableTime = currentTime + interval;
    }

    // Method to randomly select a disease while ensuring availability
    public static Disease getRandomDisease(double currentTime) {
        Disease selectedDisease = null;
        while (selectedDisease == null) {
            // Randomly pick a disease from the map
            Object[] diseases = DISEASES.values().toArray();
            Disease disease = (Disease) diseases[random.nextInt(diseases.length)];

            // Check if the disease is available
            if (disease.isAvailable(currentTime)) {
                selectedDisease = disease;
                disease.updateNextAvailableTime(currentTime); // Update next availability
            }
        }
        return selectedDisease;
    }

    // Method to retrieve all predefined diseases
    public static Map<String, Disease> getAllDiseases() {
        return DISEASES;
    }
}
