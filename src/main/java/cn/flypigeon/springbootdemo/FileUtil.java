package cn.flypigeon.springbootdemo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static void createFileByBytes(byte[] bytes, String filePath) {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.mkdirs()) {
            if (!parentFile.exists()) {
                return;
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            //将字节数组写出
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
