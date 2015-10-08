package com.metaui.fxbase.view.table.cell;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.util.UString;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.view.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 超链接Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class HyperlinkTableCell extends BaseTableCell {
    private Hyperlink hyperlink;
    private boolean isInit;
    private Paint originalTextFile;

    public HyperlinkTableCell(BaseTableColumn column, final TableFieldModel model) {
        super(column, model);

        hyperlink = new Hyperlink("");
        this.setGraphic(hyperlink);

        hyperlink.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                /*Meta meta = model.getMetaField().getMeta();
                MetaField refField = model.getMetaField().getRefField();
                Meta refMeta = meta.getRefMeta(model.getMetaField().getId());
                QueryBuilder queryBuilder = QueryBuilder.create(refMeta).add(refField.getOriginalName(), hyperlink.getText());
                QueryResult<DataMap> queryResult = refMeta.query(queryBuilder);

                FormProperty formProperty = new FormProperty(ViewManager.getViewByName(refMeta.getName() + "FormView"));
                formProperty.setFormType(FormType.READONLY);
                MUForm form = new MUForm(formProperty);
                form.setValues(queryResult.getRows().get(0));
                MUDialog.showCustomDialog(null, refMeta.getDisplayName(), form, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {
                        return null;
                    }
                });*/
            }
        });
    }

    private void init() {
        this.getTableRow().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (originalTextFile == null) {
                        originalTextFile = hyperlink.getTextFill();
                    }
                    hyperlink.setTextFill(Color.WHITE);
                } else {
                    hyperlink.setTextFill(originalTextFile);
                }
            }
        });
        isInit = true;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!isInit) {
            init();
        }
        if (UString.isNotEmpty(item)) {
            hyperlink.setText(item);
            this.setGraphic(hyperlink);
        } else {
            this.setGraphic(null);
        }
    }
}
