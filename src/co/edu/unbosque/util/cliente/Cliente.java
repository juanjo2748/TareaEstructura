import java.net.*;
import java.io.*;

public class Cliente {
	public static void name(String[] args) {
		try {
			Socket socket = new Socket("localhost", 8000);

			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String serverMessage = in.readLine();
			System.out.println("Servidor" + serverMessage);

			while (true) {
				String userInput = consoleReader.readLine();
				out.println(userInput);
				if (userInput.equalsIgnoreCase("salir")) {
					break;
				}
				String respuesta = in.readLine();
				System.out.println(respuesta);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
