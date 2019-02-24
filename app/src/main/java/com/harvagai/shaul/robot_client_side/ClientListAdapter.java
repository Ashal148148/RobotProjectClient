package com.harvagai.shaul.robot_client_side;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Adapter for connecting to the server
 *
 * @author Shaul Carvalho
 *         Date: 24/02/19
 */
class ClientListAdapter extends BaseAdapter {
    private ArrayList<String> myList;

    public ClientListAdapter(Context context, ArrayList<String> list)
    {
        myList=list;
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
