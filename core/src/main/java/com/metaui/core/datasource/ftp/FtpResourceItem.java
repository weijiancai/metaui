package com.metaui.core.datasource.ftp;

import com.metaui.core.datasource.ResourceItem;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * Ftp资源项
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FtpResourceItem extends ResourceItem {
    private Date lastModified;
    private long size;
    private String type;

    @Override
    public String getContent() throws Exception {
        return null;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return null;
    }

    @Override
    public URI getURI() throws Exception {
        return null;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
