package com.woogleFX.editorObjects.objectComponents;

import com.woogleFX.editorObjects.DragSettings;
import com.woogleFX.engine.LevelManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;

public abstract class MeshComponent extends ObjectComponent {

    public abstract Polygon getPolygon();

    public abstract Image getImage();

    public abstract double getX();

    public abstract double getY();

    public abstract double getScaleX();

    public abstract double getScaleY();

    public abstract double getDepth();

    @Override
    public void draw(GraphicsContext graphicsContext, boolean selected) {

        Polygon polygon = getPolygon();

        Image image = getImage();

        double offsetX = LevelManager.getLevel().getOffsetX();
        double offsetY = LevelManager.getLevel().getOffsetY();
        double zoom = LevelManager.getLevel().getZoom();

        graphicsContext.save();

        Affine t = graphicsContext.getTransform();
        t.appendTranslation(offsetX, offsetY);
        t.appendScale(zoom, zoom);
        graphicsContext.setTransform(t);

        graphicsContext.setFill(new ImagePattern(image, 0, 0, image.getWidth() * getScaleX(), image.getHeight() * getScaleY(), false));

        double[] xPoints = new double[polygon.getPoints().size() / 2];
        double[] yPoints = new double[polygon.getPoints().size() / 2];
        for (int i = 0; i < polygon.getPoints().size() / 2; i++) {
            xPoints[i] = polygon.getPoints().get(i * 2);
            yPoints[i] = polygon.getPoints().get(i * 2 + 1);
        }

        graphicsContext.fillPolygon(xPoints, yPoints, polygon.getPoints().size() / 2);

        graphicsContext.restore();

    }

    @Override
    public DragSettings mouseIntersection(double mouseX, double mouseY) {
        return DragSettings.NULL;
    }

    @Override
    public DragSettings mouseIntersectingCorners(double mouseX, double mouseY) {
        return DragSettings.NULL;
    }

}
