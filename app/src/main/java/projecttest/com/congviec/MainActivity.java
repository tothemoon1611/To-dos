package projecttest.com.congviec;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // Listview cong viec
    private ListView todoList;
    // Dialog id
    final private int DATE_DIALOG_ID =1;
    final private int TIME_DIALOG_ID =2;
    // Cac bo lang nghe su kien chon ngay gio
    private DatePickerDialog.OnDateSetListener datePickerLisner;
    private TimePickerDialog.OnTimeSetListener timePickerLisner;
    // cac thanh phan cua activty
    TextView tvDate;
    TextView tvTime;
    Button btnAdd;
    Button btnTime;
    Button btnDate;
    EditText edtName;
    EditText edtDetail;
    // danh sach cong viec
    ArrayList<Todo> todos;
    // cong viec vua duoc xoa, dug de undo
    Todo tmpTodo;
    // custom adapter cho listview
    TodosListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khoi tao cac thanh phan cua activity
        btnAdd = findViewById(R.id.btn_themcv);
        btnDate =findViewById(R.id.btn_date);
        btnTime = findViewById(R.id.btn_timepicker);
        todoList = findViewById(R.id.list_congviec);
        tvDate =findViewById(R.id.tv_date);
        tvTime =findViewById(R.id.tv_time);
        btnTime.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        edtDetail =findViewById(R.id.edt_noidung);
        edtName = findViewById(R.id.edt_congviec);
        todos = new ArrayList<Todo>();
        init();
    }

    @Override
    public void onClick(View v) {
     if (v.getId()==R.id.btn_date) {
        // Hien thi dialog chon ngay
        showDialog(DATE_DIALOG_ID);
     }
     else if (v.getId()==R.id.btn_timepicker) {
         // Hien thi dialog chon gio
         showDialog(TIME_DIALOG_ID);
     }
     else if (v.getId()==R.id.btn_themcv) {
         add();
     }
    }
    // khoi tao ban dau
    private void init() {
        adapter =new TodosListViewAdapter(todos,getApplicationContext());
        todoList.setAdapter(adapter);
        // Bat su kien nhan vao item de hien thi dialog chi tiec cong viec
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Todo todo= adapter.getItem(position);
                String tmp = "Tên: "+todo.getName()+"\nNội dung: "+ todo.getDetails()  +
                        "\nThời gian: "+todo.getTime()+"\nNgày: "+todo.getDate()+"\nTrạng thái: "+todo.getStatus();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage(tmp);
                // Neu cong viec co trang thai "chua xong" thi hien thi nut danh dau da xong
                if (todo.getStatus().endsWith("Chưa xong")) {
                    alertDialogBuilder.setPositiveButton("Đã xong",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                       todo.setStatus("Đã xong");
                                       adapter.notifyDataSetChanged();
                                }
                            });
                }
                // Button trở về tren dialog, khong lam gi
                alertDialogBuilder.setNegativeButton("Trở về",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do what you want to do if user clicks cancel.
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        );
        // Bat su kien nhan vao item de xoa
        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                tmpTodo = adapter.getItem(index);
                adapter.remove(tmpTodo);
                Snackbar.make(v,"Đã xóa công việc: "+tmpTodo.getName(),Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    // Neu nguoi dung muon undo thi them item vua xoa vao lai danh sach
                    public void onClick(View v) {
                        adapter.add(tmpTodo);
                        tmpTodo=null;
                        adapter.notifyDataSetChanged();
                    }
                }).show();
                return true;
            }
        });
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //Hien thi ngay hien tai
        tvDate.setText(new StringBuilder()
                .append(day).append("/").append(month+1).append("/").append(year).append(""));
        //Hien thi gio hien tai
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        tvTime.setText(new StringBuilder()
                .append(hour).append(":").append(minute).append(""));
        // Bat su kien khi chon ngay o dialog
        datePickerLisner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                tvDate.setText(new StringBuilder()
                        .append(day).append("/").append(month+1).append("/").append(year).append(""));
            }
        };
        // Bat su kien khi chon gio o dialog
        timePickerLisner = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tvTime.setText(new StringBuilder()
                            .append(hourOfDay).append(":").append(minute).append(""));
                }
    };
    }
    // them cong viec
    private void add() {
        // Kiem tra cac truong nhap ten va noi dung da duoc nhap chua
        if (edtName.getText().toString().isEmpty()) {
                Toast.makeText(this,"Tên công việc không thể để trống",Toast.LENGTH_LONG).show();
                return;
            }
            if (edtDetail.getText().toString().isEmpty()) {
                Toast.makeText(this,"nội dung công việc không thể để trống",Toast.LENGTH_LONG).show();
                return;
            }
          // Them cong viec vao danh sach
            adapter.add(new Todo(tvDate.getText().toString(),tvTime.getText().toString(),edtName.getText().toString(),edtDetail.getText().toString()));
            adapter.notifyDataSetChanged();
            // Sau khi them cong viec thi reset lai text edit va thoi gian
            reset();
    }
    // Ham dat lai thoi gian va xoa truong nhap van ban
    private void reset() {
        // xoa truong nhap van ban
        edtName.setText("");
        edtDetail.setText("");

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //Hien thi ngay hien tai
        tvDate.setText(new StringBuilder()
                .append(day).append("/").append(month+1).append("/").append(year).append(""));
        //hien thi gio hien tai
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        tvTime.setText(new StringBuilder()
                .append(hour).append(":").append(minute).append(""));

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch(id){
            case DATE_DIALOG_ID:
                // Tao dialog chon ngay, mac dinh la ngay hien tai
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this,datePickerLisner,year,month,day);
            case TIME_DIALOG_ID:
                // Tao dialog chon gio, mac dinh la gio hien tai
                Calendar c2 = Calendar.getInstance();
                int hour = c2.get(Calendar.HOUR_OF_DAY);
                int minute = c2.get(Calendar.MINUTE);
                return new TimePickerDialog(this,timePickerLisner,hour,minute,true);


        }
        return null;
    }

}
