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

@Data
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
	
	@Column(name = "ownerName")
	 private String ownerName;

}
