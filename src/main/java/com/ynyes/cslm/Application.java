package com.ynyes.cslm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdOrderService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
    
	@Autowired
    private TdOrderService tdOrderService;
	
	@Autowired
	private TdDistributorGoodsService tdDistributorGoodsService;
	
	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}

	@Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
//	    // 定时器
	    Timer timer = new Timer();
	    
	    // 定时任务
	    // 定时查找已确认收货评价订单
	    TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<TdOrder> orderList = tdOrderService.findByStatusId(5L);
                
                Date now = new Date();
                if(null != orderList && orderList.size() > 0)
                {
                	for (TdOrder order : orderList) {
						if(null != order  && null != order.getReceiveTime())
						{
							Calendar calendar = Calendar.getInstance();
            				calendar.setTime(order.getReceiveTime());
            				calendar.add(Calendar.DAY_OF_YEAR, +7 );
            				
            				Date receiveTime = calendar.getTime();
            				// 判断收货时间7天后与现在时间的差，如果现在时间在收货时间7天后，
            				// 则修改订单状态为完成，商品不可被退货及评价
            				if(now.getTime() > receiveTime.getTime())
            				{
            					order.setStatusId(6L);
            					tdOrderService.save(order);
            				}
						}
					}
                }
                
                List<TdOrder> payOrderList = tdOrderService.findByStatusId(2L);
                if(null != payOrderList && payOrderList.size() > 0)
                {
                	for (TdOrder order : payOrderList) {
						if(null != order  && null != order.getOrderTime())
						{
							Calendar calendar = Calendar.getInstance();
            				calendar.setTime(order.getOrderTime());
            				calendar.add(Calendar.MONTH,+1 );
            				
            				Date receiveTime = calendar.getTime();
            				// 一个月未付款的订单自动取消
            				if(now.getTime() > receiveTime.getTime())
            				{
            					order.setStatusId(7L);
            					tdOrderService.save(order);
            				}
						}
					}
                }
                
                
                // 查找超市商品，库存为0设置下架
                List<TdDistributorGoods> list = tdDistributorGoodsService.findByIsOnSaleAndLeftNumber();
                if(null != list  && list.size() > 0 ){
                	for (TdDistributorGoods goods : list) {
						goods.setIsOnSale(false);
					}
                }
                tdDistributorGoodsService.save(list);
            }
        };
        
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 3, 0, 0);
        Date date = calendar.getTime();
        
        timer.schedule(task, date, 1000 * 60 * 60 * 24);

	}
}