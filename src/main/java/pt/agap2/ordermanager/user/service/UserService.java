package pt.agap2.ordermanager.user.service;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.shared.Jpa;
import pt.agap2.ordermanager.user.dto.UserRequestDTO;
import pt.agap2.ordermanager.user.entity.UserEntity;
import pt.agap2.ordermanager.user.mapper.UserMapper;
import pt.agap2.ordermanager.user.repository.UserRepository;

public class UserService {

	private final UserRepository repo;

	public UserService(UserRepository repo) {
		this.repo = repo;
	}

	public UserEntity create(UserEntity user) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();
			repo.persist(em, user);
			em.getTransaction().commit();
			return user;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public List<UserEntity> list() {
		EntityManager em = Jpa.em();
		try {
			return repo.findAll(em);
		} finally {
			em.close();
		}
	}

	public UserEntity get(Long id) {
		EntityManager em = Jpa.em();
		try {
			return repo.findById(em, id);
		} finally {
			em.close();
		}
	}

	public UserEntity update(Long id, UserRequestDTO dto) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			UserEntity existing = repo.findById(em, id);
			if (existing == null) {
				em.getTransaction().rollback();
				return null;
			}

			UserMapper.copyToEntity(dto, existing);

			em.getTransaction().commit();
			return existing;

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public boolean delete(Long id) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			UserEntity existing = repo.findById(em, id);
			if (existing == null) {
				em.getTransaction().rollback();
				return false;
			}

			repo.remove(em, existing);
			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}