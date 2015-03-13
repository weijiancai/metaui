package com.metaui.fxbase.ui.component.form;

import com.metaui.core.dict.FormType;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.ICanQuery;
import com.metaui.core.ui.layout.property.FormFieldProperty;
import com.metaui.core.util.UDate;
import com.metaui.core.util.UString;
import com.metaui.core.util.UUIDUtil;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.core.ui.IValue;
import com.metaui.fxbase.ui.event.FormFieldClickEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFormField extends HBox implements IValue, ICanQuery {
    protected FormFieldProperty config;
    protected boolean isAddQueryMode;
    protected boolean isQuery;
    private Hyperlink btnQueryModel;
    private List<Condition> list = new ArrayList<Condition>();
    private StringProperty value = new SimpleStringProperty();

    public BaseFormField(FormFieldProperty property) {
        this.config = property;
        this.isQuery = (config.getFormProperty().getFormType() == FormType.QUERY);
        this.isAddQueryMode = (config.getFormProperty().getFormType() == FormType.QUERY);
    }

    protected BaseFormField() {
    }

    protected abstract void initPrep();

    protected void init() {
        initPrep();

        this.setPrefWidth(config.getWidth());
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(5);
        // 设置默认值
        String value = config.getDefaultValue();
        if ("GUID()".equals(value)) {
            if(isQuery) {
                this.setValue("");
            } else {
                this.setValue(UUIDUtil.getUUID());
            }
        } else if ("SYSDATE()".equals(value)) {
            if (isQuery) {
                this.setValue("");
            } else {
                this.setValue(UDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
        } else {
            this.setValue(config.getDefaultValue());
        }

        for (Node node : getControls()) {
            node.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
                @Override
                public void doHandler(MouseEvent event) throws Exception {
                    if (event.getClickCount() == 1) {
                        fireEvent(new FormFieldClickEvent((BaseFormField.this)));
                    }
                }
            });
        }

        initAfter();
    }

    protected void initAfter() {
        this.getChildren().addAll(getControls());

        if(isAddQueryMode) {
            btnQueryModel = new Hyperlink("=");
            btnQueryModel.setBorder(null);
            btnQueryModel.setMinWidth(30);
            btnQueryModel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = btnQueryModel.getText();
                    if (text.equals("=")) {
                        setQueryModel("!=");
                    } else if (text.equals("!=")) {
                        setQueryModel(">");
                    } else if (text.equals(">")) {
                        setQueryModel(">=");
                    } else if (text.equals(">=")) {
                        setQueryModel("<");
                    } else if (text.equals("<")) {
                        setQueryModel("<=");
                    } else if (text.equals("<=")) {
                        setQueryModel("%%");
                    } else if (text.equals("%%")) {
                        setQueryModel("*%");
                    } else if (text.equals("*%")) {
                        setQueryModel("%*");
                    } else if (text.equals("%*")) {
                        setQueryModel("null");
                    } else if (text.equals("null")) {
                        setQueryModel("not null");
                    } else if (text.equals("not null")) {
                        setQueryModel("=");
                    }
                }
            });

            // 查看查询模式上下文菜单
            MenuItem equal = new MenuItem("=");
            equal.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("=");
                }
            });
            MenuItem notEqual = new MenuItem("!=");
            notEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel("!=");
                }
            });
            MenuItem greater = new MenuItem(">");
            greater.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel(">");
                }
            });
            MenuItem greaterEqual = new MenuItem(">=");
            greaterEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel(">=");
                }
            });
            MenuItem less = new MenuItem("<");
            less.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<");
                }
            });
            MenuItem lessEqual = new MenuItem("<=");
            lessEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<=");
                }
            });
            MenuItem like = new MenuItem("%%");
            like.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%%");
                }
            });
            MenuItem leftLike = new MenuItem("*%");
            leftLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("*%");
                }
            });
            MenuItem rightLike = new MenuItem("%*");
            rightLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%*");
                }
            });
            MenuItem nullItem = new MenuItem("null");
            nullItem.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("null");
                }
            });
            MenuItem notNull = new MenuItem("not null");
            notNull.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("not null");
                }
            });
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(equal, notEqual, greater, greaterEqual, less, lessEqual, like, leftLike, rightLike);
            btnQueryModel.setContextMenu(contextMenu);
            this.getChildren().add(btnQueryModel);

            // 设置查询模式
            setQueryModel(config.getQueryModel().toSqlModel());
        }
    }

    private void setQueryModel(String queryModel) {
        btnQueryModel.setText(queryModel);
        if (queryModel.equals("=")) {
            config.setQueryModel(QueryModel.EQUAL);
        } else if (queryModel.equals("!=")) {
            config.setQueryModel(QueryModel.NOT_EQUAL);
        } else if (queryModel.equals(">")) {
            config.setQueryModel(QueryModel.GREATER_THAN);
        } else if (queryModel.equals(">=")) {
            config.setQueryModel(QueryModel.GREATER_EQUAL);
        } else if (queryModel.equals("<")) {
            config.setQueryModel(QueryModel.LESS_THAN);
        } else if (queryModel.equals("<=")) {
            config.setQueryModel(QueryModel.LESS_EQUAL);
        } else if (queryModel.equals("%%")) {
            config.setQueryModel(QueryModel.LIKE);
        } else if (queryModel.equals("*%")) {
            config.setQueryModel(QueryModel.LEFT_LIKE);
        } else if (queryModel.equals("%*")) {
            config.setQueryModel(QueryModel.RIGHT_LIKE);
        } else if (queryModel.equals("null")) {
            config.setQueryModel(QueryModel.NULL);
        } else if (queryModel.equals("not null")) {
            config.setQueryModel(QueryModel.NOT_NULL);
        }
    }

    @Override
    public List<Condition> getConditions() {
        list.clear();
        if (UString.isNotEmpty(value())) {
            list.add(new Condition(config.getMetaField().getOriginalName(), config.getQueryModel(), value(), MetaDataType.STRING));
        }

        return list;
    }

    @Override
    public StringProperty valueProperty() {
        return value;
    }

    @Override
    public void setValue(String value) {
        valueProperty().set(value);
    }

    protected abstract Node[] getControls();

    public FormFieldProperty getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return config.getName();
    }

    @Override
    public MetaField getMetaField() {
        return config.getMetaField();
    }

    @Override
    public String getDefaultValue() {
        return config.getDefaultValue();
    }
}
