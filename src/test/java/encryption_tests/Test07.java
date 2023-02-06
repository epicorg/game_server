package encryption_tests;

import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.KeyUnwrapper;
import connection_encryption.StringConverter;
import org.junit.Test;

import javax.crypto.*;
import java.security.*;

import static org.junit.Assert.assertEquals;

/**
 * Wraps a symmetric key with a public key, then unwrap the symmetric key with the public key (and check, with the
 * hash-code, if the keys are equal).
 *
 * @author Noris
 * @date 2015/05/31
 */
public class Test07 {

    @Test
    public void test() {

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecureRandom secureRandom = new SecureRandom();

        assert keyGenerator != null;
        keyGenerator.init(secureRandom);
        SecretKey symmetricKey = keyGenerator.generateKey();

        System.out.println("> SYMMETRIC KEY........: " + symmetricKey);
        System.out.println("  " + symmetricKey.getAlgorithm() + "|"
                + symmetricKey.getFormat() + "|" + symmetricKey.hashCode()
                + "\n");

        AsymmetricKeysGenerator asymmetricKeysGenerator = new AsymmetricKeysGenerator();
        asymmetricKeysGenerator.generateKeys();
        PublicKey publicKey = asymmetricKeysGenerator.getPublicKey();
        PrivateKey privateKey = asymmetricKeysGenerator.getPrivateKey();

        byte[] wrappedKey = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.WRAP_MODE, publicKey);
            wrappedKey = cipher.wrap(symmetricKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        assert wrappedKey != null;
        System.out.println("> WRAPPED KEY..........: " + new String(wrappedKey) + "\n");

        String wrappedKeyString = StringConverter.bytesToString(wrappedKey);

        System.out.println("> WRAPPED KEY (STR)....: " + wrappedKeyString + "\n");

        byte[] newWrappedKey = StringConverter.stringToBytes(wrappedKeyString);

        System.out.println("> NEW WRAPPED KEY......: " + new String(newWrappedKey) + "\n");

        assertEquals(new String(wrappedKey), new String(newWrappedKey));

        KeyUnwrapper keyUnwrapper = new KeyUnwrapper(privateKey);
        keyUnwrapper.unwrapKey(wrappedKeyString);
        SecretKey newSymmetricKey = keyUnwrapper.getUnwrappedKey();

        System.out.println("> NEW SYMMETRIC KEY....: " + newSymmetricKey);
        System.out.println("  " + newSymmetricKey.getAlgorithm() + "|"
                + newSymmetricKey.getFormat() + "|"
                + newSymmetricKey.hashCode());

        assertEquals(symmetricKey.hashCode(), newSymmetricKey.hashCode());
    }

}
