package des;

import java.util.Scanner;

/**
 * @Author {陈友豪}
 * @Date 2024/9/29 19:50
 */
public class EncryptService {
    private int[] IPout;  //初始置换盒
    private int[] VerseIPout;  //最终置换盒
    private int[] f_EP_Boxout;
    private String[][] sbox1;
    private String[][] sbox2;
    private int[] spBox;
    private int[] P10;
    private int[] P8;
    private boolean isAscii = false; // ascii检验码
    private int asciiNum; // ascii字符串数
    private boolean[] key; // 10bit密钥

    private boolean[][] pAscii; // 输入的ascii明文
    private boolean[] pBinary; // 输入的二进制明文
    private boolean[][] cAscii; //ascii密文
    private boolean[] cBinary; // 二进制密文

    public EncryptService(String pin, String keyin) {
        // 检查是否为ASCII字符串
        for (int i = 0; i < pin.length(); i++) {
            if (pin.charAt(i) != '0' && pin.charAt(i) != '1') {
                this.isAscii = true;
                break; // 找到一个非二进制字符后立即停止
            }
        }
        if (this.isAscii) { // 处理 ASCII 字符串
            byte[] bytes = pin.getBytes();
            this.asciiNum = bytes.length;
            this.pAscii = new boolean[this.asciiNum][8];

            for (int i = 0; i < this.asciiNum; i++) {
                int temp = bytes[i];
                for (int j = 7; j >= 0; j--) {
                    this.pAscii[i][j] = (temp % 2 == 1); // 直接使用布尔值表示
                    temp = temp / 2; // 继续处理下一位
                }
            }
        } else { // 处理二进制串
            int length = pin.length();
            this.pBinary = new boolean[length];
            for (int i = 0; i < length; i++) {
                this.pBinary[i] = (pin.charAt(i) == '1');
            }
        }

        // 初始化密钥
        this.key = new boolean[keyin.length()];
        for (int i = 0; i < keyin.length(); i++) {
            this.key[i] = (keyin.charAt(i) == '1');
        }
        init(); // 初始化算法数据
    }

    public String encrypt() { // 解密方法
        StringBuilder outPutCipherText = new StringBuilder(); // 使用 StringBuilder 提高性能
        boolean[] key = this.key;
        String result;

        if (this.isAscii) { // 处理 ASCII 串
            this.cAscii = new boolean[this.asciiNum][8];
            for (int i = 0; i < this.asciiNum; i++) {
                this.cAscii[i] = DesFunction(this.pAscii[i], key);
                char thischar = booleanArrayToChar(this.cAscii[i]);
                outPutCipherText.append(thischar);
            }
        } else { // 处理布尔串
            int length = this.pBinary.length;
            for (int i = 0; i < length; i += 8) {
                boolean[] segment = new boolean[Math.min(8, length - i)]; // 处理每 8 位
                System.arraycopy(this.pBinary, i, segment, 0, segment.length);
                boolean[] encryptedSegment = DesFunction(segment, key);

                String booleanList = booleanArrayToString(encryptedSegment);
                outPutCipherText.append(booleanList);
            }
        }
        return outPutCipherText.toString();
    }

    public void init() {
        this.IPout = new int[]{2, 6, 3, 1, 4, 8, 5, 7};    //初始置换盒IP
        this.VerseIPout = new int[]{4, 1, 3, 5, 7, 2, 8, 6}; //最终置换盒

        this.P10 = new int[]{3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
        this.P8 = new int[]{6, 3, 7, 4, 8, 5, 10, 9};

        this.f_EP_Boxout = new int[]{4, 1, 2, 3, 2, 3, 4, 1}; //epBox

        this.sbox1 = new String[][]{{"01", "00", "11", "10"}, {"11", "10", "01", "00"},
                {"00", "10", "01", "11"},
                {"11", "01", "00", "10"}};
        this.sbox2 = new String[][]{{"00", "01", "10", "11"}, {"10", "11", "01", "00"},
                {"11", "00", "01", "10"},
                {"10", "01", "00", "11"}};

        this.spBox = new int[]{2, 4, 3, 1}; //spBox
    }

    public boolean[] DesFunction ( boolean[] plainText, boolean[] Key){
        boolean[] plainText_1 = plainText;
        boolean[] key_1 = Key;
        boolean[] k1 = kiCreator(key_1, "1");
        boolean[] k2 = kiCreator(key_1, "2");

        plainText_1 = IP(plainText_1);
        plainText_1 = f(plainText_1, k1);
        plainText_1 = swap(plainText_1);
        plainText_1 = f(plainText_1, k2);
        plainText_1 = ViseIP(plainText_1);
        boolean[] cipherText = plainText_1;
        return cipherText;
    }

    public boolean[] kiCreator ( boolean[] k, String i){   //生成子密钥Ki
        boolean[] pList = replacement(k, this.P10); //P10操作
        boolean[] sList = new boolean[k.length];

        if (i == "1") {
            sList = leftShift(pList);  //  左移一次生成K1
        } else if (i == "2") {
            sList = leftShift(leftShift(pList));  // 左移两次生成K2
        }
        boolean[] ki = replacement(sList, this.P8); //P8操作
        return ki;
    }

    public static boolean[] leftShift ( boolean[] p){   //leftshift操作
        boolean[] shiftedResult = new boolean[p.length];
        for (int i = 0; i < p.length - 1; i++) {
            shiftedResult[i] = p[i + 1]; // 向左移动
        }
        shiftedResult[p.length - 1] = p[0]; // 循环移动到末尾
        return shiftedResult;
    }

    public boolean[] IP ( boolean[] P){    //初始置换
        boolean[] result = replacement(P, this.IPout);
        return result;
    }

    public boolean[] ViseIP ( boolean[] P){   //最终置换
        boolean[] result = replacement(P, this.VerseIPout);
        return result;
    }

    public boolean[] f ( boolean[] P, boolean[] ki){  //fk函数
        boolean[] pList = P;
        boolean[] pl = new boolean[pList.length / 2];
        boolean[] pr = new boolean[pList.length / 2];
        for (int i = 0; i < pList.length / 2; i++) {
            pl[i] = pList[i];
            pr[i] = pList[i + pList.length / 2];
        }

        boolean[] p1 = replacement(pr, this.f_EP_Boxout); //epBox（8bit）

        boolean[] p2 = new boolean[8];   // 异或操作(8bit)
        for (int i = 0; i < 8; i++) {
            p2[i] = p1[i] ^ ki[i];
        }

        boolean[] p3 = sbox(p2); // S-box(4bit)

        boolean[] p4 = replacement(p3, this.spBox); // 直接置换(4bit)

        boolean[] newpl = new boolean[4];  // 异或操作得到新的pl(4bit)
        for (int i = 0; i < 4; i++) {
            newpl[i] = pl[i] ^ p4[i];
        }

        for (int i = 0; i < 4; i++) {    // 将pl和pr组合
            pList[i] = newpl[i];
            pList[i + 4] = pr[i];
        }
        return pList;
    }

    public boolean[] swap ( boolean[] P){  //将左右两部分交换
        boolean[] result = new boolean[8];
        for (int i = 0; i < 4; i++) {
            result[i] = P[i + 4];
            result[i + 4] = P[i];
        }
        return result;
    }

    public boolean[] replacement ( boolean[] a, int[] order){  //替换操作
        boolean[] result = new boolean[order.length];
        for (int i = 0; i < order.length; i++) {
            result[i] = a[order[i] - 1];
        }
        return result;
    }

    public boolean[] sbox ( boolean[] origin){
        boolean[] r = new boolean[4];
        int parameter0 = chooseParam(origin[0], origin[3]);
        int parameter1 = chooseParam(origin[1], origin[2]);
        int parameter2 = chooseParam(origin[4], origin[7]);
        int parameter3 = chooseParam(origin[5], origin[6]);
        r[0] = intToBoolean(Character.getNumericValue(this.sbox1[parameter0][parameter1].charAt(0)));
        r[1] = intToBoolean(Character.getNumericValue(this.sbox1[parameter0][parameter1].charAt(1)));
        r[2] = intToBoolean(Character.getNumericValue(this.sbox2[parameter2][parameter3].charAt(0)));
        r[3] = intToBoolean(Character.getNumericValue(this.sbox2[parameter2][parameter3].charAt(1)));

        return r;
    }

    public static boolean intToBoolean ( int i){
        if (i == 0)
            return false;
        else
            return true;
    }


    public int chooseParam ( boolean a, boolean b){
        if (!a) {
            if (!b)
                return 0;
            else
                return 1;
        } else {
            if (!b)
                return 2;
            else
                return 3;
        }

    }

    private char booleanArrayToChar ( boolean[] booleanArray){
        int charAscii = 0;
        for (int j = 0; j < booleanArray.length; j++) {
            if (booleanArray[j]) {
                charAscii += Math.pow(2, booleanArray.length - 1 - j); // 从高位到低位计算 ASCII
            }
        }
        return (char) charAscii;
    }

    public String booleanArrayToString ( boolean[] booleanArray){
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean value : booleanArray) {
            stringBuilder.append(value ? '1' : '0');
        }
        return stringBuilder.toString(); // 返回最终的字符串
    }


}



