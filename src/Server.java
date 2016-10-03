import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket mainSocket;
	Socket clientSocket;

	public Server() {
		initialize();
	}

	private void initialize() {
		try {
			System.out.println("Attempting to connect to Socket...");
			mainSocket = new ServerSocket(2500);
			System.out.println("Connection Made.");
		} catch (IOException e) {
			System.out.println("Error: Unable to connect to socket.");
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			while (true) {
				clientSocket = mainSocket.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String currentRequest = input.readLine();
				System.out.println(currentRequest);
				if (currentRequest != null)
					new Thread(new RequestHandler(currentRequest, this)).run();
			}
		} catch (IOException e) {
			System.out.println("Error: Problem reading request.");
			e.printStackTrace();
		}
		closeSocket();
	}

	public synchronized void respond(String headResponse, byte[] response) {
		try {
			DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println(headResponse);
			dout.writeBytes(headResponse);
			if (!headResponse.contains("404")) {
				dout.writeBytes("Content-Length: " + response.length + "\r\n\r\n");
				dout.write(response);
			}
			dout.flush();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Could not connect to socket.");
			e.printStackTrace();
		}

	}

	//Currently unnecessary method used only if loop is broken.
	private void closeSocket() {
		try {
			System.out.println("Attempting to close socket...");
			mainSocket.close();
			System.out.println("Sockets sucessfully closed. Thank you");
		} catch (IOException e) {
			System.out.println("Error: Unable to close sockets");
			e.printStackTrace();
		}

	}
}
