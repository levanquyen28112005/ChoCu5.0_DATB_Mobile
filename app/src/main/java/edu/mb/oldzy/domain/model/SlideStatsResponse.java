package edu.mb.oldzy.domain.model;

public class SlideStatsResponse {
    private long totalSlides;
    private String latestCreatedAt;

    public SlideStatsResponse(long totalSlides, String latestCreatedAt) {
        this.totalSlides = totalSlides;
        this.latestCreatedAt = latestCreatedAt;
    }

    public long getTotalSlides() {
        return totalSlides;
    }

    public void setTotalSlides(long totalSlides) {
        this.totalSlides = totalSlides;
    }

    public String getLatestCreatedAt() {
        return latestCreatedAt;
    }

    public void setLatestCreatedAt(String latestCreatedAt) {
        this.latestCreatedAt = latestCreatedAt;
    }
}
