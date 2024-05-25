package com.CS109.game2048.net;

import com.CS109.game2048.engine.Grid;
import com.CS109.game2048.util.FileUtil;

import java.io.*;
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

    public Server() {
        try {

            this.grid1.initGridNumbers();
            this.grid2.initGridNumbers();

            System.out.println("Server begins to work!");
            ServerSocket serverSocket = new ServerSocket(9999);

            Socket socket1 = serverSocket.accept();
            ClientHandler client1 = new ClientHandler(socket1,grid1,grid2,this);
            new Thread(client1).start();
            clients.add(client1);
            System.out.println("Player1 ready");

            Socket socket2 = serverSocket.accept();
            ClientHandler client2 = new ClientHandler(socket2,grid2,grid1,this);
            new Thread(client2).start();
            clients.add(client2);
            System.out.println("Player2 ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendGrids(){
        for (ClientHandler clientHandler:clients){
            clientHandler.sendGrids();
        }
    }

}

class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream oss;
    private ObjectInputStream ois;
    private final Grid myGrid;
    private final Grid enemyGrid;

    ClientHandler(Socket socket, Grid myGrid, Grid enemyGrid,Server server) {
        this.socket = socket;
        this.myGrid = myGrid;
        this.enemyGrid = enemyGrid;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            this.oss = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
                    }

                    System.out.println(Arrays.deepToString(myGrid.getMatrix()));

                    this.server.sendGrids();

                } catch (SocketException e) {
                    System.out.println("Client leave.");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendGrids() {
        try {
            Grid myGrid = FileUtil.deepCopy(this.myGrid);
            Grid enemyGrid = FileUtil.deepCopy(this.enemyGrid);
            Grid[] grids = {myGrid,enemyGrid};
            oss.writeObject(grids);
            oss.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}