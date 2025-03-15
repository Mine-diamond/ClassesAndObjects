import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AutoLogin {

    public static void main(String[] args) throws Exception {
        // 延迟5秒，给用户准备时间
        System.out.println("等待5秒...");
        Thread.sleep(5000);

        // 读取并解密凭证
        String[] credentials = loadCredentials();
        if (credentials == null || credentials.length < 2) {
            System.out.println("读取凭证失败，用户名或密码格式错误。");
            return;
        }

        String username = credentials[0];
        String password = credentials[1];

        System.out.println("用户名: " + username);
        System.out.println("密码: " + password);

        Robot robot = new Robot();

        // 输入用户名
        typeString(robot, username);

        // Tab 切换到密码输入框
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        // 输入密码
        typeString(robot, password);

        // Tab 切换到登录按钮
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        // 按下 Enter 键登录
        Thread.sleep(100);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    // 从 credentials.dat 文件中读取并解密凭证
    private static String[] loadCredentials() {
        try {
            // 读取密钥
            FileInputStream keyFile = new FileInputStream("output/keyfile.key");
            byte[] keyBytes = new byte[keyFile.available()];
            keyFile.read(keyBytes);
            keyFile.close();

            // 读取加密的凭证
            FileInputStream fileIn = new FileInputStream("output/credentials.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            String encryptedCredentials = (String) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            // 解密凭证
            byte[] decryptedBytes = xorWithKey(Base64.getDecoder().decode(encryptedCredentials), keyBytes);
            String decryptedCredentials = new String(decryptedBytes, StandardCharsets.UTF_8);

            // 分割用户名和密码
            return decryptedCredentials.split(":");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 模拟键盘输入字符串，并处理特殊字符
    private static void typeString(Robot robot, String input) throws InterruptedException {
        for (char c : input.toCharArray()) {
            if (c == '@') {
                // 输入 '@' 字符时模拟 Shift + 2
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (Character.isUpperCase(c)) {
                // 处理大写字母
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                // 处理普通字符
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));
            }
            Thread.sleep(100);  // 每个字符之间延迟100ms
        }
    }

    // 使用密钥进行 XOR 解密
    private static byte[] xorWithKey(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}
