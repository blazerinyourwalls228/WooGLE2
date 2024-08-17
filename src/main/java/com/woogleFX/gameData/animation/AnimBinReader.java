package com.woogleFX.gameData.animation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimBinReader {

    // What ARE these?
    public static String[] attemptToRead(Path path, String[] stringTable) {

        if (stringTable == null) stringTable = new String[100000]; // attemptToRead(path, new String[100000]);
        assert stringTable != null;

        try {

            ByteArrayInputStream input = new ByteArrayInputStream(Files.readAllBytes(path));

            int int1 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 1: " + int1);

            int int2 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 2: " + int2);

            int int3 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 3: " + int3);

            int int4 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 4: " + int4);

            int int5 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 5: " + int5);

            int int6 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 6: " + int6);

            int int7 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 7: " + int7);

            int int8 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 8: " + int8);

            int int9 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 9: " + int9);

            int int10 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 10: " + int10);

            int int11 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Keyframe count: " + int11);

            int int12 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 12: " + int12);

            int int13 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 13: " + int13);

            int int14 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 14: " + int14);

            int int15 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 15: " + int15);

            int int16 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 16: " + int16);

            int int17 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 17: " + int17);

            int int18 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 18: " + int18);

            int int19 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 19: " + int19);

            int int20 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 20: " + int20);

            int int21 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 21: " + int21);

            int int22 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 22: " + int22);

            int int23 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 23: " + int23);

            int int24 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 24: " + int24);

            int int25 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 25: " + int25);

            int int26 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 26: " + int26);

            int int27 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 27: " + int27);

            int int28 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 28: " + int28);

            int int29 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 29: " + int29);

            int int30 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 30: " + int30);

            int int31 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 31: " + int31);

            int int32 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 32: " + int32);

            int int33 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 33: " + int33);

            int int34 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 34: " + int34);

            int int35 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 35: " + int35);

            int int36 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 36: " + int36);

            int int37 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 37: " + int37);

            int int38 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 38: " + int38);

            int int39 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Image count: " + int39);

            int int40 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 40: " + int40);

            int int41 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Sound count: " + int41);

            int int42 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 42: " + int42);

            int int43 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Particle count: " + int43);

            int int44 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 44: " + int44);

            int int45 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("______ count: " + int45);

            int int46 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 46: " + int46);

            int int47 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Part ID count: " + int47);

            int int48 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 48: " + int48);

            int int49 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 49: " + int49);

            int int50 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 50: " + int50);

            int int51 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 51: " + int51);

            int int52 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 52: " + int52);

            int int53 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("Int 53: " + int53);

            int int54 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            // System.out.println("Int 54: " + int54);

            int int55 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("FPS: " + int55);

            // (4) - (2) = (1) * 12
            System.out.println("2 to 4 - groups of 12");
            for (int i = 0; i < int1; i++) {
                System.out.print("{ ");
                System.out.print("uid1:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("uid2:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("4to6index:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (6) - (4) = (3) * 24
            System.out.println("4 to 6 - groups of 24");
            for (int i = 0; i < int3; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 6; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (8) - (6) = (5) * 12
            System.out.println("6 to 8 - groups of 12");
            for (int i = 0; i < int5; i++) {
                System.out.print("{ ");
                System.out.print("partid:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (10) - (8) = (7) * 12
            System.out.println("8 to 10 - groups of 12");
            for (int i = 0; i < int7; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (12) - (10) = (9) * 48
            System.out.print("Keyframe data: ");
            for (int i = 0; i < int9; i++) {
                System.out.print("{ ");
                System.out.print(" ???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(" ???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(" angleTL:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" angleBR:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" x?:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" y?:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" x:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" y:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" scaleX:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" scaleY:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print(" color:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(" ???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (14) - (12) = (11) * 44
            System.out.println("12 to 14 - groups of 44");
            for (int i = 0; i < int11; i++) {
                System.out.print("{ ");
                System.out.print("img:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("x:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("y:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("scaleX:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("scaleY:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                System.out.print("color:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("???:" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (16) - (14) = (13) * 20
            System.out.println("14 to 16 - groups of 20");
            for (int i = 0; i < int13; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 5; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (18) - (16) = (15) * 8
            System.out.println("16 to 18 - groups of 8");
            for (int i = 0; i < int15; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 2; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (20) - (18) = (17) * 28
            System.out.println("18 to 20 - groups of 28");
            for (int i = 0; i < int17; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                for (int j = 1; j < 7; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (22) - (20) = (19) * 12
            System.out.println("20 to 22 - groups of 12");
            for (int i = 0; i < int19; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            System.out.println("22 to 24 - groups of 40");
            for (int i = 0; i < int23; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 10; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();



            // (34) - (32) = (31) * 92
            System.out.println("32 to 34 - groups of 92");
            for (int i = 0; i < int31; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 5; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                for (int j = 6; j < 23; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // (36) - (34) = (33) * 28
            System.out.println("34 to 36 - groups of 28");
            for (int i = 0; i < int33; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat(0) + " ");
                for (int j = 2; j < 7; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();



            // (40) - (38) = (37) *

            // (42) - (40) = (39) * 4
            System.out.print("Image string table indices: ");
            for (int i = 0; i < int39; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (44) - (42) = (41) * 4
            System.out.print("Sound string table indices: ");
            for (int i = 0; i < int41; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (46) - (44) = (43) * 4
            System.out.print("Particle string table indices: ");
            for (int i = 0; i < int43; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (48) - (46) = (45) * 4
            System.out.println("______ string table indices:");
            for (int i = 0; i < int45; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (50) - (48) = (47) * 4
            System.out.print("Part ID string table indices: ");
            for (int i = 0; i < int47; i++) {
                System.out.print("{ ");
                System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                System.out.print("}  ");
            }
            System.out.println();

            // (52) - (50) = (49) * 8
            System.out.print("Repeat of 2-4 uids: ");
            for (int i = 0; i < int49; i++) {
                System.out.print("{ ");
                for (int j = 0; j < 2; j++) {
                    System.out.print(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + " ");
                }
                System.out.print("}  ");
            }
            System.out.println();

            // String table?
            int countOfSomeKind = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            int countOfSomeKind2 = ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
            System.out.println("String count: " + countOfSomeKind);
            System.out.println("String count (again?): " + countOfSomeKind2);
            for (int i = 0; i < countOfSomeKind; i++) {
                System.out.print("{i=" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0));
                System.out.print(" type=" + ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0) + "} ");
            }
            System.out.println();
            ArrayList<String> langs = new ArrayList<>();
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < countOfSomeKind2; i++) {
                langs.add(new String(input.readNBytes(4)));
                indices.add(ByteBuffer.wrap(input.readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt(0));
            }
            byte[] stringTableBytes = input.readAllBytes();
            String[] stringTable2 = new String(stringTableBytes).split("\u0000");
            String[] table3 = new String[countOfSomeKind];
            System.arraycopy(stringTable2, 0, table3, 0, stringTable2.length);
            //System.out.println("String table: " + Arrays.toString(stringTable2));

            for (int i = 0; i < countOfSomeKind2; i++) {
                System.out.print("{lang=" + langs.get(i).substring(0, langs.get(i).indexOf("\u0000")));
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = indices.get(i); stringTableBytes[j] != 0; j++)
                    stringBuilder.append((char)stringTableBytes[j]);
                System.out.print(" i=" + stringBuilder + "} ");
            }
            System.out.println();

            return table3;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
