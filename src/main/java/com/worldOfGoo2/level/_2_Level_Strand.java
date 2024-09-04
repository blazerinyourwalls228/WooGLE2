package com.worldOfGoo2.level;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.ObjectUtil;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.editorObjects.attributes.MetaEditorAttribute;
import com.woogleFX.editorObjects.attributes.dataTypes.Position;
import com.woogleFX.editorObjects.objectComponents.ImageComponent;
import com.woogleFX.editorObjects.objectComponents.RectangleComponent;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.engine.fx.FXEditorButtons;
import com.woogleFX.engine.renderer.Renderer;
import com.woogleFX.gameData.ball.AtlasManager;
import com.woogleFX.gameData.ball.BallManager;
import com.woogleFX.gameData.ball._Ball;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.WOG1Level;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.level.levelOpening.LevelLoader;
import com.worldOfGoo.ball.BallStrand;
import com.worldOfGoo.level.BallInstance;
import com.worldOfGoo2.misc._2_ImageID;
import com.worldOfGoo2.util.TerrainHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class _2_Level_Strand extends EditorObject {

    //private BallStrand strand;
    //public void setStrand(BallStrand strand) {
    //    this.strand = strand;
    //}


    private Image strandImage;


    private _2_Level_BallInstance goo1 = null;
    public _2_Level_BallInstance getGoo1() {
        return goo1;
    }
    public void setGoo1(_2_Level_BallInstance goo1) {
        this.goo1 = goo1;
    }


    private _2_Level_BallInstance goo2 = null;
    public _2_Level_BallInstance getGoo2() {
        return goo2;
    }
    public void setGoo2(_2_Level_BallInstance goo2) {
        this.goo2 = goo2;
    }


    //private int strandBallID = 2;

    public _2_Level_Strand(EditorObject parent) {
        super(parent, "Strand", GameVersion.VERSION_WOG2);

        addAttribute("ball1UID", InputField._2_BALL_UID);
        addAttribute("ball2UID", InputField._2_BALL_UID);
        addAttribute("type", InputField._2_STRAND_TYPE).setDefaultValue("1").assertRequired();
        addAttribute("filled", InputField._2_BOOLEAN);

        setMetaAttributes(MetaEditorAttribute.parse("ball1UID,ball2UID,type,filled,"));



    }


    @Override
    public String getName() {
        String gb1 = getAttribute("ball1UID").stringValue();
        String gb2 = getAttribute("ball2UID").stringValue();
        return gb1 + ", " + gb2;
    }


    private boolean setStrand(String type) {

        for (_Ball ball : BallManager.getImportedBalls()) {
            String ballType = ball.getObjects().get(0).getAttribute("name").stringValue();
            if (ballType.equals(type)) {
                for (EditorObject object : ball.getObjects()) if (object instanceof BallStrand strand2) {
                    //this.strand = strand2;
                    return true;
                }
            }
        }

        return false;

    }


    @Override
    public void onLoaded() {
        update();
    }


    @Override
    public void update() {

        if (LevelManager.getLevel() == null) return;

        for (EditorObject obj : ((WOG2Level)LevelManager.getLevel()).getObjects()) if (obj instanceof _2_Level_BallInstance ballInstance) {

            String id = ballInstance.getAttribute("uid").stringValue();
            String gb1 = getAttribute("ball1UID").stringValue();
            String gb2 = getAttribute("ball2UID").stringValue();

            if (id.equals(gb1)) goo1 = ballInstance;
            else if (id.equals(gb2)) goo2 = ballInstance;

        }

        if (goo1 != null && goo1.getAttribute("type").stringValue().equals("Terrain")) setAttribute("type", "10");

        try {
            String imageString = "";
            if (goo2 == null || goo2.getBall() == null || goo2.getBall().getObjects().get(0).getChildren("strandImageId").get(0).getAttribute("imageId").stringValue().isEmpty()) {
                if (goo1 == null || goo1.getBall() == null) return;
                for (EditorObject editorObject : goo1.getBall().getObjects())
                    if (editorObject instanceof _2_ImageID ball_image && editorObject.getTypeID().equals("strandImageId"))
                        if (!ball_image.getAttribute("imageId").stringValue().isEmpty())
                            imageString = ball_image.getAttribute("imageId").stringValue();
            } else {
                for (EditorObject editorObject : goo2.getBall().getObjects())
                    if (editorObject instanceof _2_ImageID ball_image && editorObject.getTypeID().equals("strandImageId"))
                        if (!ball_image.getAttribute("imageId").stringValue().isEmpty())
                            imageString = ball_image.getAttribute("imageId").stringValue();
            }

            if (AtlasManager.atlas.containsKey(imageString))
                strandImage = SwingFXUtils.toFXImage(AtlasManager.atlas.get(imageString), null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        addPartAsObjectPosition();

    }


    private void addPartAsObjectPosition() {

        clearObjectPositions();

        if (goo1 == null || goo2 == null) return;

        //if (strandImage == null) {
        //    update();
        //}

        if (strandImage != null) addObjectComponent(new ImageComponent() {
            public double getX() {
                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double x2 = goo2.getAttribute("pos").positionValue().getX();
                return (x1 + x2) / 2;
            }
            public double getY() {
                double y1 = -goo1.getAttribute("pos").positionValue().getY();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();
                return (y1 + y2) / 2;
            }
            public double getRotation() {

                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double y1 = -goo1.getAttribute("pos").positionValue().getY();

                double x2 = goo2.getAttribute("pos").positionValue().getX();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();

                return Math.PI / 2 + Renderer.angleTo(new Point2D(x1, y1), new Point2D(x2, y2));

            }
            public double getScaleX() {
                if (strandImage.getWidth() == 0) return 0;
                // if (goo2.getBall().getObjects().get(0).getAttribute("strandThickness").doubleValue() == 0) return goo1.getBall().getObjects().get(0).getAttribute("strandThickness").doubleValue() / strandImage.getWidth();
                if (goo1.getBall() == null || goo1.getBall().getObjects().get(0).getAttribute("strandThickness").doubleValue() <= 0) return goo2.getBall().getObjects().get(0).getAttribute("strandThickness").doubleValue() / strandImage.getWidth();
                else return goo1.getBall().getObjects().get(0).getAttribute("strandThickness").doubleValue() / strandImage.getWidth();
            }
            public double getScaleY() {

                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double y1 = -goo1.getAttribute("pos").positionValue().getY();

                double x2 = goo2.getAttribute("pos").positionValue().getX();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();

                return Math.hypot(x2 - x1, y2 - y1) / strandImage.getHeight();

            }
            public Image getImage() {
                return strandImage;
            }
            public double getDepth() {
                return 0.00000001;
            }
            public boolean isVisible() {
                return LevelManager.getLevel().getVisibilitySettings().getShowGoos() == 2;
            }
            public boolean isDraggable() {
                return false;
            }
            public boolean isResizable() {
                return false;
            }
            public boolean isRotatable() {
                return false;
            }
        });

        addObjectComponent(new RectangleComponent() {

            public double getX() {
                if (goo1 == null || goo2 == null) return 0;
                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double x2 = goo2.getAttribute("pos").positionValue().getX();
                return (x1 + x2) / 2;
            }

            public double getY() {
                if (goo1 == null || goo2 == null) return 0;
                double y1 = -goo1.getAttribute("pos").positionValue().getY();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();
                return (y1 + y2) / 2;
            }

            public double getRotation() {

                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double y1 = -goo1.getAttribute("pos").positionValue().getY();

                double x2 = goo2.getAttribute("pos").positionValue().getX();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();

                return Math.PI / 2 + Renderer.angleTo(new Point2D(x1, y1), new Point2D(x2, y2));

            }

            public double getWidth() {
                return 0.1;
            }

            public double getHeight() {
                if (goo1 == null || goo2 == null) return 0;
                double x1 = goo1.getAttribute("pos").positionValue().getX();
                double y1 = -goo1.getAttribute("pos").positionValue().getY();
                double x2 = goo2.getAttribute("pos").positionValue().getX();
                double y2 = -goo2.getAttribute("pos").positionValue().getY();
                return Math.hypot(y2 - y1, x2 - x1) - 0.35;

            }

            public double getEdgeSize() {
                return 0.025;
            }
            public boolean isEdgeOnly() {
                return false;
            }

            public double getDepth(){
                return -0.00000001;
            }

            public Paint getBorderColor() {if (goo1 != null && goo1.getAttribute("type").stringValue().equals("Terrain")) {
                    return new Color(1.0, 1.0, 1.0, 1.0);
                } else {
                    return new Color(0.0, 0.0, 0.0, 0.0);
                }
            }

            public Paint getColor() {

                double length = getHeight();

                double minSize = 0; // strand.getAttribute("minlen").doubleValue();
                double maxSize = 100000; // strand.getAttribute("maxlen2").doubleValue();

                if (length > maxSize) return new Color(1.0, 0.0, 0.0, 1.0);
                if (length < minSize) return new Color(0.0, 0.0, 1.0, 1.0);
                if (goo1 != null && goo1.getAttribute("type").stringValue().equals("Terrain") && FXEditorButtons.comboBoxSelected == goo1.getAttribute("terrainGroup").intValue() && FXEditorButtons.comboBoxSelected != -1) {
                    if (FXEditorButtons.comboBoxList.get(FXEditorButtons.comboBoxSelected)) {
                        return new Color(1.0 ,0.0, 1.0, 1);
                    } else {
                        return new Color(0.0 ,0.0, 1.0, 1);
                    }
                }
                if (goo1 != null && goo1.getAttribute("type").stringValue().equals("Terrain") && goo1.getAttribute("terrainGroup").intValue() > -1) {
                    return new Color(0.0, 0.0, 0.0, 1);
                } else {
                    return new Color(0.5, 0.5, 0.5, 1);
                }

            }

            public boolean isVisible() {
                if (goo1.getAttribute("type").stringValue().equals("Terrain")) {
                    return (LevelManager.getLevel().getVisibilitySettings().getShowGoos() == 1 || LevelManager.getLevel().getVisibilitySettings().getShowGoos() == 2 && goo1.visibilityFunction() && goo2.visibilityFunction()) || FXEditorButtons.comboBoxSelected == goo1.getAttribute("terrainGroup").intValue();
                }
                return LevelManager.getLevel().getVisibilitySettings().getShowGoos() == 1 && goo1.visibilityFunction() && goo2.visibilityFunction();
            }
            public boolean isDraggable() {
                return false;
            }
            public boolean isResizable() {
                return false;
            }
            public boolean isRotatable() {
                return false;
            }

        });

    }

}
