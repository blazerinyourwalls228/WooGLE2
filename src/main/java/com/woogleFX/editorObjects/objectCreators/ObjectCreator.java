package com.woogleFX.editorObjects.objectCreators;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.engine.LevelManager;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.WOG1Level;
import com.woogleFX.gameData.level.WOG2Level;
import com.woogleFX.gameData.level._Level;
import com.worldOfGoo.addin.*;
import com.worldOfGoo.ball.*;
import com.worldOfGoo.level.*;
import com.worldOfGoo.particle.*;
import com.worldOfGoo.resrc.*;
import com.worldOfGoo.scene.*;
import com.worldOfGoo.text.TextString;
import com.worldOfGoo.text.TextStrings;
import com.worldOfGoo2.environments.*;
import com.worldOfGoo2.misc._2_SoundEvent;
import com.worldOfGoo2.ball.*;
import com.worldOfGoo2.items.*;
import com.worldOfGoo2.level.*;
import com.worldOfGoo2.misc.*;
import com.worldOfGoo2.terrain._2_Terrain_Collection;
import com.worldOfGoo2.terrain._2_Terrain_EdgeTypesSettings;
import com.worldOfGoo2.terrain._2_Terrain_TerrainType;

import java.lang.reflect.InvocationTargetException;

public class ObjectCreator {

    public static EditorObject getDefaultParent(String name) {

        WOG1Level level = (WOG1Level) LevelManager.getLevel();

        return switch(name) {

            case "linearforcefield", "radialforcefield", "particles",
                    "SceneLayer", "buttongroup", "button", "circle",
                    "rectangle", "hinge", "compositegeom", "label",
                    "line", "motor", "slider" -> level.getSceneObject();

            case "BallInstance", "camera", "endoncollision",
                    "endonmessage", "endonnogeom", "fire", "levelexit",
                    "loopsound", "music", "pipe", "poi", "signpost",
                    "Strand", "targetheight" -> level.getLevelObject();

            case "font" -> level.getResrcObject();

            default -> null;

        };

    }


    public static EditorObject create(String name, EditorObject _parent, GameVersion version) {

        _Level level = LevelManager.getLevel();

        /*
        if (level instanceof WOG2Level) {
            try {
                return create2((Class<? extends EditorObject>) Class.forName(name), _parent, version);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

         */

        EditorObject parent = (_parent != null || !(level instanceof WOG1Level)) ? _parent : getDefaultParent(name);

        EditorObject toAdd = switch (name) {
            case "addin", "Addin_addin" -> new Addin(parent, version);
            case "Addin_id" -> new AddinID(parent, version);
            case "Addin_name" -> new AddinName(parent, version);
            case "Addin_type" -> new AddinType(parent, version);
            case "Addin_version" -> new AddinVersion(parent, version);
            case "Addin_description" -> new AddinDescription(parent, version);
            case "Addin_author" -> new AddinAuthor(parent, version);
            case "Addin_levels" -> new AddinLevels(parent, version);
            case "Addin_level" -> new AddinLevel(parent, version);
            case "Addin_dir" -> new AddinLevelDir(parent, version);
            case "Addin_wtf_name" -> new AddinLevelName(parent, version);
            case "Addin_subtitle" -> new AddinLevelSubtitle(parent, version);
            case "Addin_ocd" -> new AddinLevelOCD(parent, version);
            case "ambientparticleeffect" -> new Ambientparticleeffect(parent, version);
            case "axialsinoffset" -> new Axialsinoffset(parent, version);
            case "ball" -> new Ball(parent, version);
            case "BallInstance" -> new BallInstance(parent, version);
            case "ball_particles" -> new BallParticles(parent, version);
            case "ball_sound" -> new BallSound(parent, version);
            case "button" -> new Button(parent, version);
            case "buttongroup" -> new Buttongroup(parent, version);
            case "camera" -> new Camera(parent, version);
            case "circle" -> new Circle(parent, version);
            case "compositegeom" -> new Compositegeom(parent, version);
            case "detachstrand" -> new Detachstrand(parent, version);
            case "effects" -> new Effects(parent, version);
            case "endoncollision" -> new Endoncollision(parent, version);
            case "endonmessage" -> new Endonmessage(parent, version);
            case "endonnogeom" -> new Endonnogeom(parent, version);
            case "fire" -> new Fire(parent, version);
            case "font" -> new Font(parent, version);
            case "hinge" -> new Hinge(parent, version);
            case "label" -> new Label(parent, version);
            case "level" -> new Level(parent, version);
            case "levelexit" -> new Levelexit(parent, version);
            case "line" -> new Line(parent, version);
            case "linearforcefield" -> new Linearforcefield(parent, version);
            case "loopsound" -> new Loopsound(parent, version);
            case "marker" -> new Marker(parent, version);
            case "materials" -> new Materials(parent, version);
            case "material" -> new Material(parent, version);
            case "motor" -> new Motor(parent, version);
            case "music" -> new Music(parent, version);
            case "part" -> new Part(parent, version);
            case "particleeffect" -> new Particleeffect(parent, version);
            case "particle" -> new _Particle(parent, version);
            case "particles" -> new Particles(parent, version);
            case "pipe" -> new Pipe(parent, version);
            case "poi" -> new Poi(parent, version);
            case "rectangle" -> new Rectangle(parent, version);
            case "radialforcefield" -> new Radialforcefield(parent, version);
            case "ResourceManifest" -> new ResourceManifest(parent, version);
            case "Resources" -> new Resources(parent, version);
            case "Image" -> new ResrcImage(parent, version);
            case "scene" -> new Scene(parent, version);
            case "SceneLayer" -> new SceneLayer(parent, version);
            case "SetDefaults" -> new SetDefaults(parent, version);
            case "shadow" -> new Shadow(parent, version);
            case "signpost" -> new Signpost(parent, version);
            case "sinanim" -> new Sinanim(parent, version);
            case "sinvariance" -> new Sinvariance(parent, version);
            case "slider" -> new Slider(parent, version);
            case "sound", "Sound" -> new Sound(parent, version);
            case "splat" -> new Splat(parent, version);
            case "Strand" -> new Strand(parent, version);
            case "strand" -> new BallStrand(parent, version);
            case "string" -> new TextString(parent, version);
            case "strings" -> new TextStrings(parent, version);
            case "targetheight" -> new Targetheight(parent, version);
            case "Vertex" -> new Vertex(parent, version);

            case "FlashAnim" -> new FlashAnim(parent, version);

            default -> throw new RuntimeException("Attempted to create an invalid object: \"" + name + "\"");
        };

        if (parent != null) toAdd.setParent(parent);

        return toAdd;

    }

    public static EditorObject create2(Class<? extends EditorObject> tClass, EditorObject parent, GameVersion version) {

        EditorObject toAdd;
        try {
            toAdd = (EditorObject) tClass.getConstructors()[0].newInstance(parent);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        if (parent != null) toAdd.setParent(parent);

        // System.out.println(name);

        return toAdd;

    }

}
