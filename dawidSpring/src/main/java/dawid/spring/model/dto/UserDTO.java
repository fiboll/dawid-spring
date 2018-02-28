package dawid.spring.model.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by private on 20.01.18.
 */
public class UserDTO {

    private Long id;
    private String firstName;
    private String secondName;
    private String nickname;
    private Set<TaskDTO> tasks = new HashSet<>();
    private Long version;

    public void addTask(TaskDTO task) {

        if (!tasks.contains(task)) {
            tasks.add(task);
        }

        task.setUserName(this.getNickname());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
