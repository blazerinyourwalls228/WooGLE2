package com.worldOfGoo2.environments;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_Point;

public class _2_Environment_Gradient extends EditorObject {

    public _2_Environment_Gradient(EditorObject parent) {
        super(parent, "Environment_Gradient", GameVersion.VERSION_WOG2);
        addAttribute("enabled", InputField._2_STRING);
        addAttribute("pointA", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("pointADepth", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("pointB", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("pointBDepth", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("distance", InputField._2_STRING);
        addAttribute("power", InputField._2_STRING);
        addAttribute("startColor", InputField._2_STRING);
        addAttribute("midColor", InputField._2_STRING);
        addAttribute("endColor", InputField._2_STRING);

    }

}
