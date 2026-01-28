package com.travel.repository;

import com.travel.entity.PacksReview;
import com.travel.entity.Packs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacksReviewRepository extends JpaRepository<PacksReview, Long> {
    List<PacksReview> findByPacks(Packs packs);
}
