package com.adira.service.workbook;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.enumeration.RiskLevel;
import com.adira.enumeration.Status;
import com.adira.function.FunctionDate;
import com.adira.service.audit.AuditService;
import com.adira.service.email.EmailService;
import com.adira.service.storage.StorageProperties;
import com.adira.service.storage.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by didi-realtime on 22/10/16.
 */
@Service("workbookService")
public class WorkbookServiceImpl implements WorkbookService {

    public static final String[] fileColumn = {"Tahun Audit", "Auditor", "Domain", "Unit", "PIC", "Isu Audit",
            "Deskripsi Isu Audit", "Action Plan", "Level Resiko", "Outstanding Action Plan", "Initial Due Date", "Status",
            "Reschedule I", "Reschedule II"};

    @Autowired
    private AuditDao auditDao;
    @Autowired
    private AuditService auditService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageProperties properties;
    @Autowired
    private EmailService emailService;

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
    public void createWorkBook(List<String> contens) {
        String[] headers = getListOfHeader();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadSheet = workbook.createSheet("Data Audit");
        int indexRow = 0;
        Cell cell = null;
        String fileName;

        XSSFRow row = spreadSheet.createRow(indexRow++);
        int cellId = 0;

        for (int i = 0; i <= headers.length - 1; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        List<Audit> audits = (List<Audit>) auditDao.findAll();
        Audit audit = audits.get(0);
        row = spreadSheet.createRow(indexRow);

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getAuditYear());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getAuditor());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getDomain());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getUnit());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getPic());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getAuditIssue());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getAuditIssueDescription());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getActionPlan());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getRiskLevel().toString());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getOutstandingActionPlan());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getInitialDueDate());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getStatus().toString());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getFirstRescheduled());

        cell = row.createCell(cellId++);
        cell.setCellValue(audit.getSecondRescheduled());

        try {
            fileName = UUID.randomUUID().toString()+".xlsx";
            String rootLocation = properties.getPathLocation();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(rootLocation+File.separator+fileName));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            emailService.sendEmailWithAttachment(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readData(String fileName) throws IOException, ParseException {
        FileInputStream file = new FileInputStream(storageService.load(fileName).toFile());
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int numerOfCells = row.getPhysicalNumberOfCells();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> columnValues = new ArrayList<>();

            for (int i = 0; i <= numerOfCells-1; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    columnValues.add("");
                } else {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue()+"\t");
                            columnValues.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case Cell.CELL_TYPE_STRING:
                            if (!Arrays.asList(getListOfHeader()).contains(cell.getStringCellValue())) {
                                System.out.println(cell.getStringCellValue()+"\t");
                                columnValues.add(String.valueOf(cell.getStringCellValue()));
                            }
                            break;
                    }
                }

            }

            if (columnValues.size() > 0) {
                Audit audit = new Audit();
                setAuditProperty(columnValues, audit);
                auditDao.save(audit);

            }
        }

    }

    private String[] getListOfHeader() {
        return fileColumn;
    }

    private void setAuditProperty(List<String> colVal, Audit audit) throws ParseException {

        int i = 0;

        for (String val : colVal) {

            switch (i) {
                case 0:
                    Float yearFloat = Float.valueOf(val);

                    audit.setAuditYear(Math.round(yearFloat));
                    break;
                case 1:
                    audit.setAuditor(val);
                    break;
                case 2:
                    audit.setDomain(val);
                    break;
                case 3:
                    audit.setUnit(val);
                    break;
                case 4:
                    audit.setPic(val);
                    break;
                case 5:
                    audit.setAuditIssue(val);
                    break;
                case 6:
                    audit.setAuditIssueDescription(val);
                    break;
                case 7:
                    audit.setActionPlan(val);
                    break;
                case 8:
                    if (val != null && val.toUpperCase().equals("High".toUpperCase()))
                        audit.setRiskLevel(RiskLevel.HIGH);
                    if (val != null && val.toUpperCase().equals("Low".toUpperCase()))
                        audit.setRiskLevel(RiskLevel.LOW);
                    if (val != null && val.toUpperCase().equals("Medium".toUpperCase()))
                        audit.setRiskLevel(RiskLevel.MEDIUM);
                    break;
                case 9:
                    audit.setOutstandingActionPlan(val);
                    break;
                case 10:
                    if (val != null && !val.equals(""))
                        audit.setInitialDueDate(FunctionDate.stringToDate(val));
                    break;
                case 11:
                    switch (val.toUpperCase()) {
                        case "IN PROGRESS":
                            audit.setStatus(Status.ON_PROGRESS);
                            break;
                        case "NEW":
                            audit.setStatus(Status.NEW);
                            break;
                        case "DONE":
                            audit.setStatus(Status.DONE);
                            break;
                        default:
                            audit.setStatus(Status.NEW);
                            break;
                    }

                    break;
                case 12:
                    if (!val.equals(""))
                        audit.setFirstRescheduled(FunctionDate.stringToDate(val));

                    break;
                case 13:
                    if (!val.equals(""))
                        audit.setSecondRescheduled(FunctionDate.stringToDate(val));
                    break;
            }

            i++;
        }

    }
}
