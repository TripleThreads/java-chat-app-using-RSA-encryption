package sample.shared;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;


public class RSA {

    private final static BigInteger one = new BigInteger("1");

    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;

    private BigInteger publicKey;

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    private BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    public RSA(int N) {

        BigInteger p = BigInteger.probablePrime(N / 2, random);

        BigInteger q = BigInteger.probablePrime(N / 2, random);

        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);

        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1 value of e

        privateKey = publicKey.modInverse(phi); // d * e = 1 mod phi
    }


    String encrypt(String message, BigInteger receiverPublicKey, BigInteger receiverModulus) {

        byte[] bytes = message.getBytes();

        BigInteger messageBigInt = new BigInteger(bytes);

        BigInteger encrypted = messageBigInt.modPow(receiverPublicKey, receiverModulus);

        return Base64.getEncoder().encodeToString(encrypted.toByteArray());
    }

    BigInteger decrypt(String encrypted) {

        BigInteger encryptedInt = new BigInteger(Base64.getDecoder().decode(encrypted));

        return encryptedInt.modPow(privateKey, modulus);

    }

    String signature(String message) {

        byte[] bytes = message.getBytes();

        BigInteger messageBigInt = new BigInteger(bytes);

        BigInteger signature = messageBigInt.modPow(this.privateKey, this.modulus);

        return Base64.getEncoder().encodeToString(signature.toByteArray());
    }

    boolean verifySignature(String signature, BigInteger publicKey, BigInteger modulus, BigInteger msg) {

        BigInteger bigInteger = new BigInteger(Base64.getDecoder().decode(signature));

        BigInteger decodedSign = bigInteger.modPow(publicKey, modulus);

        return msg.equals(decodedSign);
    }


    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus + "\n";
        return s;
    }

}