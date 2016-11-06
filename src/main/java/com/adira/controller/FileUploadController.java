package com.adira.controller;

import com.adira.entity.Audit;
import com.adira.exeption.StorageFileNotFoundException;
import com.adira.service.storage.StorageService;
import com.adira.service.workbook.WorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

/**
 * Created by didi-realtime on 04/11/16.
 */
@Controller
public class FileUploadController {
    @Autowired
    private WorkbookService workbookService;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showForm(Audit audit) {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) throws ParseException {
        String originalFileName = file.getOriginalFilename();
        storageService.deleteAll();
        storageService.store(file);

        try {
            workbookService.readData(originalFileName);
        } catch (IOException e) {
            throw new StorageFileNotFoundException("File not foud "+originalFileName, e);
        }

        return "redirect:/audits";
    }
}
