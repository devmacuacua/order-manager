package pt.agap2.ordermanager.user.service;

import java.util.List;

import pt.agap2.ordermanager.user.dto.UserRequestDTO;
import pt.agap2.ordermanager.user.entity.UserEntity;

public interface IUserService {
	public UserEntity create(UserEntity user);

	public List<UserEntity> list();

	public UserEntity get(Long id);

	public UserEntity update(Long id, UserRequestDTO dto);

	public boolean delete(Long id);
}
