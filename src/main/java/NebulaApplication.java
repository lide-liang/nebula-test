import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lideliang
 * @date 2024/12/16 10:06
 */
@SpringBootApplication(scanBasePackages = {"com.ldl.nebula"},  exclude = {DataSourceAutoConfiguration.class})
public class NebulaApplication {

    public static void main(String[] args) {
        new SpringApplication(NebulaApplication.class).run(args);

    }
}

