package com.metaui.core.util;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.util.group.GroupFunction;
import com.metaui.core.util.group.GroupModel;

import java.util.*;

/**
 * List工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class UList {
    /**
     * 对List数据进行分组
     *
     * @param data
     * @param model
     * @return
     */
    public static List<DataMap> group(List<DataMap> data, GroupModel model) {
        if (data == null || data.size() == 0) {
            return new ArrayList<DataMap>();
        }

        String[] groupCols = model.getGroupCols();

        Map<String, List<DataMap>> listMap = new HashMap<String, List<DataMap>>();
        for (DataMap map : data) {
            String key = "";
            for (String groupCol : groupCols) {
                key += String.valueOf(map.get(groupCol)) + "_";
            }

            List<DataMap> list = listMap.get(key);
            if (list == null) {
                list = new ArrayList<DataMap>();
                listMap.put(key, list);
            }
            list.add(map);
        }


        List<DataMap> result = new ArrayList<DataMap>();

        for (Map.Entry<String, List<DataMap>> entry : listMap.entrySet()) {
            String key = entry.getKey();
            List<DataMap> list = entry.getValue();

            DataMap map = new DataMap();
            for (String col : groupCols) {
                map.put(col, list.get(0).get(col));
            }
            Map<String, Set<String>> countMap = new HashMap<String, Set<String>>();
            Map<String, Double> sumMap = new HashMap<String, Double>();
            for (DataMap dm : list) {
                for (Map.Entry<String, GroupFunction> computeMapEntry : model.getComputeColMap().entrySet()) {
                    if (GroupFunction.COUNT == computeMapEntry.getValue()) {
                        Set<String> set = countMap.get(computeMapEntry.getKey());
                        if (set == null) {
                            set = new HashSet<String>();
                            countMap.put(computeMapEntry.getKey(), set);
                        }
                        set.add(String.valueOf(dm.get(computeMapEntry.getKey())));
                    } else if (GroupFunction.SUM == computeMapEntry.getValue()) {
                        Double sum = sumMap.get(computeMapEntry.getKey());
                        if (sum == null) {
                            sumMap.put(computeMapEntry.getKey(), UNumber.toDouble(dm.get(computeMapEntry.getKey())));
                        } else {
                            sumMap.put(computeMapEntry.getKey(), sum + UNumber.toDouble(dm.get(computeMapEntry.getKey())));
                        }
                    }
                }
            }

            for (Map.Entry<String, GroupFunction> computeMapEntry : model.getComputeColMap().entrySet()) {
                if (GroupFunction.COUNT == computeMapEntry.getValue()) {
                    map.put(computeMapEntry.getKey(), countMap.get(computeMapEntry.getKey()));
                } else if (GroupFunction.SUM == computeMapEntry.getValue()) {
                    map.put(computeMapEntry.getKey(), sumMap.get(computeMapEntry.getKey()));
                }
            }

            result.add(map);
        }

        return result;
    }

    /**
     * 判读集合是否为空
     *
     * @param collection 集合
     * @return 如果为空 返回true
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 复制List
     *
     * @param source 源List
     * @param <T> 类型
     * @return 返回复制后的list
     */
    public static <T> List<T> copy(List<T> source) {
        List<T> result = new ArrayList<T>();
        result.addAll(source);
        return result;
    }
}
