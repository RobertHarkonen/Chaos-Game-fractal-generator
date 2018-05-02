
package chaosgame.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A class that assists in building the user interface
 * used in the ChaosGameUI class. The UI features are hard coded and
 * should only be changed if the default layout changes (such as screen size).
 * @author Robert
 */
public class UIBuilder {
    
    private int width;
    private int height;
    
    public UIBuilder(int w, int h) {
        this.width = w;
        this.height = h;
    }
    
    public VBox createSavePanel() {
        VBox savePanel = new VBox();
        savePanel.setSpacing(10);
        savePanel.setPadding(new Insets(10, 5, 10, 5));
        ComboBox<String> saves = new ComboBox();
        saves.setMaxWidth(Double.MAX_VALUE);
        saves.setValue("");
        saves.setVisibleRowCount(10);
        
        Text saveDescription = new Text("Saved settings:");
        HBox loadDeleteBox = new HBox();
        Button load = new Button("Load");
        Button delete = new Button("Delete");
        load.setMaxWidth(Double.MAX_VALUE);
        delete.setMaxWidth(Double.MAX_VALUE);
        
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
        VBox saveHelperBox = new VBox();
        saveHelperBox.setSpacing(10);
        saveHelperBox.setPadding(new Insets(5, 5, 5, 5));
        saveHelperBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        saveHelperBox.getChildren().addAll(saveInputDescription, saveInput, saveButton);
        
        savePanel.getChildren().addAll(loadDeleteHelperBox, saveHelperBox);
        
        return savePanel;
    }
    
    public Canvas createCanvas() {
        return new Canvas(width, height);
    }
    
    public VBox createControlPanel() {
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
        
        ratioBox.getChildren().addAll(ratioDescription, ratio);
        
        VBox speedControlBox = new VBox(10);
        speedControlBox.setPadding(new Insets(5, 5, 5, 5));
        speedControlBox.setBorder(new Border(new BorderStroke(Color.DARKGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text speedControlDescription = new Text("Iterations per cycle:\n (enter to confirm)");
        TextField speedControl = new TextField("10000");
        speedControl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    speedControl.setText(newValue.replaceAll("[^\\d]", ""));
                }
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
        
        grainSizeBox.getChildren().addAll(grainSizeDescription, grainSizeSlider);
        
        ComboBox colorPick = new ComboBox();
        colorPick.getItems().addAll("White", "Gray", "Red", "Orange", "Yellow",
                "Green", "Blue", "Indigo", "Violet");
        colorPick.setPromptText("Drawing color");
        controlPanel.getChildren().addAll(startButton, clearDrawn, clearCanvas,
                speedControlBox, grainSizeBox, ratioBox, repeatRule, colorPick);
        
        return controlPanel;
    }
    
    public BorderPane createLayout() {
        BorderPane bPane = new BorderPane();
        bPane.setLeft(createControlPanel());
        bPane.setCenter(createCanvas());
        bPane.setRight(createSavePanel());
        
        return bPane;
    }
}
