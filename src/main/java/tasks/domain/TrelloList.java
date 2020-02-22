package tasks.domain;

import lombok.Data;

@Data
public class TrelloList {

	private String id;
	private String name;
	private boolean isClosed;
}