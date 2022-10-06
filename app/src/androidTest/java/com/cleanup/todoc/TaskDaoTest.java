package com.cleanup.todoc;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)

public class TaskDaoTest {

	// FOR DATA

	private SaveMyTripDatabase database;

	// TABLE Project

	private static long PROJECT_ID = 1;

	private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);

	// TABLE Task

	private static Task NEW_TASK_PLACE_TO_VISIT = new Task(1, 1L, "task place", PROJECT_ID);

	private static Task NEW_TASK_IDEA = new Task(2, 2L,"task idea", PROJECT_ID);

	private static Task NEW_TASK_RESTAURANTS = new Task(3, 3L, "task restaurants", PROJECT_ID);

	@Test // Project test
	public void insertAndGetProject() throws InterruptedException {

		// BEFORE : Adding a new project

		this.database.projectDao().createProject(PROJECT_DEMO);

		// TEST

		Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));

		assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
	}

	@Test // Task tests
	public void insertAndGetItems() throws InterruptedException {

		// BEFORE : Adding demo user & demo items

		this.database.projectDao().createProject(PROJECT_DEMO);

		this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);

		this.database.taskDao().insertTask(NEW_TASK_IDEA);

		this.database.taskDao().insertTask(NEW_TASK_RESTAURANTS);

		// TEST

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));

		assertTrue(tasks.size() == 3);

	}

	@Test
	public void insertAndUpdateTask() throws InterruptedException {

		// BEFORE : Adding demo user & demo items. Next, update item added & re-save it

		this.database.projectDao().createProject(PROJECT_DEMO);

		this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);

		Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);

		//taskAdded.setSelected(true);

		this.database.taskDao().updateTask(taskAdded);


		//TEST

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));

		//assertTrue(tasks.size() == 1 && tasks.get(0).getSelected()); // ???
	}

	@Test

	public void insertAndDeleteItem() throws InterruptedException {

		// BEFORE : Adding demo user & demo item. Next, get the item added & delete it.

		this.database.projectDao().createProject(PROJECT_DEMO);


		this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);

		Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);

		this.database.taskDao().deleteTask(taskAdded.getId());

		//TEST

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));

		assertTrue(tasks.isEmpty());

	}

	@Rule

	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule(); // Force synchrony tests

	@Before // Call before run tests

	public void initDb() throws Exception { // Create database's instance and place database variable

		this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),

						SaveMyTripDatabase.class)

				.allowMainThreadQueries()

				.build();

	}

	@After

	public void closeDb() throws Exception {

		database.close();

	}

}