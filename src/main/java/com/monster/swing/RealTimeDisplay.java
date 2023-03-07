package com.monster.swing;

import javax.swing.*;
import java.awt.*;

public class RealTimeDisplay extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Thread thread;

    public RealTimeDisplay() {
        setTitle("Real Time Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    // 从数据源获取数据
                    String newData = getDataFromSource();
                    // 显示到文本区域
                    appendToTextArea(newData);
                    // 等待一段时间再获取下一批数据
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private String getDataFromSource() {
        // 从数据源获取数据，这里只是一个示例
        return String.valueOf(System.currentTimeMillis());
    }

    private void appendToTextArea(String newData) {
        // 在文本区域末尾追加新数据
        textArea.append(newData + "\n");
        // 滚动到最后一行
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        RealTimeDisplay frame = new RealTimeDisplay();
        frame.setVisible(true);
    }
}
