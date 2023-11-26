import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;
public class rsa {
    public static ArrayList<Integer> dec_to_bin_rev(int num)
    {
        ArrayList<Integer> res=new ArrayList<>();
        int x=num;
        while(x>0)
        {
            int rem = x%2;
            res.add(rem);
            x/=2;
        }
        return res;
    }

    public static int square_and_multiply(int a1, int x, BigInteger n)
    {
        BigInteger y = BigInteger.ONE;
        BigInteger a = BigInteger.valueOf(a1);
        ArrayList<Integer> binary=dec_to_bin_rev(x);
        // System.out.println(dec_to_bin_rev(b));
        for(int i=0;i<binary.size();i++)
        {
            if(binary.get(i)==1)
            {
                y = a.multiply(y).mod(n);
                a = a.multiply(a).mod(n);
            }
            else if(binary.get(i)==0)
            {
                a = a.multiply(a).mod(n);

            }
            // System.out.println(y + "\t" + a);
        }
        return y.intValue();
    }


    public static int extended_eucledian(int e, BigInteger n)
    {
        BigInteger r1 = n;
        BigInteger r2 = BigInteger.valueOf(e);
        // System.out.println(r1+"\t"+r2);
        BigInteger r = r1.mod(r2);
        BigInteger q = r1.divide(r2);
        BigInteger t1 = BigInteger.valueOf(0);
        BigInteger t2 = BigInteger.valueOf(1);
        BigInteger t = t1.subtract(q.multiply(t2));
        // System.out.println(q+"\t"+r1+"\t"+r2+"\t"+r+"\t"+t1+"\t"+t2+"\t"+t);
        // while(r!=0)
        while(!r.equals(BigInteger.ZERO))
        {
            r1=r2;
            r2=r;
            t1=t2;
            t2=t;
            q=r1.divide(r2);
            r=r1.mod(r2);
            t=t1.subtract(q.multiply(t2));
            // System.out.println(q+"\t"+r1+"\t"+r2+"\t"+r+"\t"+t1+"\t"+t2+"\t"+t);

        }
        if(t2.compareTo(BigInteger.ZERO) == -1)    return t2.add(n).intValue();
        else return t2.intValue();
    }

    public static boolean check_prime(int n)
    {
        if(n==0 || n==1 || n==2)    return false;
        for(int i=2;i<n;i++)
        {
            if(n%i==0)  return false;
        }
        return true;
    }

    public static ArrayList<Integer> encrypt(String plain_text, int e, BigInteger n)
    {
        plain_text = plain_text.toLowerCase();
        char[] plain_char = plain_text.toCharArray();
        ArrayList<Integer> cipher_text = new ArrayList<>();
        // for(int i=0;i<plain_text.length();i++)
        for(char c: plain_char)
        {
            cipher_text.add(square_and_multiply(c-96, e, n));
        }
        return cipher_text;
    }

    public static ArrayList<Character> decrypt(ArrayList<Integer> cipher_text, int e, BigInteger n, int p, int q)
    {
        int d = extended_eucledian(e,BigInteger.valueOf((p-1)*(q-1)));
        System.out.println("The private key (d) is "+d);
        ArrayList<Character> decrypted_text = new ArrayList<>();
        for(int i: cipher_text)
        {
            int c = square_and_multiply(i, d, n) + 96;
            decrypted_text.add((char)c);
        }
        return decrypted_text;
    }

    
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);

        //Plain text
        System.out.print("Enter plain text: ");
        String plain_text = s.nextLine();

        //Value p
        System.out.print("Enter value of p: ");
        int p = Integer.parseInt(s.nextLine());
        if(!check_prime(p))
        {
            System.out.println("Enter a prime number");
            System.exit(0);
        }

        //Value q
        System.out.print("Enter value of q: ");
        int q = Integer.parseInt(s.nextLine());
        if(!check_prime(q))
        {
            System.out.println("Enter a prime number");
            System.exit(0);
        }
        
        BigInteger n = BigInteger.valueOf(p*q);

        //Public key e
        System.out.print("Enter public key (e): ");
        int e = Integer.parseInt(s.nextLine());
        if(e<=0 || e>=(p-1)*(q-1))
        {
            System.out.print("Enter correct value for e (0 to phi(n))");
            System.exit(0);
        }

        //Encryption
        ArrayList<Integer> cipher_text = encrypt(plain_text,e,n);
        System.out.println("\nThe cipher text is "+cipher_text);
       
        //Decryption
        ArrayList<Character> decrypted_text = decrypt(cipher_text, e, n, p, q);
        System.out.print("The decrypted text is ");
        for(char c: decrypted_text) System.out.print(c);

        s.close();
    }
}