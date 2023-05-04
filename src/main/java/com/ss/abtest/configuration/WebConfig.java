package com.ss.abtest.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author senn
 * @since 2023/4/20 16:28
 **/
@Log4j2
@Configuration
public class WebConfig {

//    @Bean
//    public Converter<String, LocalDateTime> localDateTimeConvert() {
//        return new Converter<String, LocalDateTime>() {
//            @Override
//            public LocalDateTime convert(String source) {
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime dateTime = null;
//                try {
//                    //2020-01-01 00:00:00
//                    switch (source.length()){
//                        case 10:
//                            log.debug("传过来的是日期格式：{}",source);
//                            source=source+" 00:00:00";
//                            break;
//                        case 13:
//                            log.debug("传过来的是日期 小时格式：{}",source);
//                            source=source+":00:00";
//                            break;
//                        case 16:
//                            log.debug("传过来的是日期 小时:分钟格式：{}",source);
//                            source=source+":00";
//                            break;
//                    }
//                    dateTime = LocalDateTime.parse(source, df);
//                } catch (Exception e) {
//                    log.error(e.getMessage(),e);
//                }
//                return dateTime;
//            }
//        };
//    }

}
