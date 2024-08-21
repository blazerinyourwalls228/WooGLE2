package com.worldOfGoo2.items;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_SoundID;
import com.worldOfGoo2.misc._2_UUID;

public class _2_Item_CollisionSound extends EditorObject {

    public _2_Item_CollisionSound(EditorObject parent) {
        super(parent, "CollisionSound", GameVersion.VERSION_WOG2);

        addAttribute("materials", InputField._2_STRING).assertRequired();
        addAttribute("contactSound", InputField._2_CHILD).setChildAlias(_2_SoundID.class).assertRequired();
        addAttribute("contactParticleEffect", InputField._2_CHILD).setChildAlias(_2_UUID.class).assertRequired();
        addAttribute("minContactVelocity", InputField._2_STRING).assertRequired();
        addAttribute("maxContactVelocity", InputField._2_STRING).assertRequired();
        addAttribute("contactProbability", InputField._2_STRING).assertRequired();

    }

}
