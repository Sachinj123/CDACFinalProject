package com.raktkosh.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.raktkosh.dto.PostAddDto;
import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;
import com.raktkosh.pojos.Post;
import com.raktkosh.pojos.UserAddress;

public interface IPostService {

	Post addPost(Post post, Long userId);
	
	boolean deletePostById(Long id);
	
	List<Post> findByTypeAndAntigen(BloodTypes type,Antigens antigen);
	
	List<Post> findByPostCategory(PostCategory postcategory);
	
	List<Post> findByTypeAndSort(BloodTypes type, Antigens antigen,Sort sort);
	
	Post findById(Long id);
	
	List<Post> getAllPostByUser(Long userID);
	
	List<Post> getAllPosts();
	 
	List<PostAddDto> getAllPosts2();
	List<UserAddress> getAllPosts3();

	UserAddress findAddressById(Long id);

}
