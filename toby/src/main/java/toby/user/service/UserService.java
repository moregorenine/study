package toby.user.service;

import java.util.List;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

public class UserService {
	UserDao userDao;
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}
	
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
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
			if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
				userLevelUpgradePolicy.upgradeLevel(user);
			}
		}
		
	}

}
