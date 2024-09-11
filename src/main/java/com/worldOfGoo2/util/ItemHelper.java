package com.worldOfGoo2.util;

import com.woogleFX.file.resourceManagers.ResourceManager;
import com.woogleFX.gameData.items.ItemManager;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.items._2_Item;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ItemHelper {

    public static final Map<String, String> itemNameMap = new HashMap<>();

    public static final Map<String, String> terrainTypeNameMap = new HashMap<>();


    public static String getItemActualName(String itemUUID) {

        if (itemNameMap.containsKey(itemUUID)) return itemNameMap.get(itemUUID);

        _2_Item item = ItemManager.getItem(itemUUID);
        if (item == null) return "(None)";
        String actualName = item.getAttribute("name").stringValue();
        itemNameMap.put(itemUUID, actualName);
        return actualName;

    }


    public static String getTerrainTypeActualName(String itemUUID) {

        if (terrainTypeNameMap.containsKey(itemUUID)) return terrainTypeNameMap.get(itemUUID);

        try {
            ResourceManager.findTerrainTypes(null, GameVersion.VERSION_WOG2);
            return terrainTypeNameMap.getOrDefault(itemUUID, "(None)");
        } catch (FileNotFoundException ignored) {
            return "";
        }

    }

}
