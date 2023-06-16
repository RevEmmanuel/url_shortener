package org.example.data.repositories;

import org.example.data.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    boolean existsByLinkName(String linkName);

    Optional<Link> findByLinkName(String linkName);

}
