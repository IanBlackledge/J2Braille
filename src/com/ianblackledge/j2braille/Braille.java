package com.ianblackledge.j2braille;

class Braille {
    private static char[] brailleAZ = {'\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a', '\u281a', '\u2805', '\u2807', '\u280d', '\u281d', '\u2815', '\u280f', '\u281f', '\u2817', '\u280e', '\u281e', '\u2825', '\u2827', '\u283a', '\u282d', '\u283d', '\u2835'};
    private static char[] brailleNum = {'\u281a', '\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a'};
    private char cha;

    Braille(char cha) {
        this.cha = cha;
    }

    String getBraille(BrailleMode mode) {
        char cha = this.cha;
        String str = "";

        switch (mode) {
            case NORMAL:
                cha = brailleAZ[this.cha - 'a'];
                break;
            case SHIFT:
            case CAPS:
                cha = brailleAZ[this.cha - 'A'];
                break;
            case NUMBER:
                cha = brailleNum[this.cha - '0'];
                break;
            case SPECIAL:
                switch (this.cha) {
                    case ' ':
                        cha = '\u2800';
                        break;
                    case '.':
                        cha = '\u2832';
                        break;
                    case '!':
                        cha = '\u2816';
                        break;
                    case '?':
                        cha = '\u2826';
                        break;
                    case ',':
                        cha = '\u2802';
                        break;
                    case '\'':
                        cha = '\u2804';
                        break;
                    case '@':
                        str = "\u2808\u2801";
                        break;
                }
                break;
            case SPECIAL_START:
                switch (this.cha) {
                    case '"':
                        cha = '\u2826';
                        break;
                    case '(':
                        str = "\u2810\u2823";
                        break;
                    case '{':
                        str = "\u2838\u2823";
                        break;
                    case '[':
                        str = "\u2828\u2823";
                        break;
                    case '<':
                        str = "\u2808\u2823";
                        break;
                }
                break;
            case SPECIAL_END:
                switch (this.cha) {
                    case '"':
                        cha = '\u2834';
                        break;
                    case ')':
                        str = "\u2810\u281c";
                        break;
                    case '}':
                        str = "\u2838\u281c";
                        break;
                    case ']':
                        str = "\u2828\u281c";
                        break;
                    case '>':
                        str = "\u2808\u281c";
                        break;
                }
                break;
        }
        return str.isEmpty() ? String.valueOf(cha) : str;
    }
}
