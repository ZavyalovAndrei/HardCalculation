import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 23456;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        System.out.println("Клиент запущен.");

        try (Socket socket = new Socket(HOST, PORT); BufferedReader in = new BufferedReader(new InputStreamReader
                (socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {
            String msg;
            System.out.println(in.readLine());
            while (true) {
                System.out.println(in.readLine());
                msg = scanner.nextLine();
                out.println(msg);
                if ("end".equals(msg)) {
                    System.out.println(in.readLine() + "\nКлиент завершает свою работу.");
                    break;
                }
                System.out.println(in.readLine());
            }
        } catch (IOException err) {
            System.out.println("Сервер " + HOST + ":" + PORT + " не найден.");
        }
    }
}