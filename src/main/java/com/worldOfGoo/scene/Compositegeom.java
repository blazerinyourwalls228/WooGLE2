package com.worldOfGoo.scene;

import com.woogleFX.editorObjects.attributes.EditorAttribute;
import com.woogleFX.editorObjects.objectComponents.CircleComponent;
import com.woogleFX.editorObjects.objectComponents.ImageComponent;
import com.woogleFX.engine.renderer.Depth;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.editorObjects.attributes.MetaEditorAttribute;
import com.woogleFX.editorObjects.attributes.dataTypes.Position;
import com.woogleFX.gameData.level.WOG1Level;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.FileNotFoundException;

public class Compositegeom extends EditorObject {

    private Image image;


    public Compositegeom(EditorObject _parent, GameVersion version) {
        super(_parent, "compositegeom", version);

        addAttribute("id",               InputField._1_STRING)                             .assertRequired();
        addAttribute("x",                InputField._1_NUMBER)  .setDefaultValue("0")   .assertRequired();
        addAttribute("y",                InputField._1_NUMBER)  .setDefaultValue("0")   .assertRequired();
        addAttribute("rotation",         InputField._1_NUMBER)  .setDefaultValue("0")   .assertRequired();
        addAttribute("static",           InputField._1_FLAG)    .setDefaultValue("true").assertRequired();
        addAttribute("mass",             InputField._1_NUMBER);
        addAttribute("tag",              InputField._1_TAG);
        addAttribute("material",         InputField._1_MATERIAL).setDefaultValue("rock").assertRequired();
        addAttribute("break",            InputField._1_NUMBER);
        addAttribute("image",            InputField._1_IMAGE);
        addAttribute("imagepos",         InputField._1_POSITION);
        addAttribute("imagerot",         InputField._1_NUMBER)  .setDefaultValue("0");
        addAttribute("imagescale",       InputField._1_POSITION).setDefaultValue("1,1");
        addAttribute("rotspeed",         InputField._1_NUMBER)  .setDefaultValue("0");
        addAttribute("contacts",         InputField._1_FLAG);
        addAttribute("nogeomcollisions", InputField._1_FLAG);

        addObjectComponent(new CircleComponent() {
            public double getX() {
                return getAttribute("x").doubleValue();
            }
            public void setX(double x) {
                setAttribute("x", x);
            }
            public double getY() {
                return -getAttribute("y").doubleValue();
            }
            public void setY(double y) {
                setAttribute("y", -y);
            }
            public double getRotation() {
                return -getAttribute("rotation").doubleValue();
            }
            public void setRotation(double rotation) {
                setAttribute("rotation", -rotation);
            }
            public double getRadius() {
                return 10;
            }
            public double getEdgeSize() {
                return 4;
            }
            public boolean isEdgeOnly() {
                return true;
            }
            public double getDepth() {
                return Depth.COMPOSITEGEOM;
            }
            public Paint getBorderColor() {
                return new Color(0, 1.0, 0, 1.0);
            }
            public Paint getColor() {
                return new Color(0, 1.0, 0, 0.25);
            }
            public boolean isVisible() {
                return LevelManager.getLevel().getVisibilitySettings().getShowGeometry() != 0;
            }
            public boolean isResizable() {
                return false;
            }
        });

        addObjectComponent(new ImageComponent() {
            public double getX() {
                EditorAttribute imagepos = getAttribute("imagepos");
                if (imagepos.stringValue().isEmpty()) return getAttribute("x").doubleValue();
                else return imagepos.positionValue().getX();
            }
            public void setX(double x) {
                setAttribute("imagepos", x + "," + -getY());
            }
            public double getY() {
                EditorAttribute imagepos = getAttribute("imagepos");
                if (imagepos.stringValue().isEmpty()) return -getAttribute("y").doubleValue();
                else return -imagepos.positionValue().getY();
            }
            public void setY(double y) {
                setAttribute("imagepos", getX() + "," + -y);
            }
            public double getRotation() {
                return -getAttribute("imagerot").doubleValue();
            }
            public void setRotation(double rotation) {
                setAttribute("imagerot", -rotation);
            }
            public double getScaleX() {
                return getAttribute("imagescale").positionValue().getX();
            }
            public void setScaleX(double scaleX) {
                Position scale = getAttribute("imagescale").positionValue();
                setAttribute("imagescale", scaleX + "," + scale.getY());
            }
            public double getScaleY() {
                return getAttribute("imagescale").positionValue().getY();
            }
            public void setScaleY(double scaleY) {
                Position scale = getAttribute("imagescale").positionValue();
                setAttribute("imagescale", scale.getX() + "," + scaleY);
            }
            public double getDepth() {
                return 0;
            }
            public Image getImage() {
                return image;
            }
            public boolean isGeometryImage() {
                return true;
            }
            public boolean isVisible() {
                return LevelManager.getLevel().getVisibilitySettings().getShowGeometry() != 0;
            }
            public boolean isResizable() {
                return false;
            }
            public boolean isRotatable() {
                return false;
            }
        });

        String geometry = "Geometry<static,mass,material,tag,break,rotspeed,contacts,nogeomcollisions>";
        String image = "?Image<image,imagepos,imagerot,imagescale>";
        setMetaAttributes(MetaEditorAttribute.parse("id,x,y,rotation," + geometry + image));

        getAttribute("image").addChangeListener((observable, oldValue, newValue) -> updateImage());

    }


    @Override
    public String getName() {
        return getAttribute("id").stringValue();
    }


    @Override
    public void update() {
        updateImage();
    }


    private void updateImage() {

        if (LevelManager.getLevel() == null) return;

        if (getAttribute("image").stringValue().isEmpty()) return;

        try {
            image = getAttribute("image").imageValue(((WOG1Level)LevelManager.getLevel()).getResrc(), getVersion());
        } catch (FileNotFoundException ignored) {
            image = null;
        }

    }


    @Override
    public Class<? extends EditorObject>[] getPossibleChildren() {
        return new Class[]{ Circle.class, Rectangle.class };
    }

}
