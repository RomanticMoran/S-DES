import des.DecryptService;
import des.DecryptWindow;

import javax.swing.*;
import java.util.Scanner;

/**
 * @Author {陈友豪}
 * @Date 2024/9/27 20:17
 */
public class Main {
    public static void main(String[] args) {
        //解密界面
        SwingUtilities.invokeLater(() -> {
            DecryptWindow window = new DecryptWindow();
            window.setVisible(true);
        });

//        暴力破解
//        Scanner scanner = new Scanner(System.in);
//
//        // 输入明文
//        System.out.println("请输入明文:");
//        String plainText = scanner.nextLine();
//
//        // 输入密文
//        System.out.println("请输入密文:");
//        String cipherText = scanner.nextLine();
//
//        // 暴力破解密钥
//        String foundKey = bruteForceAttack(plainText, cipherText);
//
//        if (foundKey != null) {
//            System.out.println("找到密钥: " + foundKey);
//        } else {
//            System.out.println("未找到匹配的密钥。");
//        }
//    }
    }
}
