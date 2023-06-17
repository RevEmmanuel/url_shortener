package org.example.data.repositories;

import org.example.data.models.Link;
import org.example.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    boolean existsByLinkName(String linkName);

    Optional<Link> findByLinkName(String linkName);

    Optional<Link> findByTitleAndOwner(String title, UserEntity owner);

    List<Link> findAllByOwner(UserEntity owner);

    Optional<Link> findByOwnerAndId(UserEntity owner, Long id);

}
