package com.metaui.fxbase.ui.component.guide;

import com.metaui.fxbase.ui.ICanInput;
import javafx.scene.Node;

/**
 * 向导模型类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class GuideModel {
    private String title;
    private Node content;
    private BaseGuide guide;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Node getContent() {
        return content;
    }

    public void setContent(Node content) {
        this.content = content;
    }

    public BaseGuide getGuide() {
        return guide;
    }

    public void setGuide(BaseGuide guide) {
        this.guide = guide;
    }

    public abstract boolean isOk();

    public abstract void doOpen();
    public abstract void doNext();

    public abstract ICanInput getInputControl();
}
