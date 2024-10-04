import java.util.Random;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Main {
    private static void createAndShowGUI() {
        // 创建主框架
        JFrame frame = new JFrame("加密应用程序");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标签和输入框
        JLabel inputLabel1 = new JLabel("请输入二进制明文：");
        JTextField inputField1 = new JTextField(20);
        JButton encryptButton1 = new JButton("加密");

        JLabel inputLabel = new JLabel("请输入字母明文：");
        JTextField inputField = new JTextField(20);
        JButton encryptButton = new JButton("加密");

        JLabel keyLabel = new JLabel("生成的密钥：");
        JTextField keyField = new JTextField(20);
        keyField.setEditable(false); // 设置为不可编辑

        JLabel outputLabel = new JLabel("生成的密文：");
        JTextArea outputArea = new JTextArea(5, 20); // 5 行, 20 列
        outputArea.setEditable(false); // 输出框设置为不可编辑
        outputArea.setLineWrap(true); // 自动换行
        outputArea.setWrapStyleWord(true); // 按单词换行
        JScrollPane scrollPane = new JScrollPane(outputArea);
        JButton res_ascll = new JButton("生成ascll乱码");

        // 设置网格约束
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距

        // 添加组件到框架
        gbc.gridx = 0; gbc.gridy = 0; frame.add(inputLabel1, gbc);
        gbc.gridx = 1; gbc.gridy = 0; frame.add(inputField1, gbc);
        gbc.gridx = 2; gbc.gridy = 0; frame.add(encryptButton1, gbc);

        gbc.gridx = 0; gbc.gridy = 1; frame.add(inputLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; frame.add(inputField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; frame.add(encryptButton, gbc);

        gbc.gridx = 2; gbc.gridy = 2; frame.add(res_ascll, gbc);

        gbc.gridx = 0; gbc.gridy = 3; frame.add(keyLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; frame.add(keyField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; frame.add(outputLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; frame.add(scrollPane, gbc);


        // 按钮用于加密操作
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入的明文
                String str_plaintext = inputField.getText();

                // 进行加密操作（这里可以替换为您的加密逻辑）
                StringBuilder ciphertext = new StringBuilder();
                StringBuilder key = new StringBuilder(); // 示例密钥

                StringBuilder[] result = encrypt(str_plaintext, ciphertext);
                ciphertext = result[0]; // 假设您有一个加密方法
                key = result[1];

                // 将密文显示在输出框
                outputArea.setText(ciphertext.toString());
                // 将密钥显示在密钥输出框
                keyField.setText(key.toString());
            }
        });

        encryptButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入的明文
                String str_plaintext = inputField1.getText();

                // 进行加密操作（这里可以替换为您的加密逻辑）
                StringBuilder ciphertext = new StringBuilder();
                StringBuilder key = new StringBuilder(); // 示例密钥

                StringBuilder[] result = encrypt1(str_plaintext, ciphertext);
                ciphertext = result[0]; // 假设您有一个加密方法
                key = result[1];

                // 将密文显示在输出框
                outputArea.setText(ciphertext.toString());
                // 将密钥显示在密钥输出框
                keyField.setText(key.toString());
            }
        });

        // 按钮用于加密操作
        res_ascll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入的明文
                String str_plaintext = inputField.getText();

                // 进行加密操作（这里可以替换为您的加密逻辑）
                StringBuilder ciphertext = new StringBuilder();
                StringBuilder key = new StringBuilder(); // 示例密钥

                StringBuilder[] result = encrypt_ascll(str_plaintext, ciphertext);
                ciphertext = result[0]; // 假设您有一个加密方法
                key = result[1];

                // 将密文显示在输出框
                outputArea.setText(ciphertext.toString());
                // 将密钥显示在密钥输出框
                keyField.setText(key.toString());
            }
        });

        // 显示框架
        frame.setVisible(true);
    }


    private static StringBuilder[] encrypt(String str_plaintext, StringBuilder ciphertext){
        Random random = new Random();  // 创建Random对象

        //生成密钥
        int[] key = new int[10];
        for(int i = 0; i < 10; i++){
            key[i] = random.nextInt(2);
        }

        //生成k1、k2
        int[] k1 = encode_funtion.fun_P10(key);
        k1 = encode_funtion.Leftshift1(k1);
        int[] copy = Arrays.copyOf(k1, k1.length);

        int[] k2 = encode_funtion.Leftshift1(copy);
        k1 = encode_funtion.fun_P8(k1);
        k2 = encode_funtion.fun_P8(k2);

        int ascll_plaintext = 0;
        int[] int_plaintext = new int[8];

        for(int i = 0; i < str_plaintext.length(); i++) {
            char ch = str_plaintext.charAt(i);
            ascll_plaintext = (int) ch;
            for(int j = 7; j >= 0; j--) {
                int_plaintext[j] = ascll_plaintext % 2;
                ascll_plaintext /= 2;
            }
            //加密
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            StringBuilder tmp = new StringBuilder();
            for(int j = 0; j < 8; j++){
                tmp.append(Integer.toString(int_plaintext[j]));
            }
            ciphertext.append(tmp);
        }


        StringBuilder trans_cipher = new StringBuilder();
        for(int i = 0; i < ciphertext.length(); i += 8){
            String every_group = ciphertext.substring(i,i+8);
            for(int j = 0; j < 8; j++){
                int_plaintext[j] = Character.getNumericValue(every_group.charAt(j));
            }
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            int tmp_ascll = 0;
            for(int j = 0; j < 8; j++){
                tmp_ascll = tmp_ascll*2 + int_plaintext[j];
            }
            trans_cipher.append((char)tmp_ascll);

        }


        System.out.println("明文为："+str_plaintext);

        System.out.print("密钥为：");
        for (int i = 0; i < key.length; i++){
            System.out.print(key[i]);
        }
        System.out.println();

        System.out.print("k1为：");
        for (int i = 0; i < k1.length; i++){
            System.out.print(k1[i]);
        }
        System.out.println();

        System.out.print("k2为：");
        for (int i = 0; i < k2.length; i++){
            System.out.print(k2[i]);
        }
        System.out.println();

        System.out.println("密文为："+ ciphertext);

        System.out.println("解密后为："+trans_cipher);
        System.out.println();

        StringBuilder str_key = new StringBuilder();
        for(int i = 0; i < 10; i++){
            str_key.append(key[i]);
        }

        StringBuilder[] ans = {ciphertext, str_key};
        return ans;

    }

    private static StringBuilder[] encrypt1(String str_plaintext, StringBuilder ciphertext){
        Random random = new Random();  // 创建Random对象

        //生成密钥
        int[] key = new int[10];
        for(int i = 0; i < 10; i++){
            key[i] = random.nextInt(2);
        }

        //生成k1、k2
        int[] k1 = encode_funtion.fun_P10(key);
        k1 = encode_funtion.Leftshift1(k1);
        int[] copy = Arrays.copyOf(k1, k1.length);

        int[] k2 = encode_funtion.Leftshift1(copy);
        k1 = encode_funtion.fun_P8(k1);
        k2 = encode_funtion.fun_P8(k2);

        int[] int_plaintext = new int[8];

        for(int i = 0; i < str_plaintext.length(); i += 8) {
            String every_group = str_plaintext.substring(i,i+8);
            for(int j = 0; j < 8; j++) {
                int_plaintext[j] = Character.getNumericValue(every_group.charAt(j));
            }

            //加密
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            StringBuilder tmp = new StringBuilder();
            for(int j = 0; j < 8; j++){
                tmp.append(Integer.toString(int_plaintext[j]));
            }
            ciphertext.append(tmp);
        }


        StringBuilder trans_cipher = new StringBuilder();
        for(int i = 0; i < ciphertext.length(); i += 8){
            String every_group = ciphertext.substring(i,i+8);
            for(int j = 0; j < 8; j++){
                int_plaintext[j] = Character.getNumericValue(every_group.charAt(j));
            }
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            int tmp_ascll = 0;
            for(int j = 0; j < 8; j++){
                tmp_ascll = tmp_ascll*2 + int_plaintext[j];
            }
            trans_cipher.append((char)tmp_ascll);

        }


        System.out.println("明文为："+str_plaintext);

        System.out.print("密钥为：");
        for (int i = 0; i < key.length; i++){
            System.out.print(key[i]);
        }
        System.out.println();

        System.out.print("k1为：");
        for (int i = 0; i < k1.length; i++){
            System.out.print(k1[i]);
        }
        System.out.println();

        System.out.print("k2为：");
        for (int i = 0; i < k2.length; i++){
            System.out.print(k2[i]);
        }
        System.out.println();

        System.out.println("密文为："+ ciphertext);

        System.out.println("解密后为："+trans_cipher);
        System.out.println();

        StringBuilder str_key = new StringBuilder();
        for(int i = 0; i < 10; i++){
            str_key.append(key[i]);
        }

        StringBuilder[] ans = {ciphertext, str_key};
        return ans;

    }

    private static StringBuilder[] encrypt_ascll(String str_plaintext, StringBuilder ciphertext){
        Random random = new Random();  // 创建Random对象

        //生成密钥
        int[] key = new int[10];
        for(int i = 0; i < 10; i++){
            key[i] = random.nextInt(2);
        }

        //生成k1、k2
        int[] k1 = encode_funtion.fun_P10(key);
        k1 = encode_funtion.Leftshift1(k1);
        int[] copy = Arrays.copyOf(k1, k1.length);

        int[] k2 = encode_funtion.Leftshift1(copy);
        k1 = encode_funtion.fun_P8(k1);
        k2 = encode_funtion.fun_P8(k2);

        int ascll_plaintext = 0;
        int[] int_plaintext = new int[8];
        StringBuilder ciphertext_ascll = new StringBuilder();

        for(int i = 0; i < str_plaintext.length(); i++) {
            char ch = str_plaintext.charAt(i);
            ascll_plaintext = (int) ch;
            for(int j = 7; j >= 0; j--) {
                int_plaintext[j] = ascll_plaintext % 2;
                ascll_plaintext /= 2;
            }

            //加密
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            StringBuilder tmp = new StringBuilder();
            for(int j = 0; j < 8; j++){
                tmp.append(Integer.toString(int_plaintext[j]));
            }
            ciphertext.append(tmp);

            int int_tmp = 0;
            for(int j = 7; j >= 0; j--){
                int_tmp = int_tmp*2 + int_plaintext[j];
            }
            ciphertext_ascll.append((char)int_tmp);
        }


        StringBuilder trans_cipher = new StringBuilder();
        for(int i = 0; i < ciphertext.length(); i += 8){
            String every_group = ciphertext.substring(i,i+8);
            for(int j = 0; j < 8; j++){
                int_plaintext[j] = Character.getNumericValue(every_group.charAt(j));
            }
            int_plaintext = encode_funtion.fun_IP(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k2);
            int_plaintext = encode_funtion.fun_SW(int_plaintext);
            int_plaintext = encode_funtion.fk(int_plaintext, k1);
            int_plaintext = encode_funtion.fun_IP_1(int_plaintext);

            int tmp_ascll = 0;
            for(int j = 0; j < 8; j++){
                tmp_ascll = tmp_ascll*2 + int_plaintext[j];
            }
            trans_cipher.append((char)tmp_ascll);

        }


        System.out.println("明文为："+str_plaintext);

        System.out.print("密钥为：");
        for (int i = 0; i < key.length; i++){
            System.out.print(key[i]);
        }
        System.out.println();

        System.out.print("k1为：");
        for (int i = 0; i < k1.length; i++){
            System.out.print(k1[i]);
        }
        System.out.println();

        System.out.print("k2为：");
        for (int i = 0; i < k2.length; i++){
            System.out.print(k2[i]);
        }
        System.out.println();

        System.out.println("密文为："+ ciphertext);

        System.out.println("解密后为："+trans_cipher);
        System.out.println();

        StringBuilder str_key = new StringBuilder();
        for(int i = 0; i < 10; i++){
            str_key.append(key[i]);
        }

        StringBuilder[] ans = {ciphertext_ascll, str_key};
        return ans;

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}