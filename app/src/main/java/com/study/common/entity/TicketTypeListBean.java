package com.study.common.entity;

import java.io.Serializable;

/**
 * Author:zx on 2019/9/2811:07
 */
public class TicketTypeListBean implements Serializable {
    private static final long serialVersionUID = -3259556719308328691L;
    private int ticketId;
    private String ticketName;
    private String noPrefix;
    private String saleDay;
    private boolean status;
    private int ticketSort;
    private String updatedAt;
    private String webLogoPath;
    private boolean standard;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected;

    public TicketTypeListBean() {
    }

    public int getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return this.ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getNoPrefix() {
        return this.noPrefix;
    }

    public void setNoPrefix(String noPrefix) {
        this.noPrefix = noPrefix;
    }

    public String getSaleDay() {
        return this.saleDay;
    }

    public void setSaleDay(String saleDay) {
        this.saleDay = saleDay;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTicketSort() {
        return this.ticketSort;
    }

    public void setTicketSort(int ticketSort) {
        this.ticketSort = ticketSort;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getWebLogoPath() {
        return this.webLogoPath;
    }

    public void setWebLogoPath(String webLogoPath) {
        this.webLogoPath = webLogoPath;
    }

    public boolean isStandard() {
        return this.standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }
}
