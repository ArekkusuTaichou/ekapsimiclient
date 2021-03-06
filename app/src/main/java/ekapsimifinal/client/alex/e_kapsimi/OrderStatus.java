package ekapsimifinal.client.alex.e_kapsimi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ekapsimifinal.client.Model.Request;
import ekapsimifinal.client.alex.e_kapsimi.Common.Common;

import ekapsimifinal.client.alex.e_kapsimi.R;

import ekapsimifinal.client.alex.e_kapsimi.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase mDatabase;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        mDatabase=FirebaseDatabase.getInstance();
        requests=mDatabase.getReference("Requests");

        mRecyclerView=(RecyclerView)findViewById(R.id.listOrders);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadOrders(Common.current_user.getPhone());
    }


    private void loadOrders(String phone) {

        adapter= new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderSurname.setText(model.getSurname());
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0"))
        {
            return "Σε εξέλιξη";
        }
        else if(status.equals("1"))
        {
            return "Ετοιμάζεται";
        }
        else if(status.equals("2"))
        {
            return "Καθ΄ οδον";
        }
        else
        {
            return "Παραδώθηκε";
        }

    }
}
