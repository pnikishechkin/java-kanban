package ru.nikishechkin.kanban.manager.history;

import ru.nikishechkin.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private NodeTask head;
    private NodeTask tail;
    private int size = 0;

    private HashMap<Integer, NodeTask> history = new HashMap<>();

    public InMemoryHistoryManager() {

    }

    /**
     * Добавить последний элемент в двухсвязный список
     * @param node
     */
    private void linkLast(NodeTask node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
        }
        size++;
    }

    /**
     * Удалить ноду из двухсвязного списка
     * @param node
     */
    private void removeNode(NodeTask node) {
        if (node.equals(head)) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (node.equals(tail)) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            // prev <-> node <-> next  =>  prev <-> next
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    /**
     * Добавить задачу в список историй
     * @param task задача, подзадача или эпик
     */
    @Override
    public void add(Task task) {
        // Если не нашлась в хэш мапе, то добавить в конец списка и добавили в мапу
        // Если нашлась нода в хэшмапе, то сначала удалить ее из двухсвязного списка и затем добавить в конец.
        if (task == null) {
            return;
        }
        if (tail != null && tail.data.equals(task)) {
            return;
        }

        Integer idTask = task.getId();
        NodeTask node;
        if (!this.history.containsKey(idTask)) {
            node = new NodeTask(task);
            this.history.put(idTask, node);
        } else {
            node = history.get(idTask);
            this.removeNode(history.get(idTask));
        }
        this.linkLast(node);
    }

    /**
     * Удалить задачу из списка просмотренных
     * @param id идентификатор задачи (подзадачи или эпика)
     */
    @Override
    public void remove(int id) {
        NodeTask node = this.history.get(id);
        if (node != null) {
            this.removeNode(node);
        }
    }

    /**
     * Получить историю просмотров в правильном порядке
     * @return список с последними просмотренными задачами
     */
    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> res = new ArrayList<>();

        NodeTask node = head;
        while (node != null) {
            res.add(node.data);
            node = node.next;
        }
        return res;
    }

    private class NodeTask {
        private Task data;
        private NodeTask next;
        private NodeTask prev;

        public NodeTask(Task data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

}


