package PageObjects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    public static Map<String, String> readAddressFromExcel(String filePath){
        Map<String, String> addressData = new HashMap<>();
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Primera hoja
            Row headerRow = sheet.getRow(0); // Fila de encabezados
            Row dataRow = sheet.getRow(1);   // Primera fila de datos

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String header = headerRow.getCell(i).getStringCellValue();
                String value = dataRow.getCell(i).getStringCellValue();
                addressData.put(header, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressData;
    }

    // Méodo CLONADO específico para Payment
    public static Map<String, String> readPaymentFromExcel(String filePath) {
        Map<String, String> paymentData = new HashMap<>();
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet("Payment");
            if (sheet == null) {
                sheet = workbook.getSheetAt(0); // Fallback a primera hoja
                System.out.println("Advertencia: Usando primera hoja como fallback");
            }

            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(1);

            if (headerRow == null || dataRow == null) {
                throw new RuntimeException("El archivo no tiene el formato esperado");
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell headerCell = headerRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell valueCell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                paymentData.put(
                        headerCell.getStringCellValue().trim(),
                        valueCell.toString().trim()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer datos de pago: " + e.getMessage(), e);
        }
        return paymentData;
    }

}
