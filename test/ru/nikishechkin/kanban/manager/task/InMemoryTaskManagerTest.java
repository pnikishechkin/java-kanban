package ru.nikishechkin.kanban.manager.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nikishechkin.kanban.manager.history.HistoryManager;
import ru.nikishechkin.kanban.manager.history.InMemoryHistoryManager;
import ru.nikishechkin.kanban.model.Epic;
import ru.nikishechkin.kanban.model.SubTask;
import ru.nikishechkin.kanban.model.Task;
import ru.nikishechkin.kanban.model.TaskStatus;

import java.util.HashSet;

class InMemoryTaskManagerTest {

    static TaskManager taskManager;

    public void initData() {

        HistoryManager historyManager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager(historyManager);

        InMemoryTaskManager.idCounter = 0;

        taskManager.addEpic(new Epic("Эпик 1", "Описание")); // ID=0

        taskManager.addSubTask(new SubTask("Подзадача 1_1", "Описание", 0)); // ID=1
        taskManager.addSubTask(new SubTask("Подзадача 1_2", "Описание", 0)); // ID=2
        taskManager.addSubTask(new SubTask("Подзадача 1_3", "Описание", 0)); // ID=3

        taskManager.addEpic(new Epic("Эпик 2", "Описание")); // ID=4
        taskManager.addSubTask(new SubTask("Подзадача 2_1", "Описание", 4)); // ID=5
        taskManager.addSubTask(new SubTask("Подзадача 2_2", "Описание", 4)); // ID=6

        taskManager.addEpic(new Epic("Эпик 3", "Описание")); // ID=7
        taskManager.addSubTask(new SubTask("Подзадача 3_1", "Описание", 7)); // ID=8
        taskManager.addSubTask(new SubTask("Подзадача 3_2", "Описание", 7)); // ID=9
        taskManager.addSubTask(new SubTask("Подзадача 3_3", "Описание", 7)); // ID=10

        taskManager.addTask(new Task("Задача 1", "Описание")); // ID=11
        taskManager.addTask(new Task("Задача 2", "Описание")); // ID=12
    }

    @Test
    void getEpics_checkSize() {
        // given
        initData();

        // then
        Assertions.assertEquals(3, taskManager.getEpics().size());
    }

    @Test
    void getSubTasks_checkSize() {
        // given
        initData();

        // then
        Assertions.assertEquals(8, taskManager.getSubTasks().size());
    }

    @Test
    void getTasks_checkSize() {
        // given
        initData();

        // then
        Assertions.assertEquals(2, taskManager.getTasks().size());
    }

    @Test
    void getEpicById_epicExist() {
        // given
        initData();

        // then
        Assertions.assertNotNull(taskManager.getEpicById(0), "Ошибка поиска эпика по идентификатору");
        Assertions.assertNotNull(taskManager.getEpicById(4), "Ошибка поиска эпика по идентификатору");
        Assertions.assertNotNull(taskManager.getEpicById(7), "Ошибка поиска эпика по идентификатору");

        // В случае поиска эпика по идентификатору других типов задач, должен вернуть null
        Assertions.assertNull(taskManager.getEpicById(1), "Ошибка поиска эпика по идентификатору");
        Assertions.assertNull(taskManager.getEpicById(11), "Ошибка поиска эпика по идентификатору");
    }

    @Test
    void getEpicById_epicNotExist() {
        // given
        initData();

        // then
        Assertions.assertNull(taskManager.getEpicById(1), "Ошибка поиска эпика по идентификатору");
        Assertions.assertNull(taskManager.getEpicById(11), "Ошибка поиска эпика по идентификатору");
    }

    @Test
    void getSubTaskById_subTaskExist() {
        // given
        initData();

        // then
        Assertions.assertNotNull(taskManager.getSubTaskById(1), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNotNull(taskManager.getSubTaskById(2), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNotNull(taskManager.getSubTaskById(3), "Ошибка поиска подзадачи по идентификатору");

        Assertions.assertNotNull(taskManager.getSubTaskById(5), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNotNull(taskManager.getSubTaskById(6), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNotNull(taskManager.getSubTaskById(8), "Ошибка поиска подзадачи по идентификатору");

        Assertions.assertNotNull(taskManager.getSubTaskById(9), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNotNull(taskManager.getSubTaskById(10), "Ошибка поиска подзадачи по идентификатору");
    }

    @Test
    void getSubTaskById_subTaskNotExist() {
        // given
        initData();

        // then
        // В случае поиска подзадачи по идентификатору других типов задач, должен вернуть null
        Assertions.assertNull(taskManager.getSubTaskById(0), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNull(taskManager.getSubTaskById(11), "Ошибка поиска подзадачи по идентификатору");
    }

    @Test
    void getTaskById_taskExist() {
        // given
        initData();

        // then
        Assertions.assertNotNull(taskManager.getTaskById(11), "Ошибка поиска задачи по идентификатору");
        Assertions.assertNotNull(taskManager.getTaskById(12), "Ошибка поиска задачи по идентификатору");
    }

    @Test
    void getTaskById_taskNotExist() {
        // given
        initData();

        // then
        // В случае поиска задачи по идентификатору других типов задач, должен вернуть null
        Assertions.assertNull(taskManager.getTaskById(0), "Ошибка поиска подзадачи по идентификатору");
        Assertions.assertNull(taskManager.getTaskById(5), "Ошибка поиска подзадачи по идентификатору");
    }

    @Test
    void getSubTasksIds_checkList() {
        // given
        initData();
        HashSet<Integer> subTasks = new HashSet<>();
        subTasks.add(1);
        subTasks.add(2);
        subTasks.add(3);

        // then
        Assertions.assertEquals(subTasks, taskManager.getEpicById(0).getSubTasksIds());
    }

    @Test
    void editTask_changeStatus() {
        // given
        initData();

        // when
        taskManager.getTaskById(11).setStatus(TaskStatus.IN_PROGRESS);
        taskManager.getTaskById(12).setStatus(TaskStatus.DONE);
        taskManager.editTask(taskManager.getTaskById(11));
        taskManager.editTask(taskManager.getTaskById(12));

        // then
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTaskById(11).getStatus());
        Assertions.assertEquals(TaskStatus.DONE, taskManager.getTaskById(12).getStatus());
    }

    @Test
    void editSubTask_changeStatusSubTaskAndEpic() {
        // given
        initData();

        // when
        taskManager.getSubTaskById(5).setStatus(TaskStatus.IN_PROGRESS);
        taskManager.getSubTaskById(6).setStatus(TaskStatus.IN_PROGRESS);
        taskManager.editSubTask(taskManager.getSubTaskById(5));
        taskManager.editSubTask(taskManager.getSubTaskById(6));

        // then
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getSubTaskById(5).getStatus());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getSubTaskById(6).getStatus());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicById(4).getStatus());
    }

    @Test
    void editSubTask_changeStatusSubTask() {
        // given
        initData();

        // when
        taskManager.getSubTaskById(5).setStatus(TaskStatus.DONE);
        taskManager.getSubTaskById(6).setStatus(TaskStatus.IN_PROGRESS);
        taskManager.editSubTask(taskManager.getSubTaskById(5));
        taskManager.editSubTask(taskManager.getSubTaskById(6));

        // then
        Assertions.assertEquals(TaskStatus.DONE, taskManager.getSubTaskById(5).getStatus());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getSubTaskById(6).getStatus());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicById(4).getStatus());
    }

    @Test
    void clearTasks_taskCountIsZero() {
        // given
        initData();

        // when
        taskManager.clearTasks();

        // then
        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void clearSubTasks_subTaskCountIsZero() {
        // given
        initData();

        // when
        taskManager.clearSubTasks();

        // then
        Assertions.assertEquals(0, taskManager.getSubTasks().size());
        // Проверка, что у всех эпиков нет связанных подзадач
        Assertions.assertEquals(0, taskManager.getEpicById(0).getSubTasksIds().size());
        Assertions.assertEquals(0, taskManager.getEpicById(4).getSubTasksIds().size());
        Assertions.assertEquals(0, taskManager.getEpicById(7).getSubTasksIds().size());
    }

    @Test
    void clearEpics_epicsCountAndSubTasksIsZero() {
        // given
        initData();

        // when
        taskManager.clearEpics();

        // then
        Assertions.assertEquals(0, taskManager.getEpics().size());
        Assertions.assertEquals(0, taskManager.getSubTasks().size());
    }

    @Test
    void getHistory_historyUpdate() {
        // given
        initData();

        // when
        taskManager.getEpicById(0);
        taskManager.getEpicById(4);
        taskManager.getEpicById(0);
        taskManager.getSubTaskById(1);
        taskManager.getSubTaskById(3);
        taskManager.getSubTaskById(2);
        taskManager.getSubTaskById(6);
        taskManager.getSubTaskById(5);
        taskManager.getEpicById(4);
        taskManager.getEpicById(0);
        taskManager.getSubTaskById(3);

        // then
        Assertions.assertEquals(7, taskManager.getHistory().size());
        Assertions.assertEquals(1, taskManager.getHistory().get(0).getId());
        Assertions.assertEquals(2, taskManager.getHistory().get(1).getId());
        Assertions.assertEquals(3, taskManager.getHistory().get(6).getId());

        // when
        taskManager.getSubTaskById(5);
        taskManager.getSubTaskById(6);

        // then
        Assertions.assertEquals(7, taskManager.getHistory().size());
        Assertions.assertEquals(5, taskManager.getHistory().get(5).getId());
        Assertions.assertEquals(6, taskManager.getHistory().get(6).getId());
    }

    @Test
    void removeSubTask_epicUpdateId() {
        // given
        initData();

        // when
        taskManager.deleteSubTask(1);
        taskManager.deleteSubTask(3);

        // then
        Assertions.assertEquals(1, taskManager.getEpicById(0).getSubTasksIds().size());
        Assertions.assertTrue(taskManager.getEpicById(0).getSubTasksIds().contains(2));
        Assertions.assertFalse(taskManager.getEpicById(0).getSubTasksIds().contains(1));
        Assertions.assertFalse(taskManager.getEpicById(0).getSubTasksIds().contains(3));
    }

    @Test
    void removeTasks_historyUpdate() {
        // given
        initData();

        // when
        // Просмотрели эпики, подзадачи
        taskManager.getEpicById(0);
        taskManager.getEpicById(4);
        taskManager.getEpicById(0);
        taskManager.getSubTaskById(1);
        taskManager.getSubTaskById(3);
        taskManager.getSubTaskById(2);
        taskManager.getSubTaskById(6);
        taskManager.getSubTaskById(5);
        taskManager.getEpicById(4);
        taskManager.getEpicById(0);
        taskManager.getSubTaskById(3);

        // Количество уникальных задач - 7
        Assertions.assertEquals(7, taskManager.getHistory().size());
        Assertions.assertEquals(0, taskManager.getHistory().get(5).getId());
        Assertions.assertEquals(3, taskManager.getHistory().get(6).getId());

        // Удаляем эпик с ID 0, привязанные задачи: ID 1, 2, 3
        taskManager.deleteEpic(0);

        Assertions.assertEquals(3, taskManager.getHistory().size());
        Assertions.assertEquals(4, taskManager.getHistory().get(2).getId());

        // Просмотрели задачу с идентификатором 11
        taskManager.getTaskById(11);

        Assertions.assertEquals(4, taskManager.getHistory().size());
        Assertions.assertEquals(11, taskManager.getHistory().get(3).getId());

        // Удалили задачу с идентификатором 11
        taskManager.deleteTask(11);

        Assertions.assertEquals(3, taskManager.getHistory().size());
        Assertions.assertEquals(4, taskManager.getHistory().get(2).getId());
    }

}