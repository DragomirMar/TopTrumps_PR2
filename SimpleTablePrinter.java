// insert package and imports here

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleTablePrinter {

    private List<Object[]> rows = new ArrayList<>();

    public SimpleTablePrinter() {}

    public SimpleTablePrinter(final List<Object[]> rows) {
        if(rows == null) throw new IllegalArgumentException("rows must not be null.");
        this.rows = rows;
    }

    private static String getSeparator(final int[] lengths) {
        StringBuilder separatorBuilder = new StringBuilder("+");
        for (int i : lengths) {
            separatorBuilder.append("-".repeat(i + 2));
            separatorBuilder.append("+");
        }
        return separatorBuilder.toString();
    }

    private static int[] maxLengths(final List<Object[]> rows) {
        int[] max = new int[rows.get(0).length];
        for (Object[] row : rows) {
            for (int n = 0; n < row.length; ++n) {
                Object cell = row[n];
                max[n] = Math.max(cell.toString().length(), max[n]);
            }
        }
        return max;
    }

    public String toString() {
        int[] lengths = maxLengths(rows);
        final String separator = getSeparator(lengths);
        final StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append(separator).append('\n');
        for (Object[] row : rows) {
            final StringBuilder rowBuilder = new StringBuilder("|");
            for (int n=0; n < row.length; ++n) {
                String fmt = " %-"+lengths[n]+"s |";
                rowBuilder.append(fmt);
            }
            rowBuilder.append('\n');

            tableBuilder.append(String.format(rowBuilder.toString(), row));
            tableBuilder.append(separator).append('\n');
        }
        return tableBuilder.toString();
    }

    public void print(final OutputStream outputStream) throws IOException {
        outputStream.write(this.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void print(final List<Object[]> rows, final OutputStream outputStream) throws IOException {
        new SimpleTablePrinter(rows).print(outputStream);
    }

    public void addRows(final Collection<Object[]> rows) {
        this.rows.addAll(rows);
    }

    public void addRow(final Object[] row) {
        this.rows.add(row);
    }

    public void clear() {
        this.rows.clear();
    }
}
