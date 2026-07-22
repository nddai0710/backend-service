package vn.daijava.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.daijava.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select u from UserEntity u where u.status='ACTIVE' " +
            "AND (lower(u.firstName) like :keyword " +
            "or lower(u.lastName) like :keyword " +
            "or lower(u.username) like :keyword " +
            "or lower(u.phone) like :keyword " +
            "or lower(u.email) like :keyword)")
    Page<UserEntity> searchByKeyword(String keyword, Pageable pageable);

    UserEntity findByEmail(String email);

//    UserEntity findByUsername(String phone);

    UserEntity findByUsername(String username);

}
