package com.metaui.fxbase.view.form;

import com.metaui.core.util.UDate;
import com.metaui.core.util.UString;
import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.dom4j.util.UserDataAttribute;

import java.time.LocalDate;

/**
 * MetaUI 日期控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUDate extends BaseFormField {
    private DatePicker datePicker;
    private StringProperty value;

    public MUDate(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        value = new SimpleStringProperty();

        datePicker = new DatePicker();
        datePicker.setPrefWidth(model.getWidth());
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return UDate.dateToString(date);
            }

            @Override
            public LocalDate fromString(String date) {
                return UDate.toLocalDate(date);
            }
        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            value.set(UDate.dateToString(newValue));
        });
        /*datePicker.getEditor().setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 1) {
                    fireEvent(new FormFieldClickEvent((MUDate.this)));
                }
            }
        });*/
        /*value.addListener((observable, oldValue, newValue) -> {
            setValue(newValue);
        });*/

        this.getChildren().add(datePicker);
    }

    @Override
    public void setValue(String value) {
        if (UString.isNotEmpty(value)) {
            datePicker.setValue(UDate.toLocalDate(value));
        } else {
            datePicker.setValue(null);
        }
    }

    @Override
    protected StringProperty valueProperty() {
        return value;
    }

    public String getDate() {
        LocalDate date = datePicker.getValue();
        if (date != null) {
            return UDate.dateToString(date);
        }

        return null;
    }
}
