package HomeWorkLesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket clienSocket = null;
        Scanner scanner = new Scanner(System.in);

        try (ServerSocket serverSocket = new ServerSocket(1522)) {
            System.out.println("Сервер запущен");

            clienSocket = serverSocket.accept();
            System.out.println("Клиент подключился " + clienSocket.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(clienSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clienSocket.getOutputStream());

            Thread threadReader = new Thread(() -> {
                while (true) {
                    try {
                        out.writeUTF(scanner.nextLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadReader.setDaemon(true);
            threadReader.start();

            while (true) {
                String string = in.readUTF();
                if (string.equals("/close")) {
                    System.out.println("Клиент покинул сервер");
                    out.writeUTF("/close");
                    break;
                } else {
                    System.out.println("Клиент " + string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clienSocket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
