package pt.agap2.ordermanager.user.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.user.entity.UserEntity;

public interface IUserRepository {
	public void persist(EntityManager em, UserEntity user);

	public UserEntity findById(EntityManager em, Long id);

	public List<UserEntity> findAll(EntityManager em);

	public void remove(EntityManager em, UserEntity user);
}
