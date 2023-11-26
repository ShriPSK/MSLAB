import java.util.Scanner;
public class affine_cipher {
    static int a,b;

    private static int inverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }


    private static String encrypt(String word)
    {
        String res="";
        for(int i=0;i<word.length();i++)
        {
            if(word.charAt(i)!=' ')
            {
                int ascii;
                if(Character.isUpperCase(word.charAt(i)))   ascii=65;
                else ascii=97;
                int x=word.charAt(i) - ascii;
                int e = (a*x+b)%26;
                e+=ascii;
                char y=(char)e;
                res+=y;
            }
            else{
                res+=word.charAt(i);
            }
        }
        return res;

    }


    private static String decrypt(String word) {
        int aInverse = inverse(a, 26);
        String res="";

        for (int i = 0; i < word.length(); i++) {

            if (word.charAt(i)!=' ') {
                int ascii = Character.isUpperCase(word.charAt(i)) ? 65 : 97;
                int decryptedChar = (aInverse * (word.charAt(i) - ascii - b + 26)) % 26 + ascii;
                res+=((char) decryptedChar);
            } else {
                res+=word.charAt(i);
            }
        }
        return res;
    }

    public static void main(String []args)
    {
        Scanner s=new Scanner(System.in);
        System.out.println("----------AFFINE CIPHER----------");  
        System.out.print("Enter key values a and b: ");
        a=s.nextInt();
        b=s.nextInt();
        System.out.print("\nEnter plain text: ");
        String str=s.next();
        String cipher_text = encrypt(str);
        System.out.println("Cipher text: "+cipher_text);        
        String plain_text = decrypt(cipher_text);
        System.out.println("Plain text (decrypted): "+plain_text);
    }
}
