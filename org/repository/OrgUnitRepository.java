package com.ktn3.core_banking.org.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ktn3.core_banking.org.entity.OrgUnit;

@Repository
public interface OrgUnitRepository extends JpaRepository<OrgUnit, Long>{
	Boolean existsByCode(String code);
	
	List<OrgUnit> findByParentId(Long parentId);
	
	@Query("""
        SELECT MAX(o.code)
        FROM OrgUnit o
        WHERE o.type = 'SO'
    """)
	String findMaxCodeSO();
	
	@Query("""
        SELECT MAX(o.code)
        FROM OrgUnit o
        WHERE o.parent.id = :parentId
    """)
    String findMaxCodeByParentId(
            @Param("parentId") Long parentId
    );
}
