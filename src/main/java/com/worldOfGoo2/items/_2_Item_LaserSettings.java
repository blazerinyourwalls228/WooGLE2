package com.worldOfGoo2.items;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_Color;
import com.worldOfGoo2.misc._2_ImageID;

public class _2_Item_LaserSettings extends EditorObject {

    public _2_Item_LaserSettings(EditorObject parent) {
        super(parent, "LaserSettings", GameVersion.VERSION_WOG2);

        addAttribute("enabled", InputField._2_STRING).assertRequired();
        addAttribute("base", InputField._2_STRING).assertRequired();
        addAttribute("top", InputField._2_STRING).assertRequired();
        addAttribute("baseAdditive", InputField._2_STRING).assertRequired();
        addAttribute("topAdditive", InputField._2_STRING).assertRequired();
        addAttribute("count", InputField._2_STRING).assertRequired();
        addAttribute("timeScale", InputField._2_STRING).assertRequired();
        addAttribute("waveScale", InputField._2_STRING).assertRequired();
        addAttribute("moveTimeScale", InputField._2_STRING).assertRequired();
        addAttribute("gravityScale", InputField._2_STRING).assertRequired();
        addAttribute("rotationStep", InputField._2_STRING).assertRequired();
        addAttribute("innerGravityScale", InputField._2_STRING).assertRequired();
        addAttribute("scaleStep", InputField._2_STRING).assertRequired();
        addAttribute("positionStep", InputField._2_STRING).assertRequired();
        addAttribute("centerFactor", InputField._2_STRING).assertRequired();
        addAttribute("gradientStart", InputField._2_CHILD).setChildAlias(_2_Color.class).assertRequired();
        addAttribute("gradientEnd", InputField._2_CHILD).setChildAlias(_2_Color.class).assertRequired();
        addAttribute("image", InputField._2_CHILD).setChildAlias(_2_ImageID.class).assertRequired();

    }

}
