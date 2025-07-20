import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CourierData {
    private String login;
    private String password;
    private String firstName;

    public static CourierData withLoginAndPassword(String login, String password) {
        CourierData data = new CourierData();
        data.login = login;
        data.password = password;
        return data;
    }

    public static CourierData withPasswordAndFirstName(String password, String firstName) {
        CourierData data = new CourierData();
        data.password = password;
        data.firstName = firstName;
        return data;
    }
    public static CourierData withLoginAndFirstName(String login, String firstName) {
        CourierData data = new CourierData();
        data.login = login;
        data.firstName = firstName;
        return data;
    }

    // конструктор без параметров
    public CourierData() {
    }
}
