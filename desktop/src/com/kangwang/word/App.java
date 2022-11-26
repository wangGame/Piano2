package com.kangwang.word;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        FileHandle file = new FileHandle("Assets/srcimg");
        for (FileHandle handle : file.list()) {
            byte[] bytes = handle.readBytes();
            byte[] newbytes = new byte[bytes.length];
            int index= 0;
            for (byte aByte : bytes) {
                newbytes[index] = (byte) (aByte ^ 46);
                index++;
            }
            FileHandle handle1 = new FileHandle("Assets/outimg/"+handle.name());
            handle1.writeBytes(newbytes,false);
        }
    }
}
