/*
 * @Author: ykk 
 * @Date: 2023-05-05 20:14:24 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 20:18:45
 * @Version: 2.0
 */

package oz1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("oz1.mapper")
@SpringBootApplication
public class application {
  public static void main(String[] args) {
    SpringApplication.run(application.class, args);
  }
}
