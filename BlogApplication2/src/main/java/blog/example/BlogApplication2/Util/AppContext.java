package blog.example.BlogApplication2.Util;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    public static String EMAIL="username";

    public static ThreadLocal<Map<String,String>> appContext=ThreadLocal.withInitial(HashMap::new);
    public static String getEmail(){
        Map<String,String> context=appContext.get();
        return context.get(EMAIL);
    }
    public  static  void setEmail(String email){
        Map<String,String> context=appContext.get();
        context.put(EMAIL,email);
    }
    public static void clearContext(){
        appContext.remove();

    }
}

