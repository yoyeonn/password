package com.travel.repository;

import com.travel.entity.PacksActivity;
import com.travel.entity.Packs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacksActivityRepository extends JpaRepository<PacksActivity, Long> {
    List<PacksActivity> findByPacks(Packs packs);
}
