package com.ianblackledge.j2braille;

import java.util.Arrays;
import java.util.List;

public class J2Braille {
    private static char brailleStart = '\u2800';
    private static char brailleEnd = '\u28FF';
    private static char prefixUpper = '\u2820';
    private static char prefixAntiUpper = '\u2804';
    private static char prefixNumber = '\u283C';
    private static char prefixAntiNumber = '\u2830';
    private static List<Character> specialStarts = Arrays.asList('"', '(', '{', '[', '<');
    private static List<Character> specialEnds = Arrays.asList('"', ')', '}', ']', '>');

    public static String toBraille(String english) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = english.toCharArray();
        BrailleMode[] modes = new BrailleMode[chars.length];

        //First, process the modes of the whole string
        boolean mod = false;
        int index = 0;
        for (char cha : chars) {
            if (cha >= 'a' && cha <= 'z') {
                modes[index] = BrailleMode.NORMAL;
            } else if (cha >= 'A' && cha <= 'Z') {
                //If caps, modify previous in array
                if (index > 0 && modes[index - 1] == BrailleMode.SHIFT) {
                    modes[index - 1] = BrailleMode.CAPS;
                }

                //If caps then keep it that way, otherwise shift
                if (index > 0 && modes[index - 1] == BrailleMode.CAPS) {
                    modes[index] = BrailleMode.CAPS;
                } else {
                    modes[index] = BrailleMode.SHIFT;
                }
            } else if (cha >= '0' && cha <= '9') {
                modes[index] = BrailleMode.NUMBER;
            } else if (!mod && specialStarts.contains(cha)) {
                mod = true;
                modes[index] = BrailleMode.SPECIAL_START;
            } else if (mod && specialEnds.contains(cha)) {
                mod = false;
                modes[index] = BrailleMode.SPECIAL_END;
            } else {
                modes[index] = BrailleMode.SPECIAL;
            }
            index++;
        }

        //Next, process and build the braille string
        mod = false;
        index = 0;
        for (BrailleMode mode : modes) {
            if (mode == BrailleMode.NORMAL) {

                if (index > 0) {
                    if (modes[index - 1] == BrailleMode.CAPS) {
                        stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                    } else if (modes[index - 1] == BrailleMode.NUMBER) {
                        stringBuilder.append(prefixAntiNumber);
                    }
                }

            } else if (mode == BrailleMode.SHIFT) {

                stringBuilder.append(prefixUpper);

            } else if (mode == BrailleMode.CAPS) {

                if (index > 0) {
                    if (modes[index - 1] != BrailleMode.CAPS) {
                        stringBuilder.append(prefixUpper).append(prefixUpper);
                    } else if (modes[index - 1] == BrailleMode.NUMBER) {
                        stringBuilder.append(prefixAntiNumber);
                    }
                } else {
                    stringBuilder.append(prefixUpper).append(prefixUpper);
                }

            } else if (mode == BrailleMode.NUMBER) {

                if (index > 0) {
                    if (modes[index - 1] != BrailleMode.NUMBER) {
                        if (modes[index - 1] == BrailleMode.CAPS) {
                            stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                        }
                        stringBuilder.append(prefixNumber);
                    }
                } else {
                    stringBuilder.append(prefixNumber);
                }

            } else if (mode == BrailleMode.SPECIAL) {

                if (index > 0 && modes[index - 1] == BrailleMode.CAPS && chars[index] != ' ') {
                    stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                }

            } else if (!mod && mode == BrailleMode.SPECIAL_START) {

                mod = true;
                if (index > 0 && modes[index - 1] == BrailleMode.CAPS && chars[index] != ' ') {
                    stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                }

            } else if (mod && mode == BrailleMode.SPECIAL_END) {

                mod = false;
                if (index > 0 && modes[index - 1] == BrailleMode.CAPS && chars[index] != ' ') {
                    stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                }

            }
            stringBuilder.append(new Braille(chars[index]).getBraille(mode));
            index++;
        }

        return stringBuilder.toString();
    }

    public static String toEnglish(String braille) {
        if (hasBraille(braille)) {
            //TODO: Do this!
        }
        return braille;
    }

    public static boolean hasBraille(String braille) {
        return hasBraille(braille.toCharArray());
    }

    public static boolean hasBraille(char[] braille) {
        for (char each : braille) {
            if (each >= brailleStart && each <= brailleEnd) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBraille(char braille) {
        return braille >= brailleStart && braille <= brailleEnd;
    }
}
