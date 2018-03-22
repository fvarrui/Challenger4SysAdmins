package fvarrui.sysadmin.challenger.repository.items;

public class Item {

	private Long id;
	private String name;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Item)) {
			return false;
		}
		Item command = (Item) obj;
		if (getId() != null && getId().equals(command.getId())) {
			return true;
		}
		return false;
	}

}
