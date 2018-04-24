package chaosgame.ui;

import chaosgame.domain.Fractal;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Robert
 */
public class ChaosGameUI extends Application {

    int screenWidth;
    int screenHeight;
    int drawSpeed;
    double grainSize;
    Fractal activeFractal;

    @Override
    public void start(Stage stage) {
        screenWidth = 800;
        screenHeight = 600;
        drawSpeed = 10000;
        grainSize = 0.5;
        activeFractal = new Fractal(screenWidth, screenHeight);

        BorderPane bPane = new BorderPane();
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10, 5, 10, 5));

        Button startButton = new Button("Start");
        Button clearDrawn = new Button("Clear filled tiles");
        Button clearCanvas = new Button("Clear everything");
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setPrefHeight(45);
        clearDrawn.setMaxWidth(Double.MAX_VALUE);
        clearCanvas.setMaxWidth(Double.MAX_VALUE);
        RadioButton repeatRule = new RadioButton("Repeat rule");

        VBox ratioBox = new VBox(10);
        ratioBox.setPadding(new Insets(5, 5, 5, 5));
        ratioBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text ratioDescription = new Text("Ratio:");
        Slider ratio = new Slider(0, 1.5, 0.5);
        ratio.setMajorTickUnit(1);
        ratio.setMinorTickCount(3);
        ratio.showTickLabelsProperty().set(true);
        ratio.showTickMarksProperty().set(true);
        ratio.setOnMouseDragged(event -> {
            activeFractal.getSettings().setRatio(ratio.getValue());
        });
        ratioBox.getChildren().addAll(ratioDescription, ratio);

        VBox speedControlBox = new VBox(10);
        speedControlBox.setPadding(new Insets(5, 5, 5, 5));
        speedControlBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text speedControlDescription = new Text("Iterations per cycle:\n (enter to confirm)");
        TextField speedControl = new TextField(Integer.toString(drawSpeed));
        speedControl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    speedControl.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        speedControl.setOnAction(event -> {
            if (speedControl.getText().isEmpty()) {
                speedControl.setText("1");
                drawSpeed = 1;
            } else {
                String s = speedControl.getText();
                int temp = Integer.parseUnsignedInt(s
                        .substring(0, 8 < s.length() ? 8 : s.length()));
                if (temp < 1) {
                    temp = 1;
                } else if (temp > 50000) {
                    temp = 50000;
                }
                speedControl.setText(Integer.toString(temp));
                drawSpeed = temp;
            }
        });

        speedControlBox.getChildren().addAll(speedControlDescription, speedControl);
        
        VBox grainSizeBox = new VBox(10);
        grainSizeBox.setPadding(new Insets(5, 5, 5, 5));
        grainSizeBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text grainSizeDescription = new Text("Grain size:");
        Slider grainSizeSlider = new Slider(0.1, 1.0, 0.5);
        grainSizeSlider.setMajorTickUnit(0.9);
        grainSizeSlider.setMinorTickCount(8);
        grainSizeSlider.snapToTicksProperty().set(true);
        grainSizeSlider.showTickLabelsProperty().set(true);
        grainSizeSlider.showTickMarksProperty().set(true);
        grainSizeSlider.setOnMouseDragged(event -> {
            grainSize = grainSizeSlider.getValue();
            activeFractal.setGrainSize(grainSize);
        });
        grainSizeBox.getChildren().addAll(grainSizeDescription, grainSizeSlider);
        
        ComboBox colorPick = new ComboBox();
        colorPick.getItems().addAll("White", "Gray", "Red", "Orange", "Yellow",
                "Green", "Blue", "Indigo", "Violet");
        colorPick.setPromptText("Drawing color");

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        bPane.setCenter(canvas);
        GraphicsContext pen = canvas.getGraphicsContext2D();
        pen.setFill(Color.BLACK);
        pen.fillRect(0, 0, screenWidth, screenHeight);

        colorPick.setOnAction(event -> {
            switch (colorPick.getValue().toString()) {
                case "White":
                    pen.setFill(Color.WHITE);
                    break;
                case "Gray":
                    pen.setFill(Color.GRAY);
                    break;
                case "Red":
                    pen.setFill(Color.RED);
                    break;
                case "Orange":
                    pen.setFill(Color.ORANGE);
                    break;
                case "Yellow":
                    pen.setFill(Color.YELLOW);
                    break;
                case "Green":
                    pen.setFill(Color.GREEN);
                    break;
                case "Blue":
                    pen.setFill(Color.BLUE);
                    break;
                case "Indigo":
                    pen.setFill(Color.INDIGO);
                    break;
                case "Violet":
                    pen.setFill(Color.VIOLET);
                    break;
                default:
            }
        });

        controlPanel.getChildren().addAll(startButton, clearDrawn, clearCanvas,
                speedControlBox, grainSizeBox, ratioBox, repeatRule, colorPick);
        bPane.setLeft(controlPanel);

        canvas.setOnMouseClicked(event -> {
            int nodeX = (int) Math.round(event.getX());
            int nodeY = (int) Math.round(event.getY());
            activeFractal.addAnchor(nodeX, nodeY);
            Paint temp = pen.getFill();
            pen.setFill(Color.RED);
            pen.fillOval(nodeX - 2, nodeY - 2, 5, 5);
            pen.setFill(temp);
        });

        pen.setFill(Color.WHITE);

        AnimationTimer draw = new AnimationTimer() {
            long prev = 0;

            @Override
            public void handle(long now) {
                if (now - prev < 10000000) {
                    return;
                }

                for (int i = 0; i < drawSpeed; i++) {
                    activeFractal.iterate();
                    pen.fillRect(activeFractal.getCurrentX(), 
                            activeFractal.getCurrentY(), grainSize, grainSize);
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
                ratioBox.disableProperty().set(true);
                repeatRule.disableProperty().set(true);
            } else {
                draw.stop();
                startButton.setText("Start");
                ratioBox.disableProperty().set(false);
                repeatRule.disableProperty().set(false);
            }
        });

        clearDrawn.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");

            activeFractal.removeFilled();
            Paint temp = pen.getFill();
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);
            pen.setFill(Color.RED);

            int[][] coords = activeFractal.getAnchorCoords();
            for (int[] coord : coords) {
                pen.fillOval(coord[0] - 2, coord[1] - 2, 5, 5);
            }

            pen.setFill(temp);
            ratioBox.disableProperty().set(false);
            repeatRule.disableProperty().set(false);
        });

        clearCanvas.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");

            repeatRule.disableProperty().set(false);
            if (activeFractal.getSettings().getRepeatRule()) {
                repeatRule.fire();
            }
            activeFractal = new Fractal(screenWidth, screenHeight);
            ratio.setValue(activeFractal.getSettings().getRatio());
            Paint temp = pen.getFill();
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);
            pen.setFill(temp);

            ratioBox.disableProperty().set(false);
        });

        repeatRule.setOnAction(event -> {
            activeFractal.getSettings().toggleRepeatRule();
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
