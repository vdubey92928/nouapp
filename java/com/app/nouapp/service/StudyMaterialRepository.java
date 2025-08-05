package com.app.nouapp.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.nouapp.model.StudyMaterial;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Integer>{

    @Query("select m from StudyMaterial m where m.course=:program and m.branch= :branch and m.year=:year and m.materialtype=:materialtype")
	List<StudyMaterial> findAllByType(String program, String branch, String year, String materialtype);

    @Query("select m from StudyMaterial m where  m.materialtype=:materialtype")
    List<StudyMaterial> findByMaterial(String materialtype);

}
