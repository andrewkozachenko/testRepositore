package com.academysmart.bookagolf.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.jruby.ext.openssl.OpenSSLImpl;

import com.googlecode.jinahya.rfc4648.Base64;

/**
 * Provides data encryption using BouncyCastleProvider 
 *
 */
public class BCAESEncrypter {
	byte[] key;
	byte[] iv;
	private static final String CHARSET = "UTF-8";
	private static final int ITERATION_COUNT = 2048;
	private static final int KEY_LENGTH = 256;

	public BCAESEncrypter(String passPhrase) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		OpenSSLImpl.KeyAndIv keyAndIv = OpenSSLImpl.EVP_BytesToKey(
				KEY_LENGTH / 8, 16, MessageDigest.getInstance("MD5"), null,
				passPhrase.getBytes("UTF-8"), ITERATION_COUNT);
		key = keyAndIv.getKey();
		iv = keyAndIv.getIv();
		// initCiphers(keyAndIv);
	}

	private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data)
			throws DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		int minSize = cipher.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
		@SuppressWarnings("unused")
		int length2 = cipher.doFinal(outBuf, length1);
		// int actualLength = length1 + length2;

		// byte[] result = new byte[actualLength];
		return outBuf;
	}

	private byte[] decrypt(byte[] cipher, byte[] key, byte[] iv)
			throws DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
				iv);
		aes.init(false, ivAndKey);
		return cipherData(aes, cipher);
	}

	private byte[] encrypt(byte[] plain, byte[] key, byte[] iv)
			throws DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
				iv);
		aes.init(true, ivAndKey);
		return cipherData(aes, plain);
	}

	public String encrypt(String plainText) throws DataLengthException,
			UnsupportedEncodingException, IllegalStateException,
			InvalidCipherTextException, IOException {
		Base64 base64 = new Base64();
		return new String(base64.encode(encrypt(plainText.getBytes("UTF-8"),
				key, iv)), CHARSET);
	}

	public String decrypt(String encodedText) throws DataLengthException,
			UnsupportedEncodingException, IllegalStateException,
			InvalidCipherTextException, IOException {
		Base64 base64 = new Base64();
		return new String(decrypt(base64.decode(encodedText.getBytes()), key,
				iv), CHARSET);
	}
}
