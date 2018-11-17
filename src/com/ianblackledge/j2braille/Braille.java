package com.ianblackledge.j2braille;

class Braille {
    private static char[] brailleAZ = {'\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a', '\u281a', '\u2805', '\u2807', '\u280d', '\u281d', '\u2815', '\u280f', '\u281f', '\u2817', '\u280e', '\u281e', '\u2825', '\u2827', '\u283a', '\u282d', '\u283d', '\u2835'};
    private static char[] brailleNum = {'\u281a', '\u2801', '\u2803', '\u2809', '\u2819', '\u2811', '\u280b', '\u281b', '\u2813', '\u280a'};
    private char cha;

    Braille(char cha) {
        this.cha = cha;
    }

    char getBraille(BrailleMode mode) {
        if (mode == BrailleMode.NORMAL) {
            return brailleAZ[this.cha - 'a'];
        } else if (mode == BrailleMode.CAPS || mode == BrailleMode.SHIFT) {
            return brailleAZ[this.cha - 'A'];
        } else if (mode == BrailleMode.NUMBER) {
            return brailleNum[this.cha - '0'];
        } else if (mode == BrailleMode.SPECIAL) {
            if (this.cha == ' ') {
                return '\u2800';
            } else if (this.cha == '.') {
                return '\u2832';
            } else if (this.cha == '!') {
                return '\u2816';
            } else if (this.cha == '?') {
                return '\u2826';
            } else if (this.cha == ',') {
                return '\u2802';
            }
        }
        return this.cha;
    }
}
