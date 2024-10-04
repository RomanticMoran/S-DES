public class encode_funtion {
    public static int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    public static int[] IP_1 = {4,1,3,5,7,2,8,6};
    public static int[] P10 = {3,5,2,7,4,10,1,9,8,6};
    public static int[] P8 = {6,3,7,4,8,5,10,9};
    public static int[] EPBox = {4,1,2,3,2,3,4,1};
    public static int[] SPBox = {2,4,3,1};
    public static int[][] SBox1 ={{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,0,2}};
    public static int[][] SBox2 ={{0,1,2,3},{2,3,1,0},{3,0,1,2},{2,1,0,3}};

    public static int[] fun_IP(int[] a) {
        int[] b = new int[8];
        for (int i = 0; i < 8; i++) {
            b[i] = a[IP[i]-1];
        }
        return b;
    }

    public static int[] fun_IP_1(int[] a) {
        int[] b = new int[8];
        for (int i = 0; i < 8; i++) {
            b[i] = a[IP_1[i]-1];
        }
        return b;
    }

    public static int[] fun_P10(int[] a){
        int[] b = new int[10];
        for (int i = 0; i < 10; i++) {
            b[i] = a[P10[i]-1];
        }
        return b;
    }

    public static int[] fun_P8(int[] a){
        int[] b = new int[8];
        for (int i = 0; i < 8; i++) {
            b[i] = a[P8[i]-1];
        }
        return b;
    }

    public static int[] fun_EPBox(int[] a){
        int[] b = new int[8];
        for (int i = 0; i < 8; i++) {
            b[i] = a[EPBox[i]-1];
        }
        return b;
    }

    public static int[] fun_SBox(int[] a){
        int[] ans = new int[4];
        int x = a[0]*2+a[3];
        int y = a[1]*2+a[2];
        int tag = SBox1[x][y];
        ans[1] = tag % 2;
        tag /= 2;
        ans[0] = tag % 2;

        x = a[4]*2+a[7];
        y = a[5]*2+a[6];
        tag = SBox2[x][y];
        ans[3] = tag % 2;
        tag /= 2;
        ans[2] = tag % 2;
        return ans;
    }

    public static int[] fun_SPBox(int[] a){
        int[] b = new int[4];
        for (int i = 0; i < 4; i++) {
            b[i] = a[SPBox[i]-1];
        }
        return b;
    }

    public static int[] fun_SW(int[] a){
        int[] b = new int[8];
        for(int i = 0; i < 4; i++){
            b[i] = a[i+4];
            b[i+4] = a[i];
        }
        return b;
    }


    public static int[] Leftshift1(int[] a){
        int tmp = a[0];
        for(int i = 0; i < 9; i++){
            a[i] = a[i+1];
        }
        a[9] = tmp;
        return a;
    }

    public static int[] fun_xor(int[] a, int[] b){
        int[] c = new int[a.length];
        for(int i = 0; i < a.length; i++){
            c[i] = a[i] ^ b[i];
        }
        return c;
    }

    public static int[] F(int[] R, int[] k){
        int[] F_R = fun_EPBox(R);
        F_R = fun_xor(F_R, k);
        F_R = fun_SBox(F_R);
        F_R = fun_SPBox(F_R);
        return F_R;
    }

    public static int[] fk(int[] a, int[] k){
        int[] R = new int[4];
        int[] L = new int[4];
        int[] F_R = new int[4];
        int[] ans = new int[8];
        for (int i = 0; i < 4; i++){
            L[i] = a[i];
            R[i] = a[i+4];
        }
        F_R = F(R,k);
        L = fun_xor(L,F_R);
        for(int i = 0; i < 4; i++){
            ans[i] = L[i];
            ans[i+4] = R[i];
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
