package ru.nikishechkin.kanban.manager.task;

import ru.nikishechkin.kanban.model.Epic;
import ru.nikishechkin.kanban.model.SubTask;
import ru.nikishechkin.kanban.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    ArrayList<Task> getTasks();

    void clearEpics();

    void clearSubTasks();

    void clearTasks();

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    Task getTaskById(int id);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    void addTask(Task task);

    void editEpic(Epic epic);

    void editSubTask(SubTask subTask);

    void editTask(Task task);

    void deleteEpic(int id);

    void deleteSubTask(int id);

    void deleteTask(int id);

    ArrayList<SubTask> getListEpicSubTasks(int epicId);

    List<Task> getHistory();
}
