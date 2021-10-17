package ru.netology;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        InetSocketAddress socketAddress = new InetSocketAddress(HOST, PORT);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {

            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String msg;

            while (true) {
                System.out.println("Enter message for server...");
                msg = scanner.nextLine();

                if ("end".equals(msg)) break;

                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);

                System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8));
                inputBuffer.clear();
            }
        } finally {
            socketChannel.close();
        }




//        try (Socket clientSocket = new Socket(HOST, PORT);
//             PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//             Scanner scanner = new Scanner(System.in)) {
//
//            String message;
//            while (true) {
//                System.out.println("Долой пробелы.\n" +
//                        "Введите текст:");
//                message = scanner.nextLine();
//                out.println(message);
//
//                System.out.printf("SERVER: %s\n", in.readLine());
//                if (message.equals("end")) break;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
