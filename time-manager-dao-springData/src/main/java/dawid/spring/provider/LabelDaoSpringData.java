package dawid.spring.provider;

import dawid.spring.model.entity.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LabelDaoSpringData extends CrudRepository<Label, Long>, LabelDao {

    @Override
    default List<Label> getAllLabels() {
        List<Label> result = new ArrayList<>();
        findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}
