import java.util.Scanner;
public class caeser_cipher {

    private static String encrypt(String word,int key)
    {
        String res="";
        for(int i=0;i<word.length();i++)
        {
            int ascii;
            if(Character.isUpperCase(word.charAt(i)))   ascii=65;
            else ascii=97;
            int c=((word.charAt(i)+key-ascii)%26)+ascii;
            char m=(char)c;
            res+=m;
        }
        return res;
    }

    private static String decrypt(String word,int key)
    {
        String res="";
        for(int i=0;i<word.length();i++)
        {
            int ascii;
            if(Character.isUpperCase(word.charAt(i)))   ascii=65;
            else ascii=97;
            int c=((word.charAt(i)-key-ascii+26)%26)+ascii;
            char m=(char)c;
            res+=m;
        }
        return res;
    }

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.println("----------CAESER CIPHER----------");  
        System.out.print("Enter key: ");
        int key = Integer.parseInt(s.nextLine());
        
            System.out.print("\nEnter plain text: ");
            String str = s.nextLine();
            String cipher_text = encrypt(str,key);
            System.out.println("Cipher text: " + cipher_text);
            String plain_text = decrypt(cipher_text,key);
            System.out.println("Plain text (decrypted): " + plain_text);
    }
}
