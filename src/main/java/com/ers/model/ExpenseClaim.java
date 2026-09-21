package com.ers.model;

import java.time.LocalDate;

public class ExpenseClaim {
    private int claimId;
    private int employeeId;
    private String claimDesc;
    private double claimAmount;
    private LocalDate claimDate;
    private String status;
    private String documentPath;
    private String reason;

    public ExpenseClaim(int employeeId, String claimDesc, double claimAmount, LocalDate claimDate, String status, String documentPath) {
        this.employeeId = employeeId;
        this.claimDesc = claimDesc;
        this.claimAmount = claimAmount;
        this.claimDate = claimDate;
        this.status = status;
        this.documentPath = documentPath;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getClaimDesc() {
        return claimDesc;
    }

    public void setClaimDesc(String claimDesc) {
        this.claimDesc = claimDesc;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public String toString() {
        return "ExpenseClaims{" +
                "claimId=" + claimId +
                ", employeeId=" + employeeId +
                ", claimDesc='" + claimDesc + '\'' +
                ", claimAmount=" + claimAmount +
                ", claimDate=" + claimDate +
                ", status='" + status + '\'' +
                ", documentPath='" + documentPath + '\'' +
                '}';
    }
}
