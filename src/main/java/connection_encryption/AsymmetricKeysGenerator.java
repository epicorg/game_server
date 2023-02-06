package connection_encryption;

import java.security.*;

/**
 * Generates asymmetric keys using a {@link KeyPairGenerator}.
 *
 * @author Noris
 * @date 2015/03/30
 * @see KeyPairGenerator
 */
public class AsymmetricKeysGenerator implements KeysGenerator {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public void generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EncryptionConst.ASYMMETRIC_ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            keyPairGenerator.initialize(EncryptionConst.ASYMMETRIC_KEYSIZE, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(EncryptionConst.ASYMMETRIC_ALGORITHM + " algorithm not found!");
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

}
