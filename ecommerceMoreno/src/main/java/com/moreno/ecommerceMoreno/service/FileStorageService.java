package com.moreno.ecommerceMoreno.service;

import com.moreno.ecommerceMoreno.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new BusinessRuleException("Não foi possível criar o diretório para salvar os arquivos.");
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = "";
        try {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        } catch (Exception e) {
        }
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException ex) {
            throw new BusinessRuleException("Não foi possível salvar o arquivo " + fileName + ". Por favor, tente novamente.");
        }
    }

}