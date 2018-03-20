package me.w4springrain.springmvc.user.service;

import java.util.List;

import me.w4springrain.springmvc.user.vo.User;

public interface UserService {

	/**
	 * user 생성한다.
	 */
	void createUser();

	/**
	 * users 조회한다.
	 * @return 
	 */
	List<User> selectUsers();

}
