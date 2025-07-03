package model;

import java.sql.Timestamp;

public class TempleHistory {
    private int historyId;
    private String title;
    private String content;
    private String historicalPeriod;
    private String significance;
    private String imageUrl;
    private int adminId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TempleHistory() {}

    // Getters and Setters
    public int getHistoryId() {
        return historyId;
    }
    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getHistoricalPeriod() {
        return historicalPeriod;
    }
    public void setHistoricalPeriod(String historicalPeriod) {
        this.historicalPeriod = historicalPeriod;
    }

    public String getSignificance() {
        return significance;
    }
    public void setSignificance(String significance) {
        this.significance = significance;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}