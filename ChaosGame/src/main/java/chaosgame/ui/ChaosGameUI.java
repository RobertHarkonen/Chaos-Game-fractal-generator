
package chaosgame.ui;

import chaosgame.domain.Fractal;
import chaosgame.domain.Node;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Robert
 */
public class ChaosGameUI extends Application {
    
    int screenWidth;
    int screenHeight;
    Fractal activeFractal;
    
    @Override
    public void start(Stage stage) {
        screenWidth = 800;
        screenHeight = 600;
        activeFractal = new Fractal(screenWidth, screenHeight);
        
        BorderPane bPane = new BorderPane();
        VBox controlPanel = new VBox();
        Button startButton = new Button("Start");
        Button clearDrawn = new Button("Clear white tiles");
        Button clearCanvas = new Button("Clear everything");
        
        Slider ratio = new Slider(0, 1.5, 0.5);
        ratio.setMajorTickUnit(1);
        ratio.setMinorTickCount(3);
        ratio.showTickLabelsProperty().set(true);
        ratio.showTickMarksProperty().set(true);
        ratio.setOnMouseDragged(event -> {
            activeFractal.getSettings().setRatio(ratio.getValue());
        });
        
        controlPanel.getChildren().addAll(startButton, clearDrawn, clearCanvas, ratio);
        controlPanel.setSpacing(10);
        bPane.setLeft(controlPanel);
        
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        bPane.setCenter(canvas);

        GraphicsContext pen = canvas.getGraphicsContext2D();
        pen.setFill(Color.BLACK);
        pen.fillRect(0, 0, screenWidth, screenHeight);
        
        canvas.setOnMouseClicked(event -> {
            int nodeX = (int) Math.round(event.getX());
            int nodeY = (int) Math.round(event.getY());
            activeFractal.addAnchor(nodeX, nodeY);
            pen.setFill(Color.RED);
            pen.fillOval(nodeX - 2, nodeY - 2, 5, 5);
            pen.setFill(Color.WHITE);
        });
        
        pen.setFill(Color.WHITE);
        
        AnimationTimer draw = new AnimationTimer() {
            long prev = 0;

            @Override
            public void handle(long now) {
                if (now - prev < 10000000) {
                    return;
                }

                for (int i = 0; i < 100; i++) {
                    activeFractal.iterate();
                    pen.fillRect(activeFractal.getCurrentX(), activeFractal.getCurrentY(), 0.5, 0.5);
                }

                prev = now;
            }
        };
        
        startButton.setOnAction(event -> {
            if (activeFractal.getSettings().getAnchors().isEmpty()) {
                return;
            }
            if (startButton.getText().equals("Start")) {
                draw.start();
                startButton.setText("Stop");
                ratio.disableProperty().set(true);
            } else {
                draw.stop();
                startButton.setText("Start");
                ratio.disableProperty().set(false);
            }
        });
        
        clearDrawn.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");
            
            activeFractal.removeFilled();
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);
            pen.setFill(Color.RED);
            
            for (Node anchor : activeFractal.getSettings().getAnchors()) {
                int xCoord = anchor.getX();
                int yCoord = anchor.getY();
                
                pen.fillOval(xCoord - 2, yCoord - 2, 5, 5);
            }
            
            pen.setFill(Color.WHITE);
            ratio.disableProperty().set(false);
        });
        
        clearCanvas.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");
            
            activeFractal = new Fractal(screenWidth, screenHeight);
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);

            ratio.disableProperty().set(false);
        });
        
        Scene scene = new Scene(bPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
