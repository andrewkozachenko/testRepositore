package com.academysmart.bookagolf.encryption;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jruby.ext.openssl.OpenSSLImpl;

import com.googlecode.jinahya.rfc4648.Base64;

public class AESEncrypter {

	private final Base64 base64 = new Base64();
	private static final String CHARSET = "UTF8";
	// private static final byte[] SALT = { (byte) 0x00, (byte) 0x00, (byte)
	// 0x00,
	// (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	private static final int ITERATION_COUNT = 2048;
	private static final int KEY_LENGTH = 256;
	// private static final int KEY_LENGTH = 128;
	private Cipher ecipher;
	private Cipher dcipher;

	public AESEncrypter(String passPhrase) throws Exception {
		// SecretKeyFactory factory = SecretKeyFactory
		// .getInstance("PBKDF2WithHmacSHA1");
		// SecretKeyFactory factory = SecretKeyFactory
		// .getInstance("AES");
		// KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), SALT,
		// ITERATION_COUNT, KEY_LENGTH);
		// KeySpec spec = new PBEKeySpec(passPhrase.toCharArray());
		// SecretKey tmp = factory.generateSecret(spec);
		// SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		// SecretKey secret = new SecretKeySpec(passPhrase.getBytes(CHARSET),
		// "AES");
		// SecretKey secret = (SecretKey) buildKey(passPhrase);
		OpenSSLImpl.KeyAndIv keyAndIv = OpenSSLImpl.EVP_BytesToKey(
				KEY_LENGTH / 8, 16, MessageDigest.getInstance("MD5"), null,
				passPhrase.getBytes("UTF-8"), ITERATION_COUNT);
		initCiphers(keyAndIv);
	}

	// public AESEncrypter(String passPhrase, byte[] iv) throws Exception {
	// // SecretKeyFactory factory = SecretKeyFactory
	// // .getInstance("PBKDF2WithHmacSHA1");
	// SecretKeyFactory factory = SecretKeyFactory
	// .getInstance("PBEWithMD5AndDES");
	//
	// KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), SALT,
	// ITERATION_COUNT, KEY_LENGTH);
	// SecretKey tmp = factory.generateSecret(spec);
	// SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	// initCiphers(secretKey, iv);
	// }

	// public AESEncrypter(SecretKey secretKey) throws GeneralSecurityException,
	// UnsupportedEncodingException {
	// initCiphers(secretKey);
	// }
	//
	// private void initCiphers(SecretKey secretKey)
	// throws GeneralSecurityException, UnsupportedEncodingException {
	//
	// ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	// ecipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new
	// byte[16]));
	// // ecipher.init(Cipher.ENCRYPT_MODE, secretKey, new
	// IvParameterSpec(result1.getIv()));
	//
	// dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	// byte[] iv = ecipher.getParameters()
	// .getParameterSpec(IvParameterSpec.class).getIV();
	// dcipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	// }

	private void initCiphers(OpenSSLImpl.KeyAndIv keyAndIv) throws Exception {

		Security.addProvider(new BouncyCastleProvider());
		SecretKey secretKey = new SecretKeySpec(keyAndIv.getKey(), "AES");
		ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
		ecipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(
				keyAndIv.getIv()));
		dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
		byte[] iv = ecipher.getParameters()
				.getParameterSpec(IvParameterSpec.class).getIV();
		dcipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	}

	// private void initCiphers(SecretKey secretKey, byte[] iv)
	// throws GeneralSecurityException {
	// ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	// ecipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	//
	// dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	// dcipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	// }

	public byte[] getIV() throws GeneralSecurityException {
		return ecipher.getParameters().getParameterSpec(IvParameterSpec.class)
				.getIV();
	}

	public String encrypt(String encrypt) throws Exception {
		byte[] bytes = encrypt.getBytes(CHARSET);
		byte[] encrypted = encrypt(bytes);
		// return Base64.encodeBase64String(encrypted);
		return new String(base64.encode(encrypted), CHARSET);
	}

	public byte[] encrypt(byte[] plain) throws Exception {
		return ecipher.doFinal(plain);
	}

	public String decrypt(String encrypt) throws Exception {
		// byte[] bytes = Base64.decodeBase64(encrypt);
		byte[] bytes = base64.decode(encrypt.getBytes(CHARSET));
		byte[] decrypted = decrypt(bytes);
		return new String(decrypted, CHARSET);
	}

	public byte[] decrypt(byte[] encrypt) throws Exception {
		return dcipher.doFinal(encrypt);
	}

	// public byte[] decodeHex(String encoded) {
	// byte[] decoded = new BigInteger(encoded, 16).toByteArray();
	// if (decoded[0] == 0) {
	// final byte[] tmp = new byte[decoded.length - 1];
	// System.arraycopy(decoded, 1, tmp, 0, tmp.length);
	// decoded = tmp;
	// }
	// return decoded;
	// }
	// private Key buildKey(String password) throws NoSuchAlgorithmException,
	// UnsupportedEncodingException, DecoderException {
	// // MessageDigest digester = MessageDigest.getInstance("MD5");
	// // byte[] encodedKey = Base64.decodeBase64(password);
	// // System.out.println("encoded key:"+encodedKey);
	// // MessageDigest digester = MessageDigest.getInstance("SHA-256");
	// // digester.update(password.getBytes("UTF-8"));
	// // byte[] key = digester.digest();
	//
	// byte[] key =
	// Hex.encodeHexString(password.getBytes(CHARSET)).getBytes(CHARSET);
	// System.out.println("digest: " + new String(key));
	// SecretKeySpec spec = new SecretKeySpec(key, "AES");
	// return spec;
	// }
	public static void main(String[] args) throws Exception {
		AESEncrypter encrypter = new AESEncrypter("3xhltql7");
		String encrypted = encrypter.encrypt("admin123");
		System.out.println(encrypted);
		String decrypted = encrypter.decrypt("y4lwmnw69sCa7AClWJoPJA==");
		System.out.println(decrypted);
	}
}