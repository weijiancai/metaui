package sample;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wei_jc
 * @since 1.0
 */
public class PrintParams {
    private int pageWidth;
    private int pageHeight;
    private Integer topMargin = 0;
    private Integer leftMargin = 0;
    private Integer bottomMargin = 0;
    private Integer rightMargin = 0;
    private int pageSize = 1;

    private List<PackPrintable> pages = new ArrayList<>();

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public Integer getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(Integer topMargin) {
        this.topMargin = topMargin;
    }

    public Integer getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(Integer leftMargin) {
        this.leftMargin = leftMargin;
    }

    public Integer getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(Integer bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public Integer getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(Integer rightMargin) {
        this.rightMargin = rightMargin;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<PackPrintable> getPages() {
        return pages;
    }

    public void setPages(List<PackPrintable> pages) {
        this.pages = pages;
        for (PackPrintable params : pages) {
            params.setPrintParams(this);
        }
    }

    public void addPage(PackPrintable pageParams) {
        pages.add(pageParams);
        pageParams.setPrintParams(this);
    }
}
