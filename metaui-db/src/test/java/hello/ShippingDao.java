package hello;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.util.UNumber;
import com.metaui.core.util.UString;
import com.metaui.db.MyJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/4.
 */
@Component
public class ShippingDao {
    @Autowired
    private MyJdbcTemplate template;

    public int getShippingCount() {
        String sql = "SELECT  count(1) from wm_op_shipping";
        return template.queryForObject(sql, int.class);
    }

    public void update() {
        // 查询主表
        String sql = "SELECT * from wm_op_shipping where input_date < '2016-06-02 00:00:00' and input_date >='2016-04-01 00:11:03' order by input_date";
        List<DataMap> list = template.queryForList(sql);
        System.out.println("检索：" + list.size());
        int k = 0;
        for (DataMap map : list) {
            int packageAmount = map.getInt("package_amount");
            System.out.println(String.format("%5d %20s %s", k++, map.getString("shipping_code"), map.getString("input_date")));

            // 查询明细
            sql = "SELECT * FROM wm_op_shipping_item WHERE shipping_id=?";
            List<DataMap> items = template.queryForList(sql, map.get("shipping_id"));
            if (items.size() == 1) {
                if (items.get(0).getInt("package_amount") != packageAmount) {
                    updatePackageAmount(map, items.get(0), packageAmount);
                }
            } else {
                // 排序
                Collections.sort(items, new Comparator<DataMap>() {
                    @Override
                    public int compare(DataMap o1, DataMap o2) {
                        return o1.getString("receipt_id").compareTo(o2.getString("receipt_id"));
                    }
                });
                String findItemId = null;

                for (DataMap item : items) {
                    int itemPackAmount = item.getInt("package_amount");
                    if (itemPackAmount == packageAmount) {
                        findItemId = item.getString("shipping_item_id");
                        break;
                    }
                }

                if (UString.isEmpty(findItemId)) {
                    // 没有找到，取第一条更新件数，其余更新为0
                    for(int i = 0; i < items.size(); i++) {
                        if (i == 0) {
                            updatePackageAmount(map, items.get(i), packageAmount);
                        } else {
                            updatePackageAmount(map, items.get(i), 0);
                        }
                    }
                } else {
                    for (DataMap item : items) {
                        String itemId = item.getString("shipping_item_id");
                        if (itemId.equals(findItemId)) {
                            updatePackageAmount(map, item, packageAmount);
                        } else {
                            updatePackageAmount(map, item, 0);
                        }
                    }
                }
            }
        }

        System.out.println("最后一条记录：");
        System.out.println(list.get(list.size() - 1));
    }

    public void updatePackageAmount(DataMap map, DataMap itemMap, int packageAmount) {
        String itemId = itemMap.getString("shipping_item_id");
        int itemPackAmount = itemMap.getInt("package_amount");
        // 如果数量相同，则不更新
        if (packageAmount == itemPackAmount) {
            return;
        }
        System.out.println(map.getString("shipping_id") + " : " + map.getString("shipping_code") + ", " + map.getInt("package_amount"));
        System.out.println("--> " + itemId + " , " + itemMap.getString("receipt_id") + ", " + packageAmount);
        String sql = "UPDATE wm_op_shipping_item SET package_amount=? WHERE shipping_item_id=?";
        template.update(sql, packageAmount, itemId);
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"PX000023", "PX000038", "PX000018"};
        List<DataMap> list = new ArrayList<DataMap>();
        DataMap dataMap1 = new DataMap();
        dataMap1.put("receipt_id", "PX000023");
        DataMap dataMap2 = new DataMap();
        dataMap2.put("receipt_id", "PX000038");
        DataMap dataMap3 = new DataMap();
        dataMap3.put("receipt_id", "PX000018");

        list.add(dataMap1);
        list.add(dataMap2);
        list.add(dataMap3);

        Collections.sort(list, new Comparator<DataMap>() {
            @Override
            public int compare(DataMap o1, DataMap o2) {
                return o1.getString("receipt_id").compareTo(o2.getString("receipt_id"));
            }
        });

        for (DataMap map : list) {
            System.out.println(map);
        }
    }
}
