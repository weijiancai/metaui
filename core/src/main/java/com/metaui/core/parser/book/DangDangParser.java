package com.metaui.core.parser.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.metaui.core.util.UString;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 当当解析器
 *
 * @author wei_jc
 * @since 0.0.1
 */
public class DangDangParser implements IProductParser {
	private static final Logger log = Logger.getLogger(DangDangParser.class);
	
    public static final String SEARCH_URL = "http://searchb.dangdang.com/?key=";
    public static final String SUB_DETAIL_URL = "http://product.dangdang.com/detail/main.php?type=all&product_id=";
    private static final int TIME_OUT = 2 * 1000; // 10秒超时

    private String userId;
    private String isbn;

    public DangDangParser(String isbn) {
    	this(null, isbn);
    }
    
    public DangDangParser(String userId, String isbn) {
    	this.userId = userId;
        this.isbn = isbn;
    }

    @Override
    public List<IWebProduct> parse() {
    	List<IWebProduct> prodList = new ArrayList<IWebProduct>();
        try {
            // 打开搜索结果页面
            Document doc = Jsoup.connect(SEARCH_URL + isbn).timeout(TIME_OUT).get();
            Elements elements = doc.select("div.list_wrap div.list_main div.resultlist > ul > li");
            if (elements != null) {
            	IWebProduct prod;
                for (Element element : elements) {
                	prod = parseProduct(element);
                	if(prod != null) {
                		prodList.add(prod);
                	}
                }
            }
        } catch (Exception e) {
        	log.error("连接当当网站失败！", e);
        }

        return prodList;
    }
    
    public IWebProduct parseProduct(Element element) {
    	try {
    		WebProductImpl prod = new WebProductImpl();
            prod.setSourceSite(SiteName.DANG_DANG.name());
            List<ProductPic> picList = new ArrayList<ProductPic>();
            
            Elements mElements;
            // 打开详细页面
            String detailUrl;// = element.select("div.pic a").first().attr("href");
            detailUrl = "http://product.dangdang.com/22911745.html";

            Document detailDoc = Jsoup.connect(detailUrl)
                    .timeout(TIME_OUT)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();
            // 取书名
            prod.setName(detailDoc.select("div#showproduct_pub div.head h1").first().ownText().replace("&nbsp;", "").trim());
            // 取详细页面图片 350 * 350
            String picUrl = detailDoc.select("img#largePic").attr("wsrc");
            picList.add(new ProductPic(new URL(picUrl), true));
            
            // 取定价
            prod.setPrice(UString.getNumber(detailDoc.select("span#originalPriceTag").first().text()));
            
            mElements = detailDoc.select("div#showproduct_pub div.book_messbox div.m_t6");
            for(Element aElement : mElements) {
                Elements left = aElement.select("> div.show_info_left");
                Elements right = aElement.select("> div.show_info_right");
                String title = left.text();
                String text = right.text();

                if(title.contains("丛 书 名")) {
                    prod.setSeriesName(right.first().ownText().replace("丛 书 名：", ""));
                } else if(title.contains("作     者")) {
                    for (String str : text.split("，")) {
                        String value = str.substring(0, str.length() - 1);
                        if (str.endsWith("著")) {
                            prod.setAuthor(UString.trim(value));
                        } else if (str.endsWith("图") || str.endsWith("绘")) {
                            prod.setPainter(UString.trim(value));
                        } else if (str.endsWith("译")) {
                            prod.setTranslator(UString.trim(value));
                        }
                    }
                } else if(title.contains("出 版 社")) {
                    prod.setPublishing(text);
                } else if(left.first().ownText().contains("出版时间")) {
                    prod.setPublishDate(text);
                } else if(left.first().ownText().contains("ＩＳＢＮ")) {
                    prod.setIsbn(text);
                }
            }

            mElements = detailDoc.select("div#detail_all ul.key li");
            for(Element aElement : mElements) {
                String text = aElement.text();

                String banciStr = "版 次：";
                String pageNumStr = "页 数：";
                String wordCountStr = "字 数：";
                String printDateStr = "印刷时间：";
                String kaibenStr = "开 本：";
                String paperStr = "纸 张：";
                String printNumStr = "印 次：";
                String packStr = "包 装：";

                if (text.contains(banciStr)) {
                    prod.setBanci(UString.trim(text.substring(banciStr.length())));
                } else if (text.contains(pageNumStr)) {
                    prod.setPageNum(UString.trim(text.substring(pageNumStr.length())));
                } else if (text.contains(wordCountStr)) {
                    prod.setWordCount(UString.trim(text.substring(wordCountStr.length())));
                } else if (text.contains(printDateStr)) {
                    prod.setPrintDate(UString.trim(text.substring(printDateStr.length())));
                } else if (text.contains(kaibenStr)) {
                    prod.setKaiben(UString.trim(text.substring(kaibenStr.length())));
                } else if (text.contains(paperStr)) {
                    prod.setPaper(UString.trim(text.substring(paperStr.length())));
                } else if (text.contains(printNumStr)) {
                    prod.setPrintNum(UString.trim(text.substring(printNumStr.length())));
                } else if (text.contains("I S B N")) {
                    prod.setIsbn(UString.trim(text.substring(banciStr.length())));
                } else if (text.contains(packStr)) {
                    prod.setPack(UString.trim(text.substring(packStr.length())));
                }
            }

            // 取淘宝product_id
            String productId = detailDoc.select("body > span#pid_span").first().attr("product_id");
            // 取编辑推荐
            Document subDetailDoc = Jsoup.connect(SUB_DETAIL_URL + productId).timeout(TIME_OUT).get();
            mElements = subDetailDoc.select("div#detail_all > div#abstract > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setHAbstract(mElements.first().html());
            }
            // 取内容推荐
            mElements = subDetailDoc.select("div#detail_all > div#content > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setContent(mElements.first().html());
            }
            // 取作者简介
            mElements = subDetailDoc.select("div#detail_all > div#authorintro > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setAuthorIntro(mElements.first().html());
            }
            // 取目录
            mElements = subDetailDoc.select("div#detail_all > div#catalog > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setCatalog(mElements.first().html());
            }
            // 取媒体评论
            mElements = subDetailDoc.select("div#detail_all > div#mediafeedback > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setMediaFeedback(mElements.first().html());
            }
            // 试读章节
            mElements = subDetailDoc.select("div#detail_all > div#extract > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setExtract(mElements.first().html());
            }
            // 书摘插画
            mElements = subDetailDoc.select("div#detail_all > div#attach_image > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setExtract(prod.getExtract() + mElements.first().html());
            }

            // 下载图片
            new DownloadPicture(picList);
            prod.setProductPic(picList);
            prod.setUserId(userId);
            
            return prod;
    	} catch (Exception e) {
    		log.error(String.format("解析当当网上图书信息【%s】失败！", isbn), e);
    	}
    	
    	return null;
    }
}
