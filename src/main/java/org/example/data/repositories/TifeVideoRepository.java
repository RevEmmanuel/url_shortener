package org.example.data.repositories;

import org.example.data.models.TifeVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TifeVideoRepository extends JpaRepository<TifeVideo, Long> {

    List<TifeVideo> findAllByUniqueUserId(String uniqueUserId);

}
