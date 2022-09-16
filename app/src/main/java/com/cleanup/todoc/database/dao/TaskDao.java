package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

// Regroup CRUD actions for table Task

	@Dao

	public interface TaskDao {

		@Query("SELECT * FROM Task WHERE projectId = :projectId")
		LiveData<List<Task>> getTasks(long projectId);  // get task's list (LiveData)

		@Query("SELECT * FROM Task WHERE projectId = :projectId")
		Cursor getTasksWithCursor(long projectId);  // get task's list (Cursor)

		@Insert

		long insertTask(Task task); // Create task for Room with object (Room create id automatically)

		@Update

		int updateTask(Task task); // Update task for Room with object (We need to create the id for this object)

		@Query("DELETE FROM Task WHERE id = :taskId")

		int deleteTask(long taskId); // Delete task for Room (@Query for SQL request)

	}
