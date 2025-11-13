package com.edvaldo.perdidos_achados.service.image;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ImagemService {

    public String salvarImagemFirebase(MultipartFile imagem) throws IOException {
        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        
        BufferedImage original = ImageIO.read(imagem.getInputStream());
        BufferedImage redimensionada = Scalr.resize(
            original,
            Scalr.Method.QUALITY,
            Scalr.Mode.AUTOMATIC,
            1280, 720
        );

        
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(redimensionada, "jpg", baos);

        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(nomeArquivo, baos.toByteArray(), "image/jpeg");

       return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
    bucket.getName(),
    URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8));

    }
}
