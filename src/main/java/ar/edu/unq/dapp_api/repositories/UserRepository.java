package ar.edu.unq.dapp_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.dapp_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {}