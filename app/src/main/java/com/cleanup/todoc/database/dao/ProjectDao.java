package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

// Regroup CRUD actions for table Project

@Dao

public interface ProjectDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE) // Increase existing project with a same id than the new project create

	void createProject(Project project);

	@Query("SELECT * FROM Project WHERE id = :projectId")
	LiveData<Project> getProject(long projectId);

}
