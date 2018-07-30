package dawid.spring.model.dto;

import dawid.spring.model.adnotation.PasswordMatches;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by private on 20.01.18.
 */
@PasswordMatches
public abstract class UserDTO {

    public abstract Long getId();
    public abstract String getFirstName();
    public abstract String getSecondName();

    @NotEmpty
    public abstract String getNickname();
    public abstract Set<TaskDTO> getTasks();

    @NotEmpty
    public abstract String getPassword();
    public abstract String getMatchingPassword();

    @NotEmpty
    @org.hibernate.validator.constraints.Email
    public abstract String getEmail();

    public abstract Long getVersion();

    public void addTask(TaskDTO task) {
        //tasks.add(task);
        //task.setUserName(this.getNickname());
    }
}
