package cn.wangyu.springcloud.weather.service.impl;

import cn.wangyu.springcloud.weather.service.WeatherDataCollectionService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 数据采集服务层实现
 *
 * @Author wangyu
 * @Date 2019/10/11 17:06
 * @Version 1.0
 */
@Service
public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService {

  private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

  private static final long TIME_OUT = 1800L; // 1800s

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public void syncDateByCityId(String cityId) {
    String uri = WEATHER_URI + "citykey=" + cityId;
    this.saveWeatherData(uri);
  }

  private void saveWeatherData(String uri) {
    String key = uri;
    String strBody = null;
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

    // 调用服务接口来获取
    ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

    if (respString.getStatusCodeValue() == 200) {
      strBody = respString.getBody();
    }

    // 数据写入缓存
    ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
  }
}
