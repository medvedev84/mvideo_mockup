package com.terralink.android.mvideo.mockup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.terralink.android.mvideo.mockup.model.Product;
import com.terralink.android.mvideo.mockup.adapter.ProductsExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressCardDetailFragment extends Fragment {
    public static final String ARG_ADDRESS_NUMBER = "address_number";

    private List<String> orders;
    private HashMap<String, List<Product>> ordersCollection;

    public AddressCardDetailFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_address_card_detail, container, false);
        int i = getArguments().getInt(ARG_ADDRESS_NUMBER);

        //do business logic here
        prepareData();

        final ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.product_list);
        expListView.setAdapter(
                new ProductsExpandableListAdapter(
                        getActivity(),
                        orders,
                        ordersCollection
                )
        );
        return rootView;
    }

    private void prepareData() {
        orders = new ArrayList<String>();
        ordersCollection = new HashMap<String, List<Product>>();

        // Adding routes
        orders.add("Completion");

        // Adding orders
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Notebook Samsung NP530", 20000));
        products.add(new Product("Smartphone HTC One X", 12000));
        products.add(new Product("DVD Player Toshiba", 2000));
        products.add(new Product("Photo Camera Canon E50", 50000));
        products.add(new Product("iPhone 5C", 22000));
        products.add(new Product("Cellphone Nokia 1100", 1500));
        products.add(new Product("TV LG C34 full HD", 70000));
        products.add(new Product("USB Drive 4Gb", 200));

        ordersCollection.put(orders.get(0), products);
    }

}
