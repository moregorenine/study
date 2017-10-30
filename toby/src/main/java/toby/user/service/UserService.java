package toby.user.service;

import java.util.List;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

public class UserService {
	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * USERS 테이블에 user param insert
	 * user의 level이 없을 경우 BASIC으로 초기화
	 * @param user
	 */
	public void add(User user) {
		if(user.getLevel() == null) {
			user.setLevel(Level.BASIC);
		}
		userDao.add(user);
	}
	
	/**
	 * USERS 테이블의 사용자 레벨 업그레이드
	 */
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		
		for(User user : users) {
			Boolean changed = null;
			if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
				user.setLevel(Level.SILVER);
				changed = true;
			} else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
				user.setLevel(Level.GOLD);
				changed = true;
			} else if(user.getLevel() == Level.GOLD) {
				changed = false;
			} else {
				changed = false;
			}
			
			if(changed) {
				userDao.update(user);
			}
		}
		
	}
}
