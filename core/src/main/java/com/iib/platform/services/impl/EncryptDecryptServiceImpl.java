package com.iib.platform.services.impl;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.jcr.Session;

import org.apache.commons.codec.binary.Base64;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.iib.platform.services.EncryptDecryptService;

/**
 * Encrypt Decrypt Service Implementation
 *
 * @author niket goel
 *
 */
@Component(immediate = true, service = { EncryptDecryptService.class }, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Encrypt Decrypt Service Implementation" })
public class EncryptDecryptServiceImpl implements EncryptDecryptService {

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(EncryptDecryptServiceImpl.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	ResourceResolver resourceResolver;

	Session session;

	@Activate
	protected void activate() {
		log.info("Activated EncryptDecryptServiceImpl");

	}

	@Override
	public String fetchValue(JsonObject json, String variable)

	{
		String value = "";
	
			if (null != json.get(variable) && (json.has(variable)) )
				value = json.get(variable).getAsString();

		return value;
	}

	@Override
	public String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			log.error("Exception", ex);
		}

		return null;
	}

	@Override
	public String decrypt(String key, String initVector, String encrypted) {
		try {

			IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			log.error("Exception", ex);
		}

		return null;
	}

}
