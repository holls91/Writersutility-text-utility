package iterators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JaroDistance {
	public static double applyMine(String s, String t) {
    	s = convert(s).toLowerCase();
    	t = convert(t).toLowerCase();
        int s_len = s.length();
        int t_len = t.length();
 
        if (s_len == 0 && t_len == 0) return 1;
 
        int match_distance = Integer.max(s_len, t_len) / 2 - 1;
 
        boolean[] s_matches = new boolean[s_len];
        boolean[] t_matches = new boolean[t_len];
 
        int matches = 0;
        int transpositions = 0;
 
        for (int i = 0; i < s_len; i++) {
            int start = Integer.max(0, i-match_distance);
            int end = Integer.min(i+match_distance+1, t_len);
 
            for (int j = start; j < end; j++) {
                if (t_matches[j]) continue;
                if (s.charAt(i) != t.charAt(j)) continue;
                s_matches[i] = true;
                t_matches[j] = true;
                matches++;
                break;
            }
        }
 
        if (matches == 0) return 0;
 
        int k = 0;
        for (int i = 0; i < s_len; i++) {
            if (!s_matches[i]) continue;
            while (!t_matches[k]) k++;
            if (s.charAt(i) != t.charAt(k)) transpositions++;
            k++;
        }
 
        return (((double)matches / s_len) +
                ((double)matches / t_len) +
                (((double)matches - transpositions/2.0) / matches)) / 3.0;
    }
	
	private static String convert(String html) {
		Pattern pattern = Pattern.compile("(?:(.*?)?(&#(\\d{1,});))");
		Matcher matcher = pattern.matcher(html);
		
		if(matcher.find()){
			String replaced = String.valueOf((char) Integer.parseInt(matcher.group(3)));
			return html.replace(matcher.group(2), replaced);
		}
		
		return html;
	}
}
