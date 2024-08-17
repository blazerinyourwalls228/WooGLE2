package com.woogleFX.gameData.level.levelSaving;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.EditorAttribute;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.WOG1Level;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.level._Level;
import com.worldOfGoo2.level._2_Level_Strand;

import java.util.ArrayList;

public class LevelVerifier {

    private static boolean verifySingleObject(EditorObject object) {
        for (EditorAttribute attribute : object.getAttributes())
            if (!InputField.verify(object, attribute.getType(), attribute.stringValue(), attribute.getRequiredInFile()))
                return false;
        return true;
    }


    private static boolean verifyAllObjects(ArrayList<EditorObject> EditorObjects) {
        for (EditorObject object : EditorObjects) if (!verifySingleObject(object)) return false;
        return true;
    }


    public static boolean verifyEntireLevel(_Level level) {
        if (level instanceof WOG1Level wog1Level) {
            return verifyAllObjects(wog1Level.getScene()) &&
                    verifyAllObjects(wog1Level.getLevel()) &&
                    verifyAllObjects(wog1Level.getResrc()) &&
                    verifyAllObjects(wog1Level.getText()) &&
                    verifyAllObjects(wog1Level.getAddin());
        } else if (level instanceof WOG2Level wog2Level) {
            return verifyAllObjects(wog2Level.getObjects());
        } else return false;
    }


    public static ArrayList<String> checkForWoG2Errors(WOG2Level level) {

        ArrayList<String> errors = new ArrayList<>();

        // Check strands for type=""
        for (EditorObject editorObject : level.getObjects()) if (editorObject instanceof _2_Level_Strand strand) {
            if (strand.getAttribute("type").stringValue().isEmpty()) {
                errors.add("Strand (" + strand.getAttribute("ball1UID").stringValue() +
                        "-" + strand.getAttribute("ball2UID") + ") has no type set");
                break;
            }
        }

        // Check level for initialCameraZoom=0
        if (level.getLevel().getAttribute("initialCameraZoom").intValue() == 0) {
            errors.add("Level initialCameraZoom is zero");
        }

        return errors;

    }

}
