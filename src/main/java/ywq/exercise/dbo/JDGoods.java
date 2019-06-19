package ywq.exercise.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:JD商品数据实体.
 * <p>
 *
 * @author yanwenqiang.
 * @date 2019/6/19
 */
@Getter
@Setter
@Entity
@Table(name = "jd_goods")
public class JDGoods {

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //标准产品单位（商品集合）
    private Long spu;
    //库存量单位（最小品类单元）
    private Long sku;
    //商品标题
    private String title;
    //商品价格
    private Double price;
    //商品图片
    @Lob
    @Column(columnDefinition = "text")
    private String pic;
    //商品详情地址
    @Lob
    @Column(columnDefinition = "text")
    private String url;
    //创建时间
    private Date created;
    //更新时间
    private Date updated;
}
