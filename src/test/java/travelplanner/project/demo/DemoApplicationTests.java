package travelplanner.project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"cloud.aws.credentials.access-key=test-access-key",
		"cloud.aws.credentials.secret-key=test-secret-key",
		"cloud.aws.region.static=test-region",
		"cloud.aws.s3.bucket=test-bucket"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
