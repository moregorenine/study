package toby.user.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

import toby.mail.MailSender;
import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

public class UserServiceImpl implements UserService {
	
	UserDao userDao;
	private MailSender mailSender;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	public static final String EMAIL_ADMIN = "w4springrain@gmail.com";
	
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
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	/**
	 * user의 Level Upgrade 대상여부 판별
	 * @param user
	 * @return
	 */
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}
	
	/**
	 * user Level Update
	 * @param user
	 */
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendEmailUpgradeLevel(user);
	}

	/**
	 * Level Update된 사용자에게 안내메일 발송
	 * @param user
	 */
	private void sendEmailUpgradeLevel(User user) {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "mw-002.cafe24.com");
//		Session s = Session.getInstance(props);
//		MimeMessage message = new MimeMessage(s);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom(EMAIL_ADMIN);
		mailMessage.setSubject("Level Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "(으)로 업그레이드되었습니다.");
		
		this.mailSender.send(mailMessage);
	}
	
}
