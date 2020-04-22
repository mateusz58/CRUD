package tasks.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

    @NonNull
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NonNull
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @NonNull
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

}