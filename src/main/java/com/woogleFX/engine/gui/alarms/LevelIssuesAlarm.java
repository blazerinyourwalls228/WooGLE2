package com.woogleFX.engine.gui.alarms;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public class LevelIssuesAlarm {

    public static boolean show(ArrayList<String> error) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.setHeaderText("This level has SUPER CRITICAL issues!");
        StringBuilder errorBuilder = new StringBuilder();
        for (String text : error) errorBuilder.append(text).append("\n");
        alert.setContentText(errorBuilder.toString());
        alert.setTitle("Level Issues");
        alert.getButtonTypes().set(0, new ButtonType("OK, don't save"));
        alert.getButtonTypes().set(1, new ButtonType("Save anyway"));
        alert.showAndWait();
        return alert.getResult().getText().equals("OK, don't save");
    }

}
