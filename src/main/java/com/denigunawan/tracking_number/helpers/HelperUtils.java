package com.denigunawan.tracking_number.helpers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelperUtils {

    public static String hashToBase36(String rawData) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInt = new BigInteger(1, hashBytes);
        return bigInt.toString(36).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Error generating hash", e);
    }
}

}
