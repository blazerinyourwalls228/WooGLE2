package com.worldOfGoo2.ball;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.attributes.InputField;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.misc.*;

public class _2_Ball extends EditorObject {

    public _2_Ball(EditorObject parent) {
        super(parent, "Ball", GameVersion.VERSION_WOG2);

        addAttribute("name", InputField._2_STRING).assertRequired();
        addAttribute("width", InputField._2_STRING).assertRequired();
        addAttribute("height", InputField._2_STRING).assertRequired();

        addAttribute("shape", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_Shape.class);

        addAttribute("sizeVariance", InputField._2_STRING).assertRequired();
        addAttribute("mass", InputField._2_STRING).assertRequired();
        addAttribute("towerMass", InputField._2_STRING).assertRequired();
        addAttribute("dragMass", InputField._2_STRING).assertRequired();
        addAttribute("destroyForce", InputField._2_STRING).assertRequired();
        addAttribute("maxDropDistance", InputField._2_STRING).assertRequired();
        addAttribute("maxStrands", InputField._2_STRING).assertRequired();
        addAttribute("walkSpeed", InputField._2_STRING).assertRequired();
        addAttribute("climbSpeed", InputField._2_STRING).assertRequired();
        addAttribute("speedVariance", InputField._2_STRING).assertRequired();
        addAttribute("antiGravFactor", InputField._2_STRING).assertRequired();
        addAttribute("isAntiGravAttached", InputField._2_STRING).assertRequired();
        addAttribute("isAntiGravUnattached", InputField._2_STRING).assertRequired();
        addAttribute("dampening", InputField._2_STRING).assertRequired();
        addAttribute("isEditable", InputField._2_STRING).assertRequired();
        addAttribute("isDraggable", InputField._2_STRING).assertRequired();
        addAttribute("isDetachable", InputField._2_STRING).assertRequired();
        addAttribute("diesOnDetach", InputField._2_STRING).assertRequired();
        addAttribute("isInvulnerable", InputField._2_STRING).assertRequired();
        addAttribute("isInvulnerableToLava", InputField._2_STRING);
        addAttribute("isStickyAttached", InputField._2_STRING).assertRequired();
        addAttribute("isStickyUnattached", InputField._2_STRING).assertRequired();
        addAttribute("attachToSameTypeOnly", InputField._2_STRING).assertRequired();
        addAttribute("isGrumpy", InputField._2_STRING).assertRequired();
        addAttribute("isStatic", InputField._2_STRING).assertRequired();
        addAttribute("isStaticWhenSleeping", InputField._2_STRING).assertRequired();
        addAttribute("selectionMarkerRadius", InputField._2_STRING);
        addAttribute("attachedMaginSS", InputField._2_STRING);
        addAttribute("unattachedMarginSS", InputField._2_STRING);
        addAttribute("health", InputField._2_STRING).assertRequired();

        addAttribute("blinkColor", InputField._2_STRING).assertRequired().setChildAlias(_2_Color.class);

        addAttribute("jumpMultiplierMin", InputField._2_STRING).assertRequired();
        addAttribute("jumpMultiplierMax", InputField._2_STRING).assertRequired();
        addAttribute("collideAttached", InputField._2_STRING).assertRequired();
        addAttribute("collideWithAttached", InputField._2_STRING).assertRequired();
        addAttribute("allowAttachmentsWhenStuck", InputField._2_STRING).assertRequired();
        addAttribute("allowAttachmentsWhenFalling", InputField._2_STRING).assertRequired();
        addAttribute("motorMaxForce", InputField._2_STRING).assertRequired();
        addAttribute("motorMaxForceUndiscovered", InputField._2_STRING);
        addAttribute("isSuckable", InputField._2_STRING).assertRequired();
        addAttribute("opensExitUnattached", InputField._2_STRING);
        addAttribute("affectsAutoboundsAttached", InputField._2_STRING).assertRequired();
        addAttribute("affectsAutoboundsUnattached", InputField._2_STRING).assertRequired();
        addAttribute("burnTime", InputField._2_STRING).assertRequired();
        addAttribute("detonateForce", InputField._2_STRING).assertRequired();
        addAttribute("detonateRadius", InputField._2_STRING).assertRequired();
        addAttribute("detonateKillBalls", InputField._2_STRING);
        addAttribute("detonateKillItems", InputField._2_STRING);
        addAttribute("detonateKillTerrain", InputField._2_STRING);

        addAttribute("detonateParticleEffect", InputField._2_STRING).setChildAlias(_2_UUID.class);

        addAttribute("autoAttach", InputField._2_STRING).assertRequired();
        addAttribute("isClimber", InputField._2_STRING).assertRequired();
        addAttribute("attachedParticleBarrierFactor", InputField._2_STRING).assertRequired();

        addAttribute("material", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_Material.class);

        addAttribute("contains", InputField._2_STRING).assertRequired();

        addAttribute("popSoundId", InputField._2_STRING).assertRequired().setChildAlias(_2_SoundID.class);

        addAttribute("popParticlesId", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("fireworksParticleEffect", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("trailEffectId", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("trailParticleEffect", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("trailEffectEnabled", InputField._2_STRING).assertRequired();
        addAttribute("popDuration", InputField._2_STRING).assertRequired();
        addAttribute("popDelayMin", InputField._2_STRING).assertRequired();
        addAttribute("popDelayMax", InputField._2_STRING).assertRequired();
        addAttribute("hideEyes", InputField._2_STRING).assertRequired();
        addAttribute("botoxEyesWhenAttached", InputField._2_STRING);
        addAttribute("isBehindStrands", InputField._2_STRING).assertRequired();
        addAttribute("wakeOtherBallsAtDistance", InputField._2_STRING).assertRequired();

        addAttribute("spawnType", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_Type.class);

        addAttribute("decay", InputField._2_STRING).assertRequired();
        addAttribute("flingForceFactor", InputField._2_STRING).assertRequired();
        addAttribute("flingStrandMaxLength", InputField._2_STRING).assertRequired();
        addAttribute("autoDisable", InputField._2_STRING).assertRequired();
        addAttribute("isStacking", InputField._2_STRING);
        addAttribute("maxDragForce", InputField._2_STRING).assertRequired();
        addAttribute("dragDampeningFactor", InputField._2_STRING).assertRequired();
        addAttribute("alwaysLookAtMouse", InputField._2_STRING).assertRequired();
        addAttribute("hingeDrag", InputField._2_STRING).assertRequired();
        addAttribute("maxAttachSpeed", InputField._2_STRING).assertRequired();
        addAttribute("jumpOnWakeup", InputField._2_STRING).assertRequired();
        addAttribute("thrust", InputField._2_STRING).assertRequired();
        addAttribute("useDistantSounds", InputField._2_STRING).assertRequired();
        addAttribute("canBeRotatedByHand", InputField._2_STRING).assertRequired();
        addAttribute("stencil", InputField._2_STRING).assertRequired();
        addAttribute("stencilWhenAttached", InputField._2_STRING);
        addAttribute("canSuckLiquidExternally", InputField._2_STRING).assertRequired();
        addAttribute("canSuckLiquidFromPipe", InputField._2_STRING).assertRequired();
        addAttribute("liquidParticlesPerSecond", InputField._2_STRING).assertRequired();
        addAttribute("initialBallLiquidAmount", InputField._2_STRING).assertRequired();
        addAttribute("maxBallLiquidAmount", InputField._2_STRING).assertRequired();
        addAttribute("maxStrandLiquidAmount", InputField._2_STRING).assertRequired();
        addAttribute("strandSuckLiquidParticlesPerSecond", InputField._2_STRING).assertRequired();
        addAttribute("hitVelocityAccumulationLimit", InputField._2_STRING).assertRequired();

        addAttribute("shadowImageId", InputField._2_STRING).setChildAlias(_2_ImageID.class);

        addAttribute("shadowImageIsAdditive", InputField._2_STRING);

        addAttribute("strandType", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_StrandType.class);

        addAttribute("springConstMin", InputField._2_STRING).assertRequired();
        addAttribute("springConstMax", InputField._2_STRING).assertRequired();
        addAttribute("strandDampening", InputField._2_STRING).assertRequired();
        addAttribute("maxReplacementStrandLength", InputField._2_STRING).assertRequired();
        addAttribute("maxNormalStrandLength", InputField._2_STRING).assertRequired();
        addAttribute("minStrandLength", InputField._2_STRING).assertRequired();
        addAttribute("strandShrinkLength", InputField._2_STRING).assertRequired();
        addAttribute("maxStretchForce", InputField._2_STRING).assertRequired();
        addAttribute("maxCompressForce", InputField._2_STRING).assertRequired();
        addAttribute("strandDensity", InputField._2_STRING).assertRequired();
        addAttribute("strandThickness", InputField._2_STRING).assertRequired();
        addAttribute("isSingleStrandAllowed", InputField._2_STRING).assertRequired();
        addAttribute("isSingleStrandAllowedEvenForOneStrand", InputField._2_STRING);
        addAttribute("allowBallToStrandConversion", InputField._2_STRING).assertRequired();
        addAttribute("swallowBall", InputField._2_STRING);
        addAttribute("useStrandConnect", InputField._2_STRING).assertRequired();
        addAttribute("isStrandWalkable", InputField._2_STRING).assertRequired();
        addAttribute("canShrinkStrand", InputField._2_STRING).assertRequired();

        addAttribute("strandImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("strandInactiveImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("strandInactiveOverlayImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("strandIgniteDelay", InputField._2_STRING).assertRequired();
        addAttribute("strandBurnSpeed", InputField._2_STRING).assertRequired();
        addAttribute("strandFireParticlesId", InputField._2_STRING).assertRequired();

        addAttribute("strandBurntImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("strandBackgroundImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("detachStrandImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("detachStrandMaxLength", InputField._2_STRING).assertRequired();

        addAttribute("dragMarkerImageId", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("detachMarkerImageId", InputField._2_STRING).setChildAlias(_2_ImageID.class);

        addAttribute("markerRotSpeed", InputField._2_STRING).assertRequired();

        addAttribute("stainLiquidType", InputField._2_STRING).assertRequired().setChildAlias(_2_LiquidType.class);

        addAttribute("deselectAttenuationFunc", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_AttenuationFunction.class);

        addAttribute("selectAttenuationFunc", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_AttenuationFunction.class);

        addAttribute("dropAttenuationFunc", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_AttenuationFunction.class);

        addAttribute("dragAttenuationFunc", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_AttenuationFunction.class);

        addAttribute("spawnAttenuationFunc", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_AttenuationFunction.class);

        addAttribute("ballParts", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_Part.class);

        addAttribute("bodyPart", InputField._2_STRING).assertRequired().setChildAlias(_2_BodyPart.class);

        addAttribute("stateAnimations", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_StateAnimation.class);

        addAttribute("splatImageIds", InputField._2_STRING).assertRequired().setChildAlias(_2_ImageID.class);

        addAttribute("soundEvents", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_SoundEvent.class);

        addAttribute("flashAnimation", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_FlashAnimation.class);

        addAttribute("stateScales", InputField._2_STRING).assertRequired();

        addAttribute("particleEffects", InputField._2_STRING).assertRequired().setChildAlias(_2_Ball_ParticleEffect.class);

        addAttribute("stableFluidsDensityFactor", InputField._2_STRING).assertRequired().setChildAlias(_2_Color.class);

        addAttribute("stableFluidsMinTrailVelocity", InputField._2_STRING).assertRequired();

        addAttribute("stableFluidsDensityRange", InputField._2_STRING).assertRequired().setChildAlias(_2_Point.class);

        addAttribute("isLauncher", InputField._2_STRING).assertRequired();
        addAttribute("canBeSuckedByLauncher", InputField._2_STRING).assertRequired();
        addAttribute("whenAttachedRenderOnTop", InputField._2_STRING).assertRequired();
        addAttribute("lighting", InputField._2_STRING).assertRequired();

        addAttribute("popSpawnItems", InputField._2_STRING).setChildAlias(_2_UUID.class);

        addAttribute("popSpawnItemProbability", InputField._2_STRING);

        addAttribute("popSpawnItemCountRange", InputField._2_STRING).setChildAlias(_2_Point.class);

        addAttribute("popSpawnItemRadiusRange", InputField._2_STRING).setChildAlias(_2_Point.class);

        addAttribute("popSpawnItemScaleRange", InputField._2_STRING).setChildAlias(_2_Point.class);

        addAttribute("strandShatterItem", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("strandShatterItemProbability", InputField._2_STRING).assertRequired();

        addAttribute("strandShatterParticleEffect", InputField._2_STRING).assertRequired().setChildAlias(_2_UUID.class);

        addAttribute("strandShatterParticleEffectProbability", InputField._2_STRING).assertRequired();
        addAttribute("strandShatterFragmentSize", InputField._2_STRING).assertRequired();
        addAttribute("unwalkableTime", InputField._2_STRING).assertRequired();
        addAttribute("dynamicLightingFactor", InputField._2_STRING).assertRequired();
        addAttribute("attachedDynamicLightingFactor", InputField._2_STRING);
        addAttribute("undiscoveredDynamicLightingFactor", InputField._2_STRING);
        addAttribute("shadow", InputField._2_STRING).assertRequired();
        addAttribute("neededLighting", InputField._2_STRING).assertRequired();
        addAttribute("sortOffset", InputField._2_STRING);
        addAttribute("canPinToJelly", InputField._2_STRING);
        addAttribute("hideWhenPinnedToJelly", InputField._2_STRING);
        addAttribute("popOnUnpinFromJelly", InputField._2_STRING);
        addAttribute("canStrandCutJelly", InputField._2_STRING);
        addAttribute("inactiveStrandCollideWithJelly", InputField._2_STRING);
        addAttribute("renderInJellyBackground", InputField._2_STRING);

        addAttribute("thrusterStableFluidsImage", InputField._2_STRING).setChildAlias(_2_ImageID.class);

        addAttribute("markerColor", InputField._2_STRING).assertRequired().setChildAlias(_2_Color.class);

        addAttribute("zoomFactorAttached", InputField._2_STRING);
        addAttribute("zoomFactorUnattached", InputField._2_STRING);
        addAttribute("zoomSpeed", InputField._2_STRING);
        addAttribute("liquidParticlesRadiusScale", InputField._2_STRING);

        addAttribute("structureFillingLayers", InputField._2_STRING).setChildAlias(_2_Ball_StructureFillingLayer.class);

        addAttribute("ropeOffsetRadiusScale", InputField._2_STRING);
        addAttribute("ropeStiffness", InputField._2_STRING);
        addAttribute("ropeDamping", InputField._2_STRING);

        addAttribute("collideWithParticlesAttached", InputField._2_STRING);
        addAttribute("liquidSinkOffset", InputField._2_STRING);
        addAttribute("strandGrowMultiplier", InputField._2_STRING);
        addAttribute("renderInPlayMode", InputField._2_STRING);
        addAttribute("canActOnBalls", InputField._2_STRING);

        addAttribute("visibilityScale", InputField._2_STRING);
        addAttribute("rotationalDampening", InputField._2_STRING);
        addAttribute("collisionGroup", InputField._2_STRING);
        addAttribute("despawnTriggersFullDeath", InputField._2_STRING);

        addAttribute("deathParticleEffect", InputField._2_CHILD).setChildAlias(_2_UUID.class);

        addAttribute("flashAnimationPreLiquidLayerTextureIndices", InputField._2_STRING);
        addAttribute("inputPipeForceVariation", InputField._2_STRING);
        addAttribute("laserCount", InputField._2_STRING);

        addAttribute("laserGradientStart", InputField._2_STRING).setChildAlias(_2_Color.class);

        addAttribute("laserGradientEnd", InputField._2_STRING).setChildAlias(_2_Color.class);

        addAttribute("laserOverrideImage", InputField._2_STRING).setChildAlias(_2_ImageID.class);

        addAttribute("countMoveOnUnatachedRelease", InputField._2_STRING);
        addAttribute("maxStrandAngle", InputField._2_STRING);
        addAttribute("maxStrandSeparation", InputField._2_STRING);

    }

}
