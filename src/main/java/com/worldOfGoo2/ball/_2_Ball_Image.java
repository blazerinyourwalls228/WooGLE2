package com.worldOfGoo2.ball;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_ImageID;

public class _2_Ball_Image extends EditorObject {

    public _2_Ball_Image(EditorObject parent) {
        super(parent, "Image", GameVersion.VERSION_WOG2);

        addAttribute("imageId", InputField._2_CHILD).setChildAlias(_2_ImageID.class).assertRequired();
        addAttribute("imageMaskId", InputField._2_CHILD).setChildAlias(_2_ImageID.class).assertRequired();

    }

}
