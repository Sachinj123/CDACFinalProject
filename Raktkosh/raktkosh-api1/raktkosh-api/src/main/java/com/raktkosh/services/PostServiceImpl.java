package com.raktkosh.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserPostRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.PostAddDto;
import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;
import com.raktkosh.exceptions.CustomException;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.Post;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;

@Service
@Transactional
public class PostServiceImpl implements IPostService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserPostRepository userPostRepo;

	@Autowired
	private UserAddressRepository userAddressRepository;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserAddressRepository addRepo;

	@Override
	public Post addPost(Post post, Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found"));
		if(addRepo.existsById(userId)) {
			post.setUserId(user);
			
			return userPostRepo.save(post);
		}
		throw new RuntimeException("Can`t Create a post add Address first");
		
	}

	@Override
	public boolean deletePostById(Long id) {

		userPostRepo.deleteById(id);
		return true;
	}

	@Override
	public List<Post> findByTypeAndAntigen(BloodTypes type, Antigens antigen) {
		System.out.println(type + " and ," + antigen);

		return userPostRepo.findByTypeAndAntigen(type, antigen);
	}

	@Override
	public List<Post> findByPostCategory(PostCategory postcategory) {

		return userPostRepo.findByPostCategory(postcategory);
	}

	@Override
	public List<Post> findByTypeAndSort(BloodTypes type, Antigens antigen, Sort sort) {
		return userPostRepo.findByTypeAndSort(type, antigen, Sort.by("createdOn"));
	}

	@Override
	public Post findById(Long id) {
		return userPostRepo.findById(id).orElseThrow();

	}

	@Override
	public List<Post> getAllPostByUser(Long userID) {
		User user = new User();
		user.setId(userID);
		return userPostRepo.findByUserId(user);
	}

	@Override
	public List<Post> getAllPosts() {
		return userPostRepo.findAll();
	}

	@Override
	public List<PostAddDto> getAllPosts2() {

		List<UserAddress> addlist = new ArrayList<UserAddress>();
				addlist=userAddressRepository.getAddAddressByPost();
		List<Post> postlist = new ArrayList<Post>();
				postlist=userPostRepo.findAll();
				addlist.forEach(System.out::print);
				postlist.forEach(System.out::print);

		List<PostAddDto> list = new ArrayList<PostAddDto>();

		for (int i = 0; i < addlist.size(); i++) {
			UserAddress u = addlist.get(i);
			Post p = postlist.get(i);
			PostAddDto dto = new PostAddDto(p.getId(), p.getAntigen(), p.getCreatedOn(), p.getPostCategory(),
					p.getType(), p.getUserId(), u.getState(), u.getDistrict(), u.getCity(), u.getLocality(),
					u.getZip());
//		System.out.println(p);
//		System.out.println(u);
			System.out.println(dto);
			list.add(dto);
		}
//	 List<PostAddDto> list; for (var ele : addlist) { list.add(mapper.map(ele,
//	 PostAddDto.class)); } for (var ele : postlist) { list.add(mapper.map(ele,
//	 PostAddDto.class)); }

		return list;

	}

	@Override
	public List<UserAddress> getAllPosts3() {
		List<UserAddress> addlist = new ArrayList<UserAddress>();
		addlist=userAddressRepository.getAddAddressByPost();
		return addlist;
	}

	@Override
	public UserAddress findAddressById(Long id) {
		
		return userAddressRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Address with Does not exist")); 
	}
}
