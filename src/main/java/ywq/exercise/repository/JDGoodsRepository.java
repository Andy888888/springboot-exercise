package ywq.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ywq.exercise.dbo.JDGoods;

/**
 * 描述:JD商品Repository.
 * <p>
 *
 * @author yanwenqiang.
 * @date 2019/6/19
 */
public interface JDGoodsRepository extends JpaRepository<JDGoods, Integer> {
}
