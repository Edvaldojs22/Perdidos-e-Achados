package com.edvaldo.perdidos_achados.service.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {

    
public String salvarImagemLocal(MultipartFile imagem) throws IOException {
    String pasta = "uploads"; // Corrigido: nome da pasta
    String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();

    Path caminho = Paths.get(pasta, nomeArquivo); // separa corretamente a pasta do nome
    Files.createDirectories(caminho.getParent()); // agora caminho.getParent() é "uploads"
    Files.write(caminho, imagem.getBytes());

    return caminho.toString();
}

    
}
