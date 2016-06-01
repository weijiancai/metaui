package hello;

import com.metaui.core.datasource.DataMap;
import com.metaui.db.MyJdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/6/1.
 */
@Component
public class YwPxDao {
    private Logger log = LoggerFactory.getLogger(YwPxDao.class);

    @Autowired
    private MyJdbcTemplate template;

    /**
     * 更新已发运没有出库的批销单出库
     */
    public void updatePxStockOut() {
        // 查询
        String sql = "select px_id from BOOKBASX.dbo.yw_px where receipts_class='XS' and stock_arrear='0' and is_destroy='0' and is_verify='1' and px_id in (select receipt_id from wm_op_shipping_item)";
        List<DataMap> list = template.queryForList(sql);
        System.out.println("检索：" + list.size());
        // 更新出库
        for (DataMap map : list) {
            sql = "update BOOKBASX.dbo.yw_px set stock_arrear='1', stock_date=GETDATE(), o_id_stock='0000000001' where px_id=?";
            template.update(sql, map.getString("px_id"));
        }
    }
}
