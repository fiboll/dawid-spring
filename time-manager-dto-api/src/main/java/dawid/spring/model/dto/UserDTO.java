package dawid.spring.model.dto;

import dawid.spring.model.adnotation.PasswordMatches;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by private on 20.01.18.
 */
@PasswordMatches
@Data
@Builder
public class UserDTO {
    private Long id;

    private String firstName;

    private String secondName;

    private String nickname;

    private Set<TaskDTO> tasks;

    private String password;

    private String matchingPassword;

    private String email;

    private Long version;
}
