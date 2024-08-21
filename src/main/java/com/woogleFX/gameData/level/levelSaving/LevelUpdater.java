package com.woogleFX.gameData.level.levelSaving;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.engine.gui.alarms.AskForLevelNameAlarm;
import com.woogleFX.engine.gui.alarms.ErrorAlarm;
import com.woogleFX.engine.gui.alarms.LevelIssuesAlarm;
import com.woogleFX.file.fileExport.GOOWriter;
import com.woogleFX.file.fileExport.Goo2modExporter;
import com.woogleFX.file.fileExport.XMLUtility;
import com.woogleFX.gameData.ball._Ball;
import com.woogleFX.engine.fx.hierarchy.FXHierarchy;
import com.woogleFX.engine.fx.FXLevelSelectPane;
import com.woogleFX.engine.fx.FXPropertiesView;
import com.woogleFX.engine.fx.FXStage;
import com.woogleFX.file.FileManager;
import com.woogleFX.file.fileExport.GoomodExporter;
import com.woogleFX.gameData.level.*;
import com.woogleFX.gameData.level.levelOpening.LevelLoader;
import com.worldOfGoo.level.BallInstance;
import com.worldOfGoo.resrc.Resources;
import com.worldOfGoo.resrc.ResrcImage;
import com.worldOfGoo.resrc.Sound;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class LevelUpdater {

    private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);


    public static void saveLevel(_Level level) {
        GameVersion version = level.getVersion();
        if (saveSpecificLevel(level, version)) {
            level.setLastSavedUndoPosition(level.undoActions.size());
            if (level.getEditingStatus() != LevelTab.NO_UNSAVED_CHANGES) {
                level.setEditingStatus(LevelTab.NO_UNSAVED_CHANGES, true);
            }
        }
    }


    public static boolean saveSpecificLevel(_Level level, GameVersion version) {

        boolean okayToSave = true;

        // Check for errors in level objects
        if (!LevelVerifier.verifyEntireLevel(level)) {
            // Fail to save
            ErrorAlarm.show("Level could not be verified");
            okayToSave = false;
        }

        if (level instanceof WOG2Level wog2Level) {

            ArrayList<String> levelErrors = LevelVerifier.checkForWoG2Errors(wog2Level);

            if (!levelErrors.isEmpty()) {
                if (LevelIssuesAlarm.show(levelErrors)) return false;
            }

        }
        // TODO: check for game errors (stuff like there being a levelexit but no pipe)

        if (!okayToSave) return false;

        if (level instanceof WOG1Level) {

            try {
                LevelWriter.saveAsXML(level, FileManager.getGameDir(version) + "/res/levels/" + level.getLevelName(),
                        version, false, true);
                return true;
            } catch (IOException e) {
                ErrorAlarm.show(e);
                return false;
            }

        } else if (level instanceof WOG2Level) {

            StringBuilder export = new StringBuilder();
            GOOWriter.recursiveGOOExport(export, ((WOG2Level) level).getLevel(), 0);
            EditorObject addinObject = level.getAddinObject();
            String addin = XMLUtility.fullAddinXMLExport("", addinObject, 0);

            try {
                Files.write(Path.of(FileManager.getGameDir(version) + "/res/levels/" + level.getLevelName() + ".addin.xml"), Collections.singleton(addin), StandardCharsets.UTF_8);
                Files.writeString(Path.of(FileManager.getGameDir(version) + "/res/levels/" + level.getLevelName() + ".wog2"), export.toString());
                return true;
            } catch (IOException e) {
                ErrorAlarm.show(e);
                return false;
            }

        }

        return false;

    }


    public static void saveAll() {
        int selectedIndex = FXLevelSelectPane.getLevelSelectPane().getSelectionModel().getSelectedIndex();
        for (Tab tab : FXLevelSelectPane.getLevelSelectPane().getTabs().toArray(new Tab[0])) {
            LevelTab levelTab = (LevelTab) tab;
            if (levelTab.getLevel().getEditingStatus() == LevelTab.UNSAVED_CHANGES) {
                if (saveSpecificLevel(levelTab.getLevel(), levelTab.getLevel().getVersion())) {
                    levelTab.getLevel().setEditingStatus(LevelTab.NO_UNSAVED_CHANGES, false);
                }
            }
        }
        FXLevelSelectPane.getLevelSelectPane().getSelectionModel().select(selectedIndex);
    }

    public static void playLevel(_Level level) {
        if (level.getVersion() == GameVersion.VERSION_WOG1_OLD) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        FileManager.getGameDir(GameVersion.VERSION_WOG1_OLD) + "/WorldOfGoo.exe", level.getLevelName());
                processBuilder.directory(new File(FileManager.getGameDir(GameVersion.VERSION_WOG1_OLD)));
                processBuilder.start();
            } catch (Exception e) {
                ErrorAlarm.show(e);
            }
        } else if (level.getVersion() == GameVersion.VERSION_WOG2) {

            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        new File(FileManager.getGameDir(GameVersion.VERSION_WOG2)).getParent().replaceAll("\\\\", "/") + "/" + FileManager.get2ExtensionFilter().getExtensions().get(0));
                processBuilder.directory(new File(FileManager.getGameDir(GameVersion.VERSION_WOG2)));
                processBuilder.start();
            } catch (Exception e) {
                ErrorAlarm.show(e);
            }

        } else {

            // TODO figure something out to play in 1.5
            ErrorAlarm.show("Playing is only supported for 1.3. :(");

        }

    }

    public static void renameLevel(_Level level) {
        if (level != null) {
            AskForLevelNameAlarm.show("changeName", level.getVersion());
        }
    }

    public static void renameLevel(_Level level, String text) {

        logger.info("Renaming " + level.getLevelName() + " to " + text);

        String start = FileManager.getGameDir(level.getVersion());

        /* Change level name in directory */
        File originalLevelDirectory = new File(start + "/res/levels/" + level.getLevelName());
        File levelDirectory = new File(start + "/res/levels/" + text);
        if (!originalLevelDirectory.renameTo(levelDirectory)) {
            ErrorAlarm.show("Could not rename level! (" + level.getLevelName() + " to " + text + ")");
            return;
        }

        /* Change the names of the scene, level, resrc, addin, text files */
        File[] levelParts = levelDirectory.listFiles();
        if (levelParts == null) return;

        for (File levelPart : levelParts) {
            if (levelPart.getName().length() >= level.getLevelName().length()
                    && levelPart.getName().startsWith(level.getLevelName())) {
                if (!levelPart.renameTo(new File(start + "/res/levels/" + text + "/" + text
                        + levelPart.getName().substring(level.getLevelName().length())))) {
                    ErrorAlarm.show("Could not rename level! (" + level.getLevelName() + " to " + text + ")");
                    return;
                }
            }
        }

        /* Edit every resource */
        for (EditorObject resource : ((WOG1Level)level).getResrc()) {

            if (resource instanceof Resources) {

                resource.setAttribute("id", "scene_" + text);

            } else if (resource instanceof ResrcImage || resource instanceof Sound) {

                String previousID = resource.getAttribute("id").stringValue();
                String newID = previousID.replaceAll(level.getLevelName().toUpperCase(), text.toUpperCase());
                resource.setAttribute("id", newID);

                String previousPath = resource.getAttribute("path").stringValue();
                String newPath = previousPath.replaceAll(level.getLevelName(), text);
                resource.setAttribute("path", newPath);

            }

        }

        level.setLevelName(text);
        level.setEditingStatus(level.getEditingStatus(), true);

        saveLevel(level);

    }

    public static void deleteLevel(_Level level) {
        if (level == null) return;
        AskForLevelNameAlarm.show("delete", level.getVersion());
    }

    public static void deleteLevelForReal(_Level level) {

        if (level instanceof WOG1Level) {

            try {
                nuke(new File(FileManager.getGameDir(level.getVersion()) + "/res/levels/" + level.getLevelName()));
                TabPane levelSelectPane = FXLevelSelectPane.getLevelSelectPane();
                if (levelSelectPane.getTabs().size() == 1) {
                    FXLevelSelectPane.getLevelSelectPane().setMinHeight(0);
                    FXLevelSelectPane.getLevelSelectPane().setMaxHeight(0);
                    // If all tabs are closed, clear the side pane
                    FXHierarchy.getHierarchy().setRoot(null);
                    // Clear the properties pane too
                    FXPropertiesView.changeTableView(new EditorObject[]{});
                }
                levelSelectPane.getTabs().remove(levelSelectPane.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                ErrorAlarm.show(e);
            }

        } else if (level instanceof WOG2Level) {
            try {
                Files.delete(Path.of(FileManager.getGameDir(GameVersion.VERSION_WOG2) + "/res/levels/" + level.getLevelName() + ".wog2"));
            } catch (IOException e) {
                ErrorAlarm.show(e);
            }
        }

    }

    public static void nuke(File file) throws IOException {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) for (File child : children) {
                nuke(child);
            }
        }
        Files.delete(file.toPath());
    }

    public static void exportLevel(_Level level, boolean includeAddinInfo) {

        if (level instanceof WOG1Level) {

            String dir = FileManager.getGameDir(level.getVersion());

            FileChooser fileChooser = new FileChooser();
            if (!Files.exists(Path.of((dir + "/res/levels/" + level.getLevelName() + "/goomod")))) {
                try {
                    Files.createDirectories(Path.of((dir + "/res/levels/" + level.getLevelName() + "/goomod")));
                } catch (Exception e) {
                    ErrorAlarm.show(e);
                }
            }
            fileChooser.setInitialDirectory(new File((dir + "/res/levels/" + level.getLevelName() + "/goomod")));
            fileChooser.setInitialFileName(level.getLevelName());

            ExtensionFilter goomodFilter = new ExtensionFilter("World of Goo mod (*.goomod)", "*.goomod");
            fileChooser.getExtensionFilters().add(goomodFilter);
            File export = fileChooser.showSaveDialog(FXStage.getStage());

            ArrayList<_Level> levels = new ArrayList<>();
            levels.add(level);

            ArrayList<_Ball> balls = new ArrayList<>();
            for (EditorObject object : ((WOG1Level) level).getLevel())
                if (object instanceof BallInstance ballInstance)
                    if (!balls.contains(ballInstance.getBall())) balls.add(ballInstance.getBall());

            if (export != null) {
                try {
                    GoomodExporter.exportGoomod(export, levels, balls, level.getVersion(), includeAddinInfo);
                } catch (IOException e) {
                    logger.error("", e);
                }
            }

        } else if (level instanceof WOG2Level wog2Level) {

            try {
                Goo2modExporter.exportGoo2mod(wog2Level, includeAddinInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
