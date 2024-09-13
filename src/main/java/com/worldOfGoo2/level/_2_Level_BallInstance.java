package com.worldOfGoo2.level;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects._2_Positionable;
import com.woogleFX.editorObjects.attributes.AttributeAdapter;
import com.woogleFX.editorObjects.attributes.EditorAttribute;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.engine.fx.FXEditorButtons;
import com.woogleFX.engine.undoHandling.userActions.ObjectDestructionAction;
import com.woogleFX.file.resourceManagers.ResourceManager;
import com.woogleFX.gameData.animation.SimpleBinAnimation;
import com.woogleFX.gameData.ball.BallManager;
import com.woogleFX.gameData.ball._2Ball;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.level.levelOpening.LevelLoader;
import com.worldOfGoo2.util.BallInstanceHelper;
import com.worldOfGoo2.util.BinAnimationHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class _2_Level_BallInstance extends _2_Positionable {

    private _2_Level_TerrainGroup currentGroup = null;
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

        addAttributeAdapter("typeEnum", BallInstanceHelper.ballTypeAttributeAdapter(this, "type", "typeEnum", null));
        addAttributeAdapter("terrainGroup", new AttributeAdapter("terrainGroup") {
            private final EditorAttribute attribute = new EditorAttribute("terrainGroup", InputField._2_NUMBER, _2_Level_BallInstance.this);
            
            @Override
            public EditorAttribute getValue() {
                attribute.setValue(getAttribute2("terrainGroup").stringValue());
                return attribute;
            }

            @Override
            public void setValue(String value) {
                if (currentGroup != null) {
                    currentGroup.removeBall(_2_Level_BallInstance.this);
                }
                
                int newValue = Integer.parseInt(value);
                setAttribute2("terrainGroup", newValue);
                
                _2_Level level = ((WOG2Level)LevelManager.getLevel()).getLevel();
                ArrayList<EditorObject> terrainGroups = level.getChildren("terrainGroups");
                
                if (newValue > 0 && newValue < terrainGroups.size()) {
                    currentGroup = (_2_Level_TerrainGroup)terrainGroups.get(newValue);
                    currentGroup.addBall(_2_Level_BallInstance.this);
                }
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

        getAttribute("discovered").addChangeListener((observable, oldValue, newValue) -> update());
        getAttribute("interactive").addChangeListener((observable, oldValue, newValue) -> update());
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
                    String state = "";
                    if (getAttribute("type").stringValue().equals("LauncherL2B") || getAttribute("type").stringValue().equals("LauncherL2L")) {
                        if (getAttribute("discovered").booleanValue()) {
                            if (getAttribute("interactive").booleanValue()) state = "idle";
                            else state = "npc_idle";
                        } else {
                            if (getAttribute("interactive").booleanValue()) state = "sleep";
                            else state = "npc_sleep";
                        }
                    }
                    BinAnimationHelper.addBinAnimationAsObjectPositions(this, flashAnim, state);
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

    
    public List<_2_Level_BallInstance> getConnected() {
        _2_Level level = ((WOG2Level)LevelManager.getLevel()).getLevel();
        ArrayList<_2_Level_BallInstance> out = new ArrayList<>();
        
        for (_2_Level_Strand strand : level.getChildren("strands").toArray(_2_Level_Strand[]::new)) {
            if (strand.getGoo1() == this)
                out.add(strand.getGoo2());
            else if (strand.getGoo2() == this)
                out.add(strand.getGoo1());
        }
        
        return out;
    }
    
    
    @Override
    public List<ObjectDestructionAction> onDelete() {
        if (currentGroup != null)
            currentGroup.removeBall(this);
        
        List<ObjectDestructionAction> outActions = new ArrayList<>();
        
        WOG2Level level = (WOG2Level)LevelManager.getLevel();
        for (EditorObject object : level.getObjects()) {
            if (object instanceof _2_Level_Strand strand) {
                if (this == strand.getGoo1() || this == strand.getGoo2()) {
                    int position = strand.getParent().getChildren().indexOf(strand);
                    outActions.add(new ObjectDestructionAction(strand, position));
                }
            }
        }
        
        return outActions;
    }
    
}

