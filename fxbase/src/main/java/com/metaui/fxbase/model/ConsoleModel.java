package com.metaui.fxbase.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * 控制台模型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ConsoleModel {
    private BooleanProperty isResetSystemOut = new SimpleBooleanProperty();
    private ObservableList<String> messages = FXCollections.observableArrayList(new ArrayList<>());
    private PrintStream out = System.out;
    private PrintStream myPrintStream = new PrintStream(new MyOutputStream());

    public ConsoleModel() {
        isResetSystemOut.addListener((observable, oldValue, newValue) -> {
            switchSystemOut(newValue);
        });
    }

    public boolean getIsResetSystemOut() {
        return isResetSystemOut.get();
    }

    public BooleanProperty isResetSystemOutProperty() {
        return isResetSystemOut;
    }

    public void setIsResetSystemOut(boolean isResetSystemOut) {
        this.isResetSystemOut.set(isResetSystemOut);
    }

    public ObservableList<String> getMessages() {
        return messages;
    }

    public void setMessages(ObservableList<String> messages) {
        this.messages = messages;
    }

    public void switchSystemOut(boolean isReset) {
        if (isReset) {
            System.setOut(myPrintStream);
        } else {
            System.setOut(out);
        }
    }

    public class MyOutputStream extends OutputStream {
        public void write(int arg0) throws IOException {
            // 写入指定的字节，忽略
        }

        public void write(byte[] data) throws IOException {
            // 追加一行字符串
            messages.add(new String(data));
        }

        public void write(byte data[], int off, int len) throws IOException {
            // 追加一行字符串中指定的部分，这个最重要
            messages.add(new String(data, off, len));
            // 移动TextArea的光标到最后，实现自动滚动
//            txtLog.setCaretPosition(txtLog.getText().length());
        }
    }
}
