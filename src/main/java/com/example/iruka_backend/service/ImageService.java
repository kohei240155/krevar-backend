package com.example.iruka_backend.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  String saveImage(MultipartFile image) throws IOException;

  String saveImageFromOpenAI(String imagePath) throws IOException;
}
