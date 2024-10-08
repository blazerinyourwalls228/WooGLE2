package com.woogleFX.engine.gui;

import com.woogleFX.file.resourceManagers.BaseGameResources;
import com.woogleFX.file.FileManager;
import com.woogleFX.gameData.ball.BallManager;
import com.woogleFX.gameData.level.GameVersion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class BallSelector extends Application {

    private final GameVersion oldVersion;
    private final GameVersion newVersion;

    public BallSelector(GameVersion oldVersion, GameVersion newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public static String selected = "";
    public static Label selectedLabel = null;

    public static File ballDir = null;


    private void addBallLabels(Stage stage, VBox allBallsBox) {

        if (ballDir == null) return;

        File[] balls = ballDir.listFiles();
        if (balls == null) return;

        for (File ballFile : balls) addBallLabel(ballFile, stage, allBallsBox);

    }


    private void addBallLabel(File ballFile, Stage stage, VBox allBallsBox) {

        for (String ballName : BaseGameResources.GOO_BALL_TYPES) if (ballName.equals(ballFile.getName())) return;

        Label label = new Label(ballFile.getName());

        label.setPrefWidth(376);

        label.setOnMouseClicked(mouseEvent -> {
            if (selectedLabel == label) {
                BallManager.saveBallInVersion(selected, oldVersion, newVersion);
                stage.close();
            } else if (selectedLabel != null) {
                selectedLabel.setStyle("");
            }
            label.setStyle("-fx-background-color: #C0E0FFFF");
            selectedLabel = label;
            selected = label.getText();
        });

        allBallsBox.getChildren().add(label);

    }


    @Override
    public void start(Stage stage) {

        Pane all = new Pane();

        VBox allBallsBox = new VBox();

        ballDir = new File(FileManager.getGameDir(oldVersion) + "/res/balls");

        ScrollPane realPane = new ScrollPane(allBallsBox);

        realPane.setPrefWidth(376);
        realPane.setPrefHeight(286);

        Label selectBallToSave = new Label("Select ball to save:");

        addBallLabels(stage, allBallsBox);

        Button openButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        openButton.setLayoutX(234);
        openButton.setLayoutY(332);
        cancelButton.setLayoutX(315);
        cancelButton.setLayoutY(332);

        openButton.setOnAction(actionEvent -> {
            BallManager.saveBallInVersion(selected, oldVersion, newVersion);
            stage.close();
        });

        cancelButton.setOnAction(actionEvent -> stage.close());

        all.getChildren().addAll(realPane, selectBallToSave, openButton, cancelButton);

        realPane.setLayoutX(12);
        realPane.setLayoutY(38);

        selectBallToSave.setLayoutX(12);
        selectBallToSave.setLayoutY(12);

        stage.setScene(new Scene(all, 400, 375));
        stage.setResizable(false);
        stage.show();

    }

}
