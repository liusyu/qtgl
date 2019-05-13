package qtgl.dao.jwcMapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import qtgl.model.jwcModel.User;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") String id);

    @Select("select * from user where id=#{id} and account=#{account}")
    List<User> getUser(@Param("id")String id,@Param("account") String account);

    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(account,email) values(#{name},#{age})")
    int insert(@Param("name") String name,@Param("age") String age);

    @Update("update user set account=#{account} where id=#{id}")
    void updateAccount(@Param("account")String account,@Param("id") long id);

    @Delete("delete from user where id=#{id}")
    void delete(@Param("id") long id);


}
