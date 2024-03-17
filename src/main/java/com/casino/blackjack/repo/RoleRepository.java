package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.RoleEntity;
import com.casino.blackjack.model.enumerated.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(UserRoleEnum userRoleEnum);

    List<RoleEntity> findAllByRoleIn(List<UserRoleEnum> roles);
}
