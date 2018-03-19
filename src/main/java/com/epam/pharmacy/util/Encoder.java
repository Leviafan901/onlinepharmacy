package com.epam.pharmacy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class, customer Password Encryption Tool
 *
 * @author Sosenkov Alexei
 */
public class Encoder {

	private static final String HASH_TYPE = "MD5";
	private static final Logger LOGGER = LoggerFactory.getLogger(Encoder.class);

    /**
     * Encrypts a string to MD5 format
     *
     * @param text - Not encrypted password
     * @return Return encrypted password
     */
    public static String encode(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_TYPE);
            byte[] array = messageDigest.digest(text.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte anArray : array) {
                stringBuffer.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));//
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Don't work encoding tool", e);
        }
        return null;
    }
}
