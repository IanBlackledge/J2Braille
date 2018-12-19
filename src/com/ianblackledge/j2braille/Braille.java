/***********************************************************
 * J2Braille - Copyright (c) 2018 Ian Blackledge
 *
 * Licenced under the GNU General Public License v3.0
 * (Please see the license document for details.)
 **********************************************************/

package com.ianblackledge.j2braille;

import java.util.Arrays;

class Braille {
    static String[] brailleAZ = {"\u2801", "\u2803", "\u2809", "\u2819", "\u2811", "\u280b", "\u281b", "\u2813", "\u280a", "\u281a", "\u2805", "\u2807", "\u280d", "\u281d", "\u2815", "\u280f", "\u281f", "\u2817", "\u280e", "\u281e", "\u2825", "\u2827", "\u283a", "\u282d", "\u283d", "\u2835"};
    static String[] brailleInt = {"\u281a", "\u2801", "\u2803", "\u2809", "\u2819", "\u2811", "\u280b", "\u281b", "\u2813", "\u280a"};
    static String[] brailleSpecial = {"\u2818\u2821", "\u2808\u2814", "\u2816", "\u2808\u2801", "\u2838\u2839", "\u2808\u280e", "\u2828\u2834", "\u2808\u2822", "\u2808\u282f", "\u2810\u2814", "\u2810\u2823", "\u2810\u281c", "\u2824", "\u2828\u2824", "\u2810\u2836", "\u2810\u2816", "\u2828\u2823", "\u2838\u2823", "\u2828\u281c", "\u2838\u281c", "\u2838\u2821", "\u2812", "\u2806", "\u2804", "\u2820\u2836", "\u2802", "\u2808\u2823", "\u2832", "\u2808\u281c", "\u2838\u280c", "\u2826"};
    private static String[] englishAZ = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static String[] englishInt = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] englishSpecial = {"`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "{", "]", "}", "\\", ":", ";", "'", "\"", ",", "<", ".", ">", "/", "?"};
    private char cha = '\u0000';
    private String longCha = "";

    Braille(char cha) {
        this.cha = cha;
    }

    Braille(String string) {
        this.longCha = string;
    }

    String getBraille(BrailleMode mode) {
        String braille = String.valueOf(this.cha);

        // Decide the character(s) to return as a string
        switch (mode) {
            case LOWERCASE:
                braille = String.valueOf(brailleAZ[this.cha - 'a']);
                break;
            case UPPERCASE_SHIFT:
            case UPPERCASE_CAPS:
                braille = String.valueOf(brailleAZ[this.cha - 'A']);
                break;
            case NUMBER:
                braille = String.valueOf(brailleInt[this.cha - '0']);
                break;
            case SPACE:
                braille = "\u2800";
                break;
            case SPECIAL:
                int specialIndex = Arrays.asList(englishSpecial).indexOf(braille);
                braille = specialIndex >= 0 ? brailleSpecial[specialIndex] : braille;
                break;
            case QUOTE_START:
                braille = "\u2826";
                break;
            case QUOTE_END:
                braille = "\u2834";
                break;
        }

        return braille;
    }

    String getEnglish(BrailleMode mode) {
        String english;

        if (this.cha != '\u0000') {
            english = String.valueOf(this.cha);
        } else {
            english = longCha;
        }

        int indexSpecial = Arrays.asList(brailleSpecial).indexOf(english);
        int indexInt = Arrays.asList(brailleInt).indexOf(english);
        int indexAZ = Arrays.asList(brailleAZ).indexOf(english);

        // Decide the character(s) to return as a string
        switch (mode) {
            case LOWERCASE:
                if (indexSpecial >= 0) {
                    english = englishSpecial[indexSpecial];
                } else if (this.cha == '\u2826' || this.cha == '\u2834') {
                    english = "\"";
                } else if (this.cha == '\u2800') {
                    english = " ";
                } else if (indexAZ >= 0) {
                    english = englishAZ[indexAZ];
                }
                break;
            case UPPERCASE_SHIFT:
            case UPPERCASE_CAPS:
                if (indexAZ >= 0) {
                    english = String.valueOf(Character.toUpperCase(englishAZ[indexAZ].toCharArray()[0]));
                }
                break;
            case NUMBER:
                if (indexInt >= 0) {
                    english = englishInt[indexInt];
                }
                break;
        }

        return english;
    }
}
