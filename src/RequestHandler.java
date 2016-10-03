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
	}

	@Override
	public void run() {
		byte[] request = parseRequest();
		serv.respond(responseHeader, request);
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

		setResponseHeader(response == null);
		return response;
	}

	private void setResponseHeader(boolean responseIsNull) {
		if (responseIsNull)
			responseHeader = "HTTP/1.0 404 Not Found\r\n\r\n";
		else
			responseHeader = "HTTP/1.0 200 OK\r\n";
	}

}
