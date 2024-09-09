package com.woogleFX.gameData.ball;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo.resrc.*;

import java.util.ArrayList;

public class _2Ball {

    private GameVersion version;
    public GameVersion getVersion() {
        return version;
    }
    public void setVersion(GameVersion version) {
        this.version = version;
    }


    public final ArrayList<EditorObject> objects;
    public ArrayList<EditorObject> getObjects() {
        return objects;
    }


    public final ArrayList<EditorObject> resources;
    public ArrayList<EditorObject> getResources() {
        return resources;
    }


    private String shapeType;
    public String getShapeType() {
        return shapeType;
    }
    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }


    private double width;
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    private double height;
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    private double sizeVariance;
    public double getSizeVariance() {
        return sizeVariance;
    }
    public void setSizeVariance(double sizeVariance) {
        this.sizeVariance = sizeVariance;
    }


    public _2Ball(ArrayList<EditorObject> _objects, ArrayList<EditorObject> resources) {
        this.objects = _objects;
        this.resources = resources;

        shapeType = "circle";

        width = _objects.get(0).getAttribute("width").doubleValue();
        height = _objects.get(0).getAttribute("height").doubleValue();
        sizeVariance = _objects.get(0).getAttribute("sizeVariance").doubleValue();

        SetDefaults currentSetDefaults = null;

        for (EditorObject EditorObject : resources) {

            if (EditorObject instanceof SetDefaults setDefaults) {
                currentSetDefaults = setDefaults;
            }

            else if (EditorObject instanceof FlashAnim flashAnim) {
                flashAnim.setSetDefaults(currentSetDefaults);
            } else if (EditorObject instanceof ResrcImage resrcImage) {
                resrcImage.setSetDefaults(currentSetDefaults);
            } else if (EditorObject instanceof Sound sound) {
                sound.setSetDefaults(currentSetDefaults);
            } else if (EditorObject instanceof Font font) {
                font.setSetDefaults(currentSetDefaults);
            }

        }

    }

}
