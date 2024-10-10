package SecurityApp.services;

import SecurityApp.models.Auth;
import SecurityApp.models.User;

public interface RegistratonService {
     void makeEncode(User user);
     void registerSuperAdmin(User user);
     void registerAdmin(User user, Auth auth4);

}
