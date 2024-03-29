package services;

import check_fields.RegisterChecker;
import data_management.RegisteredUser;
import data_management.UsersDataManager;
import exceptions.RegistrationFailedException;
import messages.fields_names.CommonFields;
import messages.fields_names.RegisterFields;
import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */
public class Register implements IService {

    private UsersDataManager dataManager;

    private JSONObject jsonRequest;
    private JSONObject jsonResponse;

    private RegisteredUser registeredUser;

    private RegisterChecker registerChecker;

    public Register() {
        dataManager = UsersDataManager.getInstance();
    }

    @Override
    public JSONObject start(JSONObject request) {
        this.jsonRequest = request;
        jsonResponse = new JSONObject();
        registerChecker = new RegisterChecker();
        readFields();
        checkFields();
        if (registerChecker.noErrors())
            saveFields();
        generateResponse();
        return jsonResponse;

    }

    private void readFields() {
        try {
            String username = jsonRequest.getString(CommonFields.USERNAME.toString());
            String password = jsonRequest.getString(CommonFields.PASSWORD.toString());
            String email = jsonRequest.getString(RegisterFields.EMAIL.toString());
            registeredUser = new RegisteredUser(username, password, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkFields() {
        registerChecker.checkUsername(registeredUser.getUsername());
        registerChecker.checkPassword(registeredUser.getPassword());
        registerChecker.checkEmail(registeredUser.getEmail());
        if (registerChecker.noErrors()) {
            registerChecker.checkAlreadyUsedUsername(registeredUser.getUsername());
            registerChecker.checkAlreadyUsedEmail(registeredUser.getEmail());
        }
    }

    private void saveFields() {
        try {
            dataManager.saveRegistrationFields(registeredUser);
        } catch (RegistrationFailedException e) {
            try {
                jsonResponse.put(CommonFields.SERVER_ERROR.toString(), true);
                registerChecker.addError();
            } catch (JSONException e1) {
                e.printStackTrace();
            }
        }
    }

    private void generateResponse() {
        try {
            jsonResponse.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
            jsonResponse.put(CommonFields.NO_ERRORS.toString(), registerChecker.noErrors());
            jsonResponse.put(CommonFields.ERRORS.toString(), registerChecker.getErrors());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return ServicesFields.REGISTER.toString();
    }

}
