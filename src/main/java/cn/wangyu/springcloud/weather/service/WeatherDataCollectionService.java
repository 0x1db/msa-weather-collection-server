package cn.wangyu.springcloud.weather.service;

/**
 * 天气数据采集服务层
 *
 * @Author wangyu
 * @Date 2019/10/11 17:06
 * @Version 1.0
 */
public interface WeatherDataCollectionService {

  /**
   * @Desc 根据城市ID同步天气
   * @Author wangyu
   * @CreateDate 2019/10/11 17:07
   */
  void syncDateByCityId(String cityId);
}
