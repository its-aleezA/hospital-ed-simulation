package htms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Disease {
    private String name;
    private double scale;  // Weibull scale parameter (β)
    private double shape;  // Weibull shape parameter (α)
    private double nextAvailableTime;

    private static final Random random = new Random();
    private static final Map<String, Disease> DISEASES = new HashMap<>();

    static {
        // More realistic disease parameters
        DISEASES.put("Gastrointestinal", new Disease("Gastrointestinal", 180, 0.914));
        DISEASES.put("Cardiac", new Disease("Cardiac", 200, 1.1));
        DISEASES.put("Neurological", new Disease("Neurological", 150, 0.8));
        DISEASES.put("Respiratory", new Disease("Respiratory", 190, 1.05));
        DISEASES.put("Trauma", new Disease("Trauma", 120, 1.2));
        DISEASES.put("Infectious", new Disease("Infectious", 160, 0.95));
    }

    public Disease(String name, double scale, double shape) {
        if (scale <= 0 || shape <= 0) {
            throw new IllegalArgumentException("Scale and shape must be positive");
        }
        this.name = name;
        this.scale = scale;
        this.shape = shape;
        this.nextAvailableTime = 0;
    }

    // Getters
    public String getName() { return name; }
    public double getScale() { return scale; }
    public double getShape() { return shape; }
    public double getNextAvailableTime() { return nextAvailableTime; }

    public double generateWeibullTime() {
        return scale * Math.pow(-Math.log(random.nextDouble()), 1 / shape);
    }

    public boolean isAvailable(double currentTime) {
        return currentTime >= nextAvailableTime;
    }

    public void updateNextAvailableTime(double currentTime) {
        this.nextAvailableTime = currentTime + generateWeibullTime();
    }

    public static Disease getRandomDisease(double currentTime) {
        Disease selected = null;
        int attempts = 0;
        while (selected == null && attempts < DISEASES.size() * 2) {
            Disease disease = DISEASES.values().toArray(new Disease[0])[random.nextInt(DISEASES.size())];
            if (disease.isAvailable(currentTime)) {
                selected = disease;
                disease.updateNextAvailableTime(currentTime);
            }
            attempts++;
        }
        return selected != null ? selected : DISEASES.get("Cardiac"); // Fallback
    }

    public static Map<String, Disease> getAllDiseases() {
        return DISEASES;
    }
}