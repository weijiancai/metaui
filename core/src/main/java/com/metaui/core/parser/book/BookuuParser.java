package com.metaui.core.parser.book;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 博库书城解析器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BookuuParser extends ProductParser {
    private static final Logger log = Logger.getLogger(BookuuParser.class);
    public static final String SEARCH_URL = "http://search.bookuu.com/k_%s.html";
    private static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"; // 用户浏览器信息

    private String isbn;

    public BookuuParser(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public List<IWebProduct> parse() {
        List<IWebProduct> prodList = new ArrayList<IWebProduct>();

        String url = String.format(SEARCH_URL, isbn);
        try {
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
            Elements elements = doc.select("div.container div.main div.main-wrap div.books-list");
            for (Element element : elements) {
                IWebProduct product = parseProduct(element);
                if (product != null) {
                    prodList.add(product);
                }
            }
        } catch (IOException e) {
            log.error("连接博库网站失败！", e);
        }

        return prodList;
    }

    private IWebProduct parseProduct(Element element) {
        WebProductImpl product = new WebProductImpl();
        product.setSourceSite(SiteName.BOOKUU.name());
        product.setIsbn(isbn);
        List<ProductPic> picList = new ArrayList<ProductPic>();

        try {
            String detailUrl = element.select("h3.summary a").get(0).attr("href");
            Document doc = Jsoup.connect(detailUrl).get();

            // 取图片
            picList.add(new ProductPic(new URL(getPicUrl(element)), true));
            // 取书名
            product.setName(JsoupUtil.text(doc, "div.product-intro div.summary div#name h2"));
            // 取定价
            product.setPrice(JsoupUtil.text(doc, "div.product-intro div.sale span.original-price del").replace("?", ""));
            // 商品详情
            Elements elements = doc.select("div.detail-main div#goods-detail div.parameter ul li");
            for (Element aElement : elements) {
                String text = aElement.text();
                if (text.contains("作　者：")) {
                    product.setAuthor(text.replace("作　者：", "").trim());
                } else if (text.contains("出版社：")) {
                    product.setPublishing(text.replace("出版社：", "").trim());
                } else if (text.contains("开本：")) {
                    product.setKaiben(text.replace("开本：", "").replace("开", "").trim());
                } else if (text.contains("页数：")) {
                    product.setPageNum(text.replace("页数：", "").trim());
                } else if (text.contains("版次：")) {
                    product.setBanci(text.replace("版次：", "").trim());
                } else if (text.contains("字数：")) {
                    product.setWordCount(text.replace("字数：", "").trim());
                } else if (text.contains("印次：")) {
                    product.setPrintNum(text.replace("印次：", "").trim());
                } else if (text.contains("印刷时间：")) {
                    product.setPrintDate(text.replace("印刷时间：", "").trim());
                } else if (text.contains("出版时间：")) {
                    product.setPublishDate(text.replace("出版时间：", "").trim());
                } else if (text.contains("包装：")) {
                    product.setPack(text.replace("包装：", "").trim());
                }
            }

            elements = doc.select("div.detail-main div.detail-wrap div.txt-wrap div.section");
            for (Element aElement : elements) {
                String title = JsoupUtil.text(aElement, "div.txt-hd h5");
                if ("编辑推荐语".equals(title)) { // 编辑推荐
                    product.setHAbstract(JsoupUtil.html(aElement, "div.txt-bd"));
                } else if ("内容提要".equals(title)) { // 内容提要
                    product.setContent(JsoupUtil.html(aElement, "div.txt-bd"));
                } else if ("作者简介".equals(title)) { // 作者简介
                    product.setAuthorIntro(JsoupUtil.html(aElement, "div.txt-bd"));
                } else if ("媒体评论".equals(title)) { // 媒体评论
                    product.setMediaFeedback(JsoupUtil.html(aElement, "div.txt-bd"));
                } else if("目录".equals(title)) { // 目录
                    Elements es = aElement.select("div.txt-bd div#ml_s");
                    if (es.size() > 0) {
                        product.setCatalog(es.get(0).html());
                    } else {
                        product.setCatalog(JsoupUtil.html(aElement, "div.txt-bd div#ml"));
                    }
                } else if("精彩试读".equals(title)) { // 精彩页
                    Elements es = aElement.select("div.txt-bd div#JCY_s");
                    if (es.size() > 0) {
                        product.setExtract(es.get(0).html());
                    } else {
                        product.setExtract(JsoupUtil.html(aElement, "div.txt-bd div#JCY"));
                    }
                }
            }
           /* // 编辑推荐
            product.setHAbstract(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#bjtj_s"));
            // 内容提要
            product.setContent(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#nrty_s"));
            // 作者简介
            product.setAuthorIntro(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#zzjj_s"));
            // 目录
            product.setCatalog(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#ml_s"));
            // 精彩页
            product.setExtract(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#JCY_s"));
            // 前言
            product.setPrologue(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#XY_s"));*/

            // 下载图片
            new DownloadPicture(picList);
            product.setProductPic(picList);

        } catch (IOException e) {
            log.error(String.format("解析博库网上图书信息【%s】失败！", isbn), e);
            return null;
        }

        return product;
    }

    private String getPicUrl(Element element) {
        String picUrl = JsoupUtil.attr(element, "div.photo span.subpic a img", "src");
        return picUrl.replace("book_m", "book").replace("-fm-m.jpg", "-fm.jpg");
    }
}
