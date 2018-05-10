package chaosgame.ui;

import chaosgame.domain.Fractal;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
        
        UIBuilder builder = new UIBuilder(screenWidth, screenHeight);
        BorderPane baseLayout = new BorderPane();
        
        VBox controlPanel = builder.createControlPanel();
        Canvas canvas = builder.createCanvas();
        VBox savePanel = builder.createSavePanel();
        baseLayout.setLeft(controlPanel);
        baseLayout.setCenter(canvas);
        baseLayout.setRight(savePanel);
        
        //*************** GETTING REFERENCES FOR UI ELEMENTS ****************
        
        Button startButton = (Button) controlPanel.getChildren().get(0);
        Button clearDrawn = (Button) controlPanel.getChildren().get(1);
        Button clearCanvas = (Button) controlPanel.getChildren().get(2);
        
        VBox speedControlBox = (VBox) controlPanel.getChildren().get(3);
        TextField speedControl = (TextField) speedControlBox.getChildren().get(1);
        VBox grainSizeBox = (VBox) controlPanel.getChildren().get(4);
        Slider grainSizeSlider = (Slider) grainSizeBox.getChildren().get(1);
        VBox ratioBox = (VBox) controlPanel.getChildren().get(5);
        Slider ratio = (Slider) ratioBox.getChildren().get(1);
        RadioButton repeatRule = (RadioButton) controlPanel.getChildren().get(6);
        ComboBox colorPick = (ComboBox) controlPanel.getChildren().get(7);
        
        VBox loadDeleteHelperBox = (VBox) savePanel.getChildren().get(0);
        ComboBox<String> saves = (ComboBox) loadDeleteHelperBox.getChildren().get(1);
        Button load = (Button) loadDeleteHelperBox.getChildren().get(2);
        Button delete = (Button) loadDeleteHelperBox.getChildren().get(3);
        VBox saveHelperBox = (VBox) savePanel.getChildren().get(1);
        TextField saveInput = (TextField) saveHelperBox.getChildren().get(1);
        Button saveButton = (Button) saveHelperBox.getChildren().get(2);
        
        //********************** INITIALIZING VALUES *************************
        
        GraphicsContext pen = canvas.getGraphicsContext2D();
        pen.setFill(Color.BLACK);
        pen.fillRect(0, 0, screenWidth, screenHeight);
        pen.setFill(Color.WHITE);
        
        for (String storedSetting : activeFractal.storedSettings()) {
            saves.getItems().add(storedSetting);
        }
        
        //******************* SETTING UI FUNCTIONALITY ***********************
        
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
        
        grainSizeSlider.setOnMouseDragged(event -> {
            grainSize = grainSizeSlider.getValue();
            activeFractal.setGrainSize(grainSize);
        });
        
        ratio.setOnMouseDragged(event -> {
            activeFractal.getSettings().setRatio(ratio.getValue());
        });

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
        
        repeatRule.setOnAction(event -> {
            activeFractal.getSettings().toggleRepeatRule();
        });
        
        canvas.setOnMouseClicked(event -> {
            int nodeX = (int) Math.round(event.getX());
            int nodeY = (int) Math.round(event.getY());
            activeFractal.addAnchor(nodeX, nodeY);
            Paint temp = pen.getFill();
            pen.setFill(Color.RED);
            pen.fillOval(nodeX - 2, nodeY - 2, 5, 5);
            pen.setFill(temp);
        });
        
        load.setOnAction(event -> {
            if (saves.getValue().isEmpty()) {
                return;
            }
            if (activeFractal.getSettings().getRepeatRule()) {
                repeatRule.fire();
            }
            activeFractal = new Fractal(activeFractal.loadSettings(saves.getValue()));
            clearDrawn.fire();
            ratio.setValue(activeFractal.getSettings().getRatio());
            grainSizeSlider.setValue(activeFractal.getSettings().getGrainSize());
            grainSize = grainSizeSlider.getValue();
            if (activeFractal.getSettings().getRepeatRule()) {
                repeatRule.fire();
                activeFractal.getSettings().toggleRepeatRule();
            }
        });
        
        delete.setOnAction(event -> {
            if (saves.getValue().isEmpty()) {
                return;
            }
            activeFractal.removeSettings(saves.getValue());
            saves.getItems().remove(saves.getValue());
            saves.getItems().sort((String o1, String o2) -> o1.compareTo(o2));
            saves.setValue("");
            saves.setPromptText("");
        });
        
        saveButton.setOnAction(event -> {
            if (saveInput.getText().isEmpty()) {
                return;
            }
            activeFractal.saveSettings(saveInput.getText());
            saves.getItems().add(saveInput.getText());
            saves.getItems().sort((String o1, String o2) -> o1.compareTo(o2));
        });

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
                savePanel.disableProperty().set(true);
            } else {
                draw.stop();
                startButton.setText("Start");
                savePanel.disableProperty().set(false);
            }
        });

        clearDrawn.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");

            Paint temp = pen.getFill();
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);
            pen.setFill(Color.RED);

            int[][] coords = activeFractal.getAnchorCoords();
            for (int[] coord : coords) {
                pen.fillOval(coord[0] - 2, coord[1] - 2, 5, 5);
            }

            pen.setFill(temp);
            savePanel.disableProperty().set(false);
        });

        clearCanvas.setOnAction(event -> {
            draw.stop();
            startButton.setText("Start");

            if (activeFractal.getSettings().getRepeatRule()) {
                repeatRule.fire();
            }
            activeFractal.reset();
            ratio.setValue(activeFractal.getSettings().getRatio());
            Paint temp = pen.getFill();
            pen.setFill(Color.BLACK);
            pen.fillRect(0, 0, screenWidth, screenHeight);
            pen.setFill(temp);
            
            savePanel.disableProperty().set(false);
        });
        
        Scene scene = new Scene(baseLayout);
        stage.setScene(scene);
        stage.setTitle("Chaos Game");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
