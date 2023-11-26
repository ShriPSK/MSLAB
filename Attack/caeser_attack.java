import java.util.Scanner;

public class caeser_attack {
    public static void main(String[] args) {
        System.out.println("----------CRYPTANALYSIS OF CAESER CIPHER----------");  
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        Scanner s = new Scanner(System.in);
        System.out.print("\nEnter cipher to be attacked:");
        String cipher = s.nextLine();
        for (int key = 0; key < 26; key++) {
            String plain_text="";
            for (int j=0;j<cipher.length();j++) {
                char c = Character.toLowerCase(cipher.charAt(j));
                int num=alphabets.indexOf(c) - key;
                if(num<0)   num +=26;
                if(Character.isUpperCase(cipher.charAt(j)))
                {
                    plain_text+=Character.toUpperCase(alphabets.charAt(num));
                }
                else
                    plain_text+=alphabets.charAt(num);
            }
            System.out.println("Key:" + key + " Plain text (decrypted):" + plain_text);
        }
    }
}
