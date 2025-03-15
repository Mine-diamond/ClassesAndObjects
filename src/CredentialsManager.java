import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class CredentialsManager {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java CredentialsManager <username> <password>");
            return;
        }

        String username = args[0];
        String password = args[1];
        String credentials = username + ":" + password;

        // 生成密钥
        byte[] key = generateKey();
        saveKey(key);

        // 加密凭证
        byte[] encryptedCredentials = xorWithKey(credentials.getBytes(StandardCharsets.UTF_8), key);

        // 保存加密的凭证到 credentials.dat 文件
        saveCredentials(encryptedCredentials);

        System.out.println("Credentials saved successfully.");
    }

    // 生成密钥
    private static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];  // 128-bit key
        random.nextBytes(key);
        return key;
    }

    // 保存密钥到 keyfile.key
    private static void saveKey(byte[] key) throws Exception {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        try (FileOutputStream keyOut = new FileOutputStream("output/keyfile.key")) {
            keyOut.write(key);
        }
    }

    // 保存加密后的凭证到 credentials.dat
    private static void saveCredentials(byte[] encryptedCredentials) throws Exception {
        try (FileOutputStream fileOut = new FileOutputStream("output/credentials.dat");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(Base64.getEncoder().encodeToString(encryptedCredentials));
        }
    }

    // XOR 加密方法
    private static byte[] xorWithKey(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}
