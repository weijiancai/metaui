package com.metaui.core.util.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.metaui.core.util.UString;

/**
 * JSON代码美化
 *
 * @author wei_jc
 * @since 1.0
 */
public class JsonFormat implements CodeFormat {
    @Override
    public String format(String code) throws Exception {
        if (UString.isEmpty(code)) {
            return code;
        }

        JSONObject obj = JSON.parseObject(code);

        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }
}
