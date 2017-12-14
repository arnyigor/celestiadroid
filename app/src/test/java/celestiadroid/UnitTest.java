package celestiadroid;

import android.support.annotation.NonNull;
import com.arny.arnylib.utils.MathUtils;
import org.junit.Test;

import java.util.Stack;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
	@Test
	public void substring() throws Exception {
		String input = "1.25e56";
		input = input.substring(0, input.length() - 1);
		assertThat(input).isEqualTo("1.25e5");
		input = "555.0";
		input = cut(input);
		assertThat(input).isEqualTo("55");
		input = "55e2";
		input = cut(input);
		assertThat(input).isEqualTo("55");
		input = "55e";
		input = cut(input);
		assertThat(input).isEqualTo("55");
		input = "0.123";
		input = cut(input);
		assertThat(input).isEqualTo("0.12");
		input = "0.123e2";
		input = cut(input);
		assertThat(input).isEqualTo("0.123");
		input = "0.123e";
		input = cut(input);
		assertThat(input).isEqualTo("0.123");
		input = "123e-2";
		input = cut(input);
		assertThat(input).isEqualTo("123");
		input = cut(input);
		assertThat(input).isEqualTo("12");
		input = cut(input);
		assertThat(input).isEqualTo("1");
	}

	@Test
	public void validNumber() throws Exception {
		String input = "1.25e56";
		assertTrue(isValidInput(input));
		input = "1.25ee56";
		assertTrue(!isValidInput(input));
		input = "1.25e-56";
		assertTrue(isValidInput(input));
		input = "1.25e--56";
		assertTrue(!isValidInput(input));
		input = "1.25e";
		assertTrue(!isValidInput(input));
	}

	private boolean isValidInput(String input) {
		Stack<Character> bra = new Stack<>();
		for (char c : input.toCharArray()) {
			if ((c < '(' || c > '9') && c != 101) {
				return false;
			}
			if ('(' == c) {
				bra.push(c);
			} else {
				if (')' == c) {
					if (bra.isEmpty()) return false;
					bra.pop();
				}
			}
		}

		return bra.isEmpty();
	}

	@NonNull
	private String cut(String input) {
		if (input.endsWith(".0")) {
			input = input.substring(0, input.length() - 2);
		}
		input = input.substring(0, input.length() - 1);
		if (input.endsWith("-")) {
			input = cut(input);
		}
		if (input.endsWith("e")) {
			input = cut(input);
		}
		return input;
	}

}