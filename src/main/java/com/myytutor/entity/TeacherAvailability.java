package com.myytutor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "teacher_availability")
public class TeacherAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonIgnore
	private Teacher teacher;

	@Column(name = "start_time", nullable = false)
	private Integer startTime; // Stored as minutes since midnight

	@Column(name = "end_time", nullable = false)
	private Integer endTime; // Stored as minutes since midnight

	@Column(name = "available_time_for_slot", nullable = false)
	private Integer availableTimeForSlot; // Available minutes

	@Column(name = "total_day_availability_for_slot", nullable = false)
	private Integer totalDayAvailabilityForSlot; // Stores count of `true` weekdays

	@Column(name = "monday", nullable = false)
	private Boolean monday;

	@Column(name = "tuesday", nullable = false)
	private Boolean tuesday;

	@Column(name = "wednesday", nullable = false)
	private Boolean wednesday;

	@Column(name = "thursday", nullable = false)
	private Boolean thursday;

	@Column(name = "friday", nullable = false)
	private Boolean friday;

	@Column(name = "saturday", nullable = false)
	private Boolean saturday;

	@Column(name = "sunday", nullable = false)
	private Boolean sunday;

	@PrePersist
	@PreUpdate
	public void calculateTotalDayAvailability() {
		this.totalDayAvailabilityForSlot = (Boolean.TRUE.equals(monday) ? 1 : 0)
				+ (Boolean.TRUE.equals(tuesday) ? 1 : 0) + (Boolean.TRUE.equals(wednesday) ? 1 : 0)
				+ (Boolean.TRUE.equals(thursday) ? 1 : 0) + (Boolean.TRUE.equals(friday) ? 1 : 0)
				+ (Boolean.TRUE.equals(saturday) ? 1 : 0) + (Boolean.TRUE.equals(sunday) ? 1 : 0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getAvailableTimeForSlot() {
		return availableTimeForSlot;
	}

	public void setAvailableTimeForSlot(Integer availableTimeForSlot) {
		this.availableTimeForSlot = availableTimeForSlot;
	}

	public Boolean getMonday() {
		return monday;
	}

	public void setMonday(Boolean monday) {
		this.monday = monday;
	}

	public Boolean getTuesday() {
		return tuesday;
	}

	public void setTuesday(Boolean tuesday) {
		this.tuesday = tuesday;
	}

	public Boolean getWednesday() {
		return wednesday;
	}

	public void setWednesday(Boolean wednesday) {
		this.wednesday = wednesday;
	}

	public Boolean getThursday() {
		return thursday;
	}

	public void setThursday(Boolean thursday) {
		this.thursday = thursday;
	}

	public Boolean getFriday() {
		return friday;
	}

	public void setFriday(Boolean friday) {
		this.friday = friday;
	}

	public Boolean getSaturday() {
		return saturday;
	}

	public void setSaturday(Boolean saturday) {
		this.saturday = saturday;
	}

	public Boolean getSunday() {
		return sunday;
	}

	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}

	public Integer getTotalDayAvailabilityForSlot() {
		return totalDayAvailabilityForSlot;
	}

	public void setTotalDayAvailabilityForSlot(Integer totalDayAvailabilityForSlot) {
		this.totalDayAvailabilityForSlot = totalDayAvailabilityForSlot;
	}

}
