package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String args[]) throws IOException {


        Scanner sc = new Scanner(System.in);

        System.out.println("Iveskite teksta");
        String msg = sc.nextLine();
        System.out.println("Iveskite p");
        BigInteger p = sc.nextBigInteger();
        System.out.println("Iveskite q");
        BigInteger q = sc.nextBigInteger();


        FileWriter myWriter = new FileWriter("irasyti.txt");
        FileWriter myWriter2 = new FileWriter("desifravimas.txt");




        BigInteger n = p.multiply(q);


        var e = BigInteger.valueOf(0x10001);
        System.out.println("pirmass" + p + "antras" + q);
        var phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println(phi);

        var d = e.modInverse(phi);
        System.out.println("Public key" + e + "n reiksme" + n);


        byte[] bytes = msg.getBytes();

        int i_decypherVal = 0;
        for (int i = 0; i < msg.length(); i++) {

            int asciiVal = bytes[i];
            BigInteger val = new BigInteger(String.valueOf(asciiVal));
            BigInteger cipherVal = val.modPow(e, n);

            //System.out.println("ciphered text:"+cipherVal);

            BigInteger decypherVal = cipherVal.modPow(d, n);
            i_decypherVal = decypherVal.intValue();

            System.out.println("\nMessage: " + msg.charAt(i) +
                    ", Ascii number: " + asciiVal +
                    "\nCyphered text: " + cipherVal +

                    "\nDecyphered text: " + Character.toString((char) i_decypherVal));
            System.out.println(i_decypherVal);
            myWriter.write(String.valueOf(cipherVal) + " ");
            myWriter2.write(String.valueOf(cipherVal) + " ");

        }
        myWriter.write("Public raktas " + String.valueOf(e)+" "+ n);
        myWriter.close();
        myWriter2.close();

        BigInteger [] bigInteger = new BigInteger[bytes.length];
        String actual = Files.readString(Path.of("desifravimas.txt"));
        String [] numbers  = actual.split(" ");
        for (int i = 0;i<numbers.length;i++){
            bigInteger[i]=new BigInteger(numbers[i]);
        }

        byte [] bytes2 = new byte[bigInteger.length];
        for (int i = 0; i <bytes2.length;i++){
            bytes2[i]=bigInteger[i].modPow(d,n).byteValue();
        }
        String str = new String(bytes2);
        System.out.println(str);
    }
}
