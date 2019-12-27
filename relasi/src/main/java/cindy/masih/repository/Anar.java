package cindy.masih.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import cindy.masih.model.Adna;

@Singleton
public class Anar implements Anai {

    @PersistenceContext
    private EntityManager manager;

    public Anar(@CurrentSession EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        Long count = manager.createQuery("select count(*) from Adna where deleted_at IS NULL", Long.class).getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public List<Adna> findAll(int page, int limit) {
        TypedQuery<Adna> query = manager
                .createQuery("from Insert where deleted_at IS NULL", Adna.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Adna findById(@NotNull Long id) {
        Adna query = manager.find(Adna.class, id);
        return query;
    }

    @Override
    @Transactional
    public boolean save(@NotNull Adna Adna) {
        try {
            manager.persist(adna);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name,String email, String password, String data) {
        try {

            Adna c = manager.find(Adna.class, id);
            if (name.equals("-")==false) c.setName(name);
            if (email.equals("-")==false) c.setEmail(email);
            if (password.equals("-")==false) c.setPassword(password);
            if (data.equals("-")==false) c.setData(data);
            
          //  if (grade != 0) c.setGrade(grade);
            c.setUpdated_At(new Date());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean destroy(@NotNull Long id) {
    try {
        Adna adna = manager.find(Adna.class, id); // diganti 
        if(adna != null){
            manager.remove(insert);
        }
        adna.setDeleted_At(new Date());
        return true;
        } catch (Exception e) {
            return false;
        }
    }

}