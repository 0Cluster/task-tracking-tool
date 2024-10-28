import com.cluster.tasktrackingtool.models.User;
public interface UserRepository extends JPARepository<User, Long> {
    
    User findByUsername(String username);
    
}
