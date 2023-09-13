package com.github.monster.core.ocr.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OcrResponse {
    private int code;
    private OcrEntry[] data;
    private String msg;
    private String hotUpdate;

    public OcrResponse() {
    }

    public OcrResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
