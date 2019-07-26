package life.majiang.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import life.majiang.community.model.User;

@Mapper
public interface UserMapper {
	
	@Insert({"INSERT INTO user(name, account_id, token, gmt_create, gmt_modified)",
			 "VALUES (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})"})
	public void insert(User user);

}
