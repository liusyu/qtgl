package qtgl.service.rscService;

import org.springframework.stereotype.Service;
import qtgl.dao.rscMapper.RscMapper;
import qtgl.model.rscModel.ToKen;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ToKenService {

    @Resource
    private RscMapper RscMapper;

    public ToKen findToKenById(String id)
    {

        return  RscMapper.findToKenById(id);

    }

    public List<ToKen> findAll()
    {
        return RscMapper.findAll();

    }

}
