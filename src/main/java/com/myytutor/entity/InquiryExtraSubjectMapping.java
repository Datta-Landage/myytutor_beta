package com.myytutor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inquiry_extra_subject_mapping")
public class InquiryExtraSubjectMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one link to the inquiry
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private Inquiry inquiry;

    // Many-to-one link to the ExtraSubject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extra_subject_id", nullable = false)
    private ExtraSubject extraSubject;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public ExtraSubject getExtraSubject() {
        return extraSubject;
    }

    public void setExtraSubject(ExtraSubject extraSubject) {
        this.extraSubject = extraSubject;
    }
}
