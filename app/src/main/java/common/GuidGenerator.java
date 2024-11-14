package common;
import java.util.UUID;

public class GuidGenerator {
    public static String generateGUID() {
        // Tạo một UUID ngẫu nhiên
        UUID uuid = UUID.randomUUID();
        return uuid.toString(); // Chuyển UUID thành chuỗi
    }
}
