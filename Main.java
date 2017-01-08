import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Dhruv on 1/3/2017.
 */
public class Main extends Application{

    double G  = 6.67408 * Math.pow(10, -11);

    double dt = 0.01;

    Button startb;
    Button add;
    Button clear;
    Text name;
    Text mass;
    Text initX;
    Text initY;
    Text initVX;
    Text initVY;
    //Text charge;
    Text errorfield;
    Text time;
    TextField timeval;
    TextField nameval;
    TextField massval;
    TextField initXval;
    TextField initYval;
    TextField initVXval;
    TextField initVYval;
    //TextField chargeval;
    //RadioButton gravityval;
    //RadioButton coulombval;
    GridPane gridPane;
    GridPane results;
    VBox layout;
    ArrayList<Particle> particleList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        particleList = new ArrayList<>();
        primaryStage.setTitle("Grav");
        startb = new Button();
        startb.setText("Start simulation");
        startb.setPrefWidth(200);
        startb.setOnAction(e -> loop());
        add = new Button();
        add.setText("Add particle");
        add.setPrefWidth(200);
        add.setOnAction(e -> addParticle());
        clear = new Button();
        clear.setText("Clear all particles");
        clear.setPrefWidth(200);
        clear.setOnAction(e -> particleList.clear());
        name = new Text("Name");
        mass = new Text("Mass");
        //charge = new Text("Charge");
        //chargeval = new TextField();
        errorfield = new Text(" ");
        initX = new Text("Initial X position");
        initY = new Text("Initial Y position");
        initVX = new Text("Initial X velocity");
        initVY = new Text("Initial Y velocity");
        initXval = new TextField();
        initYval = new TextField();
        initVXval = new TextField();
        initVYval = new TextField();
        //gravityval = new RadioButton("Enable gravitational force");
        //coulombval = new RadioButton("Enable coulomb force");
        nameval = new TextField();
        massval = new TextField();
        time = new Text("Enter required time");
        timeval = new TextField();

        gridPane = new GridPane();
        HBox timer = new HBox();
        timer.setAlignment(Pos.CENTER);
        timer.getChildren().addAll(time, timeval);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.addRow(1, name, nameval);
        gridPane.addRow(2, mass, massval);
        //gridPane.addRow(3, charge, chargeval);
        gridPane.addRow(4, initX, initXval);
        gridPane.addRow(5, initY, initYval);
        gridPane.addRow(6, initVX, initVXval);
        gridPane.addRow(7, initVY, initVYval);

        layout = new VBox();
        //VBox sub = new VBox();
        //sub.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(gridPane);
        layout.getChildren().add(errorfield);
        layout.getChildren().add(add);
        //sub.getChildren().addAll(gravityval, coulombval);
        //layout.getChildren().add(sub);
        layout.getChildren().add(timer);
        layout.getChildren().add(startb);
        layout.getChildren().add(clear);

        results = new GridPane();
        ArrayList<ColumnConstraints> constraints = new ArrayList<>(5);
        for(ColumnConstraints c : constraints) {
            c.setPercentWidth(20);
        }
        results.getColumnConstraints().addAll(constraints);
        layout.getChildren().add(results);
        results.setAlignment(Pos.CENTER);
        results.setHgap(20);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public void addParticle() {
        try {
            String tname = nameval.getText();
            double tmass = Double.parseDouble(massval.getText());
            //double tcharge = Double.parseDouble(chargeval.getText());
            double tcharge = 0;
            double tinitX = Double.parseDouble(initXval.getText());
            double tinitY = Double.parseDouble(initYval.getText());
            double tinitVX = Double.parseDouble(initVXval.getText());
            double tinitVY = Double.parseDouble(initVYval.getText());
            ChargedParticle p = new ChargedParticle(tname, tinitX, tinitY, tmass, tcharge, tinitVX, tinitVY);
            particleList.add(p);
            errorfield.setText(" ");
            nameval.clear();
            massval.clear();
            initXval.clear();
            initYval.clear();
            initVXval.clear();
            initVYval.clear();
        } catch (NumberFormatException e1) {
            errorfield.setText("Illegal Argument " + e1.getMessage());
        }
    }


    void update() {
        for (int i = 0; i < particleList.size(); i++) {
            Particle p = particleList.get(i);
            p.setx(p.getvx()*dt + p.getx());
            p.sety(p.getvy()*dt + p.gety());
            p.setvx(xAccelerations.get(i)*dt + p.getvx());
            p.setvy(yAccelerations.get(i)*dt + p.getvy());
        }
    }

    public void loop() {
        try {
            double maxTime = Double.parseDouble(timeval.getText());
            double time = 0;
            while(time < maxTime) {
                calculateAccelerations();
                update();
                time += dt;
            }
            results.getChildren().clear();
            results.addRow(0, new Text("Name"), new Text("X"), new Text("Y"), new Text("X velocity"), new Text("Y Velocity"));
            for(int i = 0; i < particleList.size(); i++) {
                Particle p = particleList.get(i);
                results.addRow(i + 1, new Text(p.getname()), new Text(p.getx() + ""), new Text(p.gety() + ""), new Text(p.getvx() + ""), new Text(p.getvy() +""));
            }
        } catch (NumberFormatException e) {
            errorfield.setText("Illegal Argument " + e.getMessage());
        }


    }

    public void calculateAccelerations() {
        xAccelerations = new ArrayList<>(Collections.nCopies(particleList.size(), 0.0));
        yAccelerations = new ArrayList<>(Collections.nCopies(particleList.size(), 0.0));
        for (int i = 0; i < particleList.size(); i++) {
            Particle p1 = particleList.get(i);
            for (int j = 0; j < i; j++) {
                Particle p2 = particleList.get(j);
                double fX = G * p1.getmass() * p2.getmass() * (p1.getx() - p2.getx()) / inverseDistBetweenCubed(p1, p2);
                double fY = G * p1.getmass() * p2.getmass() * (p1.gety() - p2.gety()) / inverseDistBetweenCubed(p1, p2);
                xAccelerations.set(i, xAccelerations.get(i) - fX/p1.getmass());
                xAccelerations.set(j, xAccelerations.get(j) + fX/p2.getmass());
                yAccelerations.set(i, yAccelerations.get(i) - fY/p1.getmass());
                yAccelerations.set(j, yAccelerations.get(j) + fY/p2.getmass());
            }
        }


    }

    public double inverseDistBetweenCubed(Particle p1, Particle p2) {
        return (Math.pow(Math.pow(p1.getx() - p2.getx(), 2) + Math.pow(p1.gety() - p2.gety(), 2), -3));
    }

    ArrayList<Double> xAccelerations;
    ArrayList<Double> yAccelerations;

}
