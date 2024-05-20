package com.CS109.game2048.util;

import com.CS109.game2048.service.Grid;

import java.io.*;

public class SaveGameUtil {
    public static void saveGame(Grid grid, String fileName) {
        try {
            fileName = "src/main/resources/grid/" + fileName;
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(grid);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Grid loadGame(String fileName) {
        try {
            fileName = "src/main/resources/grid/" + fileName;
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return (Grid) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
