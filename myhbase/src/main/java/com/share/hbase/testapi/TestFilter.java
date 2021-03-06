package com.share.hbase.testapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.List;

/**
 * @user: share
 * @date: 2019/2/13
 * @description: 测试过滤器的使用
 */
public class TestFilter {

    /**
     * scan 扫描
     */
    @Test
    public void scanData() throws Exception {


        // 初始化 HBase 的conf
        Configuration conf = HBaseConfiguration.create();

        // 通过连接工厂创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        Scan scan = new Scan();

        // 1. 初始化行过滤器,使用二进制比较器，小于等于row10
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row010")));

        /**
         * 2. 获得行id中含有9的所有行
         * 	1、行过滤
         * 	2、子串比较
         * 	3、等于
         */
//        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("9"));

        /**
         * 3. 获得行id中含有9的所有行
         * 	1、行过滤
         * 	2、正则比较
         * 	3、等于
         */
//        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*9.*"));

        /**
         * 4. 获得f1列族数据
         * 	1、列族过滤
         * 	2、正则比较
         * 	3、等于f1
         */
//        FamilyFilter filter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("f1"));

        /**
         * 5. 获得所有name数据
         * 1、列限定符过滤
         * 2、正则
         * 3、等于name
         */
//        QualifierFilter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("name"));

        /**
         * 5. 获得所有50岁以下数据
         * 1、列限定符过滤
         * 2、二进制比较
         * 3、小于
         */
//        QualifierFilter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("name"));

        /**
         * 7.大于等于row005但是小于等于row095的所有行
         */
        //初始化行过滤器,使用二进制比较器，小于等于row10
//        RowFilter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,
//                new BinaryComparator(Bytes.toBytes("row020")));
//        //初始化行过滤器,使用二进制比较器，小于等于row10
//        RowFilter filter2 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,
//                new BinaryComparator(Bytes.toBytes("row005")));
//        FilterList fl = new FilterList();
//        fl.addFilter(filter1);
//        fl.addFilter(filter2);
//        scan.setFilter(fl);


        /**
         * 8. 小于row005但是大于row095的所有行
         */
//        //初始化行过滤器,使用二进制比较器，小于等于row10
//        RowFilter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,
//                new BinaryComparator(Bytes.toBytes("row005")));
//        //初始化行过滤器,使用二进制比较器，小于等于row10
//        RowFilter filter2 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,
//                new BinaryComparator(Bytes.toBytes("row095")));
//        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ONE, filter1,filter2);
//        scan.setFilter(fl);


        /**
         *  9. 年龄小于30岁的所有人信息
         *  通过值过滤器，获取到低于30岁的所有信息
         * 		ValueFilter
         */
//        //值过滤器，用二进制比较
//        ValueFilter filter = new ValueFilter(CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));
//        //列限定符过滤器
//        QualifierFilter filter2 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("age")));
//        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter,filter2);
//        scan.setFilter(fl);


        /**
         * 10. 通过单列值过滤器，获取低于30岁的所有信息
         *         SingleColumnValueFilter
         */
        //单列值过滤器，用二进制比较
//        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),Bytes.toBytes("age"),
//                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));
//
//        FamilyFilter filter1 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f1")));
//
//        FilterList fl1 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter,filter1);
//
//        //单列值过滤器，用二进制比较
//        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("f2"),Bytes.toBytes("age"),
//                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));
//
//        FamilyFilter filter3 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f2")));
//
//        FilterList fl2 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter2,filter3);
//
//        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ONE, fl1,fl2);
//
//        scan.setFilter(fl);


        scan.setFilter(filter);

        ResultScanner scanner = table.getScanner(scan);

        for (Result rs : scanner) {
            List<Cell> cells = rs.listCells();

            for (Cell cell : cells) {
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));

                System.out.println(row + "/" + cf + "/" + col + "/" + value);
            }
        }
    }
}
