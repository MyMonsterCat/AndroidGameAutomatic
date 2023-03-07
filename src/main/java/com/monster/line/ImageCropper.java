package com.monster.line;

public class ImageCropper {
    public static void main(String[] args) throws Exception {
        //延时任务：https://blog.csdn.net/qq_18948359/article/details/125499389

        // 1.调用截图,返回的是BufferedImage
        // 2.进行区域OCR（裁剪-->保存到本地文件-->OCR-->删除本地文件）
        //    2.1 判定是否是我需要的界面
        //          如果不是，点击返回按键，回到第1步
        //          如果是，继续下一步
        // 3.选择队伍，判定状态
        // 4.第一次没有执行脚本流程
        // 5.创建延迟任务


        // 执行脚本如何写？
        // 方式一：abstractTaskHandler.xx(参数)，xx(参数)方法里面就是：adb命令执行顺序 【缺点】执行过程写死了，只能改代码
        // 方式二：解析json，将其封装为自定义的对象，abstractTaskHandler.xx(自定义对象)  【缺点】需要学习自定义的语法


    }
}
