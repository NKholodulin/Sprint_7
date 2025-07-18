public class CourierData {
    private String login;
    private String password;
    private String firstName;

    // конструктор со всеми параметрами
    public CourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

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


    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
