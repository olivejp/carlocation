package nc.olive.carlocation.service;

import nc.olive.carlocation.domain.LocationDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExcelGeneratorService {

    public static final String HEADER_TITLE = "Fiche de location de véhicule";
    public static final String SHEET_NAME = "Location";
    public static final String DATE_FORMAT = "dd/mm/yyyy";

    private CellStyle createHeaderCellStyle(XSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setItalic(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private CellStyle createDateCellStyle(Workbook workbook) {
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        short dataFormat = createHelper.createDataFormat().getFormat(DATE_FORMAT);
        dateCellStyle.setDataFormat(dataFormat);
        return dateCellStyle;
    }

    private void createHeaderCell(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(1);
        Cell headerCell = headerRow.createCell(1);
        headerCell.setCellValue(HEADER_TITLE);
        headerCell.setCellStyle(headerStyle);
    }

    private void createLocalDateLabelAndValueRow(Sheet sheet, int rowNum, String rowTitle, LocalDate rowValue, CellStyle cellStyle) {
        Row row = sheet.createRow(rowNum);
        Cell label = row.createCell(1);
        Cell value = row.createCell(2);
        label.setCellValue(rowTitle);
        value.setCellValue(rowValue);
        value.setCellStyle(cellStyle);
    }

    private void createStringLabelAndValueRow(Sheet sheet, int rowNum, String rowTitle, String rowValue) {
        Row row = sheet.createRow(rowNum);
        Cell label = row.createCell(1);
        Cell value = row.createCell(2);
        label.setCellValue(rowTitle);
        value.setCellValue(rowValue);
    }

    public Workbook generateLocation(LocationDto locationDto) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        CellStyle dateCellStyle = createDateCellStyle(workbook);
        CellStyle headerStyle = createHeaderCellStyle(workbook);

        Sheet sheet = workbook.createSheet(SHEET_NAME);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 6000);

        createHeaderCell(sheet, headerStyle);
        createStringLabelAndValueRow(sheet, 2, "Immatriculation", locationDto.getImmatriculation());
        createStringLabelAndValueRow(sheet, 3, "Kilométrage", locationDto.getKilometrage().toString());
        createStringLabelAndValueRow(sheet, 4, "Emprunteur", locationDto.getNomEmprunteur());
        createLocalDateLabelAndValueRow(sheet, 5, "Date de début de location", locationDto.getDateDebut(), dateCellStyle);
        createLocalDateLabelAndValueRow(sheet, 6, "Date de fin de location", locationDto.getDateFin(), dateCellStyle);

        if (locationDto.getListeDefauts() != null && !locationDto.getListeDefauts().isEmpty()) {
            createStringLabelAndValueRow(sheet, 7, "Défauts constatés sur le véhicule", locationDto.getListeDefauts().get(0));
            if (locationDto.getListeDefauts().size() > 1) {
                for (int i = 1; i < locationDto.getListeDefauts().size(); i++) {
                    Row row = sheet.createRow(7 + i);
                    Cell defautsCell = row.createCell(2);
                    defautsCell.setCellValue(locationDto.getListeDefauts().get(i));
                }
            }
        }

        return workbook;
    }
}
