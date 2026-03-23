package com.mrdarip.stepy.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrdarip.stepy.data.local.AppDatabase
import com.mrdarip.stepy.data.mapper.toDomain
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class StepRepositoryImplTest {
    private lateinit var stepRepository: StepRepositoryImpl
    private lateinit var taskRepository: TaskRepositoryImpl
    private lateinit var db: AppDatabase

    private val testTask = Task(id = 1, name = "Test Task")
    private val initialSteps = listOf(
        Step(1, "S1", 0, 1, false),
        Step(2, "S2", 1, 1, true),
        Step(3, "S3", 2, 1, false)
    )

    private suspend fun addTask(task: Task) {
        taskRepository.addTask(task)
    }

    private suspend fun rebuildSteps(steps: List<Step>, task: Task) {
        stepRepository.rebuildTaskSteps(steps, task)
    }

    private suspend fun getSteps(taskId: Long): List<Step> {
        return db.stepDao().getStepsOfTask(taskId).map { it.toDomain() }
    }

    private fun assertStepsMatch(expected: List<Step>, actual: List<Step>) {
        assertEquals(
            expected.filter { it.position != null }.size,
            actual.filter { it.position != null }.size
        )
        assertEquals(
            expected.filter { it.position != null }.sortedBy { it.position },
            actual.filter { it.position != null }.sortedBy { it.position })
    }

    private suspend fun setupInitialSteps(task: Task) {
        rebuildSteps(initialSteps, task)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        stepRepository = StepRepositoryImpl(db.stepDao())
        taskRepository = TaskRepositoryImpl(db.taskDao(), db.executionDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun rebuildTaskSteps_savesInitialStepsCorrectly() = runTest {
        addTask(testTask)
        rebuildSteps(initialSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(initialSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_reordersStepsCorrectly() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val reorderedSteps = listOf(
            Step(2, "S2", 1, 1, true),
            Step(3, "S3", 2, 1, false),
            Step(1, "S1", 0, 1, false)
        )
        rebuildSteps(reorderedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(reorderedSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_removesMiddleStepCorrectly() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val removedSteps = listOf(
            Step(1, "S1", 0, 1, false),
            Step(3, "S3", 2, 1, false)
        )
        rebuildSteps(removedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(removedSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_addsNewStepCorrectly() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val addedSteps = listOf(
            Step(4, "S4", 3, 1, true),
            Step(1, "S1", 0, 1, false),
            Step(3, "S3", 2, 1, false),
        )
        rebuildSteps(addedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(addedSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_removesAndAddsStepCorrectly() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val addedRemovedSteps = listOf(
            Step(1, "S1", 0, 1, false),
            Step(5, "S5", 4, 1, false),
            Step(4, "S4", 3, 1, true),
        )
        rebuildSteps(addedRemovedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(addedRemovedSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_unusedStepsAreIgnored() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val unusedSteps = listOf(
            Step(1, "S1", 0, 1, false),
            Step(2, "S2", 1, 1, true),
            Step(99, "S3", null, 1, false),
        )
        rebuildSteps(unusedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(unusedSteps, savedSteps)
    }

    @Test
    fun rebuildTaskSteps_multipleUnusedStepsAreAllowedAndIgnored() = runTest {
        addTask(testTask)
        setupInitialSteps(testTask)
        val unusedSteps = listOf(
            Step(1, "S1", 0, 1, false),
            Step(2, "S2", 1, 1, true),
            Step(99, "S3", null, 1, false),
            Step(100, "S4", null, 1, false),
        )
        rebuildSteps(unusedSteps, testTask)
        val savedSteps = getSteps(testTask.id)
        assertStepsMatch(unusedSteps, savedSteps)
    }
}
