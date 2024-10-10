package SecurityApp.services;

import SecurityApp.models.User;

import java.util.List;

public interface PeopleService {

     List<User> findAll();
     User findOne(int id);
     void save(User user);
     void update(int id, User updatedPerson);
     void delete(int id);

}
