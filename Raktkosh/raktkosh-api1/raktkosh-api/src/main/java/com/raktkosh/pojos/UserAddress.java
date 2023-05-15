package com.raktkosh.pojos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users_address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
public class UserAddress extends Address {
  
  @OneToOne
  @JoinColumn(name = "user_id")
  @MapsId
  @JsonIgnore
  private User user;

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}
}
