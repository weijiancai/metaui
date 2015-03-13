package com.metaui.fxbase.ui.component.form;

import com.metaui.core.util.UString;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.ui.ICanInput;
import com.metaui.fxbase.ui.ValueConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckListView;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaUI CheckListView
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUCheckListView<T> extends BorderPane implements ICanInput<T> {
    private CheckListView<T> listView = new CheckListView<T>();
    private List<T> data;

    public MUCheckListView(List<T> list) {
        this.data = list;
        this.init();
    }

    public MUCheckListView() {
        super();
        this.init();
    }

    public void init() {
        if (data != null) {
            listView.getItems().addAll(data);
        }
        this.setCenter(listView);

        HBox box = new HBox(12);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(5));
        Button enableAll = new Button("选择所有");
        enableAll.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                listView.getCheckModel().selectAll();
            }
        });
        Button disableAll = new Button("取消所有");
        disableAll.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                listView.getCheckModel().clearSelection();
                /*for (int i = 0; i < data.size(); i++) {
//                    listView.getCheckModel().clearSelection(i);
                    listView.getSelectionModel().clearSelection(i);
                    listView.getCheckModel().clearSelection();
                }*/
            }
        });
        box.getChildren().addAll(enableAll, disableAll);
        this.setBottom(box);
    }

    public void selectAll() {
        listView.getCheckModel().selectAll();
    }

    public MultipleSelectionModel<T> getSelectionModel() {
        return listView.getSelectionModel();
    }

    public void setItems(List<T> list) {
        this.data = list;
        listView.getItems().clear();
        listView.getItems().addAll(list);
    }

    public CheckListView<T> getListView() {
        return listView;
    }

    // ======================== ICanInput ================================
    private String name;
    private ValueConverter<T> convert;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getInputValue() {
        return listView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setValueConvert(ValueConverter<T> convert) {
        this.convert = convert;
    }

    @Override
    public String getValueString() {
        if (convert != null) {
            List<String> list = new ArrayList<String>();
            for (T t : listView.getSelectionModel().getSelectedItems()) {
                list.add(convert.toString(t));
            }
            return UString.convert(list);
        }

        return UString.convert(listView.getSelectionModel().getSelectedItems());
    }

    public void setName(String name) {
        this.name = name;
    }
}
