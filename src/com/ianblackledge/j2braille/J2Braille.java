package com.ianblackledge.j2braille;

public class J2Braille {
    private static char brailleStart = '\u2800';
    private static char brailleEnd = '\u28FF';
    private static char shift = '\u2820';
    private static String caps = "\u2820\u2820";
    private static String unCaps = "\u2820\u2804";
    private static char number = '\u283C';
    private static char unNumber = '\u2830';

    public static String toBraille(String english) {
        StringBuilder brailleBuilder = new StringBuilder();

        char[] chars = english.toCharArray();
        BrailleMode mode;
        boolean quote = false;
        int index = 0;
        for (char cha : chars) {
            // Feels sloppy to put these in the loop, but it prevents odd situations
            boolean needsShift = false;
            boolean needsCaps = false;
            boolean needsUnCaps = false;
            boolean needsNumber = false;
            boolean needsUnNumber = false;

            // Next and previous characters relative to current character; ArrayIndexOutOfBoundsException-proof
            char next = index < chars.length - 1 ? chars[index + 1] : '\u0000';
            char prev = index > 0 ? chars[index - 1] : '\u0000';

            // Mode processing, feels sloppy
            if (Character.isDigit(cha)) {
                // Prefix processing
                if (!Character.isDigit(prev)) {
                    // If we're entering number mode, add a prefix
                    needsNumber = true;
                }
                // Postfix processing
                if (next != '\u0000' && !Character.isDigit(next) && !Character.isSpaceChar(next) && Character.isLowerCase(next) && next > 'a' && next < 'j') {
                    // Only add a postfix if the next character isn't a number, isn't a space, is lowercase, and can be interpreted as a Braille number
                    needsUnNumber = true;
                }
                mode = BrailleMode.NUMBER;
            } else if (Character.isLowerCase(cha)) {
                // Lowercase
                mode = BrailleMode.LOWERCASE;
            } else if (Character.isUpperCase(cha)) {
                // Caps or shift processing
                if (Character.isUpperCase(next) || Character.isUpperCase(prev)) {
                    // Prefix and postfix processing
                    if (!Character.isUpperCase(prev)) {
                        // If we're entering caps mode, add a prefix
                        needsCaps = true;
                    } else if (!Character.isUpperCase(next) && !Character.isSpaceChar(next)) {
                        // If we're exiting caps mode and the next character isn't a space, add a postfix
                        needsUnCaps = true;
                    }
                    // Caps, regardless of prefix or postfix
                    mode = BrailleMode.UPPERCASE_CAPS;
                } else {
                    // Shift, if neither of the surrounding characters are also uppercase
                    needsShift = true;
                    mode = BrailleMode.UPPERCASE_SHIFT;
                }
            } else if (cha == '"') {
                // Quotation processing
                if (!quote) {
                    // If we're not in a quotation, start one
                    mode = BrailleMode.QUOTE_START;
                    quote = true;
                } else {
                    // End a quotation if we're in one
                    mode = BrailleMode.QUOTE_END;
                    quote = false;
                }
            } else if (Character.isSpaceChar(cha)) {
                // Spaces are unique as they negate the need for postfixes
                mode = BrailleMode.SPACE;
            } else {
                // Wildcard, basically
                mode = BrailleMode.SPECIAL;
            }

            // Handle prefixes
            if (needsShift) brailleBuilder.append(shift);
            if (needsCaps) brailleBuilder.append(caps);
            if (needsNumber) brailleBuilder.append(number);

            // Main string building
            brailleBuilder.append(new Braille(cha).getBraille(mode));

            // Handle postfixes
            if (needsUnCaps) brailleBuilder.append(unCaps);
            if (needsUnNumber) brailleBuilder.append(unNumber);

            index++;
        }

        // Handle single quotation marks, kind of
        if (quote) {
            brailleBuilder.replace(brailleBuilder.lastIndexOf("\u2826"), brailleBuilder.lastIndexOf("\u2826") + 1, "\u2820\u2836");
        }

        return brailleBuilder.toString();
    }

    public static String toEnglish(String braille) {
        // Make sure there's actually any Braille characters in the string before processing
        if (hasBraille(braille)) {

        }
        return braille;
    }

    public static boolean hasBraille(String braille) {
        return hasBraille(braille.toCharArray());
    }

    public static boolean hasBraille(char[] braille) {
        for (char each : braille) {
            if (isBraille(each)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBraille(char braille) {
        return braille >= brailleStart && braille <= brailleEnd;
    }
}
