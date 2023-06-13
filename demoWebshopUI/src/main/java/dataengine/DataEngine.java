package dataengine;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import demoWebShop.Base;

public class DataEngine {

	public static Logger log = getLogger();
	
	/**
	 * @author shailesh Reads test data from external .xlsx file
	 */
	
	public Object[][] getTestData(int paramCount, String moduleName, String testcaseName) {
		Base base = new Base();
		Object[][] obj = null;
		XSSFWorkbook workbook = null;
		Boolean dataExists = false;
		int col = 0;
		
		try {
			String filePath = base.getProperties("TestData_Folder");
			FileInputStream fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			
			List<Row> selRows = new ArrayList<Row>();
			
			int sheets = workbook.getNumberOfSheets();
			System.out.println("number of sheets "+sheets);
			for (int i=0; i<sheets; i++) {
				
				//looks for sheet with matches with module name in the data excel
				if(workbook.getSheetName(i).equalsIgnoreCase(moduleName)) {
					XSSFSheet sheet = workbook.getSheetAt(i);
					Iterator<Row> rows = sheet.iterator();
					
					int coloumn = 0;
					
					while (rows.hasNext()) {
						
						Row r = rows.next();
						
						if(r.getCell(coloumn) != null
								&& r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testcaseName)) {
							selRows.add(r);
							
							if (col == 0) {
								Iterator<Cell> cellIterator = r.cellIterator();
								while (cellIterator.hasNext()) {
									Cell cell = cellIterator.next();
									if (cell.getCellType() != CellType.BLANK
											&& !cell.getStringCellValue().trim().isEmpty())
										col = col+1;
								}
							}
						}
					}
					
					if (col-1 != paramCount) {
						col=col-1;
						if (col<0) {
							col=0;
						}
						log.info("**********************************************************************************************");
						log.error("Method parameter mismatch with the test data. Please correct. Method Parameter: " + paramCount
								+ " Test Data Column: " + col);
						return new Object[0][0];
					}
					
					if (selRows.size() > 0 && col - 1 > 0) {
						obj = new Object[selRows.size()][col-1];
						dataExists = true;
					}
					
					for(int j=0; j<selRows.size(); j++) {
						int colmn = 0;
						for (int k=0; k<=selRows.get(j).getLastCellNum()-2 && colmn < col-1; k++) {
							if (selRows.get(j).getCell(k+1) != null
									&& !selRows.get(j).getCell(k+1).toString().isEmpty()
									&& !selRows.get(j).getCell(k+1).toString().trim().isEmpty()
									&& !selRows.get(j).getCell(k+1).toString().equalsIgnoreCase("BLANK")) {
								
								if(selRows.get(j).getCell(k+1).getCellType() == CellType.STRING) {
									obj[j][colmn] = selRows.get(j).getCell(k+1).toString();
									colmn++;
								} else {
									obj[j][colmn] = NumberToTextConverter
											.toText(selRows.get(j).getCell(k+1).getNumericCellValue());
									colmn++;
								}
								
							} else if (selRows.get(j).getCell(k+1) != null
									&& selRows.get(j).getCell(k+1).toString().equalsIgnoreCase("BLANK")) {
								obj[j][colmn] = null;
								colmn++;
							}
						}
					}
				}
			}
			if (!dataExists) {
				obj = new Object[0][0];
			}
			workbook.close();
			return obj;
		} catch (Exception e) {
			log.error("Unable to read test data for test case:",e);
			return new Object[0][0];
		}
	}
	
}
