package com.blog.serives.Impl;

import com.blog.serives.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        var filename = file.getOriginalFilename();

        var fullPath = path + File.separator + filename;

        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Path.of(fullPath));

        return filename;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        var fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
