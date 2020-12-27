package com.cpg.pixogramspring.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Details about Follower class")
@Entity
public class Followers {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty(notes = "The unique Id of Follower")
	int follower_id;
//	@ApiModelProperty(notes = "The id of the User")
//	int user_id;
	@ApiModelProperty(notes = "The email id of the User")
	String email;
	@ApiModelProperty(notes = "The followers belong to the User")
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="user_id" , referencedColumnName="user_id")
	private User user;
	
	public Followers() {
		
	}
	
	public Followers(String email) {
		super();
		//this.follower_id = follower_id;
		//this.user_id = user_id;
		this.email = email;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Followers [follower_id=" + follower_id + ", email=" + email + "]";
	}
	
	
	//follower email should be in user table, since follower should be a user also
	
	

}
