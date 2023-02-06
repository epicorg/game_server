package services.encrypt;

import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IExtendedService;
import services.IService;

import java.util.HashMap;

/**
 * @author Noris
 * @date 2015/04/25
 */
public class Encrypt implements IExtendedService {

    private HashMap<String, IService> subServices;

    public Encrypt() {
        subServices = new HashMap<>();
    }

    @Override
    public JSONObject start(JSONObject jsonRequest) {
        String serviceType = null;
        try {
            serviceType = jsonRequest.getString(ServicesFields.SERVICE_TYPE.toString());

            // TODO DEBUG: service name
            // System.out.println("Service: " + serviceType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IService subService = subServices.get(serviceType);
        if (subService == null)
            return null;
        return subService.start(jsonRequest);
    }

    @Override
    public void addSubService(IService... subServices) {
        for (IService service : subServices)
            this.subServices.put(service.getName(), service);
    }

    @Override
    public String getName() {
        return ServicesFields.ENCRYPT.toString();
    }

}
