package com.raktkosh.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;
import com.raktkosh.pojos.Post;
import com.raktkosh.pojos.UserAddress;
import com.raktkosh.services.IPostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class PostController {

	@Autowired
	private IPostService postService;

//	@Autowired
//	private PostAddDtoRepository postAddDtoRepository;

	// adding post by user id
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addPost(@RequestBody @Valid Post post, @PathVariable Long userId) {
		return new ResponseEntity<>(postService.addPost(post, userId), HttpStatus.CREATED);
	}

	// fetching post by user id
	@GetMapping("/byUser/{id}")
	public ResponseEntity<?> getByUser(@PathVariable Long id) {
		return new ResponseEntity<>(postService.getAllPostByUser(id), HttpStatus.CREATED);
	}

	// deleting post by post id
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<Boolean> deletePostById(@PathVariable Long postId) {
		return ResponseEntity.ok(postService.deletePostById(postId));
	}

	// fetching post by postCategory
	@GetMapping("/{postCategory}")
	public List<?> findByPostCategory(@PathVariable PostCategory postCategory) {
		return postService.findByPostCategory(postCategory);
		// return
		// ResponseEntity.status(HttpStatus.OK).body(postService.findByPostCategory(postCategory));
	}

	// fetching post by postCategory
	@GetMapping("/{bloodType}/{antigen}")
	public ResponseEntity<?> findByTypeAndAntigen(@PathVariable BloodTypes bloodType, @PathVariable Antigens antigen) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.findByTypeAndAntigen(bloodType, antigen));
	}

	// fetching post by bloodType,antigen and creation date
	@GetMapping("/{bloodType}/{antigen}/dateBy")
	public ResponseEntity<?> findByTypeAndSort(@PathVariable BloodTypes bloodType, @PathVariable Antigens antigen) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(postService.findByTypeAndSort(bloodType, antigen, Sort.by("createdOn")));
	}

	// fetching post by user id
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
	}

	// fetching list of user address by post id
	@RequestMapping("/address")
	public ResponseEntity<?> findAddresById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts3());
	}

	// fetching list of all post
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts2());
	}
//	@GetMapping("/")
//  public ResponseEntity<?> findAllPost() {
//    return ResponseEntity.status(HttpStatus.OK).body(postAddDtoRepository.getAllPostsAndAddress());
//  }
}
