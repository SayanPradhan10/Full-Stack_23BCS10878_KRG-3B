package com.resumebuilder.config;

import com.itextpdf.kernel.geom.PageSize;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for PDF generation settings.
 * Manages PDF generation parameters like timeouts, page size, and margins.
 */
@Configuration
@ConfigurationProperties(prefix = "app.pdf")
public class PDFConfig {

    private int timeoutSeconds = 10;
    private String pageSize = "A4";
    private float marginTop = 36f;
    private float marginBottom = 36f;
    private float marginLeft = 36f;
    private float marginRight = 36f;

    // Getters and Setters
    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public float getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    /**
     * Get the iText PageSize object based on the configured page size.
     */
    public PageSize getPageSizeObject() {
        switch (pageSize.toUpperCase()) {
            case "A4":
                return PageSize.A4;
            case "LETTER":
                return PageSize.LETTER;
            case "LEGAL":
                return PageSize.LEGAL;
            default:
                return PageSize.A4;
        }
    }

    /**
     * Get timeout in milliseconds.
     */
    public long getTimeoutMillis() {
        return timeoutSeconds * 1000L;
    }
}