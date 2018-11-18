package com.ianblackledge.j2braille;

class Braille {
    private static char[] brailleAZ = {'\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a', '\u281a', '\u2805', '\u2807', '\u280d', '\u281d', '\u2815', '\u280f', '\u281f', '\u2817', '\u280e', '\u281e', '\u2825', '\u2827', '\u283a', '\u282d', '\u283d', '\u2835'};
    private static char[] brailleNum = {'\u281a', '\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a'};
    private char cha;

    Braille(char cha) {
        this.cha = cha;
    }

    String getBraille(BrailleMode mode) {
        String braille = "";

        // Decide the character(s) to return
        switch (mode) {
            case LOWERCASE:
                braille = String.valueOf(brailleAZ[this.cha - 'a']);
                break;
            case UPPERCASE_SHIFT:
            case UPPERCASE_CAPS:
                braille = String.valueOf(brailleAZ[this.cha - 'A']);
                break;
            case NUMBER:
                braille = String.valueOf(brailleNum[this.cha - '0']);
                break;
            case SPACE:
                braille = "\u2800";
                break;
            case SPECIAL:
                switch (this.cha) {
                    case '.':
                        braille = "\u2832";
                        break;
                    case '!':
                        braille = "\u2816";
                        break;
                    case '?':
                        braille = "\u2826";
                        break;
                    case ',':
                        braille = "\u2802";
                        break;
                    case '\'':
                        braille = "\u2804";
                        break;
                    case '@':
                        braille = "\u2808\u2801";
                        break;
                    case '(':
                        braille = "\u2810\u2823";
                        break;
                    case '{':
                        braille = "\u2838\u2823";
                        break;
                    case '[':
                        braille = "\u2828\u2823";
                        break;
                    case '<':
                        braille = "\u2808\u2823";
                        break;
                    case ')':
                        braille = "\u2810\u281c";
                        break;
                    case '}':
                        braille = "\u2838\u281c";
                        break;
                    case ']':
                        braille = "\u2828\u281c";
                        break;
                    case '>':
                        braille = "\u2808\u281c";
                        break;
                }
                break;
            case QUOTE_START:
                if (this.cha == '"') braille = "\u2826";
                break;
            case QUOTE_END:
                if (this.cha == '"') braille = "\u2834";
                break;
        }

        return braille;
    }
}
