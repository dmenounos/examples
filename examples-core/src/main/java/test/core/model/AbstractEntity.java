package test.core.model;

import java.io.Serializable;

public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private long version = 1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		AbstractEntity identifiableEntity = (AbstractEntity) o;
		return getId() != null ? getId().equals(identifiableEntity.getId()) : super.equals(o);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return id != null ? getClass().getSimpleName() + "@" + id : super.toString();
	}
}
