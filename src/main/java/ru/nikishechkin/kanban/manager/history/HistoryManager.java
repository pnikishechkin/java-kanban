package ru.nikishechkin.kanban.manager.history;

import ru.nikishechkin.kanban.model.Task;
import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    ArrayList<Task> getHistory();
}
