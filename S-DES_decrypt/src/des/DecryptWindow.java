package des;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecryptWindow extends JFrame {

    private JTextField keyInput;               // 密钥输入框
    private JTextField cipherTextInput;        // 二进制密文输入框
    private JTextArea plainTextAscii;          // ASCII明文显示框
    private JTextArea plainTextBinary;         // 二进制明文显示框
    private JButton decryptButton;              // 解密按钮
    private JButton clearButton;                // 清空按钮

    public DecryptWindow() {
        setTitle("二进制解密器");
        setSize(600, 400);  // 增加窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());  // 使用GridBagLayout进行更灵活的布局
        GridBagConstraints gbc = new GridBagConstraints();

        // 初始化组件
        keyInput = new JTextField(20);  // 密钥输入框
        cipherTextInput = new JTextField(20);  // 密文输入框
        plainTextAscii = new JTextArea(5, 20);  // ASCII明文显示框
        plainTextBinary = new JTextArea(5, 20); // 二进制明文显示框
        plainTextAscii.setLineWrap(true);  // 自动换行
        plainTextBinary.setLineWrap(true);  // 自动换行
        JScrollPane asciiScrollPane = new JScrollPane(plainTextAscii);  // ASCII明文显示框滚动面板
        JScrollPane binaryScrollPane = new JScrollPane(plainTextBinary);  // 二进制明文显示框滚动面板

        decryptButton = new JButton("解密");
        clearButton = new JButton("清空");

        // 设置组件位置
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 密钥输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("请输入 10 位密钥:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(keyInput, gbc);

        gbc.gridy = 1; // 继续下移
        add(Box.createRigidArea(new Dimension(0, 10)), gbc); // 添加间隔

        // 二进制密文输入
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("请输入二进制密文:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        // 设置密文输入框与明文框一样大
        cipherTextInput.setPreferredSize(new Dimension(plainTextAscii.getPreferredSize().width, plainTextAscii.getPreferredSize().height));
        add(cipherTextInput, gbc);

        gbc.gridy = 3; // 继续下移
        add(Box.createRigidArea(new Dimension(0, 10)), gbc); // 添加间隔

        // ASCII明文显示
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("明文 (ASCII):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(asciiScrollPane, gbc);

        gbc.gridy = 5; // 继续下移
        add(Box.createRigidArea(new Dimension(0, 10)), gbc); // 添加间隔

        // 二进制明文显示
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("明文 (二进制):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(binaryScrollPane, gbc);

        gbc.gridy = 7; // 继续下移
        add(Box.createRigidArea(new Dimension(0, 10)), gbc); // 添加间隔

        // 按钮
        gbc.gridx = 0;
        gbc.gridy = 8;
        decryptButton.setPreferredSize(new Dimension(100, 30)); // 设置解密按钮大小
        add(decryptButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        clearButton.setPreferredSize(new Dimension(100, 30)); // 设置清空按钮大小
        add(clearButton, gbc);

        // 设置按钮之间的间距
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(Box.createRigidArea(new Dimension(20, 0)), gbc); // 增加水平间距

        // 按钮事件处理
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptText();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    // 解密函数
    private void decryptText() {
        String key = keyInput.getText();
        String cipherText = cipherTextInput.getText();

        // 输入校验
        if (key.length() != 10 || !key.matches("[01]+")) {
            JOptionPane.showMessageDialog(this, "密钥必须是 10 位二进制数字。", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!cipherText.matches("[01]+")) {
            JOptionPane.showMessageDialog(this, "密文必须是二进制。", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 调用解密服务
        DecryptService d1 = new DecryptService(cipherText, key);
        String plainAscii = d1.decrypt("1");  // 得到ascii形式明文结果
        String plainBinary = d1.decrypt("2");  // 得到二进制形式明文结果

        // 显示结果
        plainTextAscii.setText(plainAscii);
        plainTextBinary.setText(plainBinary);
    }

    // 清空输入框和输出框
    private void clearFields() {
        keyInput.setText("");
        cipherTextInput.setText("");
        plainTextAscii.setText("");
        plainTextBinary.setText("");
    }


}
