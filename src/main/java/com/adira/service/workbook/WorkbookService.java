package com.adira.service.workbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by didi-realtime on 22/10/16.
 */
public interface WorkbookService {
    void createWorkbook() throws IOException;

    void createWorkBook(List<String> contens);

    void readData(String fileName) throws IOException, ParseException;
}
