

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSADecoder {

	static BigInteger Public_M = new BigInteger("6443029814238090037785018308700578412002846194749319722182750606145164452277951398153779363417052063212035772845684432657992138898939912484992112136904917");
	static BigInteger Public_E = new BigInteger("65537");
	static String xform = "RSA/NONE/PKCS1PADDING";
	
	static String decode(String in) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		// decode
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(Public_M, Public_E);
	    KeyFactory fact = KeyFactory.getInstance("RSA", "BC");
		PublicKey pubKey = fact.generatePublic(keySpec);
	
		Cipher cipher = Cipher.getInstance(xform, "BC");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] cipherData = cipher.doFinal(Base64Coder.decode(in));
		
		String decodedText = new String(cipherData);
		
		return decodedText;

	}
}
