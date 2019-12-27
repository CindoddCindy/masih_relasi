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
import cindy.masih.model.NonAnggota;

@Singleton
public class Nar implements Nai {

    @PersistenceContext
    private EntityManager manager;

    public Nar(@CurrentSession EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        Long count = manager.createQuery("select count(*) from NonAnggota where deleted_at IS NULL", Long.class).getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public List<NonAnggota> findAll(int page, int limit) {
        TypedQuery<NonAnggota> query = manager
                .createQuery("from NonAnggota where deleted_at IS NULL", NonAnggota.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public NonAnggota findById(@NotNull Long id) {
        NonAnggota query = manager.find(NonAnggota.class, id);
        return query;
    }

    @Override
    @Transactional
    public boolean save(@NotNull NonAnggota nonanggota) {
        try {
            manager.persist(nonanggota);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name,String email, String password, String data) {
        try {

            NonAnggota c = manager.find(NonAnggota.class, id);
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
        NonAnggota nonanggota = manager.find(NonAnggota.class, id); // diganti 
        if(insert != null){
            manager.remove(insert);
        }
        nonanggota.setDeleted_At(new Date());
        return true;
        } catch (Exception e) {
            return false;
        }
    }

}