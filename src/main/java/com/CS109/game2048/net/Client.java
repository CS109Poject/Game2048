package com.CS109.game2048.net;

import com.CS109.game2048.controller.BattleModeController;
import com.CS109.game2048.engine.Grid;
import javafx.application.Platform;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Grid myGrid;
    private Grid enemyGrid;
    private boolean connectionState = false;
    private ObjectOutputStream oss;

    public Client(BattleModeController battleModeController) {
        connect();
        if (connectionState) {
            new Thread(new ClientListen(socket, this, battleModeController)).start();
            //new Thread(new ClientSend(socket)).start();
        }
        sendMessage("null");
    }

    public boolean isConnectionState() {
        return connectionState;
    }

    public void setConnectionState(boolean connectionState) {
        this.connectionState = connectionState;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    public Grid getEnemyGrid() {
        return enemyGrid;
    }

    public void setEnemyGrid(Grid enemyGrid) {
        this.enemyGrid = enemyGrid;
    }

    public void connect() {
        try {
            socket = new Socket("10.32.54.253", 9999);
            oss = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            connectionState = true;
        } catch (ConnectException ce) {
            System.out.println("connect false");
            connectionState = false;
        } catch (Exception e) {
            e.printStackTrace();
            connectionState = false;
        }
    }

    public void sendMessage(String message) {
        try {
            if (oss != null) {
                oss.writeObject(message);
                oss.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        connectionState = false;
        try {
            if (oss != null) {
                oss.close();
            }
            if (socket != null) {
                socket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientListen implements Runnable {

    private Socket socket;
    private ObjectInputStream ois;
    private Grid myGrid = new Grid();
    private Grid enemyGrid = new Grid();
    private final BattleModeController battleModeController;
    private Client client;

    ClientListen(Socket socket, Client client, BattleModeController battleModeController) {
        this.socket = socket;
        this.battleModeController = battleModeController;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while (client.isConnectionState()) {
                try {
                    Object obj = this.ois.readObject();
                    if (obj instanceof Grid[]) {
                        Grid[] grids = (Grid[]) obj;
                        myGrid.copy(grids[0]);
                        enemyGrid.copy(grids[1]);
                        this.client.setMyGrid(myGrid);
                        this.client.setEnemyGrid(enemyGrid);
                        if (!this.client.getMyGrid().getIfGameEnd() || !this.client.getEnemyGrid().getIfGameEnd()) {
                            Platform.runLater(battleModeController::afterOperate);
                        }


                    }
                }catch (EOFException|SocketException e){
                    System.out.println("Connection closed.");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}