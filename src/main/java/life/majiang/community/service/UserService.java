package life.majiang.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public void insert(User user) {	
		userMapper.insert(user);
	}

	public User findUserByToken(String token) {
		return userMapper.findUserByToken(token);
	}

}
