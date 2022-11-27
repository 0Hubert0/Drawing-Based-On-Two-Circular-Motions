package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    static double angle1 = 6.2911, angle2 = 6.2911, cnvx, cnvy, cnvy2, cnvx2,
            scx1, scx2, scy1, scy2, counter=0;
    static final double RADIUS = 70, PI=2*Math.PI;
    static boolean stopped = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();

        Color paint = new Color(0.17, 0.25, 0.66, 1);
        Scene scene = new Scene(root, 900, 625);

        Rectangle rec = new Rectangle(0, 0, 900, 625);
        rec.setFill(paint);
        root.getChildren().add(rec);

        Canvas canvas = new Canvas(900, 400);
        root.getChildren().add(canvas);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.setLineWidth(3);
        gr.setStroke(Color.BEIGE);

        Button start = new Button("start");
        start.setTranslateX(425);
        start.setTranslateY(350);
        root.getChildren().add(start);

        Button stop = new Button("stop");
        stop.setTranslateX(425);
        stop.setTranslateY(400);
        root.getChildren().add(stop);

        Button clear = new Button("clear");
        clear.setTranslateX(425);
        clear.setTranslateY(450);
        root.getChildren().add(clear);

        clear.setOnAction(event -> gr.clearRect(0, 0, 900, 400));

        Button stopc = new Button("pause looping");
        stopc.setTranslateX(402);
        stopc.setTranslateY(500);
        root.getChildren().add(stopc);

        Button startc = new Button("resume looping");
        startc.setTranslateX(400);
        startc.setTranslateY(550);
        root.getChildren().add(startc);

        Circle bigCircle1 = new Circle(250, 400, RADIUS, paint);
        bigCircle1.setStroke(Color.BEIGE);
        bigCircle1.setStrokeWidth(3);
        root.getChildren().add(bigCircle1);

        Circle bigCircle2 = new Circle(650, 400, RADIUS, paint);
        bigCircle2.setStroke(Color.BEIGE);
        bigCircle2.setStrokeWidth(3);
        root.getChildren().add(bigCircle2);

        Circle smallCircle1 = new Circle(250, bigCircle1.getCenterY() - RADIUS, 6, Color.BEIGE);
        root.getChildren().add(smallCircle1);

        Circle smallCircle2 = new Circle(650, bigCircle2.getCenterY() - RADIUS, 6, Color.BEIGE);
        root.getChildren().add(smallCircle2);

        Slider slider1 = new Slider(0.001, 0.05, 0.0225);
        slider1.setTranslateY(480);
        slider1.setTranslateX(180);
        slider1.setCursor(Cursor.HAND);
        root.getChildren().add(slider1);

        Text value1 = new Text("Value: " + Math.round(slider1.getValue() * 1000));
        value1.setStroke(Color.BEIGE);
        value1.setTranslateX(220);
        value1.setTranslateY(510);
        root.getChildren().add(value1);

        Slider slider2 = new Slider(0.001, 0.05, 0.0225);
        slider2.setTranslateY(480);
        slider2.setTranslateX(580);
        slider2.setCursor(Cursor.HAND);
        root.getChildren().add(slider2);

        Text value2 = new Text("Value: " + Math.round(slider2.getValue() * 1000));
        value2.setStroke(Color.BEIGE);
        value2.setTranslateX(620);
        value2.setTranslateY(510);
        root.getChildren().add(value2);

        Slider slider3 = new Slider(0, PI, angle1);
        slider3.setTranslateY(550);
        slider3.setTranslateX(180);
        slider3.setCursor(Cursor.HAND);
        slider3.setVisible(false);
        root.getChildren().add(slider3);

        Slider slider4 = new Slider(0, PI, angle2);
        slider4.setTranslateY(550);
        slider4.setTranslateX(580);
        slider4.setCursor(Cursor.HAND);
        slider4.setVisible(false);
        root.getChildren().add(slider4);

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (!stopped) {
                angle1 += slider1.getValue();
                slider3.setValue(angle1);
                if (angle1 > PI) {
                    angle1 -= PI;
                }
                scx1 = RADIUS * Math.cos(angle1);
                scy1 = RADIUS * Math.sin(angle1);
                smallCircle1.setCenterX(scx1 + bigCircle1.getCenterX());
                smallCircle1.setCenterY(scy1 + bigCircle1.getCenterY());

                angle2 += slider2.getValue();
                slider4.setValue(angle2);
                if (angle2 > PI) {
                    angle2 -= PI;
                }
                scx2 = RADIUS * Math.cos(angle2);
                scy2 = RADIUS * Math.sin(angle2);
                smallCircle2.setCenterX(scx2 + bigCircle2.getCenterX());
                smallCircle2.setCenterY(scy2 + bigCircle2.getCenterY());

                value1.setText("Value: " + Math.round(slider1.getValue() * 1000));
                double b = Math.round(slider1.getValue()*1000);
                b/=1000;
                slider1.setValue(b);
                value2.setText("Value: " + Math.round(slider2.getValue() * 1000));
                double c = Math.round(slider2.getValue()*1000);
                c/=1000;
                slider2.setValue(c);
            }
        }));


        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (!stopped) {
                if(counter==0)
                {
                cnvx = 450 + scx1 + scx2;
                cnvy = 175 + scy1 + scy2;

                gr.beginPath();
                gr.lineTo(cnvx, cnvy);
                gr.stroke();
                counter++;
                }
                else {
                    cnvx2 = cnvx;
                    cnvy2 = cnvy;
                    cnvx = 450 + scx1 + scx2;
                    cnvy = 175 + scy1 + scy2;

                    gr.beginPath();
                    gr.lineTo(cnvx2, cnvy2);
                    gr.lineTo(cnvx, cnvy);
                    gr.stroke();
                }
            }
        }));

        timeline2.setCycleCount(Animation.INDEFINITE);

        Timeline timeline3 = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            angle1 = slider3.getValue();
            scx1 = RADIUS * Math.cos(angle1);
            scy1 = RADIUS * Math.sin(angle1);
            smallCircle1.setCenterX(scx1 + bigCircle1.getCenterX());
            smallCircle1.setCenterY(scy1 + bigCircle1.getCenterY());

            angle2 = slider4.getValue();
            scx2 = RADIUS * Math.cos(angle2);
            scy2 = RADIUS * Math.sin(angle2);
            smallCircle2.setCenterX(scx2 + bigCircle2.getCenterX());
            smallCircle2.setCenterY(scy2 + bigCircle2.getCenterY());
        }));

        timeline3.setCycleCount(Animation.INDEFINITE);

        stopc.setOnAction(event -> {
            stopped = true;
            slider3.setVisible(true);
            slider4.setVisible(true);
            timeline3.play();
        });

        startc.setOnAction(event -> {
            stopped = false;
            slider3.setVisible(false);
            slider4.setVisible(false);
            timeline3.pause();
        });

        start.setOnAction(event -> {
            if (!stopped) {
                timeline2.play();
            }
        });

        stop.setOnAction(event -> {
            counter=0;
            if (!stopped) {
                timeline2.stop();
            }
        });

        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Particle Movement");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
