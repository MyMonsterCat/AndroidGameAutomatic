package com.monster.api;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.monster.schedule.AttackTask;
import com.monster.schedule.DynamicTaskPool;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.util.Date;

@Component
public class MainWin extends JFrame {

    @Resource
    private DynamicTaskPool dynamicTaskPool;


    static {
        FlatDarculaLaf.setup();
    }

    JFrame frame;

    JPanel jPanel;

    private JTextField textFieldX;

    private JTextField textFieldY;
    private JButton button;

    public MainWin() {
        frame = new JFrame("这是一个JFrame窗体");
        jPanel = new JPanel();


        // 设置输入框
        textFieldX = new JTextField(10);
        textFieldX.setText("请输入目标地点X坐标");
        textFieldX.setForeground(Color.GRAY);
        textFieldX.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldX.getText().equals("请输入目标地点X坐标")) {
                    textFieldX.setText("");
                    textFieldX.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldX.getText().isEmpty()) {
                    textFieldX.setForeground(Color.GRAY);
                    textFieldX.setText("请输入目标地点X坐标");
                }
            }
        });

        textFieldY = new JTextField(10);
        textFieldY.setText("请输入目标地点Y坐标");
        textFieldY.setForeground(Color.GRAY);
        textFieldY.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldY.getText().equals("请输入目标地点Y坐标")) {
                    textFieldY.setText("");
                    textFieldY.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldY.getText().isEmpty()) {
                    textFieldY.setForeground(Color.GRAY);
                    textFieldY.setText("请输入目标地点Y坐标");
                }
            }
        });

        // 设置按钮
        button = new JButton("开始");
        button.addActionListener(e -> {
            String textX = textFieldX.getText();
            String textY = textFieldY.getText();
            System.out.println(MessageFormat.format("输入的坐标是：{0},{1}", textX, textY));

            AttackTask attackTask = new AttackTask(Integer.parseInt(textX), Integer.parseInt(textY));
            attackTask.setStartTime(new Date());

            dynamicTaskPool.add(attackTask);
        });

        jPanel.add(textFieldX);
        jPanel.add(textFieldY);
        jPanel.add(button);


        frame.add(jPanel, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Swing关闭");
            }

            ;
        });


        frame.setSize(700, 500);
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = frame.getSize().width;
        int frameHeight = frame.getSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
