package com.worldOfGoo2.ball;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_Color;
import com.worldOfGoo2.misc._2_ImageID;

public class _2_Ball_Part extends EditorObject {


    public _2_Ball_Part(EditorObject parent) {
        super(parent, "Ball_Part", GameVersion.VERSION_WOG2);

        addAttribute("name", InputField._2_STRING).assertRequired();

        addAttribute("images", InputField._2_LIST_CHILD).setChildAlias(_2_Ball_Image.class).assertRequired();

        addAttribute("imageBackgroundIds", InputField._2_LIST_CHILD).setChildAlias(_2_ImageID.class).assertRequired();

        addAttribute("layer", InputField._2_NUMBER).assertRequired();
        addAttribute("drawWhenAttached", InputField._2_BOOLEAN);
        addAttribute("drawWhenNotAttached", InputField._2_BOOLEAN);
        addAttribute("minX", InputField._2_NUMBER).assertRequired();
        addAttribute("maxX", InputField._2_NUMBER).assertRequired();
        addAttribute("minY", InputField._2_NUMBER).assertRequired();
        addAttribute("maxY", InputField._2_NUMBER).assertRequired();
        addAttribute("minRangeX", InputField._2_NUMBER).assertRequired();
        addAttribute("maxRangeX", InputField._2_NUMBER).assertRequired();
        addAttribute("minRangeY", InputField._2_NUMBER).assertRequired();
        addAttribute("maxRangeY", InputField._2_NUMBER).assertRequired();

        addAttribute("states", InputField._2_LIST_CHILD).assertRequired().setChildAlias(_2_Ball_State.class);

        addAttribute("isActiveWhenUndiscovered", InputField._2_BOOLEAN).assertRequired();
        addAttribute("scale", InputField._2_NUMBER).assertRequired();
        addAttribute("scaleIsRelative", InputField._2_BOOLEAN).assertRequired();
        addAttribute("rotation", InputField._2_NUMBER).assertRequired();
        addAttribute("isEye", InputField._2_BOOLEAN).assertRequired();

        addAttribute("pupilImageIds", InputField._2_LIST_CHILD).setChildAlias(_2_ImageID.class).assertRequired();

        addAttribute("pupilInset", InputField._2_NUMBER).assertRequired();
        addAttribute("pupilScale", InputField._2_NUMBER);
        addAttribute("isRotating", InputField._2_BOOLEAN).assertRequired();
        addAttribute("stretchMaxSpeed", InputField._2_NUMBER).assertRequired();
        addAttribute("stretchParallel", InputField._2_NUMBER).assertRequired();
        addAttribute("stretchPerpendicular", InputField._2_NUMBER).assertRequired();

        addAttribute("color", InputField._2_STRING).setChildAlias(_2_Color.class);

        addAttribute("stretchFactorFromStrandForce", InputField._2_NUMBER).assertRequired();

    }


    //@Override
    //public Class<? extends EditorObject>[] getPossibleChildren() {
     //   return new Class[]{ "images", "imageBackgroundIds", "states", "pupilImageIds", "color" };
    //}

}
