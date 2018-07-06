package dawid.spring.transformer;

import dawid.spring.model.dto.UserDTO;
import dawid.spring.model.entity.User;

/**
 * Created by private on 20.01.18.
 */
public interface IUserTransformer {
    UserDTO entityToDTO(User user);
    User update(User user, UserDTO userDTO);
    User create(UserDTO accountDto);
}
