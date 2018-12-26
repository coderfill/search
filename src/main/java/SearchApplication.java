import com.project.spider.CSDNPageProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import us.codecraft.webmagic.Spider;

@EnableAutoConfiguration
@ComponentScan("com.project")
public class SearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
		Spider.create(new CSDNPageProcessor())
				.addUrl("https://blog.csdn.net/")
				.thread(5)
				.run();
	}

}

