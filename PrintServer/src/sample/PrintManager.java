package sample;

import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印管理器
 *
 * @author wei_jc
 * @since 1.0
 */
public class PrintManager {
    public static void print(PrintParams params, List<PackInfo> packInfos) {
        // 通俗理解就是书、文档
        Book book = new Book();
        // 设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(params.getPageWidth(), params.getPageHeight()); // 纸张大小
        int x = params.getLeftMargin() == null ? 0 : params.getLeftMargin();
        int y = params.getTopMargin() == null ? 0 : params.getTopMargin();
        int width = params.getRightMargin() == null ? params.getPageWidth() - x : params.getPageWidth() - params.getRightMargin() - x;
        int height = params.getBottomMargin() == null ? params.getPageHeight() - y : params.getPageHeight() - params.getBottomMargin() - y;
        p.setImageableArea(x, y, width, height); // A4(595 * 842) 设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        pf.setPaper(p);
        // 把PageFormat和Printable添加到书中，组成一个页面
        params.setPageSize(packInfos.size());
        for (PackInfo packInfo : packInfos) {
            book.append(new PackPrintable(packInfo, params), pf);
        }
//        book.append(new PrintTest("通过Paper设置页面的空白边距和可打印区域。必须"), pf);

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
        info.setClientName("团体会员团体会员团体会员团体会员团体会员团体会员团体会员团体会员");
        info.setContactMan("张三丰");
        info.setClientAddr("陕西省西安市雁展路1号曲江国际会展中心A馆");
        info.setClientPhone("186292692999");
        info.setTotalCount(6);
        info.setTotalAmount(6);
        info.setSellReason("2348288232");
        info.setSendDepartment("中国摄影出版社");
        info.setSendTel("233435382");
        info.setSendContact("李四");
        info.setProducts(createDataSource());
        List<PackInfo> list = new ArrayList<>();
        list.add(info);

        print(params, list);
    }

    private static List<Product> createDataSource() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("画册-美丽晋城", 2800.00, 1));
        list.add(new Product("画册-美加风光摄影集（袁伟发）", 2980.00, 1));
        list.add(new Product("画册-多彩世界：世界华侨华人摄影精品（袁伟发）", 200, 1));
//        list.add(new Product("画册-愚公移山（周振华）", 688.00, 1));
//        list.add(new Product("画册-静之观像（祁和亮）", 320, 1));
        return list;
    }
}
