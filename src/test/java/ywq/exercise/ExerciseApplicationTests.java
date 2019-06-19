package ywq.exercise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ywq.exercise.controller.JDGoodsController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

	@Autowired
	private JDGoodsController jdGoodsController;

	@Test
	public void contextLoads() {
		try {
			jdGoodsController.crawlerItem("手机",1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
