package org.umutalacam.readingapp.order.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetOrdersRequest {
    private Integer pageIndex;
    private Integer pageSize;
    private String startDate;
    private String endDate;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() throws ParseException {
        if (startDate == null) return null;
        return sdf.parse(startDate);
    }

    public Date getEndDate() throws ParseException {
        if (endDate == null) return null;
        return sdf.parse(endDate);
    }

    public static SimpleDateFormat getSdf(){
        return sdf;
    }
}
