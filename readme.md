## 脚手架

可以定时控制模拟点击安卓设备

## 整体框架

- GUI界面：[flatlaf](https://github.com/JFormDesigner/FlatLaf)

- 文字+图像识别：[PaddleOCR-json](https://github.com/hiroi-sora/PaddleOCR-json)

- 后端框架：SringBoot2.7.5

- 安卓设备控制:[DeviceTouch](https://github.com/MyMonsterCat/DeviceTouch)

## 快速使用

- 编写任务:需要继承CustomizeTask类，其中date是该任务执行的时间

```java

@Slf4j
public class AttackTask extends CustomizeTask {

    public AttackTask(int x, int y, Date date) {
        this.x = x;
        this.y = y;
        this.startTime = date;
        this.name = "AttackCity-Task";
    }

    private int x;
    private int y;


    @Override
    public void taskRun() {
        SthServiceImpl sthService = SpringContextUtil.getBean(ISthService.class);
        sthService.attackCity(x, y);
        log.info("任务{}执行了", this.getName());
    }
}
```

```java

@Component
@Slf4j
public class SthServiceImpl implements ISthService {

    // DeviceCli的使用请参考DeviceTouch
    @Resource
    private DeviceCli deviceCli;

    public void attackCity(int x, int y) {
        deviceCli.touchDown(x, y);
        deviceCli.touchUp(x, y);
        log.info("开始执行点击事件" + x + "," + y);

    }
}
```

- 为该任务绑定事件（也可以是接口等）

```java
// 设置按钮
button = new JButton("开始");
button.addActionListener(e -> {
    String textX = textFieldX.getText();
    String textY = textFieldY.getText();
    System.out.println(MessageFormat.format("输入的坐标是：{0},{1}", textX, textY));

  	// 设置执行时间
    Date startTime = DateUtil.offsetMillisecond(new Date(), 10).toCalendar().getTime();
    AttackTask attackTask = new AttackTask(Integer.parseInt(textX), Integer.parseInt(textY), startTime);

    dynamicTaskPool.add(attackTask);
});

jPanel.add(textFieldX);
jPanel.add(textFieldY);
jPanel.add(button);
```
