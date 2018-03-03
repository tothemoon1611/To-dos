package projecttest.com.congviec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cuong on 3/3/2018.
 */

public class TodosListViewAdapter extends ArrayAdapter<Todo>{


    ArrayList<Todo> dataSet;
    Context mContext;
    public TodosListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public TodosListViewAdapter(ArrayList<Todo> data, Context context) {
        super(context, R.layout.todolist_item, data);
        this.dataSet = data;
        this.mContext=context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = dataSet.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        View v=convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.todolist_item, null);

        }
        TextView tt1 = (TextView) v.findViewById(R.id.tv_name);
        TextView tt2 = (TextView) v.findViewById(R.id.tv_date);
        TextView tt3 = (TextView) v.findViewById(R.id.tv_time);
        TextView tt4 = (TextView) v.findViewById(R.id.tv_status);

        if (tt1 != null) {
            tt1.setText(todo.getName());
        }

        if (tt2 != null) {
            tt2.setText("Ngày: "+todo.getDate());
        }

        if (tt3 != null) {
            tt3.setText("Giờ: "+todo.getTime());
        }
        if (tt4 != null) {
            tt4.setText("Trạng thái: "+todo.getStatus());
        }

        // Return the completed view to render on screen
        return v;
    }
}

