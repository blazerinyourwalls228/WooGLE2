package com.worldOfGoo.resrc;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.editorObjects.attributes.MetaEditorAttribute;
import javafx.scene.image.Image;

public class ResrcImage extends EditorObject {

    private Image image;
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }


    private SetDefaults setDefaults;
    public void setSetDefaults(SetDefaults setDefaults) {
        this.setDefaults = setDefaults;
    }


    public String getAdjustedID() {
        if (setDefaults == null) return getAttribute("id").stringValue();
        else return setDefaults.getAttribute("idprefix").stringValue() + getAttribute("id").stringValue();
    }


    public String getAdjustedPath() {
        if (setDefaults == null) return getAttribute("path").stringValue();
        else return setDefaults.getAttribute("path").stringValue() + getAttribute("path").stringValue();
    }


    public ResrcImage(EditorObject _parent, GameVersion version) {
        super(_parent, "Image", version);

        addAttribute("id",   InputField._1_STRING).assertRequired();
        addAttribute("path", InputField._1_IMAGE_PATH).assertRequired();

        // extra attribute from 1.5
        addAttribute("atlasid", InputField._1_STRING);

        setMetaAttributes(MetaEditorAttribute.parse("id,path,"));

    }


    @Override
    public String getName() {
        return getAttribute("id").stringValue();
    }

}
