import com.alibaba.fastjson.JSONObject;
import com.yewen.entity.SocketMap;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","taotao");
        jsonObject.put("name","a");
        list.add((JSONObject) jsonObject.clone());
        jsonObject.clear();
        System.out.println(list.toString());
    }
}
