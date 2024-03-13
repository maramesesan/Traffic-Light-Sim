public class PedestrianController {

    private TrLight view;
    private Pedestrian pedestrian;
    private boolean pedestrianButtonPressed;

    public PedestrianController(TrLight view) {
        this.view = view;
        this.pedestrian = new Pedestrian(this);
        this.pedestrianButtonPressed = false;
    }

    public void increasePresses() {
        pedestrian.increasePresses();
        pedestrianButtonPressed = true;
    }

    public void notifyPedestrianPressed() {
        pedestrianButtonPressed = true;
        view.changeLightForPedestrian();
        view.stopSimulation();
    }


    public void notifySimulationStarted() {
        pedestrianButtonPressed = false;
    }

    public boolean isPedestrianButtonPressed() {
        return pedestrianButtonPressed;
    }
}
