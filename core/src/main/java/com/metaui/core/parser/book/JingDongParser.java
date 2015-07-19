package com.metaui.core.parser.book;

import com.metaui.core.util.UString;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wei_jc
 * @since 0.0.1
 */
public class JingDongParser implements IProductParser {
	private static final Logger log = Logger.getLogger(JingDongParser.class);

	public static final String SEARCH_URL = "http://search.jd.com/Search?enc=utf-8&book=y&keyword=";
	public static final String OTHER_SEARCH_URL = "http://search.jd.com/Search?enc=utf-8&keyword=";
	public static final String JOURNAL_SEARCH_URL = "http://search.jd.com/bookadvsearch?keyword=%s&isbn=%s";

	private String userId;
	private String isbn;

	public JingDongParser(String isbn) {
		this(null, isbn);
	}

	public JingDongParser(String userId, String isbn) {
		this.userId = userId;
		this.isbn = isbn;
	}

	@Override
	public List<IWebProduct> parse() {
		List<IWebProduct> prodList = new ArrayList<IWebProduct>();
		try {
			// ���������ҳ��
			// Document doc = Jsoup.connect(SEARCH_URL + isbn).get();
			Document doc;
			if(isbn.startsWith("977")) { // ��־����
				String year = Calendar.getInstance().get(Calendar.YEAR) + "";
				doc = get(String.format(JOURNAL_SEARCH_URL, year, isbn), "UTF-8");
			} else {
				doc = get(SEARCH_URL + isbn, "UTF-8");
			}
			if (doc == null) {
				return prodList;
			}
			Elements elements = doc.select("div.main div.right-extra div#plist div.item");
			IWebProduct prod;
			if (elements != null && elements.size() > 0) {
				for (Element element : elements) {
                    String text = element.select("ul.summary li.summary-service div.dd").first().text();
                    if (!text.contains("�� ���� ����")) {
                        continue;
                    }
					prod = parseProduct(element);
					if (prod != null) {
						prodList.add(prod);
					}
				}
			} else {
				doc = get(OTHER_SEARCH_URL + isbn, "UTF-8");
		        elements = doc.select("div.main div.right-extra div#plist ul.list-h li.item-book");
		        for (Element element : elements) {
                    String text = element.select("div.service").first().text();
                    if (!text.contains("�� ���� ����")) {
                        continue;
                    }
					prod = parseOtherProduct(element);
					if (prod != null) {
						prodList.add(prod);
					}
				}

			}
		} catch (Exception e) {
			log.error("���Ӿ�����վʧ�ܣ�", e);
		}

		return prodList;
	}

	private IWebProduct parseProduct(Element element) {
		Document detailDoc = null;
		try {
			WebProductImpl prod = new WebProductImpl();
			prod.setSourceSite(SiteName.JING_DONG.name());
			List<ProductPic> picList = new ArrayList<ProductPic>();

			// ȡ�������ͼƬ 160 * 160
			picList.add(new ProductPic(new URL(element.select("div.p-img a img").first().attr("data-lazyload")), false));

            Elements mElements;
			// ����ϸҳ��
			String detailUrl = element.select("div.p-img a").first().attr("href");
			detailDoc = get(detailUrl, "GBK");
			if (detailDoc == null) {
				return null;
			}
            // ȡ����
            prod.setName(detailDoc.select("div#name h1").first().ownText());
            // ȡ��ϸҳ��ͼƬ 280 * 280
            String picUrl = detailDoc.select("div#spec-n1 img").first().attr("src");
			if (!picUrl.equals("http://img10.360buyimg.com/n11/")) {
				picList.add(new ProductPic(new URL(picUrl), true));
			}

            // ȡ����
			Elements priceElement = detailDoc.select("li#summary-market div.dd");
			if(priceElement != null && priceElement.size() > 0) {
				prod.setPrice(priceElement.first().text().replace("��", "").replace("?", "").trim());
			}
            // ȡ����
			Elements authorElement = detailDoc.select("div#name div#product-authorinfo");
			if(authorElement != null && authorElement.size() > 0) {
				String authorStr = authorElement.text();
	            StringBuilder sb = new StringBuilder();
	            for(char c : authorStr.toCharArray()) {
	                if(c == '��') {
	                    prod.setAuthor(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else if(c == '��') {
	                    prod.setTranslator(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else if(c == '��') {
	                    prod.setPainter(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else {
	                    sb.append(c);
	                }
	            }
			} else {
				return null;
			}
            // ȡ������
//            prod.setPublishing(detailDoc.select("div.w div.right ul#summary li#summary-ph div.dd").first().text());
            // ȡ����ʱ��
            mElements = detailDoc.select("div.w div.right ul#summary li#summary-published div.dd");
            if(mElements != null && mElements.size() > 0) {
            	prod.setPublishDate(mElements.first().text());
            }

            // ȡISBN
//            prod.setIsbn(detailDoc.select("div.w div.right ul#summary li#summary-isbn div.dd").first().text());
            // ȡ��Ʒ����
            mElements = detailDoc.select("div.w div.right div#product-detail div#product-detail-1 > ul li");
            for (Element aElement : mElements) {
                String text = aElement.text().trim();
                if (text.startsWith("��Σ�")) { // ȡ���
                    prod.setBanci(text.substring(3).trim());
                } else if (text.startsWith("װ֡��")) { // ȡװ֡
                    prod.setPack(text.substring(3).trim());
                } else if (text.startsWith("ֽ�ţ�")) { // ȡֽ��
                    prod.setPaper(text.substring(3).trim());
                } else if (text.startsWith("ӡˢʱ�䣺")) { // ȡӡˢʱ��
                    prod.setPrintDate(text.substring(5).trim());
                } else if (text.startsWith("ӡ�Σ�")) { // ȡӡ��
                    prod.setPrintNum(text.substring(3).trim());
                } else if (text.startsWith("�������֣�")) { // ȡ��������
                    prod.setLanguage(text.substring(5).trim());
                } else if (text.startsWith("������")) { // ȡ����
                    prod.setKaiben(text.substring(3).trim());
                } else if (text.startsWith("ҳ����")) { // ȡҳ��
                    prod.setPageNum(text.substring(3).trim());
//                } else if (text.startsWith("���ߣ�")) { // ����
                    prod.setAuthor(text.substring(3).trim());
                } else if (text.startsWith("�ߴ磺")) { // �ߴ�
                    String[] strs = text.substring(3).trim().split(";");
                    if(strs.length == 1) {
                        prod.setSize(strs[0]);
                    } else if(strs.length == 2) {
                        prod.setSize(strs[0]);
                        prod.setWeight(strs[1].replace("kg", ""));
                    }
                    if(prod.getSize() != null) {
                        strs = prod.getSize().replace("cm", "").split("x");
                        if(strs.length == 3) {
                            prod.setLength(strs[0]);
                            prod.setWidth(strs[1]);
                            prod.setDeep(strs[2]);
                        }
                    }
                } else if (text.startsWith("�����磺")) { // ������
                    prod.setPublishing(text.substring(4).trim());
                } else if (text.startsWith("ISBN��")) { // ISBN
                    prod.setIsbn(text.substring(4).trim());
                }
            }

            // ȡ������Ϣ
            mElements = detailDoc.select("div.w div.right div#product-detail div#product-detail-1 > div.sub-m");
            for (Element aElement : mElements) {
                String title = aElement.select("div.sub-mt h3").text();
                String value = aElement.select("div.sub-mc").html();
                if("���ݼ��".equals(title)) { // ���ݼ��
                    prod.setContent(value);
                } else if("���߼��".equals(title)) { // ���߼��
                    prod.setAuthorIntro(value);
                } else if("������ժ".equals(title)) { // ȡ������ժ
                    prod.setExtract(value);
                } else if("�༭�Ƽ�".equals(title)) { // �༭�Ƽ�
                    prod.setHAbstract(value);
                } else if("ý������".equals(title)) { // ý������
                    prod.setMediaFeedback(value);
                } else if("Ŀ¼".equals(title)) { // Ŀ¼
                    prod.setCatalog(value);
                } else if("ǰ��".equals(title)) { // ǰ��
                    prod.setPrologue(value);
                }
            }

			// ����ͼƬ
			new DownloadPicture(picList);
			prod.setProductPic(picList);

			prod.setUserId(userId);

			return prod;
		} catch (Exception e) {
            e.printStackTrace();
			log.error(String.format("������������ͼ����Ϣ��%s��ʧ�ܣ�", isbn), e);
		}

		return null;
	}

	private IWebProduct parseOtherProduct(Element element) {
		Document detailDoc = null;
        try {
            WebProductImpl prod = new WebProductImpl();
            prod.setSourceSite(SiteName.JING_DONG.name());
            List<ProductPic> picList = new ArrayList<ProductPic>();

            // ȡ�������ͼƬ 160 * 160
            picList.add(new ProductPic(new URL(element.select("div.p-img a img").first().attr("data-lazyload")), false));
            // ����ϸҳ��
            String detailUrl = element.select("div.p-img a").first().attr("href");

            detailDoc = get(detailUrl, "GBK");
            // ȡ����
            prod.setName(detailDoc.select("div#name h1").first().ownText());
            // ȡ��ϸҳ��ͼƬ 280 * 280
            picList.add(new ProductPic(new URL(detailDoc.select("div#spec-n1 img").first().attr("src")), true));

            // ȡsummary
            Elements mElements = detailDoc.select("ul#summary");
            Element summaryElements = mElements.first();
            for (Element aElement : summaryElements.select("> li")) {
                String span = aElement.getElementsByTag("span").text();
                if ("�ݳ���/�����ߣ�".equals(span)) {
                    String values = aElement.text().replace("�ݳ���/�����ߣ�", "");
                    prod.setAuthor(UString.trim(values));
                } else if (span.contains("��") && span.contains("��") && span.contains("��")) {
                    prod.setIsbn(span.substring(6));
                }
            }

            // ȡ����
            Elements priceElement = detailDoc.select("li#summary-market div.dd");
            if(priceElement != null && priceElement.size() > 0) {
            	prod.setPrice(priceElement.first().text().substring(1));
            }

            // ȡ�༭�Ƽ�
            mElements = detailDoc.select("div.w div.right div#recommend-editor div.mc div.con");
            if (mElements.size() > 0) {
                prod.setHAbstract(mElements.first().html());
            }

            // TODO �������ݼ�����Ϣ
            mElements = detailDoc.select("div.w div.right-extra > div.m1 div.mt h2");
            for (Element aElement : mElements) {
                String text = aElement.ownText();
                if (text != null) {
                    text = text.trim();
                    String value = aElement.parent().parent().select("div.mc div.con").html();
                    if ("���ݼ��".equals(text)) { // ȡ���ݼ��
                        prod.setContent(value);
                    }  else if ("��Ŀ".equals(text)) { // ȡ��Ŀ
                        prod.setCatalog(value);
                    }
                }
            }

            // ����ͼƬ
			new DownloadPicture(picList);
			prod.setProductPic(picList);

			prod.setUserId(userId);

            return prod;
        } catch (Exception e) {
        	log.error(String.format("����������������/CD��Ϣ��%s��ʧ�ܣ�", isbn), e);
        }

        return null;
    }
    /**
     * HTTP GET����
     * ע�⣺Ŀǰֻ���ھ�����gb2312����������վ��utf8������ת���������
     *
     * @param url
     * @return ����Jsoup Document����
     */
    public static Document get(String url, String charset) {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");

        try {
            HttpResponse response = client.execute(httpGet);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.debug(String.format("HTTP����ʧ�ܣ�״̬��%s�������� ", response.getStatusLine().getStatusCode()));
                return null;
            }
            HttpEntity entity = response.getEntity();
            String content = new String(EntityUtils.toByteArray(entity), charset);

            return Jsoup.parse(content);
        } catch (Exception e) {
            log.debug("HTTP����ʧ�ܣ�����", e);
        } finally {
            httpGet.releaseConnection();
            client.getConnectionManager().shutdown();
        }
        return null;
    }
}
