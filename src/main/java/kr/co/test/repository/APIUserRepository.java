package kr.co.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.test.domain.APIUser;

public interface APIUserRepository extends JpaRepository<APIUser, String>{

}
