//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.runner.testworks.controller.suzhou;

import java.util.ArrayList;
import java.util.List;

public class EasyPage<T> {
    private long start;
    private long end;
    private long total;
    private List<T> items;

    public EasyPage(long start, long end) {
        if (start < 0L) {
            throw new IllegalArgumentException("start is negative.");
        } else if (end < start) {
            throw new IllegalArgumentException("end is less than start.");
        } else {
            this.start = start;
            this.end = end;
        }
    }

    public long getStart() {
        return this.start;
    }

    public long getEnd() {
        return this.end;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return (List)(this.items != null ? this.items : new ArrayList());
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getPageSize() {
        return this.end - this.start + 1L;
    }
}
