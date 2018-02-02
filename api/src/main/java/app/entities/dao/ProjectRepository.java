package app.entities.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Bublik on 21-Nov-17.
 */
@Repository
@Transactional
public class ProjectRepository {

    @Autowired
    SessionFactory sessionFactory;


}
