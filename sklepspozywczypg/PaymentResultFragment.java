package com.example.sklepspozywczypg;

import static com.example.sklepspozywczypg.SecondCartFragment.ORDER_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class PaymentResultFragment extends Fragment {

    private TextView txtMessage;
    private Button btnHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_result, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (null != order) {
                    if (order.isSuccess()) {
                        txtMessage.setText("Sukces! Płatność dokonana!\nSpodziewaj się zamówienia w ciągu 3 dni roboczych!");
                        Utils.clearCartItems(getActivity());
                        for (GroceryItem item: order.getItems()) {
                            Utils.increasePopularityPoint(getActivity(), item, 1);
                            Utils.changeUserPoint(getActivity(), item, 4);
                        }
                    }else {
                        txtMessage.setText("Płatność nieudana,\nProszę spróbować inną metodę płatności");
                    }
                }
            }else {
                txtMessage.setText("Płatność nieudana,\nProszę spróbować inną metodę płatności");
            }
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initViews(View view) {
        txtMessage = view.findViewById(R.id.txtMessage);
        btnHome = view.findViewById(R.id.btnHome);

    }
}
