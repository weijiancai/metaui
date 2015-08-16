package com.metaui.fxbase.model;

import com.metaui.core.util.Callback;

/**
 * Action模型 Builder
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ActionModelBuilder {
    private ActionModel model;

    private ActionModelBuilder(ActionModel model) {
        this.model = model;
    }

    public ActionModelBuilder id(String id) {
        model.setId(id);
        return this;
    }

    public ActionModelBuilder name(String name) {
        model.setName(name);
        return this;
    }

    public ActionModelBuilder displayName(String displayName) {
        model.setDisplayName(displayName);
        return this;
    }

    public ActionModelBuilder sortNum(int sortNum) {
        model.setSortNum(sortNum);
        return this;
    }

    public ActionModelBuilder callback(Callback<Void> callback) {
        model.setCallback(callback);
        return this;
    }

    public ActionModel build() {
        return model;
    }

    public static ActionModelBuilder create() {
        return new ActionModelBuilder(new ActionModel());
    }
}
