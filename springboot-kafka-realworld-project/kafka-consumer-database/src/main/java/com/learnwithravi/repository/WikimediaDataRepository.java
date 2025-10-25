package com.learnwithravi.repository;

import com.learnwithravi.entity.Wikimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikimediaDataRepository extends JpaRepository<Wikimedia, Long> {
}
