package com.github.tatarhy.pdfjs;

import java.io.Serializable;

public class PdfPageInfo implements Serializable {
    private final int pagePosition;
    private final int pageCount;

    private PdfPageInfo(int pagePosition, int pageCount) {
        this.pagePosition = pagePosition;
        this.pageCount = pageCount;
    }

    public boolean isFirst() {
        return pagePosition <= 1;
    }

    public boolean isLast() {
        return pagePosition >= pageCount;
    }

    public int getPagePosition() {
        return pagePosition;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return pagePosition + "/" + pageCount;
    }

    static public PdfPageInfo of(int pagePosition, int pageCount) {
        return new PdfPageInfo(pagePosition, pageCount);
    }
}
