package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ReusableComponents.PublicVariables;

public class ExcelConnections {

	public static FileInputStream ofis;
	public static XSSFWorkbook owb;
	public static XSSFSheet osheet;
	public static int intLastrow;
	public static int intFirstrow;

	public static List<Integer> FindRows(String pTestName) throws IOException {
		try {
			List<Integer> arrRows = new ArrayList<Integer>();
			String strFilePath = System.getProperty("user.dir") + "/TestData/TestData.xlsx";
			File ofile = new File(strFilePath);
			FileInputStream ofis = new FileInputStream(ofile);
			owb = new XSSFWorkbook(ofis);
			XSSFSheet osheet = owb.getSheet("TestData");
			int intLastRow = osheet.getLastRowNum();
			int intFirstRow = osheet.getFirstRowNum();
			for (int j = intFirstRow; j <= intLastRow; j++) {
				if (osheet.getRow(j).getCell(0).getStringCellValue().contentEquals(pTestName)
						&& (osheet.getRow(j).getCell(1).getStringCellValue().toUpperCase().contentEquals("Y"))) {
					arrRows.add(j);
				}
			}
			osheet = null;
			owb = null;
			ofis = null;
			return arrRows;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, String> GetTestData(int gRow) throws IOException {
		try {
			String strFilepath = System.getProperty("user.dir") + "/TestData/TestData.xlsx";
			HashMap<String, String> map = new HashMap<>();
			File ofile = new File(strFilepath);
			PublicVariables pv = new PublicVariables();
			FileInputStream ofis = new FileInputStream(ofile);
			owb = new XSSFWorkbook(ofis);
			XSSFSheet osheet = owb.getSheet("TestData");
			int intLastrow = osheet.getLastRowNum();
			int intFirstrow = osheet.getFirstRowNum();
			for (int j = 0; j <= osheet.getRow(gRow).getLastCellNum() - 1; j++) {
				try {
					map.put(osheet.getRow(0).getCell(j).getStringCellValue(),
							osheet.getRow(gRow).getCell(j).getStringCellValue());
				} catch (Exception ex) {
					map.put(osheet.getRow(0).getCell(j).getStringCellValue(), "");
				}
			}
			osheet = null;
			owb = null;
			ofis = null;
			return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void WriteToExcellCell(String strColName, String strValue) throws IOException {
		try {
			String strFilepath = System.getProperty("user.dir") + "/TestData/TransactionID.xslx";
			File ofile = new File(strFilepath);
			FileInputStream ofis = new FileInputStream(ofile);
			owb = new XSSFWorkbook(ofis);
			XSSFSheet osheet = owb.getSheet(PublicVariables.gCountry);
			int intLastRow = osheet.getLastRowNum();
			int intFirstRow = osheet.getFirstRowNum();
			int pRow = PublicVariables.gRowNum;
			for (int j = 0; j <= osheet.getRow(0).getLastCellNum() - 1; j++) {
				if (osheet.getRow(0).getCell(j).getStringCellValue().contentEquals(strColName)) {
					osheet.getRow(1).getCell(j).setCellValue(strValue);
					Reporting.WritetoReport("Pass", strColName + "Value updated");
					break;

				}
			}

			FileOutputStream outputStream = new FileOutputStream(
					System.getProperty("user.dir") + "/TestData/TransactionID.xslx");
			owb.write(outputStream);
			osheet = null;
			owb = null;
			ofis = null;
			ofile = null;

		} catch (Exception ex) {
			Reporting.WritetoReport("Error",
					"writing to the excell coulmn:" + strColName + "failed due to error" + ex.getMessage());
		}
	}

	public static String GetFieldValue(String strColName) throws IOException {
		try {
			Reporting.WritetoReport("pass", "column:" + strColName + "value:"
					+ PublicVariables.hmTestData.get(strColName).toString() + "retrived");
			return PublicVariables.hmTestData.get(strColName).toString();
		} catch (Exception ex) {
			Reporting.WritetoReport("Error",
					"writing to the excell coulmn:" + strColName + "failed due to error" + ex.getMessage());
			return null;

		}
	}

	public static String GetTransactionId(String strColName) throws IOException {
		try {
			String strFilepath = System.getProperty("user.dir") + "/TestData/TransactionID.xlsx";
			File ofile = new File(strFilepath);
			boolean flagModified = false;
			FileInputStream ofis = new FileInputStream(ofile);
			owb = new XSSFWorkbook(ofis);
			XSSFSheet osheet = owb.getSheet(PublicVariables.gCountry);
			return osheet.getRow(1).getCell(1).getStringCellValue();

		} catch (Exception ex) {
			Reporting.WritetoReport("Error",
					"writing to the excell coulmn:" + strColName + "failed due to error" + ex.getMessage());
			return null;
		}
	}

}
