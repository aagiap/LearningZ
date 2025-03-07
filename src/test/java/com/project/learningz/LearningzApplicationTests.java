package com.project.learningz;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LearningzApplicationTests {

	@Autowired
	private UserRepository userRepository;
//	@Test
//	public void testRoleEnumPersistence() {
//		User user = new User();
//		user.setUsername("testuser");
//		user.setPassword("password");
//		user.setEmail("testuser@example.com");
//		user.setRole(Role.STUDENT);
//
//		userRepository.save(user);
//
//		User retrievedUser = userRepository.findById(user.getId()).orElse(null);
//		assertThat(retrievedUser).isNotNull();
//		assertThat(retrievedUser.getRole()).isEqualTo(Role.STUDENT);
//	}

}
