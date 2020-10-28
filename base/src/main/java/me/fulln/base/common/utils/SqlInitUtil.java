package me.fulln.base.common.utils;

import me.fulln.base.model.sql.CreateTableVO;
import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. 读取配置文件，获取文件存放地址
 * 2. 使用excelutil类将文件转成list<CreateTable>
 * 3. 遍历list 将结果值存入.sql文件中
 * 4. 使用mybatis进行数据库生成表
 *
 * @author : fulln
 * @version : JDK 1.7
 * @serial :
 * @date : Created in  15:21  2019/6/26.
 */
public class SqlInitUtil {

    private static List<CreateTableVO> tb;

    private void load() {

        String filePath = FileUtil.getProperty("loadFile");
        int sheetIndex = FileUtil.getProperty("sheetIndex") == null ? 0 : Integer.parseInt(FileUtil.getProperty("sheetIndex"));
        int skipRows = FileUtil.getProperty("skipRows") == null ? 0 : Integer.parseInt(FileUtil.getProperty("skipRows"));
        tb = new ArrayList<>();
        try {
            List<String[]> li = ExcelUtil.readToList(filePath, sheetIndex, skipRows);
            if (li != null) {
                li.forEach(lis -> {
                    List<String> str = Arrays.asList(lis);
                    CreateTableVO ctv = new CreateTableVO();
                    ctv.setId(Integer.parseInt(str.get(0)));
                    ctv.setFieldName(str.get(1));
                    ctv.setFieldDescribName(str.get(2));
                    ctv.setFieldType(str.get(3));
                    ctv.setFieldLength(str.get(4) == null ? null : Integer.parseInt(str.get(4)));
                    ctv.setDecimal(str.get(5) == null ? null : Integer.parseInt(str.get(5)));
                    ctv.setIsNull(str.get(6));
                    ctv.setDefaultValue(str.get(7));
                    ctv.setIsPrimaryKey(str.get(8) == null ? 0 : 1);
                    ctv.setRemark(str.get(13));
                    tb.add(ctv);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成sql文件
     *
     * @return
     */
    private StringBuffer mysqlData() {
        load();

        StringBuffer sb = new StringBuffer();
        //表名中文名称
        String tableName = FileUtil.getProperty("tablename");
        //表名
        String sheetName = FileUtil.getProperty("tableEngname");

        if (sheetName == null) {
            sheetName = ExcelUtil.Sheetname;
        }

        sb.append(" DROP TABLE IF EXISTS ").append(sheetName).append(";");
        sb.append(" CREATE TABLE `").append(sheetName).append("` (");

        int s = tb.size() - 1;

        for (CreateTableVO t : tb) {

            sb.append(" `").append(t.getFieldName()).append("` ");
            sb.append(" ").append(t.getFieldType());

            if (t.getFieldLength() != null) {
                sb.append("(").append(t.getFieldLength()).append(") ");
            }

            if (t.getDecimal() != null) {
                sb.append("(").append(t.getFieldLength()).append(")");
            }
            if (t.getIsPrimaryKey() == 1) {
                sb.append(" auto_increment ");
            } else {
                if (t.getIsNull() != null) {
                    sb.append(" " + t.getIsNull() + " ");
                }
            }

            if (t.getDefaultValue() != null) {
                sb.append(" DEFAULT " + t.getDefaultValue() + " ");
            } else {
                if (t.getIsNull() == null) {
                    sb.append(" DEFAULT '' ");
                }
            }
            sb.append(" COMMENT " + "'" + t.getFieldDescribName() + "'");
            if (t.getIsPrimaryKey() == 1) {
                sb.append(" PRIMARY KEY ");
            }
            if (s != 0) {
                sb.append(",");
            }
            s--;
        }


        sb.append(" )" + "ENGINE=InnoDB DEFAULT CHARSET=utf8 ");
        sb.append("COMMENT='").append(tableName).append("'");
        return sb;
    }

    /**
     * 写入.sql文件
     */
    public void writeToSql() {
        String loadPath = FileUtil.getProperty("loadFile");
        String tablename = FileUtil.getProperty("tablename");
        int idx = loadPath.lastIndexOf("/");
        loadPath = loadPath.substring(0, idx);

        String pathFile = FileUtil.getProperty("pathFile");
        String fileName = loadPath.trim() + "/" + tablename + ".sql";
        if (pathFile != null) {
            fileName = pathFile.trim() + "/" + tablename + ".sql";
        }
        FileUtil.writeToFile(FileUtils.getFile(fileName), mysqlData());
    }

    public static void main(String[] args) {
        new SqlInitUtil().writeToSql();
    }

}