package com.charityapp.userappms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.charityapp.userappms.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	@Query(" FROM Employee e WHERE e.email = :email AND e.password = :password")
	Employee findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	@Query(" From Employee e WHERE e.email = :email")
	Employee findByEmail(
			@Param("email") String email
			);
}
