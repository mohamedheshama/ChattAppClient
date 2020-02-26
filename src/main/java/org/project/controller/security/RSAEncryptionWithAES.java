package org.project.controller.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

// Java 8 example for RSA-AES encryption/decryption.
// Uses strong encryption with 2048 key size.
public class RSAEncryptionWithAES {
     Map<String, Object> keys; // auto generated -> new
     PrivateKey privateKey;   // come from keys
     PublicKey publicKey;     // come from keys todo sent with message

     String secretAESKeyString; //AES Key todo sender
     String encryptedText;    // Encrypt our data with AES key todo sender
     String encryptedAESKeyString; // Encrypt AES Key with RSA Private Key todo sender todo sent with message

     String decryptedAESKeyString; //First decrypt the AES Key with RSA Public key // todo receiver

    public RSAEncryptionWithAES() throws Exception {
        keys = getRSAKeys();
        privateKey = (PrivateKey) keys.get("private");
        publicKey = (PublicKey) keys.get("public");
        secretAESKeyString = getSecretAESKeyAsString();
        encryptedAESKeyString = encryptAESKey(secretAESKeyString, privateKey);
    }


    public Map<String, Object> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, Object> keys) {
        this.keys = keys;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public String getEncryptedAESKeyString() {
        return encryptedAESKeyString;
    }

    public void setEncryptedAESKeyString(String encryptedAESKeyString) {
        this.encryptedAESKeyString = encryptedAESKeyString;
    }

    public String getDecryptedAESKeyString() {
        return decryptedAESKeyString;
    }

    public void setDecryptedAESKeyString(String decryptedAESKeyString) {
        this.decryptedAESKeyString = decryptedAESKeyString;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }

    String decryptedText; // Now decrypt data using the decrypted AES key! todo receiver

    public String getSecretAESKeyString() {
        return secretAESKeyString;
    }

    public void setSecretAESKeyString(String secretAESKeyString) {
        this.secretAESKeyString = secretAESKeyString;
    }


/*    public static void main(String[] args) throws Exception {
        String plainText = "Hello World!";

        // Generate public and private keys using RSA
         keys = getRSAKeys();
         privateKey = (PrivateKey) keys.get("private");
         publicKey = (PublicKey) keys.get("public");

        // First create an AES Key
        secretAESKeyString = getSecretAESKeyAsString();

        // Encrypt our data with AES key
        encryptedText = encryptTextUsingAES(plainText, secretAESKeyString);

        // Encrypt AES Key with RSA Private Key
        encryptedAESKeyString = encryptAESKey(secretAESKeyString, privateKey);

        // The following logic is on the other side.

        // First decrypt the AES Key with RSA Public key
         decryptedAESKeyString = decryptAESKey(encryptedAESKeyString, publicKey);

        // Now decrypt data using the decrypted AES key!
         decryptedText = decryptTextUsingAES(encryptedText, decryptedAESKeyString);

        System.out.println("input:" + plainText);
        System.out.println("encrypted text : " + encryptedText);
        System.out.println("AES Key:" + secretAESKeyString);
        System.out.println("decrypted:" + decryptedText);

    }*/

    // Create a new AES key. Uses 128 bit (weak)
    public static String getSecretAESKeyAsString() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secKey.getEncoded());
        return encodedKey;
    }

    // Encrypt text using AES key
    public static String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(byteCipherText);
    }

    // Decrypt text using AES key
    public static String decryptTextUsingAES(String encryptedText, String aesKeyString) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] bytePlainText = aesCipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(bytePlainText);
    }

    // Get RSA keys. Uses key size of 2048.
    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }

    // Decrypt AES Key using RSA public key
    public static String decryptAESKey(String encryptedAESKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey)));
    }

    // Encrypt AES Key using RSA private key
    private static String encryptAESKey(String plainAESKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainAESKey.getBytes()));
    }
}
