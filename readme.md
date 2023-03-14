## 功能安排

- [ ] 定时打城
- [ ] 队伍压秒
- [ ] 自动刷级
- [ ] 战报统计
- [ ] 自动铺路
- [ ] 辅助开荒

## 整体框架

### GUI界面：采用Swing第三方flatlaf

> 参考链接：https://github.com/JFormDesigner/FlatLaf

### 文字识别

方式一：[paddleocr](https://github.com/jiangnanboy/java-springboot-paddleocr)

方式二：[PaddleOCR-json](https://github.com/hiroi-sora/PaddleOCR-json)

> 参考链接：
>
> - C++编译：https://www.cnblogs.com/little-horse/p/16926646.html
> - 本地化部署：https://blog.csdn.net/f2315895270/article/details/128150679

### 图像识别：OpenCV

> 参考链接：
>
> - java+openCV：https://cloud.tencent.com/developer/article/2161336
> - java 使用 OpenCV: https://blog.csdn.net/acnwcl/article/details/123754825

### 后端框架：SringBoot2.7.5

> 参考链接：
>
> SringBoot + Swing整合：https://www.cnblogs.com/yjc-vue-react-java/p/15684900.html
>
> easyTodo： https://gitee.com/wmazh/easytodo
>
> 使用springboot搭建swing桌面程序: https://blog.csdn.net/weixin_39169535/article/details/125844737

### 调用模拟器：adb

> 参考链接：
>
> adb使用-详细教程：https://blog.csdn.net/u010610691/article/details/77663770
> STF 框架之 minitouch 工具：https://testerhome.com/topics/4400
> STF 框架之 minicap 工具：https://testerhome.com/topics/3115

## 大致流程（以执行一次定时攻城任务为例）

1. 选择队伍，输入坐标和进攻时间
2. 前端界面将数据传给后台，后台此时
   1. 调用模拟器进行截图
   2. 将图片进行文字识别，提取出征时间
   3. 后台获取到出征时间，自动生成出征脚本
   4. 设立定时任务来执行脚本
3. 在设定的时间执行脚本
4. 在adb中执行脚本，完成任务
