package tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DTO <IDTYPE extends Serializable > implements Serializable {

    @JsonProperty("id")
    private IDTYPE  id;
}
