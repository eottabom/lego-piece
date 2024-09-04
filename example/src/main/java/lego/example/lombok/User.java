package lego.example.lombok;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
// 명시적으로 포함될 필드를 지정한다.
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String email;

	// equals() 와 hashCode() 를 직접 구현하여, id 가 null 일 때와 아닐 때를 적절히 처리한다.
	// id 가 null 일 경우 name 과 email 필드만으로 비교하고,
	// id 가 아닐 때 id 를 기준으로 비교한다.
	// 이렇게 하면 영속화 전후에 equals() 와 hashCode() 가 적절히 동작한다.
//	@Override
//	public boolean equals(Object o) {
//		if (this == o) {
//			return true;
//		}
//		if (o == null || getClass() != o.getClass()) {
//			return false;
//		}
//		User user = (User) o;
//		if (this.id != null && user.id != null) {
//			return this.id.equals(user.id);
//		}
//		return this.name.equals(user.name) && this.email.equals(user.email);
//	}
//
//	@Override
//	public int hashCode() {
//		if (this.id != null) {
//			return this.id.hashCode();
//		}
//		return this.name.hashCode() + this.email.hashCode();
//	}
}
