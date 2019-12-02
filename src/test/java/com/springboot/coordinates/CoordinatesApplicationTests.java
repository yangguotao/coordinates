package com.springboot.coordinates;

import com.springboot.coordinates.utils.WriteExcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoordinatesApplicationTests {

    @Test
    void contextLoads() {
        try {
//            File file = new File("H:/测试文件.xls");
//            List<Cell[]> list = new ReadExcel().readRowsExcel(file);
//            for (Cell[] o : list) {
//                for (Cell cell : o) {
//                    System.out.println(cell.getContents());
//                }
//            }
           Integer test =  new WriteExcel().writeExcel("H:/测试文件.xls");
           if(test==1)
              System.out.println("转换成功");
//            List<Cell[]> list2 = new ReadExcel().readRowsExcel(file);
//            for (Cell[] o : list2) {
//                for (Cell cell : o) {
//                    System.out.println(cell.getContents());
//                }
//            }
        }catch (Exception e){
            System.out.println("读取数据失败："+CoordinatesApplicationTests.class);
        }

    }

}
