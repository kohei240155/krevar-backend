package com.example.iruka_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_intervals")
public class ReviewIntervalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "interval_order", nullable = false)
	private int intervalOrder;

	@Column(name = "interval_days", nullable = false)
	private int intervalDays;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIntervalOrder() {
		return intervalOrder;
	}

	public void setIntervalOrder(int intervalOrder) {
		this.intervalOrder = intervalOrder;
	}

	public int getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(int intervalDays) {
		this.intervalDays = intervalDays;
	}
}