import java.util.Random;

public class SimulationSensors implements Sensors, Runnable {
    private Random random = new Random();
    private boolean running;
    private int nrVehicles;
    private int nrCount;
    private TrafficSystemController controller;
    //private PedestrianController pedestrianController;
    private float totalWaitingTime;
    private int totalEvents;

    public SimulationSensors(TrafficSystemController controller) {
        this.controller = controller;
        this.running = true;
        this.nrVehicles = generateNrVehicles();
        this.nrCount = 1;
        this.totalWaitingTime = 0;
        this.totalEvents = 0;
        new Thread(this).start();
    }

    @Override
    public int nrVehicles() {
        return nrVehicles;
    }

    public float totalWaitingTime(){
        return totalWaitingTime;
    }

    @Override
    public void run() {
        while (running && nrCount < 10) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nrVehicles = generateNrVehicles();
            float minStartTime = 1.00f;
            float maxStartTime = 10.00f;
            float minEndTime = minStartTime;
            float maxEndTime = 30.00f;

            float startTime = minStartTime + random.nextFloat() * (maxStartTime - minStartTime);
            float endTime = minEndTime + random.nextFloat() * (maxEndTime - minEndTime);

            startTime = Math.round(startTime * 100.0f) / 100.0f;
            endTime = Math.round(endTime * 100.0f) / 100.0f;

            if (endTime <= startTime) {
                endTime = startTime + 0.03f;
            }



            if (nrVehicles != 0) {
                totalWaitingTime = (endTime - startTime) / nrVehicles;
            } else {
                totalWaitingTime = 0;
            }
            controller.updateTrafficLight(nrVehicles,totalWaitingTime, startTime, endTime);
            nrCount++;
        }

        stop();
    }

    public int generateNrVehicles() {
        return random.nextInt(2, 10);
    }

    public void stop() {
        running = false;
    }

}