package com.metaui.core.parser.mobile;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.util.Callback;

import java.util.List;

/**
 * 手机号码Action类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MobileNumberAction {
    public void fetchMobileNumber() throws Exception {
        final Meta meta = MetaManager.getMeta("MobileNumber");

        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber(new Callback<List<MobileNumber>>() {
            @Override
            public void call(List<MobileNumber> result, Object... obj) throws Exception {
                for (MobileNumber mobileNumber : result) {
                    DataMap map = new DataMap();
                    map.put("code", mobileNumber.getCode());
                    map.put("province", mobileNumber.getProvince());
                    map.put("city", mobileNumber.getCity());
                    map.put("card_type", mobileNumber.getCardType());
                    map.put("operators", mobileNumber.getOperators());
                    map.put("code_segment", mobileNumber.getCodeSegment());
                    meta.insertRow(map);
                }
                meta.save();
            }
        });

        fetchMobileNumber.fetch();
    }
}
