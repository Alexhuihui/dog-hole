package top.alexmmd.dog.service.task;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.alexmmd.dog.domain.entity.Project;
import top.alexmmd.dog.service.IEmailService;
import top.alexmmd.dog.service.IProjectService;

@Component
@Profile("prod")
public class CodeMartTask {

    @Resource
    private IProjectService projectService;

    @Resource
    private IEmailService emailService;

    private static final String API_URL = "https://codemart.com/api/project?page=1&role=develop_team&skill=";

    private final RestTemplate restTemplate;

    public CodeMartTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 180000) // Schedule to run every three minutes (180,000 milliseconds)
    public void fetchAndParseData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(ListUtil.of(StandardCharsets.UTF_8));

        // Set the request headers to accept UTF-8 character encoding
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setAcceptCharset(ListUtil.of(StandardCharsets.UTF_8));
            return execution.execute(request, body);
        });

        // Send a GET request to the API and retrieve the response as byte array
        byte[] responseBytes = restTemplate.getForObject(API_URL, byte[].class);

        // Convert the response bytes to a UTF-8 encoded string
        String response = new String(responseBytes, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        JSONArray jsonArray = jsonObject.getJSONArray("rewards");
        jsonArray.forEach(e -> {
            Project bean = JSONUtil.toBean(e.toString(), Project.class);
            String name = bean.getName();
            int ownerId = bean.getOwnerId();
            Project byOwnerIdAndName = projectService.findByOwnerIdAndName(ownerId, name);
            if (ObjectUtil.isNull(byOwnerIdAndName)) {
                projectService.saveProject(bean);
                // Generate the email body with project details
                String body = generateEmailBody(bean);

                // Send an email with the project information
                String toEmail = "201520180114@ecut.edu.cn";
                String subject = "New Project Notification";

                emailService.sendEmail(toEmail, subject, body);
            }
        });
    }

    private String generateEmailBody(Project project) {
        StringBuilder body = new StringBuilder();
        body.append(
                "Hello,\n\nA new project has been posted on our platform. Here are the project details:\n\n");
        body.append("Owner ID: ").append(project.getOwnerId()).append("\n");
        body.append("Name: ").append(project.getName()).append("\n");
        body.append("Description: ").append(project.getDescription()).append("\n");
        body.append("Cover: ").append(project.getCover()).append("\n");
        body.append("Price: ").append(project.getPrice()).append("\n");
        // Append other properties of the Project object as needed

        body.append("\n\nBest regards,\nYour Project Platform");
        return body.toString();
    }
}

