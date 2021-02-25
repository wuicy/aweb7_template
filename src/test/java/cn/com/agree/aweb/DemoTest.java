package cn.com.agree.aweb;


import cn.com.agree.aweb.demo.entity.po.DemoPO;
import cn.com.agree.aweb.demo.entity.vo.DemoVO;
import cn.com.agree.aweb.demo.mapper.DemoMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {


  @Test
  public void testMapper() {
    DemoMapper mapper = DemoMapper.INSTANCE;
    //po转vo
    DemoPO po = new DemoPO();
    DemoVO vo = mapper.poToVO(po);
    //vo list转po list
    List<DemoVO> voList = new ArrayList<>();
    List<DemoPO> poList = mapper.voToPO(voList);
  }

}
