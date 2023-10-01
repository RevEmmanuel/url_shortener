package org.example.data.repositories;

import org.example.data.models.KhalidVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhalidVideoRepository extends JpaRepository<KhalidVideo, Long> {

    KhalidVideo findByName(String name);

}
