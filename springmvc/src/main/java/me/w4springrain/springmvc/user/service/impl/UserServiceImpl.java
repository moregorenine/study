package me.w4springrain.springmvc.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.w4springrain.springmvc.common.util.CommonUtil;
import me.w4springrain.springmvc.user.dao.UserDao;
import me.w4springrain.springmvc.user.service.UserService;
import me.w4springrain.springmvc.user.vo.User;

@Service("UserService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	UserDao userDao;
	
	@Override
	public void createUser() {
		String userId = CommonUtil.generateUUID();
		logger.debug("userId 생성 : " +  userId);
		userDao.createUser(userId);
	}
	
	@Override
	public List<User> selectUsers() {
		return userDao.selectUsers();
	}

}
