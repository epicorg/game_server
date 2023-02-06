package check_fields;

import messages.fields_names.CommonFields;
import messages.fields_names.ServicesFields;
import online_management.OnlineManager;
import online_management.OnlineUser;
import org.json.JSONException;
import org.json.JSONObject;
import services.IService;
import services.Login;

import java.util.ArrayList;

/**
 * A common request checker for all {@link IService}. Before elaborating a
 * request use this class to check if the user is authorized to enjoy a server
 * service. The user must be logged and the provided hashCode must match with
 * the one generated by the server in the login.
 *
 * @author Micieli
 * @date 2015/05/21
 * @see ClientIdentityChecker
 * @see OnlineManager
 * @see OnlineUser
 * @see Login
 */
public class RequestFieldChecker {

    private ClientIdentityChecker checker;
    private static ArrayList<String> serviceNotToBeChecked = new ArrayList<>();

    public RequestFieldChecker() {
        super();
        checker = new ClientIdentityChecker();
    }

    /**
     * Checks if a request satisfy the requirements.
     *
     * @param request the request to check
     * @return true if the request is valid, false otherwise
     */
    public boolean checkRequest(JSONObject request) {
        try {
            String serviceName = request.getString(ServicesFields.SERVICE.toString());
            if (serviceNotToBeChecked.contains(serviceName)) {
                return true;
            } else {
                String username = request.getString(CommonFields.USERNAME.toString());
                int hashCode = request.getInt(CommonFields.HASHCODE.toString());
                return checker.isUserOnline(username) && checker.checkHashCode(username, hashCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Excludes same services from the check.
     *
     * @param serviceNotToBeChecked a list of service name that doesn't have to be checked
     */
    public static void setServiceNotToBeChecked(ArrayList<String> serviceNotToBeChecked) {
        RequestFieldChecker.serviceNotToBeChecked = serviceNotToBeChecked;
    }

}
