package connection_encryption;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * Unwraps a symmetric key encrypted with a public asymmetric key.
 *
 * @author Noris
 * @date 2015/03/30
 */
public class KeyUnwrapper {

    private PrivateKey privateKey;
    private SecretKey symmetricKey;

    public KeyUnwrapper(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Unwraps a symmetric key encrypted with a public asymmetric key, using the
     * corresponding private key.
     *
     * @param wrappedKeyString the wrapped symmetric key in string format.
     */
    public void unwrapKey(String wrappedKeyString) {
        byte[] wrappedKeyData = StringConverter.stringToBytes(wrappedKeyString);
        try {
            Cipher cipher = Cipher.getInstance(EncryptionConst.ASYMMETRIC_DECODE);
            cipher.init(Cipher.UNWRAP_MODE, privateKey);
            symmetricKey = (SecretKey) cipher.unwrap(wrappedKeyData, EncryptionConst.SYMMETRIC_ALGORITHM, Cipher.SECRET_KEY);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the symmetric key
     */
    public SecretKey getUnwrappedKey() {
        return symmetricKey;
    }

}
