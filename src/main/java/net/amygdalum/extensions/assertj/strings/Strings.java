package net.amygdalum.extensions.assertj.strings;

import static java.util.regex.Pattern.DOTALL;
import static org.assertj.core.error.ShouldContainPattern.shouldContainPattern;
import static org.assertj.core.error.ShouldNotContainPattern.shouldNotContainPattern;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;
import org.assertj.core.internal.StandardComparisonStrategy;

public class Strings extends org.assertj.core.internal.Strings {

	private static final Strings INSTANCE = new Strings();

	Objects objects = Objects.instance();
	Failures failures = Failures.instance();

	public Strings(ComparisonStrategy comparisonStrategy) {
		super(comparisonStrategy);
	}

	Strings() {
		this(StandardComparisonStrategy.instance());
	}

	public static Strings instance() {
		return INSTANCE;
	}

	public void assertContainsWildcardPattern(AssertionInfo info, CharSequence actual, CharSequence wildcardstring) {
		checkPatternIsNotNull(wildcardstring);
		assertNotNull(info, actual);

		if (!containsWildcardPattern(actual, wildcardstring)) {
			throw failures.failure(info, shouldContainPattern(actual, wildcardstring));
		}
	}

	public static boolean containsWildcardPattern(CharSequence actual, CharSequence wildcardstring) {
		StringTokenizer t = new StringTokenizer(wildcardstring.toString(), "?*", true);
		StringBuilder buffer = new StringBuilder();
		while (t.hasMoreTokens()) {
			String nextToken = t.nextToken();
			if ("?".equals(nextToken)) {
				buffer.append(".?");
			} else if ("*".equals(nextToken)) {
				buffer.append(".*?");
			} else {
				buffer.append(Pattern.quote(nextToken));
			}
		}
		Pattern pattern = Pattern.compile(buffer.toString(), DOTALL);
		Matcher matcher = pattern.matcher(actual);

		return matcher.find();
	}

	public void assertNotContainsWildcardPattern(AssertionInfo info, CharSequence actual, CharSequence wildcardstring) {
		checkPatternIsNotNull(wildcardstring);
		assertNotNull(info, actual);

		if (containsWildcardPattern(actual, wildcardstring)) {
			throw failures.failure(info, shouldNotContainPattern(actual, wildcardstring));
		}
	}

	private void checkPatternIsNotNull(CharSequence pattern) {
		if (pattern == null) {
			throw patternToMatchIsNull();
		}
	}

	private NullPointerException patternToMatchIsNull() {
		return new NullPointerException("The wildcard pattern to match should not be null");
	}

	private void assertNotNull(AssertionInfo info, CharSequence actual) {
		objects.assertNotNull(info, actual);
	}

}
