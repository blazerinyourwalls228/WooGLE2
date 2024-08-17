package com.worldOfGoo2.util;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.environments._2_Environment;
import com.worldOfGoo2.environments._2_Environment_ForegroundFX;
import com.worldOfGoo2.environments._2_Environment_Layer;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class EnvironmentHelper {

    public static Image singleFromEnvironment(_2_Environment environment) {

        Canvas canvas = new Canvas();

        canvas.setWidth(256);
        canvas.setHeight(256);

        //canvas.setWidth(boundsTopRight.getAttribute("x").doubleValue() - boundsBottomLeft.getAttribute("x").doubleValue());
        //canvas.setHeight(boundsTopRight.getAttribute("y").doubleValue() - boundsBottomLeft.getAttribute("y").doubleValue());

        ArrayList<_2_Environment_Layer> layers = new ArrayList<>();
        for (EditorObject part : environment.getChildren()) if (part instanceof _2_Environment_Layer layer) {
            layers.add(layer);
        }
        layers.sort((o1, o2) -> {
            var d1 = o1.getAttribute("depth").doubleValue();
            var d2 = o2.getAttribute("depth").doubleValue();
            return Double.compare(d1, d2);
        });

        var gc = canvas.getGraphicsContext2D();

        var monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        var params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);

        for (_2_Environment_Layer layer : layers) {
            /*
            if (layer.getAttribute("foreground").booleanValue()) {
                continue;
            }
            */
            try {
                var color = new BigInteger(layer.getAttribute("color").stringValue()).toString(16);
                Image image = layer.getAttribute("imageName").imageValue(null, GameVersion.VERSION_WOG2);
                ImageView view = getEffectsView(image, monochrome, color);

                int blendMode = 2;
                try {
                    Integer.parseInt(layer.getAttribute("blendingType").stringValue()); // very very sorry to whoever finds this
                } catch (Exception ignore) {}

                gc.save();

                gc.setGlobalAlpha(hex2ARGB(color).getOpacity());

                switch(blendMode) {
                    case 3: { // ADD
                        gc.setGlobalBlendMode(BlendMode.ADD);
                    }
                    default: {
                        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
                    };
                }

                WritableImage snapshot = new WritableImage((int)view.getFitWidth(), (int)view.getFitHeight());
                view.snapshot(params, snapshot);
                gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());

                gc.setGlobalBlendMode(BlendMode.SRC_OVER);
                gc.setEffect(null);

                gc.restore();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        for (EditorObject part : environment.getChildren()) if (part instanceof _2_Environment_ForegroundFX fx) {
            //if (fx.getName().equalsIgnoreCase("finalFx")) {

                var blend = new Blend(BlendMode.MULTIPLY, monochrome,
                        new ColorInput(0, 0, canvas.getWidth(), canvas.getHeight(),
                                new javafx.scene.paint.Color(
                                        part.getAttribute("layerRed").doubleValue(),
                                        part.getAttribute("layerGreen").doubleValue(),
                                        part.getAttribute("layerBlue").doubleValue(), 0f)));
                gc.setEffect(blend);
            //}
        }

        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);

        return writableImage;
    }

    private static ImageView getEffectsView(Image image, ColorAdjust monochrome, String color) {
        ImageView view = new ImageView();
        view.setFitWidth(image.getWidth());
        view.setFitHeight(image.getHeight());
        view.setImage(image);
        /*
        var blend = new Blend(BlendMode.MULTIPLY, monochrome,
                new ColorInput(0, 0, view.getImage().getWidth(), view.getImage().getHeight(),
                        new javafx.scene.paint.Color(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, color.getAlpha() / 255d)));
        view.setEffect(blend);
        */
        /*
        Lighting lighting = new Lighting(new Light.Distant(45, 90, hex2ARGB(color)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);

        view.setEffect(lighting);

         */

        var c = hex2ARGB(color);

        ColorAdjust bright = new ColorAdjust();

        bright.setSaturation(c.getSaturation());
        bright.setHue(c.getHue());

        view.setEffect(bright);

        return view;
    }

    public static javafx.scene.paint.Color hex2ARGB(String h) {
        try {
            if (h.length() == 8) { // AARRGGBB
                var a = Integer.valueOf(h.substring(0, 2), 16);
                var r = Integer.valueOf(h.substring(2, 4), 16);
                var g = Integer.valueOf(h.substring(4, 6), 16);
                var b = Integer.valueOf(h.substring(6, 8), 16);
                return new Color(r / 255d, g / 255d, b / 255d, a / 255d);
            } else { // RRGGBB
                var r = Integer.valueOf(h.substring(0, 2), 16);
                var g = Integer.valueOf(h.substring(2, 4), 16);
                var b = Integer.valueOf(h.substring(4, 6), 16);
                return new Color(r / 255d, g / 255d, b / 255d, 1.0);
            }
        } catch(Exception e) { // If a color is invalid we'll just use white
            System.out.println("Error while loading color " + h);
            e.printStackTrace();
            return new Color(1, 1, 1,1);
        }
    }

}
