package connection_encryption;

import javax.crypto.SecretKey;

/**
 * Encrypts and decrypt the data encrypted with a symmetricKey. This key can be
 * set from a package wrapped with the public key (unwrapping it with the server
 * private key).
 *
 * @author Noris
 * @date 2015/04/25
 * @see KeysGenerator
 * @see KeyUnwrapper
 * @see Encrypter
 * @see Decrypter
 */
public class ConnectionEncrypter {

    private static KeysGenerator keysGenerator;
    private static boolean enabled = false;

    private Encrypter encrypter;
    private Decrypter decrypter;
    private boolean symmetricKeySet = false;

    /**
     * Set the asymmetric keys generator and enables the encryption.
     *
     * @param keysGenerator an asymmetric keys generator
     */
    public static void setKeysGenerator(KeysGenerator keysGenerator) {
        ConnectionEncrypter.keysGenerator = keysGenerator;
        enabled = true;
    }

    /**
     * @return the public key generated by the {@link KeysGenerator} in a string format (that can be easily sent to the client).
     */
    public static String getPublicKey() {
        return KeyConverter.keyToString(keysGenerator.getPublicKey());
    }

    /**
     * @return true if the encryption is enabled, false otherwise
     */
    public static boolean isEncryptionEnabled() {
        return enabled;
    }

    /**
     * Set the asymmetric key from a package wrapped with the public key.
     * Decrypts the wrapped data with the private key.
     *
     * @param wrappedSymmetricKey the symmetric key wrapped into a package encrypted with the public key
     */
    public void setSymmetricKey(String wrappedSymmetricKey) {
        KeyUnwrapper keyUnwrapper = new KeyUnwrapper(keysGenerator.getPrivateKey());
        keyUnwrapper.unwrapKey(wrappedSymmetricKey);
        SecretKey symmetricKey = keyUnwrapper.getUnwrappedKey();
        encrypter = new Encrypter(symmetricKey);
        decrypter = new Decrypter(symmetricKey);
        symmetricKeySet = true;
    }

    /**
     * @return true if the symmetric key is set, false otherwise
     */
    public boolean isSymmetricKeySet() {
        return symmetricKeySet;
    }

    /**
     * @param response the string to encrypt
     * @return the encrypted string
     */
    public String encryptResponse(String response) {
        encrypter.encrypt(response);
        return encrypter.getEncryptedString();
    }

    /**
     * @param request the string to decrypt
     * @return the decrypted string
     */
    public String decryptRequest(String request) {
        decrypter.decrypt(request);
        return decrypter.getDecryptedString();
    }

}
