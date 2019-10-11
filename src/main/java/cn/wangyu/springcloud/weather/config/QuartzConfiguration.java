package cn.wangyu.springcloud.weather.config;

import cn.wangyu.springcloud.weather.quartz.WeatherDataSyncJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时器配置
 *
 * @Author wangyu
 * @Date 2019/10/11 17:15
 * @Version 1.0
 */
@Configuration
public class QuartzConfiguration {
  private static final int TIME = 1800; // 更新频率

  // JobDetail
  @Bean
  public JobDetail weatherDataSyncJobDetail() {
    return JobBuilder.newJob(WeatherDataSyncJob.class).withIdentity("weatherDataSyncJob")
        .storeDurably().build();
  }

  // Trigger
  @Bean
  public Trigger weatherDataSyncTrigger() {

    SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInSeconds(TIME).repeatForever();

    return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
        .withIdentity("weatherDataSyncTrigger").withSchedule(schedBuilder).build();
  }

}
