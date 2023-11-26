import java.util.Scanner;

public class affine_attack {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        int aInverse;
        
        System.out.print("Enter cipher to be attacked:");
        String cipher = s.nextLine();
        
        for (int a = 1; a < 26; a++) {
            aInverse = findInverse(a, 26);
            if (aInverse == -1) {
                continue;
            }
            
            for (int b = 0; b < 26; b++) {
                if(commonDivisor(a, b)!=1)  continue;
                
                String plain="";
                for (int i=0;i<cipher.length();i++) {
                    char c = Character.toLowerCase(cipher.charAt(i));
                    if(Character.isAlphabetic(c)==false)
                    {
                        plain+=c;
                        continue;
                    }
                    int index = alphabets.indexOf(c);
                    if (index != -1) {
                        int newIndex = (aInverse * (index - b)) % 26;
                        if (newIndex < 0) {
                            newIndex += 26;
                        }
                        if(Character.isUpperCase(cipher.charAt(i)))
                            plain+=Character.toUpperCase(alphabets.charAt(newIndex));
                        else
                            plain+=alphabets.charAt(newIndex);
                    }
                    else {
                        plain+=c;
                    }
                    
                }
                
                System.out.println("Key a: " + a + " Key b: " + b + " Plain: " + plain.toString());
            }
        }
    }
    
    public static int findInverse(int a, int m) {
        for (int i = 0; i < m; i++) {
            if ((a * i) % m == 1) {
                return i;
            }
        }
        return -1;
    }

    private static int commonDivisor(int n1, int n2) {
        if (n1 == 0 || n2 == 0)
           return 0;
        if (n1 == n2)
           return n1;
        if (n1 > n2)
           return commonDivisor(n1-n2, n2);
        return commonDivisor(n1, n2-n1);
     }
}