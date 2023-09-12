package com.github.monster.core.util;

import com.idrsolutions.image.png.PngCompressor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Monster
 * @date 2023/3/14 14:27
 */
public class ImageUtil {

    /**
     * 图像裁剪
     *
     * @param imgPath 文件地址（包含名称）
     * @param x       开始坐标X
     * @param y       开始坐标Y
     * @param width   长度
     * @param height  高度
     * @return
     */
    @SneakyThrows
    public static String imageCropper(String imgPath, String rePath, Integer x, Integer y, Integer width, Integer height) {

        // 读取原始图像
        BufferedImage originalImage = ImageIO.read(new File(imgPath));

        // 裁剪图像
        BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);
        File output = null;
        // 保存裁剪后的图像
        if (rePath == null) {
            output = new File(imgPath);
        } else {
            output = new File(rePath);
        }
        ImageIO.write(croppedImage, "png", output);
        return imgPath;

    }

    /**
     * 图片压缩
     *
     * @param oldPath    原图片地址
     * @param newImgPath 压缩后的地址
     */
    @SneakyThrows
    public static void imageCompress(String oldPath, String newImgPath) {
        PngCompressor.compress(new File(oldPath), new File(newImgPath));
    }
}
