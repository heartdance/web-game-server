package cn.flypigeon.springbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
public class Controller {

    @GetMapping("test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @PostMapping("music")
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

    @GetMapping("ip")
    public String ip(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            ip = ip.split(",")[0];
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    @GetMapping("port")
    public Integer port(HttpServletRequest request) {
        return request.getRemotePort();
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
