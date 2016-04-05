package com.metaui.core.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 剪贴板工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class UClipboard {
    public static void setContent(String string) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(string);
        clipboard.setContent(content);
    }

    public static String getString() {
        return Clipboard.getSystemClipboard().getString();
    }
}
