package qtgl.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtgl.service.jwcService.UserService;
import qtgl.service.rscService.ToKenService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class HelloWorldController {

    @Resource
    private UserService userService;

    @Resource
    private ToKenService toKenService;

    @RequestMapping("/hello")
    public String index() {

        System.out.println("111111111111111");
        test();


        return "Hello World";
    }

    public  void  test()
    {

        System.out.println("调用了：onInstanceStarting");

        System.out.println("教务处:"+userService.findAll().size());

        System.out.println("人事处:"+toKenService.findAll().size());






        System.out.println("多数据源测试");

        System.out.println("多数据元测算111111");
        System.out.println("测试");


    }

}
