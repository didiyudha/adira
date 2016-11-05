package com.adira.service.workbook;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.service.audit.AuditService;
import com.adira.service.storage.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by didi-realtime on 22/10/16.
 */
@Service("workbookService")
public class WorkbookServiceImpl implements WorkbookService {

    public static final String[] fileColumn = {"Tahun Audit", "Auditor", "Domain", "Unit", "PIC", "Isu Audit",
            "Deskripsi Isu Audit", "Action Plan", "Level Resiko", "Outstanding Action Plan", "Initial Due Date",
    "Reschedule I", "Reschedule II", "Status"};

    @Autowired
    private AuditDao auditDao;
    @Autowired
    private AuditService auditService;
    @Autowired
    private StorageService storageService;

    @Override
    public void createWorkbook() throws IOException {
        List<Audit> auditList = (List<Audit>) auditDao.findAll();
        // create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadSheet = workbook.createSheet("Employee Info");
        XSSFRow row;
        Map<String, Object[]> map = new TreeMap<String, Object[]>();
        map.put("1", new Object[]{"EMP ID", "EMP NAME", "DESIGNATION"});
        map.put("2", new Object[]{"EMP-001", "DIDI", "CEO"});
        map.put("3", new Object[]{"EMP-002", "YUDHA", "COO"});

        Set<String> keyId = map.keySet();
        int rowId = 0;

        for (String key: keyId) {
            row = spreadSheet.createRow(rowId++);
            Object[] objects = map.get(key);
            int cellId = 0;

            for (Object object : objects) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue((String) object);
            }
        }



        FileOutputStream fileOutputStream = new FileOutputStream(new File("Employee_Info.xlsx"));
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Override
    public void readData(String fileName) throws IOException {
        FileInputStream file = new FileInputStream(storageService.load(fileName).toFile());
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.println(cell.getNumericCellValue()+"\t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getStringCellValue()+"\t");
                        break;
                }
            }
        }

    }

    private String[] getListOfHeader() {

        String[] headers = {
                "Tahun Audit",
                "Auditor",
                "Domain",
                "Unit",
                "PIC",
                "Isu Audit",

        };

        return headers;
    }
}
