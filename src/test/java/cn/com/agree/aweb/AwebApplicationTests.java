package cn.com.agree.aweb;

import cn.com.agree.aweb.common.base.dao.ExampleWithRangeSpecification;
import cn.com.agree.aweb.common.base.dao.Range;
import cn.com.agree.aweb.common.crypto.DES3Util;
import cn.com.agree.aweb.common.crypto.SM3Util;
import cn.com.agree.aweb.common.util.DesensitizationUtil;
import cn.com.agree.aweb.common.util.StringUtil;
import cn.com.agree.aweb.dao.AuthorityDao;
import cn.com.agree.aweb.entity.po.AuthorityPO;
import cn.com.agree.aweb.entity.vo.AuthorityVO;
import cn.com.agree.aweb.service.UserService;
import com.alibaba.fastjson.JSON;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AwebApplicationTests {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  public void test() {
    System.out.println(passwordEncoder.encode("123456"));
  }

  @Test
  public void test1()
      throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException {
    Security.addProvider(new BouncyCastleProvider());
    final String str = "123";

    MessageDigest md1 = MessageDigest.getInstance("SM3");
    byte[] byte1 = md1.digest(str.getBytes());

    SM3Digest md2 = new SM3Digest();
    md2.update(str.getBytes(), 0, str.getBytes().length);
    byte[] byte2 = new byte[md2.getDigestSize()];
    md2.doFinal(byte2, 0);

    System.out.println(byte1.equals(byte2));
    System.out.println(byte1 == byte2);
    System.out.println(Arrays.equals(byte1, byte2));

  }

  @Test
  public void test2() throws UnsupportedEncodingException {
    String str = "123";

    String hexStr = SM3Util.encrypt(str);
    System.out.println(SM3Util.verifyString(new String("123"), hexStr));
  }

  @Test
  public void test3() {
    Class clazz = AuthorityVO.class;
    System.out.println(clazz.getName());
    System.out.println(clazz.getTypeName());
    System.out.println(clazz.getSimpleName());
    System.out.println(clazz.getCanonicalName());
  }

  @Autowired
  AuthorityDao authorityDao;

  @Test
  public void test5() {
    AuthorityPO po = new AuthorityPO();
    po.setId("a");
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withStringMatcher(StringMatcher.CONTAINING)
        .withIgnoreCase();
    Example<AuthorityPO> example = Example.of(po, matcher);

    List<Range> ranges = new ArrayList<>();
    ranges.add(new Range("createTime", "1577254457368", "1577254471036"));

    Specification<AuthorityPO> specification = new ExampleWithRangeSpecification<>(example, ranges);
    System.out.println(authorityDao.findAll(specification, Pageable.unpaged()).getContent());
  }

  @Test
  public void test6() {
//    List<RoleVO> data = new ArrayList<>();
//    RoleVO role = new RoleVO();
//    role.setName("role");
//    role.setDesc("123");
//    data.add(role);
//
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    EasyExcel.write(baos, RoleVO.class).sheet().doWrite(data);
//
//    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//    PDFUtil.excel2PDF(bais,true);
  }

  @Test
  public void test7() {
    String json = "[\n"
        + "  {\n"
        + "    \"createTime\": \"\\\"\",\n"
        + "    \"createUserName\": \"string\",\n"
        + "    \"desc\": \"string\",\n"
        + "    \"id\": \"string\",\n"
        + "    \"name\": \"string\",\n"
        + "    \"updateTime\": \"string\",\n"
        + "    \"updateUserName\": \"string\"\n"
        + "  }\n"
        + "]";

    Object object = JsonFilterUtil.cleanJSON(JSON.parse(json));
    System.out.println(object);
  }

  @Test
  public void test8() throws Exception {
    System.out.println(DES3Util.encode("123456"));
    System.out.println(DES3Util.decode("R1hNqjnpnOg="));
  }

  @Test
  public void test9()   {
    System.out.println(DesensitizationUtil.around("13610517764",3,4));
  }



}
