package com.worldOfGoo2.level;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.AttributeAdapter;
import com.woogleFX.editorObjects.attributes.EditorAttribute;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.editorObjects.attributes.MetaEditorAttribute;
import com.woogleFX.editorObjects.attributes.dataTypes.Position;
import com.woogleFX.editorObjects.objectComponents.MeshComponent;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.engine.fx.FXEditorButtons;
import com.woogleFX.engine.fx.FXPropertiesView;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.terrainTypes.TerrainTypeManager;
import com.worldOfGoo2.misc._2_Point;
import com.worldOfGoo2.terrain.BaseSettings;
import com.worldOfGoo2.terrain._2_Terrain_TerrainType;
import com.worldOfGoo2.util.ItemHelper;
import com.worldOfGoo2.util.TerrainHelper;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class _2_Level_TerrainGroup extends EditorObject {
    private ArrayList<_2_Level_BallInstance> balls = new ArrayList<>();
    
    public _2_Level_TerrainGroup(EditorObject parent) {
        super(parent, "TerrainGroup", GameVersion.VERSION_WOG2);

        addAttribute("textureOffset", InputField._2_CHILD_HIDDEN).setChildAlias(_2_Point.class);
        addAttributeAdapter("textureOffset", AttributeAdapter.pointAttributeAdapter(this, "textureOffset", "textureOffset"));


        setMetaAttributes(MetaEditorAttribute.parse("textureOffset,typeUuid,typeIndex,sortOffset,depth,foreground,collision,destructable,buildable,occluder,"));

        EditorAttribute temp = new EditorAttribute("type", InputField._2_TERRAIN_GROUP_TYPE, this).assertRequired();
        addAttributeAdapter("typeUuid", new AttributeAdapter("type") {

            @Override
            public EditorAttribute getValue() {

                if (getAttribute2("typeUuid").stringValue().isEmpty()) return temp;
                temp.setValue(ItemHelper.getTerrainTypeActualName(getAttribute2("typeUuid").stringValue()));
                return temp;

            }

            @Override
            public void setValue(String value) {
                temp.setValue(value);
                _2_Terrain_TerrainType terrainType = TerrainTypeManager.getTerrainType(value);
                setAttribute2("typeUuid", terrainType.getAttribute("uuid").stringValue());
                if (LevelManager.getLevel().getSelected().length > 0 && _2_Level_TerrainGroup.this == LevelManager.getLevel().getSelected()[0]) {
                    FXPropertiesView.changeTableView(LevelManager.getLevel().getSelected());
                }
            }

        });

    }

    @Override
    public String getName() {
        if (LevelManager.getLevel() != null && LevelManager.getLevel() instanceof WOG2Level level) {
            for (int i = 0; i < level.getLevel().getChildren("terrainGroups").size(); i++) {
                if (level.getLevel().getChildren("terrainGroups").get(i) == this) {
                    return i + ", " + this.getAttribute("type").stringValue();
                }
            }
        }
        return "";
    }

    @Override
    public void onLoaded() {
        super.onLoaded();

        EditorObject textureOffset = getChildren("textureOffset").get(0);
        textureOffset.getAttribute("x").addChangeListener((observable, oldValue, newValue) ->
                setAttribute2("textureOffset", newValue + "," + getAttribute2("textureOffset").positionValue().getY()));
        textureOffset.getAttribute("y").addChangeListener((observable, oldValue, newValue) ->
                setAttribute2("textureOffset", getAttribute2("textureOffset").positionValue().getX() + "," + newValue));
        setAttribute2("textureOffset", textureOffset.getAttribute("x").stringValue() + "," + textureOffset.getAttribute("y").stringValue());

        getAttribute("type").addChangeListener((observable, oldValue, newValue) -> update());

    }
    
    @Override
    public void postInit() {
        update();
    }

    @Override
    public void update() {

        clearObjectPositions();

        Image image = TerrainHelper.buildTerrainImage(this);

        String terrainType = getAttribute("typeUuid").stringValue();

        _2_Terrain_TerrainType terrain = TerrainTypeManager.getTerrainType(terrainType);

        BaseSettings baseSettings = (BaseSettings) terrain.getChildren("baseSettings").get(0);

        addObjectComponent(new MeshComponent() {
            @Override
            public Tri[] getMesh() {
                ArrayList<Tri> tris = new ArrayList<>();
                
                // Get all strands in this group
                ArrayList<_2_Level_Strand> strands = new ArrayList<>();
                for (EditorObject object : ((WOG2Level)LevelManager.getLevel()).getLevel().getChildren("strands")) {
                    _2_Level_Strand strand = (_2_Level_Strand)object;
                    _2_Level_BallInstance ballInstance = strand.getGoo1();
                    
                    if (ballInstance != null && ballInstance.getCurrentGroup() == _2_Level_TerrainGroup.this) {
                        strands.add(strand);
                    }
                }
                
                // Iterate through all strands and gooballs to find triangles
                for (int i = 0; i < strands.size(); i++) {
                    _2_Level_Strand strand = strands.get(i);
                    
                    for (int j = 0; j < balls.size(); j++) {
                        _2_Level_BallInstance ballInstance = balls.get(j);
                        
                        if (strand.getGoo1() == null || strand.getGoo2() == null)
                            continue;
                        
                        if (strand.getGoo1().isConnected(ballInstance) && strand.getGoo2().isConnected(ballInstance)) {
                            double[] xPositions = new double[3];
                            double[] yPositions = new double[3];
                            
                            Position a = ballInstance.getPosition();
                            xPositions[0] = a.getX();
                            yPositions[0] = -a.getY();
                            
                            Position b = strand.getGoo1().getPosition();
                            xPositions[1] = b.getX();
                            yPositions[1] = -b.getY();
                            
                            Position c = strand.getGoo2().getPosition();
                            xPositions[2] = c.getX();
                            yPositions[2] = -c.getY();
                            
                            tris.add(new Tri(xPositions, yPositions));
                        }
                    }
                }
                
                return tris.toArray(Tri[]::new);
            }

            @Override
            public Image getImage() {
                return image;
            }

            @Override
            public double getX() {
                return 0;
            }

            @Override
            public double getY() {
                return 0;
            }

            @Override
            public double getScaleX() {
                return baseSettings.getAttribute("metersToUv").doubleValue() / image.getWidth();
            }

            @Override
            public double getScaleY() {
                return baseSettings.getAttribute("metersToUv").doubleValue() / image.getHeight();
            }

            @Override
            public double getDepth() {
                return getAttribute("sortOffset").doubleValue() * 0.00001 + (getAttribute("foreground").booleanValue() ? 0.01 : 0) - 0.02;
            }

            @Override
            public boolean isVisible() {

                if (LevelManager.getLevel().getVisibilitySettings().getShowGoos() != 2) return false;

                int terrainGroup = ((WOG2Level)LevelManager.getLevel()).getLevel().getChildren("terrainGroups").indexOf(_2_Level_TerrainGroup.this);
                if (terrainGroup < 0 || terrainGroup >= FXEditorButtons.comboBoxList.size()) return true;
                else return FXEditorButtons.comboBoxList.get(terrainGroup);

            }

        });

    }
    
    public void addBall(_2_Level_BallInstance ballInstance) {
        if (!balls.contains(ballInstance))
            balls.add(ballInstance);
    }
    
    public void removeBall(_2_Level_BallInstance ballInstance) {
        balls.remove(ballInstance);
    }
    
    public ArrayList<_2_Level_BallInstance> getBalls() {
        return balls;
    }
}
