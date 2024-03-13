import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrLight {
    private JFrame frame;
    private JPanel greenPanel;
    private JPanel yellowPanel;
    private JPanel redPanel;

    private JPanel redPedestrian;
    private JPanel greenPedestrian;
    private JButton toggleButton;
    private JButton pedestrianButton;
    private Color red = Color.RED;
    private Color yellow = Color.YELLOW;
    private Color green = Color.GREEN;
    private Color black = Color.BLACK;

    private Color currentColor = red;
    private boolean stopped = false;
    private boolean go = false;
    public boolean simulationRunning = true;
    private JButton changeToGreenButton;
    private JButton changeToRedButton;
    private JLabel averageWaitingTimeLabel;
    private JLabel vehiclesLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;




    private TrafficSystemController controller1;
    private PedestrianController controller2;
    private PedestrianController pedestrianController;


    public TrLight() {
        this.controller1 = new TrafficSystemController(this);
        this.controller2 = new PedestrianController(this);
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Traffic Light Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        greenPanel = new JPanel();
        greenPanel.setBackground(black);
        greenPanel.setBounds(20, 20, 100, 100);

        yellowPanel = new JPanel();
        yellowPanel.setBackground(black);
        yellowPanel.setBounds(20, 140, 100, 100);

        redPanel = new JPanel();
        redPanel.setBackground(black);
        redPanel.setBounds(20, 260, 100, 100);

        frame.setLayout(null);

        frame.add(greenPanel);
        frame.add(yellowPanel);
        frame.add(redPanel);

        greenPedestrian = new JPanel();
        greenPedestrian.setBackground(black);
        greenPedestrian.setBounds(140, 20, 100, 100);

        redPedestrian = new JPanel();
        redPedestrian.setBackground(black);
        redPedestrian.setBounds(140, 140, 100, 100);

        frame.setLayout(null);

        frame.add(greenPedestrian);
        frame.add(redPedestrian);

        toggleButton = new JButton("Start Traffic System");

        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button pressed! Starting Traffic System...");
                simulationRunning = true;
                controller1.startTrafficSystem();
                controller2.notifySimulationStarted(); // Notifică că simularea a început
            }
        });

        toggleButton.setBounds(450, 50, 150, 30);
        frame.add(toggleButton);

        pedestrianButton = new JButton ("Waiting pedestrian");
        pedestrianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pedestrian button was pressed");
                controller2.increasePresses();
            }
        });
        pedestrianButton.setBounds(450,100,150,30);
        frame.add(pedestrianButton);


       changeToGreenButton = new JButton("Change to Green");
        changeToGreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carGreen();
            }
        });
        changeToGreenButton.setBounds(450, 150, 150, 30);
        frame.add(changeToGreenButton);

        changeToRedButton = new JButton("Change to Red");
        changeToRedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carRed();
            }
        });
        changeToRedButton.setBounds(450, 200, 150, 30);
        frame.add(changeToRedButton);


        vehiclesLabel = new JLabel("Number of Vehicles: ");
        vehiclesLabel.setBounds(20, 360, 200, 30);
        frame.add(vehiclesLabel);

        startTimeLabel = new JLabel("Start Time: ");
        startTimeLabel.setBounds(20, 390, 200, 30);
        frame.add(startTimeLabel);

        endTimeLabel = new JLabel("End Time: ");
        endTimeLabel.setBounds(20, 420, 200, 30);
        frame.add(endTimeLabel);

        averageWaitingTimeLabel = new JLabel("Average Waiting Time: ");
        averageWaitingTimeLabel.setBounds(20, 450, 200, 30);
        frame.add(averageWaitingTimeLabel);

        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updateLabels(int nrVehicles, float startTime, float endTime, float averageWaitingTime) {
        vehiclesLabel.setText("Number of Vehicles: " + nrVehicles);
        startTimeLabel.setText("Start Time: " + startTime);
        endTimeLabel.setText("End Time: " + endTime);
        averageWaitingTimeLabel.setText("Average Waiting Time: " + averageWaitingTime);
    }

    public void changeLight(int nrVehicles, float averageWaitingTime, float startTime, float endTime) {
        System.out.println("Start time: "+ startTime + "|End time: " + endTime);
        System.out.println("Updated Vehicles: " + nrVehicles);
        System.out.println("avg waiting time: " + averageWaitingTime);


        if (simulationRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(averageWaitingTime > 4){
                System.out.println("red soon");
                go = false;
                if (!stopped) {
                    carRed();
                }
            }else {
                if (nrVehicles <= 5) {
                    System.out.println("red soon");
                    go = false;
                    if (!stopped) {
                        carRed();
                    }
                } else {
                    System.out.println("green soon");
                    stopped = false;
                    if (!go) {
                        carGreen();
                    }
                }
            }
            updateLabels(nrVehicles, startTime, endTime,averageWaitingTime);
        }
    }


    public void changeLightForPedestrian() {
        System.out.println("Pedestrian pressed 5 times. Changing traffic light color to green.");

        yellowPanel.setBackground(yellow);
        redPanel.setBackground(black);
        greenPanel.setBackground(black);
        redPedestrian.setBackground(black);
        greenPedestrian.setBackground(black);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yellowPanel.setBackground(black);
                redPanel.setBackground(red);
                greenPanel.setBackground(black);
                redPedestrian.setBackground(black);
                greenPedestrian.setBackground(green);
            }
        });
        timer.setRepeats(false);
        timer.start();

        Timer timer2 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yellowPanel.setBackground(black);
                redPanel.setBackground(black);
                greenPanel.setBackground(green);
                redPedestrian.setBackground(red);
                greenPedestrian.setBackground(green);
            }
        });
        timer2.setRepeats(false);
        timer2.start();
    }

    public void stopSimulation() {simulationRunning = false;}

    public void carRed(){
        yellowPanel.setBackground(yellow);
        redPanel.setBackground(black);
        greenPanel.setBackground(black);
        redPedestrian.setBackground(black);
        greenPedestrian.setBackground(green);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redPanel.setBackground(red);
                yellowPanel.setBackground(black);
                greenPanel.setBackground(black);
                redPedestrian.setBackground(black);
                greenPedestrian.setBackground(green);
            }
        });
        timer.setRepeats(false);
        timer.start();
        stopped = true;
    }
    public void carGreen(){
        yellowPanel.setBackground(yellow);
        redPanel.setBackground(black);
        greenPanel.setBackground(black);
        redPedestrian.setBackground(red);
        greenPedestrian.setBackground(black);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redPanel.setBackground(black);
                yellowPanel.setBackground(black);
                greenPanel.setBackground(green);
                redPedestrian.setBackground(red);
                greenPedestrian.setBackground(black);
            }
        });
        timer.setRepeats(false);
        timer.start();
        go = true;
    }

    public static void main(String[] args) {
        TrLight view = new TrLight();
    }
}
