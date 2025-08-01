public class PriorityQueue {
    private Patient[] queue;
    private int size = 0;

    public PriorityQueue(int capacity) {
        queue = new Patient[capacity];
    }

    public void addPatient(Patient patient) {
        if (size < queue.length) {
            queue[size++] = patient;
            sortQueue();
        }
    }

    public Patient getNextPatient() {
        if (size == 0) return null;
        Patient next = queue[0];
        System.arraycopy(queue, 1, queue, 0, --size);
        queue[size] = null; // Clean up
        return next;
    }

    private void sortQueue() {
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

    // New method to check if the queue is full
    public boolean isFull() {
        return size >= queue.length;
    }
}
