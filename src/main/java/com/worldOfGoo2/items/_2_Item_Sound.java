package com.worldOfGoo2.items;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_SoundID;

public class _2_Item_Sound extends EditorObject {

    public _2_Item_Sound(EditorObject parent) {
        super(parent, "Item_Sound", GameVersion.VERSION_WOG2);

        addAttribute("collisionSounds", InputField._2_LIST_CHILD).setChildAlias(_2_Item_CollisionSound.class).assertRequired();
        addAttribute("minCollisionCosine", InputField._2_STRING).assertRequired();
        addAttribute("collisionTimeout", InputField._2_STRING).assertRequired();
        addAttribute("collisionLoopSound", InputField._2_CHILD).setChildAlias(_2_SoundID.class).assertRequired();
        addAttribute("minCollisionLoopAngularVelocity", InputField._2_STRING).assertRequired();
        addAttribute("maxCollisionLoopAngularVelocity", InputField._2_STRING).assertRequired();
        addAttribute("collisionLoopBlendSpeed", InputField._2_STRING).assertRequired();

    }

}
