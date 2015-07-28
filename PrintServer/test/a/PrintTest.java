package a;

import java.awt.*;
import java.awt.print.*;

/**
 * �������ʵ���˴�ӡ�ַ������ߣ��������ߣ��ʹ�ӡͼƬ������ͨ��Paper��setImageableArea�������ô�ӡ������ͱ߾࣬�ÿ�������������ô�ӡ��λ�á�
 * Test1����Ĵ�ӡ����û�����ô�ӡ����Ĭ��Ϊ��ӡֽ�ŵ�����ͱ߾࣬��������һ���õ�A4ֽ����ӡ�����X��Y��������72��72
 * @author wei_jc
 * @since 1.0.0
 */
public class PrintTest implements Printable {
    /**
     *
     * @param graphics ָ����ӡ��ͼ�λ���
     * @param pageFormat ָ����ӡҳ��ʽ��ҳ���С�Ե�Ϊ������λ��1��Ϊ1Ӣ���1/72��1Ӣ��Ϊ25.4���ס�A4ֽ����Ϊ595*842�㣩
     * @param pageIndex ָ��ҳ��
     * @return
     * @throws PrinterException
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        System.out.println("pageIndex = " + pageIndex);
        Component c = null;
        // print string
        String str = "�л����������͡��¸Һ͸����ǻ۵�ΰ�����塣";
        // ת����Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        // ���ô�ӡ��ɫΪ��ɫ
        g2.setColor(Color.black);

        // ��ӡ��ʼ������
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        switch (pageIndex) {
            case 0:
                // ���ô�ӡ���壨�������ơ���ʽ�͵��С�����������ƿ�������������߼����ƣ�
                // Javaƽ̨���������������ϵ�У�Serif��SansSerif��Monospaced��Dialog��DialogInput
                Font font = new Font("����", Font.PLAIN, 9);
                graphics.setFont(font); // ��������
                float[] dash1 = {2.0f};
                // ���ô�ӡ�ߵ�����
                // 1. �߿� 2. 3. ��֪�� 4. �հ׵Ŀ�� 5. ���ߵĿ�� 6. ƫ����
                g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
                float height = font.getSize2D(); // ����߶�
                System.out.println("x = " + x);
                // -1- ��Graphics2Dֱ�����
                // ���ַ��Ļ��ߣ����²���λ���û��ռ��еģ�x,y)λ�ô�
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
        // ͨ���������顢�ĵ�
        Book book = new Book();
        // ���ó�����
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱�����ʵ�ʴ�ӡֽ�Ŵ�С�����
        Paper p = new Paper();
        p.setSize(590, 840); // ֽ�Ŵ�С
        p.setImageableArea(10, 10, 590, 840); // A4(595 * 842) ���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
        pf.setPaper(p);
        // ��PageFormat��Printable��ӵ����У����һ��ҳ��
        book.append(new PrintTest(), pf);

        // ��ȡ��ӡ�������
        PrinterJob job = PrinterJob.getPrinterJob();
        // ���ô�ӡ��
        job.setPageable(book);

        try {
            // ������printDialog��ʾ��ӡ�Ի������û�ȷ�Ϻ��ӡ��Ҳ����ֱ�Ӵ�ӡ
            boolean a = job.printDialog();
            if(a) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
