package com.charityapp.userappms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.charityapp.userappms.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
	@Query(" FROM Donor d WHERE d.email = :email AND d.password = :password")
	Donor findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	@Query(" From Donor d WHERE d.email = :email")
	Donor findByEmail(
			@Param("email") String email
			);
}
