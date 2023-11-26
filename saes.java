// import java.util.Scanner;

public class saes {
    // private static int[] sBox = {0x9, 0x4, 0xA, 0xB, 0xD, 0x1, 0x8, 0x5, 0x6, 0x2, 0x0, 0x3, 0xC, 0xE, 0xF, 0x7};
    // private static int[] sBoxI = {0xA, 0x5,   0x9, 0xB, 0x1, 0x7, 0x8, 0xF, 0x6, 0x0, 0x2, 0x3, 0xC, 0x4, 0xD, 0xE};
    private static final int[] sBox = new int[256];
    private static final int[] sBoxI = new int[256];

    private static int multiplicativeInverse(int a) {
    if (a == 0) {
        return 0;
    }

    int t = 0;
    int newT = 1;
    int r = 0x11b;
    int newR = a;

    while (newR != 0) {
        int quotient = r / newR;

        int tempT = t;
        t = newT;
        newT = tempT ^ (quotient * newT);

        int tempR = r;
        r = newR;
        newR = tempR ^ (quotient * newR);
    }

    if (r > 1) {
        throw new RuntimeException("No inverse exists");
    }

    return t;
}


public static void s_box() {
    int p = 1, q = 1;

    do {
        // Multiply p by 3
        p = (p ^ (p << 1) ^ ((p & 0x80) != 0 ? 0x1B : 0)) & 0xFF;

        // Divide q by 3 (equals multiplication by 0xf6)
        q ^= q << 1;
        q ^= q << 2;
        q ^= q << 4;
        q ^= ((q & 0x80) != 0) ? 0x09 : 0;

        // Compute the affine transformation
        int xformed = (q ^ rotateLeft(q, 1) ^ rotateLeft(q, 2) ^ rotateLeft(q, 3) ^ rotateLeft(q, 4)) & 0xFF;

        sBox[p] = (xformed ^ 0x63);
    } while (p != 1);

    // 0 is a special case since it has no inverse
    sBox[0] = 0x63;
}

// Method to rotate left
public static int rotateLeft(int value, int shift) {
    return ((value << shift) | (value >>> (8 - shift))) & 0xFF;
}


    private static void generateInverseSBox() {
        for (int i = 0; i < 256; i++) {
            sBoxI[sBox[i]] = i;
        }
    }

    public static void main(String[] args) {
        s_box();
        System.out.println("SBOX created Successfully");
        for(int i=0;i<sBox.length;i++)
        {
            System.out.println(sBox[i]);
        }
        // generateInverseSBox();
        // Scanner s = new Scanner(System.in);
        // System.out.print("Enter 16 bit key in binary: ");
        // String str = s.nextLine();
        // int key = Integer.parseInt(str,2);


        // int[][] roundKeys = keyExpansion(key);
        // preRoundKey = roundKeys[0];
        // round1Key = roundKeys[1];
        // round2Key = roundKeys[2];

        // System.out.print("Enter 16 bit plain text in binary: ");
        // str = s.nextLine();
        // int plaintext = Integer.parseInt(str,2);

        // int cipher = encrypt(plaintext);
        // int plain = decrypt(cipher);


        // System.out.println("Cipher text: " + Integer.toBinaryString(cipher));
        // System.out.println("Plain text (decrypted): " + Integer.toBinaryString(plain));
        // s.close();
    }
}