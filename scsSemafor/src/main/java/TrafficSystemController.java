public class TrafficSystemController {
    private TrLight view;
    private PedestrianController pedestrianController;
    private int waitingTimeCounter = 0;
    private int waitingTimeSum = 0;
    private int waitingTimeSamples = 0;

    private boolean working = false;

    public TrafficSystemController(TrLight view) {
        this.view = view;
    }

    public void startTrafficSystem() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SimulationSensors simulatedSensor = new SimulationSensors(this);
        Thread simulationThread = new Thread(simulatedSensor);
            simulationThread.start();
    }

    public synchronized void updateTrafficLight(int nrVehicles, float waitingTime, float startTime, float endTime) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        view.changeLight(nrVehicles,waitingTime, startTime, endTime);
    }

}
