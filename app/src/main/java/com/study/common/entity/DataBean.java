package com.study.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Author:zx on 2019/9/2811:08
 */
public class DataBean implements Serializable {
    private static final long serialVersionUID = 7187668784524892290L;
    private int ticketSeriesId;
    private String ticketSeriesName;
    private String code;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected;
    private List<TicketTypeListBean> ticketTypeList;

    public DataBean() {
    }

    public int getTicketSeriesId() {
        return this.ticketSeriesId;
    }

    public void setTicketSeriesId(int ticketSeriesId) {
        this.ticketSeriesId = ticketSeriesId;
    }

    public String getTicketSeriesName() {
        return this.ticketSeriesName;
    }

    public void setTicketSeriesName(String ticketSeriesName) {
        this.ticketSeriesName = ticketSeriesName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TicketTypeListBean> getTicketTypeList() {
        return this.ticketTypeList;
    }

    public void setTicketTypeList(List<TicketTypeListBean> ticketTypeList) {
        this.ticketTypeList = ticketTypeList;
    }
}
