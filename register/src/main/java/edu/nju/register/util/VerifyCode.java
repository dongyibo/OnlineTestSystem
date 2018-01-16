package edu.nju.register.util;

/**
 * Created by dongyibo on 2017/11/15.
 */
public class VerifyCode {

    private static final int LEN = 8;


    /**
     * 生成随机数验证码
     * @return 验证码
     */
    public static String generateVerifyCode(){
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < LEN ; i++){
            int r = (int) (Math.random() * 36);
            if (r < 10){
                verifyCode.append(r);
            } else {
                char c = (char) (r - 10 + 'a');
                verifyCode.append(c);
            }
        }
        return verifyCode.toString();
    }
}
