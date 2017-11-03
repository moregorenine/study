package toby.user.service;

import toby.user.domain.User;

public interface UserService {
	public void add(User user);
	public void upgradeLevels();
}
