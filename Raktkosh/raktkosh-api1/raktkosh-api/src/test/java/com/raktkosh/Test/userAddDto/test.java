package com.raktkosh.Test.userAddDto;
 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

  @Autowired
  private com.raktkosh.services.PostServiceImpl impl;

  @Test
  void testFindAll() {
	  impl.getAllPosts2().forEach(System.out::println);
  }
}
