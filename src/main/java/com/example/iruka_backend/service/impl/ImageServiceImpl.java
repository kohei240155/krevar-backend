package com.example.iruka_backend.service.impl;

import com.example.iruka_backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String LOCAL_IMAGE_DIR = "C:/images/"; // Windowsフォルダのパス

    @Override
    public String saveImage(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();

        // ファイル名の正規化
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        // ローカルに保存
        File localFile = new File(LOCAL_IMAGE_DIR + fileName);
        image.transferTo(localFile);
        return localFile.getAbsolutePath();
    }

    @Override
    public String saveImageFromOpenAI(String imagePath) throws IOException {
        String fileName = Paths.get(URI.create(imagePath).getPath()).getFileName().toString();

        // ファイル名の正規化
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        // URLからファイルをダウンロードしてローカルに保存
        try (InputStream in = URI.create(imagePath).toURL().openStream()) {
            Files.copy(in, Paths.get(LOCAL_IMAGE_DIR + fileName));
        }
        return LOCAL_IMAGE_DIR + fileName;
    }
}