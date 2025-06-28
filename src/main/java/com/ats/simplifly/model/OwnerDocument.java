package com.ats.simplifly.model;

import com.ats.simplifly.model.enums.VerificationStatus;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "owner_document")
public class OwnerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private FlightOwner owner;
    @Column(name = "file_url")
    private String fileUrl;
    private String documentType;
    @Column(name = "document_status")
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    private LocalTime uploadTime;
    @ManyToOne
    private PlatformManager verifiedBy;
    private LocalTime verifiedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FlightOwner getOwner() {
        return owner;
    }

    public void setOwner(FlightOwner owner) {
        this.owner = owner;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public VerificationStatus getDocumentStatus() {
        return verificationStatus;
    }

    public void setDocumentStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public LocalTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public PlatformManager getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(PlatformManager verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalTime getVerifiedTime() {
        return verifiedTime;
    }

    public void setVerifiedTime(LocalTime verifiedTime) {
        this.verifiedTime = verifiedTime;
    }
}
