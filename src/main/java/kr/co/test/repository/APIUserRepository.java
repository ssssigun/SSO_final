package kr.co.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.test.domain.APIUser;

public interface APIUserRepository extends JpaRepository<APIUser, String>{
	List<APIUser> findAPIUserById(String id);
	Optional<APIUser> findById(String id);
}
