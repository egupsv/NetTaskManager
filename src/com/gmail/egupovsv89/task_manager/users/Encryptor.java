package com.gmail.egupovsv89.task_manager.users;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {
    private final Cipher cipher;
    SecretKey key;

    public Encryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        int keySize = 256;
        keyGen.init(keySize);
        try {
            File file = new File("key.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            byte[] decodedKey = Base64.getDecoder().decode(line);
            this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String password) {
        String result = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = password.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedText = cipher.doFinal(plainText);
            result = Base64.getEncoder().encodeToString(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
