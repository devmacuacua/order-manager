package pt.agap2.ordermanager.user.mapper;

import pt.agap2.ordermanager.user.dto.UserRequestDTO;
import pt.agap2.ordermanager.user.dto.UserResponseDTO;
import pt.agap2.ordermanager.user.entity.UserEntity;

public final class UserMapper {
	private UserMapper() {
	}

	public static UserEntity toEntity(UserRequestDTO dto) {
		UserEntity e = new UserEntity();
		e.setName(dto.getName());
		e.setEmail(dto.getEmail());
		return e;
	}

	public static void copyToEntity(UserRequestDTO dto, UserEntity target) {
		target.setName(dto.getName());
		target.setEmail(dto.getEmail());
	}

	public static UserResponseDTO toResponse(UserEntity e) {
		return new UserResponseDTO(e.getId(), e.getName(), e.getEmail());
	}
}