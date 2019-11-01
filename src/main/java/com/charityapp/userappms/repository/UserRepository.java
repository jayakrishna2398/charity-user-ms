package com.charityapp.userappms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.charityapp.userappms.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(" FROM User u WHERE u.email = :email AND u.password = :password")
	User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query(" From User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	@Modifying
	@Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
	int updateStatus(@Param("active") Boolean active, @Param("id") int id);
}
