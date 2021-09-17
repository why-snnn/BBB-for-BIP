package com.example.bbb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbb.Adapter;
import com.example.bbb.HttpHelper;
import com.example.bbb.Message;
import com.example.bbb.R;
import com.example.bbb.databinding.FragmentHomeBinding;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HomeFragment extends Fragment {

    private HttpHelper httpHelper;
    private FragmentHomeBinding binding;
    private int position;
    private ArrayList<Message> messagesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.httpHelper = HttpHelper.getHttpHelper();


        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        messagesList = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    messagesList = httpHelper.HttpGetMessage();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Adapter adapter = new Adapter(new ArrayList(messagesList), root.getContext());
        recyclerView.setAdapter(adapter);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        };
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}