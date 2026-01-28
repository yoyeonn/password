package com.travel.repository;

import com.travel.entity.Packs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacksRepository extends JpaRepository<Packs, Long> {
}
