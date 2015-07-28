package a;

import java.awt.*;
import java.awt.print.*;

/**
 * 这个例子实现了打印字符串，线（包括虚线）和打印图片。而且通过Paper的setImageableArea可以设置打印的区域和边距，让开发者随意的设置打印的位置。
 * Test1下面的打印代码没有设置打印区域，默认为打印纸张的区域和边距，比如我们一般用的A4纸，打印的起点X和Y坐标则是72，72
 * @author wei_jc
 * @since 1.0.0
 */
public class PrintTest implements Printable {
    /**
     *
     * @param graphics 指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595*842点）
     * @param pageIndex 指明页号
     * @return
     * @throws PrinterException
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        System.out.println("pageIndex = " + pageIndex);
        Component c = null;
        // print string
        String str = "中华民族是勤劳、勇敢和富有智慧的伟大民族。";
        // 转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        // 设置打印颜色为黑色
        g2.setColor(Color.black);

        // 打印起始点坐标
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        switch (pageIndex) {
            case 0:
                // 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
                // Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog和DialogInput
                Font font = new Font("黑体", Font.PLAIN, 9);
                graphics.setFont(font); // 设置字体
                float[] dash1 = {2.0f};
                // 设置打印线的属性
                // 1. 线宽 2. 3. 不知道 4. 空白的宽度 5. 虚线的宽度 6. 偏移量
                g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
                float height = font.getSize2D(); // 字体高度
                System.out.println("x = " + x);
                // -1- 用Graphics2D直接输出
                // 首字符的基线（右下部）位于用户空间中的（x,y)位置处
                g2.drawLine(10, 10, 200, 300);

//                Image src = Toolkit.getDefaultToolkit().getImage("");
//                g2.drawImage(src, (int)x, (int)y, c);
//                int img_height = src.getHeight(c);
//                int img_width = src.getWidth(c);
                int img_height = 0;
                int img_width = 0;
                System.out.println("img_height = " + img_height + " img_width = " + img_width);

                g2.drawString(str, (float) x, (float) y + height + img_height);
                g2.drawLine((int) x, (int) (y + height + img_height + 10), (int) x + 200, (int) (y + height + img_height + 10));
//                g2.drawImage(src, (int)x, (int)(y + height + img_height + 11), c);

                return PAGE_EXISTS;
            default:
                return NO_SUCH_PAGE;
        }
    }

    public static void main(String[] args) {
        // 通俗理解就是书、文档
        Book book = new Book();
        // 设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(590, 840); // 纸张大小
        p.setImageableArea(10, 10, 590, 840); // A4(595 * 842) 设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        pf.setPaper(p);
        // 把PageFormat和Printable添加到书中，组成一个页面
        book.append(new PrintTest(), pf);

        // 获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        // 设置打印类
        job.setPageable(book);

        try {
            // 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            boolean a = job.printDialog();
            if(a) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
