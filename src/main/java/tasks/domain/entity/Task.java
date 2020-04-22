package tasks.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import tasks.domain.ENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public  class Task extends ENTITY<Long> {

	private String title;

	private String content;

	@Builder
	public Task(Long id, String title, String content) {
		super(id);
		this.title = title;
		this.content = content;
	}
}