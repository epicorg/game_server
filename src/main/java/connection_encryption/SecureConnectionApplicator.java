package connection_encryption;

import messages.fields_names.EncryptFields;
import messages.fields_names.ServicesFields;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/04/25
 */
public class SecureConnectionApplicator {

    private ConnectionEncrypter connectionEncrypter = new ConnectionEncrypter();

    private static final boolean REFUSE_UNENCRYPTED_REQUEST = true;

    /**
     * Decrypts a request from the client. It doesn't decrypt the request if [a] the message is an encryption
     * establishment message (the first messages of any connection), in this case it saves the wrapped symmetric key,
     * or [b] the server is set to accept also the unencrypted requests or [c] the symmetric key is not set. In this
     * last case the server not read the request and returns an unknown service response.
     *
     * @param request the encrypted request
     * @return the decrypted request
     */
    public JSONObject decrypt(String request) throws JSONException {

        // If the request is not encrypted...
        if (isJSONObject(request)) {

            JSONObject jsonRequest = new JSONObject(request);

            // ...it can be an encryption establishment message...
            if (jsonRequest.getString(ServicesFields.SERVICE.toString())
                    .equals(ServicesFields.ENCRYPT.toString())) {

                if (jsonRequest.has(EncryptFields.WRAPPED_KEY.toString()))
                    connectionEncrypter.setSymmetricKey(jsonRequest
                            .getString(EncryptFields.WRAPPED_KEY.toString()));

                return jsonRequest;
            }

            // ...or a normal not encrypted message.
            if (REFUSE_UNENCRYPTED_REQUEST)
                return new JSONObject();

            return jsonRequest;
        }

        // If the request is encrypted but the asymmetric key wasn't set, I
        // return a JSNObject, that it cause an Unknown Service response.
        if (!connectionEncrypter.isSymmetricKeySet()) {
            return new JSONObject();
        }

        String unencryptedRequest = connectionEncrypter.decryptRequest(request);
        return new JSONObject(unencryptedRequest);

    }

    /**
     * Encrypts the response generated by the server. It doesn't encrypt the response if [a] the message is an
     * encryption establishment message or [b] the encryption is not enabled.
     *
     * @param jsonResponse the server response
     * @return the encrypted response
     */
    public String encrypt(JSONObject jsonResponse) throws JSONException {
        // If [a] the encryption is not enabled, or if [b] the response is an
        // encryption establishment message, the response is not encrypted.
        if ((jsonResponse.has(ServicesFields.SERVICE.toString()) && (jsonResponse
                .getString(ServicesFields.SERVICE.toString())
                .equals(ServicesFields.ENCRYPT.toString())))
                || !ConnectionEncrypter.isEncryptionEnabled()) {
            return jsonResponse.toString();
        }
        return connectionEncrypter.encryptResponse(jsonResponse.toString());
    }

    /*
     * Checks if the string is a valid JSONObject.
     */
    private boolean isJSONObject(String string) {
        try {
            new JSONObject(string);
        } catch (JSONException e1) {
            try {
                new JSONArray(string);
            } catch (JSONException e2) {
                return false;
            }
        }
        return true;
    }

}