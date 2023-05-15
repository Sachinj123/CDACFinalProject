package com.raktkosh.pojos;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "userId")
public class Post extends BaseEntity {
	@Enumerated(value = EnumType.STRING)
	@Column(length = 2)
	private BloodTypes type;
	@Enumerated(value = EnumType.STRING)
	@Column(length = 10)
	private Antigens antigen;
	@Enumerated(value = EnumType.STRING)
	@Column(length = 20)
	private PostCategory postCategory;
	
	@Column(insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP", name = "created_on")
	private LocalDateTime createdOn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userId;

	public BloodTypes getType() {
		return type;
	}

	public void setType(BloodTypes type) {
		this.type = type;
	}

	public Antigens getAntigen() {
		return antigen;
	}

	public void setAntigen(Antigens antigen) {
		this.antigen = antigen;
	}

	public PostCategory getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(PostCategory postCategory) {
		this.postCategory = postCategory;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
}
