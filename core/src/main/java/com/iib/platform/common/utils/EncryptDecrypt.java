package com.iib.platform.common.utils;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = { EncryptDecrypt.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "EncryptDecrypt" })
public class EncryptDecrypt {

	private static Logger log = LoggerFactory.getLogger(EncryptDecrypt.class);

	public static String encrypt(String key, String initVector, String value) {
		try {
			
			log.error("encryption for Value:: {}", value);
			
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			log.error("Exception in EncryptDecrypt API :: ", ex);
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {

			log.info(initVector);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			log.error("Exception in EncryptDecrypt API :: ", ex);
		}

		return null;
	}

	public static String makeid() {
		String text = "";
		String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 16; i++)
			text += possible.charAt((int) (Math.random() * possible.length()));

		return text;
	}

	
}
