package com.worldOfGoo2.level;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.*;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.engine.fx.FXEditorButtons;
import com.woogleFX.file.resourceManagers.ResourceManager;
import com.woogleFX.gameData.animation.SimpleBinAnimation;
import com.woogleFX.gameData.ball.BallManager;
import com.woogleFX.gameData.ball._2Ball;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.level.levelOpening.LevelLoader;
import com.worldOfGoo.resrc.FlashAnim;
import com.worldOfGoo2.util.BallInstanceHelper;
import com.worldOfGoo2.util.BinAnimationHelper;

import java.io.FileNotFoundException;

public class _2_Level_BallInstance extends EditorObject {

    private _2Ball ball = null;
    public _2Ball getBall() {
        return ball;
    }


    private final long randomSeed;
    public long getRandomSeed() {
        return randomSeed;
    }


    public _2_Level_BallInstance(EditorObject parent) {
        super(parent, "BallInstance", GameVersion.VERSION_WOG2);

        randomSeed = (long)(Math.random() * 10000000);

        addAttributeAdapter("pos", AttributeAdapter.pointAttributeAdapter(
                this, "pos", "pos"));

        addAttributeAdapter("typeEnum", new AttributeAdapter("type") {

            private final EditorAttribute typeAttribute = new EditorAttribute("type",
                    InputField._2_BALL_TYPE, _2_Level_BallInstance.this);

            @Override
            public EditorAttribute getValue() {
                if (getAttribute2("typeEnum").stringValue().isEmpty()) return typeAttribute;
                typeAttribute.setValue(BallInstanceHelper.typeEnumToTypeMap.getOrDefault(
                        getAttribute2("typeEnum").intValue(), ""));
                return typeAttribute;
            }

            @Override
            public void setValue(String value) {
                Integer typeEnum = BallInstanceHelper.typeToTypeEnumMap.get(value);
                if (typeEnum == null) {
                    try {
                        typeEnum = Integer.parseInt(value);
                        typeAttribute.setValue(value);
                    } catch (NumberFormatException ignored) {
                        typeEnum = 0;
                    }
                } else typeAttribute.setValue(value);
                setAttribute2("typeEnum", typeEnum);
            }

        });

    }

    @Override
    public String getName() {
        String id = getAttribute("uid").stringValue();
        String type = getAttribute("type").stringValue();
        return id + ", " + type;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();

        EditorObject pos = getChildren("pos").get(0);
        setAttribute2("pos", pos.getAttribute("x").stringValue() +
                "," + pos.getAttribute("y").stringValue());
        pos.getAttribute("x").addChangeListener((observable, oldValue, newValue) ->
                setAttribute2("pos", newValue + "," + getAttribute2("pos").positionValue().getY()));
        pos.getAttribute("y").addChangeListener((observable, oldValue, newValue) ->
                setAttribute2("pos", getAttribute2("pos").positionValue().getX() + "," + newValue));

        getAttribute("discovered").addChangeListener((observable, oldValue, newValue) -> update());
        getAttribute2("typeEnum").addChangeListener((observable, oldValue, newValue) -> update());

    }

    @Override
    public void update() {

        if (LevelManager.getLevel() instanceof WOG2Level level) {

            String type = getAttribute("type").stringValue();
            ball = BallManager.get2Ball(type, getVersion());
            if (ball == null) {
                String invalidBallDescription = "Ball: " + type + " (version " + getVersion() + ")";
                if (!LevelLoader.failedResources.contains(invalidBallDescription))
                    LevelLoader.failedResources.add(invalidBallDescription);
            }

            String id = getAttribute("uid").stringValue();
            for (EditorObject object : level.getObjects()) if (object instanceof _2_Level_Strand strand) {
                if (id.equals(strand.getAttribute("ball1UID").stringValue())) strand.setGoo1(this);
                else if (id.equals(strand.getAttribute("ball2UID").stringValue())) strand.setGoo2(this);
                else continue;
                strand.update();
            }

            clearObjectPositions();

            addObjectComponents(BallInstanceHelper.generateBallObjectComponents(this));

            String animation = getBall().getObjects().get(0).getChildren("flashAnimation").get(0).getAttribute("flashAnimationId").stringValue();
            if (!animation.isEmpty()) {
                try {
                    SimpleBinAnimation flashAnim = ResourceManager.getFlashAnim(getBall().getResources(), animation, GameVersion.VERSION_WOG2);
                    BinAnimationHelper.addBinAnimationAsObjectPositions(this, flashAnim, "");
                } catch (FileNotFoundException e) {
                    logger.error("", e);
                }
            }

        }

    }


    public boolean visibilityFunction() {

        if (LevelManager.getLevel().getVisibilitySettings().getShowGoos() == 0) return false;

        if (!getAttribute("type").stringValue().equals("Terrain")) return true;

        int terrainGroup = getAttribute("terrainGroup").intValue();
        if (terrainGroup < 0 || terrainGroup >= FXEditorButtons.comboBoxList.size()) return true;
        else return FXEditorButtons.comboBoxList.get(terrainGroup);

    }

}

