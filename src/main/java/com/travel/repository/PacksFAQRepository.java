package com.travel.repository;

import com.travel.entity.PacksFAQ;
import com.travel.entity.Packs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacksFAQRepository extends JpaRepository<PacksFAQ, Long> {
    List<PacksFAQ> findByPacks(Packs packs);
}
