package dawid.spring.model;

import java.util.Date;

/**
 * Created by private on 31.05.17.
 */
public class Task {

    private Long id;
    private String name;
    private String desc;
    private Date dueDate;

    private Task() {}

    private Task(TaskBuider taskBuider) {
        id = taskBuider.id;
        name = taskBuider.name;
        desc = taskBuider.desc;
        dueDate = taskBuider.dueDate;
    }

    public static class TaskBuider {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;

        public Task build() {
            return new Task(this);
        }

        public TaskBuider id(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuider name(String name) {
            this.name = name;
            return this;
        }

        public TaskBuider desc(String desc) {
            this.desc = desc;
            return this;
        }

        public TaskBuider dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getDueDate() {
        return (Date) dueDate.clone();
    }
}
