package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("FROM User user JOIN FETCH user.car car");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String hql = "SELECT user FROM User user JOIN FETCH user.car WHERE user.car.model=:m AND user.car.series=:s";
      Query query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("m", model);
      query.setParameter("s", series);
      return (User) query.getSingleResult();
   }
}
