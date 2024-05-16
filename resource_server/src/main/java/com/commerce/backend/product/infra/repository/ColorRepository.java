package com.commerce.backend.product.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.product.infra.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> { }
