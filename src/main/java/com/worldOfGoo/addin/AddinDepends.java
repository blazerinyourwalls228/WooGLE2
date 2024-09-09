package com.worldOfGoo.addin;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;

public class AddinDepends extends EditorObject {

    public AddinDepends(EditorObject _parent, GameVersion version) {
        super(_parent, "depends", version);
    }

    @Override
    public String getName() {
        return getAttribute("ref").stringValue();
    }

}
