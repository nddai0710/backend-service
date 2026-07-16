package vn.daijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.daijava.model.AddressEntity;


public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);
}
