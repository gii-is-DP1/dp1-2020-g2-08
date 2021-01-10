package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity

@Table(name = "reviews")
public class Review extends BaseEntity {

	@NotNull
	@Column(name = "stars")
	private Integer stars;
	
	@NotNull
	@Column(name = "tittle")
	private String tittle;

	@NotNull
	@Column(name = "description")
	private String description;
	
	@NotNull
    @Column(name = "review_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate reviewDate;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	 private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
