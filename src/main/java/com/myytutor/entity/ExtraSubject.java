package com.myytutor.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;



	@Entity
	@Table(name = "extra_subject")
	public class ExtraSubject {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String extraSubjectName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getExtraSubjectName() {
			return extraSubjectName;
		}

		public void setExtraSubjectName(String extraSubjectName) {
			this.extraSubjectName = extraSubjectName;
		}


	    // Getters and Setters
	}


