package br.com.erudio.services;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_dir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create th directiory where files will be stored",e);
        }

    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
         if (filename.contains("..")){
             throw new FileStorageException("Sorry! Filename contains a invalid path sequence " +filename);
         }
          Path targetLocation = this.fileStorageLocation.resolve(filename);
        } catch (Exception e) {
        throw new FileStorageException("Could not store file " +filename+". Please try Again",e);
        }
    }
}
