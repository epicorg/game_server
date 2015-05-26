package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.Login;
import check_fields.RequestFieldChecher;
import connection_encryption.SecureConnectionApplicator;
import fields_name.FieldsNames;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class ClientRequestThread implements Runnable {

	private Socket socket;
	private SecureConnectionApplicator secureConnection;
	private ServiceChooser serviceChooser;
	private RequestFieldChecher cecker;
	private PrintWriter out;

	public ClientRequestThread(Socket socket) {
		super();
		this.socket = socket;
		secureConnection = new SecureConnectionApplicator();
		serviceChooser = new ServiceChooser();
		cecker = new RequestFieldChecher();
	}

	@Override
	public void run() {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			String request = in.readLine();

			while (!socket.isClosed()) {
				if (request == null)
					return;

				// TODO DEBUG: client request
				//System.out.println("CLIENT: " + request);

				JSONObject jsonEncryptedRequest = new JSONObject(request);
				JSONObject jsonRequest = secureConnection.decrypt(jsonEncryptedRequest);
				JSONObject jResponse = null;
				
				if(cecker.checkRequest(jsonRequest)){
					jResponse = elaborateRequest(jsonRequest);
				}
				
				if (jResponse != null) {
					String response = secureConnection.encrypt(jResponse).toString();

					out.println(response);

					// TODO DEBUG: server response
					//System.out.println("SERVER: " + response);
				} else {
					System.out.println("SERVER: " + "No response.");
				}

				request = in.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private JSONObject elaborateRequest(JSONObject jsonRequest)
			throws JSONException {
		
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
				.getRemoteSocketAddress();
		
		jsonRequest.put(FieldsNames.IP_ADDRESS, inetSocketAddress.getHostName());
		jsonRequest.put(FieldsNames.LOCAL_PORT, socket.getLocalPort());

		IService service;
		if (jsonRequest.getString(FieldsNames.SERVICE).equals(FieldsNames.LOGIN)) {
			service = new Login(out);
		} else {
			service = serviceChooser.chooseService(jsonRequest);
		}

		JSONObject jResponse = service.start(jsonRequest);
		return jResponse;
	}

}
