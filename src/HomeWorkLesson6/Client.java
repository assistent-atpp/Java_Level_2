package HomeWorkLesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT =1522;

    public static void main(String[] args) {
        Socket socket =null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket(SERVER_ADRESS, SERVER_PORT);
            System.out.println("Подключен к серверу " + socket.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

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
                String string =in.readUTF();
                if (string.equals("/close")) {
                    System.out.println("Потеряно соединение с сервером");
                    out.writeUTF("/close");
                    break;
                } else {
                    System.out.println("Сервер " + string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
