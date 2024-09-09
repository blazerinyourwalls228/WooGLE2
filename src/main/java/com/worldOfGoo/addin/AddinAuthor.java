package com.worldOfGoo.addin;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;

public class AddinAuthor extends EditorObject {

    public AddinAuthor(EditorObject _parent, GameVersion version) {
        super(_parent, "author", version);
    }

    @Override
    public String getName() {
        return getAttribute("value").stringValue();
    }

}
