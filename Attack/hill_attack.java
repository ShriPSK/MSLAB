import java.util.Scanner;

import Jama.Matrix;

public class hill_attack {
    public static int mul_inv(int a, int b) {
        int t1 = 0, t2 = 1, t;
        int r, q;
        while (b != 0) {
            q = a / b;
            r = a % b;
            a = b;
            b = r;
            t = t1 - q * t2;
            t1 = t2;
            t2 = t;

        }
        if (t1 < 0)
            return t1 + 26;
        return t1;
    }
    static int[][] multiplyMatrix(int row1, int col1,
            int A[][], int row2,
            int col2, int B[][]) {
        int i, j, k;
        int C[][] = new int[row1][col2];
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }
        return C;
    }
    public static int[][] matInverse(double[][] mat1){
        Matrix m1 = new Matrix(mat1);
        Matrix m1i = m1.inverse();
        double[][] inverseArray = m1i.getArray();
        int[][] ansArray=new int[inverseArray.length][inverseArray[0].length];
        for(int i=0;i<inverseArray.length;i++){
            for(int j=0;j<inverseArray[i].length;j++){
                int ans=(int) (Math.round(inverseArray[i][j] * m1.det()));
                ans%=26;
                if(ans<0) ans+=26;
                ans=ans*mul_inv(26,(int)(Math.round(m1.det())));
                ans%=26;
                if(ans<0) ans+=26;
                ansArray[i][j]=ans;
            }
        }
        return ansArray;
    }
    public static int GCD(int a, int b) {
        if (b == 0)
            return a;
        return (GCD(b, a % b));
    }

    public static String generate_plain_text(String cipher, String key) {
        int kl = key.length();
        int c=cipher.length();          //c=length of cipher text
        int p;                          //p=length of plain text 
        int k = (int) Math.sqrt(kl);    //k=number of rows/columns in key matrix
        if (c % k == 0)
            p = c / k;
        else
            p = c / k + 1;
        double[][] keyMatrix = new double[k][k];
        int[][] cipherText = new int[k][p];

        int index = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                int temp=key.charAt(index++) - 'A';
                keyMatrix[i][j] = temp;
            }
        }
        index = 0;
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < k; j++) {
                if (index < c)
                    cipherText[j][i] = cipher.charAt(index++) - 'A';
                else
                    cipherText[j][i] = '-';
            }
        }
        int[][] inv=matInverse(keyMatrix);
        
        int[][] result = multiplyMatrix(k,k,inv,k, p,cipherText);
        
        String ans = "";
       for (int i = 0; i < result[0].length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[j][i] = (result[j][i] % 26) + 'A';
                ans += (char) result[j][i];
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter plain text: ");
        String plain = s.nextLine();
        System.out.print("Enter cipher text: ");
        String cipher = s.nextLine();
            
        //Plain text: TEXT
        //Cipher text: RGWL
        //Key: DDCF
        for (int a = 0; a < 26; a++) {
            for (int b = 0; b < 26; b++) {
                for (int c = 0; c < 26; c++) {
                    for (int d = 0; d < 26; d++) {
                        int determinant = (a * d - b * c) % 26;
                        if (determinant != 0 && GCD(determinant, 26) == 1) {
                            String k="";
                            k+=(char)('A' + a);
                            k+=(char) ('A' + b);
                            k+=(char) ('A' + c);
                            k+=(char) ('A' + d);
                            String res = generate_plain_text(cipher,k);
                            System.out.println(" Key:"+k+"\tPlain text(decrypted): "+res);
                            if(res.equals(plain))
                            {
                                System.out.println("Key Found!");
                                System.exit(0);
                            }
                            
                            
                        }
                    }
                }
            }
        }
    }
}