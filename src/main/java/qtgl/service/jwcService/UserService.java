package qtgl.service.jwcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qtgl.dao.jwcMapper.UserMapper;
import qtgl.model.jwcModel.User;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService  {
    @Autowired
    private UserMapper userMapper;

    public User findUser(String id){

        return userMapper.findUserById(id);
    }

    public  List<User>  getUser(String id,String account)
    {

        return  userMapper.getUser(id,account);
    }

    public  List<User> findAll()
    {
       return userMapper.findAll();
    }


}
