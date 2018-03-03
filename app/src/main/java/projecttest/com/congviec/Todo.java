package projecttest.com.congviec;

/**
 * Created by Cuong on 3/3/2018.
 */
// cong viec model
public class Todo {
    private String date;
    private String time;
    // ten cong viec
    private String name;
    // Chi tiet cong viec
    private String details;
    // trang thai cong viec
    private String status ="Chưa xong";

    public Todo(String date, String time, String name, String details) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.details = details;
    }
    public void setStatus(String st){
        this.status=st;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}
