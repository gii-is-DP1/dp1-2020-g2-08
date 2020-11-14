package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {

	@NotNull
	@Column(name = "aforo")
	private Integer aforo;

	@NotNull
	@Column(name = "ocupadas")
	private Integer ocupadas;
	
	
//	@Column(name = "dias_llenos")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
//	private LocalDate diasLlenos;

	// lista de reservas del hotel
	@OneToMany
	private Set<Booking> bookings;

	// lista de reviews del hotel
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
	
	private Set<Review> reviews;
	
	
	protected Set<Review> getReviewsInternal() {
		if (this.reviews == null) {
			this.reviews = new HashSet<>();
		}
		return this.reviews;
	}
	
	protected void setReviewsInternal(Set<Review> reviews) {
		this.reviews = reviews;
	}

	
	
	public List<Review> getReviews2() {
		List<Review> sortedReviews = new ArrayList<>(getReviewsInternal());
		PropertyComparator.sort(sortedReviews, new MutableSortDefinition("stars", true, true));
		return Collections.unmodifiableList(sortedReviews);
	}
	
	public void addReview(Review review) {
		getReviewsInternal().add(review);
		review.setHotel(this);
}
	public boolean removeReview(Review review) {
		return getReviewsInternal().remove(review);
	}
}