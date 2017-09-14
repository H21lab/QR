import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSAKeyGenerator {
	
	static String xform = "RSA/NONE/PKCS1PADDING";
		
	public static void main(String[] args) {
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		// generate key
				
				BigInteger Public_M = null;
				BigInteger Public_E = null;
				BigInteger Private_M = null;
				BigInteger Private_E = null;
				
				String encodedText = null;
				String decodedText = null;
				
				
				// generate key
				KeyPairGenerator kpg;
				try {
					kpg = KeyPairGenerator.getInstance("RSA", "BC");
					kpg.initialize(512);
					KeyPair kp = kpg.genKeyPair();
					
					KeyFactory fact = KeyFactory.getInstance("RSA", "BC");
					RSAPublicKeySpec pub = (RSAPublicKeySpec)fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
					RSAPrivateKeySpec priv = (RSAPrivateKeySpec)fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

					Public_M = pub.getModulus();
					Public_E = pub.getPublicExponent();
					
					Private_M = priv.getModulus();
					Private_E = priv.getPrivateExponent();
				
					System.out.println("Public_M = " + Public_M.toString());
					System.out.println("Public_E = " + Public_E.toString());
					System.out.println("Private_M = " + Private_M.toString());
					System.out.println("Private_E = " + Private_E.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// encode
				try {
					RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(Private_M, Private_E);
				    KeyFactory fact;

				    fact = KeyFactory.getInstance("RSA", "BC");
					PrivateKey privKey = fact.generatePrivate(keySpec);
				
					
					Cipher cipher = Cipher.getInstance(xform, "BC");
					cipher.init(Cipher.ENCRYPT_MODE, privKey);
					byte[] cipherData = cipher.doFinal("Test".getBytes());
					
					encodedText = new String(Base64Coder.encode(cipherData));
					
					System.out.println("Encrypted text = " + encodedText);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				// decode
				try {
					RSAPublicKeySpec keySpec = new RSAPublicKeySpec(Public_M, Public_E);
				    KeyFactory fact;

				    fact = KeyFactory.getInstance("RSA", "BC");
					PublicKey pubKey = fact.generatePublic(keySpec);
				
					
					Cipher cipher = Cipher.getInstance(xform, "BC");
					cipher.init(Cipher.DECRYPT_MODE, pubKey);
					byte[] cipherData = cipher.doFinal(Base64Coder.decode(encodedText));
					
					decodedText = new String(cipherData);
					
					System.out.println("Decrypted text = " + decodedText);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	
	
}
