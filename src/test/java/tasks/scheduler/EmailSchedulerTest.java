package tasks.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tasks.configuration.AdminConfig;
import tasks.domain.Mail;
import tasks.dao.TaskDao;
import tasks.service.EmailTemplateSelector;
import tasks.service.SimpleEmailService;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTest {
    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TaskDao taskRepository;

    @Test
    public void sendInformationEmailTest() {
        //Given
        when(adminConfig.getAdminMail()).thenReturn("mail@mail.com");
        when(taskRepository.count()).thenReturn(5L);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).
                send(argThat(
                        new MailMatcher(
                                new Mail("mail@mail.com", "", ""))),
                        argThat(new EmailTemplateSelectorMatcher(EmailTemplateSelector.SCHEDULED_EMAIL)));
    }

    private class MailMatcher implements ArgumentMatcher<Mail> {
        private final Mail expected;

        public MailMatcher(Mail expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(Mail mail) {
            return mail.getMailTo().equals(expected.getMailTo());
        }
    }

    private class EmailTemplateSelectorMatcher implements ArgumentMatcher<EmailTemplateSelector>{

        private final EmailTemplateSelector selector;

        public EmailTemplateSelectorMatcher(EmailTemplateSelector selector) {
            this.selector = selector;
        }

        @Override
        public boolean matches(EmailTemplateSelector selectorMatcher) {
            return selectorMatcher.equals(selector);
        }
    }
}
