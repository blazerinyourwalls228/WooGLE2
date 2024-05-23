package com.WooGLEFX.Functions;

import com.WooGLEFX.EditorObjects.ObjectCreators.ObjectCreator;
import com.WooGLEFX.EditorObjects.ObjectUtil;
import com.WooGLEFX.EditorObjects._Ball;
import com.WooGLEFX.Engine.FX.*;
import com.WooGLEFX.File.FileManager;
import com.WooGLEFX.File.ResourceManagers.GlobalResourceManager;
import com.WooGLEFX.EditorObjects.ObjectCreators.BlankObjectGenerator;
import com.WooGLEFX.File.ResourceManagers.BallManager;
import com.WooGLEFX.Engine.GUI.Alarms;
import com.WooGLEFX.Engine.GUI.LevelSelector;
import com.WooGLEFX.EditorObjects.EditorObject;
import com.WooGLEFX.Structures.GameVersion;
import com.WooGLEFX.Structures.SimpleStructures.LevelTab;
import com.WooGLEFX.Structures.WorldLevel;
import com.WorldOfGoo.Level.BallInstance;
import com.WorldOfGoo.Level.Signpost;
import com.WorldOfGoo.Resrc.Resources;
import com.WorldOfGoo.Resrc.ResrcImage;
import com.WorldOfGoo.Resrc.Sound;
import com.WorldOfGoo.Scene.SceneLayer;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class LevelLoader {

    private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);


    public static void newLevel(GameVersion version) {
        Alarms.askForLevelName("new", version);
    }


    public static void openLevel(GameVersion version) {
        new LevelSelector(version).start(new Stage());
    }


    /** Creates a new, default level. */
    public static void newLevel(String name, GameVersion version) {
        logger.debug("New level");

        FXLevelSelectPane.getLevelSelectPane().setMinHeight(30);
        FXLevelSelectPane.getLevelSelectPane().setMaxHeight(30);

        ArrayList<EditorObject> sceneList = new ArrayList<>();
        sceneList.add(ObjectCreator.create("scene", null));
        ArrayList<EditorObject> levelList = new ArrayList<>();
        levelList.add(ObjectCreator.create("level", null));
        ArrayList<EditorObject> resourcesList = new ArrayList<>();
        resourcesList.add(ObjectCreator.create("ResourceManifest", null));
        ArrayList<EditorObject> addinList = new ArrayList<>();
        addinList.add(BlankObjectGenerator.generateBlankAddinObject(name));
        ArrayList<EditorObject> textList = new ArrayList<>();
        textList.add(ObjectCreator.create("strings", null));

        WorldLevel level = new WorldLevel(sceneList, levelList, resourcesList, addinList, textList, version);
        LevelManager.setLevel(level);
        level.setLevelName(name);
        FXEditorButtons.updateAllButtons();
        FXMenu.updateAllButtons();

        level.getSceneObject().setAttribute("backgroundcolor", "255,255,255");

        EditorObject resourcesThing = ObjectCreator.create("Resources", level.getResourcesObject());
        resourcesThing.setAttribute("id", "scene_" + level.getLevelName());
        resourcesThing.getTreeItem().setExpanded(true);

        for (EditorObject object : level.getScene()) {
            object.update();
        }

        for (EditorObject object : level.getLevel()) {
            object.update();
        }

        for (EditorObject object : level.getResources()) {
            object.update();
        }

        // Put everything in the hierarchy
        level.getSceneObject().getTreeItem().setExpanded(true);
        FXHierarchy.getHierarchy().setRoot(level.getSceneObject().getTreeItem());

        // Add items from the Scene to it
        FXPropertiesView.getPropertiesView().setRoot(FXPropertiesView.makePropertiesViewTreeItem(level.getSceneObject()));

        LevelTab levelSelectButton = FXLevelSelectPane.levelSelectButton(level);
        FXLevelSelectPane.getLevelSelectPane().getTabs().add(levelSelectButton);

        int numTabs = FXLevelSelectPane.getLevelSelectPane().getTabs().size();
        double tabSize = 1 / (numTabs + 1.0);
        FXLevelSelectPane.getLevelSelectPane().setTabMaxWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);
        FXLevelSelectPane.getLevelSelectPane().setTabMinWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);

        LevelUpdater.saveLevel(level);

        level.setLevelTab(levelSelectButton);
        level.setEditingStatus(LevelTab.NO_UNSAVED_CHANGES, true);
        FXLevelSelectPane.getLevelSelectPane().getSelectionModel().select(levelSelectButton);
        LevelManager.onSetLevel(level);
    }


    public static final ArrayList<String> failedResources = new ArrayList<>();


    public static void openLevel(String levelName, GameVersion version) {
        // Don't open a level if none selected
        if (levelName == null || levelName.isEmpty()) {
            return;
        }
        // Don't open a level if it's already open
        for (Tab tab : FXLevelSelectPane.getLevelSelectPane().getTabs()) {
            if (tab.getText().equals(levelName)) {
                FXLevelSelectPane.getLevelSelectPane().getSelectionModel().select(tab);
                return;
            }
        }
        failedResources.clear();

        LevelManager.setVersion(version);

        WorldLevel level;

        try {
            level = FileManager.openLevel(levelName, version);
            LevelManager.setLevel(level);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Alarms.errorMessage(e);
            return;
        }

        level.setLevelName(levelName);
        FXEditorButtons.updateAllButtons();
        FXMenu.updateAllButtons();

        FXLevelSelectPane.getLevelSelectPane().setMinHeight(30);
        FXLevelSelectPane.getLevelSelectPane().setMaxHeight(30);

        for (EditorObject object : level.getLevel()) {
            if (object instanceof BallInstance) {
                boolean alreadyIn = false;
                for (_Ball ball : BallManager.getImportedBalls()) {
                    if (ball.getObjects().get(0).getAttribute("name").equals(object.getAttribute("type"))
                            && ball.getVersion() == level.getVersion()) {
                        alreadyIn = true;
                        break;
                    }
                }
                if (!alreadyIn) {
                    try {
                        _Ball ball2 = FileManager.openBall(object.getAttribute("type").stringValue(), version);

                        for (EditorObject resrc : FileManager.commonBallResrcData) {
                            GlobalResourceManager.addResource(resrc, version);
                        }

                        if (ball2 != null) {
                            ball2.makeImages(version);
                            ball2.setVersion(version);

                            BallManager.getImportedBalls().add(ball2);
                        }
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        if (!failedResources
                                .contains("Ball: " + object.getAttribute("type").stringValue() + " (version " + (version == GameVersion.OLD ? 1.3 : 1.5) + ")")) {
                            failedResources.add("Ball: " + object.getAttribute("type").stringValue() + " (version " + (version == GameVersion.OLD ? 1.3 : 1.5) + ")");
                        }
                    }
                }
            }
        }

        for (EditorObject object : level.getResources()) {
            if (object instanceof ResrcImage) {
                GlobalResourceManager.addResource(object, level.getVersion());
            }
        }

        for (EditorObject object : level.getScene()) {
            object.update();
        }

        for (EditorObject object : level.getLevel()) {
            object.update();
        }

        for (EditorObject object : level.getResources()) {
            object.update();
        }

        // Put everything in the hierarchy
        level.getSceneObject().getTreeItem().setExpanded(true);
        FXHierarchy.getHierarchy().setRoot(level.getSceneObject().getTreeItem());

        // Add items from the Scene to it
        FXPropertiesView.getPropertiesView().setRoot(FXPropertiesView.makePropertiesViewTreeItem(level.getSceneObject()));

        LevelTab levelSelectButton = FXLevelSelectPane.levelSelectButton(level);
        FXLevelSelectPane.getLevelSelectPane().getTabs().add(levelSelectButton);

        int numTabs = FXLevelSelectPane.getLevelSelectPane().getTabs().size();
        double tabSize = 1 / (numTabs + 1.0);
        FXLevelSelectPane.getLevelSelectPane().setTabMaxWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);
        FXLevelSelectPane.getLevelSelectPane().setTabMinWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);

        if (!failedResources.isEmpty()) {
            StringBuilder fullError = new StringBuilder();
            for (String resource : failedResources) {
                fullError.append("\n").append(resource);
            }
            Alarms.loadingResourcesError(fullError.substring(1));
        }

        level.setLevelTab(levelSelectButton);
        level.setEditingStatus(LevelTab.NO_UNSAVED_CHANGES, true);
        FXLevelSelectPane.getLevelSelectPane().getSelectionModel().select(levelSelectButton);
        LevelManager.onSetLevel(level);
    }


    public static void cloneLevel(String name, GameVersion version) {
        FXLevelSelectPane.getLevelSelectPane().setMinHeight(30);
        FXLevelSelectPane.getLevelSelectPane().setMaxHeight(30);

        ArrayList<EditorObject> sceneList = new ArrayList<>();
        ArrayList<EditorObject> levelList = new ArrayList<>();
        ArrayList<EditorObject> resourcesList = new ArrayList<>();
        ArrayList<EditorObject> addinList = new ArrayList<>();
        ArrayList<EditorObject> textList = new ArrayList<>();

        FileManager.supremeAddToList(resourcesList, ObjectUtil.deepClone(LevelManager.getLevel().getResourcesObject(), null));
        FileManager.supremeAddToList(sceneList, ObjectUtil.deepClone(LevelManager.getLevel().getSceneObject(), null));
        FileManager.supremeAddToList(levelList, ObjectUtil.deepClone(LevelManager.getLevel().getLevelObject(), null));
        // Generate new addin object. idk why cloning it doesn't work, but this is arguably better anyway
        FileManager.supremeAddToList(addinList, BlankObjectGenerator.generateBlankAddinObject(name));
        FileManager.supremeAddToList(textList, ObjectUtil.deepClone(LevelManager.getLevel().getTextObject(), null));

        String oldLevelName = LevelManager.getLevel().getLevelName();
        WorldLevel level = new WorldLevel(sceneList, levelList, resourcesList, addinList, textList, version);
        LevelManager.setLevel(level);

        level.setLevelName(name);
        FXEditorButtons.updateAllButtons();
        FXMenu.updateAllButtons();

        for (EditorObject object : level.getResources()) {
            if (object instanceof Resources) {
                object.setAttribute("id", "scene_" + name);
            } else if (object instanceof ResrcImage || object instanceof Sound) {
                object.setAttribute("id", object.getAttribute("id").stringValue().replaceAll(oldLevelName.toUpperCase(), name.toUpperCase()));
            }
            GlobalResourceManager.addResource(object, version);
            object.update();
        }

        for (EditorObject object : level.getScene()) {
            if (object instanceof SceneLayer) {
                object.setAttribute("image", object.getAttribute("image").stringValue().replaceAll(oldLevelName, name));
            }
            object.update();
        }

        for (EditorObject object : level.getLevel()) {
            if (object instanceof Signpost) {
                object.setAttribute("image", object.getAttribute("image").stringValue().replaceAll(oldLevelName, name));
            }
            object.update();
        }

        // Put everything in the hierarchy
        level.getSceneObject().getTreeItem().setExpanded(true);
        FXHierarchy.getHierarchy().setRoot(level.getSceneObject().getTreeItem());

        // Add items from the Scene to it
        FXPropertiesView.getPropertiesView().setRoot(FXPropertiesView.makePropertiesViewTreeItem(level.getSceneObject()));

        LevelTab levelSelectButton = FXLevelSelectPane.levelSelectButton(level);
        FXLevelSelectPane.getLevelSelectPane().getTabs().add(levelSelectButton);

        int numTabs = FXLevelSelectPane.getLevelSelectPane().getTabs().size();
        double tabSize = 1 / (numTabs + 1.0);
        FXLevelSelectPane.getLevelSelectPane().setTabMaxWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);
        FXLevelSelectPane.getLevelSelectPane().setTabMinWidth(tabSize * (FXLevelSelectPane.getLevelSelectPane().getWidth() - 15) - 15);

        LevelUpdater.saveLevel(level);

        level.setLevelTab(levelSelectButton);
        level.setEditingStatus(LevelTab.NO_UNSAVED_CHANGES, true);
        FXLevelSelectPane.getLevelSelectPane().getSelectionModel().select(levelSelectButton);
        LevelManager.onSetLevel(level);
    }


    public static void cloneLevel() {
        GameVersion version = LevelManager.getVersion();
        Alarms.askForLevelName("clone", version);
    }


}
