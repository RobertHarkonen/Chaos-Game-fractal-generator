package chaosgame.ui;

import chaosgame.domain.*;          //temporary, set to only include Fractal
import dao.SettingsDAO;
import java.sql.SQLException;
import java.util.Comparator;
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
import javax.swing.JComboBox;

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
    public void start(Stage stage) throws SQLException {
//        SettingsDAO sd = new SettingsDAO();
//        Settings testsettings = new Settings(0.5);
//        testsettings.addAnchor(new Node(100, 100, Nodetype.ANCHOR));
//        testsettings.addAnchor(new Node(100, 200, Nodetype.ANCHOR));
//        sd.saveToDatabase(testsettings);
//        Settings s2 = sd.getFromDatabase("default");
//        System.out.println(s2.getKey());
//        System.out.println(s2.getAnchors() == null);
//        System.out.println(s2.getPrev() == null);
//        System.out.println(s2.getAnchors().size() == 2);
        
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
        
        VBox savePanel = new VBox();
        savePanel.setSpacing(10);
        savePanel.setPadding(new Insets(10, 5, 10, 5));
        ComboBox<String> saves = new ComboBox();
        saves.setMaxWidth(Double.MAX_VALUE);
        saves.setValue("");
        saves.setVisibleRowCount(10);
//        saves.setPromptText("");
        for (String storedSetting : activeFractal.storedSettings()) {
            saves.getItems().add(storedSetting);
        }
        Text saveDescription = new Text("Saved settings:");
        HBox loadDeleteBox = new HBox();
        Button load = new Button("Load");
        Button delete = new Button("Delete");
        load.setMaxWidth(Double.MAX_VALUE);
        delete.setMaxWidth(Double.MAX_VALUE);
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
        loadDeleteBox.setSpacing(10);
        loadDeleteBox.getChildren().addAll(load, delete);
        VBox loadDeleteHelperBox = new VBox();
        loadDeleteHelperBox.setSpacing(10);
        loadDeleteHelperBox.setPadding(new Insets(5, 5, 5, 5));
        loadDeleteHelperBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        loadDeleteHelperBox.getChildren().addAll(saveDescription, saves, load, delete);
        
        Text saveInputDescription = new Text("Save current settings:\n(enter name)");
        TextField saveInput = new TextField("");
        saveInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (newValue.length() > 20) {
                    saveInput.setText(newValue.substring(0, 20));
                }
            }
        });
        Button saveButton = new Button("Save");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setOnAction(event -> {
            if (saveInput.getText().isEmpty()) {
                return;
            }
            activeFractal.saveSettings(saveInput.getText());
            saves.getItems().add(saveInput.getText());
            saves.getItems().sort((String o1, String o2) -> o1.compareTo(o2));
        });
        VBox saveHelperBox = new VBox();
        saveHelperBox.setSpacing(10);
        saveHelperBox.setPadding(new Insets(5, 5, 5, 5));
        saveHelperBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        saveHelperBox.getChildren().addAll(saveInputDescription, saveInput, saveButton);
        
        savePanel.getChildren().addAll(loadDeleteHelperBox, saveHelperBox);
        bPane.setRight(savePanel);
        
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
                savePanel.disableProperty().set(true);
                ratioBox.disableProperty().set(true);
                repeatRule.disableProperty().set(true);
            } else {
                draw.stop();
                startButton.setText("Start");
                savePanel.disableProperty().set(false);
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
            savePanel.disableProperty().set(false);
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
            
            savePanel.disableProperty().set(false);
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
