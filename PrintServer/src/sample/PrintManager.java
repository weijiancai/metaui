package sample;

import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ӡ������
 *
 * @author wei_jc
 * @since 1.0
 */
public class PrintManager {
    public static void print(PrintParams params, List<PackInfo> packInfos) {
        // ͨ���������顢�ĵ�
        Book book = new Book();
        // ���ó�����
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱�����ʵ�ʴ�ӡֽ�Ŵ�С�����
        Paper p = new Paper();
        p.setSize(params.getPageWidth(), params.getPageHeight()); // ֽ�Ŵ�С
        int x = params.getLeftMargin() == null ? 0 : params.getLeftMargin();
        int y = params.getTopMargin() == null ? 0 : params.getTopMargin();
        int width = params.getRightMargin() == null ? params.getPageWidth() - x : params.getPageWidth() - params.getRightMargin() - x;
        int height = params.getBottomMargin() == null ? params.getPageHeight() - y : params.getPageHeight() - params.getBottomMargin() - y;
        p.setImageableArea(x, y, width, height); // A4(595 * 842) ���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
        pf.setPaper(p);
        // ��PageFormat��Printable��ӵ����У����һ��ҳ��
        params.setPageSize(packInfos.size());
        for (PackInfo packInfo : packInfos) {
            book.append(new PackPrintable(packInfo, params), pf);
        }
//        book.append(new PrintTest("ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱���"), pf);

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

    public static void main(String[] args) {
        PrintParams params = new PrintParams();
        params.setPageWidth(283);
        params.setPageHeight(226);
        params.setLeftMargin(5);
        params.setRightMargin(5);
        params.setTopMargin(5);
        params.setBottomMargin(5);

        PackInfo info = new PackInfo();
        info.setPackIdx(25);
        info.setPackCode("00254A0025");
        info.setClientName("�����Ա�����Ա�����Ա�����Ա�����Ա�����Ա�����Ա�����Ա");
        info.setContactMan("������");
        info.setClientAddr("����ʡ��������չ·1���������ʻ�չ����A��");
        info.setClientPhone("186292692999");
        info.setTotalCount(6);
        info.setTotalAmount(6);
        info.setSellReason("2348288232");
        info.setSendDepartment("�й���Ӱ������");
        info.setSendTel("233435382");
        info.setSendContact("����");
        info.setProducts(createDataSource());
        List<PackInfo> list = new ArrayList<>();
        list.add(info);

        print(params, list);
    }

    private static List<Product> createDataSource() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("����-��������", 2800.00, 1));
        list.add(new Product("����-���ӷ����Ӱ����Ԭΰ����", 2980.00, 1));
        list.add(new Product("����-������磺���绪�Ȼ�����Ӱ��Ʒ��Ԭΰ����", 200, 1));
//        list.add(new Product("����-�޹���ɽ�����񻪣�", 688.00, 1));
//        list.add(new Product("����-��֮�����������", 320, 1));
        return list;
    }
}
