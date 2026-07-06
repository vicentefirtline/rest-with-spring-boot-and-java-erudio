package br.com.erudio.services;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.controllers.FileController;
import br.com.erudio.exception.FileNotFoundException;
import br.com.erudio.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_dir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            logger.info("Creating Directories");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could not create th directiory where files will be stored");
            throw new FileStorageException("Could not create th directiory where files will be stored",e);
        }

    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
         if (filename.contains("..")){
             logger.error("Sorry! Filename contains a invalid path sequence ");
             throw new FileStorageException("Sorry! Filename contains a invalid path sequence " +filename);

         }

            logger.info("Saving file in Disk");

          Path targetLocation = this.fileStorageLocation.resolve(filename);
         Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
                 return filename;
        } catch (Exception e) {
            logger.error("Could not store file " +filename+". Please try Again");
        throw new FileStorageException("Could not store file " +filename+". Please try Again",e);
        }
    }

   public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.error("File not Found"+fileName);
                throw new FileNotFoundException("File not Found"+fileName);
            }
        } catch (Exception e) {
            logger.error("File not Found"+fileName);
            throw new FileNotFoundException("File not Found"+fileName,e);
        }
   }


}
