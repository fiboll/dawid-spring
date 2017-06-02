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

    private Task(TaskBuilder taskBuilder) {
        id = taskBuilder.id;
        name = taskBuilder.name;
        desc = taskBuilder.desc;
        dueDate = taskBuilder.dueDate;
    }

    public static class TaskBuilder {
        private Long id;
        private String name;
        private String desc;
        private Date dueDate;

        public Task build() {
            return new Task(this);
        }

        public TaskBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TaskBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public TaskBuilder dueDate(Date dueDate) {
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
