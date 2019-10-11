package cn.wangyu.springcloud.weather.quartz;

import cn.wangyu.springcloud.weather.model.City;
import cn.wangyu.springcloud.weather.service.WeatherDataCollectionService;
import java.util.ArrayList;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author wangyu
 * @Date 2019/10/11 17:16
 * @Version 1.0
 */
public class WeatherDataSyncJob extends QuartzJobBean {

  private final static Logger LOG = LoggerFactory.getLogger(WeatherDataSyncJob.class);

  @Autowired
  private WeatherDataCollectionService weatherDataCollectionService;

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext)
      throws JobExecutionException {
    LOG.info("Weather Data Sync Job. Start！");
    //TODO 改为由城市数据API微服务来提供数据
    List<City> cityList = null;

    try {
      cityList = new ArrayList<>();
      City city = new City();
      city.setCityId("101280601");
      cityList.add(city);
    } catch (Exception e) {
      LOG.error("Error：", e);
    }

    // 遍历城市ID获取天气
    for (City city : cityList) {
      String cityId = city.getCityId();
      LOG.info("Weather Data Sync Job, cityId:" + cityId);

      weatherDataCollectionService.syncDateByCityId(cityId);
    }

    LOG.info("Weather Data Sync Job. End！");
  }
}
