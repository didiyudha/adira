package com.adira.service.workbook;

import com.adira.dao.AuditRepository;
import com.adira.dao.AuditTokenRepository;
import com.adira.entity.Audit;
import com.adira.entity.AuditToken;
import com.adira.enumeration.DocumentType;
import com.adira.enumeration.RiskLevel;
import com.adira.enumeration.Status;
import com.adira.function.FunctionDate;
import com.adira.service.audit.AuditService;
import com.adira.service.email.EmailService;
import com.adira.service.security.SecurityService;
import com.adira.service.storage.StorageProperties;
import com.adira.service.storage.StorageService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.file.Files;
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
    private AuditRepository auditRepository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageProperties properties;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AuditTokenRepository auditTokenRepository;

    @Override
    public void createWorkbook() throws IOException {
        List<Audit> auditList = (List<Audit>) auditRepository.findAll();
        Audit audit = auditList.get(0);
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
        XSSFCellStyle style=workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        XSSFSheet spreadSheet = workbook.createSheet("Data Audit");
        int indexRow = 0;
        Cell cell = null;
        String fileName;

        XSSFRow row = spreadSheet.createRow(indexRow++);

        int cellId = 0;

        for (int i = 0; i <= headers.length - 1; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
            workbook.getSheetAt(0).autoSizeColumn(i);
        }

        List<Audit> audits = (List<Audit>) auditRepository.findAll();
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
        spreadSheet.autoSizeColumn(5);

        try {
            fileName = UUID.randomUUID().toString()+".xlsx";
            String rootLocation = properties.getUploadPath();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(rootLocation+File.separator+fileName));
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            /**
             * send email with attachment && create token
             */
            String token = securityService.generateJwtToken(audit.getId());
            String htmlContent = emailService.constructHtmlContent(audit, token);
            emailService.sendEmailWithAttachment("kioson.xero.integration@gmail.com", fileName, htmlContent, DocumentType.DATA);

            /**
             * Create audit token that maps audit and active token
             */

            AuditToken auditToken = new AuditToken(audit, token, AuditToken.AuditTokenStatus.ACTIVE);
            auditTokenRepository.save(auditToken);

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
        FileInputStream file = new FileInputStream(storageService.load(fileName, DocumentType.DATA).toFile());
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        List<Audit> audits = new ArrayList<>();
        int iAudit = 0;

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
                iAudit = iAudit + 1;
                setAuditProperty(columnValues, audit);
                String refNo = auditService.generateAuditName(audit);
                refNo.concat("/");
                refNo = refNo + String.valueOf(iAudit);
                audit.setReferenceNo(refNo);
                audits.add(audit);

            }
        }

        if (audits != null) auditRepository.save(audits);
        Files.deleteIfExists(storageService.load(fileName, DocumentType.DATA));

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
