package com.CS109.game2048.util;

import com.CS109.game2048.engine.Grid;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveGameUtil {
    public static void saveGame(Grid grid, String fileName, String email) {
        try {

            Path directoryPath = Paths.get("src/main/resources/grid/" + email);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(fileName);

            FileOutputStream fileOut = new FileOutputStream(filePath.toFile());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(grid);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Grid loadGame(String fileName, String email) {
        try {

            Path filePath = Paths.get("src/main/resources/grid/" + email + "/" + fileName);
            if (!Files.exists(filePath)) {
                return null;
            }

            FileInputStream fileIn = new FileInputStream(filePath.toFile());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return (Grid) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
