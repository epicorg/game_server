package encryption_tests;

import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.KeyUnwrapper;
import connection_encryption.StringConverter;

import javax.crypto.*;
import java.security.*;

/**
 * Tries to unwrap a wrapped symmetric key with an invalid asymmetric key.
 *
 * @author Noris
 * @date 2015/05/31
 */
class Test08 {

    public static void main(String[] args) {

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

        AsymmetricKeysGenerator asymmetricKeysGenerator1 = new AsymmetricKeysGenerator();
        asymmetricKeysGenerator1.generateKeys();
        PublicKey publicKey1 = asymmetricKeysGenerator1.getPublicKey();

        byte[] wrappedKey = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.WRAP_MODE, publicKey1);
            wrappedKey = cipher.wrap(symmetricKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        assert wrappedKey != null;
        System.out.println("> WRAPPED KEY..........: " + new String(wrappedKey) + "\n");

        String wrappedKeyString = StringConverter.bytesToString(wrappedKey);

        System.out.println("> WRAPPED KEY STR......: " + wrappedKeyString + "\n");

        byte[] newWrappedKey = StringConverter.stringToBytes(wrappedKeyString);

        System.out.println("> NEW WRAPPED KEY......: " + new String(newWrappedKey) + "\n");

        AsymmetricKeysGenerator asymmetricKeysGenerator2 = new AsymmetricKeysGenerator();
        asymmetricKeysGenerator2.generateKeys();
        PrivateKey privateKey2 = asymmetricKeysGenerator2.getPrivateKey();

        KeyUnwrapper keyUnwrapper = new KeyUnwrapper(privateKey2);
        keyUnwrapper.unwrapKey(wrappedKeyString);
        SecretKey newSymmetricKey = keyUnwrapper.getUnwrappedKey();

        System.out.println("> NEW SYMMETRIC KEY....: " + newSymmetricKey);
        System.out.println("  " + newSymmetricKey.getAlgorithm() + "|"
                + newSymmetricKey.getFormat() + "|"
                + newSymmetricKey.hashCode());

    }

}
