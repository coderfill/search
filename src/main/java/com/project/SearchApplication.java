import com.project.data.jpa.GenericRepository;
import com.project.data.jpa.GenericRepositoryFactoryBean;
import com.project.spider.csdn.CsdnPageProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import us.codecraft.webmagic.Spider;

@EnableAutoConfiguration
@ComponentScan("com.project")
@EnableJpaRepositories(basePackages = "{com.project}", repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class)
@EntityScan("com.project")
public class SearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
		Spider.create(new CsdnPageProcessor())
				.addUrl("https://blog.csdn.net/")
				.thread(5)
				.run();
	}

}

