package sample.server;


// A Java program for a Server

import sample.shared.Message;
import sample.shared.PublicKeys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private PublicKeys publicKeys;
    private Controller serverController;

    int port;
    private ObjectOutputStream objectOutputStream;

    // constructor with port
    Server(int port, Controller controller) {
        this.serverController = controller;
        this.port = port;
    }


    @Override
    public void run() {
        // starts server and waits for a connection
        try {

            Socket socket;

            ServerSocket server;


            server = new ServerSocket(port);


            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client accepted");

            objectOutputStream.writeObject(publicKeys);


            Object object;

            while ((object = objectInputStream.readObject()) != null) {

                if (object.getClass() == PublicKeys.class) {

                    this.serverController.onPublicKeyArrived((PublicKeys) object);

                }

                else if (object.getClass() == Message.class) {

                    serverController.onMessageArrived((Message) object);

                }

            }

            System.out.println("Closing connection");

            // close connection
            socket.close();

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }

    void sendMessage(Message message) {

        try {

            objectOutputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    void setPublicKeys(PublicKeys publicKeys) {
        this.publicKeys = publicKeys;
    }

}
