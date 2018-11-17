package com.ianblackledge.j2braille;

public class J2Braille {
    private static char brailleStart = '\u2800';
    private static char brailleEnd = '\u28FF';
    private static char prefixUpper = '\u2820';
    private static char prefixAntiUpper = '\u2804';
    private static char prefixNumber = '\u283C';
    private static char prefixAntiNumber = '\u2830';

    public static String toBraille(String english) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = english.toCharArray();
        BrailleMode[] modes = new BrailleMode[chars.length];

        //First, process the modes of the whole string
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
            } else {
                modes[index] = BrailleMode.SPECIAL;
            }
            index++;
        }

        //Next, process and build the braille string
        index = 0;
        for (BrailleMode mode : modes) {
            switch (mode) {
                case NORMAL:
                    if (index > 0) {
                        if (modes[index - 1] == BrailleMode.CAPS) {
                            stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                        } else if (modes[index - 1] == BrailleMode.NUMBER) {
                            stringBuilder.append(prefixAntiNumber);
                        }
                    }

                    break;
                case SHIFT:
                    stringBuilder.append(prefixUpper);

                    break;
                case CAPS:
                    if (index > 0) {
                        if (modes[index - 1] != BrailleMode.CAPS) {
                            stringBuilder.append(prefixUpper).append(prefixUpper);
                        } else if (modes[index - 1] == BrailleMode.NUMBER) {
                            stringBuilder.append(prefixAntiNumber);
                        }
                    } else {
                        stringBuilder.append(prefixUpper).append(prefixUpper);
                    }

                    break;
                case NUMBER:
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

                    break;
                case SPECIAL:
                    if (index > 0 && modes[index - 1] == BrailleMode.CAPS && chars[index] != ' ') {
                        stringBuilder.append(prefixUpper).append(prefixAntiUpper);
                    }
                    break;
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
