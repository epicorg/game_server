package connection_encryption;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static connection_encryption.StringConverter.bytesToString;

/**
 * @author Noris
 * @date 2015/03/30
 */
public class Encrypter {

    private byte[] encryptedData;
    private SecretKey symmetricKey;

    public Encrypter(SecretKey symmetricKey) {
        super();
        this.symmetricKey = symmetricKey;
    }

    /**
     * Encrypts a string using a symmetric key.
     *
     * @param unencryptedString the string to encrypt
     */
    public void encrypt(String unencryptedString) {
        byte[] unencryptedData = unencryptedString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance(EncryptionConst.SYMMETRIC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            encryptedData = cipher.doFinal(unencryptedData);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(EncryptionConst.SYMMETRIC_ALGORITHM + " algorithm not found!");
            e.printStackTrace();
        }
    }

    /**
     * @return the encrypted data.
     */
    public byte[] getEncryptedData() {
        return encryptedData;
    }

    /**
     * @return the encrypted data in string format.
     */
    public String getEncryptedString() {
        return bytesToString(encryptedData);
    }

}
