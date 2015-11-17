package sample;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * @author wei_jc
 * @since 1.0
 */
public class PackPrintable implements Printable {
    private PackInfo info;
    private PrintParams printParams;

    public PackPrintable(PackInfo info, PrintParams printParams) {
        this.info = info;
        this.printParams = printParams;
    }

    public PrintParams getPrintParams() {
        return printParams;
    }

    public void setPrintParams(PrintParams printParams) {
        this.printParams = printParams;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if ( pageIndex < 0 || pageIndex >= printParams.getPageSize()) {
            return Printable.NO_SUCH_PAGE;
        }

        // 转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        // 设置打印颜色为黑色
        g2.setColor(Color.black);

        // 打印起始点坐标
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();
        double width = pageFormat.getImageableWidth();
        double height = pageFormat.getImageableHeight();

        g2.drawLine((int)x, (int)y, (int)width, 1);

        return Printable.PAGE_EXISTS;
    }
}
