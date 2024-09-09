package com.worldOfGoo.addin;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;

public class Addin extends EditorObject {

    public Addin(EditorObject _parent, GameVersion version) {
        super(_parent, "addin", version);

        setAttribute("spec-version", "1.1");

    }


    @Override
    public String getName() {
        return getAttribute("spec-version").stringValue();
    }

}
