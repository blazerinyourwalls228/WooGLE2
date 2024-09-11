package com.woogleFX.gameData.animation;

public class SimpleBinAnimation {

    public String name;

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

    public static class SimpleBinAnimationExternal {
        public int globalIdHash;
        public int type;
        public int property12Offset;
        public int property9Offset;
        public int property9Length;
    }

    public static class SimpleBinAnimationProperty9 {
        public int type;
        public float unknown1;
        public float unknown2;
        public float unknown3;
        public float unknown4;
        public float unknown5;
        public float unknown6;
    }

    public static class SimpleBinAnimationProperty12 {
        public int type;
        public int idHash;
        public int attribute3;
        public int attribute4;
        public int attribute5;
        public float attribute6;
        public int attribute7;
        public int attribute8;
        public int attribute9;
        public int attribute10;
        public int attribute11;
        public int attribute12;
        public int attribute13;
        public int attribute14;
        public int attribute15;
        public int attribute16;
        public int attribute17;
        public int attribute18;
        public int attribute19;
        public int attribute20;
        public int attribute21;
        public int attribute22;
        public int stringTableIndex;
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
    public SimpleBinAnimationExternal[] externals;
    public SimpleBinAnimationProperty9[] property9s;
    public SimpleBinAnimationProperty12[] property12s;
    public int[] imageStringTableIndices;
    public int[] stateAliasStringTableIndices;
    public SimpleBinAnimationStringDeclaration[] stringDeclarations;
    public SimpleBinAnimationStringDefinition[] stringDefinitions;
    public byte[] stringTable;

}
