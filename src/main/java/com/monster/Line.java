//package com.monster;
//
//import com.android.ddmlib.IDevice;
//import com.monster.constant.CoordinateEnum;
//import com.monster.ocr.OcrEntry;
//import com.monster.ocr.OcrUtil;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class Line {
//
//    //    @Resource
//    private AdbHandler adbHandler;
//
//    public static void main(String[] args) {
//        AdbHandler adbHandler = new AdbHandler();
//        attackCity(adbHandler, 1274, 1133);
//        adbHandler.exit();
//    }
//
//    /**
//     * 攻城
//     *
//     * @param adbHandler
//     * @param x
//     * @param y
//     */
//    @SneakyThrows
//    public static void attackCity(AdbHandler adbHandler, int x, int y) {
//
//        for (IDevice iDevice : adbHandler.getDevices()) {
//            System.out.println("获取当前设备 " + iDevice);
//
//            // 跳转到建业
////            JumpCity(adbHandler, iDevice, x, y);
//
//
//            adbHandler.executeCommand(iDevice, "input touchscreen multitouch 0 2 1000 540 960 540 720", "点击中心点");
//
////            Thread.sleep(1000);
//
//
//            // 点击城池
////            long nowTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant().getEpochSecond();
////            shotCropperOcrClick(adbHandler, iDevice, nowTime + ".png", "建业", null, 770, 370, 300, 200, CoordinateEnum.BottomRight);
//
////            adbHandler.executeCommand(iDevice, AdbCommand.getTap(ResolvingPowerConstant.Center_1600_900[0], ResolvingPowerConstant.Center_1600_900[1]), "点击中心点");
////            // 出征
////            String expeditionName = System.currentTimeMillis() + ".png";
////            shotCropperOcrClick(adbHandler, iDevice, expeditionName,"出征", Arrays.asList("出证"),1092, 420, 200, 200,CoordinateEnum.Center);
////
////            // 派出部队
////            String troopsName = System.currentTimeMillis() + ".png";
////            shotCropperOcrClick(adbHandler, iDevice, expeditionName,"出征", Arrays.asList("出证"),1092, 420, 200, 200,CoordinateEnum.Center);
////
//
//
////            adbHandler.swipe(iDevice, AdbCommand.getSwipe(44, 52, 721, 500));
//
//            System.out.println("执行完毕，退出程序");
//
//        }
//
//    }
//
//
//    /**
//     * 跳转到制定地点
//     *
//     * @param iDevice
//     * @throws InterruptedException
//     */
//    private static void JumpCity(AdbHandler adbHandler, IDevice iDevice, Integer x, Integer y) throws InterruptedException {
//        Thread.sleep(1000);
//        // 点击搜索
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1418, 98), "点击搜索按钮");
//
//
//        // 点击左边输入 1187 854
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1187, 854), "点击左侧输入坐标");
//
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第1次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第2次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第3次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第4次删除");
//
//        // 去冀州州府 516 1431
//        adbHandler.executeCommand(iDevice, AdbCommand.getText(x.toString()), "输入冀州州府x坐标");
//
//        // 需要点击两次，第一次取消输入框
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1362, 857), "取消输入框");
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1362, 857), "点击右侧输入坐标");
//
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第1次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第2次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第3次删除");
//        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第4次删除");
//
//        // 去冀州州府 516 1431
//        adbHandler.executeCommand(iDevice, AdbCommand.getText(y.toString()), "输入冀州州府y坐标");
//
//        // 跳转
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1481, 852), "取消输入框");
//        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1481, 852), "点击跳转按钮");
//    }
//}
