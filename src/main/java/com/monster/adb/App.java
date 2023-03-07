//package com.monster.adb;
//
//import com.android.ddmlib.IDevice;
//import lombok.SneakyThrows;
//
//public class App {
//
//    @SneakyThrows
//    public static void main(String[] args) {
//        System.out.println("Hello Springboot!");
//        AdbHandler adbHandler = new AdbHandler();
//
//
//        for (IDevice iDevice : adbHandler.getDevices()) {
//            System.out.println("获取当前设备 " + iDevice);
//
//            // 点击搜索
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1418,98),"点击搜索按钮");
//
//            // 点击左边输入 1187 854
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1187,854),"点击左侧输入坐标");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第1次删除");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第2次删除");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第3次删除");
//
//            // 去冀州州府 516 1431
//            adbHandler.executeCommand(iDevice,AdbCommand.getText("516"),"输入冀州州府x坐标");
//
//            // 需要点击两次，第一次取消输入框
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1362,857),"取消输入框");
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1362,857),"点击右侧输入坐标");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第1次删除");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第2次删除");
//
//            adbHandler.executeCommand(iDevice,AdbCommand.getDelete(),"第3次删除");
//
//            // 去冀州州府 516 1431
//            adbHandler.executeCommand(iDevice,AdbCommand.getText("1431"),"输入冀州州府y坐标");
//
//            // 跳转
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1481,852),"取消输入框");
//            adbHandler.executeCommand(iDevice,AdbCommand.getTap(1481,852),"点击跳转按钮");
//
//
////            adbController.swipe(iDevice, AdbCommand.getSwipe(44, 52, 721, 500));
////            adbController.getScreenShot(iDevice,"C:\\Users\\Monster\\Desktop\\222.png");
//            System.out.println("执行完毕，退出程序");
//
//        }
//        adbHandler.exit();
//        // TODO java顺序队列
//        // 获取当前的分辨率，如果分辨率不对，不予执行
//        // 获取当前的设备，检查设备是否含有st这个应用，不包含不予执行
//
//        // 截取当前图片，放到指定位置
//        // 图像识别
//
//    }
//
//}
