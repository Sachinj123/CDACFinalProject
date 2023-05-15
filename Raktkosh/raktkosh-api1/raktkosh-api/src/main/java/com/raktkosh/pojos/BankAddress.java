//package com.raktkosh.pojos;
//
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.MapsId;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "banks_address")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class BankAddress extends Address {
//  
//  @OneToOne
//  @JoinColumn(name = "bank_id")
//  @MapsId
//  @JsonIgnore
//  private BloodBank bank;
//}
