package com.edvaldo.perdidos_achados.service.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {

    public String salvarImagemLocal(MultipartFile imagem) throws IOException {
        String pasta = "uploads";
        Files.createDirectories(Paths.get(pasta));

        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        Path caminho = Paths.get(pasta, nomeArquivo);

        BufferedImage original = ImageIO.read(imagem.getInputStream());
        if (original == null) {
            throw new IOException("Arquivo enviado não é uma imagem válida.");
        }

        // Redimensiona para no máximo 1280x720 mantendo proporção
        BufferedImage redimensionada = Scalr.resize(
            original,
            Scalr.Method.QUALITY,
            Scalr.Mode.AUTOMATIC,
            1280, 720
        );

        // Comprime a imagem redimensionada para ~70% de qualidade
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.7f); // 70% de qualidade
            }

            writer.write(null, new IIOImage(redimensionada, null, null), param);
            writer.dispose();

            Files.write(caminho, baos.toByteArray());
        }

        return "http://localhost:8080/uploads/" + nomeArquivo;
    }
}
