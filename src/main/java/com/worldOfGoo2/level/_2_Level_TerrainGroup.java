package com.worldOfGoo2.level;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.AttributeAdapter;
import com.woogleFX.editorObjects.attributes.EditorAttribute;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.editorObjects.attributes.MetaEditorAttribute;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.engine.fx.FXPropertiesView;
import com.woogleFX.file.resourceManagers.GlobalResourceManager;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_Point;
import com.worldOfGoo2.terrain._2_Terrain_TerrainType;
import com.worldOfGoo2.util.ItemHelper;

public class _2_Level_TerrainGroup extends EditorObject {

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
                // TODO: Show a preview of the terrain types... and render them I guess
                for (EditorObject resource : GlobalResourceManager.getSequelResources()) {
                    if (resource instanceof _2_Terrain_TerrainType) {
                        if (resource.getAttribute("name").stringValue().equals(value)) {
                            setAttribute2("typeUuid", resource.getAttribute("uuid").stringValue());
                            if (LevelManager.getLevel().getSelected().length > 0 && _2_Level_TerrainGroup.this == LevelManager.getLevel().getSelected()[0]) {
                                FXPropertiesView.changeTableView(LevelManager.getLevel().getSelected());
                            }
                            return;
                        }
                    }
                }
            }

        });

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

    }



}
