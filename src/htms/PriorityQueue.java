package htms;

import java.util.Arrays;

public class PriorityQueue {
    private final Patient[] queue;
    private int size = 0;

    public PriorityQueue(int capacity) {
        queue = new Patient[capacity];
    }

    public boolean addPatient(Patient patient) {
        if (size >= queue.length) return false;
        
        queue[size++] = patient;
        sortQueue();
        return true;
    }

    public Patient getNextPatient() {
        if (size == 0) return null;
        
        Patient next = queue[0];
        System.arraycopy(queue, 1, queue, 0, --size);
        queue[size] = null;
        return next;
    }

    private void sortQueue() {
        // Simple bubble sort (okay for small queues)
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (queue[j].getTriageCategory() > queue[j + 1].getTriageCategory()) {
                    Patient temp = queue[j];
                    queue[j] = queue[j + 1];
                    queue[j + 1] = temp;
                }
            }
        }
    }
    
    public Patient[] getPatients() {
        return Arrays.copyOf(queue, size);
    }
    
    public boolean isFull() {
        return size >= queue.length;
    }
    
    public int size() {
        return size;
    }
}