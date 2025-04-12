package com.example.demo.repository;

import com.example.demo.model.SmoothieDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SmoothieDetailsRepository extends JpaRepository<SmoothieDetails, Long>{
	
	  @Query(value = "SELECT * FROM smoothie_details WHERE calories BETWEEN :calories - 100 AND :calories + 100 ", nativeQuery = true)
 List<SmoothieDetails> findSmoothieByCalories(Long calories);
}
