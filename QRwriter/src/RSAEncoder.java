import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSAEncoder {

	static BigInteger Private_M = new BigInteger("6443029814238090037785018308700578412002846194749319722182750606145164452277951398153779363417052063212035772845684432657992138898939912484992112136904917");
	static BigInteger Private_E = new BigInteger("3679550833140056443447164322193418068731864654526194511176876220890446359908870904694431361197612627882237301251302253435245305318423390261411669770598965");
	static String xform = "RSA/NONE/PKCS1PADDING";
	
	static String encode(String in) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		// encode
	
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(Private_M, Private_E);
	    KeyFactory fact;
	
	    fact = KeyFactory.getInstance("RSA", "BC");
		PrivateKey privKey = fact.generatePrivate(keySpec);
	
		
		Cipher cipher = Cipher.getInstance(xform, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, privKey);
		byte[] cipherData = cipher.doFinal(in.getBytes());
		
		String encodedText = new String(Base64Coder.encode(cipherData));
		
		return encodedText;
	}
}
