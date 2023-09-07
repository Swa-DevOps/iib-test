package com.iib.platform.services;

import org.osgi.annotation.versioning.ConsumerType;

import com.google.gson.JsonObject;

@ConsumerType
public interface EncryptDecryptService {

	public String fetchValue(JsonObject json, String variable);

	public String encrypt(String key, String initVector, String value);

	public String decrypt(String key, String initVector, String encrypted);

}
