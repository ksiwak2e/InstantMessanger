package com.uksw.instantmessanger;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.jmdns.ServiceInfo;

public class SearchHostsActivity extends AppCompatActivity {



    private static final String TAG = "SearchHostsActivity";

    private static RecyclerView hostsRecyclerView;
    private static HostAdapter hostAdapter;
    Handler handler = new android.os.Handler();
    private JmdnsService jmdnsService;
    private ServiceInfo localPresence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hosts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jmdnsService=new JmdnsService(getBaseContext());
        handler.postDelayed(new Runnable() {
            public void run() {
                jmdnsService.setUp();
                localPresence=jmdnsService.getServiceInfo();
            }
        }, 1000);

        hostsRecyclerView = (RecyclerView) findViewById(R.id.hosts_list_recycler_view);
        hostsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        HostsModel model = HostsModel.get(getBaseContext());
        List<Host> hosts = model.getHosts();

        hostAdapter = new HostAdapter(hosts);
        hostsRecyclerView.setAdapter(hostAdapter);

        //REFRESHIN


    }

    public static void updateAdapter(List<Host> hosts) {
        hostsRecyclerView.getRecycledViewPool().clear();
        //hostAdapter.notifyDataSetChanged();
        hostAdapter.setHosts(hosts);
        hostAdapter.notifyDataSetChanged();
        //hostsRecyclerView.setAdapter(hostAdapter);
    }

    private class HostHolder extends RecyclerView.ViewHolder
    {
        private TextView hostTextView;
        private Host host;
        public HostHolder ( View itemView)
        {
            super(itemView);

            hostTextView = (TextView) itemView.findViewById(R.id.host_contact_name);
            View itemvView2= itemView.findViewById(R.id.card_layout);
            itemvView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchHostsActivity.this, ChatActivity.class);
                    startActivity(intent);
                }
            });
        }

        public void bindHost( Host host)
        {
            this.host = host;
            if (this.host == null)
            {
                Log.d(TAG,"Trying to work on a null Contact object ,returning.");
                return;
            }
            hostTextView.setText(this.host.getHostName());

        }
    }

    private class HostAdapter extends RecyclerView.Adapter<HostHolder>
    {
        private List<Host> hosts;

        public HostAdapter( List<Host> hostsList)
        {
            hosts = hostsList;
        }

        @Override
        public HostHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.host_item, parent,
                            false);
            return new HostHolder(view);
        }

        @Override
        public void onBindViewHolder(HostHolder holder, int position) {
            Host host = hosts.get(position);
            holder.bindHost(host);

        }

        @Override
        public int getItemCount() {
            return hosts.size();
        }

        public void setHosts(List<Host> hosts){
            this.hosts=hosts;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //new Thread(){public void run() {setUp();}}.start();
    }

    @Override
    protected void onStop() {
        if (jmdnsService != null) {
            jmdnsService.removeService();
            super.onStop();
        }
    }
}
