package tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tasks.configuration.AdminConfig;
import tasks.configuration.CompanyConfig;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    AdminConfig adminConfig;

    CompanyConfig companyConfig;

    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public MailCreatorService(AdminConfig adminConfig, CompanyConfig companyConfig, TemplateEngine templateEngine) {
        this.adminConfig = adminConfig;
        this.companyConfig = companyConfig;
        this.templateEngine = templateEngine;
    }

    Context createBasicContext() {
        Context context = new Context();

        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("admin_config", adminConfig);
        return context;
    }

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = createBasicContext();
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("preview", "Trello app - new card added");
        context.setVariable("message", message);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduledEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = createBasicContext();
        context.setVariable("preview", "Trello app - Your daily information");
        context.setVariable("message", message);
        context.setVariable("button", "See tasks");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/scheduled-mail", context);
    }
}
