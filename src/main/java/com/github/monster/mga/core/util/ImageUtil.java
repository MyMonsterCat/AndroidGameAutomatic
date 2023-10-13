package com.github.monster.mga.core.util;

import com.idrsolutions.image.png.PngCompressor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

/**
 * @author Monster
 */
public class ImageUtil {

    /**
     * 图像裁剪
     *
     * @param imgPath 文件地址（包含名称）
     * @param startX  左上角坐标X
     * @param startY  左上角坐标Y
     * @param endX    右下角坐标X
     * @param endY    右下角坐标Y
     */
    @SneakyThrows
    public static String imageCropper(String imgPath, String rePath, Integer startX, Integer startY, Integer endX, Integer endY) {
        // 读取原始图像
        BufferedImage originalImage = ImageIO.read(new File(imgPath));
        // 裁剪图像
        BufferedImage croppedImage = originalImage.getSubimage(startX, startY, endX - startX, endY - startY);
        File output;
        // 保存裁剪后的图像
        output = new File(Objects.requireNonNullElse(rePath, imgPath));
        ImageIO.write(croppedImage, "png", output);
        // JVM退出时删除
        output.deleteOnExit();
        return rePath;
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
