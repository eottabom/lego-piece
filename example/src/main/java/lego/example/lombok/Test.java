package lego.example.lombok;

import lombok.Data;
import lombok.NonNull;

@Data
public class Test {

	@NonNull
	private String name;

	@NonNull
	private String email;

}
