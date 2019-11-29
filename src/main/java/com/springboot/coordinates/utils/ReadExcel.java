package com.springboot.coordinates.utils;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Excel
 *
 * @Author: yangguotao
 * @Date: 2019/11/28
 * @Version 1.0
 */


@Component
public class ReadExcel {
    private Logger logger = LoggerFactory.getLogger(ReadExcel.class);

    /**
     * 读取Excel文档
     * @param file Excel文件
     * @return 返回列数据List数据集合
     */
    public List<List<Object>> readCellExcel(File file) {
        List<List<Object>> outerCellList = new ArrayList<>();
        try {
            System.out.println(file.getAbsolutePath());
            InputStream is = new FileInputStream(file.getAbsolutePath());
            Workbook workbook = Workbook.getWorkbook(is);
            int sheets_Number = workbook.getNumberOfSheets();//获取sheets页数
            for (int index = 0; index < sheets_Number; index++) {
                Sheet sheet = workbook.getSheet(index);
                for (int i = 0; i < sheet.getRows(); i++) {
                    List<Object> innerList = new ArrayList<>();
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(i, j).getContents();
                        if (cellinfo.isEmpty()) { continue; }
                        innerList.add(cellinfo);
                    }
                    outerCellList.add(innerList);
                }
            }
        } catch (Exception e) {
            logger.error("读取Excel文件错误:" + e);
        }
        return outerCellList;
    }
    /**
     * 读取Excel文档
     * @param file Excel文件
     * @return 返回行数据List数据集合
     */
    public  List<Cell[]> readRowsExcel(File file) {
        List<Cell[]> outerRowsList = new ArrayList<>();
        try {
            System.out.println(file.getAbsolutePath());
            InputStream is = new FileInputStream(file.getAbsolutePath());
            Workbook workbook = Workbook.getWorkbook(is);
            int sheets_Number = workbook.getNumberOfSheets();//获取sheets页数
            for (int index = 0; index < sheets_Number; index++) {
                Sheet sheet = workbook.getSheet(index);
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] cells = sheet.getRow(i);
                    outerRowsList.add(cells);
                }
            }
        } catch (Exception e) {
            logger.error("读取Excel行文件错误:" + e);
        }
        return outerRowsList;
    }
}
