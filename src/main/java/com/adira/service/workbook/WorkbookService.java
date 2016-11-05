package com.adira.service.workbook;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by didi-realtime on 22/10/16.
 */
public interface WorkbookService {
    void createWorkbook() throws IOException;

    void readData(String fileName) throws IOException;
}