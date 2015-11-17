package sample;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author wei_jc
 * @since 1.0
 */
public class PackPrintable implements Printable {
    private PackInfo info;
    private PrintParams printParams;

    private int x;
    private int y;
    private int width;
    private int height;
    private int currentY;
    private int currentX;

    public PackPrintable(PackInfo info, PrintParams printParams) {
        this.info = info;
        this.printParams = printParams;
        File file = new File("C:\\Users\\wei_jc\\Desktop\\1.oxps");
        if (file.exists()) {
            file.delete();
        }
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
        // 设置打印字体（字体名称、样式和点大小）
        Font font = new Font("楷体", Font.PLAIN, 9);
        g2.setFont(font);

        // 打印起始点坐标
        x = (int)pageFormat.getImageableX();
        y = (int)pageFormat.getImageableY();
        width = (int) pageFormat.getImageableWidth();
        height = (int) pageFormat.getImageableHeight();
        currentY = y;
        currentX = x;

        // 顶线
        g2.drawLine(x, y, width, y);
        // 包号
        g2.drawString("包号", x + 25, y + 20);
        // 字体高度
        int fontHeight = g2.getFontMetrics().getHeight();
        // 第几包
        g2.drawString("第" + info.getPackIdx() + "包", x + 20, y + 20 + fontHeight);
        // 条码
        Font barFont = new Font("C39HrP36DmTt", Font.PLAIN, 40);
        g2.setFont(barFont);
        int fontWidth = (int) g2.getFontMetrics().getStringBounds("*" + info.getPackCode() + "*", g2).getWidth();
        g2.drawString("*" + info.getPackCode() + "*", x + 83 + (width - 83 - fontWidth)/2, y + 35);
        currentY += 47;
        // 包号左边线
        g2.drawLine(x, y, x, currentY);
        // 包号右边线
        g2.drawLine(83, y, 83, currentY);
        g2.drawLine(width, y, width, currentY);
        // 包号下边线
        g2.drawLine(x, currentY, width, currentY);
//        currentY += 1;
        // 客户名称
        Font boldFont = new Font("楷体", Font.BOLD, 11);
        g2.setFont(boldFont);
        fontWidth = (int)g2.getFontMetrics().getStringBounds(info.getClientName(), g2).getWidth();
        g2.drawString(info.getClientName(), (width - fontWidth)/2, currentY + g2.getFontMetrics().getAscent());
        fontHeight = g2.getFontMetrics().getHeight();
        g2.drawLine(x, currentY, x, currentY + fontHeight);
        g2.drawLine(width, currentY, width, currentY + fontHeight);
        currentY += fontHeight;
        g2.drawLine(x, currentY, width, currentY);
//        currentY += 1;
        // 客户地址
        g2.setFont(font);
        g2.drawString(info.getClientAddr(), x, currentY + g2.getFontMetrics().getAscent());
        fontHeight = g2.getFontMetrics().getHeight();
        currentY += fontHeight;
        // 联系人，电话
        g2.drawString(info.getContactMan() + " " + info.getClientPhone(), x, currentY + g2.getFontMetrics().getAscent());
        g2.drawLine(x, currentY - fontHeight, x, currentY + fontHeight);
        g2.drawLine(width, currentY - fontHeight, width, currentY + fontHeight);
        currentY += fontHeight;
        g2.drawLine(x, currentY, width, currentY);
//        currentY += 1;
        // 商品名称，定价，数量
        g2.setFont(boldFont);
        fontHeight = g2.getFontMetrics().getHeight();
        drawString(g2, "商品名称", 190, 1, true);
        drawString(g2, "定价", 48, 1, true);
        drawString(g2, "数量", width - 190 - 48, 1, true);
        g2.drawLine(width, currentY - fontHeight, width, currentY + fontHeight);
        currentY += fontHeight;
        g2.drawLine(x, currentY, width, currentY);
        // 表格数据
        g2.setFont(font);
        int i = 0;
        for (Product product : info.getProducts()) {
            currentX = x;
            NumberFormat nf = new DecimalFormat("#,###.##");
            drawString(g2, product.getName(), 190, 0, true);
            drawString(g2, nf.format(product.getPrice()), 48, 2, true);
            drawString(g2, product.getAmount() + "", width - 190 - 48, 1, true);
            g2.drawLine(width, currentY - fontHeight, width, currentY + fontHeight);
            currentY += fontHeight;
            g2.drawLine(x, currentY, width, currentY);
            if(i++ == 3) {
                break;
            }
        }
        // 合并包信息
        if (info.getProducts().size() > 4) {
            currentX = x;
            Font itaFont = new Font("楷体", Font.BOLD | Font.ITALIC, 11);
            g2.setFont(itaFont);
            drawString(g2, "等共" + info.getTotalCount() + "个品种" + info.getTotalAmount() + "册拼包", width, 0, true);
            g2.drawLine(width, currentY - fontHeight, width, currentY + fontHeight);
            currentY += fontHeight;
            g2.drawLine(x, currentY, width, currentY);
        }

//        g2.drawLine((int)x, (int)y, (int)x, (int)height);
//        g2.drawLine((int)x, (int)height, (int)width, (int)height);
//        g2.drawLine((int)width, (int)y, (int)width, (int)height);
//        g2.drawLine((int)(width/2), (int)(y/2), (int)(width/2), (int)(height));

        return Printable.PAGE_EXISTS;
    }

    public void drawString(Graphics2D g2, String string, int width, int align, boolean leftLine) {
        int fontWidth = (int) g2.getFontMetrics().getStringBounds(string, g2).getWidth();
        int fontHeight = g2.getFontMetrics().getHeight();
        switch (align) {
            case 0: { // 左对齐
                g2.drawString(string, currentX, currentY + g2.getFontMetrics().getAscent());
                break;
            }
            case 1: { // 居中对齐
                g2.drawString(string, currentX + (width - fontWidth)/2, currentY + g2.getFontMetrics().getAscent());
                break;
            }
            case 2: { // 居右对齐
                g2.drawString(string, currentX + fontWidth, currentY + g2.getFontMetrics().getAscent());
            }
        }

        if (leftLine) {
            g2.drawLine(currentX, currentY, currentX, currentY + fontHeight + 2);
        }

        currentX += width;
    }
}
