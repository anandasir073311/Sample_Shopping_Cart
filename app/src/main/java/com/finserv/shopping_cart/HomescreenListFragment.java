package com.finserv.shopping_cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finserv.shopping_cart.bo.MenuBO;

import java.util.ArrayList;
import java.util.Vector;

public class HomescreenListFragment extends Fragment {

    ListView lstHomeScreen;
    Vector<MenuBO> menuList = new Vector<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreenlist, container, false);
        lstHomeScreen = view.findViewById(R.id.lstHomeScreen);

        inititlizeMenu();

        MenuAdapter adapter = new MenuAdapter(menuList);
        lstHomeScreen.setAdapter(adapter);

        return view;
    }

    private void inititlizeMenu() {
        menuList = new Vector<>();
        menuList.add(new MenuBO(1, "Product Catalog"));
        menuList.add(new MenuBO(2, "Settings"));
        menuList.add(new MenuBO(3, "About"));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    class MenuAdapter extends BaseAdapter {

        Vector<MenuBO> items;

        public MenuAdapter(Vector<MenuBO> menuDB) {
            items = menuDB;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return items.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MenuBO configTemp = items.get(position);
            final ViewHolder holder;
            if (convertView == null) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_homescreenlist, parent,
                        false);
                holder = new ViewHolder();
                holder.menuBTN = convertView
                        .findViewById(R.id.menuTvName);
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.position = position;
            holder.menu = configTemp;
            holder.menuBTN.setText(configTemp.getMenuName());
            return convertView;
        }

        class ViewHolder {
            MenuBO menu;
            int position;
            private TextView menuBTN;
        }
    }
}
