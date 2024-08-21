package com.worldOfGoo2.environments;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc._2_Point;

public class _2_Environment extends EditorObject {

    public _2_Environment(EditorObject parent) {
        super(parent, "Environment", GameVersion.VERSION_WOG2);

        addAttribute("name", InputField._2_STRING).assertRequired();
        addAttribute("id", InputField._2_STRING).assertRequired();
        addAttribute("clearColor", InputField._2_STRING);
        addAttribute("layers", InputField._2_LIST_CHILD).assertRequired().setChildAlias(_2_Environment_Layer.class);
        addAttribute("bloomPosition", InputField._2_CHILD).assertRequired().setChildAlias(_2_Point.class);
        addAttribute("bloomRange", InputField._2_STRING).assertRequired();
        addAttribute("lightingColor", InputField._2_STRING).assertRequired();
        addAttribute("dynamicLighting", InputField._2_STRING).assertRequired();
        addAttribute("downloadLighting", InputField._2_STRING);
        addAttribute("dynamicLightingColor", InputField._2_STRING);
        addAttribute("dynamicLightingDecayFactor", InputField._2_STRING);
        addAttribute("lightingTopLeftColor", InputField._2_STRING).assertRequired();
        addAttribute("lightingTopRightColor", InputField._2_STRING).assertRequired();
        addAttribute("lightingBottomLeftColor", InputField._2_STRING).assertRequired();
        addAttribute("lightingBottomRightColor", InputField._2_STRING).assertRequired();
        addAttribute("lightingBackgroundTopLeftColor", InputField._2_STRING);
        addAttribute("lightingBackgroundTopRightColor", InputField._2_STRING);
        addAttribute("lightingBackgroundBottomLeftColor", InputField._2_STRING);
        addAttribute("lightingBackgroundBottomRightColor", InputField._2_STRING);
        addAttribute("waterTopColor", InputField._2_STRING).assertRequired();
        addAttribute("waterBottomColor", InputField._2_STRING).assertRequired();
        addAttribute("waterFoamColor", InputField._2_STRING);
        addAttribute("waterFoamEndColor", InputField._2_STRING);
        addAttribute("waterColorBlend", InputField._2_STRING);
        addAttribute("waterBloomFactor", InputField._2_STRING);
        addAttribute("waterRandomRippleTimeout", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("waterRandomRippleSize", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("waterRandomRippleTime", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("liquidOverrides", InputField._2_LIST_CHILD).setChildAlias(_2_Environment_LiquidOverride.class).assertRequired();
        addAttribute("backgroundBloomFactor", InputField._2_STRING).assertRequired();
        addAttribute("stableFluidsBloomFactor", InputField._2_CHILD).setChildAlias(_2_Environment_StableFluidsBloomFactor.class);
        addAttribute("windForce", InputField._2_CHILD).setChildAlias(_2_Point.class).assertRequired();
        addAttribute("windFactor", InputField._2_STRING);
        addAttribute("windAddFactor", InputField._2_STRING);
        addAttribute("windDecorationFactor", InputField._2_STRING);
        addAttribute("shadowOffset", InputField._2_STRING);
        addAttribute("shadowColor", InputField._2_STRING);
        addAttribute("inactiveStrandOverlayFactor", InputField._2_STRING);
        addAttribute("trajectoryOverlayFactor", InputField._2_STRING);
        addAttribute("finalFx", InputField._2_CHILD).setChildAlias(_2_Environment_ForegroundFX.class).assertRequired();
        addAttribute("sound", InputField._2_CHILD).setChildAlias(_2_Environment_Sound.class).assertRequired();
        addAttribute("fireBlendingType", InputField._2_STRING).assertRequired();
        addAttribute("fireLut", InputField._2_STRING).assertRequired();
        addAttribute("filmGrainBlend", InputField._2_STRING);
        addAttribute("vignetteBlendStart", InputField._2_STRING);
        addAttribute("vignetteBlendEnd", InputField._2_STRING);
        addAttribute("vignetteShape", InputField._2_CHILD).setChildAlias(_2_Point.class);
        addAttribute("postProcessBlendingType", InputField._2_STRING);
        addAttribute("lowPerformanceLaserFactor", InputField._2_STRING);
        addAttribute("environmentName", InputField._2_STRING);
        addAttribute("backgroundFx", InputField._2_CHILD).setChildAlias(_2_Environment_ForegroundFX.class);
        addAttribute("preForegroundFx", InputField._2_CHILD).setChildAlias(_2_Environment_ForegroundFX.class);
        addAttribute("foregroundFx", InputField._2_CHILD).setChildAlias(_2_Environment_ForegroundFX.class);
        addAttribute("postForegroundFx", InputField._2_CHILD).setChildAlias(_2_Environment_ForegroundFX.class);

    }

}
