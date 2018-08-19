package com.example.kira666.greetmusic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CutAudio {
    public void cutAudio(File input, File output) {

        byte[] buffer = null;
        InputStream fIn = null;

        try {
            fIn = new FileInputStream(input);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }
        FileOutputStream saving = null;
        BufferedInputStream bis = new BufferedInputStream(fIn);
        if (bis != null) {

            try {
                saving = new FileOutputStream(output);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (NullPointerException e1) {
                e1.printStackTrace();
            }

            buffer = new byte[4 * 1024];
            int maxTamano = 30 * (128 * 1024); // desired_seconds*(song's
// bitrate*1024)
            int data = 0;
            try {
                data = bis.read(buffer);

                int totDataRead = data * 8;
                int p = 0;
                while (data != -1 && totDataRead < maxTamano) {
                    p++;
//                    System.out.println("write write" + p);
                    saving.write(buffer, 0, data);
                    data = bis.read(buffer);
                    totDataRead += data * 8;

                }
                fIn.close();
                saving.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NullPointerException e1) {
                e1.printStackTrace();
            }
        }
    }
}
