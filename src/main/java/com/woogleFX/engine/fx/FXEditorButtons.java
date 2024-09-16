package com.woogleFX.engine.fx;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.ObjectManager;
import com.woogleFX.editorObjects.clipboardHandling.ClipboardManager;
import com.woogleFX.editorObjects.objectCreators.ObjectCreator;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.gameData.ball.*;
import com.woogleFX.engine.SelectionManager;
import com.woogleFX.file.FileManager;
import com.woogleFX.file.resourceManagers.ResourceManager;
import com.woogleFX.editorObjects.objectCreators.ObjectAdder;
import com.woogleFX.engine.undoHandling.UndoManager;
import com.woogleFX.engine.gui.PaletteReconfigurator;
import com.woogleFX.gameData.items.ItemManager;
import com.woogleFX.gameData.level.*;
import com.woogleFX.gameData.level.levelOpening.LevelLoader;
import com.woogleFX.gameData.level.levelSaving.LevelUpdater;
import com.worldOfGoo.ball.Part;
import com.worldOfGoo2.ball._2_Ball_Image;
import com.worldOfGoo2.ball._2_Ball_Part;
import com.worldOfGoo2.items._2_Item;
import com.worldOfGoo2.level._2_Level_BallInstance;
import com.worldOfGoo2.level._2_Level_Item;
import com.worldOfGoo2.misc._2_Point;
import com.worldOfGoo2.util.BallInstanceHelper;
import com.worldOfGoo2.util.ItemHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class FXEditorButtons {

    private static class DelayedTooltip extends Tooltip {
        // Tooltip with a shorter delay than the default
        public DelayedTooltip(String text) {
            super(text);
            setShowDelay(javafx.util.Duration.millis(150));
        }
    }


    private static ToolBar functionsToolbar;


    private static ToolBar oldGooballsToolbar;
    public static ToolBar getOldGooballsToolbar() {
        return oldGooballsToolbar;
    }


    private static ToolBar newGooballsToolbar;
    public static ToolBar getNewGooballsToolbar() {
        return newGooballsToolbar;
    }


    private static ToolBar sequelGooballsToolbar;
    public static ToolBar getSequelGooballsToolbar() {
        return sequelGooballsToolbar;
    }


    private static ToolBar nullGooballsToolbar;
    public static ToolBar getNullGooballsToolbar() {
        return nullGooballsToolbar;
    }


    private static ToolBar addObjectsToolbar;


    private static ToolBar newAddObjectsToolbar;


    private static void setIcon(Button button, String pathString) {
        button.setGraphic(new ImageView(FileManager.getIcon(pathString)));
    }



    public static Button createTemplateForBall(int size, _Ball ball) {

        double minX = 0;
        double minY = 0;
        double maxX = 0;
        double maxY = 0;

        for (EditorObject EditorObject : ball.getObjects()) {
            String state = "standing";

            if (EditorObject instanceof Part part) {

                boolean ok = false;

                if (part.getAttribute("state").stringValue().isEmpty()) {
                    ok = true;
                } else {
                    String word = part.getAttribute("state").stringValue();
                    while (word.contains(",")) {
                        if (word.substring(0, word.indexOf(",")).equals(state)) {
                            ok = true;
                            break;
                        }
                        word = word.substring(word.indexOf(",") + 1);
                    }
                    if (word.equals(state)) {
                        ok = true;
                    }
                }
                if (!ok) continue;

                double lowX;
                double highX;
                double lowY;
                double highY;

                String x = part.getAttribute("x").stringValue();
                String y = part.getAttribute("y").stringValue();

                double scale = Double.parseDouble(part.getAttribute("scale").stringValue());

                if (x.contains(",")) {
                    lowX = Double.parseDouble(x.substring(0, x.indexOf(",")));
                    highX = Double.parseDouble(x.substring(x.indexOf(",") + 1));
                } else {
                    lowX = Double.parseDouble(x);
                    highX = lowX;
                }
                if (y.contains(",")) {
                    lowY = Double.parseDouble(y.substring(0, y.indexOf(",")));
                    highY = Double.parseDouble(y.substring(y.indexOf(",") + 1));
                } else {
                    lowY = Double.parseDouble(y);
                    highY = lowY;
                }

                double myX = 0.5 * (highX - lowX) + lowX;
                double myY = 0.5 * (highY - lowY) + lowY;

                String[] imageStrings = part.getAttribute("image").listValue();

                if (imageStrings.length == 0) continue;

                String imageString = imageStrings[0];
                Image img;
                try {
                    img = ResourceManager.getImage(ball.getResources(), imageString, ball.getVersion());
                    if (img == null) continue;
                } catch (FileNotFoundException ignored) {
                    continue;
                }

                BufferedImage image = SwingFXUtils.fromFXImage(img, null);

                double iWidth = image.getWidth() * scale;
                double iHeight = image.getHeight() * scale;

                if (myX - iWidth / 2 < minX) minX = myX - iWidth / 2;
                if (-myY - iHeight / 2 < minY) minY = -myY - iHeight / 2;
                if (myX + iWidth / 2 > maxX) maxX = myX + iWidth / 2;
                if (-myY + iHeight / 2 > maxY) maxY = -myY + iHeight / 2;

            }
        }

        double width = maxX - minX;
        double height = maxY - minY;

        Button idk = new Button();
        if (width < 0 || height < 0) return idk;

        BufferedImage toWriteOn = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics writeGraphics = toWriteOn.getGraphics();

        for (EditorObject EditorObject : ball.getObjects()) {

            String state = "standing";

            if (EditorObject instanceof Part part) {

                boolean ok = false;

                if (part.getAttribute("state").stringValue().isEmpty()) {
                    ok = true;
                } else {
                    String word = part.getAttribute("state").stringValue();
                    while (word.contains(",")) {
                        if (word.substring(0, word.indexOf(",")).equals(state)) {
                            ok = true;
                            break;
                        }
                        word = word.substring(word.indexOf(",") + 1);
                    }
                    if (word.equals(state)) {
                        ok = true;
                    }
                }
                if (!ok) continue;

                String[] imageStrings = part.getAttribute("image").listValue();

                if (imageStrings.length == 0) continue;
                String imageString = imageStrings[0];
                Image img;
                try {
                    img = ResourceManager.getImage(ball.getResources(), imageString, ball.getVersion());
                    if (img == null) continue;
                } catch (FileNotFoundException ignored) {
                    continue;
                }

                double scale = part.getAttribute("scale").doubleValue();

                double lowX;
                double highX;
                double lowY;
                double highY;

                String x = part.getAttribute("x").stringValue();
                String y = part.getAttribute("y").stringValue();

                if (x.contains(",")) {
                    lowX = Double.parseDouble(x.substring(0, x.indexOf(",")));
                    highX = Double.parseDouble(x.substring(x.indexOf(",") + 1));
                } else {
                    lowX = Double.parseDouble(x);
                    highX = lowX;
                }
                if (y.contains(",")) {
                    lowY = Double.parseDouble(y.substring(0, y.indexOf(",")));
                    highY = Double.parseDouble(y.substring(y.indexOf(",") + 1));
                } else {
                    lowY = Double.parseDouble(y);
                    highY = lowY;
                }

                double myX = 0.5 * (highX - lowX) + lowX;
                double myY = 0.5 * (highY - lowY) + lowY;

                if (myY == 0) {
                    myY = -0;
                }

                double screenX = myX + toWriteOn.getWidth() / 2.0 - img.getWidth() * scale / 2;
                double screenY = -myY + toWriteOn.getHeight() / 2.0 - img.getHeight() * scale / 2;

                writeGraphics.drawImage(SwingFXUtils.fromFXImage(img, null), (int) screenX, (int) screenY,
                        (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);

                String[] pupilImageStrings = part.getAttribute("pupil").listValue();
                if (pupilImageStrings.length == 0) continue;

                String pupilImageString = pupilImageStrings[0];
                Image pupilImage;
                try {
                    pupilImage = ResourceManager.getImage(ball.getResources(), pupilImageString, ball.getVersion());
                    if (pupilImage == null) continue;
                } catch (FileNotFoundException ignored) {
                    continue;
                }

                double screenX2 = myX + toWriteOn.getWidth() / 2.0 - pupilImage.getWidth() * scale / 2;
                double screenY2 = -myY + toWriteOn.getHeight() / 2.0
                        - pupilImage.getHeight() * scale / 2;

                writeGraphics.drawImage(SwingFXUtils.fromFXImage(pupilImage, null), (int) screenX2,
                        (int) screenY2, (int) (pupilImage.getWidth() * scale),
                        (int) (pupilImage.getHeight() * scale), null);

            }

            double scaleFactor = (double) size / Math.max(toWriteOn.getWidth(), toWriteOn.getHeight());

            java.awt.Image tmp = toWriteOn.getScaledInstance((int) (toWriteOn.getWidth() * scaleFactor),
                    (int) (toWriteOn.getHeight() * scaleFactor), java.awt.Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage((int) (toWriteOn.getWidth() * scaleFactor),
                    (int) (toWriteOn.getHeight() * scaleFactor), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            idk.setGraphic(new ImageView(SwingFXUtils.toFXImage(dimg, null)));
        }

        idk.setPrefSize(size, size);
        idk.setOnAction(e -> {

            String name = ball.getObjects().get(0).getAttribute("name").stringValue();

            EditorObject ballInstance = ObjectCreator.create("BallInstance", ((WOG1Level)LevelManager.getLevel()).getLevelObject(), ball.getVersion());
            assert ballInstance != null;
            ballInstance.setAttribute("type", name);

            ((WOG1Level)LevelManager.getLevel()).getLevel().add(ballInstance);

            ObjectAdder.addAnything(ballInstance);

        });
        return idk;
    }


    public static Button createTemplateFor2Ball(int size, _2Ball ball) {

        ArrayList<_2_Ball_Image> images = new ArrayList<>();
        for (EditorObject editorObject : ball.getObjects()) if (editorObject instanceof _2_Ball_Part && editorObject.getAttribute("name").stringValue().equals(ball.getObjects().get(0).getChildren("bodyPart").get(0).getAttribute("partName").stringValue())) for (EditorObject child : editorObject.getChildren())
            if (child instanceof _2_Ball_Image ball_image) images.add(ball_image);

        double _scaleX = 1;
        double _scaleY = 1;
        if (!images.isEmpty()) {

            String imageString = images.get(0).getChildren().get(0).getAttribute("imageId").stringValue();

            BufferedImage image = AtlasManager.atlas.get(imageString);

            int _width = image.getWidth();
            int _height = image.getHeight();

            double width = ball.getObjects().get(0).getAttribute("width").doubleValue();
            double height = ball.getObjects().get(0).getAttribute("height").doubleValue();

            _scaleX = width / _width;
            _scaleY = height / _height;

        }

        Image image = BallInstanceHelper.createBallImageWoG2(null, ball, _scaleX, _scaleY, new Random(0));

        Button idk = new Button();
        if (image == null) return idk;
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(20);
        idk.setGraphic(imageView);

        idk.setPrefSize(size, size);
        idk.setOnAction(e -> {

            String name = ball.getObjects().get(0).getAttribute("name").stringValue();

            _2_Level_BallInstance ballInstance = (_2_Level_BallInstance)ObjectCreator.create2(
                _2_Level_BallInstance.class, ((WOG2Level)LevelManager.getLevel()).getLevel(), ball.getVersion());
            
            ballInstance.createPosition();
            ballInstance.setAttribute("type", name);
            ballInstance.setTypeID("balls");
            ballInstance.onLoaded();

            ((WOG2Level)LevelManager.getLevel()).getObjects().add(ballInstance);

            ObjectAdder.addAnything(ballInstance);

        });
        return idk;

    }


    public static void addBallsTo() {
        int size = 18;
        int i = 0;
        for (String paletteBall : PaletteManager.getPaletteBalls()) {

            GameVersion version = PaletteManager.getPaletteVersions().get(i);

            if (version == GameVersion.VERSION_WOG1_OLD || version == GameVersion.VERSION_WOG1_NEW) {

                _Ball ball = BallManager.getBall(paletteBall, version);
                if (ball == null) continue;

                Button button = createTemplateForBall(size, ball);
                button.setTooltip(new DelayedTooltip("Add " + ball.getObjects().get(0).getAttribute("name").stringValue()));
                if (ball.getVersion() == GameVersion.VERSION_WOG1_OLD) {
                    oldGooballsToolbar.getItems().add(button);
                } else if (ball.getVersion() == GameVersion.VERSION_WOG1_NEW) {
                    newGooballsToolbar.getItems().add(button);
                }
            } else {
                _2Ball ball = BallManager.get2Ball(paletteBall, version);
                if (ball == null) continue;

                Button button = createTemplateFor2Ball(size, ball);
                button.setTooltip(new DelayedTooltip("Add " + ball.getObjects().get(0).getAttribute("name").stringValue()));
                if (ball.getVersion() == GameVersion.VERSION_WOG1_OLD) {
                    oldGooballsToolbar.getItems().add(button);
                } else if (ball.getVersion() == GameVersion.VERSION_WOG1_NEW) {
                    newGooballsToolbar.getItems().add(button);
                }
                 else {
                    sequelGooballsToolbar.getItems().add(button);
                }
            }
            i++;
        }
    }


    private static final Button buttonNewOld = new Button();
    private static final Button buttonNewNew = new Button();
    private static final Button buttonNew2 = new Button();
    private static final Button buttonOpenOld = new Button();
    private static final Button buttonOpenNew = new Button();
    private static final Button buttonOpen2 = new Button();
    private static final Button buttonClone = new Button();
    private static final Button buttonSave = new Button();
    private static final Button buttonSaveAll = new Button();
    private static final Button buttonSaveAndPlay = new Button();
    private static final Button buttonExport = new Button();
    private static final Button buttonDummyExport = new Button();

    private static void level(ToolBar toolBar) {

        String prefix = "ButtonIcons/Level/";

        setIcon(buttonNewOld, prefix + "new_lvl_old.png");
        buttonNewOld.setOnAction(e -> LevelLoader.newLevel(GameVersion.VERSION_WOG1_OLD));
        buttonNewOld.setTooltip(new DelayedTooltip("New Level (1.3)"));
        toolBar.getItems().add(buttonNewOld);

        setIcon(buttonNewNew, prefix + "new_lvl_new.png");
        buttonNewNew.setOnAction(e -> LevelLoader.newLevel(GameVersion.VERSION_WOG1_NEW));
        buttonNewNew.setTooltip(new DelayedTooltip("New Level (1.5)"));
        toolBar.getItems().add(buttonNewNew);

        setIcon(buttonNew2, prefix + "new_level_2.png");
        buttonNew2.setOnAction(e -> LevelLoader.newLevel(GameVersion.VERSION_WOG2));
        buttonNew2.setTooltip(new DelayedTooltip("New Level (2)"));
        toolBar.getItems().add(buttonNew2);

        toolBar.getItems().add(new Separator());

        setIcon(buttonOpenOld, prefix + "open_lvl_old.png");
        buttonOpenOld.setOnAction(e -> LevelLoader.openLevel(GameVersion.VERSION_WOG1_OLD));
        buttonOpenOld.setTooltip(new DelayedTooltip("Open Level (1.3)"));
        toolBar.getItems().add(buttonOpenOld);

        setIcon(buttonOpenNew, prefix + "open_lvl_new.png");
        buttonOpenNew.setOnAction(e -> LevelLoader.openLevel(GameVersion.VERSION_WOG1_NEW));
        buttonOpenNew.setTooltip(new DelayedTooltip("Open Level (1.5)"));
        toolBar.getItems().add(buttonOpenNew);

        setIcon(buttonOpen2, prefix + "open_lvl_2.png");
        buttonOpen2.setOnAction(e -> LevelLoader.openLevel(GameVersion.VERSION_WOG2));
        buttonOpen2.setTooltip(new DelayedTooltip("Open Level (2)"));
        toolBar.getItems().add(buttonOpen2);

        toolBar.getItems().add(new Separator());

        setIcon(buttonClone, prefix + "clone_lvl.png");
        buttonClone.setOnAction(e -> LevelLoader.cloneLevel());
        buttonClone.setTooltip(new DelayedTooltip("Clone Level"));
        toolBar.getItems().add(buttonClone);

        setIcon(buttonSave, prefix + "save.png");
        buttonSave.setOnAction(e -> LevelUpdater.saveLevel(LevelManager.getLevel()));
        buttonSave.setTooltip(new DelayedTooltip("Save Level"));
        toolBar.getItems().add(buttonSave);

        setIcon(buttonSaveAll, prefix + "save_all.png");
        buttonSaveAll.setOnAction(e -> LevelUpdater.saveAll());
        buttonSaveAll.setTooltip(new DelayedTooltip("Save All Levels"));
        toolBar.getItems().add(buttonSaveAll);

        setIcon(buttonSaveAndPlay, prefix + "play.png");
        buttonSaveAndPlay.setOnAction(e -> LevelUpdater.playLevel(LevelManager.getLevel()));
        buttonSaveAndPlay.setTooltip(new DelayedTooltip("Save and Play Level on Level Version"));
        toolBar.getItems().add(buttonSaveAndPlay);

        toolBar.getItems().add(new Separator());

        setIcon(buttonExport, prefix + "make_goomod.png");
        buttonExport.setOnAction(e -> LevelUpdater.exportLevel(LevelManager.getLevel(), true));
        buttonExport.setTooltip(new DelayedTooltip("Export Level"));
        toolBar.getItems().add(buttonExport);

        setIcon(buttonDummyExport, prefix + "make_dummy_goomod.png");
        buttonDummyExport.setOnAction(e -> LevelUpdater.exportLevel(LevelManager.getLevel(), false));
        buttonDummyExport.setTooltip(new DelayedTooltip("Export Level Without Addin Info"));
        toolBar.getItems().add(buttonDummyExport);

    }


    public static final Button buttonUndo = new Button();
    public static final Button buttonRedo = new Button();
    private static final Button buttonCut = new Button();
    private static final Button buttonCopy = new Button();
    private static final Button buttonPaste = new Button();
    private static final Button buttonDelete = new Button();
    public static final Button buttonSelectMoveAndResize = new Button();
    public static final Button buttonStrandMode = new Button();
    public static final Button buttonGeometryMode = new Button();

    private static void edit(ToolBar toolBar) {

        String prefix = "ButtonIcons/Edit/";

        setIcon(buttonUndo, prefix + "undo.png");
        buttonUndo.setOnAction(e -> UndoManager.undo());
        buttonUndo.setTooltip(new DelayedTooltip("Undo"));
        toolBar.getItems().add(buttonUndo);

        setIcon(buttonRedo, prefix + "redo.png");
        buttonRedo.setOnAction(e -> UndoManager.redo());
        buttonRedo.setTooltip(new DelayedTooltip("Redo"));
        toolBar.getItems().add(buttonRedo);

        toolBar.getItems().add(new Separator());

        setIcon(buttonCut, prefix + "cut.png");
        buttonCut.setOnAction(e -> ClipboardManager.cut());
        buttonCut.setTooltip(new DelayedTooltip("Cut"));
        toolBar.getItems().add(buttonCut);

        setIcon(buttonCopy, prefix + "copy.png");
        buttonCopy.setOnAction(e -> ClipboardManager.copy());
        buttonCopy.setTooltip(new DelayedTooltip("Copy"));
        toolBar.getItems().add(buttonCopy);

        setIcon(buttonPaste, prefix + "paste.png");
        buttonPaste.setOnAction(e -> ClipboardManager.paste());
        buttonPaste.setTooltip(new DelayedTooltip("Paste"));
        toolBar.getItems().add(buttonPaste);

        toolBar.getItems().add(new Separator());

        setIcon(buttonDelete, prefix + "delete.png");
        buttonDelete.setOnAction(e -> ObjectManager.delete(LevelManager.getLevel()));
        buttonDelete.setTooltip(new DelayedTooltip("Delete"));
        toolBar.getItems().add(buttonDelete);

        toolBar.getItems().add(new Separator());

        setIcon(buttonSelectMoveAndResize, prefix + "selection_mode.png");
        buttonSelectMoveAndResize.setOnAction(e -> SelectionManager.selectionMode());
        buttonSelectMoveAndResize.setStyle("-fx-background-color: #9999ff;"); // Highlighted by default
        buttonSelectMoveAndResize.setTooltip(new DelayedTooltip("Select, Move and Resize"));
        toolBar.getItems().add(buttonSelectMoveAndResize);

        setIcon(buttonStrandMode, prefix + "strand_mode.png");
        buttonStrandMode.setOnAction(e -> SelectionManager.strandMode());
        buttonStrandMode.setTooltip(new DelayedTooltip("Place Strands"));
        toolBar.getItems().add(buttonStrandMode);

        setIcon(buttonGeometryMode, prefix + "geometry_mode.png");
        buttonGeometryMode.setOnAction(e -> SelectionManager.geometryMode());
        buttonGeometryMode.setTooltip(new DelayedTooltip("[Very Experimental] Build Geometry"));
        toolBar.getItems().add(buttonGeometryMode);

    }


    private static final Button buttonUpdateLevelResources = new Button();
    private static final Button buttonImportImages = new Button();
    private static final Button buttonAddTextResource = new Button();
    private static final Button buttonCleanResources = new Button();
    private static final Button buttonSetMusic = new Button();
    private static final Button buttonSetLoopsound = new Button();
    private static final Button buttonAddItem = new Button();

    private static void resources(ToolBar toolBar) {

        String prefix = "ButtonIcons/Resources/";

        setIcon(buttonUpdateLevelResources, prefix + "update_level_resources.png");
        buttonUpdateLevelResources.setOnAction(e -> LevelResourceManager.updateLevelResources(LevelManager.getLevel()));
        buttonUpdateLevelResources.setTooltip(new DelayedTooltip("Update Level Resources"));
        toolBar.getItems().add(buttonUpdateLevelResources);

        setIcon(buttonImportImages, prefix + "import_img.png");
        buttonImportImages.setOnAction(e -> LevelResourceImporter.importImages(LevelManager.getLevel()));
        buttonImportImages.setTooltip(new DelayedTooltip("Import Images"));
        toolBar.getItems().add(buttonImportImages);

        setIcon(buttonAddTextResource, prefix + "add_text_resource.png");
        buttonAddTextResource.setOnAction(e -> LevelResourceManager.newTextResource(LevelManager.getLevel()));
        buttonAddTextResource.setTooltip(new DelayedTooltip("Add Text Resource"));
        toolBar.getItems().add(buttonAddTextResource);

        MenuButton menuButton = new MenuButton("Add Items");
        buttonAddItem.setGraphic(menuButton);
        buttonAddItem.setTooltip(new DelayedTooltip("Add Items"));
        toolBar.getItems().add(buttonAddItem);

        toolBar.getItems().add(new Separator());

        setIcon(buttonCleanResources, prefix + "clean_level_resources.png");
        buttonCleanResources.setOnAction(e -> LevelResourceManager.cleanLevelResources(LevelManager.getLevel()));
        buttonCleanResources.setTooltip(new DelayedTooltip("Clean Level Resources"));
        toolBar.getItems().add(buttonCleanResources);

        toolBar.getItems().add(new Separator());

        setIcon(buttonSetMusic, prefix + "import_music.png");
        buttonSetMusic.setOnAction(e -> LevelResourceImporter.importMusic(LevelManager.getLevel()));
        buttonSetMusic.setTooltip(new DelayedTooltip("Set Music"));
        toolBar.getItems().add(buttonSetMusic);

        setIcon(buttonSetLoopsound, prefix + "import_soundloop.png");
        buttonSetLoopsound.setOnAction(e -> LevelResourceImporter.importLoopsound(LevelManager.getLevel()));
        buttonSetLoopsound.setTooltip(new DelayedTooltip("Set Loop Sound"));
        toolBar.getItems().add(buttonSetLoopsound);

    }

    public static void updateItemsSelector(WOG2Level wog2Level) {
        MenuButton content = (MenuButton) buttonAddItem.getGraphic();
        content.getItems().clear();
        for (var entry : ItemHelper.itemTypeMap.entrySet()) {
            Menu item = new Menu(entry.getValue());
            for (var loadedItemEntry : ItemManager.itemMap.entrySet()) {
                if (loadedItemEntry.getValue().getAttribute("type").intValue() == entry.getKey()) {
                    MenuItem sub = new MenuItem(loadedItemEntry.getKey());
                    sub.setOnAction(e -> {
                        var object = ObjectAdder.addObject2(_2_Level_Item.class, wog2Level.getLevel().getPossibleChildrenTypeIDs()[3], wog2Level.getLevel());
                        object.setAttribute("type", loadedItemEntry.getKey());
                    });
                    item.getItems().add(sub);
                }
            }
            content.getItems().add(item);
        }
    }


    private static final Button buttonShowHideCamera = new Button();
    private static final Button buttonShowHideForcefields = new Button();
    private static final Button buttonShowHideGeometry = new Button();
    private static final Button buttonShowHideGraphics = new Button();
    private static final Button buttonShowHideGoos = new Button();
    private static final Button buttonShowHideParticles = new Button();
    private static final Button buttonShowHideLabels = new Button();
    private static final Button buttonShowHideAnim = new Button();
    private static final Button buttonShowHideSceneBGColor = new Button();
    public static final Button buttonViewTerrainGroup = new Button();
    private static final Button buttonResetCamera = new Button();
    public static void cameraGraphic(Image image) {
        buttonShowHideCamera.setGraphic(new ImageView(image));
    }
    public static void forcefieldsGraphic(Image image) {
        buttonShowHideForcefields.setGraphic(new ImageView(image));
    }
    public static void geometryGraphic(Image image) {
        buttonShowHideGeometry.setGraphic(new ImageView(image));
    }
    public static void graphicsGraphic(Image image) {
        buttonShowHideGraphics.setGraphic(new ImageView(image));
    }
    public static void goosGraphic(Image image) {
        buttonShowHideGoos.setGraphic(new ImageView(image));
    }
    public static void particlesGraphic(Image image) {
        buttonShowHideParticles.setGraphic(new ImageView(image));
    }
    public static void labelsGraphic(Image image) {
        buttonShowHideLabels.setGraphic(new ImageView(image));
    }
    public static void animGraphic(Image image) {
        buttonShowHideAnim.setGraphic(new ImageView(image));
    }
    public static void sceneBGGraphic(Image image) {
        buttonShowHideSceneBGColor.setGraphic(new ImageView(image));
    }

    private static void showHide(ToolBar toolBar) {

        String prefix = "ButtonIcons/ShowHide/";

        setIcon(buttonShowHideCamera, prefix + "showhide_cam.png");
        buttonShowHideCamera.setOnAction(e -> VisibilityManager.showHideCameras());
        buttonShowHideCamera.setTooltip(new DelayedTooltip("Show/Hide Camera"));
        toolBar.getItems().add(buttonShowHideCamera);

        setIcon(buttonShowHideForcefields, prefix + "showhide_forcefields.png");
        buttonShowHideForcefields.setOnAction(e -> VisibilityManager.showHideForcefields());
        buttonShowHideForcefields.setTooltip(new DelayedTooltip("Show/Hide Force Fields"));
        toolBar.getItems().add(buttonShowHideForcefields);

        setIcon(buttonShowHideGeometry, prefix + "showhide_geometry.png");
        buttonShowHideGeometry.setOnAction(e -> VisibilityManager.showHideGeometry());
        buttonShowHideGeometry.setTooltip(new DelayedTooltip("Show/Hide Geometry"));
        toolBar.getItems().add(buttonShowHideGeometry);

        setIcon(buttonShowHideGraphics, prefix + "showhide_images.png");
        buttonShowHideGraphics.setOnAction(e -> VisibilityManager.showHideGraphics());
        buttonShowHideGraphics.setTooltip(new DelayedTooltip("Show/Hide Graphics"));
        toolBar.getItems().add(buttonShowHideGraphics);

        setIcon(buttonShowHideGoos, prefix + "showhide_goobs.png");
        buttonShowHideGoos.setOnAction(e -> VisibilityManager.showHideGoos());
        buttonShowHideGoos.setTooltip(new DelayedTooltip("Show/Hide Goo Balls"));
        toolBar.getItems().add(buttonShowHideGoos);

        setIcon(buttonShowHideParticles, prefix + "showhide_particles.png");
        buttonShowHideParticles.setOnAction(e -> VisibilityManager.showHideParticles());
        buttonShowHideParticles.setTooltip(new DelayedTooltip("Show/Hide Particles"));
        toolBar.getItems().add(buttonShowHideParticles);

        setIcon(buttonShowHideLabels, prefix + "showhide_labels.png");
        buttonShowHideLabels.setOnAction(e -> VisibilityManager.showHideLabels());
        buttonShowHideLabels.setTooltip(new DelayedTooltip("Show/Hide Labels"));
        toolBar.getItems().add(buttonShowHideLabels);

        setIcon(buttonShowHideAnim, prefix + "showhide_anim.png");
        buttonShowHideAnim.setOnAction(e -> VisibilityManager.showHideAnim());
        buttonShowHideAnim.setTooltip(new DelayedTooltip("Show/Hide Animations"));
        toolBar.getItems().add(buttonShowHideAnim);

        setIcon(buttonShowHideSceneBGColor, prefix + "showhide_scenebgcolor.png");
        buttonShowHideSceneBGColor.setOnAction(e -> VisibilityManager.showHideSceneBGColor());
        buttonShowHideSceneBGColor.setTooltip(new DelayedTooltip("Show/Hide Scene Background Color"));
        toolBar.getItems().add(buttonShowHideSceneBGColor);

        MenuButton menuButton = new MenuButton("Terrain Groups");
        buttonViewTerrainGroup.setGraphic(menuButton);
        buttonViewTerrainGroup.setTooltip(new DelayedTooltip("Show/Hide Scene Background Color"));
        toolBar.getItems().add(buttonViewTerrainGroup);

        setIcon(buttonResetCamera, prefix + "showhide_cam.png");
        buttonResetCamera.setOnAction(e -> {
            if (LevelManager.getLevel() instanceof WOG2Level wog2Level) {
                wog2Level.setOffsetX(1000);
                wog2Level.setOffsetY(500);
                wog2Level.setZoom(300);
            }
        });
        buttonResetCamera.setTooltip(new DelayedTooltip("Reset Camera"));
        toolBar.getItems().add(buttonResetCamera);

    }

    public static ArrayList<Boolean> comboBoxList = new ArrayList<>();
    public static int comboBoxSelected = -1;

    public static void updateTerrainGroupSelector(WOG2Level level) {

        MenuButton content = (MenuButton) buttonViewTerrainGroup.getGraphic();
        content.getItems().clear();
        comboBoxList.clear();
        int i = 0;
        for (EditorObject terrainGroup : level.getLevel().getChildren("terrainGroups")) {

            CheckBox checkBox = new CheckBox(i + (terrainGroup.getAttribute("foreground").booleanValue() ? "" : "*"));
            checkBox.setSelected(true);
            int finalI = i;
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                    comboBoxList.set(finalI, newValue));

            CustomMenuItem menuItem = new CustomMenuItem(checkBox);

            menuItem.setHideOnClick(false);

            checkBox.addEventHandler(EventType.ROOT, event -> {
                if (event instanceof MouseEvent mouseEvent && mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
                    comboBoxSelected = finalI;
                } else if (event instanceof MouseEvent mouseEvent && mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    comboBoxSelected = -1;
                }
                if (event instanceof MouseEvent mouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    if (mouseEvent.isControlDown()) {
                        for (MenuItem menuItem1 : content.getItems()) {
                            CheckBox checkBox1 = (CheckBox) ((CustomMenuItem)menuItem1).getContent();
                            checkBox1.setSelected(mouseEvent.isShiftDown());
                        }
                        checkBox.setSelected(!mouseEvent.isShiftDown());
                    }
                }
            });

            content.getItems().add(menuItem);
            comboBoxList.add(true);

            i++;

        }

    }


    private static final Button addLineButton = new Button();
    private static final Button addRectangleButton = new Button();
    private static final Button addCircleButton = new Button();
    private static final Button addSceneLayerButton = new Button();
    private static final Button addCompGeomButton = new Button();
    private static final Button addHingeButton = new Button();
    private static final Button autoPipeButton = new Button();
    private static final Button addVertexButton = new Button();
    private static final Button addFireButton = new Button();
    private static final Button addLinearFFButton = new Button();
    private static final Button addRadialFFButton = new Button();
    private static final Button addParticlesButton = new Button();
    private static final Button addSignpostButton = new Button();
    private static final Button addLabelButton = new Button();

    private static void addObjects(ToolBar toolBar) {

        String prefix = "ButtonIcons/AddObject/";

        setIcon(addLineButton, prefix + "line.png");
        addLineButton.setOnAction(e -> ObjectAdder.addObject("line"));
        addLineButton.setTooltip(new DelayedTooltip("Add Line"));
        toolBar.getItems().add(addLineButton);

        setIcon(addRectangleButton, prefix + "rectangle.png");
        addRectangleButton.setOnAction(e -> ObjectAdder.addObject("rectangle"));
        addRectangleButton.setTooltip(new DelayedTooltip("Add Rectangle"));
        toolBar.getItems().add(addRectangleButton);

        setIcon(addCircleButton, prefix + "circle.png");
        addCircleButton.setOnAction(e -> ObjectAdder.addObject("circle"));
        addCircleButton.setTooltip(new DelayedTooltip("Add Circle"));
        toolBar.getItems().add(addCircleButton);

        setIcon(addSceneLayerButton, prefix + "SceneLayer.png");
        addSceneLayerButton.setOnAction(e -> ObjectAdder.addObject("SceneLayer"));
        addSceneLayerButton.setTooltip(new DelayedTooltip("Add Scene Layer"));
        toolBar.getItems().add(addSceneLayerButton);

        setIcon(addCompGeomButton, prefix + "compositegeom.png");
        addCompGeomButton.setOnAction(e -> ObjectAdder.addObject("compositegeom"));
        addCompGeomButton.setTooltip(new DelayedTooltip("Add Composite Geometry"));
        toolBar.getItems().add(addCompGeomButton);

        setIcon(addHingeButton, prefix + "hinge.png");
        addHingeButton.setOnAction(e -> ObjectAdder.addObject("hinge"));
        addHingeButton.setTooltip(new DelayedTooltip("Add Hinge"));
        toolBar.getItems().add(addHingeButton);

        toolBar.getItems().add(new Separator());

        setIcon(autoPipeButton, prefix + "pipe.png");
        autoPipeButton.setOnAction(e -> ObjectAdder.autoPipe());
        autoPipeButton.setTooltip(new DelayedTooltip("Auto Pipe"));
        toolBar.getItems().add(autoPipeButton);

        setIcon(addVertexButton, prefix + "Vertex.png");
        addVertexButton.setOnAction(e -> ObjectAdder.addObject("Vertex"));
        addVertexButton.setTooltip(new DelayedTooltip("Add Vertex"));
        toolBar.getItems().add(addVertexButton);

        toolBar.getItems().add(new Separator());

        setIcon(addFireButton, prefix + "fire.png");
        addFireButton.setOnAction(e -> ObjectAdder.addObject("fire"));
        addFireButton.setTooltip(new DelayedTooltip("Add Fire"));
        toolBar.getItems().add(addFireButton);

        setIcon(addLinearFFButton, prefix + "linearforcefield.png");
        addLinearFFButton.setOnAction(e -> ObjectAdder.addObject("linearforcefield"));
        addLinearFFButton.setTooltip(new DelayedTooltip("Add Linear Force Field"));
        toolBar.getItems().add(addLinearFFButton);

        setIcon(addRadialFFButton, prefix + "radialforcefield.png");
        addRadialFFButton.setOnAction(e -> ObjectAdder.addObject("radialforcefield"));
        addRadialFFButton.setTooltip(new DelayedTooltip("Add Radial Force Field"));
        toolBar.getItems().add(addRadialFFButton);

        setIcon(addParticlesButton, prefix + "particles.png");
        addParticlesButton.setOnAction(e -> ObjectAdder.addObject("particles"));
        addParticlesButton.setTooltip(new DelayedTooltip("Add Particles"));
        toolBar.getItems().add(addParticlesButton);

        toolBar.getItems().add(new Separator());

        setIcon(addSignpostButton, prefix + "signpost.png");
        addSignpostButton.setOnAction(e -> ObjectAdder.addObject("signpost"));
        addSignpostButton.setTooltip(new DelayedTooltip("Add Signpost"));
        toolBar.getItems().add(addSignpostButton);

        setIcon(addLabelButton, prefix + "label.png");
        addLabelButton.setOnAction(e -> ObjectAdder.addObject("label"));
        addLabelButton.setTooltip(new DelayedTooltip("Add Label"));
        toolBar.getItems().add(addLabelButton);

    }

    private static final Button addPipeInLiquidButton = new Button();
    private static final Button addPipeInBallsButton = new Button();
    private static final Button addPoolButton = new Button();
    private static final Button addStickyTerrainButton = new Button();
    private static final Button addDeadlyTerrainButton = new Button();
    private static final Button addFrictionlessTerrainButton = new Button();
    private static final Button addUnwalkableTerrainButton = new Button();
    private static final Button addNonStickyTerrainButton = new Button();
    private static final Button addClearTerrainButton = new Button();
    private static final Button addWaterButton = new Button();
    private static final Button addLevelExit2Button = new Button();

    private static void addWoG2Items(ToolBar toolbar) {
        String prefix = "ObjectIcons/WoG2/";

        setIcon(addPipeInLiquidButton, prefix + "pipeInLiquid.png");
        addPipeInLiquidButton.setOnAction(e -> ObjectAdder.addWOG2Item("PipeInLiquid"));
        addPipeInLiquidButton.setTooltip(new DelayedTooltip("Add PipeInLiquid"));
        toolbar.getItems().add(addPipeInLiquidButton);

        setIcon(addPipeInBallsButton, prefix + "pipeInBalls.png");
        addPipeInBallsButton.setOnAction(e -> ObjectAdder.addWOG2Item("PipeInBalls"));
        addPipeInBallsButton.setTooltip(new DelayedTooltip("Add PipeInBalls"));
        toolbar.getItems().add(addPipeInBallsButton);

        setIcon(addPoolButton, prefix + "pool.png");
        addPoolButton.setOnAction(e -> ObjectAdder.addWOG2Item("Pool"));
        addPoolButton.setTooltip(new DelayedTooltip("Add Pool"));
        toolbar.getItems().add(addPoolButton);

        setIcon(addStickyTerrainButton, prefix + "terrainSticky.png");
        addStickyTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainSticky"));
        addStickyTerrainButton.setTooltip(new DelayedTooltip("Add TerrainSticky"));
        toolbar.getItems().add(addStickyTerrainButton);

        setIcon(addDeadlyTerrainButton, prefix + "terrainDeadly.png");
        addDeadlyTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainDeadly"));
        addDeadlyTerrainButton.setTooltip(new DelayedTooltip("Add TerrainDeadly"));
        toolbar.getItems().add(addDeadlyTerrainButton);

        setIcon(addFrictionlessTerrainButton, prefix + "terrainFrictionless.png");
        addFrictionlessTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainFrictionless"));
        addFrictionlessTerrainButton.setTooltip(new DelayedTooltip("Add TerrainFrictionless"));
        toolbar.getItems().add(addFrictionlessTerrainButton);

        setIcon(addUnwalkableTerrainButton, prefix + "terrainUnwalkable.png");
        addUnwalkableTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainUnwalkable"));
        addUnwalkableTerrainButton.setTooltip(new DelayedTooltip("Add TerrainUnwalkable"));
        toolbar.getItems().add(addUnwalkableTerrainButton);

        setIcon(addNonStickyTerrainButton, prefix + "terrainNonSticky.png");
        addNonStickyTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainNonSticky"));
        addNonStickyTerrainButton.setTooltip(new DelayedTooltip("Add TerrainNonSticky"));
        toolbar.getItems().add(addNonStickyTerrainButton);

        setIcon(addClearTerrainButton, prefix + "terrainClear.png");
        addClearTerrainButton.setOnAction(e -> ObjectAdder.addWOG2Item("TerrainClear"));
        addClearTerrainButton.setTooltip(new DelayedTooltip("Add TerrainClear"));
        toolbar.getItems().add(addClearTerrainButton);

        setIcon(addWaterButton, prefix + "water.png");
        addWaterButton.setOnAction(e -> ObjectAdder.addWOG2Item("Water"));
        addWaterButton.setTooltip(new DelayedTooltip("Add Water"));
        toolbar.getItems().add(addWaterButton);

        setIcon(addLevelExit2Button, "ObjectIcons/level/levelexit.png");
        addLevelExit2Button.setOnAction(e -> ObjectAdder.addWOG2Item("LevelExit"));
        addLevelExit2Button.setTooltip(new DelayedTooltip("Add LevelExit"));
        toolbar.getItems().add(addLevelExit2Button);
    }



    public static void init() {

        VBox vBox = FXContainers.getvBox();

        functionsToolbar = new ToolBar();
        level(functionsToolbar);
        functionsToolbar.getItems().add(new Separator());
        edit(functionsToolbar);
        functionsToolbar.getItems().add(new Separator());
        resources(functionsToolbar);
        functionsToolbar.getItems().add(new Separator());
        showHide(functionsToolbar);
        for (Node node : functionsToolbar.getItems()) node.setDisable(true);
        vBox.getChildren().add(1, functionsToolbar);

        oldGooballsToolbar = new ToolBar();
        oldGooballsToolbar.setMinHeight(27);
        oldGooballsToolbar.setOnMouseClicked(e -> showPaletteConfigurator(e, oldGooballsToolbar));
        newGooballsToolbar = new ToolBar();
        newGooballsToolbar.setMinHeight(27);
        newGooballsToolbar.setOnMouseClicked(e -> showPaletteConfigurator(e, newGooballsToolbar));
        sequelGooballsToolbar = new ToolBar();
        sequelGooballsToolbar.setMinHeight(27);
        sequelGooballsToolbar.setOnMouseClicked(e -> showPaletteConfigurator(e, sequelGooballsToolbar));
        nullGooballsToolbar = new ToolBar();
        nullGooballsToolbar.setMinHeight(27);
        nullGooballsToolbar.setOnMouseClicked(e -> showPaletteConfigurator(e, nullGooballsToolbar));
        addBallsTo();
        addWoG2Items(sequelGooballsToolbar);
        vBox.getChildren().add(2, nullGooballsToolbar);

        addObjectsToolbar = new ToolBar();
        vBox.getChildren().add(3, addObjectsToolbar);

    }


    public static void updateAllButtons() {

        boolean inLevel = LevelManager.getLevel() != null;

        for (Node node : functionsToolbar.getItems()) node.setDisable(!inLevel);
        for (Node node : oldGooballsToolbar.getItems()) node.setDisable(!inLevel);
        for (Node node : newGooballsToolbar.getItems()) node.setDisable(!inLevel);
        for (Node node : sequelGooballsToolbar.getItems()) node.setDisable(!inLevel);
        for (Node node : nullGooballsToolbar.getItems()) node.setDisable(!inLevel);
        for (Node node : addObjectsToolbar.getItems()) node.setDisable(!inLevel);

        buttonUndo.setDisable(!inLevel || LevelManager.getLevel().undoActions.isEmpty());
        buttonRedo.setDisable(!inLevel || LevelManager.getLevel().redoActions.isEmpty());
        buttonCut.setDisable(!inLevel || LevelManager.getLevel().getSelected().length == 0);
        buttonCopy.setDisable(!inLevel || LevelManager.getLevel().getSelected().length == 0);
        buttonPaste.setDisable(!inLevel);
        buttonDelete.setDisable(!inLevel || LevelManager.getLevel().getSelected().length == 0);

        boolean hasOld = !FileManager.getGameDir(GameVersion.VERSION_WOG1_OLD).isEmpty();
        buttonNewOld.setDisable(!hasOld);
        buttonOpenOld.setDisable(!hasOld);

        boolean hasNew = !FileManager.getGameDir(GameVersion.VERSION_WOG1_NEW).isEmpty();
        buttonNewNew.setDisable(!hasNew);
        buttonOpenNew.setDisable(!hasNew);

        boolean has2 = !FileManager.getGameDir(GameVersion.VERSION_WOG2).isEmpty();
        buttonNew2.setDisable(!has2);
        buttonOpen2.setDisable(!has2);

        if (FXContainers.getvBox().getChildren().get(3) instanceof ToolBar) {
            FXContainers.getvBox().getChildren().remove(3);
        }

        if (LevelManager.getLevel() == null) {
            FXContainers.getvBox().getChildren().add(3, addObjectsToolbar);
        } else if (LevelManager.getLevel().getVersion() == GameVersion.VERSION_WOG2) {
            newAddObjectsToolbar = new ToolBar();
            //addObjects(newAddObjectsToolbar);
            for (Node node : newAddObjectsToolbar.getItems()) node.setDisable(true);
            FXContainers.getvBox().getChildren().add(3, newAddObjectsToolbar);
        } else {
            addObjectsToolbar = new ToolBar();
            addObjects(addObjectsToolbar);
            for (Node node : addObjectsToolbar.getItems()) node.setDisable(true);
            FXContainers.getvBox().getChildren().add(3, addObjectsToolbar);
        }


    }


    public static void showPaletteConfigurator(MouseEvent mouseEvent, ToolBar toolbar) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Configure Palette...");
            menuItem.setOnAction(actionEvent -> new PaletteReconfigurator().start(new Stage()));
            contextMenu.getItems().add(menuItem);
            if (toolbar != null) {
                toolbar.setContextMenu(contextMenu);
            }
        }
    }

}
