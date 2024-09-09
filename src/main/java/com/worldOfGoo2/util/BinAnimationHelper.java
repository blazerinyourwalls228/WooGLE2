package com.worldOfGoo2.util;

import com.woogleFX.editorObjects.EditorObject;
import com.woogleFX.editorObjects.objectComponents.ImageComponent;
import com.woogleFX.editorObjects.objectComponents.ObjectComponent;
import com.woogleFX.file.resourceManagers.ResourceManager;
import com.woogleFX.gameData.animation.SimpleBinAnimation;
import com.woogleFX.gameData.level.GameVersion;
import com.worldOfGoo2.level._2_Level_BallInstance;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public class BinAnimationHelper {

    public static void addBinAnimationAsObjectPositions(EditorObject editorObject, SimpleBinAnimation binAnimation) {

        ArrayList<ObjectComponent> objectComponents = new ArrayList<>();

        for (SimpleBinAnimation.SimpleBinAnimationState simpleBinAnimationState : binAnimation.states) {
            SimpleBinAnimation.SimpleBinAnimationGroup group = binAnimation.groups[simpleBinAnimationState.groupOffset];
            addBinAnimationGroupAsObjectPositions(objectComponents, editorObject, binAnimation, group, 0, 0, 1, 1, 0);
            break;
        }

        for (int i = objectComponents.size() - 1; i >= 0; i--) {
            editorObject.addObjectComponent(objectComponents.get(i));
        }

    }


    public static void addBinAnimationGroupAsObjectPositions(ArrayList<ObjectComponent> objectComponents, EditorObject editorObject, SimpleBinAnimation binAnimation, SimpleBinAnimation.SimpleBinAnimationGroup animationGroup, double x, double y, double scaleX, double scaleY, double rotation) {

        for (int i = 0; i < animationGroup.sectionLength; i++) {
            SimpleBinAnimation.SimpleBinAnimationSection section = binAnimation.sections[i + animationGroup.sectionOffset];
            for (int j = 0; j < section.elementLength; j++) {
                SimpleBinAnimation.SimpleBinAnimationElement element = binAnimation.elements[j + section.elementOffset];
                if (element.frame != 0) continue;
                switch (element.type) {
                    case 1 -> {
                        SimpleBinAnimation.SimpleBinAnimationKeyframe keyframe = binAnimation.keyframes[element.offset];
                        SimpleBinAnimation.SimpleBinAnimationGroup group = binAnimation.groups[keyframe.groupOffset];
                        addBinAnimationGroupAsObjectPositions(objectComponents, editorObject, binAnimation, group, x + (keyframe.offsetX - keyframe.centerX) * scaleX, y + (keyframe.offsetY - keyframe.centerY) * scaleY, scaleX * keyframe.scaleX, scaleY * keyframe.scaleY, rotation - keyframe.angleBottomRight);
                    }
                    case 2 -> {
                        SimpleBinAnimation.SimpleBinAnimationPart part = binAnimation.parts[element.offset];
                        // Render part
                        StringBuilder stringBuilder = new StringBuilder();
                        int byteIndex = binAnimation.stringDefinitions[binAnimation.imageStringTableIndices[part.imageIndex]].stringTableIndex;
                        while (binAnimation.stringTable[byteIndex] != 0x00) {
                            stringBuilder.append((char)binAnimation.stringTable[byteIndex]);
                            byteIndex++;
                        }
                        try {
                            Image image = ResourceManager.getImage((editorObject instanceof _2_Level_BallInstance ballInstance ? ballInstance.getBall().getResources() : null), stringBuilder.toString(), GameVersion.VERSION_WOG2);

                            double scaleFactor = ((editorObject instanceof _2_Level_BallInstance ballInstance ? ballInstance.getBall().getObjects().get(0).getChildren("ballParts").get(0).getAttribute("scale").doubleValue() : 1)) / 100;

                            double addX = x * scaleFactor + (part.offsetX - part.centerX * part.scaleX) * scaleX * scaleFactor + image.getWidth() * scaleX * part.scaleX * scaleFactor / 2;
                            double addY = y * scaleFactor + (part.offsetY - part.centerY * part.scaleY) * scaleY * scaleFactor + image.getHeight() * scaleY * part.scaleY * scaleFactor / 2;


                            objectComponents.add(new ImageComponent() {
                                @Override
                                public Image getImage() {
                                    return image;
                                }

                                @Override
                                public double getX() {
                                    double ballX = editorObject.getChildren("pos").get(0).getAttribute("x").doubleValue();
                                    return ballX + addX;
                                }

                                @Override
                                public void setX(double x) {
                                    editorObject.getChildren("pos").get(0).setAttribute("x", x - addX);
                                }

                                @Override
                                public double getY() {
                                    double ballY = -editorObject.getChildren("pos").get(0).getAttribute("y").doubleValue();
                                    return ballY + addY;
                                }

                                @Override
                                public void setY(double y) {
                                    editorObject.getChildren("pos").get(0).setAttribute("y", -y + addY);
                                }

                                @Override
                                public double getScaleX() {
                                    return scaleX * part.scaleX * scaleFactor;
                                }

                                @Override
                                public double getScaleY() {
                                    return scaleY * part.scaleY * scaleFactor;
                                }

                                @Override
                                public double getDepth() {
                                    return 100000;
                                }
                            });

                        } catch (IOException ignored) {
                            ignored.printStackTrace();
                        }

                    }
                }
            }
        }

    }

}
