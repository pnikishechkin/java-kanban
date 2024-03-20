package ru.nikishechkin.kanban.manager.history;

import ru.nikishechkin.kanban.model.Task;
import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int id);
    List<Task> getHistory();
}
