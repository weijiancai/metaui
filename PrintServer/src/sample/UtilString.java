package sample;

/**
 * @author wei_jc
 * @since 1.0
 */
public class UtilString {
    public static String substringByByte(String var0, int var1, int var2) {
        if(var0 == null) {
            return null;
        } else {
            byte[] var3 = var0.getBytes();
            if(var2 > var3.length) {
                var2 = var3.length;
            }

            byte[] var4 = new byte[var2 - var1];
            System.arraycopy(var3, var1, var4, 0, var2 - var1);
            return new String(var4);
        }
    }
}
