import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket mainSocket;
	Socket clientSocket;
	
	public Server(){
		initialize();
	}
	private void initialize(){
		try {
			System.out.println("Attempting to connect to Socket...");
			mainSocket = new ServerSocket(2500);		
		} catch (IOException e) {
			System.out.println("Error: Unable to connect to socket.");
			e.printStackTrace();
		}
	}
	
	public void listen() {
		try {
			while(true){
				clientSocket = mainSocket.accept();
				BufferedReader input = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
				String currentRequest = input.readLine();
				System.out.println(currentRequest);
				if(currentRequest != null)
				new Thread(new RequestHandler(currentRequest, this)).run();
			}
		} catch (IOException e) {
			System.out.println("Error: Problem reading request.");
			e.printStackTrace();
		}
		closeSockets();
	}
	
	public synchronized void respond(String responseString){
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			out.println(responseString);
			out.flush();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Could not connect to socket.");
			e.printStackTrace();
		}
		
	}
	
	private void closeSockets(){
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
