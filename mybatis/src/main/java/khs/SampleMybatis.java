package khs;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SampleMybatis {
	private static final Logger logger = LogManager.getLogger(SampleMybatis.class);

	public static void main(String[] args) throws Exception {
		logger.debug("Hello Mybatis");
		
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
		  HumanMapper mapper = session.getMapper(HumanMapper.class);
		  List<HumanTable> human = mapper.selectHuman();
		  human.forEach((item)->{
			  logger.debug(item.name + " " + item.age);
		  });
		} finally {
		  session.close();
		}
	}
}