package pt.agap2.ordermanager.user.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.user.entity.UserEntity;

public class UserRepository implements IUserRepository {

	public void persist(EntityManager em, UserEntity user) {
		em.persist(user);
	}

	public UserEntity findById(EntityManager em, Long id) {
		return em.find(UserEntity.class, id);
	}

	public List<UserEntity> findAll(EntityManager em) {
		return em.createQuery(IUserQueries.GET_USER_ENTITY_ORDER_BY_U_ID_DESC, UserEntity.class).getResultList();
	}

	public void remove(EntityManager em, UserEntity user) {
		em.remove(user);
	}
}