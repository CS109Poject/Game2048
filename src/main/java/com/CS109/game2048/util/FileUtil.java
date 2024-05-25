package com.CS109.game2048.util;

import java.io.*;

public class FileUtil {
    public static <T> T deepCopy(T object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oss = new ObjectOutputStream(bos);
        oss.writeObject(object);
        oss.flush();
        oss.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        T copy =(T) ois.readObject();
        ois.close();

        return copy;

    }
}
