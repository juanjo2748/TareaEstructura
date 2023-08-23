import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor{
  private static Map <String,String> responses = new HashMap<>0;
  public static void main(String [] args){
    loadResponses("responses.txt");
    try{
    ServerSocket serverSocket = new ServerSocket(12345);
    System.out.println("Servidor en espera de conexiones...");
    while(true){
        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

        ClientHandler clientHandler = new ClientHandler(clientSocket);
        Thread thread = new Thread(clientHandler);
        thread.start();
      
    }
    }catch(IOException e){
      e.printStackTrace();
      
    }
  }
  private static void loadResponses(String filePath) {
     try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    responses.put(parts[0], parts[1]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
   private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                out.println("¡Bienvenido al chatbot! Escribe 'salir' para desconectar.");

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equalsIgnoreCase("salir")) {
                        break;
                    }
                    String response = responses.getOrDefault(input, "Lo siento, no entendí esa pregunta.");
                    out.println("Chatbot: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
}
