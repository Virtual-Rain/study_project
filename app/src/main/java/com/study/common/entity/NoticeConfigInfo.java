package com.study.common.entity;

import java.io.Serializable;
import java.util.List;

public class NoticeConfigInfo implements Serializable {
    public Number seriesId;
    public String seriesName;
    public String code;
    public List<TicketData> ticketList;
    public List<PlayData>  playList;
    public boolean checked;

    public Number getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Number seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TicketData> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketData> ticketList) {
        this.ticketList = ticketList;
    }

    public List<PlayData> getPlayList() {
        return playList;
    }

    public void setPlayList(List<PlayData> playList) {
        this.playList = playList;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
