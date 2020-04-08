package hiber.dao;

import hiber.model.Car;
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
   public User findUser(Car car) {
      TypedQuery<Object> query = sessionFactory.getCurrentSession().createQuery("select us.id, us.firstName, us.lastName, us.email from Car car inner join User us on car.id=us.id where car.name=:name and car.series=:series")
      .setParameter("name", car.getName())
      .setParameter("series", car.getSeries());
      Object[] obj = (Object[]) query.getResultList().get(0);
      return new User(Long.parseLong(obj[0].toString()), obj[1].toString(), obj[2].toString(), obj[3].toString());
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
