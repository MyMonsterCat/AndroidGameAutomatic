package com.monster.ocr;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EscapedWriter extends FilterWriter {
    /**
     * Convenience field containing the system's line separator.
     */
    public final String lineSeparator = System.getProperty("line.separator");
    private final int cr = lineSeparator.charAt(0);
    private final int lf = (lineSeparator.length() == 2) ? lineSeparator.charAt(1) : -1;

    /**
     * Constructs an EscapedWriter around the given Writer.
     */
    public EscapedWriter(Writer fos) {
        super(fos);
    }

    private final StringBuffer mini = new StringBuffer();

    /**
     * Print a single character (unsupported).
     */
    public void print(int ch) throws IOException {
        write(ch);
        throw new RuntimeException();
    }

    /**
     * Write a segment of the given String.
     */
    public void write(String s, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(s.charAt(i));
        }
    }

    /**
     * Write a single character.
     */
    public void write(int ch) throws IOException {
        if (ch >= 32 && ch <= 126 || ch == cr || ch == lf || ch == ' ') {
            super.write(ch);
            return;
        }

        mini.setLength(0);
        mini.append(Integer.toHexString(ch));

        while (mini.length() < 4) {
            mini.insert(0, "0");
        }

        mini.insert(0, "\\u");
        for (int i = 0; i < mini.length(); i++) {
            super.write(mini.charAt(i));
        }
    }
}