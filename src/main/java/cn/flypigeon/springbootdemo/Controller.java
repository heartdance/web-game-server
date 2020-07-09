package cn.flypigeon.springbootdemo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class Controller {

    @PostMapping("/music")
    public Music music(MultipartFile file) {
        try {
            String filePath = "./music/" + getFileName(file.getOriginalFilename());
            FileUtil.createFileByBytes(file.getBytes(), filePath);
            return AudioUtil.getSongInfo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFileName(String sourceName) {
        if (sourceName == null) {
            sourceName = "unknown";
        }
        int sep = sourceName.lastIndexOf(".");
        String fileName;
        String extension;
        if (sep != -1) {
            fileName = sourceName.substring(0, sep);
            extension = sourceName.substring(sep);
        } else {
            fileName = sourceName;
            extension = ".mp3";
        }
        String uuid = UUID.randomUUID().toString();
        return fileName + "." + uuid + extension;
    }

}
