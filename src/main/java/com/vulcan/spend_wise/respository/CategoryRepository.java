package com.vulcan.spend_wise.respository;

import com.vulcan.spend_wise.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
