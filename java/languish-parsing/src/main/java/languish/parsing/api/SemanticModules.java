package languish.parsing.api;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class SemanticModules {

	public static final Function<List<Object>, Object> UNWRAP_INODE_RULE =
			new Function<List<Object>, Object>() {

				@Override
				public Object apply(List<Object> arg0) {
					return arg0.get(0);
				}
			};

	public static final Function<List<Object>, Object> EMPTY_LIST_RULE =
			new Function<List<Object>, Object>() {
				@Override
				public Object apply(List<Object> arg) {
					return ImmutableList.of();
				}
			};

	public static final Map<String, Function<String, Object>> getIdentityLeafRules(
			List<String> ruleNames) {

		ImmutableMap.Builder<String, Function<String, Object>> result =
				ImmutableMap.builder();

		for (String ruleName : ruleNames) {
			result.put(ruleName, new Function<String, Object>() {
				@Override
				public Object apply(String from) {
					return from;
				}
			});
		}

		return result.build();
	}

	public static final Map<String, Function<List<Object>, Object>> getUnwrapRules(
			List<String> ruleNames) {

		ImmutableMap.Builder<String, Function<List<Object>, Object>> result =
				ImmutableMap.builder();

		for (String ruleName : ruleNames) {
			result.put(ruleName, UNWRAP_INODE_RULE);
		}

		return result.build();
	}

	public static Map<String, Function<List<Object>, Object>> getEmptyListLeafRules(
			List<String> ruleNames) {

		ImmutableMap.Builder<String, Function<List<Object>, Object>> result =
				ImmutableMap.builder();

		for (String ruleName : ruleNames) {
			result.put(ruleName, EMPTY_LIST_RULE);
		}

		return result.build();
	}

	private SemanticModules() {
	}
}
