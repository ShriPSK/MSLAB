import java.util.ArrayList;  
import java.util.Scanner;  
public class hill_cipher {  
    private static int[][] keyMatrix = new int[2][2];
    private static int[][] inverseMatrix = new int[2][2];

    private static void getKeyMatrix() {  
        Scanner s = new Scanner(System.in);  
        System.out.print("Enter key matrix:");  
        String key = s.nextLine();  
          
        double sq = Math.sqrt(key.length());  
        if (sq != (long) sq) {  
            throw new java.lang.Error("Cannot Form a square matrix");  
        }  
        int len = (int) sq;  
        int k = 0;  
        for (int i = 0; i < len; i++)  
        {  
            for (int j = 0; j < len; j++)  
            {  
                if(Character.isUpperCase(key.charAt(k)))
                {
                    keyMatrix[i][j] = ((int) key.charAt(k)) - 65;  
                }
                else
                {
                    keyMatrix[i][j] = ((int) key.charAt(k)) - 97;  
                }
                k++;  
            }  
        }  
    }  
    
    //matrix mod26 = (1,0,0,1)
        private static void isValidinverseMatrix(int[][] keyMatrix, int[][] inverseMatrix) {  
        int[][] product = new int[2][2];  
        
        product[0][0] = (keyMatrix[0][0]*inverseMatrix[0][0] + keyMatrix[0][1] * inverseMatrix[1][0]) % 26;  
        product[0][1] = (keyMatrix[0][0]*inverseMatrix[0][1] + keyMatrix[0][1] * inverseMatrix[1][1]) % 26;  
        product[1][0] = (keyMatrix[1][0]*inverseMatrix[0][0] + keyMatrix[1][1] * inverseMatrix[1][0]) % 26;  
        product[1][1] = (keyMatrix[1][0]*inverseMatrix[0][1] + keyMatrix[1][1] * inverseMatrix[1][1]) % 26;  
        
        if(product[0][0] != 1 || product[0][1] != 0 || product[1][0] != 0 || product[1][1] != 1) {  
            throw new java.lang.Error("Invalid reverse matrix found!");  
        }  
    }  
    
    private static void inverse_matrix() {  
        int detmod26 = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26;
        int factor;  
        
        // factor*det = 1 mod 26  
        for(factor=1; factor < 26; factor++)  
        {  
            if((detmod26 * factor) % 26 == 1)  
            {  
                break;  
            }  
        }  

        // Calculate the reverse key matrix elements using the factor found  
        inverseMatrix[0][0] = keyMatrix[1][1]           * factor % 26;  
        inverseMatrix[0][1] = (26 - keyMatrix[0][1])    * factor % 26;  
        inverseMatrix[1][0] = (26 - keyMatrix[1][0])    * factor % 26;  
        inverseMatrix[1][1] = keyMatrix[0][0]           * factor % 26;  
    }  
    
    private static String echo(ArrayList<Integer> str,String string) {  
        String res=""; 
        for(int i=0; i < str.size(); i ++) {  
            if(Character.isUpperCase(string.charAt(i)))
            {
                int n=str.get(i)+65;
                res+=(char)n;
            }
            else
            {
                int n=str.get(i)+97;
                res+=(char)n;
            }
        }  
        return res;  
    }  
    
    public static String encrypt(String str)  
    {  
        ArrayList<Integer> strToNum = new ArrayList<>();  
        ArrayList<Integer> cipher = new ArrayList<>();  
        str = str.replaceAll("[^a-zA-Z]","");  
        if(str.length() % 2 == 1) {  
            str += "z";  
        }  
        
        getKeyMatrix();
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];  
        if(det == 0) {  
            throw new java.lang.Error("Det equals to zero, invalid key matrix!");  
        } 
        
        for(int i=0; i < str.length(); i++) {  
            if(Character.isUpperCase(str.charAt(i)))
                strToNum.add(str.charAt(i) - 65);
            else
                strToNum.add(str.charAt(i) - 97);
        }  

        
        for(int i=0; i < strToNum.size(); i += 2) {  
            int x = (keyMatrix[0][0] * strToNum.get(i) + keyMatrix[0][1] * strToNum.get(i+1)) % 26;  
            int y = (keyMatrix[1][0] * strToNum.get(i) + keyMatrix[1][1] * strToNum.get(i+1)) % 26;  
            cipher.add(x);  
            cipher.add(y);  
        }  
        
        return echo(cipher,str);  
    }  
    

    public static String decrypt(String str)  
    { 
        ArrayList<Integer> strToNum = new ArrayList<>();  
        ArrayList<Integer> plain = new ArrayList<>();

        for(int i=0; i < str.length(); i++) {  
            if(Character.isUpperCase(str.charAt(i)))
                strToNum.add(str.charAt(i) - 65);
            else
                strToNum.add(str.charAt(i) - 97);
        }   
        inverse_matrix();  
        isValidinverseMatrix(keyMatrix, inverseMatrix);  
        
        for(int i=0; i < strToNum.size(); i += 2) {  
            plain.add((inverseMatrix[0][0] * strToNum.get(i) + inverseMatrix[0][1] * strToNum.get(i+1)) % 26);  
            plain.add((inverseMatrix[1][0] * strToNum.get(i) + inverseMatrix[1][1] * strToNum.get(i+1)) % 26);  
        }  
        
        return echo (plain, str);  
    }  
    
    public static void main(String[] args)
    {   
        Scanner s = new Scanner(System.in);  
        System.out.println("----------Hill Cipher----------");  
        System.out.print("Enter plain text: ");  
        String str = s.nextLine();  
        String cipher_text = encrypt(str);
        System.out.println("Cipher text: "+cipher_text);
        String plain_text = decrypt(cipher_text);
        System.out.println("Plain text (decrypted): "+plain_text);
    }  
} 