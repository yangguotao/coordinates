package com.springboot.coordinates.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * @Author: yangguotao
 * @Date: 2019/11/28
 * @Version 1.0
 */
@Component
public class WriteExcel {

    private Logger logger = LoggerFactory.getLogger(WriteExcel.class);
    private static final String EXCEL_XLS = "xls";//2003版
    private static final String EXCEL_XLSX = "xlsx";//2007/2010
    private static final String X_STR = "x轴(7)";//表格中x轴的标记
    private static final String Y_STR = "y轴(6)";//表格中x轴的标记
    private static final String B_STR = "B(纬度)";//表格中十进制纬度的标记
    private static final String L_STR = "L(经度)";//表格中十进制经度的标记
    private static final String _B_STR = "_B(纬度)";//表格中度分秒纬度的标记
    private static final String _L_STR = "_L(经度)";//表格中度分秒经度的标记
    //private static final double L0 =  111.0000070986107947;
    private static final double L0 =  111.0000072;
   // private static final double L0 = ‬111.00000708333333;
    private static final double K0 = 1.0; //投影比例因子
    private int x = -1, y = -1, B = -1, L = -1, _B = -1, _L = -1;

    public Integer writeExcel(String finalXlsxPath) {
        OutputStream out = null;
        try {
            int sheetRowNum = 0;
            Sheet sheet = null;
            //创建文件
            File file = new File(finalXlsxPath);
            //读取源数据
            List<jxl.Cell[]> list = new ReadExcel().readRowsExcel(file);
            Workbook wb = getWorkbook(file);
            int wbSheetsNumCount = wb.getNumberOfSheets();
            for (int index = 0; index < wbSheetsNumCount; index++) {
                sheet = wb.getSheetAt(index);
                sheetRowNum = sheet.getLastRowNum();
                for (int i = 1; i < sheetRowNum; i++) {
                    Row row = sheet.getRow(i);
                    sheet.removeRow(row);
                    //创建文件输出流
                    out = new FileOutputStream(finalXlsxPath);
                    wb.write(out);
                }
                getColumnLabel(list.get(0));
                if (x == -1 || y == -1 || B == -1 || L == -1 || _B == -1 || _L == -1) {
                    logger.error("数据格式错误");
                    return -1;
                }
                //写入数据
                for (int i = 1; i < list.size(); i++) {
                    jxl.Cell[] list2 = list.get(i);
                    Row newRow = sheet.createRow(i);
                    Double[] TempArry = new Double[2];
                    //列
                    for (int j = 0; j < list2.length; j++) {
                        double[] ll;
                        //创建一行从第二行开始
                        Cell newCell = newRow.createCell(j);
                        String cell = list2[j].getContents();
                        if (cell.isEmpty()) {
                            continue;
                        }
                        //查找x,y坐标
                        if (j == x) {
                            TempArry[0] = Double.valueOf(cell);
                        }
                        if (j == y) {
                            TempArry[1] = Double.valueOf(cell);
                        }
                        //保存原始数据
                        newCell.setCellValue(cell);
                        //转换数据
                        if (TempArry[0] != null && TempArry[1] != null) {
                            double b = TempArry[0];
                            double n = TempArry[1];
                            //计算数据
                            ll = GaussKruger.xy2ll(K0, L0, TempArry[0], TempArry[1]);
                            //System.out.println(String.format("[%f, %f]",  ll[0], ll[1]));
                            //保存转换数据
                            Cell LCell = newRow.createCell(L);
                            Cell BCell = newRow.createCell(B);
                            LCell.setCellValue(ll[0]);
                            BCell.setCellValue(ll[1]);
                            //转换数据格式：度分秒
                            String _l = LonAndLatFormat.DecimalRpm(ll[0]);
                            String _b = LonAndLatFormat.DecimalRpm(ll[1]);
                            Cell _LCell = newRow.createCell(_L);
                            Cell _BCell = newRow.createCell(_B);
                            _LCell.setCellValue(_l);
                            _BCell.setCellValue(_b);
                            break;
                        }
                    }
                }
            }
            out = new FileOutputStream(finalXlsxPath);
            wb.write(out);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("数据写入失败", e.getMessage());
            }
        }
        return 1;
    }

    /**
     * 判断Excel版本
     *
     * @param file
     * @return 获取Workbook
     * @throws Exception
     */
    public Workbook getWorkbook(File file) throws Exception {

        Workbook wb = null;
        FileInputStream is = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {
            wb = new HSSFWorkbook(is);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {
            wb = new XSSFWorkbook(is);
        }
        return wb;
    }

    /**
     * Get column label
     *
     * @param cells
     */
    public void getColumnLabel(jxl.Cell[] cells) {
        for (int i = 0; i < cells.length; i++) {
            String sign = cells[i].getContents();
            if (sign.equals(X_STR)) {
                x = i;
            }
            if (sign.equals(Y_STR)) {
                y = i;
            }
            if (sign.equals(B_STR)) {
                B = i;
            }
            if (sign.equals(L_STR)) {
                L = i;
            }
            if (sign.equals(_B_STR)) {
                _B = i;
            }
            if (sign.equals(_L_STR)) {
                _L = i;
            }
        }
    }
}
