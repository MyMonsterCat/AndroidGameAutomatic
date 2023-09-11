package com.github.monster.core.ocr.core;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

@Data
public class OcrResponse {
    private int code;
    private OcrEntry[] data;
    private String msg;
    private String hotUpdate;

    @Override
    public String toString() {
        return "OcrResponse{" +
                "code=" + code +
                ", data=" + Arrays.toString(data) +
                ", msg='" + msg + '\'' +
                ", hotUpdate='" + hotUpdate + '\'' +
                '}';
    }

    public OcrResponse() {
    }

    public OcrResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
