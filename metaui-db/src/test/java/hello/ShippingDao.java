package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/4.
 */
@Component
public class ShippingDao {
    @Autowired
    private JdbcTemplate template;

    public int getShippingCount() {
        String sql = "SELECT  count(1) from wm_op_shipping";
        return template.queryForObject(sql, int.class);
    }
}
