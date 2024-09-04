package lego.example.lombok;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserTests {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	void equalsAndHashCodeBeforeAndAfterPersist() {
		User firstUser = new User("tester", "tester@example.com");
		User secondUser = new User("tester", "tester@example.com");

		// 영속화 전에는 id가 null 이므로, equals()와 hashCode()가 같게 동작한다.
		assertThat(firstUser).isEqualTo(secondUser);
		assertThat(firstUser.hashCode()).isEqualTo(secondUser.hashCode());

		this.entityManager.persist(firstUser);
		this.entityManager.persist(secondUser);
		this.entityManager.flush(); // 실제로 DB에 반영하여 id 할당

		// 영속화 이후에 id 가 할당되면, 서로 다른 객체로 인식될 수 있다.
		assertThat(firstUser).isNotEqualTo(secondUser);
		assertThat(firstUser.hashCode()).isNotEqualTo(secondUser.hashCode());
	}

	@Test
	@Transactional
	void hashCodeIssueWithIdAssignment() {
		Set<User> users = new HashSet<>();

		User user = new User("tester", "tester@example.com");

		assertThat(users.add(user)).isTrue(); // 성공적으로 추가됨
		assertThat(users.contains(user)).isTrue(); // Set 에 포함됨

		user.setId(1L);

		// 같은 객체임에도 불구하고 id가 바뀌었으므로 Set 에서 찾을 수 없음
		// assertThat(users.contains(user)).isFalse(); // 여기서 실패.

		// Set 에 다시 추가할 경우, 같은 객체임에도 불구하고 다시 추가될 수 있음
		// assertThat(users.add(user)).isTrue();

		users.add(user); // 같은 객체임에도 id 가 null 에서 1L 로 변경이 되어서,
		assertThat(users).hasSize(2); // 다시 추가되어서 Set 의 size 가 2가 된다.
	}

}
