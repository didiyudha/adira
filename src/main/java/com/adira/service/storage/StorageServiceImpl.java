package com.adira.service.storage;

import com.adira.enumeration.DocumentType;
import com.adira.exeption.StorageException;
import com.adira.exeption.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by didi-realtime on 04/11/16.
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation;
    private final Path auditeePath;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadPath());
        this.auditeePath = Paths.get(properties.getAuditeePath());
    }

    @Override
    public void init() {
        try {

            Files.createDirectory(rootLocation);
            Files.createDirectory(auditeePath);

        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {

        try {

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file "+ file.getOriginalFilename());
            }

            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));

        } catch (IOException e) {
            throw new StorageException("Failed to store file "+ file.getOriginalFilename(), e);
        }
    }

    @Override
    public void store(MultipartFile file, DocumentType documentType) {
        try {

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file "+ file.getOriginalFilename());
            }

            switch (documentType.toString()) {
                case "DATA":
                    Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
                    break;
                case "AUDITEE":
                    Files.copy(file.getInputStream(), this.auditeePath.resolve(file.getOriginalFilename()));
                    break;
            }


        } catch (IOException e) {
            throw new StorageException("Failed to store file "+ file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read store files ", e);
        }
    }

    @Override
    public Path load(String fileName, DocumentType documentType) {
        Path path = null;

        switch (documentType.toString().toUpperCase()) {
            case "DATA":
                path = rootLocation.resolve(fileName);
                break;
            case "AUDITEE":
                path = auditeePath.resolve(fileName);
                break;
        }
        return path;
    }

    @Override
    public Resource loadAsResource(String fileName, DocumentType documentType) {

        try {

            Path file = load(fileName, documentType);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: "+ fileName);
            }

        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: "+ fileName);
        }

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        FileSystemUtils.deleteRecursively(auditeePath.toFile());
    }
}
