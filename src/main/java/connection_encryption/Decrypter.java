package connection_encryption;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Noris
 * @date 2015/03/30
 */
public class Decrypter {

    private SecretKey symmetricKey;
    private byte[] decryptedData;

    public Decrypter(SecretKey symmetricKey) {
        this.symmetricKey = symmetricKey;
    }

    /**
     * Decrypts a string using the symmetric key (obviously the key must be the
     * same used to encrypt the string).
     *
     * @param encryptedString the encrypted string
     */
    public void decrypt(String encryptedString) {
        byte[] encryptedData = StringConverter.stringToBytes(encryptedString);
        try {
            Cipher cipher = Cipher.getInstance(EncryptionConst.SYMMETRIC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            decryptedData = cipher.doFinal(encryptedData);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(EncryptionConst.SYMMETRIC_ALGORITHM + " algorithm not found!");
            e.printStackTrace();
        }
    }

    /**
     * @return the decrypted data
     */
    public byte[] getDecryptedData() {
        return decryptedData;
    }

    /**
     * @return the decrypted data in string format
     */
    public String getDecryptedString() {
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

}
