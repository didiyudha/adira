package com.adira.service.storage;

import com.adira.enumeration.DocumentType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by didi-realtime on 04/11/16.
 */
public interface StorageService {
    void init();

    void store(MultipartFile file);

    void store(MultipartFile file, DocumentType documentType);

    Stream<Path> loadAll();

    Path load(String fileName, DocumentType documentType);

    Resource loadAsResource(String fileName, DocumentType documentType);

    void deleteAll();
}
