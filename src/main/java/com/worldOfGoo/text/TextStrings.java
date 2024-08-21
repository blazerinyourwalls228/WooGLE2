package com.worldOfGoo.text;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;

public class TextStrings extends EditorObject {

    public TextStrings(EditorObject _parent, GameVersion version) {
        super(_parent, "strings", version);
    }


    @Override
    public Class<? extends EditorObject>[] getPossibleChildren() {
        return new Class[]{ TextString.class };
    }


}
