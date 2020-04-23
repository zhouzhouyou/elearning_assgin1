package edu.yuri.elearning.mapper;

import edu.yuri.elearning.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findAllByUserId(Integer id);

    boolean existsByCourseIdAndUserId(Integer courseId, Integer userId);
}
