package model;

import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/2/16 0016 12:43
 * @Version 1.0
 */
public class sqlKit {
    //将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
    public static void joinIds(List<Integer> idList, StringBuilder ret) {
        ret.append("(");
        boolean isFirst = true;
        for (Integer id : idList) {
            if (isFirst) {
                isFirst = false;
            } else {
                ret.append(", ");
            }
            ret.append(id.toString());
        }
        ret.append(")");
    }
}