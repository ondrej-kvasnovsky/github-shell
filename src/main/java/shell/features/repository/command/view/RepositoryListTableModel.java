package shell.features.repository.command.view;

import org.springframework.shell.table.BeanListTableModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;

public class RepositoryListTableModel<T> extends BeanListTableModel<T> {

    public RepositoryListTableModel(Iterable<T> list, LinkedHashMap<String, Object> header) {
        super(list, header);
    }

    @Override
    public Object getValue(int row, int column) {
        Object value = super.getValue(row, column);
        if (value instanceof Double) {
            Double percent = (Double) value * 100;
            NumberFormat formatter = new DecimalFormat("#0.00");
            return formatter.format(percent);
        }
        return value;
    }
}
