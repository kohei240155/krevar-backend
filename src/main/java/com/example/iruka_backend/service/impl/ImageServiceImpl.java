package com.example.iruka_backend.service.impl;

import com.example.iruka_backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String LOCAL_IMAGE_DIR = "C:/images/"; // Windowsフォルダのパス
    // private static final String S3_BUCKET_URL = "https://s3.amazonaws.com/your-bucket/";

    @Override
    public String saveImage(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();

        // ファイル名の正規化
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        if (isLocalEnvironment()) {
            // ローカルに保存
            File localFile = new File(LOCAL_IMAGE_DIR + fileName);
            image.transferTo(localFile);
            return localFile.getAbsolutePath();
        } else {
            // S3に保存（実装は簡略化し、現時点ではコメントアウト）
            // String s3Url = S3_BUCKET_URL + fileName;
            // Files.write(Paths.get(s3Url), image.getBytes());
            // return s3Url;
            return "S3 storage not yet implemented";
        }
    }

    private boolean isLocalEnvironment() {
        // 現在は常にローカル環境として判定
        return true;
    }
}
