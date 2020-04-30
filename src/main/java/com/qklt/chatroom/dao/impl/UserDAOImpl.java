package com.qklt.chatroom.dao.impl;


import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public UserDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 检查用户名和密码是否匹配
	 * @param ld
	 * @return
	 */
	public boolean checkAccount(LoginData ld) {
		String sql = "SELECT count(*) FROM users_account WHERE userName = ? " +
				"AND password = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, ld.getUsername(), ld.getPassword());
		return count == 1;
	}

	public User getUserByName(String userName) {
		String sql = "SELECT * FROM users_information WHERE userName = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, userName);
		return user;
	}

	public boolean checkDuplicateNames(String userName) {
		String sql = "SELECT count(*) FROM users_account WHERE userName = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, userName);
		return count > 0;
	}

	public boolean insertUserToAccount(LoginData ld) {
		String sql = "INSERT INTO users_account(userName, password) "
				+ "VALUES (?,?)";
		int count = jdbcTemplate.update(sql, ld.getUsername(), ld.getPassword());
		return count == 1;
	}

	public boolean insertUserToInformation(User user) {
		String sql = "INSERT INTO users_information(userName) "
				+ "VALUES (?)";
		int count = jdbcTemplate.update(sql, user.getUserName());
		return count == 1;
	}

	public boolean updateUserInformation(User user) {
		String sql = "UPDATE users_information " +
				"SET sex = ?, age = ?, portrait = ?" +
				"WHERE userName = ?";
		int count = jdbcTemplate.update(sql, user.getSex(),
				user.getAge(), user.getPortrait(), user.getUserName());
		return count == 1;
	}



}
