package registration_tests;

import messages.fields_names.CommonFields;
import messages.fields_names.RegisterFields;
import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import server.ServerInitializer;
import services.Register;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for client register message without errors. Note: I can check only if the expected fields are wrapped into the
 * JSONObject, and not their order, because "An object is an unordered set of name/value pairs" (from the
 * <a href="http://www.json.org/">JSON</a> specifications).
 *
 * @author Noris
 * @date 2015/03/31
 */
public class Test01 {

    @Test
    public void test() throws JSONException {

        // Initialize the server
        new ServerInitializer().init();

        JSONObject jsonFromClient = new JSONObject();
        jsonFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
        jsonFromClient.put(CommonFields.USERNAME.toString(), "Schopenhauer");
        jsonFromClient.put(CommonFields.PASSWORD.toString(), "DieWeltAlsWilleUndL0L");
        jsonFromClient.put(RegisterFields.EMAIL.toString(), "arthur@paralipomena.org");

        Register register = new Register();
        String response = register.start(jsonFromClient).toString();

        /*
         * Console print "fake" control: it can not work because "An object is
         * an unordered set of name/value pairs".
         */
        String expectedResponse = "{\"" + ServicesFields.SERVICE + "\":\""
                + ServicesFields.REGISTER + "\",\""
                + CommonFields.NO_ERRORS + "\":" + "true}";

        // assertEquals(expectedResponse, response);
        System.out.println(expectedResponse + "\n" + response);

        /*
         * The "real" JUnit test: I check only if the expected fields are
         * wrapped into the JSONObject.
         */
        JSONObject jsonResponse = new JSONObject(response);

        String serviceField;
        if (jsonResponse.has(ServicesFields.SERVICE.toString()))
            serviceField = jsonResponse.getString(ServicesFields.SERVICE.toString());
        else
            serviceField = null;

        assertEquals(ServicesFields.REGISTER.toString(), serviceField);

        Boolean noErrorsField;
        if (jsonResponse.has(CommonFields.NO_ERRORS.toString()))
            noErrorsField = (Boolean) jsonResponse.get(CommonFields.NO_ERRORS.toString());
        else
            noErrorsField = false;

        assertTrue(noErrorsField);
    }

}
