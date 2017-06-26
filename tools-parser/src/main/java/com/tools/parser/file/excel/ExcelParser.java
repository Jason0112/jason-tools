package com.tools.parser.file.excel;


import com.tools.parser.exception.ParserException;
import com.tools.parser.IParser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author ：cheng.zhencai    
 * @date ：2017/04/30
 * @description : Excel解析类
 */
public class ExcelParser implements IParser {

    private Logger logger = LoggerFactory.getLogger(ExcelParser.class);
    /**
     * 2003excel文件后缀
     */
    private final String XLS_FILE_POSTFIX = "xls";
    /**
     * 2007excel文件后缀
     */
    private final String XLSX_FILE_POSTFIX = "xlsx";

    private final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    /**
     * 解析 Excel 文件
     *
     * @param inputStream 流
     * @param parameter   参数(fileName)
     * @return 返回解析数据
     * @throws ParserException
     */
    @Override
    public List<Object> parser(InputStream inputStream, String... parameter) throws ParserException {
        if (parameter == null || parameter.length == 0) {
            throw new ParserException("缺少文件名参数..");
        }
        List<Object> rows = null;
        try {
            if (parameter[0].endsWith(XLS_FILE_POSTFIX)) {
                HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
                rows = readerXlsx(workbook);
            }
            if (parameter[0].endsWith(XLSX_FILE_POSTFIX)) {

                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                rows = readerXls(workbook);
            }
        } catch (Exception e) {
            throw new ParserException("获取文件流,出现异常.", e);
        }
        return rows;
    }

    /**
     * 可读取 xlsx  2010
     *
     * @param workbook 工作溥
     * @return 返回数据
     * @throws ParserException
     */
    private List<Object> readerXlsx(HSSFWorkbook workbook) throws ParserException {

        List<Object> rows = new ArrayList<>();

        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            logger.info("开始解析sheetName为:{}中的数据", xssfSheet.getSheetName());
            for (int rowNum = 1, num = xssfSheet.getLastRowNum(); rowNum <= num; rowNum++) {
                HSSFRow row = xssfSheet.getRow(rowNum);
                if (row != null) {
                    List<String> json = getObject(row);
                    rows.add(json);

                }
            }
            logger.info("sheetName为:{}解析完毕,总共解析{}条数据", xssfSheet.getSheetName(), xssfSheet.getLastRowNum());
        }
        return rows;
    }

    /**
     * 可读取xls  2003
     *
     * @param workbook 工作溥
     * @return 数据
     * @throws ParserException
     */
    private List<Object> readerXls(XSSFWorkbook workbook) throws ParserException {

        List<Object> rows = new ArrayList<>();

        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            logger.info("开始解析sheetName为:{}中的数据", xssfSheet.getSheetName());
            for (int rowNum = 1, num = xssfSheet.getLastRowNum(); rowNum <= num; rowNum++) {
                XSSFRow row = xssfSheet.getRow(rowNum);
                if (row != null) {

                    List<String> object = getObject(row);
                    rows.add(object);

                }
            }
            logger.info("sheetName为:{}解析完毕,总共解析{}条数据", xssfSheet.getSheetName(), xssfSheet.getLastRowNum());
        }
        return rows;
    }

    /**
     * @param row 单行数据
     * @return 转换后的行数据
     */
    private List<String> getObject(Row row) throws ParserException {
        Iterator<Cell> it = row.cellIterator();
        List<String> datas = new ArrayList<>(row.getRowNum());
        while (it.hasNext()) {
            datas.add(getValue(it.next()));
        }
        return datas;
    }


    /**
     * 获取单元格值
     *
     * @param cell 单元格
     * @return 单元格数据
     */
    private String getValue(Cell cell) {

        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return decimalFormat.format(cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return null;
    }

}
