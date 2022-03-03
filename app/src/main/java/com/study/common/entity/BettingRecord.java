package com.study.common.entity;

/**
 * Author:zx on 2019/9/2509:59
 */
public class BettingRecord {
    private String orderId;
    private String nextOrderId;
    private String preOrderId;
    private String ticketName;
    private String methodName;
    private String betTime;
    private String playId;
    private String issue;
    private String betNum;
    private String betAmount;
    private String prize;
    private String state;
    private boolean canCancel;
    private int diskSurface;
    private boolean isTrace;

    public BettingRecord(String ticketName, String playId, String issue, String betAmount, String prize) {
        this.ticketName = ticketName;
        this.playId = playId;
        this.issue = issue;
        this.betAmount = betAmount;
        this.prize = prize;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNextOrderId() {
        return nextOrderId;
    }

    public void setNextOrderId(String nextOrderId) {
        this.nextOrderId = nextOrderId;
    }

    public String getPreOrderId() {
        return preOrderId;
    }

    public void setPreOrderId(String preOrderId) {
        this.preOrderId = preOrderId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodname) {
        this.methodName = methodname;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBetNum() {
        return betNum;
    }

    public void setBetNum(String betNum) {
        this.betNum = betNum;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public int getDiskSurface() {
        return diskSurface;
    }

    public void setDiskSurface(int diskSurface) {
        this.diskSurface = diskSurface;
    }

    public boolean isTrace() {
        return isTrace;
    }

    public void setTrace(boolean trace) {
        isTrace = trace;
    }
}
