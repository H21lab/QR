package testpackage.qrreader;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DESDecoder {

	static String k1 = ((new StringBuilder()).append("A").append("T").append("J").append("o").append("a").append("G").append("N").append("t").append("V")
			.append("m").append("t").append("J").append("R").append("k").append("5").append("s").append("W").append("T").append("N").append("K")).toString();
	static String k2 = ((new StringBuilder()).append("b").append("G").append("R").append("B").append("P").append("T").append("1").append("V").append("M")
			.append("m").append("h").append("o").append("Y").append("2").append("1").append("W").append("a").append("0").append("l").append("G")).toString();
	static String k3 = ((new StringBuilder()).append("T").append("m").append("x").append("Z").append("M").append("0").append("p").append("s").append("Z")
			.append("E").append("E").append("9").append("P").append("V").append("U").append("y").append("a").append("G").append("h").append("j")).toString();
	static String k4 = ((new StringBuilder()).append("b").append("V").append("Z").append("r").append("S").append("U").append("Z").append("O").append("b")
			.append("F").append("k").append("z").append("S").append("m").append("x").append("k").append("Q").append("T").append("0").append("9")).toString();;

	static String K = k1 + k2 + k3 + k4;

	static String decode(String in) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

		String CIPHER_TEXT = in;

		String algorithm = "DESede";
		String transformation = "DESede/CBC/PKCS5Padding";

		byte[] keyValue = Base64Coder.decode(K.toCharArray());

		DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

		// Initialization Vector of 8 bytes set to zero.
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);

		SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
		// Cipher encrypter = Cipher.getInstance(transformation);
		// encrypter.init(Cipher.ENCRYPT_MODE, key, iv);
		// byte[] input = PLAIN_TEXT.getBytes("UTF-8");
		// byte[] encrypted = encrypter.doFinal(input);
		// CIPHER_TEXT = new String(Base64Coder.encode(encrypted));
		// System.out.println("ENCODED = " + CIPHER_TEXT);

		Cipher decrypter = Cipher.getInstance(transformation);
		decrypter.init(Cipher.DECRYPT_MODE, key, iv);

		byte[] decrypted = decrypter.doFinal(Base64Coder.decode(CIPHER_TEXT.toString()));

		String decodedText = new String(decrypted, "UTF-8");

		return decodedText;

	}
}
