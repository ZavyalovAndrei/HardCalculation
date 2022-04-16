import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 23456;

    public static void main(String[] args) throws IOException {
        ServerSocket servSocket = new ServerSocket(PORT);
        try (Socket socket = servSocket.accept();
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("Вы успешно подключились к серверу, который находит числа из ряда Фибоначчи");
            while (true) {
                int userEntry = checkEntry(in, out, servSocket);
                String message = "Выбранное число из ряда Фибоначчи равно: ";
                if (userEntry < 0) {
                    out.println(message + "-" + fibonacci(Math.abs(userEntry)));
                } else if (userEntry == 0) {
                    out.println("Ошибка ввода!");
                } else {
                    out.println(message + fibonacci(userEntry));
                }
            }
        }
    }

    private static int checkEntry(BufferedReader in, PrintWriter out, ServerSocket socket) throws IOException {
        while (true) {
            out.println("Введите номер числа из ряда Фибоначчи(любое целое число кроме 0) или введите \"end\" для " +
                    "завершения работы с сервером.");
            try {
                String entry = in.readLine();
                if (entry.equals("end")) {
                    out.println("Сервер завершает свою работу");
                    socket.close();
                } else {
                    return Integer.parseInt(entry);
                }
            } catch (NumberFormatException err) {
                out.println("Ошибка ввода!");
            }
        }
    }

    private static long fibonacci(int ordinalNumber) {
        long penultimateNum = 0;
        long ultimateNum = 1;
        long currentNum = 2;
        long result = 0;
        if (ordinalNumber == 1) {
            return penultimateNum;
        } else if (ordinalNumber == 2) {
            return ultimateNum;
        } else {
            while (ordinalNumber != currentNum) {
                result = penultimateNum + ultimateNum;
                penultimateNum = ultimateNum;
                ultimateNum = result;
                currentNum++;
            }
            return result;
        }
    }
}