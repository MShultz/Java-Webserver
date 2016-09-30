import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RequestHandler implements Runnable {
	String request;
	Server serv;

	public RequestHandler(String request, Server serv) {
		this.request = request;
		this.serv = serv;
	}

	@Override
	public void run() {
		serv.respond(parseRequest());
	}

	private String parseRequest() {
		return getResponse(request.substring(request.indexOf(" "), request.lastIndexOf(" ")).trim());
	}

	private String getResponse(String pageRequested) {
		String response = "HTTP/1.0 200 OK\r\n\r\n";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/My Website" + pageRequested));
			while (reader.ready()) {
				response += reader.readLine() + "\n";
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: File Not Found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: Something went wrong.");
			e.printStackTrace();
		}
		System.out.println(response);
		return response;
	}

}
