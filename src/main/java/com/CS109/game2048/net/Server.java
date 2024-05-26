package com.CS109.game2048.net;

import com.CS109.game2048.engine.Grid;
import com.CS109.game2048.util.FileUtil;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    //private int clientCount = 0;
    private Grid grid1 = new Grid();
    private Grid grid2 = new Grid();
    private List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    public Server() {
        try {

            this.grid1.initGridNumbers();
            this.grid2.initGridNumbers();


            try {
                System.out.println("Server begins to work!");
                serverSocket = new ServerSocket(9999);


                Socket socket1 = serverSocket.accept();
                ClientHandler client1 = new ClientHandler(socket1, grid1, grid2, this);
                new Thread(client1).start();
                clients.add(client1);
                System.out.println("Player1 ready");

                Socket socket2 = serverSocket.accept();
                ClientHandler client2 = new ClientHandler(socket2, grid2, grid1, this);
                new Thread(client2).start();
                clients.add(client2);
                System.out.println("Player2 ready");

                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"All players are ready!");
                    alert.showAndWait();
                });

            }catch (BindException e){
                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"You have already issue a challenge.");
                    alert.showAndWait();
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendGrids() {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendGrids();
        }
    }

    public void closeServer() {
        try {
            for (ClientHandler client : clients) {
                client.socket.close();
            }

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            clients.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ClientHandler implements Runnable {
    public Socket socket;
    private Server server;
    private ObjectOutputStream oss;
    private ObjectInputStream ois;
    private final Grid myGrid;
    private final Grid enemyGrid;

    ClientHandler(Socket socket, Grid myGrid, Grid enemyGrid, Server server) {
        this.socket = socket;
        this.myGrid = myGrid;
        this.enemyGrid = enemyGrid;
        this.server = server;
        try {
            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            this.oss = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
//            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
//            this.oss = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            //System.out.println("aaa");
            this.oss.flush();
            while (true) {
                try {
                    String receive = (String) ois.readObject();

                    switch (receive) {
                        case "up":
                            myGrid.up();
                            break;
                        case "down":
                            myGrid.down();
                            break;
                        case "right":
                            myGrid.right();
                            break;
                        case "left":
                            myGrid.left();
                            break;
                        case "start":
                            myGrid.setIfGameBegin(true);
                            break;
                    }

                    this.server.sendGrids();

                } catch (EOFException | SocketException e) {
                    System.out.println("Client Disconnected.");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void sendGrids() {
        try {
            Grid myGrid = FileUtil.deepCopy(this.myGrid);
            Grid enemyGrid = FileUtil.deepCopy(this.enemyGrid);
            Grid[] grids = {myGrid, enemyGrid};
            oss.writeObject(grids);
            oss.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}