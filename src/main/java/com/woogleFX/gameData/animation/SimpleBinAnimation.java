package com.woogleFX.gameData.animation;

public class SimpleBinAnimation {

    public static class SimpleBinAnimationState {
        public int globalIdHash;
        public int localIdHash;
        public int groupOffset;
    }

    public static class SimpleBinAnimationGroup {
        public int startingFrame;
        public int duration;
        public int sectionOffset;
        public int sectionLength;
        public int eventOffset;
        public int eventLength;
    }

    public static class SimpleBinAnimationSection {
        public int type;
        public int elementOffset;
        public int elementLength;
    }

    public static class SimpleBinAnimationElement {
        public int type;
        public int offset;
        public int frame;
    }

    public static class SimpleBinAnimationKeyframe {
        public int groupOffset;
        public int unknown1;
        public float angleTopLeft;
        public float angleBottomRight;
        public float centerX;
        public float centerY;
        public float offsetX;
        public float offsetY;
        public float scaleX;
        public float scaleY;
        public int colorize;
        public float unknown2;
    }

    public static class SimpleBinAnimationPart {
        public int imageIndex;
        public float angleTopLeft;
        public float angleBottomRight;
        public float centerX;
        public float centerY;
        public float offsetX;
        public float offsetY;
        public float scaleX;
        public float scaleY;
        public int colorize;
        public int unknown1;
    }

    public static class SimpleBinAnimationStringDeclaration {
        public int index;
        public int type;
    }

    public static class SimpleBinAnimationStringDefinition {
        public String language;
        public int stringTableIndex;
    }

    public int fps;
    public SimpleBinAnimationState[] states;
    public SimpleBinAnimationGroup[] groups;
    public SimpleBinAnimationSection[] sections;
    public SimpleBinAnimationElement[] elements;
    public SimpleBinAnimationKeyframe[] keyframes;
    public SimpleBinAnimationPart[] parts;
    public int[] imageStringTableIndices;
    public int[] stateAliasStringTableIndices;
    public SimpleBinAnimationStringDeclaration[] stringDeclarations;
    public SimpleBinAnimationStringDefinition[] stringDefinitions;
    public byte[] stringTable;

}
