package hello;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.util.UNumber;
import com.metaui.db.MyJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        String sql = "SELECT top 10 * from wm_op_shipping order by input_date";
        List<DataMap> list = template.queryForList(sql);
        for (DataMap map : list) {
            System.out.println(map.getString("shipping_id") + " : " + map.getString("shipping_code") + ", " + map.getInt("package_amount"));
            // 查询明细
            sql = "SELECT * FROM wm_op_shipping_item WHERE shipping_id=?";
            List<DataMap> items = template.queryForList(sql, map.get("shipping_id"));
            if (items.size() == 1 && items.get(0).getInt("package_amount") != map.getInt("package_amount")) {
                updatePackageAmount(items.get(0).getString("shipping_item_id"), map.getInt("package_amount"));
            } else {
                for (DataMap item : items) {

                }
            }
        }
    }

    public void updatePackageAmount(String itemId, int packageAmount) {
        System.out.println("--> " + itemId + " , " + packageAmount);
        String sql = "UPDATE wm_op_shipping_item SET package_amount=? WHERE shipping_item_id=?";
//        template.update(sql, packageAmount, itemId);
    }
}
