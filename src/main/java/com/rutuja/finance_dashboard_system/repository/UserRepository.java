package com.rutuja.finance_dashboard_system.repository;

import com.rutuja.finance_dashboard_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
}
