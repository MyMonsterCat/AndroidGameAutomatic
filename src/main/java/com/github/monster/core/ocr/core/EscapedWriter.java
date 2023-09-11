package com.github.monster.core.ocr.core;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EscapedWriter extends FilterWriter {
    /**
     * 系统分隔符
     */
    public final String lineSeparator = System.getProperty("line.separator");
    private final int cr = lineSeparator.charAt(0);
    private final int lf = (lineSeparator.length() == 2) ? lineSeparator.charAt(1) : -1;

    /**
     * 围绕给定的Writer构造一个EscapedWriter。
     */
    public EscapedWriter(Writer fos) {
        super(fos);
    }

    private final StringBuffer mini = new StringBuffer();

    /**
     * 打印单个字符
     */
    public void print(int ch) throws IOException {
        write(ch);
        throw new RuntimeException();
    }

    /**
     * 输出给定字符
     */
    public void write(String s, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(s.charAt(i));
        }
    }

    /**
     * 单个字符
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
