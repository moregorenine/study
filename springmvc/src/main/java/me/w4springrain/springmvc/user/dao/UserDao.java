package me.w4springrain.springmvc.user.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import me.w4springrain.springmvc.user.vo.User;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void createUser(String userId) {
		sqlSession.insert("me.w4springrain.springmvc.user.dao.createUser", userId);
	}

	public List<User> selectUsers() {
		List<User> users = sqlSession.selectList("me.w4springrain.springmvc.user.dao.selectUsers");
		return users;
	}

	
}
