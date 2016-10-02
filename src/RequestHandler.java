import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler implements Runnable {
	String request;
	Server serv;
	String responseHeader;

	public RequestHandler(String request, Server serv) {
		this.request = request;
		this.serv = serv;
		responseHeader = "HTTP/1.0 200 OK\r\n";
	}

	@Override
	public void run() {
		serv.respond(responseHeader, parseRequest());
	}

	private byte[] parseRequest() {
		return getResponse(request.substring(request.indexOf(" "), request.lastIndexOf(" ")).trim());
	}

	private byte[] getResponse(String pageRequested) {
		byte[] response = null; 
		try {
			response = Files.readAllBytes(Paths.get("src/My Website" + pageRequested));			
		} catch (IOException e) {
			responseHeader = "HTTP/1.0 404 Not Found\r\n\r\n";
		}
			
		return response;
	}

}
