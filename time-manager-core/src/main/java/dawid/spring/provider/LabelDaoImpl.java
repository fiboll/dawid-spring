package dawid.spring.provider;

import dawid.spring.model.entity.Label;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LabelDaoImpl implements LabelDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    @SuppressWarnings("unchecked")
    public List<Label> getAllLabels() {
        return em.createQuery("SELECT DISTINCT l FROM Label l FETCH ALL PROPERTIES").getResultList();
    }
}
