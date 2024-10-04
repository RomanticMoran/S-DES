package des;

/**
 * @Author {陈友豪}
 * @Date 2024/9/29 21:19
 */
import java.util.Scanner;

public class AttackService {

    private static String bruteForceAttack(String plainText, String cipherText) {
        long startTime = System.currentTimeMillis();

        // 遍历所有可能的10位密钥
        for (int i = 0; i < 1024; i++) {
            String key = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');

            EncryptService encryptService = new EncryptService(plainText, key);
            String encryptedText = encryptService.encrypt();

            // 检查加密后的文本是否与密文匹配
            if (encryptedText.equals(cipherText)) {
                long endTime = System.currentTimeMillis();
                System.out.println("找到密钥用时: " + (endTime - startTime) + " 毫秒");
                return key;
            }
        }

        return null; // 未找到匹配的密钥
    }
}
