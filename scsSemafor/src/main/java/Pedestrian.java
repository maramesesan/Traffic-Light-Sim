public class Pedestrian {

    private int nrPresses = 0;

    private PedestrianController controller;

    public Pedestrian(PedestrianController controller) {
        this.controller = controller;
    }


    public int increasePresses() {
        nrPresses++;
        System.out.println("Pressed " + nrPresses);

        if (nrPresses == 5) {
            controller.notifyPedestrianPressed();
        }

        return nrPresses;
    }
}
