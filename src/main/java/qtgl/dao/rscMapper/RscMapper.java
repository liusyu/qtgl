package qtgl.dao.rscMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import qtgl.model.rscModel.ToKen;

import java.util.List;

@Repository
public interface RscMapper {

    @Select("select * from token where id=#{id}")
    ToKen findToKenById(@Param("id") String id);

    @Select("select * from token")
    List<ToKen> findAll();
}
