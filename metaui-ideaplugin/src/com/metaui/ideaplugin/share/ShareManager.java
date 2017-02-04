package com.metaui.ideaplugin.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.metaui.core.parser.http.JSoupParser;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/26.
 */
public class ShareManager {
    public List<Book> getBooks() throws IOException {
        String url = "http://192.168.31.110:8000/share/books/";
        JSoupParser parser = new JSoupParser(url);
        JSONArray array = parser.getJsonArray();
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.getJSONObject(i).getJSONObject("fields"));
        }
        String json = JSON.toJSONString(list);
        return JSON.parseArray(json, Book.class);
    }

    public DefaultTreeModel getBookTreeModel() throws IOException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        for (Book book : getBooks()) {
            root.add(new DefaultMutableTreeNode(book));
        }

        return new DefaultTreeModel(root);
    }

    public static void main(String[] args) throws IOException {
        ShareManager manager = new ShareManager();
        List<Book> list = manager.getBooks();
        System.out.println(list);
    }
}
