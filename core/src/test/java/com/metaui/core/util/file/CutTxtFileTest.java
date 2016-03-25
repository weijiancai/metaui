package com.metaui.core.util.file;

import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.JdbcDrivers;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.util.Callback;
import com.metaui.core.util.UFile;
import org.junit.Test;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2015/12/19.
 */
public class CutTxtFileTest {
    private int count = 1;
    private int lineCount = 1;
    private boolean isInsert;
    private String tableName = "";

    @Test
    public void testCutByLine() throws Exception {
        String file = "C:\\Users\\weiji\\Downloads\\验收库\\41-最后.sql";
        CutTxtFile cutFile = new CutTxtFile(file);
        cutFile.setCutRows(1000);
        Map<String, String> map = new HashMap<String, String>();
        map.put("ECARGOTESTB.", "");
        map.put("\"ECARGOTESTB\".", "");
        cutFile.setReplaceMap(map);
        cutFile.setCharset("GBK");

        cutFile.cutByLine();
    }

    @Test
    public void testImpData() throws Exception {
        String file = "C:\\Users\\weiji\\Downloads\\验收库\\41-最后.sql";
        final PrintWriter pw = new PrintWriter("C:\\Users\\weiji\\Downloads\\验收库\\41_clob.sql");
        final String[] clobTables = new String[]{"POLICYOTHERINFO", "PLAN_TABLE", "ELECTRONICPOLICY", "FREIGHTDETAILFILE", "FREIGHTPOLICY", "POLICYOTHERINFO"};

        DBDataSource dataSource = new DBDataSource("ecargo", JdbcDrivers.ORACLE, "jdbc:oracle:thin:@//192.168.31.115:1521/orcl", "ecargo_publish", "ecargo", "");
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        final StringBuilder sb = new StringBuilder();
        UFile.readFile(file, "GBK", new Callback<String, Boolean>() {
            @Override
            public Boolean call(String line, Object... obj) throws Exception {
                lineCount++;
                if (line.startsWith("REM ") || line.startsWith("SET DEFINE") || lineCount < 3548132) {
                    return true;
                }

                if(line.startsWith("Insert into")) {
                    isInsert = true;
                    // 提交上一次插入
                    if (sb.length() > 0) {
                        System.out.println(lineCount + " - " + count + " - " + tableName);
                        try {
                            template.update(sb.toString());
                        } catch (SQLException e) {
                            if (e.getMessage() != null && e.getMessage().contains("ECARGO_PUBLISH.SYS_C0012224")) {
                                System.err.println("插入重复值");
                            } else {
                                throw e;
                            }
                        }
                        template.commit();
                        count++;
                        // 清除内容
                        sb.delete(0, sb.length());
                        isInsert = false;
                    }
                }

                if(isInsert) {
                    line = line.replace("ECARGOTESTB.", "");
                    if (line.startsWith("Insert into")) {
                        tableName = line.substring(12, line.indexOf(" ", 12));
                    }
                    if (tableName.equals("PORT_OLD")) {
                        return true;
                    }

                    if(Arrays.binarySearch(clobTables, tableName) >= 0) {
                        pw.write(line);
                        pw.flush();
                    } else {
                        sb.append(line);
                    }
                }


                // 一万条数据一提交
                /*if(count % 10000 == 0) {
                    template.commit();
                    template.newConnection();
                }*/

                return true;
            }
        });

        if (sb.length() > 0) {
            template.update(sb.toString());
        }
        template.commit();
        // 关闭文件
        pw.close();
    }
}