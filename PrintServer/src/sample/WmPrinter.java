package sample;/*
 * Copyright (c) 2007 Patrick Wright
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


import org.xhtmlrenderer.event.DocumentListener;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.swing.NaiveUserAgent;
import org.xhtmlrenderer.util.Uu;

import javax.print.*;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**

 *
 * @author rk
 */
public class WmPrinter implements Runnable, DocumentListener, Printable, PrintJobListener {
    private final static String template = "printingtemplate.xhtml";
    protected XHTMLPanel panel;
    protected Graphics2DRenderer g2r = null;

    /**
     * the base directory of the templates
     */
    private String base;

    /**
     * the logging mechanism log4j
     */
    private Logger log;

    /**
     * the tread that runs after initialization
     */
    private Thread runner;

    /**
     * the renderer used to render the xhtml dom tree into the page
     */
    private Java2DRenderer j2dr;

    private UserAgentCallback uac;
    private PrintService service;

    /**
     * the constructor of the cameventprinter: starts logging and starts the
     * thread
     *
     * @param url
     */
    public WmPrinter(PrintService service, String url) {
        this.service = service;
        log = Logger.getLogger(WmPrinter.class.getName());

        uac = new NaiveUserAgent();
        // </snip>

        log.info("template printing");

        panel = new XHTMLPanel();
        panel.getSharedContext().setPrint(true);
        panel.getSharedContext().setInteractive(false);
        panel.getSharedContext().setBaseURL(PrintServiceModel.getInstance().getUrl());
        panel.getSharedContext().getTextRenderer();

        try {
            panel.setDocument(url);
        } catch (Exception e) {
            WmDialog.showException(e);
        }

        start();
    }

    /**
     * we're starting the thread
     */
    public void start() {
        if (runner == null) {
            runner = new Thread(this, "Runner");
            runner.start();
        }
    }

    /**
     * we're running now
     */
    public void run() {
        try {
            // show the document in the log for debugging purpose
            log.fine("--------------------------------");

            // we want to use printing
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;

            PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
            attrs.add(OrientationRequested.PORTRAIT);
            attrs.add(PrintQuality.HIGH);
            attrs.add(new JobName("标签打印.rio", null));
            attrs.add(new PageRanges(1));
            attrs.add(new MediaPrintableArea(0, 0, 120, 80, MediaPrintableArea.MM));
            attrs.add(MediaSizeName.NA_8X10);
//            attrs.add(MediaSizeName.ISO_A4); // 可用
//            attrs.add(MediaSize.ISO.A4); // 不可用
//            attrs.add(Sides.DUPLEX);

//            PrintService service = PrintServiceLookup.lookupDefaultPrintService();

            // maybe we want to show the printer choice dialog

            // PrintService[] services = PrintServiceLookup
            // .lookupPrintServices(null, null);
            // PrintService service = ServiceUI.printDialog(null, 100, 100,
            // services, svc, flavor, attrs);

            if (service != null) {
                log.info("printer selected : " + service.getName());
                DocPrintJob job = service.createPrintJob();
                job.addPrintJobListener(this);
                PrintJobAttributeSet atts = job.getAttributes();
                Attribute[] arr = atts.toArray();
                for (int i = 0; i < arr.length; i++) {
                    log.fine("arr[" + i + "]= " + arr[0].getName());
                }

                Doc sdoc = new SimpleDoc(this, flavor, null);
//                SharedContext ctx = new SharedContext(uac);
//                ctx.setBaseURL(base);

                // print the doc as specified
                job.print(sdoc, attrs);

            } else {
                log.info("printer selection cancelled");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error loading file ", e);
            e.printStackTrace();
        }
        // stop the thread
        runner = null;
    }

    /**
     * The main function is made for debugging this application separately only.
     */
    public static void main(String args[]) {
        new WmPrinter(PrintServiceLookup.lookupDefaultPrintService(), "http://115.29.163.55:9587/html/wm/print/pack_tag/wy_zgsy.jsp?task_id=2D7A22520F0B4074B07DB2A4C1A82A2F");

    }

    public void documentStarted() {

    }

    public void documentLoaded() {
        log.info("document loaded");
    }

    public void onLayoutException(Throwable t) {

    }

    public void onRenderException(Throwable t) {

    }

    public int print(Graphics graphics, PageFormat pf, int page)
            throws PrinterException {
        log.info("print");

        try {
            Graphics2D g2 = (Graphics2D) graphics;

            if (g2r == null) {
                g2r = new Graphics2DRenderer();
                g2r.getSharedContext().setPrint(true);
                g2r.getSharedContext().setInteractive(false);
                g2r.getSharedContext().setDPI(72f);
                g2r.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
                g2r.getSharedContext().setUserAgentCallback(panel.getSharedContext().getUserAgentCallback());
                g2r.setDocument(panel.getDocument(), panel.getSharedContext().getUac().getBaseURL());
                g2r.getSharedContext().setReplacedElementFactory(panel.getSharedContext().getReplacedElementFactory());
                g2r.layout(g2, null);
                g2r.getPanel().assignPagePrintPositions(g2);
            }

            if (page >= g2r.getPanel().getRootLayer().getPages().size()) {
                return Printable.NO_SUCH_PAGE;
            }

            // render the document
            g2r.getPanel().paintPage(g2, page);

            return Printable.PAGE_EXISTS;
        } catch (Exception ex) {
            Uu.p(ex);
            return Printable.NO_SUCH_PAGE;
        }
    }

    public void printDataTransferCompleted(PrintJobEvent pje) {
        log.info("print data transfer completed");
    }

    public void printJobCanceled(PrintJobEvent pje) {
        log.info("print job cancelled");
    }

    public void printJobCompleted(PrintJobEvent pje) {
        log.info("print job completed");
    }

    public void printJobFailed(PrintJobEvent pje) {
        log.severe("print job failed");
    }

    public void printJobNoMoreEvents(PrintJobEvent pje) {
        log.info("print job no more events");
    }

    public void printJobRequiresAttention(PrintJobEvent pje) {
        log.info("print job requires attention");
    }
}